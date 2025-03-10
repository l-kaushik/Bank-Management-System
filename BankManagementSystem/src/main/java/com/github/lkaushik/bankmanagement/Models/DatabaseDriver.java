package com.github.lkaushik.bankmanagement.Models;

import com.github.lkaushik.bankmanagement.Views.ClientAccountType;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver() {
        try{
            this.conn = DriverManager.getConnection("jdbc:sqlite:trustusbank.db");

            // check if database is empty
            if(!isDatabaseInitialized()) {
                System.out.println("Database is not initialized\nInitializing new database.");
                initializeDatabase();
            }
        } catch(SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    private boolean isDatabaseInitialized() {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='Admins'";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return  resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void initializeDatabase() {
        try(Statement statement = conn.createStatement()) {
            for(String query : DatabaseCreator.tableCreationQueries) {
                statement.execute(query);
            }
            createAdminAccount();
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
    }

    private void createAdminAccount() {
        String query = "INSERT INTO Admins(Username, Password) VALUES(?, ?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, "Admin");
            preparedStatement.setString(2, "123456");

            // Execute the query and check the result
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin account created successfully.");
            } else {
                System.out.println("Failed to create admin account.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating admin account: " + e.getMessage());
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

    public String getClientPasswordSalt(String pAddress) {
        String query = "SELECT Salt FROM Clients WHERE PayeeAddress = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, pAddress);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString("Salt");
            }
            else {
                return "";
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return "";
        }
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

    private void updateTransactionTable(String sender, String receiver, double amount, String message) throws SQLException {
        String query = "INSERT INTO Transactions (Sender, Receiver, Amount, Date, Message) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, receiver);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, String.valueOf(LocalDate.now()));
            preparedStatement.setString(5, message);
            preparedStatement.executeUpdate();
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

    private void updateCheckingAccount(String owner, double balance) throws SQLException {
        updateAccount(owner, balance, "CheckingAccounts");
    }

    private void updateSavingsAccounts(String owner, double balance) throws SQLException {
        updateAccount(owner, balance, "SavingsAccounts");
    }

    public void performSelfTransfer(String owner, double toChecking, double toSavings) {
        try {
            conn.setAutoCommit(false);
            updateCheckingAccount(owner, toChecking);
            updateSavingsAccounts(owner, toSavings);
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rollbackTransaction();
        } finally {
            resetAutoCommit();
        }
    }

    private void updateAccount(String owner, double balance, String tableName) throws SQLException{
        String query = "UPDATE " + tableName + " SET Balance = ? WHERE Owner = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, owner);
            preparedStatement.executeUpdate();
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

    public boolean pushClientData(Client client, String password, String salt) {
        try {
            conn.setAutoCommit(false);

            pushCheckingAccount(client.checkingAccountProperty().getValue(), client.payeeAddressProperty().getValue());
            pushSavingsAccount(client.savingAccountProperty().getValue(), client.payeeAddressProperty().getValue());
            pushClient(client, password, salt);

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

    private void pushClient(Client client, String password, String salt) throws SQLException {
        String query = "INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, Salt, Date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, client.firstNameProperty().getValue());
            preparedStatement.setString(2, client.lastNameProperty().getValue());
            preparedStatement.setString(3, client.payeeAddressProperty().getValue());
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, salt);
            preparedStatement.setString(6, LocalDate.now().toString());
            preparedStatement.executeUpdate();
        }
    }

    private void pushSavingsAccount(Account account, String owner) throws SQLException {
        String query = "INSERT INTO SavingsAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, owner);
            preparedStatement.setString(2, account.accountNumberProperty().getValue());
            preparedStatement.setDouble(3, ((SavingsAccount) account).withdrawalLimitProperty().getValue());
            preparedStatement.setDouble(4, account.balanceProperty().getValue());
            preparedStatement.executeUpdate();
        }
    }

    private void pushCheckingAccount(Account account, String owner) throws SQLException {
        String query = "INSERT INTO CheckingAccounts (Owner, AccountNumber, TransactionLimit, Balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, owner);
            preparedStatement.setString(2, account.accountNumberProperty().getValue());
            preparedStatement.setInt(3,  ((CheckingAccount) account).transactionLimitProperty().getValue());
            preparedStatement.setDouble(4, account.balanceProperty().getValue());
            preparedStatement.executeUpdate();
        }
    }

    public ResultSet fetchAllClientsData() {
        String query = "SELECT * FROM Clients";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public ResultSet fetchClientData(String address) {
        String query = "SELECT * FROM Clients WHERE PayeeAddress = ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, address);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  resultSet;
    }

    public boolean deleteClientData(String address) {
        try {
            conn.setAutoCommit(false);

            deleteFromTable(address, "Clients", "PayeeAddress");
            deleteFromTable(address, "CheckingAccounts", "Owner");
            deleteFromTable(address, "SavingsAccounts", "Owner");
            removeUserFromTransaction(address);

            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rollbackTransaction();
            return false;
        }finally {
            resetAutoCommit();
        }
        return true;
    }

    private void removeUserFromTransaction(String address) throws SQLException{
        String query = "UPDATE Transactions SET Sender = CASE WHEN Sender = ? THEN ? ELSE Sender END, " +
                "Receiver = CASE WHEN Receiver = ? THEN ? ELSE Receiver END WHERE Sender = ? or Receiver = ?";

        String newAddress = "REM(" + address + ")";

        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, newAddress);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, newAddress);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, address);
            preparedStatement.executeUpdate();
        }
    }

    private void deleteFromTable(String address, String tableName, String columnName) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";

        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, address);
            preparedStatement.executeUpdate();
        }
    }

    public boolean transferFundsFromAdmin(String receiver, double newAmount, double actualAmount, ClientAccountType accountType) {
        try{
            conn.setAutoCommit(false);

            if (Objects.requireNonNull(accountType) == ClientAccountType.CHECKING) {
                updateCheckingAccount(receiver, newAmount);
            } else {
                updateSavingsAccounts(receiver, newAmount);
            }

            updateTransactionTable("Admin", receiver, actualAmount, "deposited");
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rollbackTransaction();
            return false;
        } finally {
            resetAutoCommit();
        }
        return true;
    }

    public boolean updateSavingsAccount(String payeeAddress, String accountNumber, double amount) {
        return updateClientAccount(payeeAddress, "SavingsAccounts", "WithdrawalLimit", 2000.0, accountNumber, amount);
    }

    public boolean updateCheckingAccount(String payeeAddress, String accountNumber, double amount) {
        return updateClientAccount(payeeAddress, "CheckingAccounts", "TransactionLimit", 10, accountNumber, amount);
    }

    private boolean updateClientAccount(String payeeAddress, String tableName, String limitColumnName, double limit, String accountNumber, double amount) {
        String query = "UPDATE " + tableName + " SET AccountNumber = ?, " + limitColumnName + " = ?, Balance = ? WHERE Owner = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setDouble(2, limit);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, payeeAddress);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateClientPassword(String address, String password, String salt) {
        String query = "UPDATE Clients SET Password = ?, Salt = ? WHERE PayeeAddress = ?";

        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, salt);
            preparedStatement.setString(3, address);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean insertReport(String address, String message) {
        String query = "INSERT INTO Reports(Sender, Date, Message) VALUES(?, ?, ?)";

        try(PreparedStatement preparedStatement = conn.prepareStatement((query))) {
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, LocalDate.now().toString());
            preparedStatement.setString(3, message);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
