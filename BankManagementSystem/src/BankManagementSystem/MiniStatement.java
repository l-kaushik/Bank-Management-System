package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MiniStatement extends JFrame implements ActionListener {

    String pin;

    JButton exitButton;

    MiniStatement(String inPin) {
        pin = inPin;

        Common.InitializeJFrame(this, "Mini Statement", null, new Dimension(400, 600), JFrame.EXIT_ON_CLOSE, false,
                new Point(20, 20), new Color(255, 204, 204));

        JLabel balanceLabel = Common.CreateLabel("", new Font("System", Font.BOLD, 14), new Rectangle(20, 100, 400, 300));
        add(balanceLabel);

        JLabel bankNameLabel = Common.CreateLabel("IDK Bank", Common.SystemBold16, new Rectangle(150, 20, 200, 20));
        add(bankNameLabel);

        JLabel cardNumberLabel = Common.CreateLabel("", Common.SystemBold16, new Rectangle(20, 80, 300, 20));
        add(cardNumberLabel);

        JLabel totalBalanceLabel = Common.CreateLabel("", Common.SystemBold16, new Rectangle(20, 450, 300, 20));
        add(totalBalanceLabel);

        exitButton = Common.CreateButton("Exit", Common.SystemBold16, Color.BLACK, new Rectangle(20, 500, 100, 25),
                this);

        add(exitButton);

        setCardNumberLabel(cardNumberLabel);
        setBalanceLabel(balanceLabel, totalBalanceLabel);

        setVisible(true);
    }

    private void setCardNumberLabel(JLabel cardNumberLabel) {
        try (MyCon con = new MyCon()) {
            String query = "SELECT * FROM login WHERE pin = ?";

            PreparedStatement preparedStatement = con.connection.prepareStatement(query);
            preparedStatement.setString(1, pin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cardNumberLabel.setText("Card Number: " 
                        + resultSet.getString("card_number").substring(0, 4) + "XXXXXXXX"
                        + resultSet.getString("card_number").substring(12));
                }
            }

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBalanceLabel(JLabel balanceLabel, JLabel totalBalanceLabel) {
        int balance = 0;
        try (MyCon con = new MyCon()) {
            String query = "SELECT * FROM bank WHERE pin = ?";

            PreparedStatement preparedStatement = con.connection.prepareStatement(query);
            preparedStatement.setString(1, pin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
    
                    balanceLabel.setText(balanceLabel.getText() + "<html>"
                        + resultSet.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
                        + resultSet.getString("type")
                        + (resultSet.getString("type").equals("Deposit") ? 
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "&nbsp;&nbsp;&nbsp;")
                        + resultSet.getString("amount") + "<br><br><html>");
    
                    if (resultSet.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }
                }
            }

            totalBalanceLabel.setText("Your Total Balance is Rs. " + balance);

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

    public static void main(String[] args) {
        new MiniStatement("1111");
    }
}
