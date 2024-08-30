package com.policykart.member.entity;

import com.policykart.member.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "bill")
@Entity(name = "bill")
public class Bill extends AbstractEntity {

    @Column(name = "subscription_id", nullable = false, columnDefinition = "varchar(32)")
    private String subscriptionId;

    @Column(name = "member_id", nullable = false, columnDefinition = "varchar(32)")
    private String memberId;

    @Column(name = "amount", columnDefinition = "int")
    private Double amount;

    @Column(name = "previous_outstanding_amount", columnDefinition = "int")
    private Double previousOutstandingAmount;

    @Column(name = "late_fee", columnDefinition = "int")
    private Double lateFee;

    @Column(name = "generated_on", columnDefinition = "date")
    private LocalDate generatedOn;

    @Column(name = "due_date", columnDefinition = "date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "varchar(20)")
    private PaymentStatus paymentStatus;

}
