package com.policykart.auth.service.impl;

import com.policykart.auth.dto.CredentialsDto;
import com.policykart.auth.dto.SignUpDto;
import com.policykart.auth.dto.TokenDto;
import com.policykart.auth.dto.UserDto;
import com.policykart.auth.entity.User;
import com.policykart.auth.exception.AuthServiceException;
import com.policykart.auth.exception.error.ErrorType;
import com.policykart.auth.repository.UserRepository;
import com.policykart.auth.service.AuthService;
import com.policykart.auth.utils.ConvertUtils;
import com.policykart.auth.utils.HashUtils;
import com.policykart.auth.utils.IdGenerator;
import com.policykart.auth.utils.JwtUtils;
import com.policykart.auth.utils.ObjectMapperUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public TokenDto signUpUser(SignUpDto signUpDto) {
        if (!StringUtils.hasText(signUpDto.getEmail())) {
            throw new AuthServiceException(ErrorType.INVALID_EMAIL);
        }
        Optional<User> dbUser = userRepository.findByEmailAndDeletedFalse(signUpDto.getEmail());

        if (dbUser.isPresent()) {
            logger.error("A user already exists with emailId : {}", signUpDto.getEmail());
            throw new AuthServiceException(ErrorType.USER_ALREADY_EXISTS);
        }

        User user = ConvertUtils.convertToUser(signUpDto);
        user.setUserId(IdGenerator.generateUserId(signUpDto.getEmail()));

        TokenDto tokens = getToken(user.getUserId());
        user.setRefreshTokens(
                ObjectMapperUtils.convertToJsonString(Collections.singleton(tokens.getRefreshToken()))
        );

        userRepository.save(user);
        return tokens;
    }

    @Override
    public TokenDto login(CredentialsDto credentials) {
        if (!StringUtils.hasText(credentials.getEmail()) || !StringUtils.hasText(credentials.getPassword())) {
            throw new AuthServiceException(ErrorType.INVALID_CREDENTIALS);
        }
        User user = getUserByEmail(credentials.getEmail());

        if (!HashUtils.checkPassword(credentials.getPassword(), user.getPasswordHash())) {
            logger.error("Incorrect password entered for email : {}", credentials.getEmail());
            throw new AuthServiceException(ErrorType.INCORRECT_PASSWORD);
        }

        TokenDto tokenDto = getToken(user.getUserId());
        Set<String> dbTokens = ConvertUtils.convertToStringSet(user.getRefreshTokens());
        dbTokens.add(tokenDto.getRefreshToken());

        userRepository.updateRefreshToken(user.getUserId(), ObjectMapperUtils.convertToJsonString(dbTokens));
        return tokenDto;
    }

    @Override
    public UserDto getUser(String accessToken) {
        Claims claims = JwtUtils.parseToken(accessToken);
        return ConvertUtils.convertToUserDto(getUserByUid(claims.getSubject()));
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }

        Claims claims = JwtUtils.parseToken(refreshToken);

        User dbUser = getUserByUid(claims.getSubject());
        Set<String> dbTokens = ConvertUtils.convertToStringSet(dbUser.getRefreshTokens());

        if (CollectionUtils.isEmpty(dbTokens)) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }

        for (String token : dbTokens) {
            if (refreshToken.equalsIgnoreCase(token)) {
                return JwtUtils.createAccessToken(dbUser.getUserId());
            }
        }

        throw new AuthServiceException(ErrorType.INVALID_TOKEN);
    }

    @Override
    public void clearSessions(String accessToken) {
        Claims claims = JwtUtils.parseToken(accessToken);
        userRepository.updateRefreshToken(
                claims.getSubject(),
                ObjectMapperUtils.convertToJsonString(Collections.emptyList())
        );
    }

    @Override
    public boolean isValidToken(String accessToken) {
        try {
            JwtUtils.parseToken(accessToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private TokenDto getToken(String uid) {
        return TokenDto.builder()
                .accessToken(JwtUtils.createAccessToken(uid))
                .refreshToken(JwtUtils.createRefreshToken(uid))
                .build();
    }

    private User getUserByUid(String uid) {
        Optional<User> dbUser = userRepository.findByUserIdAndDeletedFalse(uid);

        if (dbUser.isEmpty()) {
            logger.error("User not found for uid : {}", uid);
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        return dbUser.get();
    }

    private User getUserByEmail(String email) {
        Optional<User> dbUser = userRepository.findByEmailAndDeletedFalse(email);

        if (dbUser.isEmpty()) {
            logger.error("User not found for email : {}", email);
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        return dbUser.get();
    }
}
