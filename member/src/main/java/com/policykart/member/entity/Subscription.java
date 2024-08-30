package com.policykart.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "subscription")
@Entity(name = "subscription")
public class Subscription extends AbstractEntity {

    @Column(name = "member_id", nullable = false, columnDefinition = "varchar(32)")
    private String memberId;

    @Column(name = "policy_id", nullable = false, columnDefinition = "varchar(100)")
    private String policyId;

    @Column(name = "policy_number", nullable = false, columnDefinition = "varchar(32)")
    private String policyNumber;

    @Column(name = "subscription_start_time", nullable = false, columnDefinition = "bigint")
    private LocalDate subscriptionStartTime;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private boolean active;

    @PrePersist
    public void prePersistSubscription() {
        this.setSubscriptionStartTime(LocalDate.now());
    }
}
