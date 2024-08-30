package com.policykart.policy.repository;

import com.policykart.policy.entity.PolicyProviderMapping;

import java.util.List;
import java.util.Optional;

public interface PolicyProviderMappingRepository extends AbstractRepository<PolicyProviderMapping, String> {
    Optional<List<PolicyProviderMapping>> findByPolicyIdAndDeletedFalse(String policyId);
}
