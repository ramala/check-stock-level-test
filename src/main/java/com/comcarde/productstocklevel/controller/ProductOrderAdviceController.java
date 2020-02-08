package com.comcarde.productstocklevel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.comcarde.productstocklevel.delegate.ProductOrderAdviceDelegate;
import com.comcarde.productstocklevel.model.CheckStockResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ProductOrderAdviceController {

    @Autowired
    private ProductOrderAdviceDelegate productOrderAdviceDelegate;

    @GetMapping("/product-order-advice")
    public ResponseEntity<CheckStockResponse> getProductOrderAdvice() {

        CheckStockResponse response = productOrderAdviceDelegate.prepareStockCheckResponse();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
