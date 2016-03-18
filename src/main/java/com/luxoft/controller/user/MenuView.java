package com.luxoft.controller.user;

import com.luxoft.entity.model.Customer;
import com.luxoft.entity.model.Order;
import com.luxoft.entity.model.Product;
import com.luxoft.entity.service.ClientService;
import com.luxoft.entity.service.ProductTypeService;
import org.apache.log4j.Logger;
import org.primefaces.event.ToggleEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by iivaniv on 30.06.2015.
 */
@ManagedBean
@ViewScoped
public class MenuView {

    private static final Logger log = Logger.getLogger(MenuView.class);

    @ManagedProperty("#{clientService}")
    private ClientService clientService;

    @ManagedProperty("#{productTypeService}")
    private ProductTypeService productTypeService;

    private Map<String, Map<String, Map<String, String>>> assembledProductType;

    @PostConstruct
    public void init() {
        assembledProductType = new HashMap<String, Map<String, Map<String, String>>>();
//        assembledProductType = productTypeService.getAssembledProductType();
        System.out.println(assembledProductType.toString());
    }

    public String save() {

        Customer customer = new Customer("ivan", "ivanov", "6893467542");
        Order order = new Order();
        Product product = new Product();
        System.out.println("RegistrationUserBean:: Registering user " + customer.getFirstName() + " " + customer.getLastName());

        clientService.save(customer);
        return "hello";
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void update() {
        addMessage("Success", "Data updated");
    }

    public void delete() {
        addMessage("Success", "Data deleted");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Map<String, Map<String, Map<String, String>>> getAssembledProductType() {
        return assembledProductType;
    }

    public void setAssembledProductType(Map<String, Map<String, Map<String, String>>> assembledProductType) {
        this.assembledProductType = assembledProductType;
    }


    public ProductTypeService getProductTypeService() {
        return productTypeService;
    }

    public void setProductTypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

}
