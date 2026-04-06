package com.example.toshio.demoapi.controller;

import com.example.toshio.demoapi.api.DefaultApi;
import com.example.toshio.demoapi.model.*;
import com.example.toshio.demoapi.service.InsuranceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DefaultApiController implements DefaultApi {

    private final InsuranceService insuranceService;

    public DefaultApiController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @Override
    public ResponseEntity<EligibilityResponse> eligibilityPost(EligibilityRequest request) {
        return ResponseEntity.ok(insuranceService.checkEligibility(request));
    }

    @Override
    public ResponseEntity<EstimateResponse> estimatePost(EstimateRequest request) {
        return ResponseEntity.ok(insuranceService.estimatePremium(request));
    }

    @Override
    public ResponseEntity<List<Product>> productsGet() {
        return ResponseEntity.ok(insuranceService.getProducts());
    }

    @Override
    public ResponseEntity<List<Plan>> productsProductIdPlansGet(String productId) {
        return ResponseEntity.ok(insuranceService.getPlans(productId));
    }
}