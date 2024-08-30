package com.policykart.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PolicyProviderDto {
    private String id;
    private String policyId;
    private String providerId;
}
