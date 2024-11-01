package com.github.lkaushik.bankmanagement.Models;

import java.sql.*;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver() {
        try{
            this.conn = DriverManager.getConnection("jdbc:sqlite:trustusbank.db");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * Client Section
    * */

    public ResultSet getClientData(String pAddress, String password) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Clients WHERE PayeeAddress = ? AND Password = ?";
        try{
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, pAddress);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /*
    * Admin Section
    * */

    /*
    * Utility Methods
    * */

}
