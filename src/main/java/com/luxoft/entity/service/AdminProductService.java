package com.luxoft.entity.service;

import com.luxoft.entity.dao.*;
import com.luxoft.entity.model.*;
import com.luxoft.exceptions.NoProductFindException;
import com.luxoft.exceptions.admin.NoFindCategoryException;
import com.luxoft.exceptions.admin.NoFindSubTypeException;
import com.luxoft.exceptions.admin.NoFindTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iivaniv on 15.07.2015.
 */
@Service
public class AdminProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductTypeDao productTypeDao;

    @Autowired
    private FeatureDao featureDao;

    @Autowired
    private ProductSubTypeDao productSubTypeDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;


    public List<Product> getProductByParams(String name, String category, String status, String stockLocation) throws NoFindCategoryException {
        Map<String, Object> params = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(name)) params.put("name", name);
        if (!StringUtils.isEmpty(stockLocation)) params.put("stockLocation", stockLocation);
        if (!StringUtils.isEmpty(status)) {
            ProductStatus ps;
            try {
                ps = ProductStatus.valueOf(status);
                params.put("productStatus", ps);
            } catch (IllegalArgumentException ex) {
                throw new NoFindCategoryException();
            }
        }
        if (!StringUtils.isEmpty(category)) {
            ProductCategory productCategory = productCategoryDao.getProductCategoryByName(category);
            if (productCategory != null) {
                params.put("productCategories", productCategory);
            }
        }
        return productDao.getProductsByParameters(params);
    }

    public Product getProduct(Product selectedProduct) throws NoProductFindException {
        Product product = productDao.getProductById(selectedProduct.getProductId());
        if (product != null)
            return product;
        else
            throw new NoProductFindException();
    }

    public List<String> completeProductNames(String query) {
        List<String> products = productDao.getCompleteProductNames();
        List<String> names = new ArrayList<String>();
        String str = query.toLowerCase();

        for (String product : products) {
            if (product.toLowerCase().startsWith(str)) {
                names.add(product);
            }
        }
        return names;
    }

    public List<String> completeProductCategories(String query) throws NoFindCategoryException, NoProductFindException {
        List<String> productCategories = productCategoryDao.getCategoriesWithProduct();
        List<String> names = new ArrayList<String>();
        String str = query.toLowerCase();

        for (String productCategory : productCategories) {
            if (productCategory.toLowerCase().startsWith(str)) {
                names.add(productCategory);
            }
        }
        return names;
    }

    public List<String> completeProductStatus(String query) {
        List<ProductStatus> list = productDao.getProdStatuses();
        List<String> statuses = new ArrayList<String>();
        for (ProductStatus status : list) {
            String buf = status.name();
            if (buf.toLowerCase().startsWith(query))
                statuses.add(buf);
        }
        return statuses;
    }

    public List<String> completeProductStockAddress(String query) {
        List<String> list = productDao.getAllStockAddress();
        List<String> statuses = new ArrayList<String>();
        for (String status : list) {
            if (status.toLowerCase().startsWith(query))
                statuses.add(status);
        }
        return statuses;
    }

    @Transactional
    public void saveProduct(Product product, String prodType, String prodSubType, String prodCategory) throws NoFindTypeException, NoFindSubTypeException, NoFindCategoryException {
        ProductCategory productCategory = null;
        productTypeDao.getProductByName(prodType);
        productSubTypeDao.getSubTypeByName(prodSubType);
        productCategory = productCategoryDao.getProductCategoryByName(prodCategory);
        product.setProductCategories(productCategory);

        List<Features> features = new ArrayList<Features>();
        Features f1 = new Features();
        f1.setName("tratata");
        Features f2 = new Features();
        f2.setName("blablabla");
        features.add(f1);
        features.add(f2);
        featureDao.save(f1);
        featureDao.save(f2);

        product.setFeatures(features);

        product.setProductStatus(ProductStatus.AVALIABLE);
        productDao.save(product);

    }

    public List<ProductType> getAllProductType() {
        return productTypeDao.getAll();
    }

    public ProductTypeDao getProductTypeDao() {
        return productTypeDao;
    }

    public void setProductTypeDao(ProductTypeDao productTypeDao) {
        this.productTypeDao = productTypeDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

}
