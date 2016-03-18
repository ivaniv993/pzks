package com.luxoft.entity.dao;

import com.luxoft.entity.model.ProductSubType;
import com.luxoft.entity.model.ProductType;
import com.luxoft.exceptions.admin.NoFindSubTypeException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Repository
public class ProductSubTypeDao {

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private SessionFactory sessionFactory;

    public List<ProductSubType> getSubTypesByTypeId(Long id) {
        String query = "from com.luxoft.entity.model.ProductSubType where productType.id = :id";
        List<ProductSubType> productSubTypes = sessionFactory.getCurrentSession().createQuery(query).setParameter("id", id).list();
        if (productSubTypes != null) {
            return productSubTypes;
        }
        return new ArrayList<ProductSubType>();
    }

    @Transactional(readOnly = false)
    public ProductSubType getSubTypeByName(String name) throws NoFindSubTypeException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProductSubType.class).add(Restrictions.like("name", name));
        List<ProductSubType> productSubTypes = criteria.list();
        if (productSubTypes != null && !productSubTypes.isEmpty()) {
            return productSubTypes.get(0);
        }else throw new NoFindSubTypeException();
    }

}
