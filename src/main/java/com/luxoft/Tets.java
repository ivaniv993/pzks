package com.luxoft;

import com.luxoft.config.PersistenceConfig;
import com.luxoft.entity.dao.CustomerJdbcTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by IIvaniv on 02.10.2015.
 */
public class Tets {

    public static void main(String...arg){
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        JdbcTemplate jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
        CustomerJdbcTemplate customerJdbcTemplate = (CustomerJdbcTemplate)context.getBean("customerJdbcTemplate");
        customerJdbcTemplate.verifyTwoTables();
    }
}
