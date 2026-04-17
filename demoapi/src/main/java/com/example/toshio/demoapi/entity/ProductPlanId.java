package com.example.toshio.demoapi.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ProductPlanId implements java.io.Serializable {

    private String productId;
    private String planId;
}
