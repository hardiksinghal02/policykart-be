package com.policykart.auth.service;

import com.policykart.auth.dto.CredentialsDto;
import com.policykart.auth.dto.SignUpDto;
import com.policykart.auth.dto.TokenDto;
import com.policykart.auth.dto.UserDto;

public interface AuthService {
    TokenDto signUpUser(SignUpDto signUpDto);

    TokenDto login(CredentialsDto credentials);

    UserDto getUser(String accessToken);

    String refreshAccessToken(String refreshToken);

    void clearSessions(String accessToken);

    boolean isValidToken(String accessToken);
}
