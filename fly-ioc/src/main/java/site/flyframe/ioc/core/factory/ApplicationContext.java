package site.flyframe.ioc.core.factory;

import lombok.extern.slf4j.Slf4j;
import site.flyframe.ioc.annotation.Autowired;
import site.flyframe.ioc.annotation.AutowiredEnum;
import site.flyframe.ioc.core.BeanPostProcessor;
import site.flyframe.ioc.exception.BeanFactoryException;
import site.flyframe.ioc.exception.InjectionException;
import site.flyframe.ioc.exception.enums.BeanFactoryExceptionEnum;
import site.flyframe.ioc.exception.enums.InjectionExceptionEnum;
import site.flyframe.ioc.meta.BeanDefinition;
import site.flyframe.ioc.meta.Depend;
import site.flyframe.ioc.meta.DependEnum;
import site.flyframe.ioc.utils.ReflectionUtil;
import site.flyframe.ioc.utils.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zeng
 * @Classname ApplicationContext
 * @Description flyframe应用上下文，提供一个基于注解扫描的IoC容器
 * @Date 2019/9/10 22:28
 */
@Slf4j
public class ApplicationContext extends BaseBeanFactory {

    public ApplicationContext(List<String> scannerPackageList) throws Exception {
        super(scannerPackageList);
        log.info("run success");
    }

    @Override
    Object doGetBean(String beanName) {
        // 查找该beanName对应的BeanDefinition是否存在
        if (beanNameBeanDefinitionMap.get(beanName)==null){
            log.info("can not find this bean by beanName"+beanName);
            return null;
        }

        // 判断单例缓冲中是否存在该bean
        if (singletonCache.getBeanForBeanName(beanName)!=null){
            return singletonCache.getBeanForBeanName(beanName);
        }

        // 获取beanName对应的BeanDefinition
        BeanDefinition beanDefinition = beanNameBeanDefinitionMap.get(beanName);
        if (beanDefinition!=null){
            Object o = null;
            try {
                // 将beanDefinition解析为bean对象
                o = parserBeanDefinitionToObject(beanDefinition);
            } catch (BeanFactoryException e) {
                e.printStackTrace();
            }
            // 如果该bean为单例则加入到单例缓存中
            if (beanDefinition.isSingleton()){
                singletonCache.putBean(beanName,o);
            }
            return o;
        }else {
            return null;
        }
    }

    @Override
    <T> T doGetBean(Class<T> tClass) {
        // 查找改类型对应的BeanDefinition
        if (!beanClassBeanDefinitionMap.containsKey(tClass)){
            return null;
        }
        // 判断单例缓冲中是否存在该bean
        if (singletonCache.getBeanForBeanClass(tClass)!=null){
            return singletonCache.getBeanForBeanClass(tClass);
        }
        // 获取该Class对应的BeanDefinition
        BeanDefinition definition = beanClassBeanDefinitionMap.get(tClass);
        Object o;
        try {
            // 将BeanDefinition解析为对象
            o = parserBeanDefinitionToObject(definition);
        } catch (BeanFactoryException e) {
            e.printStackTrace();
            return null;
        }
        // 放入单例缓存中
        if (definition.isSingleton()){
            singletonCache.putBean((o));
        }
        return (T) o;
    }

    /**
     * 将BeanDefinition解析成java bean对象
     * @param beanDefinition BeanDefinition
     * @return 对象
     * @throws BeanFactoryException BeanDefinition异常
     */
    private Object parserBeanDefinitionToObject(BeanDefinition beanDefinition) throws BeanFactoryException {
        try {
            // 创建对象
            Object object = createObject(beanDefinition);
            if (object==null){
                return null;
            }
            List<Depend> depends = beanDefinition.getDepends();
            setMethodInjection(object,depends);

            // 进行Autowired注入
            autowiredInjection(object,depends);

            // 进行value注入
            valueInjection(object,depends);

            // 执行BeanPostProcessor的beforeInitMethodInvoke方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                object=beanPostProcessor.beforeInitMethodInvoke(object,beanDefinition.getBeanName());
            }

            // 调用init方法
            invokeInitMethod(object,beanDefinition);

            // 执行BeanPostProcessor的afterInitMethodInvoke方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                object=beanPostProcessor.afterInitMethodInvoke(object,beanDefinition.getBeanName());
            }
            return object;
        }catch (Exception e) {
            log.error("bean definition parser failed");
            throw new BeanFactoryException(BeanFactoryExceptionEnum.BEAN_DEFINITION_PARSER_FAILED);
        }
    }

    private void invokeInitMethod(Object target,BeanDefinition beanDefinition) throws BeanFactoryException {
        try {
            String initMethod = beanDefinition.getInitMethod();
            if (initMethod==null||"".equals(initMethod)){
                return;
            }
            Method method = beanDefinition.getBeanClass().getMethod(initMethod);
            if (method==null){
                return;
            }
            method.invoke(target);
        }catch (Exception e){
            log.error("the init method of bean invoke fail");
            throw new BeanFactoryException(BeanFactoryExceptionEnum.BEAN_DEFINITION_PARSER_FAILED);
        }
    }


    /**
     * 构造对象
     * @param beanDefinition BeanDefinition
     * @return 创建的对象
     * @throws Exception 创建对象异常
     */
    private Object createObject(BeanDefinition beanDefinition) throws Exception{
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
            List parameters=null;

            if (depend.getDependValue() instanceof List){
                parameters=(List)depend.getDependValue();
            }
            if (parameters==null){
                continue;
            }
            // 判断参数是否都存在
            int count=0;
            for (Object parameter : parameters) {
                if (!beanNameBeanDefinitionMap.containsKey(parameter.toString())){
                    break;
                }
                count++;
            }
            // 找到了合适的构造方法,构造出参数对象
            List<Object> args=new ArrayList<>();
            if (count==parameters.size()){
                for (Object  parameter : parameters) {
                    Object o = null;
                    try {
                        o = parserBeanDefinitionToObject(beanNameBeanDefinitionMap.get(parameter.toString()));
                    } catch (BeanFactoryException e) {
                        e.printStackTrace();
                    }
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
     */
    private void setMethodInjection(Object target,List<Depend> depends) {
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
            Object dependValue = depend.getDependValue();
            BeanDefinition definition = beanNameBeanDefinitionMap.get(dependValue.toString());
            if (method!=null&&definition!=null){
                Object arg = null;
                try {
                    arg = parserBeanDefinitionToObject(definition);
                } catch (BeanFactoryException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(target,arg);
                } catch (Exception e) {
                    log.warn("set Injection my fail when invoke "+method.getName()+" with "+target.getClass().getName());
                    e.printStackTrace();
                }
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
     * @param target 目标对象
     * @param depends 依赖
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
                BeanDefinition beanDefinition;
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
