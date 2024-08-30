package com.policykart.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class SubmitClaimDto {
    private String policyId;
    private String memberId;
    private ClaimDetailsDto claimDetails;
}
