package spring.axteam.lib.rest.rest.engine;

import spring.axteam.lib.rest.rest.repository.RestRepository;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**

 Class that defines a logic for registering beans that extend RestRepository.
 * @author  Alexei Vezzola
 */
public class RepositoryRestRegistration implements BeanDefinitionRegistryPostProcessor {
    private static final Logger logger = Logger.getLogger(RepositoryRestRegistration.class);
    private final List<String>  packages;

    public RepositoryRestRegistration(List<String >packages) {
        this.packages = packages;
    }

    /**

     Method that adds a proxy of the class implementing RestRepository to the Spring registry.
     @param registry the Spring registry
     @throws BeansException if an error occurs during bean registration
     */
    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        InvocationHandler invocationHandler = new CustomInvocationHandler();
        List<Class<?>> repos= getInterfacesExtendingRestRepository(RestRepository.class);
        logger.debug("find " + repos.size() + " repos that implement"  + RestRepository.class.getClass());
        for(Class  repo : repos){
            logger.debug("try to instance proxy for class  "  + repo.getName());
            RestRepository restTemplateExampleProxy = (RestRepository) Proxy.newProxyInstance(
                    repo.getClassLoader(),
                    new Class[]{repo},
                    invocationHandler
            );
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(repo, () -> restTemplateExampleProxy);
            registry.registerBeanDefinition(repo.getName(), beanDefinitionBuilder.getBeanDefinition());
            logger.debug("instance correctly repository with name  "  + repo.getName());
        }
    }

    /**
     * Do nothing
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        return;
    }


    /**

     Class that handles the logic of invoking an object.
     */
    public class CustomInvocationHandler implements InvocationHandler {

        /**
         * Do nothing
         * @param proxy proxy Object
         * @param method method
         * @param args methods args
         * @return 1
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return 1;
        }
    }

    /**
     * This method find all Interface that Extend RestRepository
     * @param restRepositoryInterface All Clazz
     * @return
     */
    public  List<Class<?>> getInterfacesExtendingRestRepository(Class<?> restRepositoryInterface) {
        List<Class<?>> interfaces = new ArrayList<>();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory(applicationContext);

        try {
            for(String pack :  this.packages) {
                String packageSearchPath = "classpath*:"+pack.replace(".", "/")+"/**"; // Imposta il percorso del package da scansionare
                System.out.println(packageSearchPath);
                org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

                for (org.springframework.core.io.Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        if (metadataReader.getClassMetadata().isInterface()) {
                            if (metadataReader.getClassMetadata().getInterfaceNames() != null) {
                                for (String interfaceName : metadataReader.getClassMetadata().getInterfaceNames()) {
                                    if (restRepositoryInterface.getName().equals(interfaceName)) {
                                        logger.debug("find  an interface " + restRepositoryInterface.getName() + " that extend " + RestRepository.class.getClass());
                                        interfaces.add(Class.forName(metadataReader.getClassMetadata().getClassName()));

                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return interfaces;
    }



}
