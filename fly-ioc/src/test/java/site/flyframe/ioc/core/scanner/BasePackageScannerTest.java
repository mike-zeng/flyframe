package site.flyframe.ioc.core.scanner;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zeng
 * @Classname BasePackageScannerTest
 * @Description TODO
 * @Date 2019/9/13 11:03
 */
public class BasePackageScannerTest {
    private Scanner scanner;

    @Before
    public void setUp() {
        scanner=new PackageScanner("site/flyframe/ioc/test");
    }

    @Test
    public void scanner() {
        List<Class<?>> scanner = this.scanner.scanner();
        assertTrue(scanner.size()>0);
    }
}