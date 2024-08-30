package com.policykart.policy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.net.URL;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "policy_provider_mapping")
@Entity(name = "policy_provider_mapping")
public class PolicyProviderMapping extends AbstractEntity {

    @Column(name = "policy_id", nullable = false, columnDefinition = "varchar(32)")
    private String policyId;

    @Column(name = "provider_id", nullable = false, columnDefinition = "varchar(32)")
    private String providerId;

}
