package spring.axteam.lib.rest.rest.engine;

import com.google.gson.Gson;
import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**

 This class is targeted by the custom annotation {@link EnableRestRepository} and allows for the instantiation of auto-configuration beans.
 * @author  Alexei Vezzola
 */
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class AutoConfigRestRepository {
    private List<String> packages = new ArrayList<>();
    private String mainClass= null;

    /**

     Method that retrieves the package of the main class for which it is possible to use reflection
     to retrieve the {@link EnableAspectJAutoProxy} annotation.
     @return the name of the main class or null
     @throws ClassNotFoundException
     */
    public  String getBasePackage() throws ClassNotFoundException {
        String mainClassName = getMainClassName();
        if (mainClassName != null) {
            int lastDotIndex = mainClassName.lastIndexOf(".");
            if (lastDotIndex != -1) {
                System.out.println(mainClassName.substring(0, lastDotIndex));
                mainClassName=  mainClassName.substring(0, lastDotIndex +1);
                mainClass = mainClassName;
                return mainClassName;
            }
        }
        return null;
    }

    /**

     Method that retrieves the name of the main class from the JVM trace.
     @return the name of the class or null
     @throws ClassNotFoundException
     */
    private  String getMainClassName()  {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            if ("main".equals(element.getMethodName())) {
                return element.getClassName();
            }
        }
        return null;
    }


    /**

     Method that retrieves all classes annotated with ${@link EnableRestRepository}
     and sets a list of classes.
     @throws AutoConfigurationException
     */
    public AutoConfigRestRepository() throws AutoConfigurationException {
        // Imposta il tuo pacchetto radice
        Reflections reflections =null;
        try {
            reflections = new Reflections(getBasePackage());
        }catch (Exception e){
            throw new AutoConfigurationException("can't find main class");
        }
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(EnableRestRepository.class);
        System.out.println(annotatedClasses);
        for (Class<?> clazz : annotatedClasses) {
                EnableRestRepository blobbyAnnotation = clazz.getAnnotation(EnableRestRepository.class);
                packages.addAll(Arrays.asList(blobbyAnnotation.packages()));
        }
        System.out.println(packages);
        if(packages.isEmpty()){
            System.out.println(" no RestRepository packageLocation, Add Default of the main class package : " + mainClass);
            packages.add(mainClass);
        }


    }

    /**

     Bean for restTemplate.
     @return
     */
    @Bean
    @ConditionalOnMissingBean
    RestTemplate restTemplate ()
    {
        return new RestTemplate();
    }

    /**

     Bean for the Gson object.
     @return gson
     */
    @Bean
    @ConditionalOnMissingBean
    Gson gson(){
        return new Gson();
    }

    /**

     Bean that defines the registry of the RestRepository.
     @return
     */
    @Bean
    @ConditionalOnMissingBean
    RepositoryRestRegistration repositoryRestRegistration(){
        return new RepositoryRestRegistration(packages);
    }


    /**

     Bean that defines the logic for resolving the RestRepository interfaces.
     @return
     */
    @Bean
    @ConditionalOnMissingBean
    RestTemplateResolver restTemplateResolver( ){
        return new RestTemplateResolver();
    }

    /**

     Bean that returns the configuration aspect that wraps the behaviors of the RestRepository interface.
     @param restTemplateResolver
     @return
     */
    @Bean
    @ConditionalOnMissingBean
    RestRepositoryResolver restRepositoryResolver( RestTemplateResolver restTemplateResolver){
        return new RestRepositoryResolver(restTemplateResolver);
    }
}





