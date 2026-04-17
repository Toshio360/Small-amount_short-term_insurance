package com.example.toshio.demoapi.repository;

import com.example.toshio.demoapi.entity.InsuranceApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceApplicationRepository
        extends JpaRepository<InsuranceApplicationEntity, String> {
}
