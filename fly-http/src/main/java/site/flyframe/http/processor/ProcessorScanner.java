package site.flyframe.http.processor;

import java.util.Set;

/**
 * @author zeng
 * @Classname ProcessorScanner
 * @Description TODO
 * @Date 2019/9/25 19:46
 */
public interface ProcessorScanner {
    /**
     * 从上下文环境指定的包中扫描所有的http请求处理器
     * @return 处理器
     */
    Set<Class<?>> scanner();
}
