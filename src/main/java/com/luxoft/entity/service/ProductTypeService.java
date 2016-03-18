package com.luxoft.entity.service;

import com.luxoft.entity.dao.ProductCategoryDao;
import com.luxoft.entity.dao.ProductSubTypeDao;
import com.luxoft.entity.dao.ProductTypeDao;
import com.luxoft.entity.model.ProductCategory;
import com.luxoft.entity.model.ProductSubType;
import com.luxoft.entity.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeDao productTypeDao;

    public ProductSubTypeDao getProductSubTypeDao() {
        return productSubTypeDao;
    }

    public void setProductSubTypeDao(ProductSubTypeDao productSubTypeDao) {
        this.productSubTypeDao = productSubTypeDao;
    }


    @Autowired
    private ProductSubTypeDao productSubTypeDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Transactional(readOnly = true)
    public List<ProductType> getAll(){
        List<ProductType> productTypes = productTypeDao.getAll();
        for (ProductType productType : productTypes){
            List<ProductSubType> productSubTypes = productType.getProductSubTypes();
            for (ProductSubType productSubType : productSubTypes){
                List<ProductCategory> productCategories = productSubType.getProductCategorys();
                System.out.println("productCategories = " + productCategories);

                productSubType.setProductCategorys(productCategories);
            }
            productType.setProductSubTypes(productSubTypes);
        }
        return productTypes;
    }

    public ProductTypeDao getProductTypeDao() {
        return productTypeDao;
    }


    public void setProductTypeDao(ProductTypeDao productTypeDao) {
        this.productTypeDao = productTypeDao;
    }



}
