package site.flyframe.ioc.meta;

/**
 * @author zeng
 * @Classname Depend
 * @Description TODO
 * @Date 2019/9/11 22:23
 */
public class Depend {
    private DependEnum type;
    private String dependKey;
    private Object dependValue;

    public Depend(){}
    public Depend(DependEnum type, String dependKey, Object dependValue) {
        this.type = type;
        this.dependKey = dependKey;
        this.dependValue = dependValue;
    }

    public String getDependKey() {
        return dependKey;
    }

    public void setDependKey(String dependKey) {
        this.dependKey = dependKey;
    }

    public DependEnum getType() {
        return type;
    }

    public void setType(DependEnum type) {
        this.type = type;
    }

    public Object getDependValue() {
        return dependValue;
    }

    public void setDependValue(Object dependValue) {
        this.dependValue = dependValue;
    }
}
