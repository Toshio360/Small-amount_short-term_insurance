package com.example.toshio.demoapi.service;

import com.example.toshio.demoapi.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {

    public EligibilityResponse checkEligibility(EligibilityRequest req) {
        EligibilityResponse res = new EligibilityResponse();
        res.setEligible(true);
        res.setReason("申込要件を満たしています");
        return res;
    }

    public EstimateResponse estimatePremium(EstimateRequest req) {
        EstimateResponse res = new EstimateResponse();
        res.setPremium(60000);

        EstimateResponseBreakdown breakdown = new EstimateResponseBreakdown();
        breakdown.setBase(60000);
        breakdown.setDiscount(0);
        breakdown.setTotal(60000);

        res.setBreakdown(breakdown);
        return res;
    }

    public List<Product> getProducts() {
        Product p = new Product();
        p.setProductId("product_001");
        p.setName("医療保障プラス");
        p.setDescription("入院・通院をカバーする少額短期保険（最長2年）");
        return List.of(p);
    }

    public List<Plan> getPlans(String productId) {
        Plan plan = new Plan();
        plan.setPlanId("plan_basic");
        plan.setName("基本プラン");
        plan.setCoverageAmount(1_000_000);
        plan.setBasePremium(5000);
        return List.of(plan);
    }
}
