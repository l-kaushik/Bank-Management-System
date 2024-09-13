package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class BalanceEnquiry extends JFrame implements ActionListener {

    String pin = null;

    JButton backButton;

    JLabel amountLabel;

    BalanceEnquiry(String inPin) {

        pin = inPin;

        Common.InitializeJFrame(this, "BalanceEnquiry Money", null, new Dimension(1550, 1080), JFrame.EXIT_ON_CLOSE,
                false,
                new Point(0, 0));

        JLabel backgroundImageLabel = Common.GetScaledImageWithLabel("icons/atm2.png", 1550, 830);
        backgroundImageLabel.setBounds(0, 0, 1550, 830);
        add(backgroundImageLabel);

        JLabel currentBalanceLabel = Common.CreateLabel("Your Current Balance is Rs. ", Color.WHITE,
                Common.SystemBold16, new Rectangle(430, 180, 700, 35));
        backgroundImageLabel.add(currentBalanceLabel);

        amountLabel = Common.CreateLabel("", Color.WHITE,
                Common.SystemBold16, new Rectangle(430, 220, 400, 35));
        backgroundImageLabel.add(amountLabel);

        backButton = Common.CreateButton("BACK", Common.RalewayBold14, Color.BLACK,
                new Rectangle(700, 406, 150, 35), this);
        backgroundImageLabel.add(backButton);

        setBalance(amountLabel);

        setVisible(true);
    }

    private void setBalance(JLabel amountLabel) {
        int balance = 0;
        try (MyCon con = new MyCon()) {
            String fetchQuery = "SELECT * FROM bank WHERE pin = ?";
            PreparedStatement preparedStatementFetchData = con.connection.prepareStatement(fetchQuery);
            preparedStatementFetchData.setString(1, pin);

            try (ResultSet resultSet = preparedStatementFetchData.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }
                }
            }

            amountLabel.setText("" + balance);
            preparedStatementFetchData.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            new AtmWindow(pin);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new BalanceEnquiry("1111");
    }

}