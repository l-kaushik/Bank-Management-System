package BankManagementSystem;

import java.security.MessageDigest;
import java.security.SecureRandom;

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
}
