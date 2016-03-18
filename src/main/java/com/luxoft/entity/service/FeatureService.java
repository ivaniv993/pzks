package com.luxoft.entity.service;

import com.luxoft.entity.dao.FeatureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iivaniv on 30.07.2015.
 */
@Service
public class FeatureService {

    @Autowired
    private FeatureDao featureDao;

    public List<String> getFeaturesName(String query){
        System.out.println("SIZE = "+featureDao.getAutocompleteFeatureNames(query).size());
        return featureDao.getAutocompleteFeatureNames(query);
    }
}
