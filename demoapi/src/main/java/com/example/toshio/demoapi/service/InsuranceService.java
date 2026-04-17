package com.example.toshio.demoapi.service;

import com.example.toshio.demoapi.entity.InsuranceApplicationEntity;
import com.example.toshio.demoapi.entity.OperatorUserEntity;
import com.example.toshio.demoapi.entity.ProductEntity;
import com.example.toshio.demoapi.model.ApplicationRequest;
import com.example.toshio.demoapi.model.ApplicationResponse;
import com.example.toshio.demoapi.model.EligibilityRequest;
import com.example.toshio.demoapi.model.EligibilityResponse;
import com.example.toshio.demoapi.model.EstimateRequest;
import com.example.toshio.demoapi.model.EstimateResponse;
import com.example.toshio.demoapi.model.EstimateResponseBreakdown;
import com.example.toshio.demoapi.model.Plan;
import com.example.toshio.demoapi.model.Product;
import com.example.toshio.demoapi.model.User;
import com.example.toshio.demoapi.repository.InsuranceApplicationRepository;
import com.example.toshio.demoapi.repository.OperatorUserRepository;
import com.example.toshio.demoapi.repository.PersonRepository;
import com.example.toshio.demoapi.repository.ProductPlanRepository;
import com.example.toshio.demoapi.repository.ProductRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {
    @Autowired
    private OperatorUserRepository operatorUserRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPlanRepository productPlanRepository;
    @Autowired
    private InsuranceApplicationRepository insuranceApplicationRepository;

    public EligibilityResponse checkEligibility(EligibilityRequest req) {
        EligibilityResponse res = new EligibilityResponse();
        res.setEligible(true);
        res.setReason("申込要件を満たしています");
        return res;
    }

    public EstimateResponse estimatePremium(EstimateRequest req) {
        var plan = getPlans(req.getProductId()).stream()
                .filter(e -> e.getPlanId().equals(req.getPlanId())).findFirst().orElse(new Plan());
        EstimateResponse res = new EstimateResponse();
        res.setPremium(plan.getBasePremium() * req.getPeriod() + 1000 * req.getAge());

        EstimateResponseBreakdown breakdown = new EstimateResponseBreakdown();
        breakdown.setBase(plan.getBasePremium());
        breakdown.setDiscount(0);
        breakdown.setTotal(60000);

        res.setBreakdown(breakdown);
        return res;
    }

    public List<Product> getProducts() {
        return productRepository.findAll().stream().map(p -> new Product()
                .productId(p.getProductId()).name(p.getName()).description(p.getDescription()))
                .toList();
    }

    public List<Plan> getPlans(String productId) {
        return productPlanRepository.findByProduct_ProductId(productId).stream().map(pp -> {
            var plan = pp.getPlan();
            return new Plan().planId(plan.getPlanId()).name(plan.getName())
                    .coverageAmount(plan.getCoverageAmount()).basePremium(plan.getBasePremium());
        }).toList();
    }

    public @Nullable ApplicationResponse applyContract(ApplicationRequest request) {
        return new ApplicationResponse().applicationId("APP-1234567890").status("ACCEPTED")
                .message("申込を受け付けました");
    }

    public @Nullable User getUser(String username) {
        var operatorUser = operatorUserRepository.findByUsername(username);
        return new User().userId(Long.toString(operatorUser.getId()))
                .name(operatorUser.getDisplayName()).email(operatorUser.getEmail());
    }

}
