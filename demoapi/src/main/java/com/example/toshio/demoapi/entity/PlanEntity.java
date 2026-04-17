package com.example.toshio.demoapi.entity;

import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

/**
 * プラン
 */
@Entity
@Table(name = "plan")
@Data
public class PlanEntity {
  @Id
  private String planId;
  @OneToMany(mappedBy = "plan")
  private Set<ProductPlanEntity> productPlans;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private Integer coverageAmount;
  @Column(nullable = false)
  private Integer basePremium;
}
