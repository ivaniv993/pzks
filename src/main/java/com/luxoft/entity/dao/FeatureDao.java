package com.luxoft.entity.dao;

import com.luxoft.entity.model.Customer;
import com.luxoft.entity.model.Features;
import com.luxoft.exceptions.NoFeatureFindException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iivaniv on 24.07.2015.
 */
@Repository
public class FeatureDao extends AbstractDao {

    @Transactional
    public void save(Features features) {
        getCurrentSession().save(features);
    }

    @Transactional
    public void update(Features features) {
        getCurrentSession().update(features);
    }

    @Transactional
    public void delete(Features features) {
        getCurrentSession().delete(features);
    }

    @Transactional
    public Features getFeatureByName(String name) throws NoFeatureFindException {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Features.class).add(Restrictions.like("name", name));
        List<Features> features = criteria.list();
        if (!features.isEmpty())
            return features.get(0);
        else throw new NoFeatureFindException();
    }

    @Transactional
    public List<String> getAutocompleteFeatureNames(String query)  {
        Session session = getCurrentSession();

        Criteria criteria = session.createCriteria(Features.class)
                .setProjection(Projections.distinct(Projections.property("name")))
                .add(Restrictions.like("name", query+"%"));

        return criteria.list();
    }

    public List<String> getFeatureNames() {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Features.class)
                .setProjection(Projections.distinct(Projections.property("name")));
        return criteria.list();
    }
}
