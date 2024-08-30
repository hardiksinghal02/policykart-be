package com.policykart.policy.service;

import com.policykart.policy.dto.PolicyBenefitDto;
import com.policykart.policy.dto.PolicyDto;
import com.policykart.policy.dto.PolicyProviderDto;
import com.policykart.policy.dto.ProviderDto;
import com.policykart.policy.enums.Benefit;

import java.util.List;

public interface PolicyService {
    List<ProviderDto> getChainOfProviders(String policyId);

    ProviderDto onboardProvider(ProviderDto providerDto);

    PolicyProviderDto registerPolicyProvider(PolicyProviderDto policyProviderDto);

    ProviderDto getProviderById(String providerId);

    PolicyDto createPolicy(PolicyDto policyDto);

    PolicyDto getPolicyByPolicyId(String policyId);

    List<PolicyBenefitDto> getEligibleBenefits(String policyId);

    PolicyBenefitDto getEligibleClaimAmount(String policyId, Benefit benefit);
}
