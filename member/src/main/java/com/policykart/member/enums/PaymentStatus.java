package com.policykart.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum PaymentStatus {

    PENDING("Pending"),
    PAID("Paid"),
    OVERDUE("Overdue")

    ;

    private final String name;
}
