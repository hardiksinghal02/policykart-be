package com.policykart.member.utils;

import com.policykart.member.dto.PolicyBenefitDto;
import com.policykart.member.dto.SubscribeDto;
import com.policykart.member.entity.Subscription;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class ConvertUtils {


    public Subscription toMemberPolicyMapping(SubscribeDto subscribeDto) {
        return Subscription.builder()
                .id(subscribeDto.getPolicyId())
                .memberId(subscribeDto.getUserId())
                .policyId(subscribeDto.getPolicyId())
                .policyNumber(subscribeDto.getPolicyNumber())
                .build();
    }

    public SubscribeDto toSubscriptionDto(Subscription memberPolicyMapping) {
        return SubscribeDto.builder()
                .policyNumber(memberPolicyMapping.getPolicyNumber())
                .userId(memberPolicyMapping.getMemberId())
                .policyId(memberPolicyMapping.getPolicyId())
                .build();
    }

    public <T> List<PolicyBenefitDto> convertToPolicyBenefitsDto(T object) {
        List benefitList = ObjectMapperUtils.convert(object, List.class);

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
}
