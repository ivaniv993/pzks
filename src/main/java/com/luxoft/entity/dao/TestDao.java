package com.luxoft.entity.dao;

import com.luxoft.entity.model.Customer;
import com.luxoft.entity.model.DemoCustomer;
import com.luxoft.utils.CustomerUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IIvaniv on 30.09.2015.
 */
@Repository
public class TestDao {

    Logger logger = Logger.getLogger("TestDao");

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private SessionFactory sessionFactory;

    public void compatreTwoTable(){

        List<String> differentProducts = new ArrayList<String>();
        DetachedCriteria criteriaExpected = DetachedCriteria
                .forClass(DemoCustomer.class, "c")
                .setProjection(Projections.property("c.id"))
                .addOrder(Order.asc("id"));
        Criteria criteriaComputed = sessionFactory.getCurrentSession()
                .createCriteria(Customer.class, "dc")
                .add(Subqueries.propertyNotIn("dc.id", criteriaExpected))
                .addOrder(Order.asc("id"));
        //get redundant product
        List<Customer> missingProducts = uncheckedCast(criteriaComputed.list());
        for(Customer customer : missingProducts){
            differentProducts.add(new String(" {Object with id [" + customer.getId() + "] is missing in computed table } "));
        }

        DetachedCriteria criteriaComputedMissingRows = DetachedCriteria
                .forClass(DemoCustomer.class, "dc")
                .setProjection(Projections.property("dc.id"))
                .addOrder(Order.asc("id"));
        Criteria criteriaRows = sessionFactory.getCurrentSession()
                .createCriteria(Customer.class, "c")
                .add(Subqueries.propertyIn("c.id", criteriaComputedMissingRows))
                .addOrder(Order.asc("id"));;

        Criteria allDemoProduct = sessionFactory.getCurrentSession()
                .createCriteria(DemoCustomer.class, "c").addOrder(Order.asc("id"));

        List<DemoCustomer> redundantProducts = new ArrayList<DemoCustomer>();
        List<Customer> allDemoCustomerAccordngExpectCustom = uncheckedCast(criteriaRows.list());
        List<DemoCustomer> colectiveCustomer = uncheckedCast(allDemoProduct.list());
        List<DemoCustomer> removedCustomer = new ArrayList<DemoCustomer>();
        for ( DemoCustomer colCustumer : colectiveCustomer ){
            for( Customer customer : allDemoCustomerAccordngExpectCustom ){
                if ( customer.getId().equals(colCustumer.getId())){
                    String messege = "";
//                    if (!(messege = CustomerUtils.compareCustomer(colCustumer, customer )).equals("")){
//                        differentProducts.add(messege);
//                    }
                    removedCustomer.add(colCustumer);
                }
            }
        }
        colectiveCustomer.removeAll(removedCustomer);
        redundantProducts.addAll(colectiveCustomer);
        for(DemoCustomer demoCustomer : redundantProducts){
            differentProducts.add(new String(" {Object with id [" + demoCustomer.getId() + "] is redundant in computed table } "));
        }
        logger.info(" 1. Missing objects in expected queue \n" +
                " 2.  Redundant objects in expected queue  \n"+
                " 3. Objects with where fields are different from expected queue : \n"+differentProducts + " \n ");

    }



    public static <O, I extends O> O uncheckedCast(I entity) {
        return entity;
    }

}


