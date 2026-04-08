package com.example.toshio.demoapi.service;

import com.example.toshio.demoapi.model.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplicationService {

    public PolicyHolderResponse registerPolicyHolder(PolicyHolder req) {
        PolicyHolderResponse res = new PolicyHolderResponse();
        res.setPolicyHolderId("ph_" + UUID.randomUUID());
        res.setStatus("registered");
        return res;
    }

    public InsuredPersonResponse registerInsuredPerson(InsuredPerson req) {
        InsuredPersonResponse res = new InsuredPersonResponse();
        res.setInsuredId("ins_" + UUID.randomUUID());
        res.setStatus("registered");
        return res;
    }

    public ApplicationResponse applyContract(ApplicationRequest req) {
        ApplicationResponse res = new ApplicationResponse();
        res.setApplicationId("app_" + UUID.randomUUID());
        res.setStatus("accepted");
        return res;
    }
}