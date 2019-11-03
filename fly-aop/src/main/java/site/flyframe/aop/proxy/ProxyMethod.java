package site.flyframe.aop.proxy;

public class ProxyMethod{

    private InvokeUnit before;
    private InvokeUnit after;
    private InvokeUnit afterReturn;
    private InvokeUnit afterThrow;
    private ProxyMethod inner;
    private InvokeUnit target;

    Object process() throws Throwable {
        Object ret=null;
        System.out.println("=============");
        try {
            // before
            invokeIfNotNull(before);
            if (inner!=null){
                ret=inner.process();
            }
            invokeIfNotNull(target);
            // afterReturn
            invokeIfNotNull(afterReturn);
        }catch (Throwable e){
            // afterThrow
            invokeIfNotNull(afterThrow);
        }finally {
            // after
            invokeIfNotNull(after);
        }
        return ret;
    }

    private void invokeIfNotNull(InvokeUnit invokeUnit)throws Throwable{
        if (invokeUnit!=null){
            invokeUnit.invoke();
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

    public void setAfterThrow(InvokeUnit afterThrow) {
        this.afterThrow = afterThrow;
    }

    void setInner(ProxyMethod inner) {
        this.inner = inner;
    }

    public void setTarget(InvokeUnit target) {
        this.target = target;
    }
}
