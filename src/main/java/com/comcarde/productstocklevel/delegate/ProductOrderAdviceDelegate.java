package com.comcarde.productstocklevel.delegate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Component;
import com.comcarde.productstocklevel.exception.ProductAdviceJsonProcessingException;
import com.comcarde.productstocklevel.model.CheckStockResponse;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.model.ProductToBeOrderd;
import com.comcarde.productstocklevel.model.StockCheckAdvice;
import com.comcarde.productstocklevel.repository.ProductOrderAdviceRepository;
import com.comcarde.productstocklevel.repository.ProductRepository;
import com.comcarde.productstocklevel.repository.ProductRulesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductOrderAdviceDelegate {

    private ProductRepository productRepository;
    private ProductRulesRepository productRulesRepository;
    private ProductOrderAdviceRepository productOrderAdviceRepository;

    public ProductOrderAdviceDelegate(ProductRepository productRepository, ProductRulesRepository productRulesRepository, ProductOrderAdviceRepository productOrderAdviceRepository) {
        this.productRepository = productRepository;
        this.productRulesRepository = productRulesRepository;
        this.productOrderAdviceRepository = productOrderAdviceRepository;
    }

    public CheckStockResponse prepareStockCheckResponse() throws ProductAdviceJsonProcessingException {
        List<Product> productList = productRepository.findAll();
        List<ProductRules> productRulesList = productRulesRepository.findAll();
        CheckStockResponse response = new CheckStockResponse();
        response.setToBeOrderdList(new ArrayList<>());

        for (Product product : productList) {
            for (ProductRules productRules : productRulesList) {
                if (product.getProductName().equalsIgnoreCase(productRules.getProductName())) {
                    applyRules(product, productRules, response);
                }
            }
        }
        StockCheckAdvice stockCheckAdvice = new StockCheckAdvice();
        stockCheckAdvice.setAdviceGivenDate(Calendar.getInstance().getTime());
        try {
            stockCheckAdvice.setAdvicePayload(new ObjectMapper().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new ProductAdviceJsonProcessingException("Not able to write stock check advice payload to object");
        }
        productOrderAdviceRepository.save(stockCheckAdvice);
        return response;
    }

    private void applyRules(Product product, ProductRules productRules, CheckStockResponse response) {
        int quantityToOrder = 0;
        if (product.getCurrentStock() < productRules.getMinimumStockLevel() && !productRules.isProductBlocked()) {
            quantityToOrder = productRules.getMinimumStockLevel() - product.getCurrentStock();
        }
        if (productRules.getAdditionalVolume() > 0) {
            quantityToOrder += productRules.getAdditionalVolume();
        }
        if (quantityToOrder > 0) {
            response.addToOrderList(new ProductToBeOrderd(product.getProductName(), quantityToOrder));
        }
    }
}
