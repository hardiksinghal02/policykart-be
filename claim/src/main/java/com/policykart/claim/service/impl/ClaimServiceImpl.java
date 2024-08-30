package com.policykart.claim.service.impl;

import com.policykart.claim.dto.ClaimStatusDto;
import com.policykart.claim.dto.SubmitClaimDto;
import com.policykart.claim.entity.Claim;
import com.policykart.claim.exception.ClaimServiceException;
import com.policykart.claim.exception.error.ErrorType;
import com.policykart.claim.repository.ClaimRepository;
import com.policykart.claim.service.ClaimService;
import com.policykart.claim.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public Map<String, Object> submitClaim(SubmitClaimDto claimObject) {
        if (claimObject == null ||
                !StringUtils.hasText(claimObject.getPolicyId()) ||
                !StringUtils.hasText(claimObject.getMemberId()) ||
                claimObject.getClaimDetails() == null
        ) {
            throw new ClaimServiceException(ErrorType.INVALID_INPUT);
        }

        Claim claim = claimRepository.save(ConvertUtils.convertToClaim(claimObject));
        Map<String, Object> resp = new HashMap<>();
        resp.put("claimId", claim.getId());
        resp.put("claimStatus", ConvertUtils.convertToClaimStatus(claim.getStatus()));
        return resp;
    }

    @Override
    public ClaimStatusDto getClaimStatus(String memberId, String claimId) {
        Optional<Claim> claimOptional = claimRepository.findById(claimId);

        if (claimOptional.isEmpty()) {
            throw new ClaimServiceException(ErrorType.CLAIM_NOT_FOUND);
        }

        Claim claim = claimOptional.get();

        if(!StringUtils.hasText(memberId) || !memberId.equals(claim.getMemberId())) {
            throw new ClaimServiceException(ErrorType.UNAUTHORIZED);
        }
        return ConvertUtils.convertToClaimStatus(claim.getStatus());
    }
}
