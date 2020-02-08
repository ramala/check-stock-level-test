package com.comcarde.productstocklevel.controller;

import com.comcarde.productstocklevel.exception.ProductNotFoundException;
import com.comcarde.productstocklevel.model.CheckStockResponse;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.model.ProductToBeOrderd;
import com.comcarde.productstocklevel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();

        return new ResponseEntity<>(allProducts, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{product_name}")
    public ResponseEntity<Product> getEmployeeById(@PathVariable("product_name") String product_name) throws ProductNotFoundException {

        Product product = productService.findProductByName(product_name);
        return new ResponseEntity<>(product, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{stock-check}")
    public ResponseEntity<CheckStockResponse> getStockCheckAdvice() {

        CheckStockResponse response = new CheckStockResponse();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
