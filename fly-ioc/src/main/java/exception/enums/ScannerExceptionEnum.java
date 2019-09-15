package exception.enums;

/**
 * @author zeng
 * @Classname ScannerExceptionEnum
 * @Description TODO
 * @Date 2019/9/15 17:08
 */
public enum ScannerExceptionEnum {
    /**
     * 扫描失败
     */
    SCANNER_FAILED("扫描失败");
    private String msg;

    ScannerExceptionEnum(String msg){
        this.msg=msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
