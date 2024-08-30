package com.policykart.member.dto;

import com.policykart.member.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BillDto implements Comparable<BillDto>{
    private String billId;
    private String subscriptionId;
    private String policyNumber;
    private String memberId;
    private LocalDate generatedOn;
    private LocalDate dueDate;
    private Double amount;
    private Double lateFee;
    private Double previousOutstandingAmount;
    private PaymentStatus status;

    @Override
    public int compareTo(BillDto o) {
        return o.getGeneratedOn().compareTo(this.getGeneratedOn());
    }
}
