package com.luxoft.entity.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iivaniv on 07.07.2015.
 */
@Entity
@Table(name="product_sub_type", schema = "public")
public class ProductSubType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSubType")
    private List<ProductCategory> productCategorys;

    @JoinColumn( name="product_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProductType productType;

    @Column(name="name")
    private String name;

    public ProductSubType(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductCategory> getProductCategorys() {
        return productCategorys;
    }

    public void setProductCategorys(List<ProductCategory> productCategorys) {
        this.productCategorys = productCategorys;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
