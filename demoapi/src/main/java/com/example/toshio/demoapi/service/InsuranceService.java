package com.example.toshio.demoapi.service;

import com.example.toshio.demoapi.entity.InsuranceApplicationEntity;
import com.example.toshio.demoapi.entity.OperatorUserEntity;
import com.example.toshio.demoapi.entity.PersonEntity;
import com.example.toshio.demoapi.entity.PlanEntity;
import com.example.toshio.demoapi.entity.ProductEntity;
import com.example.toshio.demoapi.model.ApplicationRequest;
import com.example.toshio.demoapi.model.ApplicationResponse;
import com.example.toshio.demoapi.model.EligibilityRequest;
import com.example.toshio.demoapi.model.EligibilityResponse;
import com.example.toshio.demoapi.model.EstimateRequest;
import com.example.toshio.demoapi.model.EstimateResponse;
import com.example.toshio.demoapi.model.EstimateResponseBreakdown;
import com.example.toshio.demoapi.model.InsuredPerson;
import com.example.toshio.demoapi.model.Person;
import com.example.toshio.demoapi.model.Plan;
import com.example.toshio.demoapi.model.Product;
import com.example.toshio.demoapi.model.User;
import com.example.toshio.demoapi.repository.InsuranceApplicationRepository;
import com.example.toshio.demoapi.repository.OperatorUserRepository;
import com.example.toshio.demoapi.repository.PersonRepository;
import com.example.toshio.demoapi.repository.PlanRepository;
import com.example.toshio.demoapi.repository.ProductPlanRepository;
import com.example.toshio.demoapi.repository.ProductRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    @Autowired
    private PlanRepository planRepository;

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

    public ApplicationResponse applyContract(ApplicationRequest request) {

        // --- ProductEntity を取得 ---
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid productId"));

        // --- PlanEntity を取得 ---
        PlanEntity plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid planId"));

        // --- Person → PersonEntity 変換 ---
        PersonEntity policyHolderEntity = convertPerson(request.getPolicyHolder());
        policyHolderEntity = personRepository.save(policyHolderEntity);

        PersonEntity insuredEntity = convertPerson(request.getInsured());
        insuredEntity = personRepository.save(insuredEntity);

        // --- OperatorUser（ログインユーザー）を取得 ---
        OperatorUserEntity operatorUser = operatorUserRepository.findById(request.getOperatorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid operatorUserId"));

        // --- InsuranceApplicationEntity を組み立てる ---
        InsuranceApplicationEntity entity = new InsuranceApplicationEntity();
        entity.setApplicationId(UUID.randomUUID().toString());
        entity.setProduct(product);
        entity.setPlan(plan);
        entity.setPolicyHolder(policyHolderEntity);
        entity.setInsured(insuredEntity);

        // 続柄（例：本人 or 家族）
        entity.setRelationship(
                policyHolderEntity.getName().equals(insuredEntity.getName())
                        ? "本人"
                        : "家族");

        // 保険期間（例：固定値 or 計算）
        entity.setPeriod(10);

        entity.setOperatorUser(operatorUser);

        // --- DB 保存 ---
        InsuranceApplicationEntity saved = insuranceApplicationRepository.save(entity);

        // --- レスポンス返却 ---
        return new ApplicationResponse()
                .applicationId(saved.getApplicationId())
                .status("申込完了");
    }

    private PersonEntity convertPerson(InsuredPerson insured) {
        PersonEntity entity = new PersonEntity();
        entity.setName(insured.getName());
        entity.setBirthDate(insured.getBirthDate());
        entity.setAddress(insured.getAddress());
        return entity;
    }

    private PersonEntity convertPerson(Person policyHolder) {
        PersonEntity entity = new PersonEntity();
        entity.setName(policyHolder.getName());
        entity.setBirthDate(policyHolder.getBirthDate());
        entity.setAddress(policyHolder.getAddress());
        return entity;
    }

    public @Nullable User getUser(String username) {
        var operatorUser = operatorUserRepository.findByUsername(username);
        return new User().userId(operatorUser.getId())
                .name(operatorUser.getDisplayName()).email(operatorUser.getEmail());
    }

}
