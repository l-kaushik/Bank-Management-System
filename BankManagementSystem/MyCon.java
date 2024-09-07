package BankManagementSystem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;


public class MyCon {
    
    Connection connection;
    Statement statement;
    
    MyCon(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankSystem",Creds.UserCreds.username, Creds.UserCreds.password );
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
