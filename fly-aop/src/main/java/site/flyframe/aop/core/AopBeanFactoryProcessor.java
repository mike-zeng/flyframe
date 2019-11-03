package site.flyframe.aop.core;

import lombok.extern.slf4j.Slf4j;
import site.flyframe.aop.annotation.Advise;
import site.flyframe.aop.annotation.Aspect;
import site.flyframe.ioc.annotation.Component;
import site.flyframe.ioc.core.BeanFactoryProcessor;
import site.flyframe.ioc.core.factory.BaseBeanFactory;
import site.flyframe.ioc.exception.BeanFactoryException;
import site.flyframe.ioc.meta.BeanDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class AopBeanFactoryProcessor implements BeanFactoryProcessor {

    private static List<Object> aspectObjectList=new ArrayList<>();

    private static Map<Method,Object> aspectMethodObjectHashMap=new ConcurrentHashMap<>();

    private static List<Method> aspectMethodList=new ArrayList<>();

    public void postProcessBeanFactory(BaseBeanFactory beanFactory) throws BeanFactoryException {
        // 获取所有的beanDefinition
        final List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        // 获取Aspect修饰的beanDefinition
        List<BeanDefinition> aspectBeanDefinitions = getAspectBeanDefinitions(beanDefinitions);
        List<Object> aspectObject = getAspectObject(aspectBeanDefinitions);
        aspectObjectList.add(aspectObject);

        // 缓存所有的方法
        for (Object o : aspectObject) {
            List<Method> adviceMethods = getAdviceMethods(o);
            for (Method adviceMethod : adviceMethods) {
                aspectMethodList.add(adviceMethod);
                aspectMethodObjectHashMap.put(adviceMethod,o);
            }
        }

        // 方法排序

        // 打印日志
        log.info("get aspect beanDefinition success");
    }

    public static Map<Method, Object> getAspectMethodObjectHashMap() {
        return aspectMethodObjectHashMap;
    }

    public static List<Method> getAspectMethodList() {
        return aspectMethodList;
    }

    // 提取方法
    private List<Method> getAdviceMethods(Object o){
        Method[] declaredMethods = o.getClass().getDeclaredMethods();
        List<Method> ret=new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if (null!=declaredMethod.getAnnotation(Advise.class)){
                ret.add(declaredMethod);
            }
        }
        return ret;
    }

    public static List<Object> getAspectObjectList() {
        return aspectObjectList;
    }


    /**
     * 过滤出所有被Aspect注解修饰的类
     * @param beanDefinitions beanDefinition
     * @return 过滤后的列表
     */
    private List<BeanDefinition> getAspectBeanDefinitions(List<BeanDefinition> beanDefinitions){
        List<BeanDefinition> ret= new ArrayList<>();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (filter(beanDefinition)) {
                ret.add(beanDefinition);
            }
        }
        return ret;
    }


    private List<Object> getAspectObject(List<BeanDefinition> beanDefinitions){
        List<Object> objects=new ArrayList<>();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Object o = beanDefinition.getBeanClass().newInstance();
                objects.add(o);
            } catch (Exception e) {
                log.warn("can not parser this aspect class need a no-argument construct"+beanDefinition.getBeanClass());
            }
        }
        return objects;
    }

    private boolean filter(BeanDefinition beanDefinition){
        Annotation[] annotations = beanDefinition.getBeanClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Aspect){
                return true;
            }
        }
        return false;
    }
}
