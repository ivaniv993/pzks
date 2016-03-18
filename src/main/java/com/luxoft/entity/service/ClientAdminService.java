package com.luxoft.entity.service;

import com.luxoft.entity.dao.ClientDao;
import com.luxoft.entity.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iivaniv on 20.07.2015.
 */
@Service
public class ClientAdminService  {

    @Autowired
    private ClientDao clientDao;

    public List<Customer> searchClientByCriteria( String firstName, String lastName, String phoneNumber){
        Map<String, Object> criteries = new HashMap<String, Object>();
        if(!firstName.equals("")){
            criteries.put("firstName", firstName);
        }
        if(!lastName.equals("")){
            criteries.put("lastName", lastName);
        }
        if(!phoneNumber.equals("")){
            criteries.put("callPhone", phoneNumber);
        }
        List<Customer> customers = clientDao.getCustomerByCriteria(criteries);
        return customers;
    }


    public ClientDao getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

}
