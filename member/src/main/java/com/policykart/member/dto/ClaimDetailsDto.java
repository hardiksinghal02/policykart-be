package com.policykart.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClaimDetailsDto {

    private String providerId;
    private List<ClaimBenefitDto> benefitsAvailed;
    private Long totalClaimAmount;
    private Long totalBilledAmount;
}
