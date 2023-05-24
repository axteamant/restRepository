package spring.axteam.lib.rest.rest.repository;

import org.springframework.http.HttpHeaders;
import spring.axteam.lib.rest.rest.restcomponent.*;


/**

 Class that defines all the possible REST calls, which is the core of the library.
 * @author  Alexei Vezzola
 */
// All 45 Methods;
public interface RestRepository  {


   /**
    * Sends a request with a body.
    *
    * @param <T>                       The response type.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T> T request(InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**
    * Sends a request with a body.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T, Q> T requestAndBody(Q body, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with headers.
    *
    * @param <T>                       The response type.
    * @param HttpHeaders               The request headers.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T> T requestAndHeaders(HttpHeaders HttpHeaders, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with path variables.
    *
    * @param <T>                       The response type.
    * @param pathVariableConfig        The path variable configuration.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T> T requestAndPathVariables(PathVariableConfig pathVariableConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with query parameters.
    *
    * @param <T>                       The response type.
    * @param queryParamsConfig         The query parameter configuration.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T> T requestAndQueryParams(QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a proxy.
    *
    * @param <T>                       The response type.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T> T requestAndProxy(ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body and headers.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param HttpHeaders               The request headers.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeaders(Q body, HttpHeaders HttpHeaders, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body and path variables.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param pathVariableConfig        The path variable configuration.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndPathVariables(Q body, PathVariableConfig pathVariableConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**
    * Sends a
    request with a body and a proxy.
    *

    @param <T> The response type.
    @param <Q> The request body type.
    @param body The request body.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The configuration for the REST locator.
    @return The response.
    */
   <T, Q> T requestAndBodyAndProxy(Q body, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**

    Sends a request with headers and path variables.
    @param <T> The response type.
    @param HttpHeaders The request headers.
    @param pathVariableConfig The path variable configuration.
    @param interfaceRestLocatiorConfig The configuration for the REST locator.
    @return The response.
    */
   <T> T requestAndHeadersAndPathVariables(HttpHeaders HttpHeaders, PathVariableConfig pathVariableConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**

    Sends a request with headers and query parameters.
    @param <T> The response type.
    @param HttpHeaders The request headers.
    @param queryParamsConfig The query parameter configuration.
    @param interfaceRestLocatiorConfig The configuration for the REST locator.
    @return The response.
    */
   <T> T requestAndHeadersAndQueryParams(HttpHeaders HttpHeaders, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**

    Sends a request with headers and a proxy.
    @param <T> The response type.
    @param HttpHeaders The request headers.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The configuration for the REST locator.
    @return The response.
    */
   <T> T requestAndHeadersAndProxy(HttpHeaders HttpHeaders, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**
    * Sends a request with a body and query parameters.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param queryParamsConfig         The query parameter configuration.
    * @param interfaceRestLocatiorConfig The configuration for the REST locator.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndQueryParams(Q body, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with path variables and query parameters.
    *
    * @param <T>                       The response type.
    * @param pathVariableConfig        The path variables configuration.
    * @param queryParamsConfig         The query parameters configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndPathVariablesAndQueryParams(PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with path variables and a proxy.
    *
    * @param <T>                       The response type.
    * @param pathVariableConfig        The path variables configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndPathVariablesAndProxy(PathVariableConfig pathVariableConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with query parameters and a proxy.
    *
    * @param <T>                       The response type.
    * @param queryParamsConfig         The query parameters configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndQueryParamsAndProxy(QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, headers, path variables, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param httpHeaders               The HTTP headers.
    * @param pathVariableConfig        The path variables configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeadersAndPathVariables(Q body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, headers, query parameters, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param httpHeaders               The HTTP headers.
    * @param queryParamsConfig         The query parameters configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeadersAndQueryParams(Q body, HttpHeaders httpHeaders, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, headers, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param httpHeaders               The HTTP headers.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeadersAndProxy(Q body, HttpHeaders httpHeaders, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, path variables, query parameters, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param pathVariableConfig        The path variables configuration.
    * @param queryParamsConfig         The query parameters configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndPathVariablesAndQueryParams(Q body, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, path variables, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param pathVariableConfig        The path variables configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndPathVariablesAndProxy(Q body, PathVariableConfig pathVariableConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, query parameters, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param queryParamsConfig         The query parameters configuration.
    *
    @param proxyConfig The proxy configuration.

    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T, Q> T requestAndBodyAndQueryParamsAndProxy(Q body, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**

    Sends a request with headers, path variables, query parameters, and a REST locator configuration.
    @param <T> The response type.
    @param httpHeaders The HTTP headers.
    @param pathVariableConfig The path variables configuration.
    @param queryParamsConfig The query parameters configuration.
    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndHeadersAndPathVariablesAndQueryParams(HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**

    Sends a request with headers, path variables, a proxy, and a REST locator configuration.
    @param <T> The response type.
    @param httpHeaders The HTTP headers.
    @param pathVariableConfig The path variables configuration.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndHeadersAndPathVariablesAndProxy(HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**

    Sends a request with headers, query parameters, a proxy, and a REST locator configuration.
    @param <T> The response type.
    @param httpHeaders The HTTP headers.
    @param queryParamsConfig The query parameters configuration.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndHeadersAndQueryParamsAndProxy(HttpHeaders httpHeaders, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with path variables, query parameters, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param pathVariableConfig        The path variables configuration.
    * @param queryParamsConfig         The query parameters configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndPathVariablesAndQueryParamsAndProxy(PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, headers, path variables, query parameters, and a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param httpHeaders               The HTTP headers.
    * @param pathVariableConfig        The path variables configuration.
    * @param queryParamsConfig         The query parameters configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeadersAndPathVariablesAndQueryParams(Q body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, headers, path variables, a proxy, query parameters, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param httpHeaders               The HTTP headers.
    * @param pathVariableConfig        The path variables configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeadersAndPathVariablesAndProxy(Q body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, headers, query parameters, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param httpHeaders               The HTTP headers.
    * @param queryParamsConfig         The query parameters configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeadersAndQueryParamsAndProxy(Q body, HttpHeaders httpHeaders, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, path variables, query parameters, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param pathVariableConfig        The path variables configuration.
    * @param queryParamsConfig         The query parameters configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndPathVariablesAndQueryParamsAndProxy(Q body, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**

    Sends a request with headers, path variables, query parameters, a proxy, and a REST locator configuration.
    @param <T> The response type.
    @param httpHeaders The HTTP headers.
    @param pathVariableConfig The path variables configuration.
    @param queryParamsConfig The query parameters configuration.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndHeadersAndPathVariablesAndQueryParamsAndProxy(HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a body, headers, path variables, query parameters, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param <Q>                       The request body type.
    * @param body                      The request body.
    * @param httpHeaders               The HTTP headers.
    * @param pathVariableConfig        The path variables configuration.
    * @param queryParamsConfig         The query parameters configuration.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T, Q> T requestAndBodyAndHeadersAndPathVariablesAndQueryParamsAndProxy(Q body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a multi-part body, headers, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param body                      The multi-part body.
    * @param httpHeaders               The HTTP headers.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndMultiPartAndHeaders(MultiPartBodyConfig body, HttpHeaders httpHeaders, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a multi-part body, headers, path variables, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param body                      The multi-part body.
    * @param httpHeaders               The HTTP headers.
    * @param pathVariableConfig        The path variables configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndMultiPartAndHeadersAndPathVariables(MultiPartBodyConfig body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a multi-part body, headers, query parameters, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param body                      The multi-part body.
    * @param httpHeaders               The HTTP headers.
    * @param queryParamsConfig         The query parameters configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndMultiPartAndHeadersAndQueryParams(MultiPartBodyConfig body, HttpHeaders httpHeaders, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a multi-part body, headers, a proxy, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param body                      The multi-part body.
    * @param httpHeaders               The HTTP headers.
    * @param proxyConfig               The proxy configuration.
    * @param interfaceRestLocatiorConfig The REST locator configuration.
    * @return The response.
    */
   <T> T requestAndMultiPartAndHeadersAndProxy(MultiPartBodyConfig body, HttpHeaders httpHeaders, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**
    * Sends a request with a multi-part body, headers, path variables, query parameters, and a REST locator configuration.
    *
    * @param <T>                       The response type.
    * @param body                      The multi-part body.
    * @param httpHeaders               The HTTP headers.
    * @param pathVariableConfig        The path variables configuration.
    * @param queryParamsConfig         The query parameters configuration
   .

    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndMultiPartAndHeadersAndPathVariablesAndQueryParams(MultiPartBodyConfig body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);

   /**

    Sends a request with a multi-part body, headers, path variables, a proxy, and a REST locator configuration.
    @param <T> The response type.
    @param body The multi-part body.
    @param httpHeaders The HTTP headers.
    @param pathVariableConfig The path variables configuration.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndMultiPartAndHeadersAndPathVariablesAndProxy(MultiPartBodyConfig body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**

    Sends a request with a multi-part body, headers, query parameters, a proxy, and a REST locator configuration.
    @param <T> The response type.
    @param body The multi-part body.
    @param httpHeaders The HTTP headers.
    @param queryParamsConfig The query parameters configuration.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndMultiPartAndHeadersAndQueryParamsAndProxy(MultiPartBodyConfig body, HttpHeaders httpHeaders, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);
   /**

    Sends a request with a multi-part body, headers, path variables, query parameters, a proxy, and a REST locator configuration.
    @param <T> The response type.
    @param body The multi-part body.
    @param httpHeaders The HTTP headers.
    @param pathVariableConfig The path variables configuration.
    @param queryParamsConfig The query parameters configuration.
    @param proxyConfig The proxy configuration.
    @param interfaceRestLocatiorConfig The REST locator configuration.
    @return The response.
    */
   <T> T requestAndMultiPartAndHeadersAndPathVariablesAndQueryParamsAndProxy(MultiPartBodyConfig body, HttpHeaders httpHeaders, PathVariableConfig pathVariableConfig, QueryParamsConfig queryParamsConfig, ProxyConfig proxyConfig, InterfaceRestLocatiorConfig interfaceRestLocatiorConfig);






}
