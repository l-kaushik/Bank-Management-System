package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Pin extends ResizableATM implements ActionListener {

    String UID;

    JButton backButton;
    JButton changeButton;

    JLabel changeYourPinLabel;
    JLabel newPinLabel;
    JLabel reEnterNewPinLabel;

    JPasswordField firstPasswordField;
    JPasswordField secondPasswordField;

    Pin(String inUID) {

        super("Balance Enquiry");

        UID = inUID;

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        initializeLabels();
        initializeTextFields();
        initializeButton();
    }

    private void initializeLabels() {
        changeYourPinLabel = new JLabel("CHANGE YOUR PIN");
        changeYourPinLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(changeYourPinLabel);

        newPinLabel = new JLabel("New PIN: ");
        newPinLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(newPinLabel);

        reEnterNewPinLabel = new JLabel("Re-Enter New PIN: ");
        reEnterNewPinLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(reEnterNewPinLabel);
    }

    private void initializeTextFields() {
        firstPasswordField = new JPasswordField();
        backgroundImageLabel.add(firstPasswordField);

        secondPasswordField = new JPasswordField();
        backgroundImageLabel.add(secondPasswordField);
    }

    private void initializeButton() {
        changeButton = new JButton("Change PIN");
        Common.setButtonAttributes(changeButton, Color.black, this);
        backgroundImageLabel.add(changeButton);

        backButton = new JButton("BACK");
        Common.setButtonAttributes(backButton, Color.black, this);
        backgroundImageLabel.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == changeButton) {
            String pin1 = new String(firstPasswordField.getPassword());
            String pin2 = new String(secondPasswordField.getPassword());

            // pin validation
           if(!pinValidation(pin1, pin2)) return;

            // update pin in database
            updateTables(pin1, pin2);
        }

        if (e.getSource() == backButton) {
            new AtmWindow(UID);
            dispose();
        }
    }

    private boolean pinValidation(String pin1, String pin2) {
        if (!Common.ValidateString(pin1, "Please enter your PIN, in the first box."))
            return false;
        if (!Common.ValidateString(pin2, "Please enter your PIN, in the second box."))
            return false;
        if (!pin1.equals(pin2)) {
            JOptionPane.showMessageDialog(this, "Entered PINs are not matching.", "PIN mismatched",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // if (pin.equals(pin1)) {
        //     JOptionPane.showMessageDialog(this, "Cannot use old pin.", "PIN mismatched",
        //             JOptionPane.ERROR_MESSAGE);
        //     return false;
        // }

        return true;
    }

    private void updateTables(String pin1, String pin2) {
        try (MyCon con = new MyCon()) {

            String[] tableNames = { "login", "signupthree" };
            String baseQuery = "UPDATE %s SET pin = ? WHERE UID = ?";

            for (String table : tableNames) {
                String query = String.format(baseQuery, table);
                PreparedStatement preparedStatement = con.connection.prepareStatement(query);
                preparedStatement.setString(1, pin1);
                preparedStatement.setString(2, UID);

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

    @Override
    protected void handleResizing() {
        super.handleResizing();

        if (!isVisible())
            return;
        Dimension size = getSize();

        updateLabels(size);
        updateTextFields(size);
        updateButton(size);
    }

    private void updateLabels(Dimension size) {
        updateLabelFonts(size);
        updateChangeYourPinLabel(size);
        updateNewPinLabel(size);
        updateReEnterNewPinLabel(size);
    }

    private void updateLabelFonts(Dimension size) {
        Font scaledFont16 = Common.SystemBold16.deriveFont(size.width / 1400.0f * 16);
        Font scaledFont22 = new Font("System", Font.BOLD, (int) (size.width / 1400.0f * 22));
        
        changeYourPinLabel.setFont(scaledFont22);
        newPinLabel.setFont(scaledFont16);
        reEnterNewPinLabel.setFont(scaledFont16);
    }

    private void updateChangeYourPinLabel(Dimension size) {
        int x = (int) (size.width / 3.2);
        int y = (int) (size.height / 6);
        int width = (int) (size.width / 2.21);
        int height = (int) (size.height / 10);
        changeYourPinLabel.setBounds(x, y, width, height);
    }

    private void updateNewPinLabel(Dimension size) {
        int x = (int) (size.width / 3.6);
        int y = (int) (size.height / 4.1);
        int width = (int) (size.width / 2.21);
        int height = (int) (size.height / 10);
        newPinLabel.setBounds(x, y, width, height);
    }

    private void updateReEnterNewPinLabel(Dimension size) {
        int x = (int) (size.width / 3.6);
        int y = (int) (size.height / 3.4);
        int width = (int) (size.width / 2.21);
        int height = (int) (size.height / 10);
        reEnterNewPinLabel.setBounds(x, y, width, height);
    }

    private void updateTextFields(Dimension size) {
        updateTextFieldFonts(size);
        updateFirstPasswordField(size);
        updateSecondPasswordField(size);
    }

    private void updateTextFieldFonts(Dimension size) {
        Font scaledFont14 = new Font("System", Font.BOLD, (int) (size.width/1400.0f * 14));
       
        firstPasswordField.setFont(scaledFont14);
        secondPasswordField.setFont(scaledFont14);
    }

    private void updateFirstPasswordField(Dimension size) {
        int x = (int) (size.width / 2.5);
        int y = (int) (size.height / 3.6);
        int width = (int) (size.width / 7.7);
        int height = (int) (size.height / 35);
        firstPasswordField.setBounds(x, y, width, height);
    }

    private void updateSecondPasswordField(Dimension size) {
        int x = (int) (size.width / 2.5);
        int y = (int) (size.height / 3);
        int width = (int) (size.width / 7.7);
        int height = (int) (size.height / 35);
        secondPasswordField.setBounds(x, y, width, height);
    }

    private void updateButton(Dimension size) {
        updateButtonFonts(size);
        updateChangeButton(size);
        udpateBackButton(size);
    }

    private void updateButtonFonts(Dimension size) {
        Font scaledFont14 = Common.RalewayBold14.deriveFont(size.width/1400.0f * 14);
        
        changeButton.setFont(scaledFont14);
        backButton.setFont(scaledFont14);
    }

    private void updateChangeButton(Dimension size) {
        int x = (int) (size.width / 2.28);
        int y = (int) (size.height / 2.4);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        changeButton.setBounds(x, y, width, height);
    }

    private void udpateBackButton(Dimension size) {
        int x = (int) (size.width / 2.28);
        int y = (int) (size.height / 2.1);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);;
        backButton.setBounds(x, y, width, height);
    }

    public static void main(String[] args) {
        new Pin("");
    }

}
