package com.comcarde.productstocklevel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.comcarde.productstocklevel.exception.ProductRulesNotFoundException;
import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.repository.ProductRulesRepository;

@ExtendWith(MockitoExtension.class)
class ProductRulesServiceTest {

    @Mock
    ProductRulesRepository productRulesRepository;

    @InjectMocks
    ProductRulesService testee;

    @Test
    void testGetAllProductRules_when_empty() {
        when(productRulesRepository.findAll()).thenReturn(Collections.emptyList());
        List<ProductRules> allProductRuless = testee.getAllProductRules();
        assertEquals(allProductRuless.size(), 0);
    }
    @Test
    void testGetAllProductRules() {
        when(productRulesRepository.findAll()).thenReturn(Collections.singletonList(new ProductRules("a", 5, false, 0)));
        List<ProductRules> allProductRules = testee.getAllProductRules();
        assertEquals(allProductRules.size(), 1);
        assertEquals(allProductRules.get(0).getProductName(), "a");
        assertEquals(allProductRules.get(0).getMinimumStockLevel(), 5);
        assertFalse(allProductRules.get(0).isProductBlocked());
        assertEquals(allProductRules.get(0).getAdditionalVolume(), 0);
    }

    @Test
    void findProductRulesByProductName() throws ProductRulesNotFoundException {
        when(productRulesRepository.findById(eq("a"))).thenReturn(Optional.of(new ProductRules("a", 6, true, 2)));
        ProductRules productRules = testee.findProductRulesByProductName("a");
        assertNotNull(productRules);
        assertEquals(productRules.getProductName(), "a");
        assertEquals(productRules.getMinimumStockLevel(), 6);
        assertTrue(productRules.isProductBlocked());
        assertEquals(productRules.getAdditionalVolume(), 2);
    }
    @Test
    void testFindProductRulesByName_throws_exception() {

        Assertions.assertThrows(ProductRulesNotFoundException.class, () -> {
            testee.findProductRulesByProductName("a");
        });
    }

    @Test
    void testCreateOrUpdateProductRules() {
        ProductRules productRules = new ProductRules("a", 6, true, 2);
        when(productRulesRepository.findById(eq("a"))).thenReturn(Optional.of(productRules));
        when(productRulesRepository.save(any(ProductRules.class))).thenReturn(productRules);

        ProductRules readProductRules = testee.createOrUpdateProductRules(productRules);
        assertEquals(readProductRules.getProductName(), productRules.getProductName());
        assertEquals(readProductRules.getMinimumStockLevel(), productRules.getMinimumStockLevel());
        assertEquals(readProductRules.getAdditionalVolume(), productRules.getAdditionalVolume());
        assertEquals(readProductRules.isProductBlocked(), productRules.isProductBlocked());
    }


    @Test
    void testDeleteProductRules_throws_exception() {

        Assertions.assertThrows(ProductRulesNotFoundException.class, () -> {
            testee.deleteProductRules("a");
        });
    }

    @Test
    void testDeleteProductRules() throws ProductRulesNotFoundException {

        ProductRules productRules = new ProductRules("a", 6, true, 2);
        when(productRulesRepository.findById(eq("a"))).thenReturn(Optional.of(productRules));
        doNothing().when(productRulesRepository).deleteById("a");

        testee.deleteProductRules("a");
        verify(productRulesRepository).findById(eq("a"));
        verify(productRulesRepository).deleteById(eq("a"));
    }
}