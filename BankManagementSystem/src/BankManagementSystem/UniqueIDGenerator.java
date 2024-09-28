package BankManagementSystem;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UniqueIDGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int UID_LENGTH = 6; // 56 billion combinations :O
    private static final SecureRandom random = new SecureRandom();


    // find a way to check if generate id is already used or not

    public static String generateUID() {

        while(true){
            String uid;
            uid = generateRandomUID();
            if(!isUIDExists(uid)) return uid;
        }
    }

    private static String generateRandomUID() {
        StringBuilder uid = new StringBuilder(UID_LENGTH);
        for (int i = 0; i < UID_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            uid.append(CHARACTERS.charAt(randomIndex));
        }
        return uid.toString();
    }

    private static boolean isUIDExists(String uid) {

        try (MyCon con = new MyCon()) {
            String query = "SELECT UID FROM login WHERE UID = ?";
            
            PreparedStatement preparedStatement = con.connection.prepareStatement(query);
            preparedStatement.setString(1, uid);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    preparedStatement.close();
                    return false;
                }
            }

            preparedStatement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
