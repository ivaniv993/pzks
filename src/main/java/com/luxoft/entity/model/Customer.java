package com.luxoft.entity.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iivaniv on 01.07.2015.
 */
@Entity
@BatchSize(size=5)
@Table(name="customer", schema = "public")
public class Customer implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return !(id != null ? !id.equals(customer.id) : customer.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="first_name")
    private String firstName;


    @Column(name="last_name")
    private String lastName;

    @Column(name="call_phone")
    private String callPhone;

    public String getHernia() {
        return hernia;
    }

    public void setHernia(String hernia) {
        this.hernia = hernia;
    }

    @Basic
    private String hernia;

//    @OneToMany(cascade=CascadeType.ALL, mappedBy = "customer")
//    private List<Order> orders;

    public Customer() {}

    @Override
    public String toString() {
        return this.getId()+","+this.getFirstName()+","+this.getLastName()+","+this.getCallPhone();
    }

    public Customer(String firstname, String lastname, String phone) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.callPhone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCallPhone() {
        return callPhone;
    }

    public void setCallPhone(String callPhone) {
        this.callPhone = callPhone;
    }

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
}