package com.example.toshio.demoapi.repository;

import com.example.toshio.demoapi.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanEntity, String> {
}
