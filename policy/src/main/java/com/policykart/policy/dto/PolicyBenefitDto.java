package com.policykart.policy.dto;

import com.policykart.policy.enums.Benefit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PolicyBenefitDto implements Serializable {
    private Benefit benefit;
    private String description;
    private Integer maxAmount;
}