package site.flyframe.ioc.core.scanner;

import java.util.*;

/**
 * @author zeng
 * @Classname BaseScanner
 * @Description
 * @Date 2019/9/10 22:30
 */
public abstract class BasePackageScanner implements Scanner {
    Set<Class<?>> classes=new HashSet<Class<?>>();

    BasePackageScanner(){}

    /**
     * 模板方法，初始化类内部的一些配置信息
     */
    protected void init() {}

    @Override
    public List<Class<?>> scanner() {
        // 初始化信息，为扫描做准备
        init();
        List<Class<?>> ret= new ArrayList<Class<?>>();
        for (Class<?> aClass : classes) {
            if (filter(aClass)){
                ret.add(aClass);
            }
        }
        return ret;
    }

    /**
     * 定义过滤规则，排除不符合要求的类
      * @param tClass 类
     * @return 过滤结果
     */
    abstract boolean filter(Class tClass);
}
