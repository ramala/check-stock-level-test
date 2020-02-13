package com.comcarde.productstocklevel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.comcarde.productstocklevel.exception.ProductNotFoundException;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

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
    public void testCreateOrUpdateProduct() {
        Product product = new Product("a", 6);
        when(productRepository.findById(eq("a"))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product readProduct = testee.createOrUpdateProduct(product);
        assertEquals(readProduct.getProductName(), product.getProductName());
        assertEquals(readProduct.getCurrentStock(), product.getCurrentStock());
    }

    @Test
    void testDeleteProduct_throws_exception() {

        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            testee.deleteProduct("a");
        });
    }

    @Test
    void testDeleteProduct() throws ProductNotFoundException {
        Product product = new Product("a", 6);
        when(productRepository.findById(eq("a"))).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById("a");

        testee.deleteProduct("a");
        verify(productRepository).findById(eq("a"));
        verify(productRepository).deleteById(eq("a"));
    }
}