package site.flyframe.aop.core;

import site.flyframe.ioc.annotation.Component;
import site.flyframe.ioc.core.BeanFactoryProcessor;
import site.flyframe.ioc.core.factory.BaseBeanFactory;
import site.flyframe.ioc.exception.BeanFactoryException;

@Component
public class AopBeanFactoryProcessor implements BeanFactoryProcessor {
    public void postProcessBeanFactory(BaseBeanFactory beanFactory) throws BeanFactoryException {
        // 获取所有的beanDefinition

        // 获取Aspect修饰的beanDefinition

        // 提取切面信息

        // 生成代理对象

        // 打印日志
    }
}
