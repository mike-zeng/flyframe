package site.flyframe.aop.proxy;

public class ProxyMethod{

    private InvokeUnit before;
    private InvokeUnit after;
    private InvokeUnit afterReturn;
    private InvokeUnit afterThrow;
    private InvokeUnit target;

    private Object retValue;


    public Object process() throws Throwable {
        try {
            // before
            invokeIfNotNull(before);
            invokeIfNotNull(target);
            // afterReturn
            invokeIfNotNull(afterReturn);
        }catch (Throwable e){
            e.printStackTrace();
            // afterThrow
            invokeIfNotNull(afterThrow);
        }finally {
            // after
            invokeIfNotNull(after);
        }
        return retValue;
    }

    private void invokeIfNotNull(InvokeUnit invokeUnit)throws Throwable{
        if (invokeUnit!=null){
            invokeUnit.invoke();
            retValue=invokeUnit.getRetValue();
        }
    }

    void setBefore(InvokeUnit before) {
        this.before = before;
    }

    void setAfter(InvokeUnit after) {
        this.after = after;
    }

    void setAfterReturn(InvokeUnit afterReturn) {
        this.afterReturn = afterReturn;
    }

    void setAfterThrow(InvokeUnit afterThrow) {
        this.afterThrow = afterThrow;
    }

    void setTarget(InvokeUnit target) {
        this.target = target;
    }
}
