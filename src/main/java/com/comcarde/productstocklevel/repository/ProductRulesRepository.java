package com.comcarde.productstocklevel.repository;

import com.comcarde.productstocklevel.model.ProductRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRulesRepository extends JpaRepository<ProductRules, String> {
}
