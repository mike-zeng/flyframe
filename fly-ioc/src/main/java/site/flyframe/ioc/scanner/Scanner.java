package site.flyframe.ioc.scanner;

import java.util.List;

/**
 * @author zeng
 * @Classname Scanner
 * @Description 包扫描器
 * @Date 2019/9/10 22:30
 */
public interface Scanner {

    /**
     * 扫描
     * @return 返回扫描的类信息
     */
    List<Class<?>> scanner();
}
