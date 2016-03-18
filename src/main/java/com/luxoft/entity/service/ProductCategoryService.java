package com.luxoft.entity.service;

import com.luxoft.entity.dao.ProductCategoryDao;
import com.luxoft.entity.model.ProductCategory;
import com.luxoft.exceptions.admin.NoFindCategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Service("productCategoryService")
public class ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;


    public List<ProductCategory> getCategoryBySubTypeId(Long id){
        return productCategoryDao.getCategoryBySubTypeId(id);
    }

    public List<ProductCategory> getAllProductCategories() throws NoFindCategoryException {
        return productCategoryDao.getAllProductCategories();
    }

    public ProductCategory getProductCategoryByName(String name) throws NoFindCategoryException {
        return productCategoryDao.getProductCategoryByName(name);
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public void setProductCategoryDao(ProductCategoryDao productCategoryDao) {
        this.productCategoryDao = productCategoryDao;
    }
}
