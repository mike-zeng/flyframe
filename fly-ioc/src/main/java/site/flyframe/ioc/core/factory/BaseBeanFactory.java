package site.flyframe.ioc.core.factory;

import site.flyframe.ioc.annotation.Bean;
import site.flyframe.ioc.annotation.Value;
import site.flyframe.ioc.cache.Cache;
import site.flyframe.ioc.cache.SingletonCache;
import site.flyframe.ioc.core.BeanPostProcessor;
import site.flyframe.ioc.core.scanner.PackageScanner;
import site.flyframe.ioc.core.scanner.Scanner;
import site.flyframe.ioc.meta.BeanDefinition;
import site.flyframe.ioc.meta.Depend;
import site.flyframe.ioc.meta.DependEnum;
import site.flyframe.ioc.meta.factory.DefaultBeanDefinitionFactory;
import site.flyframe.ioc.utils.StringUtil;

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

    private DefaultBeanDefinitionFactory defaultBeanDefinitionFactory=new DefaultBeanDefinitionFactory();

    ConcurrentHashMap<String,BeanDefinition> beanNameBeanDefinitionMap=new ConcurrentHashMap<String, BeanDefinition>();
    ConcurrentHashMap<Class<?>,BeanDefinition> beanClassBeanDefinitionMap=new ConcurrentHashMap<Class<?>, BeanDefinition>();

    List<BeanPostProcessor> beanPostProcessorList=new ArrayList<>();

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
            BeanDefinition beanDefinition = converterBeanClassToBeanDefinition(aClass);
            beanDefinitions.add(beanDefinition);
        }
        return beanDefinitions;
    }

    private boolean checkBeanAnnotation(Annotation annotation){
        if (annotation==null){
            return false;
        }
        Annotation[] declaredAnnotations = annotation.annotationType().getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation instanceof Bean){
                return true;
            }
        }
        return false;
    }
    /**
     * 将Class对象转化成BeanDefinition对象
     * @param aClass Class对象
     * @return BeanDefinition对象
     */
    private BeanDefinition converterBeanClassToBeanDefinition(Class<?> aClass){
        BeanDefinition beanDefinition=null;

        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (checkBeanAnnotation(annotation)){
                // 使用工厂模式来创建BeanDefinition
                beanDefinition =defaultBeanDefinitionFactory.createBeanDefinition(annotation, aClass);
                break;
            }
        }
        if (beanDefinition==null){
            return null;
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
