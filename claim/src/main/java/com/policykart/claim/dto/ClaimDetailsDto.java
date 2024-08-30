package com.policykart.claim.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClaimDetailsDto {

    private String providerId;
    private List<PolicyBenefitDto> benefitsAvailed;
    private Long totalClaimAmount;
    private Long totalBilledAmount;
}
