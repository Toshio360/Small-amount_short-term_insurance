package com.example.toshio.demo.dto;

import lombok.Data;

@Data
public class ApplicationRequest {
    private String productId;
    private String planId;
    private PolicyHolder policyHolder;
    private InsuredPerson insured;
    private Boolean eligible;
}
