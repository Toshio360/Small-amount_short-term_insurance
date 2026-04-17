package com.example.toshio.demoapi.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 申込結果
 */

@Entity
@Table(name = "insurance_application")
@Data
public class InsuranceApplicationEntity {

  @Id
  private String applicationId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  @ManyToOne(optional = false)
  @JoinColumn(name = "plan_id")
  private PlanEntity plan;

  @ManyToOne(optional = false)
  @JoinColumn(name = "policy_holder_id")
  private PersonEntity policyHolder;

  @ManyToOne(optional = false)
  @JoinColumn(name = "insured_id")
  private PersonEntity insured;

  @Column(nullable = false)
  private String relationship;

  @Column(nullable = false)
  private Integer period;

  @ManyToOne(optional = false)
  @JoinColumn(name = "operator_user_id")
  private OperatorUserEntity operatorUser;
}
