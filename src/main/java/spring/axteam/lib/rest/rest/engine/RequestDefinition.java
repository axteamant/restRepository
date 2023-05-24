package spring.axteam.lib.rest.rest.engine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**

 Annotation that defines a REST call with protocol, host, and path.
 * @author  Alexei Vezzola
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestDefinition {

    String protocol() default "" ;
    String host() ;
    String path() default "";
}
