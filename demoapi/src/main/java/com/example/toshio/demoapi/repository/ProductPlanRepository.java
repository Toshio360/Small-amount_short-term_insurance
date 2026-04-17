package com.example.toshio.demoapi.repository;

import com.example.toshio.demoapi.entity.ProductPlanEntity;
import com.example.toshio.demoapi.entity.ProductPlanId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPlanRepository extends JpaRepository<ProductPlanEntity, ProductPlanId> {

    List<ProductPlanEntity> findByProduct_ProductId(String productId);
}
