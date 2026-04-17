package com.example.toshio.demoapi.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

/**
 * 人物情報
 */
@Entity
@Table(name = "person")
@Data
public class PersonEntity {
  @Id
  private String personId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = true)
  private LocalDate birthDate;

  @Column(nullable = true)
  private String phone;
}

