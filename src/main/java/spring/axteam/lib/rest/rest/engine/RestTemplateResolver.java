package spring.axteam.lib.rest.rest.engine;


import spring.axteam.lib.rest.rest.exception.UnsupportedRequestBodyAndMultipart;
import spring.axteam.lib.rest.rest.RestConfigConfigurationException;
import spring.axteam.lib.rest.rest.exception.LocatorException;
import spring.axteam.lib.rest.rest.restcomponent.*;
import spring.axteam.lib.rest.rest.restrequests.RestRequest;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**

 Class that defines the logic for creating a REST call by retrieving
 the information from the method name definition.
 @param <Q> the REST request object
 @author Alexei Vezzola
 */
@Service
@Data
public class RestTemplateResolver <Q>  {
    private static final Logger logger = Logger.getLogger(RestTemplateResolver.class);
    private List<String> name;
    private Object [] args;
    private Q body;
    private RestRequest.Builder restRequestBuilder;

    /**

     Method that creates a {@link RestRequest} with the necessary information to
     create a suitable {@link org.springframework.web.client.RestTemplate} object.
     @param name the method name
     @param baseRestLocatorConfig the configuration of the target of the call
     @param args the method arguments
     @return An instance of {@link RestRequest} with all the necessary information to make a REST call
     @throws RestConfigConfigurationException If there is a configuration error
     @throws LocatorException If there is an error in the method definition
     @throws UnsupportedRequestBodyAndMultipart If a request is defined with both a body and a multi-part request
     */
    protected RestRequest resolver(String name, BaseRestLocatorConfig baseRestLocatorConfig , Object[]  args)
            throws RestConfigConfigurationException, LocatorException, UnsupportedRequestBodyAndMultipart {
        this.name= Arrays.asList(name.split("And"));
        this.args= args;
        this.restRequestBuilder = new RestRequest.Builder();
        restRequestBuilder.baseRestLocatorConfig(baseRestLocatorConfig);
        verifyMethod();
        setProxy();
        setBody();
        setHeaders();
        queryParams();
        pathVariables();
        return  this.restRequestBuilder.build();
    }

    /**
     * Verifica se nel nome del metodo Esiste un "PathVariables" e nel caso setta
     * il {@link RestRequest.Builder#pathVariableConfig(PathVariableConfig)}
     * @throws LocatorException
     */
    private void pathVariables() throws LocatorException {
        int position = getPosition("PathVariables");
        if (position != 0)
        {
            restRequestBuilder.pathVariableConfig((PathVariableConfig) args[position-1]);
        }
    }
    /**

     Checks if the method name contains "PathVariables" and, if so, sets the
     {@link RestRequest.Builder#pathVariableConfig(PathVariableConfig)}.
     @throws LocatorException If there is an error in the method definition
     */
    private void queryParams() throws LocatorException {
        int position = getPosition("QueryParams");
        if (position != 0)
        {
            restRequestBuilder.queryParams((QueryParamsConfig) args[position-1]);
        }
    }


    /**

     Checks if the first element of the method name, split by "And", contains an HttpMethod, and if so,
     sets it using {@link RestTemplateResolver#setMethod(String)}.
     @throws LocatorException If there is an error
     */
    private void verifyMethod() throws RestConfigConfigurationException {
        setMethod(this.name.get(0).replace("Request","get"));

    }
    /**

     Checks if the method name contains "Proxy" and, if so, sets the
     {@link RestRequest.Builder#proxy(ProxyConfig)}.
     @throws LocatorException If there is an error in the method definition
     */

    private void setProxy() throws RestConfigConfigurationException {

        int position = getPosition("Proxy");
        if (position != 0)
        {
            logger.info("setup request with proxy ");
            try{
                restRequestBuilder.proxy((ProxyConfig) args[position-1]);
            }catch (ClassCastException classCastException){
                throw new RestConfigConfigurationException(" to set a proxy, you have to respect syntax and provide an object of " +
                        " type "+ ProxyConfig.class.getName());
            }
        }
    }

    /**

     Checks if the method name contains "Headers" and, if so, sets the
     {@link RestRequest.Builder#headers(HttpHeaders)}.
     @throws LocatorException If there is an error in the method definition
     */

    private void setHeaders() throws RestConfigConfigurationException {
        int position  = getPosition("Headers");
        if(position!=0)
            try {
                restRequestBuilder.headers((HttpHeaders) args[position - 1]);
            }catch (ClassCastException classCastException){
                throw new RestConfigConfigurationException(" to set headers, you have to respect syntax and provide an object of " +
                        " type "+ HttpHeaders.class.getName());
            }
    }
    /**

     Checks if the method name contains "Body" or "MultiPart", and if a body exists,
     sets it using {@link RestRequest.Builder#body(Object)}. If a MultiPart exists, sets it using
     {@link RestRequest.Builder#multipart(MultiPartBodyConfig)}.
     @throws LocatorException If there is an error
     */
    private void  setBody() throws UnsupportedRequestBodyAndMultipart, RestConfigConfigurationException {
        int position  = getPosition("Body");
        int position_part  = getPosition("MultiPart");
        if(position!= 0 && position_part != 0){
            throw new UnsupportedRequestBodyAndMultipart("can't set a request with body and Multipart in the same request");
        }
        if(position!= 0)
        {
            restRequestBuilder.body(args[position-1]);
        }
        //MULTIPART REQUEST
        else if(position_part!= 0)
        {
            try {
                restRequestBuilder.multipart((MultiPartBodyConfig) args[position_part-1]);
            }catch (ClassCastException classCastException){
                throw new RestConfigConfigurationException(" to set multipart, you have to respect syntax and provide an object of " +
                        " type "+ MultiPartBodyConfig.class.getName());
            }
        }

    }

    /**

     Utility method that retrieves the portion of a string for a specific key by splitting the string
     using "And" as the delimiter.
     @param element The input string
     @return The portion of the string for the specified key
     */
    public int  getPosition(String element) {
        for (int i = 0; i< name.size(); i++) {
            if (name.get(i).equalsIgnoreCase(element)) {
                return i;
            }
        }
        return 0;
    }

    /**

     Sets the HTTP method.
     @param httpMethod The HTTP method to set
     @throws RestConfigConfigurationException If the method does not exist
     */
    private void setMethod(String httpMethod) throws RestConfigConfigurationException {
        restRequestBuilder.method(httpMethod);
    }




}
