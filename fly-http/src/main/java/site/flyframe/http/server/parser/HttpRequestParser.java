package site.flyframe.http.server.parser;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zeng
 * @Classname HttpRequestParser
 * @Description Http请求解析
 * @Date 2019/9/19 22:35
 */
public class HttpRequestParser implements HttpParser {
    private FullHttpRequest request;

    private static final String UN_LEN_CODED="x-www-form-urlencoded";
    private static final String JSON="application/json";

    public HttpRequestParser(FullHttpRequest request){
        this.request=request;
    }

    @Override
    public Map<String, Object> parser() {

        Map<String,Object> ret=new HashMap<String, Object>();
        String strContentType =request.headers().get("Content-Type");
        HttpMethod method = request.method();
        // 从uri中解析参数
        QueryStringDecoder urlDecoder = new QueryStringDecoder(request.uri());
        ret.putAll(urlDecoder.parameters());

        if (strContentType==null){
            return ret;
        }
        // 从body中解析参数
        if (strContentType.contains(UN_LEN_CODED)){
            HttpPostRequestDecoder bodyDecoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false), request);
            for (InterfaceHttpData bodyHttpData : bodyDecoder.getBodyHttpDatas()) {
                if (bodyHttpData.getHttpDataType()== InterfaceHttpData.HttpDataType.Attribute){
                    MemoryAttribute attribute = (MemoryAttribute) bodyHttpData;
                    ret.put(attribute.getName(),attribute.getValue());
                }
            }
        } else if (strContentType.contains(JSON)) {
            // 处理json格式的数据

        } else {

        }
        return ret;
    }
}
