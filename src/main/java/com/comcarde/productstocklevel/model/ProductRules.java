package com.comcarde.productstocklevel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_RULES")
public class ProductRules {

    @Id
    @Column(name = "product_name")
    private String productName;

    @Column(name = "minimum_stock_level")
    private int minimumStockLevel;

    @Column(name = "product_blocked")
    private boolean productBlocked;

    @Column(name = "additional_volume")
    private int additionalVolume;

    public ProductRules() {
        super();
    }

    public ProductRules(String productName, int minimumStock, boolean productBlocked, int additionalVolume) {
        this.productName = productName;
        this.minimumStockLevel = minimumStock;
        this.productBlocked = productBlocked;
        this.additionalVolume = additionalVolume;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMinimumStockLevel() {
        return minimumStockLevel;
    }

    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }

    public boolean isProductBlocked() {
        return productBlocked;
    }

    public void setProductBlocked(boolean productBlocked) {
        this.productBlocked = productBlocked;
    }

    public int getAdditionalVolume() {
        return additionalVolume;
    }

    public void setAdditionalVolume(int additionalVolume) {
        this.additionalVolume = additionalVolume;
    }
}
