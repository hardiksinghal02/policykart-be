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
@Table(name = "provider")
@Entity(name = "provider")
public class Provider extends AbstractEntity {

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "address", columnDefinition = "varchar(320)")
    private String address;

    @Column(name = "state", columnDefinition = "varchar(40)")
    private String state;

    @Column(name = "city", columnDefinition = "varchar(40)")
    private String city;

    @Column(name = "pin_code", columnDefinition = "int")
    private Integer pinCode;

    @Column(name = "cover_image_url", columnDefinition = "varchar(320)")
    private URL coverImageUrl;

}
