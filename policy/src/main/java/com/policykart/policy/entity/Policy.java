package com.policykart.policy.entity;

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
@Table(name = "policy")
@Entity(name = "policy")
public class Policy extends AbstractEntity {

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "premium", nullable = false, columnDefinition = "int")
    private Double premium;

    @Column(name = "cover", nullable = false, columnDefinition = "bigint")
    private Integer cover;

    @Column(name = "tenure_in_years", nullable = false, columnDefinition = "int")
    private Integer tenureInYears;

    @Column(name = "benefits", nullable = false, columnDefinition = "json")
    private String benefits;
}
