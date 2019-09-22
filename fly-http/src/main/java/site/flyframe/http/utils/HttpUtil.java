package site.flyframe.http.utils;

/**
 * @author zeng
 * @Classname HttpUtil
 * @Description TODO
 * @Date 2019/9/22 13:10
 */
public class HttpUtil {

    private static final int SESSION_ID_LEN=15;
    private HttpUtil(){}

    public static String produceSessionId(){
        StringBuilder stringBuffer=new StringBuilder();
        for (int i=0;i<SESSION_ID_LEN;i++){
            int num= 'A'+(int) (0+Math.random()*(26));
            stringBuffer.append(((char) num));
        }
        return stringBuffer.toString();
    }
}
