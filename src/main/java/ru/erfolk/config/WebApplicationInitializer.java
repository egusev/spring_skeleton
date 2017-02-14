package ru.erfolk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Slf4j
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final int JAVA_VERSION = getVersion();

    public WebApplicationInitializer() {
        super();

        if (JAVA_VERSION < 8) {
            log.error("Java 8 is required. Current version: {}. Please update your system.", JAVA_VERSION);
            throw new RuntimeException("Java 8 is required");
        }
    }

    /**
     * Retrieving current java version
     *
     * @return Double Java version
     */
    private static int getVersion() {
        String version = System.getProperty("java.version");
        log.info("Checking java version. Version found {}", version);
        String ver[] = version.split("\\.");
        if (ver.length < 2) {
            log.warn("Can't parse java version");
            return 0;
        }
        int iVer = 0;
        try {
            iVer = Integer.parseInt(ver[1]);
        } catch (NumberFormatException ignore) {
            log.warn("Can't parse java version {}", ver[1]);
        }
        return iVer;
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new OpenEntityManagerInViewFilter(),
                new HiddenHttpMethodFilter()
        };
    }

    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet servlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        servlet.setThrowExceptionIfNoHandlerFound(true);
        return servlet;
    }
}
