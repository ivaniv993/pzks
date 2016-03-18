package com.luxoft.entity.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by iivaniv on 03.08.2015.
 */
public abstract class AbstractDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

