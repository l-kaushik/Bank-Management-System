package com.github.lkaushik.bankmanagement.Models;

import com.github.lkaushik.bankmanagement.Views.PasswordStatus;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PasswordManager {

    private static final Set<Character> validChars = new HashSet<>();

    static {
        char[] validCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()".toCharArray();
        for (char c : validCharacters) {
            validChars.add(c);
        }
    }

    public static PasswordStatus validate(String password) {
        if(password.length() < 8) return PasswordStatus.TOO_SHORT;
        for(int i = 0; i < password.length(); i++) {
            if(!validChars.contains(password.charAt(i))) return PasswordStatus.INVALID_CHARACTER;
        }
        return PasswordStatus.VALID;
    }

    // Method to generate a random salt
    public static byte[] generateSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            byte[] salt = new byte[16];
            sr.nextBytes(salt); // Generate 16 random bytes for salt
            return salt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to hash a string with a salt
    private static byte[] hashStringWithSalt(String input, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt); // Add salt to the hash function
            // Hash the input
            return md.digest(input.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String byteToString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static byte[] stringToByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String hashPassword(String password, String salt) {
        return byteToString(Objects.requireNonNull(hashStringWithSalt(password, stringToByte(salt))));
    }

    public static String hashPassword(String password, byte[] salt) {
        return  byteToString(Objects.requireNonNull(hashStringWithSalt(password, salt)));
    }

    public static String hashPassword(String password) {
        return byteToString(Objects.requireNonNull(hashStringWithSalt(password, generateSalt())));
    }
}
