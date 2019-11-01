package site.flyframe.ioc.core;

import site.flyframe.ioc.core.factory.BaseBeanFactory;
import site.flyframe.ioc.exception.BeanFactoryException;

public interface BeanFactoryProcessor {
    void postProcessBeanFactory(BaseBeanFactory beanFactory) throws BeanFactoryException;
}
