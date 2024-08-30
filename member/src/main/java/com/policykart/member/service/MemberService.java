package com.policykart.member.service;

import com.policykart.member.dto.BillDto;
import com.policykart.member.dto.ClaimStatusDto;
import com.policykart.member.dto.PolicyBenefitDto;
import com.policykart.member.dto.SubmitClaimDto;
import com.policykart.member.dto.SubscribeDto;
import com.policykart.member.enums.Benefit;

import java.util.List;
import java.util.Map;

public interface MemberService {

    Map<String, Object> submitClaim(SubmitClaimDto claimRequest);

    ClaimStatusDto getClaimStatus(String claimId, String memberId, String policyId);

    List<BillDto> getBills(String memberId, String policyId);

    PolicyBenefitDto getEligibleClaimAmount(String policyId, String memberId, Benefit benefit);

    SubscribeDto subscribePolicy(SubscribeDto subscribeDto);

    Map<Benefit, PolicyBenefitDto> getEligibleBenefits(String policyId, String memberId);

}
