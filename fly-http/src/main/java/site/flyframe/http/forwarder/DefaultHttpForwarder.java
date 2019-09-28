package site.flyframe.http.forwarder;

import site.flyframe.http.processor.HttpProcessorScanner;
import site.flyframe.http.processor.Processor;
import site.flyframe.http.processor.ProcessorPath;
import site.flyframe.http.processor.ProcessorScanner;
import site.flyframe.http.request.FlyHttpRequest;
import site.flyframe.http.response.ErrorResponseContent;
import site.flyframe.http.response.FlyHttpResponse;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zeng
 * @Classname DefaultHttpForwarder
 * @Description 默认的转发器
 * @Date 2019/9/25 16:52
 */
public class DefaultHttpForwarder extends BaseHttpForwarder {

    /**
     * 路由映射关系
     */
    private static Map<String, Processor> routingMap=new ConcurrentHashMap<>();

    public DefaultHttpForwarder(){
        init();
    }

    private void init(){
        ProcessorScanner scanner=new HttpProcessorScanner();
        final Set<Class<?>> classes = scanner.scanner();
        for (Class<?> aClass : classes) {
            try {
                ProcessorPath processorPath = aClass.getAnnotation(ProcessorPath.class);
                if (processorPath==null|| " ".equals(processorPath.path())){
                    continue;
                }
                final Processor o = ((Processor) aClass.newInstance());
                routingMap.put(processorPath.path(),o);
            }catch (Exception ignored){

            }
        }
    }

    @Override
    protected void doForwarder(FlyHttpRequest request,FlyHttpResponse response) throws Exception {
        final String url = request.getUrl();
        // 支持精确匹配
        if (routingMap.containsKey(url)){
            Processor processor = routingMap.get(url);
            processor.processor(request,response);
        }else {
            // 404错误 找不到该资源
            response.setContent(new ErrorResponseContent(ErrorResponseContent.ErrorPage.PAGE_NOT_FOUND));
        }
    }
}
