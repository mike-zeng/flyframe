package site.flyframe.ioc.meta.factory;

import site.flyframe.ioc.annotation.Component;
import site.flyframe.ioc.meta.BeanDefinition;
import site.flyframe.ioc.utils.StringUtil;

import java.lang.annotation.Annotation;

public class ComponentBeanDefinitionFactory extends AbstractBeanDefinitionFactory {
    @Override
    BeanDefinition createBeanDefinition(Annotation annotation,Class aClass) {
        BeanDefinition beanDefinition=new BeanDefinition();
        beanDefinition.setBeanClass(aClass);
        Component component = (Component) annotation;
        beanDefinition.setLazy(component.lazy());
        if (!"".equals(component.beanName())){
            beanDefinition.setBeanName(component.beanName());
        }else{
            beanDefinition.setBeanName(StringUtil.classNameToDefaultBeanName(aClass.getSimpleName()));
        }
        beanDefinition.setSingleton(component.singleton());
        return beanDefinition;
    }
}
