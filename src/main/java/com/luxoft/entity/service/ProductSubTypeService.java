package com.luxoft.entity.service;

import com.luxoft.entity.dao.ProductSubTypeDao;
import com.luxoft.entity.model.ProductSubType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Service
public class ProductSubTypeService {


    @Autowired
    private ProductSubTypeDao productSubTypeDao;

    @Transactional(readOnly = false)
    public List<ProductSubType> getSubTypeByTypeId(Long id){
        return productSubTypeDao.getSubTypesByTypeId(id);
    }


    public ProductSubTypeDao getProductSubTypeDao() {
        return productSubTypeDao;
    }

    public void setProductSubTypeDao(ProductSubTypeDao productSubTypeDao) {
        this.productSubTypeDao = productSubTypeDao;
    }
}
