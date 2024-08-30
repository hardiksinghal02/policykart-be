package com.policykart.policy.repository;

import com.policykart.policy.entity.Policy;

import java.util.Optional;

public interface PolicyRepository extends AbstractRepository<Policy, String> {
    Optional<Policy> findByIdAndDeletedFalse(String policyId);
}
