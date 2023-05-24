package spring.axteam.lib.rest.rest.engine;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation that start all RestRepository
 * @author  Alexei Vezzola
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@EnableAutoConfiguration()
@Import({AutoConfigRestRepository.class})
public @interface EnableRestRepository{
    String[] packages() default {};
}
