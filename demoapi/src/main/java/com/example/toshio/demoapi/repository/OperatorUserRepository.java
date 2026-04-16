package com.example.toshio.demoapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.toshio.demoapi.entity.OperatorUser;

@Repository
public interface OperatorUserRepository extends JpaRepository<OperatorUser, Long> {
    OperatorUser findByUsername(String username);
}
