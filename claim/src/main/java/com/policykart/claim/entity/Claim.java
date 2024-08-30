package com.policykart.claim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "claim")
@Entity(name = "claim")
public class Claim extends AbstractEntity {

    @Column(name = "policy_id", nullable = false, columnDefinition = "varchar(32)")
    private String policyId;

    @Column(name = "member_id", nullable = false, columnDefinition = "varchar(32)")
    private String memberId;

    @Column(name = "claim_details", columnDefinition = "json")
    private String claimDetails;

    @Column(name = "status", columnDefinition = "json")
    private String status;
}
