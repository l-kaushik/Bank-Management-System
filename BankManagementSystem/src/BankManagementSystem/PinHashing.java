package BankManagementSystem;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;

public class PinHashing {

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
    public static byte[] hashStringWithSalt(String input, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt); // Add salt to the hash function
            byte[] hashedBytes = md.digest(input.getBytes()); // Hash the input
            return hashedBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String byteToString(byte[] input) {
        String str = new String(input, StandardCharsets.UTF_8);
        return str;
    }
}
