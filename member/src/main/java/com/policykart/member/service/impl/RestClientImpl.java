package com.policykart.member.service.impl;

import com.policykart.member.dto.ClaimStatusDto;
import com.policykart.member.dto.PolicyBenefitDto;
import com.policykart.member.dto.PolicyDto;
import com.policykart.member.dto.ResponseDto;
import com.policykart.member.dto.SubmitClaimDto;
import com.policykart.member.dto.UserDto;
import com.policykart.member.enums.Benefit;
import com.policykart.member.exception.MemberServiceException;
import com.policykart.member.exception.error.ErrorType;
import com.policykart.member.service.RestClient;
import com.policykart.member.utils.ConvertUtils;
import com.policykart.member.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RestClientImpl implements RestClient {

    @Value("${auth.service.domain}")
    private String authServiceDomain;

    @Value("${claim.service.domain}")
    private String claimServiceDomain;

    @Value("${policy.service.domain}")
    private String policyServiceDomain;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDto getUser(String accessToken) {
        String url = authServiceDomain + "/getUser";
        ResponseDto resp = restTemplate.postForObject(url, accessToken, ResponseDto.class);

        if (resp == null || (resp.isSuccess() && resp.getData() == null)) {
            throw new MemberServiceException(ErrorType.USER_NOT_FOUND);
        }

        if (resp.isSuccess()) {
            return ObjectMapperUtils.convert(resp.getData(), UserDto.class);
        }

        throw new MemberServiceException(resp.getError().getCode());
    }

    @Override
    public Map<String, Object> submitClaim(SubmitClaimDto claimObject) {
        String url = claimServiceDomain + "/submitClaim";
        ResponseDto resp = restTemplate.postForObject(url, claimObject, ResponseDto.class);

        if (resp == null || (resp.isSuccess() && resp.getData() == null)) {
            throw new MemberServiceException(ErrorType.SOMETHING_WENT_WRONG);
        }

        if (resp.isSuccess()) {
            return ObjectMapperUtils.convert(resp.getData(), Map.class);
        }

        throw new MemberServiceException(resp.getError().getCode());
    }

    @Override
    public PolicyDto getPolicyByPolicyId(String policyId) {
        String url = String.format(policyServiceDomain + "/getPolicyById?policyId=%s", policyId);
        ResponseDto resp = restTemplate.getForObject(url, ResponseDto.class);

        if (resp == null || (resp.isSuccess() && resp.getData() == null)) {
            throw new MemberServiceException(ErrorType.POLICY_NOT_FOUND);
        }

        if (resp.isSuccess()) {
            return ObjectMapperUtils.convert(resp.getData(), PolicyDto.class);
        }

        throw new MemberServiceException(resp.getError().getCode());
    }

    @Override
    public ClaimStatusDto getClaimStatus(String memberId, String claimId) {
        String url = String.format(claimServiceDomain + "/getClaimStatus?claimId=%s&&memberId=%s", claimId, memberId);
        ResponseDto resp = restTemplate.getForObject(url, ResponseDto.class);

        if (resp == null || (resp.isSuccess() && resp.getData() == null)) {
            throw new MemberServiceException(ErrorType.CLAIM_NOT_FOUND);
        }

        if (resp.isSuccess()) {
            return ObjectMapperUtils.convert(resp.getData(), ClaimStatusDto.class);
        }

        throw new MemberServiceException(resp.getError().getCode());
    }

    @Override
    public PolicyBenefitDto getEligibleClaimAmount(String policyId, Benefit benefit) {
        String url = policyServiceDomain + "/getEligibleClaimAmount?policyId=" + policyId + "&benefit=" + benefit;
        ResponseDto resp = restTemplate.getForObject(url, ResponseDto.class);

        if (resp == null || (resp.isSuccess() && resp.getData() == null)) {
            throw new MemberServiceException(ErrorType.POLICY_NOT_FOUND);
        }

        if (resp.isSuccess()) {
            return ObjectMapperUtils.convert(resp.getData(), PolicyBenefitDto.class);
        }

        throw new MemberServiceException(resp.getError().getCode());
    }

    @Override
    public List<PolicyBenefitDto> getEligibleBenefits(String policyId) {
        String url = policyServiceDomain + "/getEligibleBenefits?policyId=" + policyId;
        ResponseDto resp = restTemplate.getForObject(url, ResponseDto.class);

        if (resp == null || (resp.isSuccess() && resp.getData() == null)) {
            throw new MemberServiceException(ErrorType.POLICY_NOT_FOUND);
        }

        if (resp.isSuccess()) {
            return ConvertUtils.convertToPolicyBenefitsDto(resp.getData());
        }

        throw new MemberServiceException(resp.getError().getCode());
    }
}
