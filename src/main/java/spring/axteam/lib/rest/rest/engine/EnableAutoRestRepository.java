package spring.axteam.lib.rest.rest.engine;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation that start all RestRepository
 * @author  Alexei Vezzola
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import({AutoConfigRestRepository.class})
@EnableAspectJAutoProxy
public @interface EnableAutoRestRepository {
    String[] packages() default {};

}