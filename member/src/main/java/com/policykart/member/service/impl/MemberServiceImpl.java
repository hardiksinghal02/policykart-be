package com.policykart.member.service.impl;

import com.policykart.member.dto.BillDto;
import com.policykart.member.dto.ClaimBenefitDto;
import com.policykart.member.dto.ClaimStatusDto;
import com.policykart.member.dto.PolicyBenefitDto;
import com.policykart.member.dto.PolicyDto;
import com.policykart.member.dto.SubmitClaimDto;
import com.policykart.member.dto.SubscribeDto;
import com.policykart.member.entity.Bill;
import com.policykart.member.entity.Subscription;
import com.policykart.member.enums.Benefit;
import com.policykart.member.enums.PaymentStatus;
import com.policykart.member.exception.MemberServiceException;
import com.policykart.member.exception.error.ErrorType;
import com.policykart.member.repository.BillRepository;
import com.policykart.member.repository.SubscriptionRepository;
import com.policykart.member.service.MemberService;
import com.policykart.member.service.RestClient;
import com.policykart.member.utils.ConvertUtils;
import com.policykart.member.utils.IdGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private RestClient restClient;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Map<String, Object> submitClaim(SubmitClaimDto claimRequest) {
        PolicyDto policy = restClient.getPolicyByPolicyId(claimRequest.getPolicyId());

        if (policy == null) {
            throw new MemberServiceException(ErrorType.POLICY_NOT_FOUND);
        }

        Map<Benefit, PolicyBenefitDto> eligibleBenefits =
                getEligibleBenefits(claimRequest.getPolicyId(), claimRequest.getMemberId());

        Long totalBilledAmount = 0L;
        Long totalClaimAmount = 0L;

        for(ClaimBenefitDto b : claimRequest.getClaimDetails().getBenefitsAvailed()) {
            if(!eligibleBenefits.containsKey(b.getBenefit())) {
                throw new MemberServiceException(ErrorType.NOT_ELIGIBLE,
                        "Not eligible to avail benefit : " + b.getBenefit());
            }

            totalBilledAmount += b.getAmountPaid();
            if (b.getAmountPaid() > eligibleBenefits.get(b.getBenefit()).getMaxAmount()) {
                totalClaimAmount += eligibleBenefits.get(b.getBenefit()).getMaxAmount();
            } else {
                totalClaimAmount += b.getAmountPaid();
            }
        }

        claimRequest.getClaimDetails().setTotalBilledAmount(totalBilledAmount);
        claimRequest.getClaimDetails().setTotalClaimAmount(totalClaimAmount);

        return restClient.submitClaim(claimRequest);
    }

    @Override
    public ClaimStatusDto getClaimStatus(String claimId, String memberId, String policyId) {
        if (!StringUtils.hasText(claimId) ||
                !StringUtils.hasText(memberId) ||
                !StringUtils.hasText(policyId)) {
            throw new MemberServiceException(ErrorType.INVALID_INPUT);
        }

        ClaimStatusDto claimStatus = restClient.getClaimStatus(memberId, claimId);

        if (claimStatus == null) {
            throw new MemberServiceException(ErrorType.CLAIM_NOT_FOUND);
        }

        return claimStatus;
    }

    @Override
    public List<BillDto> getBills(String memberId, String policyId) {
        if (!StringUtils.hasText(memberId) || !StringUtils.hasText(policyId)) {
            throw new MemberServiceException(ErrorType.INVALID_INPUT);
        }

        Optional<Subscription> subOption =
                subscriptionRepository.findByMemberIdAndPolicyIdAndActiveTrue(memberId, policyId);

        if (subOption.isEmpty()) {
            throw new MemberServiceException(ErrorType.SUBSCRIPTION_NOT_FOUND);
        }

        Optional<List<Bill>> dbBillsOptional =
                billRepository.findBySubscriptionIdAndDeletedFalse(subOption.get().getId());

        return dbBillsOptional.map(bills -> bills.stream().map(bill -> BillDto.builder()
                        .billId(bill.getId())
                        .subscriptionId(subOption.get().getId())
                        .policyNumber(subOption.get().getPolicyNumber())
                        .memberId(bill.getMemberId())
                        .generatedOn(bill.getGeneratedOn())
                        .dueDate(bill.getDueDate())
                        .amount(bill.getAmount())
                        .lateFee(bill.getLateFee())
                        .previousOutstandingAmount(bill.getPreviousOutstandingAmount())
                        .status(bill.getPaymentStatus())
                        .build()).sorted().collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public PolicyBenefitDto getEligibleClaimAmount(String policyId, String memberId, Benefit benefit) {
        Optional<Subscription> subscriptionOptional =
                subscriptionRepository.findByMemberIdAndPolicyIdAndActiveTrue(memberId, policyId);

        if (subscriptionOptional.isEmpty()) {
            String errorMessage =
                    String.format("No active policy found for memberId : %s, policyId : %s", memberId, policyId);
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        return restClient.getEligibleClaimAmount(policyId, benefit);
    }

    @Override
    public SubscribeDto subscribePolicy(SubscribeDto subscribeDto) {
        validateNewSubscription(subscribeDto);

        PolicyDto policy = restClient.getPolicyByPolicyId(subscribeDto.getPolicyId());

        if (policy == null) {
            throw new MemberServiceException(ErrorType.POLICY_NOT_FOUND);
        }

        String policyNumber = IdGeneratorUtils.generatePolicyNumber(subscribeDto.getUserId(), subscribeDto.getPolicyId());
        subscribeDto.setPolicyNumber(policyNumber);
        Subscription mapping = ConvertUtils.toMemberPolicyMapping(subscribeDto);
        mapping.setActive(true);
        Subscription savedEntity = subscriptionRepository.save(mapping);

        billRepository.save(
                Bill.builder()
                        .subscriptionId(savedEntity.getId())
                        .memberId(savedEntity.getMemberId())
                        .amount(policy.getPremium() / 12)
                        .previousOutstandingAmount((double) 0)
                        .lateFee((double) 0)
                        .generatedOn(LocalDate.now())
                        .dueDate(LocalDate.now().plusDays(5))
                        .paymentStatus(PaymentStatus.PAID)
                        .build()
        );

        return ConvertUtils.toSubscriptionDto(savedEntity);
    }

    @Override
    public Map<Benefit, PolicyBenefitDto> getEligibleBenefits(String policyId, String memberId) {
        Optional<Subscription> subscriptionOptional =
                subscriptionRepository.findByMemberIdAndPolicyIdAndActiveTrue(memberId, policyId);

        if (subscriptionOptional.isEmpty()) {
            String errorMessage =
                    String.format("No active policy found for memberId : %s, policyId : %s", memberId, policyId);
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        List<PolicyBenefitDto> benefitsList = restClient.getEligibleBenefits(policyId);

        Map<Benefit, PolicyBenefitDto> benefitsMap = new HashMap<>();

        for(PolicyBenefitDto b : benefitsList) {
            benefitsMap.put(b.getBenefit(), b);
        }

        return benefitsMap;
    }

    private void validateNewSubscription(SubscribeDto subscribeDto) {
        if (subscribeDto == null ||
                !StringUtils.hasLength(subscribeDto.getPolicyId()) ||
                !StringUtils.hasLength(subscribeDto.getUserId())) {
            throw new RuntimeException("Invalid input for subscription");
        }
        Optional<Subscription> mapping = subscriptionRepository
                .findByMemberIdAndPolicyIdAndActiveTrue(
                        subscribeDto.getUserId(),
                        subscribeDto.getPolicyId()
                );

        if (mapping.isPresent()) {
            throw new RuntimeException("Subscription already exists");
        }
    }
}
