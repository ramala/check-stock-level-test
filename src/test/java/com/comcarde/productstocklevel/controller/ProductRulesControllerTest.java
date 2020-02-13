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
import com.comcarde.productstocklevel.model.ProductRules;

@SpringBootTest(classes = ProductStockLevelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRulesControllerTest {

    private static final String BASE_URL = "http://localhost:";

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int localPort;

    @Test
    void testGetAllProductRules() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + localPort + "/product/rules",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetProductRulesByProductName() {
        ProductRules productRules = restTemplate.getForObject(BASE_URL + localPort + "/product/rules/a", ProductRules.class);
        System.out.println(productRules.getProductName());
        assertNotNull(productRules);
    }

    @Test
    public void testCreateProductRules() {
        ProductRules productRules = new ProductRules();
        productRules.setProductName("a");
        productRules.setMinimumStockLevel(5);
        productRules.setProductBlocked(Boolean.TRUE);
        productRules.setAdditionalVolume(15);
        ResponseEntity<ProductRules> postResponse = restTemplate.postForEntity(BASE_URL + localPort + "/product/rules", productRules, ProductRules.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateProductRules() {
        ProductRules productRules = restTemplate.getForObject(BASE_URL + localPort + "/product/rules" + "b", ProductRules.class);
        productRules.setProductName("b");
        productRules.setProductBlocked(Boolean.TRUE);
        productRules.setAdditionalVolume(15);
        restTemplate.put(BASE_URL + localPort+ "/product/" + productRules.getProductName(), productRules);
        ProductRules updatedProduct = restTemplate.getForObject(BASE_URL + localPort+ "/product/rules/" + productRules.getProductName(), ProductRules.class);
        assertNotNull(updatedProduct);
    }

    @Test
    public void testDeleteProductRulesByProductName() {
        ProductRules productRules = restTemplate.getForObject(BASE_URL + localPort + "/product/riles/c", ProductRules.class);
        assertNotNull(productRules);
        restTemplate.delete(BASE_URL + localPort + "/product/rules/c");
        try {
            productRules = restTemplate.getForObject(BASE_URL + localPort + "/product/c", ProductRules.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}