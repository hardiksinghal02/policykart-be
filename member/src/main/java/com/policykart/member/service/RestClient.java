package com.policykart.member.service;

import com.policykart.member.dto.ClaimStatusDto;
import com.policykart.member.dto.PolicyBenefitDto;
import com.policykart.member.dto.PolicyDto;
import com.policykart.member.dto.SubmitClaimDto;
import com.policykart.member.dto.UserDto;
import com.policykart.member.enums.Benefit;

import java.util.List;
import java.util.Map;

public interface RestClient {

    UserDto getUser(String accessToken);

    Map<String, Object> submitClaim(SubmitClaimDto claimObject);

    PolicyDto getPolicyByPolicyId(String policyId);

    ClaimStatusDto getClaimStatus(String memberId, String claimId);

    PolicyBenefitDto getEligibleClaimAmount(String policyId, Benefit benefit);

    List<PolicyBenefitDto> getEligibleBenefits(String policyId);
}
