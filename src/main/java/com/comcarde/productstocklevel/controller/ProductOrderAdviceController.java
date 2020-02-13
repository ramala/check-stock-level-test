package com.comcarde.productstocklevel.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.comcarde.productstocklevel.delegate.ProductOrderAdviceDelegate;
import com.comcarde.productstocklevel.model.CheckStockResponse;

@RestController
public class ProductOrderAdviceController {

    private ProductOrderAdviceDelegate productOrderAdviceDelegate;

    public ProductOrderAdviceController(ProductOrderAdviceDelegate productOrderAdviceDelegate) {
        this.productOrderAdviceDelegate = productOrderAdviceDelegate;
    }

    @GetMapping("/product-order-advice")
    public ResponseEntity<CheckStockResponse> getProductOrderAdvice() {

        CheckStockResponse response = productOrderAdviceDelegate.prepareStockCheckResponse();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
