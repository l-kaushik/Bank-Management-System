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
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class FastCash extends JFrame implements ActionListener {

    String pin;

    JButton oneHundredsButton;
    JButton fiveHundredsButton;
    JButton oneThousandsButton;
    JButton twoThousandsButton;
    JButton fiveThousandsButton;
    JButton tenThousandsButton;
    JButton backtButton;

    FastCash(String inPin) {
        pin = inPin;

        Common.InitializeJFrame(this, "Deposit Money", null, new Dimension(1550, 1080), JFrame.EXIT_ON_CLOSE, false,
                new Point(0, 0));

        JLabel backgroundImageLabel = Common.GetScaledImageWithLabel("icons/atm2.png", 1550, 830);
        backgroundImageLabel.setBounds(0, 0, 1550, 830);
        add(backgroundImageLabel);

        JLabel selectTransactionLabel = Common.CreateLabel("SELECT WITHDRAWAL AMOUNT", Color.WHITE,
                new Font("System", Font.BOLD, 24), new Rectangle(440, 180, 700, 35));
        backgroundImageLabel.add(selectTransactionLabel);

        oneHundredsButton = Common.CreateButton("Rs. 100", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 270, 150, 35), this);
        backgroundImageLabel.add(oneHundredsButton);

        fiveHundredsButton = Common.CreateButton("Rs. 500", Common.RalewayBold14, Color.BLACK,
                new Rectangle(680, 270, 170, 35), this);
        backgroundImageLabel.add(fiveHundredsButton);

        oneThousandsButton = Common.CreateButton("Rs. 1000", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 315, 150, 35), this);
        backgroundImageLabel.add(oneThousandsButton);

        twoThousandsButton = Common.CreateButton("Rs. 2000", Common.RalewayBold14, Color.BLACK,
                new Rectangle(680, 315, 170, 35), this);
        backgroundImageLabel.add(twoThousandsButton);

        fiveThousandsButton = Common.CreateButton("Rs. 5000", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 360, 150, 35), this);
        backgroundImageLabel.add(fiveThousandsButton);

        tenThousandsButton = Common.CreateButton("Rs. 10000", new Font("Raleway", Font.BOLD, 13), Color.BLACK,
                new Rectangle(680, 360, 170, 35), this);
        backgroundImageLabel.add(tenThousandsButton);

        backtButton = Common.CreateButton("BACK", Common.RalewayBold14, Color.BLACK, new Rectangle(680, 405, 170, 35),
                this);
        backgroundImageLabel.add(backtButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backtButton) {
            new AtmWindow(pin);
            dispose();
        }

        String amount = ((JButton) e.getSource()).getText().substring(4);
        withdrawal(amount);

        dispose();
        new AtmWindow(pin);

    }

    private void withdrawal(String amount) {
        Date date = new Date();

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
            try (ResultSet resultSet = preparedStatementFetchData.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }
                }
            }

            if (balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(this, "Insufficient Balance");
                return;
            }

            preparedStatementInsertData.executeUpdate();
            JOptionPane.showMessageDialog(this, "Rs. " + amount + " Withdrawn Successfully.");

            preparedStatementFetchData.close();
            preparedStatementInsertData.close();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FastCash("");
    }

}
