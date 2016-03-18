package com.luxoft.entity.service;

import com.luxoft.entity.dao.ProductDao;
import com.luxoft.entity.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iivaniv on 09.07.2015.
 */
@Service("productService")
public class ProductService {

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    private ProductDao productDao;

    @Transactional(readOnly = false)
    public void save(Product product){
        productDao.save(product);
    }

    @Transactional(readOnly = false)
    public void delete(Product product){
        productDao.delete(product);
    }

    @Transactional(readOnly = false)
    public void update(Product product){
        productDao.update(product);
    }

    @Transactional(readOnly = false)
    public Product getProductById(Long id){
        return productDao.getProductById(id);
    }

    @Transactional
    public List<Product> getAll(){
        return productDao.getAll();
    }

}
