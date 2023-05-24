package spring.axteam.lib.rest.rest.restcomponent;



import spring.axteam.lib.rest.rest.exception.LocatorException;
import lombok.Builder;


/**

 Class that handles the locator, defining the target URL.
 * @author  Alexei Vezzola
 */
@Builder
public class BaseRestLocatorConfig {
    private static final String ERROR = "Mandatory endpoint.property.hostname is not correctly configured. "
            + "Check in your related enpoint.properties file if it's null or blank.";

    private String protocol ;

    
    private String host;

    private String path;
    public String getProtocol() {
        if (protocol == null || protocol.equals("")) {
            protocol = "http";
        }
        return protocol;

    }
    public String getPath(){
        if (path != null && !path.equals("")) {
            return "/" + path;
        }
        return "";
    }
    public String getServiceEndpoint() throws LocatorException {
       return  getProtocol()+ "://" + getHost() + getPath();
    }
    private String getHost() throws LocatorException {
        if(host==null || host.isEmpty()){
            LocatorException le = new LocatorException ();
        /*    le.setDefaultExceptionMsg("Mandatory endpoint.property.hostname is not correctly configured. "
                    + "Check in your related enpoint.properties file if it's null or blank."
            );
        */
            throw le;
        }
        return host;
    }
}
