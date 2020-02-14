package com.comcarde.productstocklevel.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.comcarde.productstocklevel.exception.ProductNotFoundException;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.service.ProductService;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();

        return new ResponseEntity<>(allProducts, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/product/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable("productName") String productName) throws ProductNotFoundException {
        Product product = productService.findProductByName(productName);
        return new ResponseEntity<>(product, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createOrUpdateProduct(@RequestBody Product product) {
        Product updated = productService.createOrUpdateProduct(product);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/product/{Product}")
    public HttpStatus deleteProductByProductName(@PathVariable("Product") String productName) throws ProductNotFoundException {
        productService.deleteProduct(productName);
        return HttpStatus.NO_CONTENT;
    }
}
