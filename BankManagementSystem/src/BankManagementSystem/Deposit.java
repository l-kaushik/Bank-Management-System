package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import javax.swing.JButton;

public class Deposit extends ResizableATM implements ActionListener {

    String pin = null;

    JLabel depositAmountLabel;
    JTextField amounTextField;

    JButton depositButton;
    JButton backButton;

    Deposit(String inPin) {
        super("Depost Money");

        pin = inPin;

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        initializeLabelsAndFields();
        initializeButtons();
    }

    private void initializeLabelsAndFields() {
        depositAmountLabel = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        depositAmountLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(depositAmountLabel);

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

    private void initializeButtons() {
        depositButton = new JButton("DEPOSIT");
        Common.setButtonAttributes(depositButton, Color.BLACK, this);
        backgroundImageLabel.add(depositButton);

        backButton = new JButton("BACK");
        Common.setButtonAttributes(backButton, Color.BLACK, this);
        backgroundImageLabel.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == depositButton) {
            depositAction();
        } else if (e.getSource() == backButton) {
            new AtmWindow(pin);
            dispose();
        }
    }

    private void depositAction() {
        String amount = amounTextField.getText();
        Date date = new Date();

        if (!validateAmount(amount))
            return;

        insertIntoDatabase(amount, date);
    }

    private boolean validateAmount(String amount) {
        return Common.ValidateString(amount, "Amount cannot be empty");
    }

    private void insertIntoDatabase(String amount, Date date) {

        try (MyCon con = new MyCon()) {

            String query = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = con.connection.prepareStatement(query);

            preparedStatement.setString(1, pin);
            preparedStatement.setString(2, date.toString());
            preparedStatement.setString(3, "Deposit");
            preparedStatement.setString(4, amount);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully", "Deposit Success",
                    JOptionPane.INFORMATION_MESSAGE);

            new AtmWindow(pin);
            dispose();

        } catch (Exception e) {
            if (e instanceof SQLException) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }else {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
            }
            e.printStackTrace();
        } 
    }

    @Override
    protected void handleResizing() {
        super.handleResizing();

        if(!isVisible()) return;

        Dimension size = getSize();

        updateLabels(size);
        updateTextFields(size);
        updateButtons(size);
    }

    private void updateLabels(Dimension size) {
        Font scaledFont16 = Common.SystemBold16.deriveFont(size.width/1400.0f * 16);
        depositAmountLabel.setFont(scaledFont16);

        int x = (int) (size.width/ 3.4);
        int y = (int) (size.height / 4.9);
        int width = 400;
        int height = 35;
        depositAmountLabel.setBounds(x, y, width, height);
    }

    private void updateTextFields(Dimension size) {
        Font scaledFont22 = Common.RalewayBold22.deriveFont(size.width/1400.0f * 22 );
        amounTextField.setFont(scaledFont22);

        int x = (int) (size.width / 3.4);
        int y = (int) (size.height / 4);
        int width = (int) (size.width / 4.84);
        int height = (int) (size.width / 50);
        amounTextField.setBounds(x, y, width, height);
    }

    private void updateButtons(Dimension size) {
        updateDepositButtonLocation(size);
        updateBackButtonLocation(size);
    }

    private void updateDepositButtonLocation(Dimension size) {
        Font scaledFont18 = Common.RalewayBold18.deriveFont(size.width / 1400.0f * 18);
        depositButton.setFont(scaledFont18);

        int x = (int) (size.width / 2.25);
        int y = (int) (size.height / 2.4);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        depositButton.setBounds(x, y, width, height);
    }

    private void updateBackButtonLocation(Dimension size) {
        Font scaledFont18 = Common.RalewayBold18.deriveFont(size.width / 1400.0f * 18);
        backButton.setFont(scaledFont18);
        
        int x = (int) (size.width / 2.25);
        int y = (int) (size.height / 2.1 );
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        backButton.setBounds(x, y, width, height);
    }

    public static void main(String[] args) {
        new Deposit("");
    }

}
