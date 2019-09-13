package factory;

import org.junit.Before;
import org.junit.Test;
import test.test01.ComponentTest;
import test.test01.ServiceTest;
import test.test01.Test01;
import test.test01.TestClass;
import test.test02.ValueTest;
import test.test03.SetTest01;
import test.test04.ConstructTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zeng
 * @Classname ApplicationContextTest
 * @Description TODO
 * @Date 2019/9/13 11:11
 */
public class ApplicationContextTest {
    private Factory factory;

    @Before
    public void setUp() throws Exception {
        List<String> list=new ArrayList<>();
        list.add("test");
        factory=new ApplicationContext(list);
    }

    @Test
    public void getBean() {
        // 1. 测试注解扫描是否成功
        Test01 bean = factory.getBean(ComponentTest.class);
        assert bean!=null;
        bean = factory.getBean(ServiceTest.class);
        assert bean!=null;
        bean= (Test01) factory.getBean("s1");
        assert bean!=null;
        bean= (Test01) factory.getBean("c1");
        assert bean!=null;
        System.out.println("1. 注解扫描正确");

        // 2. 测试value注入
        ValueTest valueTest=factory.getBean(ValueTest.class);
        assert valueTest.getValue()==10;
        assert valueTest.getStr()!=null&&valueTest.getStr().equals("hello");
        System.out.println("2. value注入成功");

        // 3. 测试set注入
        SetTest01 setTest01=factory.getBean(SetTest01.class);
        assert setTest01.getValueTest()!=null;
        assert setTest01.getValueTest().getStr()!=null;
        assert setTest01.getValueTest().getStr().equals("hello");
        assert setTest01.getValueTest().getValue()==10;
        System.out.println("3. set注入成功");

        // 4. 测试构造方法注入
        ConstructTest constructTest=factory.getBean(ConstructTest.class);
        assert constructTest.getValueTest()!=null;
        assert constructTest.getValueTest().getValue()==10;
        assert constructTest.getValueTest().getStr()!=null;
        assert constructTest.getValueTest().getStr().equals("hello");
        System.out.println("4. 构造注入成功");
    }

    @Test
    public void testGetBean() {
    }

    @Test
    public void destroy() {
        factory.destroy();
        assert factory.getBean(TestClass.class)==null;
        assert factory.isDestroy();
    }

    @Test
    public void isDestroy() {
        factory.destroy();
        assert factory.isDestroy();
    }

    @Test
    public void doGetBean() {

    }

    @Test
    public void testDoGetBean() {
    }
}