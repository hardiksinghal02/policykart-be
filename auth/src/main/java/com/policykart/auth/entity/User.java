package com.policykart.auth.entity;

import com.policykart.auth.enums.Gender;
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
@Table(name = "user")
@Entity(name = "user")
public class User extends AbstractEntity {

    @Column(name = "user_id", nullable = false, columnDefinition = "varchar(32)")
    private String userId;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32)")
    private String name;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(32)")
    private String email;

    @Column(name = "dob", columnDefinition = "date")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "varchar(32)")
    private Gender gender;

    @Column(name = "password_hash", nullable = false, columnDefinition = "varchar(320)")
    private String passwordHash;

    @Column(name = "refresh_tokens", columnDefinition = "json")
    private String refreshTokens;

}
