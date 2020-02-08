package com.comcarde.productstocklevel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.comcarde.productstocklevel.model.StockCheckAdvice;

@Repository
public interface ProductOrderAdviceRepository extends JpaRepository<StockCheckAdvice, String> {
}
