package com.policykart.policy.repository;

import com.policykart.policy.entity.Provider;

import java.util.Optional;

public interface ProviderRepository extends AbstractRepository<Provider, String> {
    Optional<Provider> findByIdAndDeletedFalse(String providerId);
}
