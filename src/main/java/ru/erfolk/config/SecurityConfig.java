package ru.erfolk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import ru.erfolk.security.UserOrgAuthenticationFilter;
import ru.erfolk.services.UserService;
import ru.erfolk.web.controllers.Endpoints;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Configuration
@EnableWebSecurity
@Slf4j
//@ImportResource("classpath:security-context.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected static final String USE_SECURITY = "spring-security.use";

    @Resource
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        log.info("Use security {}", env.getProperty(USE_SECURITY, Boolean.class, false));
        if (env.getProperty(USE_SECURITY, Boolean.class, false)) {
            web.ignoring().antMatchers("/js/**", "/images/**", "/css/**", "/fonts/**");
        } else {
            web.ignoring().antMatchers("/**");
        }
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (env.getProperty(USE_SECURITY, Boolean.class, false)) {
            http.csrf().disable()
                    // Configure the login entry point
                    .addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    // Configure the logout
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .and()

//					.exceptionHandling().authenticationEntryPoint(new RESTAuthenticationEntryPoint()).and()

                    // Configure the logout
//					.logout().permitAll().logoutSuccessHandler(new RESTLogoutSuccessHandler()).and()

                    // Configures url based authorization
                    .authorizeRequests()
                    // Anyone can access to the home page
                    .antMatchers("/").permitAll()
                    // Others page authorised by service
                    .antMatchers("**").access("@authorizationService.check(authentication,request)");
        }
    }

    /**
     * Set authentication manager to process authentication requests.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter getAuthenticationFilter() throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/");
        successHandler.setAlwaysUseDefaultTargetUrl(true);

        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler("/?error=bad_credentials");

        UserOrgAuthenticationFilter filter = new UserOrgAuthenticationFilter();
        filter.setFilterProcessesUrl(Endpoints.LOGIN);
        filter.setAuthenticationManager(authenticationManager());
        filter.setUsernameParameter("username");
        filter.setOrgParameter("org");
        filter.setPasswordParameter("password");
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }
}
