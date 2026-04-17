package com.example.toshio.demoapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.toshio.demoapi.entity.OperatorUserEntity;

@Repository
public interface OperatorUserRepository extends JpaRepository<OperatorUserEntity, Long> {
    OperatorUserEntity findByUsername(String username);
}
