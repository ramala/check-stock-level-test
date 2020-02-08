package com.comcarde.productstocklevel.model;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "product_name")
    private String productName;

    @Column(name = "current_stock")
    private int currentStock;

    public Product(String productName, int quantityToOrder) {
        this.productName = productName;
        this.currentStock = quantityToOrder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    @Override
    public String toString() {
        return String.format("Prodcut [productName: %s, currentStock: %s]", productName, currentStock);
    }
}
