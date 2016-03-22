package com.luxoft.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iivaniv on 07.07.2015.
 */
@Entity
@Table(name="feature", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Features implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(mappedBy = "features")
    private List<Product> products;

    public Features(){}


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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
