package ru.erfolk.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories("ru.erfolk.repositories")
@PropertySource("classpath:application.properties")
@Slf4j
public class DataConfiguration {
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    @Value("${db.driver}")
    private String DB_DRIVER;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.format_sql}")
    private String HIBERNATE_FORMAT_SQL;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${hibernate.generate_statistics}")
    private boolean HIBERNATE_GENERATE_STATISTICS;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${db.populate.schema}")
    private String DB_POPULATE_SCHEMA;

    @Value("${db.populate.data}")
    private String DB_POPULATE_DATE;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("ru.erfolk");

        Properties jpaProperties = new Properties();
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, HIBERNATE_DIALECT);
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, HIBERNATE_FORMAT_SQL);
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, HIBERNATE_HBM2DDL_AUTO);
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, HIBERNATE_SHOW_SQL);

        jpaProperties.put("hibernate.id.new_generator_mappings", false);
        jpaProperties.put("hibernate.generate_statistics", HIBERNATE_GENERATE_STATISTICS );

        jpaProperties.put("hibernate.jdbc.fetch_size", 400);
        jpaProperties.put("hibernate.jdbc.batch_size", 100);
        jpaProperties.put("hibernate.order_inserts", true);
        jpaProperties.put("hibernate.order_updates", true);

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    @DependsOn("entityManagerFactory")
    public ResourceDatabasePopulator initDatabase(DataSource dataSource) throws Exception {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);

        if (!DB_POPULATE_SCHEMA.isEmpty()) {
            log.info("db.populate.schema is set. run {}", DB_POPULATE_SCHEMA);
            populator.addScript(new ClassPathResource(DB_POPULATE_SCHEMA));
        }
        if (!DB_POPULATE_DATE.isEmpty()) {
            log.info("db.populate.data is set. run {}", DB_POPULATE_DATE);
            populator.addScript(new ClassPathResource(DB_POPULATE_DATE));
        }
        populator.populate(dataSource.getConnection());
        return populator;
    }

}
