package spring.axteam.lib.rest.rest.restcomponent;


import lombok.Data;
import lombok.NoArgsConstructor;


/**

 Class that defines proxy configuration.
 * @author  Alexei Vezzola
 */
@Data
@NoArgsConstructor

public class ProxyConfig {

    private  String host;
    private  int port;
    private  String proxyUsername;
    private  String proxyPassword;
    public String type;
    private int connectTimeout;
    private int socketTimeout;
    private int connectionRequestTimeout;
}
