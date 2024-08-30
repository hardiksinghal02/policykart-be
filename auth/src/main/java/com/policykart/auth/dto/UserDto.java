package com.policykart.auth.dto;

import com.policykart.auth.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    private String uid;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String email;
}
