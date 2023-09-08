
![Spring RestRepository](./logo.png)
![Spring RestRepository](https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1280px-Spring_Framework_Logo_2018.svg.png)

# Spring RestRepository
## Objective

The objective of this library is to facilitate REST calls by serving as a wrapper for [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) and enabling calls with a similar logic to JPA repositories. Additionally, calls can be made using the constructors of RestRequest and its Inner Constructor.

Table of Contents
-----------------
[TOC]

## Installation
This library is designed to support Java 8 to Java 16. To install it, you need to create the `.jar` artifact first.

### Requirements
- Java installed on your machine
- Maven (mvn) installed

To install the library, download the source code and run the following command at the root of the project:

```
mvn clean install
```
or you can use mvnw wrapper

linux: 
```
./mvnw  clean install
```
windows: 
```
./mvnw.cmd  clean install
```
This command will generate the jar file in the target folder and by default, it will put the artifact in the local `.m2` Maven repository.

### Installation

To install the jar for convenience, you can use Maven. Once the library has been added to the local Maven repository, you can import it as a dependency in your project's pom.xml file:

```xml
<dependency>
    <groupId>spring.axteam.lib.rest</groupId>
    <artifactId>rest-repository-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```


## Usage

There are two different possibile configurations

1 *recommended* ) Using `@EnableRestRepository` Annotation and define a bean in the application Context of type : `RepositoryRestRegistration` in a Spring `@Configuration`  class.



```java
    @Configuration
    @EnableRestRepository
    static public class ExampleEneableRestRepository {
        @Bean
        RepositoryRestRegistration repositoryRestRegistration(){
            List<String> packs= new ArrayList<>();
            packs.add("com.xxy.core");
            packs.add("com.xx1.core");
            return new RepositoryRestRegistration(packs);
        }
    }
```

`RepositoryRestRegistration`  is a Object that define all possible RestReposiotry packages.
if is empty , project it looks from package `com.**` . In the example code `RepositoryRestRegistration` looks for all RestRepositories Interface that are in packages :

- com.xxy.core.**
- com.xx1.core.**


---- 

2) Using `@EnableAutoRestRepository` Annotation.
   this annotation scan all packages to find `@EnableAutoRestRepository` properties packages

By default Reflections looking from main class and start scan from there all packages recursive .
If the project don't have a main class , it looks from package `com.**`

DISCLAMER  : this configuration can be very memory and time consuming when target project is very large, the first configuration is more performing , the second is better in a micro service architecture


```java
    @Configuration
    @EnableAutoRestRepository(packages = {})
    static public class ExampleAutoEneableRestRepository {
    }
```

packages is a annotation attribute that can be used to specify packages that contain RestRepositories Interfaces . If it is ampty, then the library will look all packages in `com.**`

---- 
### EnableRestRepository

This annotation starts the process behind RestRepository.
By default, it scans the main package to find, instantiate, and add all interfaces that extend `RestRepository` to the context. The annotation also allows for the explicit declaration of packages that contain your own `RestRepository` interfaces.

```java
@EnableRestRepository(packages = {})
```

### Using RestRepository

Once you have defined the target packages (default: main), you can extend any interface that extends
`RestRepository` to inherit the default methods.

```java
public interface RestRepository extends RestRepository {}
```

#### Required Object Definition:

| Variables         | Descpriton                     | Example
| -------------     | ------------------------------ | ------------------------------ |
| **MultiPartBodyConfig** | body of a multi-part request| application-multipart header required                           |
| **HttpHeaders** | httpHeaders  | map of headers            |
|**QueryParamsConfig**  | query params, order is not important  |`/hello?key=value`|
|**ProxyConfig** |  a simplify proxy pojo | host, port autentication ecc ecc | 
| **InterfaceRestLocatiorConfig**  |  define the request server url ecc. | url , protocol , path |
| **Body** | body of a simple rest request , it is a generic| `<T>` | 
|**PathVariableConfig**| list of path variable, order is very important| `sompath/<key1>/<key2>` |


All objects are POJOs (Plain Old Java Objects) and do not have specific logic, except for **BaseRestLocatorConfig** and **InterfaceRestLocatiorConfig**.

#####Examples
```java
 InterfaceRestLocatiorConfig interfaceRestLocatiorConfig= new InterfaceRestLocatiorConfig(
        "http","127.0.0.1:5000",
        "ciao",HttpMethod.GET,String.class


        );
                System.out.println(restTemplateExample2.request(interfaceRestLocatiorConfig).toString());
```
this is a simple http request, and it is this case:
`[GET] http://127.0.0.1:5000/ciao`

the name are pritty self explaining and are all the possibile configurations

#### Create custom method

You can create your own methods while strictly following the syntax:

```java
Object postAndBodyAndHeaders(Map<String, String> values, HttpHeaders httpHeaders);
```

The method name should start with the HTTP verb, in this case, POST. You can then combine different options using the "And" keyword and add an additional "And" at the end to avoid conflicts and define the function name. The possible options include:

| Variables         | Descpriton                     | Example | 
| -------------     | ------------------------------ |----------|
| PathVariables     | url path variables               | `/somerest/<parma1>/<para2>` |
| QueryParams       | url query params                 | `/somerest?key=value` | 
| Proxy             | proxy config                     ||
| Headers           | Headers                          |`{"Authorization" :"sometoken"}` |
| Body              | request body                     | `{"some":"body"}` , class | 
| MultiPart         | MultipartRequest                 | `{"file" :"/pathToFile" , "key, "value"}` |


Note that you cannot combine Body and MultiPart, as one would override the other.

It is crucial to maintain the order of types relative to the keys:

| Variables         | Descpriton
| -------------     | ------------------------------ |
|**PathVariables**  | Requires an object that extends **PathVariableConfig** in the method signature.|
| **QueryParams**   | Requires an object that extends **QueryParamsConfig** in the method signature. |
| **Proxy**         | Requires an object that extends **ProxyConfig** in the method signature.       |
| **Headers**       | Requires an object that extends **HttpHeaders** in the method signature.       |
| **Body**          | Requires an object that extends **T generic** in the method signature.         |
| **MultiPart**     | Requires an object that extends **MultiPartBodyConfig** in the method signature.    |

After defining the options, it is vital to specify the REST call, including the host, path, and protocol, by using a specific annotation:

```java
@RequestDefinition(protocol = "http", host = "127.0.0.1:5000", path = "saluta")
Object postAndBodyAndHeaders(Map<String, String> values, HttpHeaders httpHeaders);
```

This represents a simple HTTP request with a POST method, body, and headers:

```
[POST] http://127.0.0.1:5000/ciao

{
  "key": "value"
}

Headers: {"key": "value"}
```
#### Create multi-part request

In order to make a multi-part request, you need to define the multi-part headers and provide an object of type `MultiPartBodyConfig`.

You can either use the default methods of the `RestRepository` interface or implement your own logic using a custom method name.

An example of a multi-part request using default methods:

```java
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.MULTIPART_FORM_DATA);
headers.set("Authorization", "ciao");
FileSystemResource exy = new FileSystemResource("pom.xml");
MultiPartBodyConfig multiPartBodyConfig = new MultiPartBodyConfig();
MultiValueMap<String, Object> multi = new LinkedMultiValueMap<>();
multi.add("file", exy);
multi.add("world", "hellooooo!!!!");
multiPartBodyConfig.setBody(multi);
System.out.println(restTemplateExample2.requestAndMultiPartAndHeaders(
    multiPartBodyConfig,
    headers,
    new InterfaceRestLocatorConfig(
        "http",
        "127.0.0.1:5000",
        "upload",
        HttpMethod.POST,
        String.class)));
```

Result:

```
[POST] http://127.0.0.1:5000/upload
Headers: 
{
  "content-type": "multi-part",
  "Authorization": "ciao"
}
Parts: 
file: file object
world: string
```

An example of a multi-part request using a custom method name:

```java
public interface RestTemplateExample2 extends RestRepository {

    @RequestDefinition(protocol = "http", host = "127.0.0.1:5000", path = "upload")
    String postAndMultiPartAndHeaders(MultiPartBodyConfig body, HttpHeaders httpHeaders);
    
}

```

Usage:

```java
restTemplateExample2.postAndMultiPartAndHeaders(multiPartBodyConfig, headers);
```

Result:

```
[POST] http://127.0.0.1:5000/upload
Headers: 
{
  "content-type": "multi-part",
  "Authorization": "ciao"
}
Parts: 
file: file object
world: string
```




### Using RestRequest

The RestRequest class serves as a wrapper for [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) and enables calls with a similar logic to JPA repositories. You can make calls using the constructors of RestRequest and its Inner Constructor. You can use all the methods of RestRepository without enabling it.

When no default method is defined, the default HTTP method is GET:

```java
BaseRestLocatorConfig baseRestLocatorConfig = BaseRestLocatorConfig.builder()
        .protocol("http")
        .host("127.0.0.1:5000")
        .path("test")
        .build();
        RestRequest<String> restRequest = new RestRequest<String>(
        baseRestLocatorConfig,
        new HttpHeaders()
        );
        String result = restRequest.execute(String.class);
```

This represents a simple HTTP GET request:

```
[GET] http://127.0.0.1:test/ciao

Headers: {"key": "value"}
```

And it returns a String object.

### Using RestRequestBuilder

You can also use the internal builder of RestRequest without enabling RestRepository:

```java
RestRequest.Builder builder = new RestRequest.Builder();
        RestRequest<String> request = builder
        .baseRestLocatorConfig(baseRestLocatorConfig)
        .headers(new HttpHeaders())
        .method("GET")
        .build();

        String result = request.execute(String.class);
```

This represents a simple HTTP GET request:

```
[GET] http://127.0.0.1:test/ciao

Headers: {"key": "value"}
```

And it returns a String object.

### End
