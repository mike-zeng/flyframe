package site.flyframe.http.response;

/**
 * @author zeng
 * @Classname ResponseContent
 * @Description 封装响应内容的接口
 * @Date 2019/9/25 7:11
 */
public interface ResponseContent {
    /**
     * 获取字节码数据
     * @return 字节码
     */
    byte[] getBytes();
}
