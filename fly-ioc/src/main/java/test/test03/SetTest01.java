package test.test03;

import annotation.Component;
import test.test02.ValueTest;

/**
 * @author zeng
 * @Classname SetTest01
 * @Description TODO
 * @Date 2019/9/13 15:56
 */
@Component
public class SetTest01 {
    private ValueTest valueTest;

    public void setValueTest(ValueTest valueTest) {
        this.valueTest = valueTest;
    }

    public ValueTest getValueTest() {
        return valueTest;
    }
}

