package com.luxoft.entity.service;

import com.luxoft.entity.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IIvaniv on 01.10.2015.
 */
@Service
public interface CustomerService {

    List<Customer> selectAllComputedCustomer(int lowerId, int topId);

    List<Customer> selectAllExpectedCustomer(int lowerId, int topId);

    List<String> verifyTwoTables();

    int[] batchUpdate(final List<Customer> customers);


    void getProductFewCriteriaBuilder();
}
