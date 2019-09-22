package site.flyframe.ioc.test.test01;

import site.flyframe.ioc.annotation.Component;

/**
 * @author zeng
 * @Classname ComponetTest
 * @Description TODO
 * @Date 2019/9/13 11:33
 */
@Component(beanName = "c1",singleton = false,lazy = false)
public class ComponentTest extends Test01 {
}
