package factory;

import annotation.Component;
import annotation.Service;
import annotation.Value;
import cache.Cache;
import cache.SingletonCache;
import meta.BeanDefinition;
import meta.Depend;
import meta.DependEnum;
import scanner.PackageScanner;
import scanner.Scanner;
import utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zeng
 * @Classname BaseBeanFactory
 * @Description TODO
 * @Date 2019/9/10 22:27
 */
public abstract class BaseBeanFactory implements Factory{
    private boolean destroy=false;
    private Scanner scanner;

    ConcurrentHashMap<String,BeanDefinition> beanNameBeanDefinitionMap=new ConcurrentHashMap<String, BeanDefinition>();
    ConcurrentHashMap<Class<?>,BeanDefinition> beanClassBeanDefinitionMap=new ConcurrentHashMap<Class<?>, BeanDefinition>();
    Cache singletonCache=new SingletonCache();

    BaseBeanFactory(List<String> scannerPackageList) throws Exception {
        // 负责从包中扫描出带注解的类，然后转化为BeanDefinition对象
        scanner=new PackageScanner(scannerPackageList);
        List<Class<?>> classes=loadClass();
        List<BeanDefinition> beanDefinitions=converterBeanClassToBeanDefinition(classes);
        loadBeanDefinition(beanDefinitions);
        // 校验BeanDefinition之间的关系是否符合容器要求
        if (!verify()){
            throw new Exception();
        }
    }


    /**
     * 校验BeanDefinition关系是否合法
     */
    private boolean verify(){
        return true;
    }

    /**
     * 加载BeanDefinition，将BeanDefinition信息保存在容器内部
     * @param beanDefinitions BeanDefinitions
     */
    private void loadBeanDefinition(List<BeanDefinition> beanDefinitions){
        for (BeanDefinition beanDefinition : beanDefinitions) {
            beanNameBeanDefinitionMap.put(beanDefinition.getBeanName(),beanDefinition);
            beanClassBeanDefinitionMap.put(beanDefinition.getBeanClass(),beanDefinition);
        }
        // 加载非懒加载的对象
        for (BeanDefinition beanDefinition : beanDefinitions) {
           if (!beanDefinition.isLazy()){
               Object o = doGetBean(beanDefinition.getBeanClass());
               // 如果是单例对象则缓存
               if (beanDefinition.isSingleton()){
                   singletonCache.putBean(o);
               }
           }
        }
    }

    /**
     * 将BeanClass转化为BeanDefinition
     * @param classes 类信息
     * @return BeanDefinition
     */
    private List<BeanDefinition> converterBeanClassToBeanDefinition(List<Class<?>> classes){
        List<BeanDefinition> beanDefinitions=new ArrayList<BeanDefinition>(classes.size());
        for (Class<?> aClass : classes) {
            beanDefinitions.add(converterBeanClassToBeanDefinition(aClass));
        }
        return beanDefinitions;
    }

    /**
     * 将Class对象转化成BeanDefinition对象
     * @param aClass Class对象
     * @return BeanDefinition对象
     */
    private BeanDefinition converterBeanClassToBeanDefinition(Class<?> aClass){
        Annotation[] annotations = aClass.getAnnotations();
        BeanDefinition beanDefinition=new BeanDefinition();
        beanDefinition.setBeanClass(aClass);

        Component component = aClass.getAnnotation(Component.class);
        Service service=aClass.getAnnotation(Service.class);
        if (component!=null){
            beanDefinition.setLazy(component.lazy());
            if (!"".equals(component.beanName())){
                beanDefinition.setBeanName(component.beanName());
            }else{
                beanDefinition.setBeanName(StringUtil.classNameToDefaultBeanName(aClass.getSimpleName()));
            }
            beanDefinition.setSingleton(component.singleton());
        } else if (service!=null) {
            beanDefinition.setLazy(service.lazy());
            if (!"".equals(service.beanName())){
                beanDefinition.setBeanName(service.beanName());
            }else{
                beanDefinition.setBeanName(StringUtil.classNameToDefaultBeanName(aClass.getSimpleName()));
            }
            beanDefinition.setSingleton(service.singleton());
        }
        // 设置依赖关系
        List<Depend> dependList=new ArrayList<>();
        // 1. value依赖
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
           if (field.getAnnotationsByType(Value.class).length!=0){
               Value valueAnnotation=field.getAnnotationsByType(Value.class)[0];
               Depend depend=new Depend(DependEnum.VALUE,field.getName(),valueAnnotation.value());
               dependList.add(depend);
           }
        }
        // 2. set依赖
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if (StringUtil.isSetMethod(method.getName())){
                String fieldName=StringUtil.setMethodNameToFieldName(method.getName());
                Depend depend=new Depend(DependEnum.SET,fieldName,fieldName);
                dependList.add(depend);
            }
        }
        // 3. 构造方法依赖
        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            // 对每个构造参数创建依赖对象
            Depend depend=new Depend();
            List<String> parameterArg=new ArrayList<>();
            for (Parameter parameter : parameters) {
                if (parameter.getAnnotation(Value.class)!=null){
                    Value value=parameter.getAnnotation(Value.class);
                    parameterArg.add(value.value()[0]);
                }else{
                    parameterArg.add(StringUtil.classNameToDefaultBeanName(parameter.getType().getSimpleName()));
                }
            }
            depend.setType(DependEnum.CONSTRUCT);
            depend.setDependKey(constructor.toGenericString());
            depend.setDependValue(parameterArg);
            dependList.add(depend);
        }
        beanDefinition.setDepends(dependList);
        return beanDefinition;
    }

    /**
     * 通过包扫描器扫描出所有的包
     */
    private List<Class<?>> loadClass(){
        return scanner.scanner();
    }

    /**
     * 从容器中获取bean对象
     * @param beanName beanName
     * @return bean对象
     */
    @Override
    public Object getBean(String beanName) {
        if (!destroy){
            return doGetBean(beanName);
        }
        return null;
    }

    /**
     * 从容器中获取bean对象
     * @param tClass bean类型
     * @param <T> 类型
     * @return bean对象
     */
    @Override
    public <T> T getBean(Class<T> tClass) {
        if (!destroy){
            return doGetBean(tClass);
        }
        return null;
    }

    /**
     * 获取bean对象
     * @param beanName bean名称
     * @return bean对象
     */
    abstract Object doGetBean(String beanName);

    /**
     * 获取bean对象
     * @param tClass bean类型
     * @param <T> 实际类型
     * @return bean对象
     */
    abstract <T> T doGetBean(Class<T> tClass);

    @Override
    public void destroy() {
        destroy=true;
    }

    @Override
    public boolean isDestroy(){
        return destroy;
    }
}
