package spring.axteam.lib.rest.examples;
import spring.axteam.lib.rest.rest.RestConfigConfigurationException;
import spring.axteam.lib.rest.rest.exception.LocatorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import spring.axteam.lib.rest.rest.restcomponent.InterfaceRestLocatiorConfig;
import spring.axteam.lib.rest.rest.restcomponent.MultiPartBodyConfig;
import spring.axteam.lib.rest.rest.restcomponent.PathVariableConfig;
import spring.axteam.lib.rest.rest.restcomponent.QueryParamsConfig;

import java.util.*;

@Service
public class Example {

    public final RestTemplateExample example;
    private final RestTemplateExample2 restTemplateExample2;
    public Example(RestTemplateExample example, RestTemplateExample2 example2) throws LocatorException, RestConfigConfigurationException {
        this.example = example;
        this.restTemplateExample2  =example2;

        PathVariableConfig pathVariableConfig= new PathVariableConfig();
        pathVariableConfig.setVariables(new ArrayList<>());
        pathVariableConfig.getVariables().add("pippi");
        pathVariableConfig.getVariables().add("calzelunghe");
        QueryParamsConfig queryParamsConfi =new QueryParamsConfig();
        queryParamsConfi.setParams(new HashMap<>());
        queryParamsConfi.getParams().put("section", "Luffy");
        Map<String, String > body= new HashMap<>();
        body.put(
                "collezione", "estate inverno"
        );
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.set("Authorization", " sono vero lo giuro");
        System.out.println(restTemplateExample2.putAndBodyAndHeadersAndPathVariablesAndQueryParams(
                body,
                httpHeaders,
                pathVariableConfig,
                queryParamsConfi

        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "ciao");
        FileSystemResource exy = new FileSystemResource("C:\\Users\\RAD\\Desktop\\RestRepositoryLib\\pom.xml");
        MultiPartBodyConfig multiPartBodyConfig= new MultiPartBodyConfig();
        MultiValueMap<String, Object> multi = new LinkedMultiValueMap<>();
        multi.add("file", exy);
        multi.add("ciao", "hellooooo!!!!");
        multiPartBodyConfig.setBody(multi);
        System.out.println(restTemplateExample2.requestAndMultiPartAndHeaders(
                multiPartBodyConfig,
                headers,
                new InterfaceRestLocatiorConfig(
                        "http",
                            "127.0.0.1:5000",
                        "upload",
                        HttpMethod.POST,
                        String.class


                )).toString());
        System.out.println(restTemplateExample2.postAndMultiPartAndHeaders
                (    multiPartBodyConfig,
                        headers)
        );
        System.out.println(restTemplateExample2.postAndMultiPartAndHeadersAnd1(
                    multiPartBodyConfig,
                        headers)
        );

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Response{
        String message;
    }
}
