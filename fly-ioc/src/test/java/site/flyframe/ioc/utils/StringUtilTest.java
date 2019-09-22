package site.flyframe.ioc.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zeng
 * @Classname StringUtilTest
 * @Description TODO
 * @Date 2019/9/13 11:09
 */
public class StringUtilTest {

    @Test
    public void classNameToDefaultBeanName() {
        assertEquals("testClass",StringUtil.classNameToDefaultBeanName("TestClass"));
    }

    @Test
    public void setMethodNameToFieldName() {
        assertEquals("site/flyframe/ioc/test",StringUtil.setMethodNameToFieldName("setTest"));
    }

    @Test
    public void isSetMethod() {
        assertEquals(true,StringUtil.isSetMethod("setTest"));
    }
}