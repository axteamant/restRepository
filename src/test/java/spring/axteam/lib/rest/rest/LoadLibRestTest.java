package spring.axteam.lib.rest.rest;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import spring.axteam.lib.rest.examples.RestTemplateExample;
import spring.axteam.lib.rest.examples.RestTemplateExample2;
import spring.axteam.lib.rest.rest.engine.EnableAutoRestRepository;
import spring.axteam.lib.rest.rest.engine.EnableRestRepository;
import spring.axteam.lib.rest.rest.engine.RepositoryRestRegistration;
import spring.axteam.lib.rest.rest.restcomponent.InterfaceRestLocatiorConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.internal.Checks.notNull;

@EnableAspectJAutoProxy
public class LoadLibRestTest {


    @Test
    public void contextLoadReflect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(new Class[]{ContextTestSpring.class});
        assertReal(context);
    }

    public void assertReal(AnnotationConfigApplicationContext context) {
        RestTemplateExample2 example =null;
                context.refresh();
        for(String i : context.getBeansOfType(RestTemplateExample2.class).keySet()){
            example= context.getBeansOfType(RestTemplateExample2.class).get(i);
            break;
        }
        try {
            System.out.println("cose che capitano");
            System.out.println(example + "ciao caio");
            InterfaceRestLocatiorConfig<Object> k=
                    new InterfaceRestLocatiorConfig<>(
                            "hdada","adncjad","ckdK", HttpMethod.GET, Object.class

                    );


            notNull(example);
            try {
                System.out.println(example.request(k).toString());
            }catch (Exception malformedURLException){
                System.out.println(malformedURLException.getMessage());
                assert  malformedURLException.getMessage().contains("unknown protocol: hdada");
                return;

            }

            assert false;


        } catch (NullPointerException n) {

            assert false;
        }
    }

    @Test
    public void contextLoadFromConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(NoReflectContextImpl.class);
        assertReal(context);
    }
    @EnableAutoRestRepository(packages = {"spring.axteam.lib.rest.examples"})
    static public class ContextTestSpring {

    }
    @Configuration
    @EnableRestRepository
    static public class NoReflectContextImpl {
        @Bean
        RepositoryRestRegistration repositoryRestRegistration(){
            List<String> packs= new ArrayList<>();
            packs.add("spring.axteam.lib.rest.examples");
            return new RepositoryRestRegistration(packs);
        }
    }
}
