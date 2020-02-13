package com.comcarde.productstocklevel.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.comcarde.productstocklevel.ProductStockLevelApplication;
@SpringBootTest(classes = ProductStockLevelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductOrderAdviceControllerTest {

    private static final String BASE_URL = "http://localhost:";

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int localPort;

    @Test
    void testGetProductOrderAdvice() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + localPort + "/product-order-advice",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }
}