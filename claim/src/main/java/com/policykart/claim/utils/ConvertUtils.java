package com.policykart.claim.utils;

import com.policykart.claim.dto.ClaimStatusDto;
import com.policykart.claim.dto.SubmitClaimDto;
import com.policykart.claim.entity.Claim;
import com.policykart.claim.enums.ClaimStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConvertUtils {

    public Claim convertToClaim(SubmitClaimDto claimObject) {
        return Claim.builder()
                .policyId(claimObject.getPolicyId())
                .memberId(claimObject.getMemberId())
                .claimDetails(ObjectMapperUtils.convertToJsonString(claimObject.getClaimDetails()))
                .status(ObjectMapperUtils.convertToJsonString(
                        ClaimStatusDto.builder()
                                .claimStatus(ClaimStatus.SUBMITTED)
                                .build()
                ))
                .build();
    }

    public ClaimStatusDto convertToClaimStatus(String statusString) {
        return ObjectMapperUtils.convertToObject(statusString, ClaimStatusDto.class);
    }
}
