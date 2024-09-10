package BankManagementSystem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class MyCon {

    // add a way to check if folder is present or not, if not then create one

    String folderPath = "BankManagementSystem/src/Databases";
    String fileName = "SQLiteTest1.db";
    String filePath = folderPath + "/" + fileName;

    Connection connection;
    Statement statement;

    MyCon() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            statement = connection.createStatement();

            String createSignupTable = "CREATE TABLE IF NOT EXISTS signup (" +
            "form_no TEXT PRIMARY KEY, " +
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
            "form_no TEXT PRIMARY KEY, " +
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
            "form_no TEXT PRIMARY KEY, " +
            "account_type TEXT, " +
            "card_number TEXT, " +
            "pin TEXT, " +
            "facility TEXT)";
            statement.execute(createSignupTable3);

            String createLoginTable = "CREATE TABLE IF NOT EXISTS login(" +
            "form_no TEXT PRIMARY KEY, " +
            "card_number TEXT, " +
            "pin TEXT)";                    // find a better way to store pin
            statement.execute(createLoginTable);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyCon();
    }
}
