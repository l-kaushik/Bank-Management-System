package BankManagementSystem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class MyCon {

    String folderPath = "src/Databases";
    String fileName = "SQLiteTest1.db";
    String filePath = folderPath + "/" + fileName;

    Connection connection;
    Statement statement;

    MyCon() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            statement = connection.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS signup (" +
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
            statement.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyCon();
    }
}
