package site.flyframe.ioc.meta.factory;

import site.flyframe.ioc.annotation.Component;
import site.flyframe.ioc.annotation.Service;
import site.flyframe.ioc.meta.BeanDefinition;

import java.lang.annotation.Annotation;

public class DefaultBeanDefinitionFactory {
    public BeanDefinition createBeanDefinition(Annotation annotation, Class aClass){
        if (annotation instanceof Component){
            return new ComponentBeanDefinitionFactory().createBeanDefinition(annotation, aClass);
        }else if (annotation instanceof Service){
            return new ServiceBeanDefinitionFactory().createBeanDefinition(annotation,aClass);
        }
        return null;
    }
}
