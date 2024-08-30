package com.policykart.auth.controller;

import com.policykart.auth.dto.CredentialsDto;
import com.policykart.auth.dto.ResponseDto;
import com.policykart.auth.dto.SignUpDto;
import com.policykart.auth.dto.TokenDto;
import com.policykart.auth.dto.UserDto;
import com.policykart.auth.exception.AuthServiceException;
import com.policykart.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-service")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseDto<TokenDto> signupUser(@RequestBody SignUpDto signUpDto) {
        try {
            TokenDto resp = authService.signUpUser(signUpDto);
            return ResponseDto.success(resp);
        } catch (AuthServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/login")
    public ResponseDto<TokenDto> login(@RequestBody CredentialsDto credentials) {
        try {
            TokenDto resp = authService.login(credentials);
            return ResponseDto.success(resp);
        } catch (AuthServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/refresh/token")
    public ResponseDto<String> refreshToken(@RequestBody String refreshToken) {
        try {
            String resp = authService.refreshAccessToken(refreshToken);
            return ResponseDto.success(resp);
        } catch (AuthServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/getUser")
    public ResponseDto<UserDto> getUser(@RequestBody String accessToken) {
        try {
            UserDto resp = authService.getUser(accessToken);
            return ResponseDto.success(resp);
        } catch (AuthServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/clearSession")
    public ResponseDto<Void> clearSessions(@RequestBody String accessToken) {
        try {
            authService.clearSessions(accessToken);
            return ResponseDto.success(null);
        } catch (AuthServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }

    @PostMapping("/isValidToken")
    public ResponseDto<Boolean> isValidToken(@RequestBody String accessToken) {
        try {
            boolean resp = authService.isValidToken(accessToken);
            return ResponseDto.success(resp);
        } catch (AuthServiceException e) {
            return ResponseDto.failure(e.getErrorType());
        } catch (Exception e) {
            return ResponseDto.failure(e);
        }
    }
}
