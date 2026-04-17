package com.example.toshio.demoapi.repository;

import com.example.toshio.demoapi.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {
}
