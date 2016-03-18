package com.luxoft.entity.dao;

import com.luxoft.entity.model.ProductCategory;
import com.luxoft.exceptions.admin.NoFindCategoryException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Repository
public class ProductCategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<ProductCategory> getCategoryBySubTypeId(Long id) {

        System.out.println("ID === " + id);
        String query = "from com.luxoft.entity.model.ProductCategory where productSubType.id = " + id;
        List<ProductCategory> productCategories = sessionFactory.getCurrentSession().createQuery(query).list();
        if (productCategories != null)
            return productCategories;
        return new ArrayList<ProductCategory>();
    }

    @Transactional(readOnly = false)
    public ProductCategory getProductCategoryByName(String name) throws NoFindCategoryException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ProductCategory.class).add(Restrictions.like("name", name));
        List<ProductCategory> productCategories = criteria.list();
        if (productCategories != null && !productCategories.isEmpty())
            return productCategories.get(0);
        else throw new NoFindCategoryException();
    }

    @Transactional(readOnly = false)
    public List<ProductCategory> getAllProductCategories() throws NoFindCategoryException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ProductCategory.class);
        List<ProductCategory> productCategories = criteria.list();
        if (productCategories != null && !productCategories.isEmpty())
            return productCategories;
        else throw new NoFindCategoryException();
    }

    @Transactional(readOnly = false)
    public List<String> getCategoriesWithProduct() throws NoFindCategoryException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ProductCategory.class)
                .setProjection(Projections.distinct(Projections.property("name")))
                .createCriteria("products", JoinType.RIGHT_OUTER_JOIN);
        List<String> productCategories = criteria.list();
        System.out.println("productCategories size == " + productCategories.size());
        if ( !productCategories.isEmpty()) {
            return productCategories;
        } else throw new NoFindCategoryException();
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
