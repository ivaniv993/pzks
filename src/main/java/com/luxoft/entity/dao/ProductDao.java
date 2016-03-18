package com.luxoft.entity.dao;

import com.luxoft.entity.model.*;
import com.luxoft.entity.service.CustomerService;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Repository
public class ProductDao {

    Logger logger = Logger.getLogger("ProductDao");

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TestDao testDao;

    @Autowired
    private CustomerService customerService;

    @Transactional
    public void save(Product product) {
        sessionFactory.getCurrentSession().save(product);
    }

    @Transactional
    public void delete(Product product) {
        sessionFactory.getCurrentSession().delete(product);
    }

    public void update(Product product) {

        customerService.getProductFewCriteriaBuilder();
//        customerService.verifyTwoTables();

//        List<Customer> customers = Arrays.asList(new Customer("vvvv","vvv","vvv"),new Customer("wwww","www","www"));
//        customerService.batchUpdate(customers);
//        logger.info(customers.toString());
//        testDao.compatreTwoTable();
//        sessionFactory.getCurrentSession().update(product);
    }

    @Transactional
    public List<Product> getProductsByParameters(Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
        List<Product> products = criteria.list();
        if (products == null)
            return new ArrayList<Product>();
        return products;
    }

    @Transactional
    public Product getProductById(Long id) {

        StringBuilder query = new StringBuilder("from Product p where p.id=").append(id);
        List<Product> products = sessionFactory.getCurrentSession().createQuery(String.valueOf(query)).list();
        if (products != null) {
            return products.get(0);
        }
        Criteria crit = ((Session) sessionFactory).createCriteria(Product.class);
        return new Product();
    }

    @Transactional
    public List<Product> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class);
        List<Product> products = criteria.list();
        if (products != null) {
            return products;
        }
        return new ArrayList<Product>();
    }

    @Transactional
    public List<String> getCompleteProductNames() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class).setProjection(Projections.distinct(Projections.property("name")));
        List<String> products = criteria.list();
        if (products != null) {
            return products;
        }
        return new ArrayList<String>();
    }

    @Transactional
    public List<String> getAllStockAddress() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class).setProjection(Projections.distinct(Projections.property("stockLocation")));
        List<String> list = criteria.list();
        if (list != null) {
            return list;
        } else return new ArrayList<String>();
    }

    @Transactional
    public List<ProductStatus> getProdStatuses() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class).setProjection(Projections.distinct(Projections.property("productStatus")));
        List<ProductStatus> list = criteria.list();
        if (list != null) {
            return list;
        } else return new ArrayList<ProductStatus>();
    }

}

