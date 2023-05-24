package spring.axteam.lib.rest.rest.restcomponent;

import org.springframework.http.HttpMethod;

/**

 Class that defines the request when using default methods provided by {@link spring.axteam.lib.rest.rest.repository.RestRepository}.
 It is a pojo
 @param <T> Type Return
 * @author  Alexei Vezzola
 */
public class InterfaceRestLocatiorConfig<T> extends BaseRestLocatorConfig{

    HttpMethod httpMethod;
    private Class<T> typeReturn;
    public InterfaceRestLocatiorConfig(String protocol, String host, String path, HttpMethod httpMethod, Class<T> typeReturn) {
        super(protocol, host, path);
        this.httpMethod = httpMethod ;
        this.typeReturn= typeReturn;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Class<T> getTypeReturn() {
        return typeReturn;
    }
}
