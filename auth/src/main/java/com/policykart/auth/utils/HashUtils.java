package com.policykart.auth.utils;

import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

@UtilityClass
public class HashUtils {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }

    public static boolean checkPassword(String plainTextPwd, String hashedPws) {
        return BCrypt.checkpw(plainTextPwd, hashedPws);
    }
}
