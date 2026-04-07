package com.example.toshio.demo;

import com.example.toshio.client.api.DefaultApi;
import com.example.toshio.client.model.EligibilityRequest;
import com.example.toshio.client.model.EstimateRequest;
import com.example.toshio.client.model.Product;
import com.example.toshio.client.model.Plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contract")
public class DemoController {

    @Autowired
    private DefaultApi api; // OpenAPI generated client

    @GetMapping("/product")
    public String selectProduct(Model model) {
        model.addAttribute("products", api.productsGet());
        return "product-select";
    }

    @GetMapping("/plan")
    public String selectPlan(@RequestParam String productId, Model model) {
        model.addAttribute("productId", productId);
        model.addAttribute("productName",
                api.productsGet().stream().filter(p -> p.getProductId().equals(productId))
                        .findFirst().map(Product::getName).orElse(""));
        model.addAttribute("plans", api.productsProductIdPlansGet(productId));
        return "plan-select";
    }

    @PostMapping("/estimate")
    public String estimate(@RequestParam String productId, @RequestParam String planId,
            @RequestParam int age, @RequestParam int period, Model model) {

        var estimateRequest =
                new EstimateRequest().productId(productId).planId(planId).age(age).period(period);

        var result = api.estimatePost(estimateRequest);

        model.addAttribute("productId", productId);
        model.addAttribute("selectedPlanId", planId);
        model.addAttribute("age", age);
        model.addAttribute("period", period);
        model.addAttribute("premium", result.getPremium());
        model.addAttribute("plans", api.productsProductIdPlansGet(productId));
        model.addAttribute("productName",
                api.productsGet().stream().filter(p -> p.getProductId().equals(productId))
                        .findFirst().map(Product::getName).orElse(""));

        return "plan-select";
    }

    @GetMapping("/eligibility")
    public String eligibilityForm(@RequestParam String productId, @RequestParam String planId,
            @RequestParam int age, Model model) {

        model.addAttribute("productId", productId);
        model.addAttribute("planId", planId);
        model.addAttribute("age", age);

        return "eligibility";
    }

    @PostMapping("/eligibility")
    public String eligibility(@RequestParam String productId, @RequestParam String planId,
            @RequestParam int age, @RequestParam String prefecture,
            @RequestParam boolean hasMedicalHistory, Model model) {

        var eligibilityRequest = new EligibilityRequest().age(age).prefecture(prefecture)
                .hasMedicalHistory(hasMedicalHistory);

        var result = api.eligibilityPost(eligibilityRequest);

        model.addAttribute("eligible", result.getEligible());
        model.addAttribute("reason", result.getReason());

        return "eligibility";
    }
}
