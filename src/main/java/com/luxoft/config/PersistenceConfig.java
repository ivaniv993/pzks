package com.luxoft.config;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.jpa.internal.EntityManagerImpl;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.logging.Level;


/**
 * Created by iivaniv on 02.07.2015.
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:app.properties"})
@ComponentScan(basePackages = {"com.luxoft"})
public class PersistenceConfig {

    private static final Logger log = Logger.getLogger(PersistenceConfig.class);

    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";

    @Resource
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        log.info("Data Source " + dataSource);
        return dataSource;
    }

    @Bean(name = "jdbcTemplate")
    @Autowired
    public JdbcTemplate getJdbsTemplate( DataSource dataSource ){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

//    @Bean
//    public HibernateEntityManager getEntityManager(){
//        HibernateEntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl()
//        HibernateEntityManager entityManagerFactory = new EntityManagerImpl()
//    }

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.luxoft.entity.model");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }



    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager( SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(name="sessionFactory")
    public LocalSessionFactoryBean getSessionFactory() {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("com.luxoft.entity.model");
        sessionFactory.setHibernateProperties(getProperties());
        log.info("Session Factory " + sessionFactory);
        return sessionFactory;
    }


    private Properties getProperties(){

        Properties prop = new Properties();
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.max_fetch_depth", 3);
        prop.put("hibernate.default_schema", "public");
        prop.put("hibernate.generate_statistics", "true");
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "update");
        prop.put("hibernate.mapping.precedence", "class");
        return prop;
    }

}
