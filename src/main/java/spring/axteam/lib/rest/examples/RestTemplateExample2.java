package spring.axteam.lib.rest.examples;

import spring.axteam.lib.rest.rest.engine.RequestDefinition;
import spring.axteam.lib.rest.rest.repository.RestRepository;
import spring.axteam.lib.rest.rest.restcomponent.MultiPartBodyConfig;
import spring.axteam.lib.rest.rest.restcomponent.PathVariableConfig;
import spring.axteam.lib.rest.rest.restcomponent.QueryParamsConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface RestTemplateExample2 extends RestRepository {
    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="data" )
    ResponseExample putAndBodyAndHeadersAndPathVariablesAndQueryParams(
        Map<String,String> body,
        HttpHeaders httpHeaders,
        PathVariableConfig pathVariableConfig,
        QueryParamsConfig queryParamsConfig
    );
    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="upload" )
    String postAndMultiPartAndHeaders(MultiPartBodyConfig body, HttpHeaders httpHeaders);
    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="upload1" )
    String postAndMultiPartAndHeadersAnd1(MultiPartBodyConfig body, HttpHeaders httpHeaders);
}




