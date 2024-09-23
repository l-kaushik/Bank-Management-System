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

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

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

        JLabel balanceLabel = Common.CreateLabel("", new Font("System", Font.BOLD, 14),
                new Rectangle(20, 100, 400, 300));
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

    // Fetch balance entries from the database
    private List<Map<String, String>> fetchBalanceEntries(String pin) {
        List<Map<String, String>> balanceEntries = new ArrayList<>();
        try (MyCon con = new MyCon()) {
            String query = "SELECT * FROM bank WHERE pin = ?";
            PreparedStatement preparedStatement = con.connection.prepareStatement(query);
            preparedStatement.setString(1, pin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, String> entry = new HashMap<>();
                    entry.put("date", resultSet.getString("date"));
                    entry.put("type", resultSet.getString("type"));
                    entry.put("amount", resultSet.getString("amount"));
                    balanceEntries.add(entry);
                }
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balanceEntries;
    }

    // Update the balance label with formatted HTML
    private void updateBalanceLabel(JLabel balanceLabel, List<Map<String, String>> balanceEntries) {
        StringBuilder balanceHtml = new StringBuilder("<html>");
        for (int i = balanceEntries.size() - 1; i >= balanceEntries.size() - 8; i--) { // Reverse order
            Map<String, String> entry = balanceEntries.get(i);
            String type = entry.get("type");
            balanceHtml.append(entry.get("date")).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                    .append(type)
                    .append(type.equals("Deposit") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            : "&nbsp;&nbsp;&nbsp;")
                    .append(entry.get("amount")).append("<br><br>");
        }
        balanceHtml.append("</html>");
        balanceLabel.setText(balanceHtml.toString());
    }

    // Calculate and update the total balance
    private void updateTotalBalance(JLabel totalBalanceLabel, List<Map<String, String>> balanceEntries) {
        int balance = 0;
        for (Map<String, String> entry : balanceEntries) {
            String type = entry.get("type");
            int amount = Integer.parseInt(entry.get("amount"));
            if (type.equals("Deposit")) {
                balance += amount;
            } else {
                balance -= amount;
            }
        }
        totalBalanceLabel.setText("Your Total Balance is Rs. " + balance);
    }

    // Main method to set the balance labels
    private void setBalanceLabel(JLabel balanceLabel, JLabel totalBalanceLabel) {
        List<Map<String, String>> balanceEntries = fetchBalanceEntries(pin);
        updateBalanceLabel(balanceLabel, balanceEntries);
        updateTotalBalance(totalBalanceLabel, balanceEntries);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

    public static void main(String[] args) {
        new MiniStatement("1234");
    }
}
