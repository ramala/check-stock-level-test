package com.comcarde.productstocklevel.model;

public class ProductToBeOrderd {

    private String productName;
    private int quantity;

    public ProductToBeOrderd(String productName, int quantityToOrder) {
        this.productName = productName;
        this.quantity = quantityToOrder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
