package ru.erfolk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"ru.erfolk"}, excludeFilters = {
        @ComponentScan.Filter(value = {Controller.class, ControllerAdvice.class, RestController.class}, type = FilterType.ANNOTATION),
        @ComponentScan.Filter(pattern = {"ru\\.erfolk\\.web\\..*"}, type = FilterType.REGEX)
})
@Slf4j
public class RootConfiguration {
}
