package test.test04;

import annotation.Component;
import test.test02.ValueTest;

/**
 * @author zeng
 * @Classname ConstructTest
 * @Description TODO
 * @Date 2019/9/13 16:10
 */
@Component
public class ConstructTest {
    private ValueTest valueTest;

    public ConstructTest(ValueTest valueTest){
        this.valueTest=valueTest;
    }

    public ValueTest getValueTest() {
        return valueTest;
    }
}
