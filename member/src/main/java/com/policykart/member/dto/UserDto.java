package com.policykart.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    private String uid;
    private String name;
    private Date dob;
    private String gender;
    private String email;
}
