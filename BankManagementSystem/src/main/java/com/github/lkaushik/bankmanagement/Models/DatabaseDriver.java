package com.github.lkaushik.bankmanagement.Models;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;

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

    private ResultSet getAccountData(String owner, String tableName) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + tableName + " WHERE Owner = ?";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, owner);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getClientSavingsAccountData(String owner) {
        return getAccountData(owner, "SavingsAccounts");
    }

    public ResultSet getClientCheckingAccountData(String owner) {
        return getAccountData(owner, "CheckingAccounts");
    }

    public ResultSet getClientTransactionData(String pAddress) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Transactions WHERE Sender = ? OR Receiver = ?";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, pAddress);
            preparedStatement.setString(2, pAddress);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private void updateTransactionTable(String sender, String receiver, double amount, String message) {
        String query = "INSERT INTO Transactions (Sender, Receiver, Amount, Date, Message) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, receiver);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, String.valueOf(LocalDate.now()));
            preparedStatement.setString(5, message);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transferFundsFromChecking(String sender, String receiver, double amount, String message, double senderCheckingBalance, double receiverCheckingBalance) {
        try {
            // start transaction
            conn.setAutoCommit(false);

            updateTransactionTable(sender, receiver,amount, message);
            updateCheckingAccount(sender, senderCheckingBalance - amount);
            updateCheckingAccount(receiver, receiverCheckingBalance + amount);

            // save all changes
            conn.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        } finally {
           resetAutoCommit();
        }
    }

    public void transferFundsFromSavings(String sender, String receiver, double amount, String message, double senderCheckingBalance, double senderSavingsBalance, double receiverCheckingBalance) {
        try {
            // start transaction
            conn.setAutoCommit(false);

            updateTransactionTable(sender, receiver,amount, message);
            updateCheckingAccount(sender, 0.0);
            updateSavingsAccounts(sender,  senderSavingsBalance - (amount - senderCheckingBalance));
            updateCheckingAccount(receiver, receiverCheckingBalance + amount);

            // save all changes
            conn.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        } finally {
            resetAutoCommit();
        }
    }

    private void rollbackTransaction() {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace();
        }
    }

    private void resetAutoCommit() {
        try {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isClient(String pAddress) {
        String query = "SELECT * FROM Clients WHERE PayeeAddress = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, pAddress);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.isBeforeFirst(); // Returns true if there’s at least one row
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateCheckingAccount(String owner, double balance) {
        updateAccount(owner, balance, "CheckingAccounts");
    }

    public void updateSavingsAccounts(String owner, double balance) {
        updateAccount(owner, balance, "SavingsAccounts");
    }

    public void performSelfTransfer(String owner, double toChecking, double toSavings) {
        try {
            conn.setAutoCommit(false);
            updateCheckingAccount(owner, toChecking);
            updateSavingsAccounts(owner, toSavings);
            conn.commit();
        } catch (SQLException e) {
            rollbackTransaction();
        } finally {
            resetAutoCommit();
        }
    }

    private void updateAccount(String owner, double balance, String tableName) {
        String query = "UPDATE " + tableName + " SET Balance = ? WHERE Owner = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, owner);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Admin Section
    * */

    public ResultSet getAdminData(String username, String password) {
        String query = "SELECT * FROM Admins WHERE Username = ? AND Password = ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /*
    * Utility Methods
    * */

    public void closeResources(ResultSet resultSet) {
        try {
            if(resultSet != null) {
                Statement statement = resultSet.getStatement();
                if(statement != null) statement.close();
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isAccountNumberExists(String accountNumber) {
        String query = "SELECT * FROM SavingsAccounts WHERE AccountNumber = ?" +
                " UNION " +
                "SELECT * FROM CheckingAccounts WHERE AccountNumber = ?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumber);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch ( SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean pushClientData(Client client, String password) {
        try {
            conn.setAutoCommit(false);

            pushCheckingAccount(client.checkingAccountProperty().getValue(), client.payeeAddressProperty().getValue());
            pushSavingsAccount(client.savingAccountProperty().getValue(), client.payeeAddressProperty().getValue());
            pushClient(client, password);

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rollbackTransaction();
            return false;
        } finally {
            resetAutoCommit();
        }
    }

    private void pushClient(Client client, String password) throws SQLException {
        String query = "INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, Date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, client.firstNameProperty().getValue());
            preparedStatement.setString(2, client.lastNameProperty().getValue());
            preparedStatement.setString(3, client.payeeAddressProperty().getValue());
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, LocalDate.now().toString());
            preparedStatement.executeUpdate();
        }
    }

    private void pushSavingsAccount(Account account, String owner) throws SQLException {
        if(account == null) return;
        String query = "INSERT INTO SavingsAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, owner);
            preparedStatement.setString(2, account.accountNumberProperty().getValue());
            preparedStatement.setDouble(3, 2000);
            preparedStatement.setDouble(4, account.balanceProperty().getValue());
            preparedStatement.executeUpdate();
        }
    }

    private void pushCheckingAccount(Account account, String owner) throws SQLException {
        if(account == null) return;
        String query = "INSERT INTO CheckingAccounts (Owner, AccountNumber, TransactionLimit, Balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, owner);
            preparedStatement.setString(2, account.accountNumberProperty().getValue());
            preparedStatement.setInt(3, 10);
            preparedStatement.setDouble(4, account.balanceProperty().getValue());
            preparedStatement.executeUpdate();
        }
    }
}
