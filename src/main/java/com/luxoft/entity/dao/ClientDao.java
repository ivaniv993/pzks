package com.luxoft.entity.dao;

import com.luxoft.entity.model.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by iivaniv on 03.07.2015.
 */
@Repository
public class ClientDao {

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Customer customer) {
        getSessionFactory().getCurrentSession().save(customer);
    }

    public void update(Customer customer) {
        sessionFactory.getCurrentSession().update(customer);
    }

    public void delete(Customer customer) {
        sessionFactory.getCurrentSession().delete(customer);
    }

    @Transactional
    public Customer findByClientId(Long id) {
        String query = "from com.luxoft.entity.model.Customer c where c.id=" + id;
        List<Customer> list = sessionFactory.getCurrentSession().createQuery(query).list();
        if (list != null)
            return list.get(0);
        return new Customer();
    }

    @Transactional
    public List<Customer> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Customer.class);
        @SuppressWarnings("unchecked")
        List<Customer> customers = criteria.list();
        if (customers != null) {
            return customers;
        } else return new ArrayList<Customer>();
    }

    @Transactional
    public List<Customer> getCustomerByCriteria(Map<String, Object> criteries) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Customer.class);
        Restrictions.allEq(criteries);
        List<Customer> customers = uncheckedCast(criteria.list());
        if (customers != null) {
            return customers;
        } else return new ArrayList<Customer>();
    }

    private static <O, I extends O> O uncheckedCast(I entity) {
        return entity;
    }
}
