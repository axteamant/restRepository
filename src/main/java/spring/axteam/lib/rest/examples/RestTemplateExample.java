package spring.axteam.lib.rest.examples;

import spring.axteam.lib.rest.rest.engine.RequestDefinition;
import spring.axteam.lib.rest.rest.repository.RestRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import java.util.Map;
@Component
public interface RestTemplateExample extends RestRepository
{

    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="saluta" )
    Object postAndBody(Map<String , String > values);
    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="ciao" )
    Object getAndRequest();
    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="headers" )
    Object getAndHeaders(HttpHeaders httpHeaders);
    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="saluta" )
    Object postAndBodyAndHeaders(Map<String , String > values, HttpHeaders httpHeaders);
    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000",path ="empty" )

    Object postAndBodyAndMyMethod(Map<String, String>  body);
}

