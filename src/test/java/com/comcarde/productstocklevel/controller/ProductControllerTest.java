package com.comcarde.productstocklevel.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import com.comcarde.productstocklevel.ProductStockLevelApplication;
import com.comcarde.productstocklevel.model.Product;

@SpringBootTest(classes = ProductStockLevelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private static final String BASE_URL = "http://localhost:";

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int localPort;

    @Test
    void getAllProducts() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + localPort + "/products",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetProductByProductName() {
        Product product = restTemplate.getForObject(BASE_URL + localPort + "/product/a", Product.class);
        System.out.println(product.getProductName());
        assertNotNull(product);
    }

    @Test
    public void testCreateEmployee() {
        Product product = new Product();
        product.setProductName("a");
        product.setCurrentStock(6);
        ResponseEntity<Product> postResponse = restTemplate.postForEntity(BASE_URL + localPort + "/product", product, Product.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateEmployee() {
        Product product = restTemplate.getForObject(BASE_URL + localPort + "/product/" + "b", Product.class);
        product.setProductName("b");
        product.setCurrentStock(7);
        restTemplate.put(BASE_URL + localPort+ "/product/" + product.getProductName(), product);
        Product updatedProduct = restTemplate.getForObject(BASE_URL + localPort+ "/product/" + product.getProductName(), Product.class);
        assertNotNull(updatedProduct);
    }

    @Test
    public void testDeleteEmployee() {
        Product product = restTemplate.getForObject(BASE_URL + localPort + "/product/c", Product.class);
        assertNotNull(product);
        restTemplate.delete(BASE_URL + localPort + "/employees/c");
        try {
            product = restTemplate.getForObject(BASE_URL + localPort + "/product/c", Product.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}