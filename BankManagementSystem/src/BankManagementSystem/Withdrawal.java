package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Withdrawal extends ResizableFrame implements ActionListener {

    String pin = null;

    JTextField amounTextField;

    JButton withdrawalButton;
    JButton backButton;

    Withdrawal(String inPin) {

        pin = inPin;

        Common.InitializeJFrame(this, "Withdrawal Money", null, new Dimension(1550, 1080), JFrame.EXIT_ON_CLOSE, false,
                new Point(0, 0));

        JLabel backgroundImageLabel = Common.GetScaledImageWithLabel("icons/atm2.png", 1550, 830);
        backgroundImageLabel.setBounds(0, 0, 1550, 830);
        add(backgroundImageLabel);

        JLabel withdrawalAmountLabel = Common.CreateLabel("MAXIMUM WITHDRAWAL IS RS.10,000", Color.WHITE,
                Common.SystemBold16, new Rectangle(460, 180, 700, 35));
        backgroundImageLabel.add(withdrawalAmountLabel);

        JLabel amountLabel = Common.CreateLabel("PLEASE ENTER YOUR AMOUNT", Color.WHITE,
                Common.SystemBold16, new Rectangle(460, 220, 400, 35));
        backgroundImageLabel.add(amountLabel);

        amounTextField = Common.CreateTextField(Common.RalewayBold22, new Rectangle(460, 270, 320, 25));

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

        withdrawalButton = Common.CreateButton("WITHDRAWAL", Common.RalewayBold14, Color.BLACK,
                new Rectangle(700, 362, 150, 35), this);
        backgroundImageLabel.add(withdrawalButton);

        backButton = Common.CreateButton("BACK", Common.RalewayBold14, Color.BLACK,
                new Rectangle(700, 406, 150, 35), this);
        backgroundImageLabel.add(backButton);

        setVisible(true);
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
        
        if (!Common.ValidateString(amount, "Please enter the amount you want to withdrawal."))
        return;

        if(Integer.parseInt(amount) > 10000){
            JOptionPane.showMessageDialog(this, "The maximum withdrawal limit is 10,000", "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String queryFetchData = "SELECT * FROM bank WHERE pin = ?";
        String queryInsertData = "INSERT INTO bank VALUES(?, ?, ?, ?)";
        int balance = 0;

        try (MyCon con = new MyCon()) {
            PreparedStatement preparedStatementFetchData = con.connection.prepareStatement(queryFetchData);
            preparedStatementFetchData.setString(1, pin);

            PreparedStatement preparedStatementInsertData = con.connection.prepareStatement(queryInsertData);
            preparedStatementInsertData.setString(1, pin);
            preparedStatementInsertData.setString(2, date.toString());
            preparedStatementInsertData.setString(3, "Withdrawal");
            preparedStatementInsertData.setString(4, amount);

            // calculating the current balance from transaction history
            try(ResultSet resultSet = preparedStatementFetchData.executeQuery()){
                while(resultSet.next()){
                    if(resultSet.getString("type").equals("Deposit")){
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    }
                    else{
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }
                }
            }

            if(balance < Integer.parseInt(amount)){
                JOptionPane.showMessageDialog(this, "Insufficient Balance");
                return;
            }

            preparedStatementInsertData.executeUpdate();
            JOptionPane.showMessageDialog(this, "Rs. " + amount + " Withdrawn Successfully.");
            preparedStatementFetchData.close();
            preparedStatementInsertData.close();
            dispose();
            new AtmWindow(pin);

        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    @Override
    protected void handleResizing() {

    }

    public static void main(String[] args) {
        new Withdrawal("");
    }

}
