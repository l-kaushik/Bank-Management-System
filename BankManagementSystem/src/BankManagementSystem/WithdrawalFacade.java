package BankManagementSystem;

import javax.swing.JOptionPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class WithdrawalFacade {

    static public boolean performDatabaseOperations(String pin, String amount, Date date) {
        try (MyCon con = new MyCon()) {
            int balance = calculateBalance(con, pin);
            if (!isSufficientBalance(balance, amount)) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return false;
            }
            insertWithdrawalTransaction(con, pin, amount, date);
            JOptionPane.showMessageDialog(null, "Rs. " + amount + " Withdrawn Successfully.");
        } catch (Exception e) {
            handleDatabaseProcessingException(e);
        }

        return true;
    }

    static private int calculateBalance(MyCon con, String pin) throws SQLException {
        String queryFetchData = "SELECT * FROM bank WHERE pin = ?";
        int balance = 0;

        try (PreparedStatement preparedStatementFetchData = con.connection.prepareStatement(queryFetchData)) {
            preparedStatementFetchData.setString(1, pin);
            try (ResultSet resultSet = preparedStatementFetchData.executeQuery()) {
                while (resultSet.next()) {
                    int transactionAmount = Integer.parseInt(resultSet.getString("amount"));
                    String transactionType = resultSet.getString("type");
                    balance += "Deposit".equals(transactionType) ? transactionAmount : -transactionAmount;
                }
            }
        }
        return balance;
    }

    static private boolean isSufficientBalance(int balance, String amount) {
        return balance >= Integer.parseInt(amount);
    }

    static private void insertWithdrawalTransaction(MyCon con, String pin, String amount, Date date)
            throws SQLException {
        String queryInsertData = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatementInsertData = con.connection.prepareStatement(queryInsertData)) {
            preparedStatementInsertData.setString(1, pin);
            preparedStatementInsertData.setString(2, date.toString());
            preparedStatementInsertData.setString(3, "Withdrawal");
            preparedStatementInsertData.setString(4, amount);
            preparedStatementInsertData.executeUpdate();
        }
    }

    static private void handleDatabaseProcessingException(Exception e) {
        if (e instanceof SQLException) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        } else if (e instanceof NumberFormatException) {
            JOptionPane.showMessageDialog(null, "Invalid amount format.");
        } else {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage());
        }
        e.printStackTrace();
    }
}
