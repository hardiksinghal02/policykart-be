package com.policykart.member.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ClaimStatus {

    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    PARTIALLY_ACCEPTED("Partially accepted"),
    IN_REVIEW("In review"),
    SUBMITTED("Submitted")

    ;

    private final String name;
}
