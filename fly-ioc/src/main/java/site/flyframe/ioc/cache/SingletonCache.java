package site.flyframe.ioc.cache;

import site.flyframe.ioc.utils.StringUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zeng
 * @Classname SingletonCache
 * @Description TODO
 * @Date 2019/9/10 22:12
 */
public class SingletonCache implements Cache{
    private ConcurrentHashMap<String,Object> beanNameMap=new ConcurrentHashMap<String, Object>();

    private ConcurrentHashMap<Class,Object> beanClassMap=new ConcurrentHashMap<Class, Object>();

    public Object getBeanForBeanName(String beanName) {
        return beanNameMap.get(beanName);
    }

    public <T> T getBeanForBeanClass(Class<T> tClass) {
        T t = (T) beanClassMap.get(tClass);
        return t;
    }

    public void putBean(String beanName,Object o) {
        beanNameMap.put(beanName,o);
        beanClassMap.put(o.getClass(),o);
    }

    public void putBean(Object o) {
        beanNameMap.put(StringUtil.classNameToDefaultBeanName(o.getClass().getSimpleName()),o);
        beanClassMap.put(o.getClass(),o);
    }
}
