package site.flyframe.ioc.scanner;

import site.flyframe.ioc.annotation.Component;
import site.flyframe.ioc.annotation.Service;
import site.flyframe.ioc.utils.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zeng
 * @Classname ClassPathScanner
 * @Description TODO
 * @Date 2019/9/10 22:31
 */
public class PackageScanner extends BasePackageScanner {
    private List<String> packageNames=new ArrayList<String>();

    public PackageScanner(String packageName){
        packageNames.add(packageName);
    }

    public PackageScanner(List<String> packageNames){
        this.packageNames.addAll(packageNames);
    }

    @Override
    protected void init() {
        // 扫描并保存所有的类信息
        Set<Class<?>> set;
        for (String packageName : packageNames) {
            set=ReflectionUtil.getClassFromPackagePath(packageName);
            classes.addAll(set);
        }
    }

    @Override
    boolean filter(Class tClass) {
        Annotation[] annotations = tClass.getAnnotations();
        if (annotations==null||annotations.length==0){
            return false;
        }
        // 判断类上的注解是否为Component Controller和Service
        for (Annotation annotation : annotations) {
            if (filter(annotation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断注解是否符合要求
     * @param annotation 注解
     * @return 是否返回
     */
    private boolean filter(Annotation annotation){
        return annotation instanceof Component||annotation instanceof Service;
//        Annotation[] annotations = site.flyframe.ioc.annotation.getClass().getAnnotations();
//        for (Annotation an : annotations) {
//            if (an instanceof Bean){
//                return true;
//            }
//        }
//        return false;
    }
}
