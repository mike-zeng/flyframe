package site.flyframe.http.response;

/**
 * @author zeng
 * @Classname ErrorResponseContent
 * @Description TODO
 * @Date 2019/9/28 20:03
 */
public class ErrorResponseContent implements ResponseContent {
    private ErrorPage errorPage;

    public ErrorResponseContent(ErrorPage errorPage){
        this.errorPage=errorPage;
    }

    @Override
    public byte[] getBytes() {
        return ("<h1>"+errorPage.toString()+"</h1>").getBytes();
    }
    public enum ErrorPage{
        /**
         * 404
         */
        PAGE_NOT_FOUND("404 Page Not Found !"),
        HTTP_INTERNAL_SERVER_ERROR("500 HTTP-Internal Server Error !")
        ;
        String msg;
        ErrorPage(String msg){
            this.msg=msg;
        }

        @Override
        public String toString() {
            return msg;
        }
    }
}
