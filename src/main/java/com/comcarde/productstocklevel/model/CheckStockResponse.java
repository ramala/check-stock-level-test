package com.comcarde.productstocklevel.model;

import java.util.List;

public class CheckStockResponse {

    private List<ProductToBeOrderd> toBeOrderdList;

    public List<ProductToBeOrderd> getToBeOrderdList() {
        return toBeOrderdList;
    }

    public void setToBeOrderdList(List<ProductToBeOrderd> toBeOrderdList) {
        this.toBeOrderdList = toBeOrderdList;
    }

    public void addToOrderList(ProductToBeOrderd productToBeOrderd) {
        this.toBeOrderdList.add(productToBeOrderd);
    }
}
