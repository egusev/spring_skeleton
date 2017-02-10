package ru.erfolk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = {"com.gotomedic.web", "com.gotomedic.bootstrap"})
@Slf4j
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final String MESSAGESOURCE_BASENAME = "message.source.basename";

    private static final String MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE = "message.source.use-code-as-default-message";

    @Value("${message.source.basename}")
    private String MESSAGES_SOURCE;

    @Value("${message.source.use-code-as-default-message}")
    private String MESSAGES_SOURCE_USE_CODE_AS_DEFAULT_MESSAGE;

    @Resource
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(environment.getRequiredProperty(MESSAGESOURCE_BASENAME));
        messageSource.setUseCodeAsDefaultMessage(
                Boolean.parseBoolean(
                        environment.getRequiredProperty(MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE)
                )
        );

        return messageSource;
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
     registry.addViewController("/hotels/partialsList").setViewName("hotels/partialsList::content");
     registry.addViewController("/hotels/partialsCreate").setViewName("hotels/partialsCreate::content");
     registry.addViewController("/hotels/partialsEdit").setViewName("hotels/partialsEdit::content");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(31556926);
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
