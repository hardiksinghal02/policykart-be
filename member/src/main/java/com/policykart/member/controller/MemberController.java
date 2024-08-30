package com.policykart.member.controller;

import com.policykart.member.dto.BillDto;
import com.policykart.member.dto.ClaimStatusDto;
import com.policykart.member.dto.PolicyBenefitDto;
import com.policykart.member.dto.ResponseDto;
import com.policykart.member.dto.SubmitClaimDto;
import com.policykart.member.dto.SubscribeDto;
import com.policykart.member.dto.UserDto;
import com.policykart.member.enums.Benefit;
import com.policykart.member.exception.MemberServiceException;
import com.policykart.member.exception.error.ErrorType;
import com.policykart.member.service.MemberService;
import com.policykart.member.service.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member-service")
public class MemberController {

    @Autowired
    private RestClient restClient;

    @Autowired
    private MemberService memberService;

    @GetMapping("/viewBills")
    public ResponseDto<Object> viewBills(
            @RequestHeader(value = "authorization") String accessToken,
            @RequestParam String memberId,
            @RequestParam String policyId
    ) {
        try {
            validateAuthorization(accessToken, memberId);
            List<BillDto> resp = memberService.getBills(memberId, policyId);
            return ResponseDto.success(resp);
        } catch (MemberServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @GetMapping("/getClaimStatus")
    public ResponseDto<ClaimStatusDto> getClaimStatus(
            @RequestHeader(value = "authorization") String accessToken,
            @RequestParam String claimId,
            @RequestParam String memberId,
            @RequestParam String policyId
    ) {
        try {
            validateAuthorization(accessToken, memberId);
            ClaimStatusDto resp = memberService.getClaimStatus(claimId, memberId, policyId);
            return ResponseDto.success(resp);
        } catch (MemberServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/submitClaim")
    public ResponseDto<Map<String, Object>> submitClaim(
            @RequestHeader(value = "authorization") String accessToken,
            @RequestBody SubmitClaimDto claimRequest
    ) {
        try {
            validateAuthorization(accessToken, claimRequest.getMemberId());
            Map<String, Object> resp = memberService.submitClaim(claimRequest);
            return ResponseDto.success(resp);
        } catch (MemberServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @GetMapping("/getEligibleBenefits")
    public ResponseDto<Map<Benefit, PolicyBenefitDto>> getEligibleBenefits(
            @RequestHeader(value = "authorization") String accessToken,
            @RequestParam String policyId, @RequestParam String memberId) {
        try {
            validateAuthorization(accessToken, memberId);
            Map<Benefit, PolicyBenefitDto> resp = memberService.getEligibleBenefits(policyId, memberId);
            return ResponseDto.success(resp);
        } catch (MemberServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @GetMapping("/getEligibleClaimAmount")
    public ResponseDto<PolicyBenefitDto> getEligibleClaimAmount(
            @RequestHeader(value = "authorization") String accessToken,
            @RequestParam String policyId,
            @RequestParam String memberId,
            @RequestParam Benefit benefit
    ) {
        try {
            validateAuthorization(accessToken, memberId);
            PolicyBenefitDto resp = memberService.getEligibleClaimAmount(policyId, memberId, benefit);
            return ResponseDto.success(resp);
        } catch (MemberServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/subscribe")
    public ResponseDto<SubscribeDto> subscribePolicy(
            @RequestHeader(value = "authorization") String accessToken,
            @RequestBody SubscribeDto subscribeDto) {
        try {
            validateAuthorization(accessToken, subscribeDto.getUserId());
            SubscribeDto resp = memberService.subscribePolicy(subscribeDto);
            return ResponseDto.success(resp);
        } catch (MemberServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    private void validateAuthorization(String accessToken, String memberId) {
        UserDto user = restClient.getUser(accessToken);
        if (!memberId.equals(user.getUid())) {
            throw new MemberServiceException(ErrorType.UNAUTHORIZED);
        }
    }
}
