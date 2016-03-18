package com.luxoft.entity.service;

import com.luxoft.entity.dao.ClientDao;
import com.luxoft.entity.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iivaniv on 07.07.2015.
 */
@Service("clientService")
public class ClientService {

    @Autowired
    private ClientDao clientDao;

    @Transactional(readOnly = false)
    public void save(Customer stock) {
        clientDao.save(stock);
    }

    @Transactional(readOnly = false)
    public void update(Customer stock) {
        clientDao.update(stock);
    }

    @Transactional(readOnly = false)
    public void delete(Customer stock) {
        clientDao.delete(stock);
    }

    @Transactional(readOnly = true)
    public Customer findByClientId(Long id) {
        return clientDao.findByClientId(id);
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll(){
        return clientDao.getAll();
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
}
