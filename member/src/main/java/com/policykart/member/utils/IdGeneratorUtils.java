package com.policykart.member.utils;

import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class IdGeneratorUtils {

    public String generatePolicyNumber(String userId, String policyId) {
        String input = userId + policyId + System.currentTimeMillis();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            long codeLong = getCodeLong(hashBytes);

            // Format the long integer to a 10-digit string
            return String.format("%010d", codeLong);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    private static long getCodeLong(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder(new BigInteger(1, hashBytes).toString(16));

        // Ensure the hex string is of length 64 (SHA-256 produces 64 characters hex string)
        while (hexString.length() < 64) {
            hexString.insert(0, "0");
        }

        // Step 3: Extract a 10-digit code from the hex string
        // Take the first 10 characters and convert to a long integer
        String codeHex = hexString.substring(0, 10);
        long codeLong = new BigInteger(codeHex, 16).longValue();
        return codeLong;
    }
}
