package com.policykart.policy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Benefit {
    ROOM_RENT("Room Rent"),
    E_CONSULTATION("E-consultation"),
    PRE_HOSPITALIZATION_COVERAGE("Pre-hospitalization coverage"),
    POST_HOSPITALIZATION_COVERAGE("Post-hospitalization coverage"),
    AMBULANCE_CHARGES("Ambulance charges"),
    FREE_HEALTH_CHECKUP("Free health checkup"),
    CO_PAY("Co-pay");

    private final String title;
}
