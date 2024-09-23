package BankManagementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Withdrawal extends ResizableATM implements ActionListener {

    String pin = null;

    JTextField amounTextField;

    JButton withdrawalButton;
    JButton backButton;

    JLabel withdrawalAmountLabel;
    JLabel amountLabel;
    
    Withdrawal(String inPin) {

        super("Withdrawal Money"); // sets the title for frame

        pin = inPin;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        initializeComponents(screenSize);

        add(backgroundImageLabel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void initializeComponents(Dimension screenSize) {
        // components initialization here
        initializeWithdrawalAmountLabel(screenSize);
        initializeAmountLabel(screenSize);
        initializeAmountTextField(screenSize);
        initializeWithdrawalButton(screenSize);
        initializeBackButton(screenSize);
    }

    private void initializeWithdrawalAmountLabel(Dimension screenSize) {
        withdrawalAmountLabel = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        withdrawalAmountLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(withdrawalAmountLabel);
    }

    private void initializeAmountLabel(Dimension screenSize) {
        amountLabel = new JLabel("PLEASE ENTER YOUR AMOUNT");
        amountLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(amountLabel);
    }

    private void initializeAmountTextField(Dimension screenSize) {
        amounTextField = new JTextField();

        // Add a KeyListener to only allow numeric input
        amounTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // If the character is not a digit, consume the event (ignore input)
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        backgroundImageLabel.add(amounTextField);
    }

    private void initializeWithdrawalButton(Dimension screenSize) {

        withdrawalButton = new JButton("WITHDRAWAL");
        withdrawalButton.setForeground(Color.BLACK);
        withdrawalButton.addActionListener(this);
        withdrawalButton.setFocusable(false);
        backgroundImageLabel.add(withdrawalButton);
    }

    private void initializeBackButton(Dimension screenSize) {
        backButton = new JButton("BACK");
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(this);
        backButton.setFocusable(false);
        backgroundImageLabel.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == withdrawalButton) {
            initWithdrawal();
        } else if (e.getSource() == backButton) {
            new AtmWindow(pin);
            dispose();
        }
    }

    private void initWithdrawal() {
        String amount = amounTextField.getText();
        Date date = new Date();

        // perform data validation
        if (!validateAmount(amount))
            return;

        performDatabaseOperations(amount, date);
    }

    private boolean validateAmount(String amount) {

        try {
            if (Integer.parseInt(amount) > 10000) {
                throw new IllegalArgumentException("Exceeds maximum withdrawal limit");
            }
            return true;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "The maximum withdrawal limit is 10,000", "Withdrawal Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    private void performDatabaseOperations(String amount, Date date) {
        try (MyCon con = new MyCon()) {
            int balance = calculateBalance(con);
            if (!isSufficientBalance(balance, amount)) {
                JOptionPane.showMessageDialog(this, "Insufficient Balance");
                return;
            }
            insertWithdrawalTransaction(con, amount, date);
            JOptionPane.showMessageDialog(this, "Rs. " + amount + " Withdrawn Successfully.");
            dispose();
            new AtmWindow(pin);
        } catch (Exception e) {
            handleDatabaseProcessingException(e);
        }
    }

    private int calculateBalance(MyCon con) throws SQLException {
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

    private boolean isSufficientBalance(int balance, String amount) {
        return balance >= Integer.parseInt(amount);
    }

    private void insertWithdrawalTransaction(MyCon con, String amount, Date date) throws SQLException {
        String queryInsertData = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatementInsertData = con.connection.prepareStatement(queryInsertData)) {
            preparedStatementInsertData.setString(1, pin);
            preparedStatementInsertData.setString(2, date.toString());
            preparedStatementInsertData.setString(3, "Withdrawal");
            preparedStatementInsertData.setString(4, amount);
            preparedStatementInsertData.executeUpdate();
        }
    }

    private void handleDatabaseProcessingException(Exception e) {
        if (e instanceof SQLException) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        } else if (e instanceof NumberFormatException) {
            JOptionPane.showMessageDialog(this, "Invalid amount format.");
        } else {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
        }
        e.printStackTrace();
    }

    @Override
    protected void handleResizing() {

        super.handleResizing();

        // prevents resizing when frame is not visible
        if (!this.isVisible())
            return;

        Dimension size = this.getSize(); // Get the current window size

        updateFonts(size);
        updateLabelsLocations(size);
        updateAmountTextFieldLocation(size);
        updateButtons(size);

        // Revalidate and repaint to apply changes
        this.revalidate();
        this.repaint();
    }

    private void updateFonts(Dimension size) {
        float scaleFactor = size.width / 1400.0f;

        updateLabelFonts(scaleFactor);
        updateAmountTextFieldFont(scaleFactor);
        updateButtonFonts(scaleFactor);
    }

    private void updateLabelFonts(float scaleFactor) {
        Font scaledFont16 = new Font("System", Font.BOLD, (int) (16 * scaleFactor));

        withdrawalAmountLabel.setFont(scaledFont16);
        amountLabel.setFont(scaledFont16);
    }

    private void updateAmountTextFieldFont(float scaleFactor) {
        Font scaledFont22 = new Font("Raleway", Font.BOLD, (int) (22 * scaleFactor));

        amounTextField.setFont(scaledFont22);
    }

    private void updateButtonFonts(float scaleFactor) {
        Font scaleFont14 = Common.RalewayBold14.deriveFont(14 * scaleFactor);

        withdrawalButton.setFont(scaleFont14);
        backButton.setFont(scaleFont14);
    }

    private void updateLabelsLocations(Dimension size) {
        updateWithdrawalAmountLabelLocation(size);
        updateAmountLabelLocation(size);
    }

    private void updateWithdrawalAmountLabelLocation(Dimension size) {
        int x = (int) (size.width/ 3.35);
        int y = (int) (size.height / 6);
        int width = 700;
        int height = 35;
        withdrawalAmountLabel.setBounds(x, y, width, height);
    }

    private void updateAmountLabelLocation(Dimension size) {
        int x = (int) (size.width/ 3.35);
        int y = (int) (size.height / 4.9);
        int width = 400;
        int height = 35;
        amountLabel.setBounds(x, y, width, height);
    }

    private void updateAmountTextFieldLocation(Dimension size) {
        int x = (int) (size.width / 3.35);
        int y = (int) (size.height / 4);
        int width = (int) (size.width / 4.84);
        int height = (int) (size.width / 40);
        amounTextField.setBounds(x, y, width, height);
    }

    private void updateButtons(Dimension size) {
        updateWithdrawalButtonLocation(size);
        updateBackButtonLocation(size);
    }

    private void updateWithdrawalButtonLocation(Dimension size) {
        int x = (int) (size.width / 2.25);
        int y = (int) (size.height / 2.4);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        withdrawalButton.setBounds(x, y, width, height);
    }

    private void updateBackButtonLocation(Dimension size) {
        int x = (int) (size.width / 2.25);
        int y = (int) (size.height / 2.1 );
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        backButton.setBounds(x, y, width, height);
    }

    public static void main(String[] args) {
        new Withdrawal("1111");
    }

}
