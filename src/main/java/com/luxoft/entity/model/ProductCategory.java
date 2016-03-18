package com.luxoft.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iivaniv on 07.07.2015.
 */
@Entity
@Table(name="product_category", schema = "public")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "product_sub_type_id", referencedColumnName = "id")
    @ManyToOne( optional = false )
    private ProductSubType productSubType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategories")
    private List<Product> products;

    @Column( name="name")
    private String name;

    public ProductCategory(){}

    public ProductCategory(ProductSubType productSubType, String name) {
        this.productSubType = productSubType;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductSubType getProductSubType() {
        return productSubType;
    }

    public void setProductSubType(ProductSubType productSubType) {
        this.productSubType = productSubType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", productSubType=" + productSubType +
                ", name='" + name + '\'' +
                '}';
    }
}
