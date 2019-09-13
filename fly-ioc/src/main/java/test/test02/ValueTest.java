package test.test02;

import annotation.Component;
import annotation.Value;

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
