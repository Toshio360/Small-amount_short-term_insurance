package com.example.toshio.demoapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_plan")
@Data
public class ProductPlanEntity {

    @EmbeddedId
    private ProductPlanId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @MapsId("planId")
    @JoinColumn(name = "plan_id")
    private PlanEntity plan;
}
