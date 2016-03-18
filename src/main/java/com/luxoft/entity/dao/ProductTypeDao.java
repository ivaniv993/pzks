package com.luxoft.entity.dao;

import com.luxoft.entity.model.Product;
import com.luxoft.entity.model.ProductType;
import com.luxoft.exceptions.admin.NoFindTypeException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Repository
public class ProductTypeDao {

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private SessionFactory sessionFactory;

    public List<ProductType> getAll(){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ProductType.class);
        List<ProductType> productTypes = criteria.list();
        if (productTypes != null){
            return productTypes;
        }
        return new ArrayList<ProductType>();
    }

    @Transactional(readOnly = false)
    public ProductType getProductByName( String name) throws NoFindTypeException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProductType.class).add(Restrictions.like("name", name));
        List<ProductType> productTypes = criteria.list();
        if(productTypes != null && !productTypes.isEmpty()){
            return productTypes.get(0);
        }else throw new NoFindTypeException();
    }



}
