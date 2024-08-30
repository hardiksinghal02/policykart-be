package com.policykart.member.dto;

import com.policykart.member.enums.Benefit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClaimBenefitDto implements Serializable {
    private Benefit benefit;
    private Integer amountPaid;
}
