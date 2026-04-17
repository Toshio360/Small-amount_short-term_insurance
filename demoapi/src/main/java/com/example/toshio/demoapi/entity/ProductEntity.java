package com.example.toshio.demoapi.entity;

import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

/**
 * 少額短期保険商品
 */

@Entity
@Table(name = "product")
@Data
public class ProductEntity {
  @Id
  private String productId;
  @OneToMany(mappedBy = "product")
  private Set<ProductPlanEntity> productPlans;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private String description;
}
