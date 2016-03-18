package com.luxoft.utils;

import com.luxoft.entity.model.Customer;
import com.luxoft.entity.model.DemoCustomer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIvaniv on 01.10.2015.
 */

public class CustomerUtils {

    public static List<String> compareCustomer(Customer demoCustomer, Customer customer) {

        Field[] fieldsDemoCustomer = demoCustomer.getClass().getDeclaredFields();
        Field[] fieldsCustomer = customer.getClass().getDeclaredFields();
        List<String> messages = new ArrayList<String>();

        for (int i = 0; i < fieldsDemoCustomer.length; i++ ){
            fieldsDemoCustomer[i].setAccessible(true);
            fieldsCustomer[i].setAccessible(true);
            try {
                if (fieldsCustomer[i].get(customer) != null) {
                    if (fieldsDemoCustomer[i].get(demoCustomer) != null) {
                        if (!fieldsCustomer[i].get(customer).equals(fieldsDemoCustomer[i].get(demoCustomer))) {
                            messages.add(" {Object with id [" + demoCustomer.getId() + "] property: " + fieldsDemoCustomer[i].getName() + " have wrong value} ");
                        }
                    } else {
                        messages.add(" {Object with id [" + demoCustomer.getId() + "] property: " + fieldsDemoCustomer[i].getName() + " have wrong value} ");
                    }
                } else {
                    if (fieldsDemoCustomer[i].get(demoCustomer) != null) {
                        messages.add(" {Object with id [" + demoCustomer.getId() + "] property: " + fieldsDemoCustomer[i].getName() + " in expected result not null} ");
                    }
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return messages;

    }


}
