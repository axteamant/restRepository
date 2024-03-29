package spring.axteam.lib.rest.rest.engine;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation that start all RestRepository
 * @author  Alexei Vezzola
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@Import({ConfigRestRepository.class})
public @interface EnableRestRepository{
    String[] packages() default {};
}
