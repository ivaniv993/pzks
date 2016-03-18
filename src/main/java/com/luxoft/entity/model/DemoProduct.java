package com.luxoft.entity.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iivaniv on 07.07.2015.
 */
@Entity
@Table(name="demo_product", schema = "public")
public class DemoProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

    @Column( name= "name")
    private String name;

    @Column(name="stock_location")
    private String stockLocation;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private Double price;

    @JoinColumn(name = "prod_category_id", referencedColumnName = "id")
    @ManyToOne(optional = true )
    private ProductCategory productCategories;

    @Column(name="prod_status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Order order;

    @ManyToMany
    @JoinTable(name="product_feature",
            joinColumns = @JoinColumn(name="product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="feature_id", referencedColumnName = "id")
    )
    private List<Features> features;

    public DemoProduct(){}

    public DemoProduct(String name, String stockLocation, ProductCategory productCategories, String description, ProductStatus productStatus, Order order) {
        this.name = name;
        this.stockLocation = stockLocation;
        this.productCategories = productCategories;
        this.description = description;
        this.productStatus = productStatus;
        this.order = order;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("init producrt "+name);
        this.name = name;
    }

    public String getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(String stockLocation) {
        this.stockLocation = stockLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Features> getFeatures() {
        return features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(ProductCategory productCategories) {
        this.productCategories = productCategories;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
