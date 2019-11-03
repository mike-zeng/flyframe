package site.flyframe.ioc.meta.factory;

import site.flyframe.ioc.meta.BeanDefinition;
import site.flyframe.ioc.utils.StringUtil;

import java.lang.annotation.Annotation;

public class SimplerBeanDefinitionFactory extends AbstractBeanDefinitionFactory {
    @Override
    BeanDefinition createBeanDefinition(Annotation annotation, Class aClass) {
        BeanDefinition beanDefinition=new BeanDefinition();
        beanDefinition.setBeanName(StringUtil.classNameToDefaultBeanName(aClass.getSimpleName()));
        beanDefinition.setBeanClass(aClass);
        return beanDefinition;
    }
}
