package com.example.toshio.demo.controller;

import com.example.toshio.client.api.DefaultApi;
import com.example.toshio.client.model.EligibilityRequest;
import com.example.toshio.client.model.EstimateRequest;
import com.example.toshio.client.model.Product;
import com.example.toshio.demo.dto.PolicyHolder;
import com.example.toshio.demo.dto.ApplicationRequest;
import com.example.toshio.demo.dto.InsuredPerson;
import com.example.toshio.demo.enums.ApplicationSteps;
import com.example.toshio.demo.security.CustomOidcUser;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/contract")
@SessionAttributes({"policyHolder", "insured", "products", "productId", "planId"})
public class InsuranceController {
    // -----------------------------
    // セッションに保持する初期値
    // -----------------------------
    @ModelAttribute("productId")
    public String productId() {
        return null;
    }

    @ModelAttribute("planId")
    public String planId() {
        return null;
    }

    @ModelAttribute("policyHolder")
    public PolicyHolder policyHolder() {
        return new PolicyHolder();
    }

    @ModelAttribute("insured")
    public InsuredPerson insured() {
        return new InsuredPerson();
    }

    @Autowired
    private DefaultApi api; // OpenAPI generated client

    @GetMapping("/policyholder")
    public String policyHolder(Model model,@AuthenticationPrincipal Object user) {
        log.info("GET/policyholder - User: " + user.toString());
        if(user instanceof CustomOidcUser) {
            var customUser = (CustomOidcUser) user;
            log.info("DB User: " + customUser.getOpUser().toString());
        }else {
            log.warn("Unexpected user type: " + user.getClass().getName());
        }
        model.addAttribute("step", ApplicationSteps.POLICYHOLDER);
        model.addAttribute("steps", ApplicationSteps.values());
        return "policyholder-form";
    }

    @PostMapping("/policyholder")
    public String postPolicyHolder(@ModelAttribute("policyHolder") PolicyHolder policyHolder,
            Model model,@AuthenticationPrincipal Object user) {
        model.addAttribute("step", ApplicationSteps.INSURED);
        model.addAttribute("steps", ApplicationSteps.values());
        return "insured-form";
    }

    @PostMapping("/insured")
    public String postInsured(@ModelAttribute("insured") InsuredPerson insuredPerson, Model model) {
        model.addAttribute("products", api.productsGet());
        model.addAttribute("step", ApplicationSteps.PRODUCT);
        model.addAttribute("steps", ApplicationSteps.values());
        return "product-select";
    }

    @GetMapping("/product")
    public String selectProduct(Model model) {
        model.addAttribute("products", api.productsGet());
        model.addAttribute("step", ApplicationSteps.PRODUCT);
        model.addAttribute("steps", ApplicationSteps.values());
        return "product-select";
    }

    @GetMapping("/plan")
    public String selectPlan(@RequestParam(name = "productId") String productId,
            @ModelAttribute(name = "productId") String selectedProductId,
            @ModelAttribute("policyHolder") PolicyHolder policyHolder, Model model) {

        // ★ それでも無ければ商品選択に戻す
        if (Strings.isBlank(productId) && Strings.isBlank(selectedProductId))
            return "redirect:/contract/product";
        if (Strings.isNotBlank(productId) && !productId.equals(selectedProductId))
            model.addAttribute("productId", productId);

        model.addAttribute("productName",
                api.productsGet().stream().filter(p -> p.getProductId().equals(productId))
                        .findFirst().map(Product::getName).orElse(""));
        model.addAttribute("plans", api.productsProductIdPlansGet(productId));
        model.addAttribute("age",
                policyHolder.getBirthDate() != null
                        ? policyHolder.getBirthDate().until(java.time.LocalDate.now()).getYears()
                        : null);
        model.addAttribute("step", ApplicationSteps.PLAN);
        model.addAttribute("steps", ApplicationSteps.values());
        return "plan-select";
    }

    @GetMapping("/estimate")
    public String estimate(@ModelAttribute("productId") String productId,
            @RequestParam String planId, @ModelAttribute("planId") String sessionPlanId,
            @RequestParam int age, @RequestParam int period, Model model) {

        if (productId == null)
            return "redirect:/contract/product";
        if (planId == null && sessionPlanId == null)
            return "redirect:/contract/plan";
        if (Strings.isNotBlank(planId) && !planId.equals(sessionPlanId))
            model.addAttribute("planId", planId);

        var estimateRequest = new EstimateRequest().productId(productId)
                .planId(planId != null ? planId : sessionPlanId).age(age).period(period);

        var result = api.estimatePost(estimateRequest);

        model.addAttribute("premium", result.getPremium());
        model.addAttribute("age", age);
        model.addAttribute("period", period);

        model.addAttribute("plans", api.productsProductIdPlansGet(productId));
        model.addAttribute("productName",
                api.productsGet().stream().filter(p -> p.getProductId().equals(productId))
                        .findFirst().map(Product::getName).orElse(""));
        model.addAttribute("planId", Strings.isNotBlank(planId) ? planId : sessionPlanId);

        model.addAttribute("step", ApplicationSteps.PLAN);
        model.addAttribute("steps", ApplicationSteps.values());

        return "plan-select";
    }

    @GetMapping("/eligibility/check")
    public String eligibilityForm(@RequestParam(required = false) Integer age,
            @ModelAttribute("productId") String productId, @ModelAttribute("planId") String planId,
            @ModelAttribute("policyHolder") PolicyHolder policyHolder, Model model) {
        // ★ productId と planId が無ければ商品選択に戻す
        if (productId == null) {
            return "redirect:/contract/product";
        }
        if (planId == null) {
            return "redirect:/contract/plan?productId=" + productId;
        }

        model.addAttribute("age",
                policyHolder.getBirthDate() != null
                        ? policyHolder.getBirthDate().until(java.time.LocalDate.now()).getYears()
                        : null);
        api.productsGet().stream().filter(p -> p.getProductId().equals(productId)).findFirst()
                .map(Product::getName).orElse("");

        model.addAttribute("step", ApplicationSteps.ELIGIBILITY);
        model.addAttribute("steps", ApplicationSteps.values());
        return "eligibility";
    }

    @PostMapping("/eligibility/result")
    public String eligibility(@RequestParam int age, @RequestParam String prefecture,
            @RequestParam boolean hasMedicalHistory, @ModelAttribute("productId") String productId,
            @ModelAttribute("planId") String planId,
            @ModelAttribute("applicationRequest") ApplicationRequest applicationRequest,
            @ModelAttribute("policyHolder") PolicyHolder policyHolder,
            @ModelAttribute("insured") InsuredPerson insured, Model model) {

        var eligibilityRequest = new EligibilityRequest().age(age).prefecture(prefecture)
                .hasMedicalHistory(hasMedicalHistory);

        var result = api.eligibilityPost(eligibilityRequest);

        // ★ ApplicationRequest を完成させる
        applicationRequest.setProductId(productId);
        applicationRequest.setPlanId(planId);
        applicationRequest.setEligible(result.getEligible());
        applicationRequest.setPolicyHolder(policyHolder);
        applicationRequest.setInsured(insured);

        model.addAttribute("step", ApplicationSteps.CONFIRM);
        model.addAttribute("steps", ApplicationSteps.values());
        return "eligibility";
    }

    @GetMapping("application-confirm")
    public String getMethodName(
            @ModelAttribute("applicationRequest") ApplicationRequest applicationRequest,
            Model model) {
        if (applicationRequest.getEligible() == null) {
            return "redirect:/contract/eligibility?productId=" + applicationRequest.getProductId()
                    + "&planId=" + applicationRequest.getPlanId() + "&age="
                    + applicationRequest.getInsured().getBirthDate()
                            .until(java.time.LocalDate.now()).getYears();
        }
        model.addAttribute("applicationRequest", applicationRequest);
        model.addAttribute("step", ApplicationSteps.CONFIRM);
        model.addAttribute("steps", ApplicationSteps.values());
        return "application-confirm";
    }

    @PostMapping("application-confirm")
    public String completeApplication(
            @ModelAttribute("applicationRequest") ApplicationRequest applicationRequest,
            Model model) {
        var req = new com.example.toshio.client.model.ApplicationRequest()
                .productId(applicationRequest.getProductId()).planId(applicationRequest.getPlanId())
                .policyHolder(convertPerson(applicationRequest.getPolicyHolder()))
                .insured(convertInsured(applicationRequest.getInsured()));
        var response = api.applicationPost(req);
        model.addAttribute("applicationId", response.getApplicationId());
        model.addAttribute("step", ApplicationSteps.COMPLETE);
        model.addAttribute("steps", ApplicationSteps.values());
        return "application-complete";
    }

    private com.example.toshio.client.model.Person convertPerson(PolicyHolder src) {
        return new com.example.toshio.client.model.Person().name(src.getName())
                .address(src.getAddress()).birthDate(src.getBirthDate()).phone(src.getPhone());
    }

    private com.example.toshio.client.model.InsuredPerson convertInsured(InsuredPerson src) {
        return new com.example.toshio.client.model.InsuredPerson().name(src.getName())
                .address(src.getAddress()).birthDate(src.getBirthDate()).phone(src.getPhone())
                .relationship(com.example.toshio.client.model.InsuredPerson.RelationshipEnum
                        .fromValue(src.getRelationship().getValue()));
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
