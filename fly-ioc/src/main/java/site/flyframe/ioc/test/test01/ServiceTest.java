package site.flyframe.ioc.test.test01;

import site.flyframe.ioc.annotation.Service;

/**
 * @author zeng
 * @Classname ServiceTest
 * @Description TODO
 * @Date 2019/9/13 11:33
 */
@Service(beanName = "s1",singleton = false,lazy = false)
public class ServiceTest extends Test01{
}
