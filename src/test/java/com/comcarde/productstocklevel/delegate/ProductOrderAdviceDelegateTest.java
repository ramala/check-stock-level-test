package com.comcarde.productstocklevel.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.comcarde.productstocklevel.exception.ProductAdviceJsonProcessingException;
import com.comcarde.productstocklevel.model.CheckStockResponse;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.model.StockCheckAdvice;
import com.comcarde.productstocklevel.repository.ProductOrderAdviceRepository;
import com.comcarde.productstocklevel.repository.ProductRepository;
import com.comcarde.productstocklevel.repository.ProductRulesRepository;

@ExtendWith(MockitoExtension.class)
class ProductOrderAdviceDelegateTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductRulesRepository productRulesRepository;
    @Mock
    private ProductOrderAdviceRepository productOrderAdviceRepository;

    @Captor
    ArgumentCaptor<StockCheckAdvice> stockCheckAdviceCaptor;

    @InjectMocks
    ProductOrderAdviceDelegate testee;


    @Test
    void testPrepareStockCheckResponse() throws ProductAdviceJsonProcessingException {
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

        verify(productOrderAdviceRepository).save(stockCheckAdviceCaptor.capture());

        StockCheckAdvice captorValue = stockCheckAdviceCaptor.getValue();
        assertEquals(captorValue.getAdvicePayload(), "{\"toBeOrderdList\":[{\"productName\":\"d\",\"quantity\":23},{\"productName\":\"e\",\"quantity\":3}]}");
    }
}