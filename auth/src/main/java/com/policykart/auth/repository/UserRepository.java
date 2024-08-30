package com.policykart.auth.repository;

import com.policykart.auth.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends AbstractRepository<User, String> {

    Optional<User> findByEmailAndDeletedFalse(String email);

    Optional<User> findByUserIdAndDeletedFalse(String uid);

    @Modifying
    @Transactional
    @Query("update user u SET u.refreshTokens=:tokenString WHERE userId=:uid")
    void updateRefreshToken(@Param("uid") String uid, @Param("tokenString") String tokenString);
}
