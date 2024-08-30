package com.policykart.claim.controller;

import com.policykart.claim.dto.ClaimStatusDto;
import com.policykart.claim.dto.ResponseDto;
import com.policykart.claim.dto.SubmitClaimDto;
import com.policykart.claim.exception.ClaimServiceException;
import com.policykart.claim.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/claim-service")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping("/getClaimStatus")
    public Object getClaimStatus(@RequestParam String memberId,
                                 @RequestParam String claimId) {
        try {
            ClaimStatusDto resp = claimService.getClaimStatus(memberId, claimId);
            return ResponseDto.success(resp);
        } catch (ClaimServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }

    }

    @PostMapping("/submitClaim")
    public ResponseDto<Map<String, Object>> submitClaim(@RequestBody SubmitClaimDto claimObject) {
        try {
            Map<String, Object> resp = claimService.submitClaim(claimObject);
            return ResponseDto.success(resp);
        } catch (ClaimServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }
}
