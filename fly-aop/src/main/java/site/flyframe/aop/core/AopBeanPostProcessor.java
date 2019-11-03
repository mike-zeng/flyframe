package site.flyframe.aop.core;

import site.flyframe.aop.annotation.Aspect;
import site.flyframe.aop.proxy.CglibProxyClassGenerateStrategy;
import site.flyframe.aop.proxy.ProxyClassContext;
import site.flyframe.ioc.annotation.Component;
import site.flyframe.ioc.core.BeanPostProcessor;

@Component
public class AopBeanPostProcessor implements BeanPostProcessor {

    private ProxyClassContext proxyClassContext=new ProxyClassContext();

    {
        proxyClassContext.setStrategy(new CglibProxyClassGenerateStrategy());
    }

    @Override
    public Object beforeInitMethodInvoke(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object afterInitMethodInvoke(Object bean, String beanName) throws Exception {
        // 排除Aspect对象
        if (checkAspectObject(bean)){
            return bean;
        }
        // 生成并返回一个代理对象
        bean=proxyClassContext.getProxy(bean);
        return bean;
    }

    private boolean checkAspectObject(Object object){
        if (object.getClass().getAnnotation(Aspect.class)==null) {
            return false;
        }
        return true;
    }
}
