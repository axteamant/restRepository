package spring.axteam.lib.rest.rest.exception;

/**

 Class that handles an exception in case of a request with both body and multipart.
 */
public class UnsupportedRequestBodyAndMultipart extends Exception{

    public UnsupportedRequestBodyAndMultipart(String s) {
        super(s);
    }
}
