package com.github.lkaushik.bankmanagement.Models;

import com.github.lkaushik.bankmanagement.Views.PasswordStatus;

import java.util.HashSet;
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
}
