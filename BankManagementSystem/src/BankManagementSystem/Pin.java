package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Pin extends JFrame implements ActionListener {

    String pin;

    JButton backButton;
    JButton changeButton;

    JPasswordField firstPasswordField;
    JPasswordField secondPasswordField;

    Pin(String inPin) {

        pin = inPin;

        Common.InitializeJFrame(this, "BalanceEnquiry Money", null, new Dimension(1550, 1080), JFrame.EXIT_ON_CLOSE,
                false,
                new Point(0, 0));

        JLabel backgroundImageLabel = Common.GetScaledImageWithLabel("icons/atm2.png", 1550, 830);
        backgroundImageLabel.setBounds(0, 0, 1550, 830);
        add(backgroundImageLabel);

        JLabel changeYourPinLabel = Common.CreateLabel("CHANGE YOUR PIN", Color.WHITE,
                Common.SystemBold28, new Rectangle(430, 180, 400, 35));
        backgroundImageLabel.add(changeYourPinLabel);

        JLabel newPinLabel = Common.CreateLabel("New PIN: ", Color.WHITE,
                Common.SystemBold16, new Rectangle(430, 250, 150, 35));
        backgroundImageLabel.add(newPinLabel);

        firstPasswordField = new JPasswordField();
        firstPasswordField.setBounds(600, 250, 200, 35);
        backgroundImageLabel.add(firstPasswordField);

        JLabel reEnterNewPinLabel = Common.CreateLabel("Re-Enter New PIN: ", Color.WHITE,
                Common.SystemBold16, new Rectangle(430, 300, 200, 35));
        backgroundImageLabel.add(reEnterNewPinLabel);

        secondPasswordField = new JPasswordField();
        secondPasswordField.setBounds(600, 300, 200, 35);
        backgroundImageLabel.add(secondPasswordField);

        changeButton = Common.CreateButton("Change PIN", Common.RalewayBold14, Color.BLACK,
                new Rectangle(700, 406, 150, 35), this);
        backgroundImageLabel.add(changeButton);

        backButton = Common.CreateButton("BACK", Common.RalewayBold14, Color.BLACK,
                new Rectangle(700, 406, 150, 35), this);
        backgroundImageLabel.add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == changeButton) {
            String pin1 = new String(firstPasswordField.getPassword());
            String pin2 = new String(secondPasswordField.getPassword());

            // pin validation
            pinValidation(pin1, pin2);

            // update pin in database
            updateTables(pin1, pin2);
        }

        if (e.getSource() == backButton) {
            new AtmWindow(pin);
            dispose();
        }
    }

    private void pinValidation(String pin1, String pin2) {
        if (!Common.ValidateString(pin1, "Please enter your PIN, in the first box."))
            return;
        if (!Common.ValidateString(pin2, "Please enter your PIN, in the second box."))
            return;
        if (!pin1.equals(pin2)) {
            JOptionPane.showMessageDialog(this, "Entered PINs are not matching.", "PIN mismatched",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (pin.equals(pin1)) {
            JOptionPane.showMessageDialog(this, "Cannot use old pin.", "PIN mismatched",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void updateTables(String pin1, String pin2) {
        try (MyCon con = new MyCon()) {

            String[] tableNames = { "bank", "login", "signupthree" };
            String baseQuery = "UPDATE %s SET pin = ? WHERE pin = ?";

            for (String table : tableNames) {
                String query = String.format(baseQuery, table);
                PreparedStatement preparedStatement = con.connection.prepareStatement(query);
                preparedStatement.setString(1, pin1);
                preparedStatement.setString(2, pin);

                preparedStatement.executeUpdate();
                preparedStatement.close();
            }

            JOptionPane.showMessageDialog(this, "PIN changed successfully", "PIN Change Success",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            new AtmWindow(pin1);
            dispose();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Pin("");
    }

}
