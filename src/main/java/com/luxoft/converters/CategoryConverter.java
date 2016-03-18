package com.luxoft.converters;

import com.luxoft.entity.dao.ProductCategoryDao;
import com.luxoft.entity.model.ProductCategory;
import com.luxoft.entity.service.ProductCategoryService;
import com.luxoft.exceptions.admin.NoFindCategoryException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iivaniv on 20.07.2015.
 */
@FacesConverter("com.luxoft.converters.CategoryConverter")
public class CategoryConverter implements Converter {

    public ProductCategoryService getProductCategoryService() {
        return productCategoryService;
    }

    public void setProductCategoryService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @ManagedProperty("#{productCategoryService}")
    private ProductCategoryService productCategoryService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ProductCategory productCategory = null;
        String str = value.toLowerCase();
        List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
        try {
            if (productCategoryService != null)
                System.out.println("Yeapppp");
            else System.out.println("------------ "+value);
            productCategory = productCategoryService.getProductCategoryByName(value);
        } catch (NoFindCategoryException e) {
            FacesMessage msg = new FacesMessage("Category Conversion error.", "Invalid Category name.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        return productCategory;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        ProductCategory productCategory = (ProductCategory) value;
        return productCategory.getName();
    }
}
