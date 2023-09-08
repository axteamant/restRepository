package spring.axteam.lib.rest.rest.engine;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**

 This class is targeted by the custom annotation {@link EnableAutoRestRepository} and allows for the instantiation of auto-configuration beans.
 * @author  Alexei Vezzola
 */

//@EnableAutoConfiguration
public class ConfigRestRepository {


    private Logger logger = LogManager.getLogger(this.getClass().getName());
    public ConfigRestRepository(){
    }

    private String mainClass= null;



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





