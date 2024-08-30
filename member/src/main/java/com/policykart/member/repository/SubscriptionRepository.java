package com.policykart.member.repository;

import com.policykart.member.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends AbstractRepository<Subscription, String> {

    Optional<Subscription> findByMemberIdAndPolicyIdAndActiveTrue(String memberId, String policyId);

    Optional<List<Subscription>> findByActiveTrue();
}
