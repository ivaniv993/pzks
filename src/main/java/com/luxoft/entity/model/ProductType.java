package com.luxoft.entity.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iivaniv on 07.07.2015.
 */
@Entity
@Table(name="product_type", schema = "public")
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productType")
    private List<ProductSubType> productSubTypes;


    public ProductType(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductSubType> getProductSubTypes() {
        return productSubTypes;
    }

    public void setProductSubTypes(List<ProductSubType> productSubTypes) {
        this.productSubTypes = productSubTypes;
    }
}
