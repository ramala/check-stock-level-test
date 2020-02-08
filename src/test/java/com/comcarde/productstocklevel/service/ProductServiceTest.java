package com.comcarde.productstocklevel.service;

import com.comcarde.productstocklevel.exception.ProductNotFoundException;
import com.comcarde.productstocklevel.model.CheckStockResponse;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.repository.ProductRepository;
import com.comcarde.productstocklevel.repository.ProductRulesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRulesRepository productRulesRepository;

    @InjectMocks
    ProductService testee;

    @Test
    void testGetAllProducts_When_empty() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> allProducts = testee.getAllProducts();
        assertEquals(allProducts.size(), 0);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(new Product("a", 5)));
        List<Product> allProducts = testee.getAllProducts();
        assertEquals(allProducts.size(), 1);
        assertEquals(allProducts.get(0).getProductName(), "a");
        assertEquals(allProducts.get(0).getCurrentStock(), 5);
    }

    @Test
    void testFindProductByName() throws ProductNotFoundException {
        when(productRepository.findById(eq("a"))).thenReturn(Optional.of(new Product("a", 6)));
        Product product = testee.findProductByName("a");
        assertNotNull(product);
        assertEquals(product.getProductName(), "a");
        assertEquals(product.getCurrentStock(), 6);
    }

    @Test
    void testFindProductByName_throws_exception() {

        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            testee.findProductByName("a");
        });
    }

    @Test
    void testPrepareStockCheckResponse() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("a", 5));
        productList.add(new Product("b", 8));
        productList.add(new Product("c", 2));
        productList.add(new Product("d", 0));
        productList.add(new Product("e", 1));
        when(productRepository.findAll()). thenReturn(productList);

        List<ProductRules> productRulesList = new ArrayList<>();
        productRulesList.add(new ProductRules("a", 4, false, 0));
        productRulesList.add(new ProductRules("b", 4, false, 0));
        productRulesList.add(new ProductRules("c", 4, true, 0));
        productRulesList.add(new ProductRules("d", 8, false, 15));
        productRulesList.add(new ProductRules("e", 4, false, 0));
        when(productRulesRepository.findAll()).thenReturn(productRulesList);

        CheckStockResponse response = testee.prepareStockCheckResponse();
        assertEquals(response.getToBeOrderdList().size(), 2);
        assertEquals(response.getToBeOrderdList().get(0).getProductName(), "d");
        assertEquals(response.getToBeOrderdList().get(0).getQuantity(), 23);
        assertEquals(response.getToBeOrderdList().get(1).getProductName(), "e");
        assertEquals(response.getToBeOrderdList().get(1).getQuantity(), 3);
    }
}