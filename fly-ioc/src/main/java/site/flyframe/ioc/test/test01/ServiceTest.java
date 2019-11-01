package site.flyframe.ioc.test.test01;

import site.flyframe.ioc.annotation.Service;

/**
 * @author zeng
 * @Classname ServiceTest
 * @Description TODO
 * @Date 2019/9/13 11:33
 */
@Service(beanName = "s1",singleton = false,lazy = false,initMethod = "initMethod",destroyMethod = "destroyMethod")
public class ServiceTest extends Test01{
    public void doService(){
        System.out.println("do service");
    }

    public void initMethod(){
        System.out.println("initMethod");
    }

    public void destroyMethod(){
        System.out.println("destroy method");
    }
}
