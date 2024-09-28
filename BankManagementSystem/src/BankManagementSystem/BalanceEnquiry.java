package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;

class BalanceEnquiry extends ResizableATM implements ActionListener {

    String UID = null;

    JButton backButton;

    JLabel currentBalanceLabel;
    JLabel amountLabel;

    BalanceEnquiry(String inUID) {

        super("Balance Enquiry");

        UID = inUID;

        currentBalanceLabel = new JLabel("Your Current Balance is Rs. ");
        currentBalanceLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(currentBalanceLabel);

        amountLabel = new JLabel("");
        amountLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(amountLabel);

        backButton = new JButton("BACK");
        Common.setButtonAttributes(backButton, Color.BLACK, this);
        backgroundImageLabel.add(backButton);

        setBalance(amountLabel);

        setVisible(true);
    }

    private void setBalance(JLabel amountLabel) {
        int balance = 0;
        try (MyCon con = new MyCon()) {
            String fetchQuery = "SELECT * FROM bank WHERE UID = ?";
            PreparedStatement preparedStatementFetchData = con.connection.prepareStatement(fetchQuery);
            preparedStatementFetchData.setString(1, UID);

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
            new AtmWindow(UID);
            this.dispose();
        }
    }

    @Override
    protected void handleResizing() {

        super.handleResizing();

        if (!isVisible())
            return;

        Dimension size = getSize();

        updateCurrentBalanceLabel(size);
        updateAmountLabel(size);
        updateBackButton(size);

    }

    private void updateCurrentBalanceLabel(Dimension size) {

        currentBalanceLabel.setFont(Common.SystemBold16.deriveFont(size.width / 1400.0f * 16));

        int x = (int) (size.width / 3.6);
        int y = (int) (size.height / 6);
        int width = (int) (size.width / 2.21);
        int height = (int) (size.height / 10);
        currentBalanceLabel.setBounds(x, y, width, height);

    }

    private void updateAmountLabel(Dimension size) {
        amountLabel.setFont(Common.SystemBold16.deriveFont(size.width / 1400.0f * 16));

        int x = (int) (size.width / 3.6);
        int y = (int) (size.height / 4.5);
        int width = (int) (size.width / 2.21);
        int height = (int) (size.height / 10);
        amountLabel.setBounds(x, y, width, height);
    }

    private void updateBackButton(Dimension size) {
        backButton.setFont(Common.RalewayBold14.deriveFont(size.width / 1400.0f * 14));

        int x = (int) (size.width / 2.25);
        int y = (int) (size.height / 2.1);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        backButton.setBounds(x, y, width, height);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("1111");
    }

}