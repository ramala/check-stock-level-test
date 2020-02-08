package com.comcarde.productstocklevel.service;

import com.comcarde.productstocklevel.model.ProductRules;
import com.comcarde.productstocklevel.repository.ProductRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRulesService {

    @Autowired
    ProductRulesRepository productRulesRepository;

    public List<ProductRules> getAllProductRules() {
        List<ProductRules> productRules = productRulesRepository.findAll();

        return productRules;
    }
}
