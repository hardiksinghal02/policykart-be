package com.policykart.claim.dto;

import com.policykart.claim.enums.Benefit;
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
    private Integer amountPaid;
}
