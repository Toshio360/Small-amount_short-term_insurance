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
        Product p = new Product();
        p.setProductId("product_001");
        p.setName("医療保障プラス");
        p.setDescription("入院・通院をカバーする少額短期保険（最長2年）");
        return List.of(p,
                new Product().productId("product_002").name("損害保険プラス")
                        .description("自転車・バイク事故の損害をカバーする保険"),
                new Product().productId("product_003").name("死亡保険").description("お葬式までをカバーします"));
    }

    public List<Plan> getPlans(String productId) {
        Plan plan = new Plan().planId("plan_basic").name("基本プラン").coverageAmount(1_000_000)
                .basePremium(5000);
        Plan plan2 = new Plan().planId("plan_standard").name("スタンダードプラン").coverageAmount(5_000_000)
                .basePremium(15000);
        Plan plan3 = new Plan().planId("plan_premium").name("プレミアムプラン").coverageAmount(10_000_000)
                .basePremium(30000);
        switch (productId) {
            case "product_001":
                return List.of(plan, plan2, plan3);
            case "product_002":
                return List.of(plan2, plan3);
            case "product_003":
                return List.of(plan3);
            default:
                return List.of();
        }
    }
}
