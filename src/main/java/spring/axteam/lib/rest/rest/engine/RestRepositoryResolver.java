package spring.axteam.lib.rest.rest.engine;

import spring.axteam.lib.rest.rest.repository.RestRepository;
import spring.axteam.lib.rest.rest.restcomponent.InterfaceRestLocatiorConfig;
import spring.axteam.lib.rest.rest.restcomponent.BaseRestLocatorConfig;
import spring.axteam.lib.rest.rest.restrequests.RestRequest;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Locale;

/**

 Class that resolves the method names by defining the concrete logic.
 * @author  Alexei Vezzola
 */
@Data
@Aspect
public class RestRepositoryResolver {


    private final RestTemplateResolver restTemplateResolver;

    /**

     Around advice for all methods of {@link RestRepository}.
     @param point the join point
     @return the real rest object
     @throws Throwable
     */
    @Around("execution(*  spring.axteam.lib.rest.rest.repository.RestRepository+.*(..))")
    public Object logRequestRest(ProceedingJoinPoint point) throws Throwable {
        BaseRestLocatorConfig config ;
        String name = point.getSignature().getName();
        Class returnType= ((MethodSignature) point.getSignature()).getReturnType();
        if (point.getArgs()[point.getArgs().length - 1] instanceof InterfaceRestLocatiorConfig) {
            config = (InterfaceRestLocatiorConfig) point.getArgs()[point.getArgs().length - 1];
            name = name.replace("request",
                    ((InterfaceRestLocatiorConfig) config).getHttpMethod().name().toLowerCase(Locale.ROOT));
            returnType= ((InterfaceRestLocatiorConfig) config).getTypeReturn();
        } else {
            config = config(point);
        }
        RestRequest restRequest = restTemplateResolver.resolver(name, config, point.getArgs());
        System.out.println(((MethodSignature) point.getSignature()).getReturnType() +"  #############");
        return restRequest.execute(returnType);
    }


    /**

     Method that retrieves the properties of the {@link RequestDefinition} annotation
     in case of creating a custom method in the interface that extends {@link spring.axteam.lib.rest.rest.repository.RestRepository}.
     @param joinPoint the join point
     @return the request properties
     */
    private BaseRestLocatorConfig config (ProceedingJoinPoint joinPoint)  {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> declaringClass = methodSignature.getDeclaringType();
        String methodName = methodSignature.getName();
        for(Method method : declaringClass.getMethods()){
            if(method.getName().equalsIgnoreCase(methodName)){
                RequestDefinition requestDefinition = method.getAnnotation(RequestDefinition.class);
                return BaseRestLocatorConfig.builder().path(requestDefinition.path()).host(requestDefinition.host())
                        .protocol(requestDefinition.protocol()).build();
            }
        }
       return null;
    }


}
