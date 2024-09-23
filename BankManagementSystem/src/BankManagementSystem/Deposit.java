package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Deposit extends JFrame implements ActionListener {

    String pin = null;

    JTextField amounTextField;

    JButton depositButton;
    JButton backButton;

    Deposit(String inPin) {

        pin = inPin;

        Common.InitializeJFrame(this, "Deposit Money", null, new Dimension(1550, 1080), JFrame.EXIT_ON_CLOSE, false,
                new Point(0, 0));

        JLabel backgroundImageLabel = Common.GetScaledImageWithLabel("icons/atm2.png", 1550, 830);
        backgroundImageLabel.setBounds(0, 0, 1550, 830);
        add(backgroundImageLabel);

        JLabel depositAmountLabel = Common.CreateLabel("ENTER AMOUNT YOU WANT TO DEPOSIT", Color.WHITE,
                Common.SystemBold16, new Rectangle(460, 180, 400, 35));
        backgroundImageLabel.add(depositAmountLabel);

        amounTextField = Common.CreateTextField(Common.RalewayBold22, new Rectangle(460, 230, 320, 25));

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

        depositButton = Common.CreateButton("DEPOSIT", Common.RalewayBold18, Color.BLACK,
                new Rectangle(700, 362, 150, 35), this);
        backgroundImageLabel.add(depositButton);

        backButton = Common.CreateButton("BACK", Common.RalewayBold18, Color.BLACK,
                new Rectangle(700, 406, 150, 35), this);
        backgroundImageLabel.add(backButton);

        setVisible(true);

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

    public static void main(String[] args) {
        new Deposit("");
    }

}
