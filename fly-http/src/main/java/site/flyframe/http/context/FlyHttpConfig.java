package site.flyframe.http.context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zeng
 * @Classname FlyHttpConfig
 * @Description http服务器的配置信息
 * @Date 2019/9/25 17:09
 */
public class FlyHttpConfig {
    /**
     * 路由配置信息，由路径/处理类组成
     */
    private HashMap<String,Class> routingConfig;

    /**
     * 基本的配置信息，可以用户自定义
     */
    private HashMap<String,String> baseConfig;

    private List<String> processorPackagePath=new LinkedList<String>();




    public String getBaseConfig(String configName){
        return baseConfig.get(configName);
    }

    public void addProcessorPackagePath(String path){
        processorPackagePath.add(path);
    }
    public List<String> getProcessorPackagePath(){
        return processorPackagePath;
    }
}
