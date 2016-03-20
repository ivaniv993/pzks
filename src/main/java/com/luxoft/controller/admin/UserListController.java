package com.luxoft.controller.admin;

import com.luxoft.entity.model.Customer;
import com.luxoft.entity.service.*;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by iivaniv on 20.07.2015.
 */
@ManagedBean
@ViewScoped
public class UserListController {

    @ManagedProperty("#{productService}")
    private ProductService productService;

    @ManagedProperty("#{clientService}")
    private ClientService clientService;

    @ManagedProperty("#{productTypeService}")
    private ProductTypeService productTypeService;

    @ManagedProperty("#{adminProductService}")
    private AdminProductService adminProductService;

    @ManagedProperty("#{clientAdminService}")
    private ClientAdminService clientAdminService;

    private List<Customer> customers;

    private String firstName;

    private String lastName;
    private String telephoneNumber;

    @PostConstruct
    public void init(){

//        customers = clientService.getAll();
    }

    public void onRowEdit(RowEditEvent event) {
        Customer customer = (Customer) event.getObject();
        FacesMessage msg = new FacesMessage("Product Edited", customer.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        clientService.update(customer);
    }


    public void searchByCriteria(){
        customers = clientAdminService.searchClientByCriteria(firstName, lastName, telephoneNumber);
    }

    public ProductTypeService getProductTypeService() {
        return productTypeService;
    }

    public void setProductTypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public AdminProductService getAdminProductService() {
        return adminProductService;
    }

    public void setAdminProductService(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public ClientAdminService getClientAdminService() {
        return clientAdminService;
    }

    public void setClientAdminService(ClientAdminService clientAdminService) {
        this.clientAdminService = clientAdminService;
    }
}
