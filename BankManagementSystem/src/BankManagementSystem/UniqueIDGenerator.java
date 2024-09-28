package BankManagementSystem;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class UniqueIDGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int UID_LENGTH = 6; // 56 billion combinations :O
    private static final SecureRandom random = new SecureRandom();
    private static final Set<String> generatedUIDs = new HashSet<>();


    // find a way to check if generate id is already used or not

    public static String generateUID() {
        String uid;
        do {
            uid = generateRandomUID();
        } while (generatedUIDs.contains(uid)); 

        generatedUIDs.add(uid);
        return uid;
    }

    private static String generateRandomUID() {
        StringBuilder uid = new StringBuilder(UID_LENGTH);
        for (int i = 0; i < UID_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            uid.append(CHARACTERS.charAt(randomIndex));
        }
        return uid.toString();
    }
}
