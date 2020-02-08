package com.comcarde.productstocklevel.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.comcarde.productstocklevel.exception.ProductRulesNotFoundException;
import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.service.ProductRulesService;

@RestController
public class ProductRulesController {

    @Autowired
    private ProductRulesService productRulesService;

    @GetMapping("/product/rules")
    public ResponseEntity<List<ProductRules>> getAllProductRuless() {
        List<ProductRules> allProductRuless = productRulesService.getAllProductRules();

        return new ResponseEntity<>(allProductRuless, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/product/rules/{productName}")
    public ResponseEntity<ProductRules> getProductRulesByProductName(@PathVariable("productName") String productName) throws ProductRulesNotFoundException {
        ProductRules productRules = productRulesService.findProductRulesByProductName(productName);
        return new ResponseEntity<>(productRules, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/product/rules")
    public ResponseEntity<ProductRules> createOrUpdateProductRules(ProductRules productRules) {
        ProductRules updatedProductRules = productRulesService.createOrUpdateProductRules(productRules);
        return new ResponseEntity<ProductRules>(updatedProductRules, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("product/rules/{productName}")
    public HttpStatus deleteProductById(@PathVariable("productName") String productName) throws ProductRulesNotFoundException {
        productRulesService.deleteProduct(productName);
        return HttpStatus.NO_CONTENT;
    }
}
