package com.policykart.policy.utils;

import com.policykart.policy.dto.PolicyBenefitDto;
import com.policykart.policy.dto.PolicyDto;
import com.policykart.policy.dto.PolicyProviderDto;
import com.policykart.policy.dto.ProviderDto;
import com.policykart.policy.entity.Policy;
import com.policykart.policy.entity.PolicyProviderMapping;
import com.policykart.policy.entity.Provider;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class ConvertUtils {

    public Provider toProvider(ProviderDto providerDto) {
        return Provider.builder()
                .id(providerDto.getId())
                .name(providerDto.getName())
                .address(providerDto.getAddress())
                .state(providerDto.getState())
                .city(providerDto.getCity())
                .pinCode(providerDto.getPinCode())
                .coverImageUrl(providerDto.getCoverImageUrl())
                .build();
    }

    public ProviderDto toProviderDto(Provider provider) {
        return ProviderDto.builder()
                .id(provider.getId())
                .name(provider.getName())
                .address(provider.getAddress())
                .pinCode(provider.getPinCode())
                .state(provider.getState())
                .city(provider.getCity())
                .coverImageUrl(provider.getCoverImageUrl())
                .build();
    }

    public Policy toPolicy(PolicyDto policyDto) {
        return Policy.builder()
                .name(policyDto.getName())
                .premium(policyDto.getPremium())
                .cover(policyDto.getCover())
                .tenureInYears(policyDto.getTenureInYears())
                .benefits(ObjectMapperUtils.convertToJsonString(policyDto.getBenefits()))
                .build();
    }

    public PolicyDto toPolicyDto(Policy policy) {
        return PolicyDto.builder()
                .policyId(policy.getId())
                .name(policy.getName())
                .premium(policy.getPremium())
                .cover(policy.getCover())
                .tenureInYears(policy.getTenureInYears())
                .benefits(convertToPolicyBenefitsDto(policy.getBenefits()))
                .build();
    }



    public List<PolicyBenefitDto> convertToPolicyBenefitsDto(String jsonString) {
        List benefitList = ObjectMapperUtils.convertToObject(jsonString, List.class);

        if (CollectionUtils.isEmpty(benefitList)) {
            return Collections.emptyList();
        }

        List<PolicyBenefitDto> result = new ArrayList<>();

        for (Object o : benefitList) {
            PolicyBenefitDto converted = ObjectMapperUtils.convert(o, PolicyBenefitDto.class);
            result.add(converted);
        }
        return result;
    }

    public PolicyProviderMapping convertToPolicyProviderMapping(PolicyProviderDto policyProviderDto) {
        return PolicyProviderMapping.builder()
                .id(policyProviderDto.getId())
                .policyId(policyProviderDto.getPolicyId())
                .providerId(policyProviderDto.getProviderId())
                .build();
    }

    public PolicyProviderDto convertToPolicyProviderDto(PolicyProviderMapping policyProviderMapping) {
        return PolicyProviderDto.builder()
                .id(policyProviderMapping.getId())
                .policyId(policyProviderMapping.getPolicyId())
                .providerId(policyProviderMapping.getProviderId())
                .build();
    }
}
