package utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zeng
 * @Classname ReflectionUtilTest
 * @Description TODO
 * @Date 2019/9/13 11:10
 */
public class ReflectionUtilTest {

    @Test
    public void getClassFromPackagePath() {
        assertTrue(ReflectionUtil.getClassFromPackagePath("test").size()>0);
    }
}