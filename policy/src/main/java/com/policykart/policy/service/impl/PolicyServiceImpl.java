package com.policykart.policy.service.impl;

import com.policykart.policy.dto.PolicyBenefitDto;
import com.policykart.policy.dto.PolicyDto;
import com.policykart.policy.dto.PolicyProviderDto;
import com.policykart.policy.dto.ProviderDto;
import com.policykart.policy.entity.Policy;
import com.policykart.policy.entity.PolicyProviderMapping;
import com.policykart.policy.entity.Provider;
import com.policykart.policy.enums.Benefit;
import com.policykart.policy.exception.PolicyServiceException;
import com.policykart.policy.exception.error.ErrorType;
import com.policykart.policy.repository.PolicyProviderMappingRepository;
import com.policykart.policy.repository.PolicyRepository;
import com.policykart.policy.repository.ProviderRepository;
import com.policykart.policy.service.PolicyService;
import com.policykart.policy.utils.ConvertUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PolicyServiceImpl implements PolicyService {

    private final Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private PolicyProviderMappingRepository policyProviderMappingRepository;
    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public List<ProviderDto> getChainOfProviders(@NonNull String policyId) {

        Optional<Policy> policyOptional = policyRepository.findByIdAndDeletedFalse(policyId);

        if (policyOptional.isEmpty()) {
            String errorMessage =
                    String.format("No policy found for policyId : %s", policyId);
            logger.error(errorMessage);
            throw new PolicyServiceException(ErrorType.POLICY_NOT_FOUND, errorMessage);
        }

        Optional<List<PolicyProviderMapping>> policyProviderMappings =
                policyProviderMappingRepository.findByPolicyIdAndDeletedFalse(policyId);

        if (policyProviderMappings.isEmpty() || CollectionUtils.isEmpty(policyProviderMappings.get())) {
            String errorMessage =
                    String.format("No provider found for policyId : %s", policyId);
            logger.error(errorMessage);
            throw new PolicyServiceException(ErrorType.PROVIDER_NOT_FOUND, errorMessage);
        }

        return policyProviderMappings.get().stream()
                .map(mapping -> providerRepository.findByIdAndDeletedFalse(mapping.getProviderId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ConvertUtils::toProviderDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProviderDto onboardProvider(ProviderDto providerDto) {
        try {
            Provider savedEntity = providerRepository.save(ConvertUtils.toProvider(providerDto));
            return ConvertUtils.toProviderDto(savedEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new PolicyServiceException(ErrorType.SOMETHING_WENT_WRONG, e.getMessage());
        }
    }

    @Override
    public PolicyProviderDto registerPolicyProvider(PolicyProviderDto policyProviderDto) {
        registerPolicyProviderValidation(policyProviderDto);

        PolicyProviderMapping savedEntity =
                policyProviderMappingRepository.save(ConvertUtils.convertToPolicyProviderMapping(policyProviderDto));

        return ConvertUtils.convertToPolicyProviderDto(savedEntity);
    }

    @Override
    public ProviderDto getProviderById(String providerId) {
        Optional<Provider> providerOptional = providerRepository.findByIdAndDeletedFalse(providerId);

        if (providerOptional.isEmpty()) {
            String errorMessage = String.format("No provider found for providerId : %s", providerId);
            throw new RuntimeException(errorMessage);
        }
        return ConvertUtils.toProviderDto(providerOptional.get());
    }

    @Override
    public PolicyDto createPolicy(PolicyDto policyDto) {
        Policy policy = ConvertUtils.toPolicy(policyDto);
        Policy savedPolicy = policyRepository.save(policy);
        return ConvertUtils.toPolicyDto(savedPolicy);
    }

    @Override
    public PolicyDto getPolicyByPolicyId(String policyId) {
        Optional<Policy> policyOptional = policyRepository.findByIdAndDeletedFalse(policyId);

        if (policyOptional.isEmpty()) {
            throw new PolicyServiceException(ErrorType.POLICY_NOT_FOUND);
        }

        return ConvertUtils.toPolicyDto(policyOptional.get());
    }

    @Override
    public List<PolicyBenefitDto> getEligibleBenefits(String policyId) {
        Optional<Policy> policy = policyRepository.findById(policyId);

        if (policy.isEmpty()) {
            String errorMessage = String.format("No policy found for policyId : %s", policyId);
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        return ConvertUtils.convertToPolicyBenefitsDto(policy.get().getBenefits());
    }

    @Override
    public PolicyBenefitDto getEligibleClaimAmount(String policyId, Benefit benefit) {
        List<PolicyBenefitDto> benefits = getEligibleBenefits(policyId);

        if (CollectionUtils.isEmpty(benefits)) {
            String errorMessage = String.format("No benefits found for policyId : %s", policyId);
            throw new RuntimeException(errorMessage);
        }

        PolicyBenefitDto resp;

        for (PolicyBenefitDto b : benefits) {
            if (b.getBenefit().equals(benefit)) {
                resp = b;
            }
        }

        List<PolicyBenefitDto> filtered = benefits.stream().filter(b -> b.getBenefit().equals(benefit)).toList();

        if (CollectionUtils.isEmpty(filtered)) {
            String errorMessage =
                    String.format("Benefit %s doesn't exist for policyId : %s", benefit.getTitle(), policyId);
            throw new RuntimeException(errorMessage);
        }

        return filtered.get(0);
    }

    private void registerPolicyProviderValidation(PolicyProviderDto policyProviderDto) {
        Optional<Policy> policyOptional = policyRepository.findByIdAndDeletedFalse(policyProviderDto.getPolicyId());

        if (policyOptional.isEmpty()) {
            String errorMessage = String.format("No policy found for policyId : %s", policyProviderDto.getPolicyId());
            throw new PolicyServiceException(ErrorType.POLICY_NOT_FOUND, errorMessage);
        }

        Optional<Provider> providerOptional =
                providerRepository.findByIdAndDeletedFalse(policyProviderDto.getProviderId());

        if (providerOptional.isEmpty()) {
            String errorMessage = String.format("No provider found for providerId : %s", policyProviderDto.getProviderId());
            throw new PolicyServiceException(ErrorType.PROVIDER_NOT_FOUND, errorMessage);
        }
    }
}
