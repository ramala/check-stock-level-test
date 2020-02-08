package com.comcarde.productstocklevel.service;

import com.comcarde.productstocklevel.exception.ProductNotFoundException;
import com.comcarde.productstocklevel.model.CheckStockResponse;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.model.ProductToBeOrderd;
import com.comcarde.productstocklevel.repository.ProductRepository;
import com.comcarde.productstocklevel.repository.ProductRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRulesRepository productRulesRepository;

//    public ProductService(ProductRepository productRepository, ProductRulesRepository productRulesRepository) {
//        this.productRepository = productRepository;
//        this.productRulesRepository = productRulesRepository;
//    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductByName(String productName) throws ProductNotFoundException {

        Optional<Product> product = productRepository.findById(productName);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException(String.format("No product found with product name: %s", productName));
        }
    }

    public CheckStockResponse prepareStockCheckResponse() {
        List<Product> productList = productRepository.findAll();
        List<ProductRules> productRulesList = productRulesRepository.findAll();
        CheckStockResponse response = new CheckStockResponse();
        response.setToBeOrderdList(new ArrayList<>());

        for (Product product: productList) {
            for(ProductRules productRules: productRulesList) {
                if(product.getProductName().equalsIgnoreCase(productRules.getProductName())) {
                    applyRules(product, productRules, response);
                }
            }
        }
        return response;
    }

    private void applyRules(Product product, ProductRules productRules, CheckStockResponse response) {
        int quantityToOrder = 0;
        if (product.getCurrentStock() < productRules.getMinimumStockLevel() && !productRules.isProductBlocked()) {
            quantityToOrder = productRules.getMinimumStockLevel() - product.getCurrentStock();
        }
        if(productRules.getAdditionalVolume() > 0) {
            quantityToOrder += productRules.getAdditionalVolume();
        }
        if(quantityToOrder > 0) {
            response.addToOrderList(new ProductToBeOrderd(product.getProductName(), quantityToOrder));
        }
    }
}
