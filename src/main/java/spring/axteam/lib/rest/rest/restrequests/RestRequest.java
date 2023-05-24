package spring.axteam.lib.rest.rest.restrequests;

import spring.axteam.lib.rest.rest.RestConfigConfigurationException;
import spring.axteam.lib.rest.rest.exception.LocatorException;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.axteam.lib.rest.rest.restcomponent.*;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.util.Map;

/**

 Class that handles the creation of components required to make a REST call.
 @param <T> Type of the REST response
 @author Alexei Vezzola
 */
public class RestRequest<T> {
    private  HttpMethod method;
    private BaseRestLocatorConfig baseRestLocatorConfig;
    private  HttpHeaders httpHeaders;
    private  Object body;
    private   URI uri;
    private  SimpleClientHttpRequestFactory factory;
    private RestTemplate restTemplate;
    private HttpEntity<Object> requestEntity ;

    /**
     * Class that handles setting all the properties of the private constructor.
     * @param method The request method
     * @param baseRestLocatorConfig Object defining the URL
     * @param httpHeaders Headers
     * @param body Request body object
     * @param factory Validated object in case of {@link ProxyConfig}
     * @param uri Actual URI populated with {@link PathVariableConfig} and {@link QueryParamsConfig}
     */
    private void setObject(HttpMethod method, BaseRestLocatorConfig baseRestLocatorConfig, HttpHeaders httpHeaders, T body,  SimpleClientHttpRequestFactory factory,
                           URI uri) {
        this.method = method;
        this.baseRestLocatorConfig = baseRestLocatorConfig;
        this.httpHeaders = httpHeaders;
        this.body = body;
        this.uri = uri;
        this.factory = factory;
        if (this.body != null)
            this.requestEntity = new HttpEntity(this.body, this.httpHeaders);
        else
            this.requestEntity = new HttpEntity(new org.json.JSONObject(), this.httpHeaders);
        if (factory != null) {
            this.restTemplate = new RestTemplate(factory);
        } else {
            this.restTemplate = new RestTemplate();
        }
    }

    /**
     * Private constructor
     * @param method The request method
     * @param baseRestLocatorConfig Object defining the URL
     * @param httpHeaders Headers
     * @param body Request body object
     * @param factory Validated object in case of {@link ProxyConfig}
     * @param uri Actual URI populated with {@link PathVariableConfig} and {@link QueryParamsConfig}
     */
    private RestRequest(HttpMethod method, BaseRestLocatorConfig baseRestLocatorConfig, HttpHeaders httpHeaders, T body,  SimpleClientHttpRequestFactory factory,
                        URI uri)                      {
        setObject(method,baseRestLocatorConfig,httpHeaders,body,factory,uri);

    }
    Builder builder= new Builder();

    /**
     * Full constructor, calls the {@link Builder} to create the actual object.
     * @param method HttpMethod
     * @param baseRestLocatorConfig LocatorConfig
     * @param httpHeaders Headers
     * @param body Request body multi-part
     * @param proxy ProxyConfig
     * @param paramsConfig Rest Request params
     * @param pathVariableConfig Rest path Variable Config
     * @throws LocatorException
     */

    public  RestRequest(           HttpMethod method,
                                   BaseRestLocatorConfig baseRestLocatorConfig,
                                   HttpHeaders httpHeaders,
                                   MultiPartBodyConfig body,
                                   ProxyConfig proxy,
                                   QueryParamsConfig paramsConfig,
                                   PathVariableConfig pathVariableConfig) throws LocatorException {
        builder.method(method)
                .baseRestLocatorConfig(baseRestLocatorConfig)
                .headers(httpHeaders)
                .multipart(body)
                .proxy(proxy)
                .queryParams(paramsConfig)
                .pathVariableConfig(pathVariableConfig);
        setObject(builder.method, builder.baseRestLocatorConfig, builder.httpHeaders, (T) builder.body, builder.factory, builder.uri);

    }
    /**
     * Full constructor, calls the {@link Builder} to create the actual object.
     * @param method HttpMethod
     * @param baseRestLocatorConfig LocatorConfig
     * @param httpHeaders Headers
     * @param body Request body Generics
     * @param proxy ProxyConfig
     * @param paramsConfig Rest Request params
     * @param pathVariableConfig Rest path Variable Config
     * @throws LocatorException
     */
    public  RestRequest(HttpMethod method,
                        BaseRestLocatorConfig baseRestLocatorConfig,
                        HttpHeaders httpHeaders,
                        T body,
                        ProxyConfig proxy,
                        QueryParamsConfig paramsConfig,
                        PathVariableConfig pathVariableConfig) throws LocatorException {
        builder.method(method)
                .baseRestLocatorConfig(baseRestLocatorConfig)
                .headers(httpHeaders)
                .body(body)
                .proxy(proxy)
                .queryParams(paramsConfig)
                .pathVariableConfig(pathVariableConfig);
        setObject(builder.method, builder.baseRestLocatorConfig, builder.httpHeaders, (T) builder.body, builder.factory, builder.uri);

    }

    /**

     Executes the REST request and returns the response body of the specified type.
     This method sends the HTTP request to the specified URI or the service endpoint
     defined in the base REST locator configuration. The method, request entity, and
     response body type are used to construct the exchange request.
     @param clazz The class type of the response body.
     @return The response body of the specified type.
     @throws LocatorException If an error occurs while executing the REST request.
     */




    public   T execute(Class<T> clazz) throws LocatorException {
        System.out.println( baseRestLocatorConfig.getServiceEndpoint());
        System.out.println("URL : " + ( this.uri!=null?this.uri.toString():
                baseRestLocatorConfig.getServiceEndpoint()));
        System.out.println("METHOD" + method.toString());
        System.out.println("BODY" + requestEntity);
        System.out.println("RETURN CLAZZ" + clazz);
        ResponseEntity<T> o = restTemplate.exchange(this.uri!=null?this.uri.toString():
                baseRestLocatorConfig.getServiceEndpoint(), method, requestEntity,clazz);
        System.out.println("RETURN STATUS" +o.getStatusCode());
        System.out.println("RETURN BODY" +o.getBody());
        return o. getBody();
    }

    /**

     Builder class for constructing instances of RestRequest.

     This builder provides methods for setting various properties of the RestRequest object

     and ultimately constructs the RestRequest instance.

     @param <Q> The type of the request body.
     */
    public static class Builder<Q> {
        private static final Logger logger = Logger.getLogger(RestRequest.class);
        private HttpMethod method;
        private BaseRestLocatorConfig baseRestLocatorConfig;
        private HttpHeaders httpHeaders;
        private Q body;
        private SimpleClientHttpRequestFactory factory;
        private QueryParamsConfig queryParamsConfig;
        private URI uri;

        /**

         Sets the HTTP method of the REST request based on the provided string.
         @param httpMethod The HTTP method as a string.
         @return This builder instance.
         @throws RestConfigConfigurationException If the provided HTTP method is invalid.
         */
        public Builder method(String httpMethod) throws RestConfigConfigurationException {
            HttpMethod method = HttpMethod.resolve(httpMethod.toUpperCase());
            if (method == null) {
                throw new RestConfigConfigurationException("Invalid HTTP method: " + httpMethod +
                        ". Methods need to start with an HTTP verb.");
            }
            this.method = method;
            return this;
        }
        /**

         Sets the HTTP method of the REST request.
         @param httpMethod The HTTP method.
         @return This builder instance.
         */
        public Builder method(HttpMethod httpMethod) {
            this.method = httpMethod;
            return this;
        }
        /**

         Sets the base REST locator configuration.
         @param baseRestLocatorConfig The base REST locator configuration.
         @return This builder instance.
         */
        public Builder baseRestLocatorConfig(BaseRestLocatorConfig baseRestLocatorConfig) {
            this.baseRestLocatorConfig = baseRestLocatorConfig;
            return this;
        }
        /**

         Sets the base REST locator configuration using the specified protocol, host, and path.
         @param protocol The protocol of the service endpoint.
         @param host The host of the service endpoint.
         @param path The path of the service endpoint.
         @return This builder instance.
         */
        public Builder baseRestLocatorConfig(String protocol, String host, String path) {
            this.baseRestLocatorConfig = BaseRestLocatorConfig.builder()
                    .protocol(protocol).host(host).path(path).build();
            return this;
        }
        /**

         Sets the HTTP headers of the REST request.
         @param httpHeaders The HTTP headers.
         @return This builder instance.
         */
        public Builder headers(HttpHeaders httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }
        /**

         Sets the HTTP headers of the REST request using the specified map.
         @param httpHeaders The map of HTTP headers.
         @return This builder instance.
         */
        public Builder headers(Map<String, String> httpHeaders) {
            HttpHeaders httpHeader = new HttpHeaders();
            for (String header : httpHeaders.keySet()) {
                httpHeader.set(header, httpHeaders.get(header).toString());
            }
            this.httpHeaders = httpHeader;
            return this;
        }
        /**

         Sets the request body of the REST request.
         @param arg The request body.
         @return This builder instance.
         */
        public Builder body(Q arg) {
            this.body = arg;
            return this;
        }
        /**

         Sets the query parameters configuration of the REST request.
         @param queryParamsConfig The query parameters configuration.
         @return This builder instance.
         @throws LocatorException If an error occurs while constructing the URI.
         */
        public
        Builder queryParams(QueryParamsConfig queryParamsConfig) throws LocatorException {
            if (baseRestLocatorConfig != null && queryParamsConfig != null) {
                this.queryParamsConfig = queryParamsConfig;
                UriComponentsBuilder uriComponentsBuilder = getUri();
                for (String param : queryParamsConfig.getParams().keySet()) {
                    uriComponentsBuilder = uriComponentsBuilder.queryParam(param, queryParamsConfig.getParams().get(param));
                }
                this.uri = uriComponentsBuilder.build().toUri();
                return this;
            }
            return this;
        }

        /**
         * Constructs the UriComponentsBuilder based on the base REST locator configuration and
         * the current URI.
         *
         * @return The constructed UriComponentsBuilder.
         * @throws LocatorException If an error occurs while constructing the URI.
         */
        private UriComponentsBuilder getUri() throws LocatorException {
            if (this.uri != null) {
                return UriComponentsBuilder.fromUri(uri);
            }
            return UriComponentsBuilder.fromUriString(baseRestLocatorConfig.getServiceEndpoint());
        }

        /**
         * Sets the path variable configuration of the REST request.
         *
         * @param pathVariableConfig The path variable configuration.
         * @return This builder instance.
         * @throws LocatorException If an error occurs while constructing the URI.
         */
        public Builder pathVariableConfig(PathVariableConfig pathVariableConfig) throws LocatorException {
            if (baseRestLocatorConfig != null && pathVariableConfig != null) {
                this.queryParamsConfig = queryParamsConfig;
                UriComponentsBuilder uriComponentsBuilder = getUri();
                uriComponentsBuilder = uriComponentsBuilder.pathSegment(pathVariableConfig.getVariables().toArray(new String[0]));
                this.uri = uriComponentsBuilder.build().toUri();
            }
            return this;
        }

        /**
         * Sets the multipart body configuration of the REST request.
         *
         * @param multiPartBodyConfig The multipart body configuration.
         * @return This builder instance.
         */
        public Builder multipart(MultiPartBodyConfig multiPartBodyConfig) {
            this.body = (Q) multiPartBodyConfig.getBody();
            return this;
        }

        /**
         * Checks if a string is empty or null.
         *
         * @param delegate The string to check.
         * @return True if the string is empty or null, false otherwise.
         */
        private boolean isEmptyNull(String delegate) {
            return delegate == null || delegate.isEmpty();
        }

        /**
         * Sets the proxy configuration of the REST request.
         *
         * @param proxyConfig The proxy configuration.
         * @return This builder instance.
         */
        public Builder proxy(ProxyConfig proxyConfig) {
            if (proxyConfig != null) {
                SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
                java.net.Proxy proxy = new java.net.Proxy(
                        java.net.Proxy.Type.valueOf(proxyConfig.getType()),
                        new InetSocketAddress(proxyConfig.getHost(), proxyConfig.getPort()));
                if (!isEmptyNull(proxyConfig.getProxyUsername()) && !isEmptyNull(proxyConfig.getProxyUsername())) {
                    logger.info("Setting up request with proxy credentials");
                    Authenticator.setDefault(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    proxyConfig.getProxyUsername(),
                                    proxyConfig.getProxyPassword().toCharArray());
                        }
                    });
                }
                requestFactory.setProxy(proxy);
                if (proxyConfig.getConnectTimeout() != 0) {
                    requestFactory.setConnectTimeout(proxyConfig.getConnectTimeout() * 1000);
                }
                if (proxyConfig.getConnectionRequestTimeout() != 0) {
                    requestFactory.setReadTimeout(proxyConfig.getConnectionRequestTimeout() * 1000);
                }
                factory = requestFactory;
            }
            return this;
        }
        /**
         * Builds the RestRequest object with the configured properties.
         *
         * @return The constructed RestRequest object.
         */
        public RestRequest build() {
            return new RestRequest(
                    method,
                    baseRestLocatorConfig,
                    httpHeaders,
                    body,
                    factory,
                    uri);
        }
    }

    // ALL costructors Body
    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, null, (T) null, null, null, null);
    }
    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, (T) null, null, null, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, null, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       ProxyConfig proxy) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, proxy, null, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, null, pathVariableConfig);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       ProxyConfig proxy,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, proxy, paramsConfig, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       ProxyConfig proxy,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, proxy, null, pathVariableConfig);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       QueryParamsConfig paramsConfig,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, pathVariableConfig);
    }


    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, (T) null, null, null, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, null, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       ProxyConfig proxy) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, proxy, null, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, null, pathVariableConfig);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       ProxyConfig proxy,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, proxy, paramsConfig, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       ProxyConfig proxy,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, proxy, null, pathVariableConfig);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       T body,
                       QueryParamsConfig paramsConfig,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, pathVariableConfig);
    }



    // ALL costructors MultiPart

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, null, null);
    }
    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       ProxyConfig proxy) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, proxy, null, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, null, pathVariableConfig);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       ProxyConfig proxy,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, proxy, paramsConfig, null);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       ProxyConfig proxy,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, proxy, null, pathVariableConfig);
    }

    public RestRequest(HttpMethod method,
                       BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       QueryParamsConfig paramsConfig,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(method, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, pathVariableConfig);
    }
    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, null, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       ProxyConfig proxy) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, proxy, null, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, null, pathVariableConfig);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       ProxyConfig proxy,
                       QueryParamsConfig paramsConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, proxy, paramsConfig, null);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       ProxyConfig proxy,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, proxy, null, pathVariableConfig);
    }

    public RestRequest(BaseRestLocatorConfig baseRestLocatorConfig,
                       HttpHeaders httpHeaders,
                       MultiPartBodyConfig body,
                       QueryParamsConfig paramsConfig,
                       PathVariableConfig pathVariableConfig) throws LocatorException {
        this(HttpMethod.GET, baseRestLocatorConfig, httpHeaders, body, null, paramsConfig, pathVariableConfig);
    }


}
