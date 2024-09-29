package BankManagementSystem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.nio.file.*;

public class MyCon implements AutoCloseable {

    static final String folderPath = "BankManagementSystem/src/Databases";
    static final String fileName = "SQLiteTest1.db";
    static final String filePath = folderPath + "/" + fileName;

    Connection connection;
    Statement statement;

    MyCon() {

        createPathIfNotExists();
        createTablesIfNotExists();

    }

    public static void createPathIfNotExists() {
        Path path = Paths.get(folderPath);

        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createTablesIfNotExists() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            statement = connection.createStatement();

            String createSignupTable = "CREATE TABLE IF NOT EXISTS signup (" +
                    "UID TEXT PRIMARY KEY, " +
                    "name TEXT, " +
                    "father_name TEXT, " +
                    "DOB TEXT, " +
                    "gender TEXT, " +
                    "email TEXT, " +
                    "marital_status TEXT, " +
                    "address TEXT, " +
                    "city TEXT, " +
                    "pincode TEXT, " +
                    "state TEXT)";
            statement.execute(createSignupTable);

            String createSignupTable2 = "CREATE TABLE IF NOT EXISTS signuptwo (" +
                    "UID TEXT PRIMARY KEY, " +
                    "religion TEXT, " +
                    "category TEXT, " +
                    "income TEXT, " +
                    "education TEXT, " +
                    "occupation TEXT, " +
                    "pan TEXT, " +
                    "aadhar TEXT, " +
                    "senior_citizen TEXT, " +
                    "existing_account TEXT)";
            statement.execute(createSignupTable2);

            String createSignupTable3 = "CREATE TABLE IF NOT EXISTS signupthree (" +
                    "UID TEXT PRIMARY KEY, " +
                    "account_type TEXT, " +
                    "facility TEXT)";
            statement.execute(createSignupTable3);

            String createLoginTable = "CREATE TABLE IF NOT EXISTS login(" +
                    "UID TEXT PRIMARY KEY, " +
                    "card_number TEXT, " +
                    "pin TEXT, " +
                    "pin_salt TEXT)"; 
            statement.execute(createLoginTable);

            String createBankTable = "CREATE TABLE IF NOT EXISTS bank(" +
                    "UID TEXT," +
                    "date TEXT, " +
                    "type TEXT, " +
                    "amount TEXT)";
            statement.execute(createBankTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new MyCon();
    }

    @Override
    public void close() throws Exception {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
