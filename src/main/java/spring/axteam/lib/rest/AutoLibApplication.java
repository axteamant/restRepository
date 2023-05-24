package spring.axteam.lib.rest;

import spring.axteam.lib.rest.rest.engine.EnableRestRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/**

 Startup class.
 // FIXME
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableRestRepository(packages = {})
public class AutoLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoLibApplication.class, args);
	}


}
