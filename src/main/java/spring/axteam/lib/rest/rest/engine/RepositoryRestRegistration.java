package spring.axteam.lib.rest.rest.engine;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**

 Class that defines a logic for registering beans that extend RestRepository.
 * @author  Alexei Vezzola
 */
public class RepositoryRestRegistration implements BeanDefinitionRegistryPostProcessor {
    public RepositoryRestRegistration(List<String> packages){
        this.packages = packages;
        this.useAnnotation = false;

    }
    private static final Logger logger = Logger.getLogger(RepositoryRestRegistration.class);
    private final List<String>  packages;
    private final  boolean useAnnotation;
    private String mainClass;
    public RepositoryRestRegistration(List<String> packages, boolean useAnnotation) {
        this.packages = packages;
        this.useAnnotation = useAnnotation;
    }
    void useAnnotationConfig(){

        // Imposta il tuo pacchetto radice
        Reflections reflections =null;

        if(!useAnnotation){
            logger.debug("use  RestRepositoryFactoryConfig object find in context, skip reflections configuration");
            return;
        }
        logger.debug("use Reflection configuration .....");

        try {
            logger.debug("try to find main class .....");
            String mainclass= getBasePackage();
            reflections = new Reflections(mainclass);
            logger.debug(" main class on " + mainclass + "start to reflect to find annotations EnableRestRepository");
        }catch (Exception e){
            logger.debug("no main class scan for com");

            reflections= new Reflections("spring.axteam.lib.rest");
            logger.debug("can't find main class, continue using default jf base rest package");
        }
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(EnableAutoRestRepository.class);
        int all =0;
        logger.debug("number of RestRepository classes  " + all );
        if(annotatedClasses.isEmpty()){
            logger.debug("no rest Repository , continue....");
        }
        for (Class<?> clazz : annotatedClasses) {
            EnableAutoRestRepository blobbyAnnotation = clazz.getAnnotation(EnableAutoRestRepository.class);
            packages.addAll(Arrays.asList(blobbyAnnotation.packages()));
        }

        if(packages.isEmpty()){
            packages.add("spring.axteam.lib.rest");
        }
    }

    /**

     Method that adds a proxy of the class implementing RestRepository to the Spring registry.
     @param registry the Spring registry
     @throws BeansException if an error occurs during bean registration
     */
    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        useAnnotationConfig();

        List<Class<?>> repos= getInterfacesExtendingRestRepositoryNew(packages);
        logger.debug("find " + repos.size() + " repos that implement"  + RestRepository.class.getClass());
        for(Class  repo : repos){
            InvocationHandler invocationHandler = new CustomInvocationHandler(repo);
            logger.debug("try to instance proxy for class  "  + repo.getName());
            RestRepository restTemplateExampleProxy = (RestRepository) Proxy.newProxyInstance(
                    repo.getClassLoader(),
                    new Class[]{repo},
                    invocationHandler
            );
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(repo, () -> restTemplateExampleProxy);
            System.out.println("###### " +repo.getName());
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

        private Class repo;
        public CustomInvocationHandler(Class repo) {
            super();
            this.repo=repo;
        }

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
            if(method.getName().equalsIgnoreCase("toString"))return repo.getName();
            return 1;
        }
    }

    public List<Class<?>> getInterfacesExtendingRestRepositoryNew(List<String> packages) {
        List<Class<?>> result = new ArrayList<>();
        try {


            for (String pack : packages) {
                Reflections reflections = new Reflections(new ConfigurationBuilder()
                        .forPackages(pack)
                        .setScanners(new SubTypesScanner(false)));

                Set<Class<? extends RestRepository>> subTypes = reflections.getSubTypesOf(RestRepository.class);
                for (Class<?> clazz : subTypes) {
                    if (clazz.isInterface()) {
                        result.add(clazz);
                    }
                }

            }
        }catch (Exception e){e.printStackTrace();}

        return result;
    }



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
                logger.info(" main class find :"+ mainClassName.substring(0, lastDotIndex));
                mainClassName=  mainClassName.substring(0, lastDotIndex +1);
                mainClass = mainClassName;
                logger.warn ("Repository main package is : " + mainClassName);
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





}
