package com.policykart.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PolicyDto {

    private String policyId;

    private String name;

    private Double premium;

    private Integer cover;

    private Integer tenureInYears;

    private List<PolicyBenefitDto> benefits;
}
