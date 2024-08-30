package com.policykart.auth.utils;

import com.policykart.auth.dto.SignUpDto;
import com.policykart.auth.dto.UserDto;
import com.policykart.auth.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class ConvertUtils {

    public User convertToUser(SignUpDto signUpDto) {
        return User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .passwordHash(HashUtils.hashPassword(signUpDto.getPassword()))
                .dob(signUpDto.getDob())
                .gender(signUpDto.getGender())
                .build();
    }

    public UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .uid(user.getUserId())
                .name(user.getName())
                .dob(user.getDob())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }

    public Set<String> convertToStringSet(String s) {
        Set set = ObjectMapperUtils.convertToObject(s, Set.class);
        Set<String> items = new HashSet<>();

        if (CollectionUtils.isEmpty(set)) {
            return items;
        }

        for (Object item : set) {
            items.add((String) item);
        }

        return items;
    }
}
