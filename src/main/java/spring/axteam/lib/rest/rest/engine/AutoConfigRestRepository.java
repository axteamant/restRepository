package spring.axteam.lib.rest.rest.engine;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class AutoConfigRestRepository {
    public List<String> getPackages() {
        return packages;
    }

    public static void setPackages(List<String> packages) {
        AutoConfigRestRepository.packages = packages;
    }

    public static boolean isUseAnnotation() {
        return useAnnotation;
    }

    public static void setUseAnnotation(boolean use) {
        useAnnotation = use;
    }

    private static List<String> packages = new ArrayList<>();
    private static boolean useAnnotation = true;
    private Logger logger = LogManager.getLogger(this.getClass().getName());
    private String mainClass= null;



    /**

     Method that retrieves all classes annotated with ${@link EnableAutoRestRepository}
     and sets a list of classes.
     @throws AutoConfigurationException
     */

    /**

     Bean for restTemplate.
     @return
     */
    @Bean
    @ConditionalOnMissingBean
    //@ConditionalOnMissingBean
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
    //@ConditionalOnMissingBean
    Gson gson(){
        return new Gson();
    }

    /**

     Bean that defines the registry of the RestRepository.
     @return
     */
    @Bean
    @ConditionalOnMissingBean
    //@ConditionalOnMissingBean
    RepositoryRestRegistration repositoryRestRegistration(){
        return new RepositoryRestRegistration(packages, useAnnotation);
    }


    /**

     Bean that defines the logic for resolving the RestRepository interfaces.
     @return
     */
    @Bean
    @ConditionalOnMissingBean
    // @ConditionalOnMissingBean
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





