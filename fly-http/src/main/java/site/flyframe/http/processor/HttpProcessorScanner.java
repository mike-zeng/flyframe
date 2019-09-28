package site.flyframe.http.processor;

import site.flyframe.http.context.FlyHttpContext;
import site.flyframe.http.utils.ReflectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zeng
 * @Classname HttpProcessorScanner
 * @Description TODO
 * @Date 2019/9/25 19:48
 */
public class HttpProcessorScanner implements ProcessorScanner {
    @Override
    public Set<Class<?>> scanner() {
        FlyHttpContext context=FlyHttpContext.getFlyHttpContext();
        Set<Class<?>> classes=new HashSet<>();
        List<String> processorPackagePath = context.getConfig().getProcessorPackagePath();
        if (processorPackagePath.size()==0){
            return classes;
        }

        for (String path : processorPackagePath) {
            Set<Class<?>> retClasses = ReflectionUtil.getClassFromPackagePath(path);
            for (Class<?> aClass : retClasses) {
                if (Processor.class.isAssignableFrom(aClass)) {
                    classes.add(aClass);
                }
            }
        }
        return classes;
    }
}
