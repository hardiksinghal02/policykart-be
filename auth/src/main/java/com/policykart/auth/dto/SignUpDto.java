package com.policykart.auth.dto;

import com.policykart.auth.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpDto {
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String email;
    private String password;
}
