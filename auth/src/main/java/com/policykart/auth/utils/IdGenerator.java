package com.policykart.auth.utils;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class IdGenerator {

    private static final String HASH_ALGORITHM = "SHA-256";

    public static String generateUserId(String email) {
        long timestamp = System.currentTimeMillis();
        String input = email + ":" + timestamp;
        return generateShortId(hash(input));
    }

    private static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not found: " + HASH_ALGORITHM, e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String generateShortId(String longHash) {
        if (longHash.length() > 16) {
            return longHash.substring(0, 16);
        }
        return longHash;
    }
}
