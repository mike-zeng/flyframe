package site.flyframe.http.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zeng
 * @Classname HttpUtilTest
 * @Description TODO
 * @Date 2019/9/22 13:16
 */
public class HttpUtilTest {

    @Test
    public void produceSessionId() {
        System.out.println(HttpUtil.produceSessionId());
    }
}