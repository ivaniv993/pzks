package com.luxoft.controller.admin;

import com.luxoft.email.EmailService;
import com.luxoft.entity.model.*;
import com.luxoft.entity.service.AdminProductService;
import com.luxoft.entity.service.FeatureService;
import com.luxoft.entity.service.ProductService;
import com.luxoft.entity.service.ProductTypeService;
import com.luxoft.exceptions.NoProductFindException;
import com.luxoft.exceptions.admin.NoFindCategoryException;
import com.luxoft.exceptions.admin.NoFindSubTypeException;
import com.luxoft.exceptions.admin.NoFindTypeException;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iivaniv on 09.07.2015.
 *
 */
@ManagedBean(name="editController")
@ViewScoped
public strictfp class EditController implements Serializable {

    private List<Product> products;

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @ManagedProperty("#{productService}")

    private ProductService productService;

    public AdminProductService getAdminProductService() {
        return adminProductService;
    }

    public void setAdminProductService(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @ManagedProperty("#{productTypeService}")
    private ProductTypeService productTypeService;

    @ManagedProperty("#{adminProductService}")
    private AdminProductService adminProductService;


    @ManagedProperty("#{featureService}")
    private FeatureService featureService;

    @ManagedProperty("#{emailService}")
    private EmailService emailService;

    private String name;
    private String category;
    private String status;
    private String stockAddress;

    private List<ProductType> productTypes;

    private Map<String, Map<String, List<String>>> assembledType;
    private List<String> productTypeNames;
    private List<String> productSubTypeNames;
    private List<String> productCategoriesNames;
    private String prodType;
    private String prodSubType;
    private String prodCategory;

    private Product selectedRow;
    private ProductStatus[] productStatuses;
    private Product product, selectedProduct;

    private List<Features> features;

    @PostConstruct
    public void init() {

        productTypeNames = new ArrayList<String>();
        productSubTypeNames = new ArrayList<String>();
        productCategoriesNames = new ArrayList<String>();
//        productTypes = productTypeService.getAll();
//        products = productService.getAll();
//
//        assembledType = new HashMap<String, Map<String, List<String>>>();
//        for ( ProductType productType : productTypes ){
//            Map<String, List<String>> pc = new HashMap<String, List<String>>();
//            for ( ProductSubType productSubType : productType.getProductSubTypes()){
//                List<String> categoriesName = new ArrayList<String>();
//                List<ProductCategory> productCategories =  productSubType.getProductCategorys();
//                for (ProductCategory productCategory : productCategories){
//                    categoriesName.add(productCategory.getName());
//                }
//                pc.put(productSubType.getName(), categoriesName);
//            }
//            assembledType.put(productType.getName(), pc);
//        }
//        features =new ArrayList<Features>();
//        productTypeNames.addAll(assembledType.keySet());
        product = new Product();
        productStatuses = ProductStatus.values();
    }

    public void onProductTypeChange(){
        productSubTypeNames = new ArrayList<String>();

        if (assembledType.containsKey(prodType)){
            productSubTypeNames.addAll(assembledType.get(prodType).keySet());
            productCategoriesNames = new ArrayList<String>();
            if (assembledType.get(prodType).containsKey(prodSubType)){
                productCategoriesNames.addAll(assembledType.get(prodType).get(prodSubType));
            }
        }
    }

    public void onItemSelect(){
        System.out.println("----------------------------");
        for (Features feature : features){
            System.out.println("Name  = "+feature.getName());
        }

    }

    public void addFeature(){
        Features feature =  new Features();
        features.add(feature);
    }

    public List<String> completeFeatureName(String query){
        return featureService.getFeaturesName(query);
    }

    public List<String> completeProductNames(String query){
        return adminProductService.completeProductNames(query);
    }

    public List<String> completeProductCategories(String query){
        List<String> products = null;
        try {
            products = adminProductService.completeProductCategories(query);
        } catch (NoFindCategoryException e) {
            FacesMessage msg = new FacesMessage("Can't find category");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return  products;
    }

    public List<String> completeProductStatuses(String query){
        return adminProductService.completeProductStatus(query);
    }

    public List<String> completeProductStockAddress(String query){
        return adminProductService.completeProductStockAddress(query);
    }



    public void searchByCriteria() {

        emailService.sendMail();

        //TODO remove comments
//        try {
//            products = adminProductService.getProductByParams(name, category, status, stockAddress);
//        } catch (NoFindCategoryException e) {
//            FacesMessage msg = new FacesMessage("Category doesn`t exist");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }


    public void delete() {
        if(selectedProduct == null ){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("No any selected product", "Please select product"));
        }else {
            try {
                adminProductService.getProduct(selectedProduct);
                productService.delete(selectedProduct);
            } catch (NoProductFindException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("No existing product", "Please select product"));
            }
        }
        products = productService.getAll();
    }

    public void onRowEdit(RowEditEvent event) {
        Product product = (Product) event.getObject();
        FacesMessage msg = new FacesMessage("Product Edited", product.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        productService.update(product);
    }

    public void onSaveProduct() {
        String msg = "", title="Success";
        try {
            adminProductService.saveProduct(product, prodType, prodSubType, prodCategory);
        } catch (NoFindTypeException e) {
            title = "Error";
            msg = "Wrong product type";
        } catch (NoFindSubTypeException e) {
            title = "Error";
            msg = "Wrong product sub type";
        } catch (NoFindCategoryException e) {
            title = "Error";
            msg = "Wrong category type";
        }finally {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(title, msg) );
        }
        products = productService.getAll();

    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdSubType() {
        return prodSubType;
    }

    public void setProdSubType(String prodSubType) {
        this.prodSubType = prodSubType;
    }

    public ProductTypeService getProductTypeService() {
        return productTypeService;
    }

    public void setProductTypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public Product getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Product selectedRow) {
        this.selectedRow = selectedRow;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductStatus[] getProductStatuses() {
        return productStatuses;
    }

    public void setProductStatuses(ProductStatus[] productStatuses) {
        this.productStatuses = productStatuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockAddress() {
        return stockAddress;
    }

    public void setStockAddress(String stockAddress) {
        this.stockAddress = stockAddress;
    }


    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public List<String> getProductTypeNames() {
        return productTypeNames;
    }

    public void setProductTypeNames(List<String> productTypeNames) {
        this.productTypeNames = productTypeNames;
    }

    public List<String> getProductCategoriesNames() {
        return productCategoriesNames;
    }

    public void setProductCategoriesNames(List<String> productCategoriesNames) {
        this.productCategoriesNames = productCategoriesNames;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public List<String> getProductSubTypeNames() {
        return productSubTypeNames;
    }

    public void setProductSubTypeNames(List<String> productSubTypeNames) {
        this.productSubTypeNames = productSubTypeNames;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
    public List<Features> getFeatures() {
        return features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }


    public FeatureService getFeatureService() {
        return featureService;
    }

    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }
}
