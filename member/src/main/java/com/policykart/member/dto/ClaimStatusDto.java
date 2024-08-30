package com.policykart.member.dto;

import com.policykart.member.enums.ClaimStatus;
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
