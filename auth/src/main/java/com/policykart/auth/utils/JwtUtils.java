package com.policykart.auth.utils;

import com.policykart.auth.exception.AuthServiceException;
import com.policykart.auth.exception.error.ErrorType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.private.key}")
    private String privateKey;

    public static String STATIC_PRIVATE_KEY;

    @PostConstruct
    public void init() {
        STATIC_PRIVATE_KEY = privateKey;
    }

    public static String createAccessToken(String subject) {
        return createToken(subject, 1000 * 60 * 15L);       // 15 minutes
    }

    public static String createRefreshToken(String subject) {
        return createToken(subject, 1000 * 60 * 60 * 24 * 180L);    // 6 months
    }

    public static String createToken(String userId, Long expiresInMs) {
        try {
            return Jwts.builder()
                    .subject(userId)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + expiresInMs))
                    .signWith(SignatureAlgorithm.HS256, STATIC_PRIVATE_KEY)
                    .compact();
        } catch (Exception e) {
            throw new AuthServiceException(ErrorType.SOMETHING_WENT_WRONG, e.getMessage());
        }

    }


    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(STATIC_PRIVATE_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthServiceException(ErrorType.TOKEN_EXPIRED);
        } catch (MalformedJwtException | SignatureException e) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        } catch (Exception e) {
            throw new AuthServiceException(ErrorType.SOMETHING_WENT_WRONG, e.getMessage());
        }
    }
}
