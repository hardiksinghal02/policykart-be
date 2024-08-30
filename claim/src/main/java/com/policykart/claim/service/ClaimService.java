package com.policykart.claim.service;

import com.policykart.claim.dto.ClaimStatusDto;
import com.policykart.claim.dto.SubmitClaimDto;

import java.util.Map;

public interface ClaimService {

    Map<String, Object> submitClaim(SubmitClaimDto claimObject);

    ClaimStatusDto getClaimStatus(String memberId, String claimId);
}
