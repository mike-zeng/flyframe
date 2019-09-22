package site.flyframe.ioc.test.test02;

import site.flyframe.ioc.annotation.Component;
import site.flyframe.ioc.annotation.Value;

/**
 * @author zeng
 * @Classname ValueTest
 * @Description TODO
 * @Date 2019/9/13 11:42
 */
@Component
public class ValueTest {
    @Value("10")
    private int value;

    @Value("hello")
    private String str;

    public int getValue() {
        return value;
    }

    public String getStr() {
        return str;
    }
}
