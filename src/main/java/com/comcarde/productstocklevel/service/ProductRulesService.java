package com.comcarde.productstocklevel.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comcarde.productstocklevel.exception.ProductRulesNotFoundException;
import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.repository.ProductRulesRepository;

@Service
public class ProductRulesService {

    @Autowired
    ProductRulesRepository productRulesRepository;

    public List<ProductRules> getAllProductRules() {
        return productRulesRepository.findAll();
    }

    public ProductRules findProductRulesByProductName(String productName) throws ProductRulesNotFoundException {

        Optional<ProductRules> product = productRulesRepository.findById(productName);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductRulesNotFoundException(String.format("No product rules found for product name: %s", productName));
        }
    }

    public ProductRules createOrUpdateProductRules(ProductRules productRules) {
        Optional<ProductRules> optionalProductRules = productRulesRepository.findById(productRules.getProductName());

        if (optionalProductRules.isPresent()) {
            ProductRules newProductRules = optionalProductRules.get();
            newProductRules.setProductName(productRules.getProductName());
            newProductRules.setMinimumStockLevel(productRules.getMinimumStockLevel());
            newProductRules.setProductBlocked(productRules.isProductBlocked());
            newProductRules.setAdditionalVolume(productRules.getAdditionalVolume());
            return productRulesRepository.save(newProductRules);
        } else {
            return productRulesRepository.save(productRules);
        }
    }

    public void deleteProductRules(String productName) throws ProductRulesNotFoundException {
        Optional<ProductRules> product = productRulesRepository.findById(productName);
        if (product.isPresent()) {
            productRulesRepository.deleteById(productName);
        } else {
            throw new ProductRulesNotFoundException(String.format("No product rules exists for product name: %s", productName));
        }
    }
}
