package site.flyframe.ioc.meta.factory;

import site.flyframe.ioc.meta.BeanDefinition;

import java.lang.annotation.Annotation;

public abstract class AbstractBeanDefinitionFactory {
    abstract BeanDefinition createBeanDefinition(Annotation annotation,Class aClass);
}
