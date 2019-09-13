package factory;

import annotation.Autowired;
import annotation.AutowiredEnum;
import exception.InjectionException;
import exception.enums.InjectionExceptionEnum;
import meta.BeanDefinition;
import meta.Depend;
import meta.DependEnum;
import utils.ReflectionUtil;
import utils.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zeng
 * @Classname ApplicationContext
 * @Description TODO
 * @Date 2019/9/10 22:28
 */
public class ApplicationContext extends BaseBeanFactory {

    ApplicationContext(List<String> scannerPackageList) throws Exception {
        super(scannerPackageList);
    }

    @Override
    Object doGetBean(String beanName) {
        if (beanNameBeanDefinitionMap.get(beanName)==null){
            return null;
        }
        BeanDefinition beanDefinition = beanNameBeanDefinitionMap.get(beanName);
        if (beanDefinition!=null){
            return parserBeanDefinitionToObject(beanDefinition);
        }
        return null;
    }

    @Override
    <T> T doGetBean(Class<T> tClass) {
        if (!beanClassBeanDefinitionMap.containsKey(tClass)){
            return null;
        }
        BeanDefinition definition = beanClassBeanDefinitionMap.get(tClass);
        Object o = parserBeanDefinitionToObject(definition);
        return (T)o;
    }

    private Object parserBeanDefinitionToObject(BeanDefinition beanDefinition){
        try {
            // 创建对象
            Object object = createObject(beanDefinition);

            List<Depend> depends = beanDefinition.getDepends();
            setMethodInjection(object,depends);
            // 进行Autowired注入
            autowiredInjection(object,depends);
            // 进行value注入
            valueInjection(object,depends);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造对象
     * @param beanDefinition BeanDefinition
     * @return 创建的对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private Object createObject(BeanDefinition beanDefinition) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        // 使用空参构造方法创建对象
        Constructor<?>[] constructors = beanDefinition.getBeanClass().getConstructors();
        if (beanDefinition.getBeanClass().getConstructors().length==1&&beanDefinition.getBeanClass().getConstructors()[0].getParameterCount()==0){
            Constructor constructor=beanDefinition.getBeanClass().getConstructors()[0];
            return constructor.newInstance();
        }
        if (beanDefinition.getDepends()==null||beanDefinition.getDepends().size()==0){
            for (Constructor<?> constructor : constructors) {
               if (constructor.getParameterCount()==0){
                   return constructor.newInstance();
               }
            }
            return null;
        }
        // 提取构造注入信息
        HashMap<String, Depend> dependHashMap=new HashMap<>();
        for (Depend depend : beanDefinition.getDepends()) {
            if (depend.getType()==DependEnum.CONSTRUCT){
                dependHashMap.put(depend.getDependKey(),depend);
            }
        }
        // 找出依赖信息全部存在的的构造方法
        for (Constructor<?> constructor : constructors) {
            Depend depend=dependHashMap.get(constructor.toGenericString());
            if (depend==null){
                continue;
            }
            List<String> parameters=(List<String>)depend.getDependValue();
            // 判断参数是否都存在
            int count=0;
            for (String parameter : parameters) {
               if (!beanNameBeanDefinitionMap.containsKey(parameter)){
                   break;
               }
               count++;
            }
            // 找到了合适的构造方法,构造出参数对象
            List<Object> args=new ArrayList<>();
            if (count==parameters.size()){
                for (String parameter : parameters) {
                    Object o = parserBeanDefinitionToObject(beanNameBeanDefinitionMap.get(parameter));
                    args.add(o);
                }
                // 参数准备好了
                if (args.size()==count) {
                    if (args.size()==1){
                        return constructor.newInstance(args.get(0));
                    }
                    return constructor.newInstance(args);
                }
            }
        }
        return null;
    }

    /**
     * 通过set方法注入
     * @param target 目标对象
     * @param depends set类型的依赖
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void setMethodInjection(Object target,List<Depend> depends) throws InvocationTargetException, IllegalAccessException {
        List<Depend> setDepend = filterDependsByDependType(depends, DependEnum.SET);
        // 获取所有set方法
        Method[] methods = target.getClass().getDeclaredMethods();
        final String setPrefix="set";
        Map<String,Method> methodMap=new HashMap<>(methods.length);
        for (Method method : methods) {
           if (method.getName().startsWith(setPrefix)){
               if (StringUtil.isSetMethod(method.getName())){
                   methodMap.put(StringUtil.setMethodNameToFieldName(method.getName()),method);
               }
           }
        }
        // 进行set注入
        for (Depend depend : setDepend) {
            Method method = methodMap.get(depend.getDependKey());
            BeanDefinition definition = beanNameBeanDefinitionMap.get(depend.getDependValue());
            if (method!=null&&definition!=null){
                Object arg = parserBeanDefinitionToObject(definition);
                method.invoke(target,arg);
            }
        }
    }

    /**
     * 通过value对象进行注入
     * @param target 目标对象
     * @param depends 依赖值
     */
    private void valueInjection(Object target,List<Depend> depends){
        // 获取value Depend
        HashMap<String,Depend> valueDependMap=new HashMap<>();
        for (Depend depend : depends) {
           if (depend.getType()==DependEnum.VALUE){
               valueDependMap.put(depend.getDependKey(),depend);
           }
        }
        // 获取字段
        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Depend dependValue=valueDependMap.get(declaredField.getName());
           if (dependValue!=null){
               // 注入，value的值为Integer或String类型，未来将支持更多类型
               declaredField.setAccessible(true);
               try {
                   String[] str=(String[])dependValue.getDependValue();
                   Object arg= ReflectionUtil.stringArrayToOtherTypeData(str,declaredField.getType().getName());
                   declaredField.set(target,arg);
               }catch (Exception e){
                    e.printStackTrace();
               }
           }
        }
    }


    private List<Depend> filterDependsByDependType(List<Depend> depends,DependEnum type){
        List<Depend> list=new ArrayList<>();
        for (Depend depend : depends) {
            if (depend.getType()==type){
                list.add(depend);
            }
        }
        return list;
    }
    /**
     * 通过Autowired注解注入
     * @param target
     * @param depends
     */
    private void autowiredInjection(Object target,List<Depend> depends) throws InjectionException {
        try {
            List<Depend> autowiredDepends=filterDependsByDependType(depends,DependEnum.AUTOWIRED);
            if (autowiredDepends.size()==0){
                return;
            }
            for (Depend depend : autowiredDepends) {
                Field declaredField = target.getClass().getDeclaredField(depend.getDependKey());
                declaredField.setAccessible(true);
                BeanDefinition beanDefinition=null;
                // 判断注入的类型
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                if (annotation==null){
                    throw new InjectionException(InjectionExceptionEnum.AUTOWIRED_INJECTION_FAILED);
                }
                if (annotation.type()== AutowiredEnum.BY_TYPE){
                    beanDefinition=beanClassBeanDefinitionMap.get(target.getClass());
                }else{
                    beanDefinition=beanNameBeanDefinitionMap.get(annotation.beanName());
                }
                Object o = parserBeanDefinitionToObject(beanDefinition);
                declaredField.set(target,o);
            }
        }catch (Exception e){
           throw new InjectionException(InjectionExceptionEnum.AUTOWIRED_INJECTION_FAILED);
        }
    }

}
