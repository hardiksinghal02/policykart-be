package com.policykart.claim.dto;

import com.policykart.claim.enums.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ClaimStatusDto {
    private ClaimStatus claimStatus;
    private Long acceptedAmount;
}
