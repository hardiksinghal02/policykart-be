package com.policykart.policy.controller;

import com.policykart.policy.dto.PolicyBenefitDto;
import com.policykart.policy.dto.PolicyDto;
import com.policykart.policy.dto.PolicyProviderDto;
import com.policykart.policy.dto.ProviderDto;
import com.policykart.policy.dto.ResponseDto;
import com.policykart.policy.enums.Benefit;
import com.policykart.policy.exception.PolicyServiceException;
import com.policykart.policy.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("policy-service")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/getChainOfProviders")
    public ResponseDto<List<ProviderDto>> getChainOfProvider(@RequestParam String policyId) {
        try {
            List<ProviderDto> resp = policyService.getChainOfProviders(policyId);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/onboard/provider")
    public ResponseDto<ProviderDto> onboardProvider(@RequestBody ProviderDto providerDto) {
        try {
            ProviderDto resp = policyService.onboardProvider(providerDto);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/register/policyProvider")
    public ResponseDto<PolicyProviderDto> registerPolicyProvider(@RequestBody PolicyProviderDto providerDto) {
        try {
            PolicyProviderDto resp = policyService.registerPolicyProvider(providerDto);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @GetMapping("/getProviderById")
    public ResponseDto<ProviderDto> getProviderById(@RequestParam String providerId) {
        try {
            ProviderDto resp = policyService.getProviderById(providerId);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/create/policy")
    public ResponseDto<PolicyDto> onboardProvider(@RequestBody PolicyDto policyDto) {
        try {
            PolicyDto resp = policyService.createPolicy(policyDto);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @GetMapping("/getPolicyById")
    public ResponseDto<PolicyDto> getPolicyById(@RequestParam String policyId) {
        try {
            PolicyDto resp = policyService.getPolicyByPolicyId(policyId);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @GetMapping("/getEligibleBenefits")
    public ResponseDto<List<PolicyBenefitDto>> getEligibleBenefits(@RequestParam String policyId) {
        try {
            List<PolicyBenefitDto> resp = policyService.getEligibleBenefits(policyId);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @GetMapping("/getEligibleClaimAmount")
    public ResponseDto<PolicyBenefitDto> getEligibleClaimAmount(@RequestParam String policyId,
                                                                @RequestParam Benefit benefit) {
        try {
            PolicyBenefitDto resp = policyService.getEligibleClaimAmount(policyId, benefit);
            return ResponseDto.success(resp);
        } catch (PolicyServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }
}
