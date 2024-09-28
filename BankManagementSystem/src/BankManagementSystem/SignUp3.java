package BankManagementSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Random;

public class SignUp3 extends ResizableFrame implements ActionListener {

    String UID = null;

    JRadioButton savingAccountRadioButton;
    JRadioButton fixedDepopsiteRadioButton;
    JRadioButton currentAccountRadioButton;
    JRadioButton recurringDepositeRadioButton;

    JCheckBox ATMCardCheckBox;
    JCheckBox internetBankingCheckBox;
    JCheckBox mobileBankingCheckBox;
    JCheckBox emailAlertsCheckBox;
    JCheckBox chequeBookCheckBox;
    JCheckBox eStatementCheckBox;
    JCheckBox legalStaementCheckBox;

    JButton submitButton;
    JButton cancelButton;

    JPanel contentPanel;

    JLabel topCenterImage;
    JLabel pageNumberLabel;
    JLabel formNoLabel;
    JLabel accountDetailsLabel;
    JLabel accountTypeLabel;
    JLabel cardNumberLabel;
    JLabel cardNumberInfoLabel;
    JLabel cardNumberFormatLabel;
    JLabel cardNumberInfoLabel2;
    JLabel pinNumberLabel;
    JLabel pinNumberInfoLabel;
    JLabel pinNumberFormatInfoLabel;



    SignUp3(String inUID) {

        UID = inUID;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setupFrame(screenSize);
        initializeComponents(screenSize);
        setVisible(true);
    }

    private void setupFrame(Dimension screenSize) {
        setTitle("Application Form");
        setLayout(new BorderLayout());
        setSize((int) (screenSize.width / 2), (int) (screenSize.height * 0.7));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initializeComponents(Dimension screenSize) {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Common.FrameBackgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 50, 10, 50);
        gbc.weightx = 1.0;

        // components initialization here
        initializeTopCenterImage(gbc, screenSize);
        initializetopDetailsLabel(gbc);
        initializeAccountTypeSelection(gbc);
        initializeCardNumberLabels(gbc);
        initializePinNumberLabels(gbc);
        initializeServiceRequiredSelection(gbc);
        initializeAcceptTermsLabel(gbc);
        initializeSubmitButton(gbc);
        initializeCancelButton(gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeTopCenterImage(GridBagConstraints gbc, Dimension screenSize) {
        // top center image
        topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", 100, 100);
        scaleTopCenterImage(screenSize);
    }

    private void initializetopDetailsLabel(GridBagConstraints gbc) {
        JPanel topDetailsPanel = new JPanel();
        topDetailsPanel.setLayout(new GridLayout(2, 2, 20, 10));
        topDetailsPanel.setBackground(Common.FrameBackgroundColor);

        pageNumberLabel = new JLabel("Page 3");
        topDetailsPanel.add(pageNumberLabel);

        formNoLabel = new JLabel("Form No: 1123");
        formNoLabel.setBorder(new EmptyBorder(0, 50, 0, 0));
        topDetailsPanel.add(formNoLabel);

        accountDetailsLabel = new JLabel("Account Details");
        topDetailsPanel.add(accountDetailsLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 50);
        contentPanel.add(topDetailsPanel, gbc);
    }

    private void initializeAccountTypeSelection(GridBagConstraints gbc) {
        initializeAccountTypeLabel(gbc);
        initializeAccountTypeButtons(gbc);
    }

    private void initializeAccountTypeLabel(GridBagConstraints gbc) {
        accountTypeLabel = new JLabel("Account Type: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 50, 10, 50);
        contentPanel.add(accountTypeLabel, gbc);
    }

    private void initializeAccountTypeButtons(GridBagConstraints gbc) {

        JPanel accountTypeButtonPanel = initializeAccountTypeButtonPanel(gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        ButtonGroup accountTypeButtonGroup = new ButtonGroup();
        accountTypeButtonGroup.add(savingAccountRadioButton);
        accountTypeButtonGroup.add(currentAccountRadioButton);
        accountTypeButtonGroup.add(fixedDepopsiteRadioButton);
        accountTypeButtonGroup.add(recurringDepositeRadioButton);

        contentPanel.add(accountTypeButtonPanel, gbc);
    }

    private JPanel initializeAccountTypeButtonPanel(GridBagConstraints gbc) {
        JPanel accountTypePanel = new JPanel();
        accountTypePanel.setLayout(new GridLayout(2, 2, 20, 10));
        accountTypePanel.setBackground(Common.FrameBackgroundColor);

        savingAccountRadioButton = new JRadioButton("Saving Account");
        savingAccountRadioButton.setBackground(Common.FrameBackgroundColor);
        savingAccountRadioButton.setFocusable(false);
        accountTypePanel.add(savingAccountRadioButton);

        fixedDepopsiteRadioButton = new JRadioButton("Fixed Deposite Account");
        fixedDepopsiteRadioButton.setBackground(Common.FrameBackgroundColor);
        fixedDepopsiteRadioButton.setFocusable(false);
        accountTypePanel.add(fixedDepopsiteRadioButton);

        currentAccountRadioButton = new JRadioButton("Current Account");
        currentAccountRadioButton.setBackground(Common.FrameBackgroundColor);
        currentAccountRadioButton.setFocusable(false);
        accountTypePanel.add(currentAccountRadioButton);

        recurringDepositeRadioButton = new JRadioButton("Recurring Deposite Account");
        recurringDepositeRadioButton.setBackground(Common.FrameBackgroundColor);
        recurringDepositeRadioButton.setFocusable(false);
        accountTypePanel.add(recurringDepositeRadioButton);

        return accountTypePanel;
    }

    private void initializeCardNumberLabels(GridBagConstraints gbc) {
        JPanel cardDetailsPanel = new JPanel(new GridBagLayout());
        cardDetailsPanel.setBackground(Common.FrameBackgroundColor);

        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.WEST;
        innerGbc.insets = new Insets(0, 0, 0, 20); // Add spacing between columns

        // First row: Card Number label and Format label
        cardNumberLabel = new JLabel("Card Number: ");
        cardDetailsPanel.add(cardNumberLabel, innerGbc);

        innerGbc.gridx = 1;
        cardNumberFormatLabel = new JLabel("XXXX-XXXX-XXXX-4841");
        cardDetailsPanel.add(cardNumberFormatLabel, innerGbc);

        // Second row: Additional info labels
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        cardNumberInfoLabel = new JLabel("(Your 16-digit Card Number)");
        cardDetailsPanel.add(cardNumberInfoLabel, innerGbc);

        innerGbc.gridx = 1;
        cardNumberInfoLabel2 = new JLabel("(It would appear on atm card/cheque Book and Statements)");
        cardDetailsPanel.add(cardNumberInfoLabel2, innerGbc);

        // Add the cardDetailsPanel to the content panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 30, 10, 50);

        contentPanel.add(cardDetailsPanel, gbc);
    }

    private void initializePinNumberLabels(GridBagConstraints gbc) {

        JPanel pinDetailsPanel = new JPanel(new GridBagLayout());
        pinDetailsPanel.setBackground(Common.FrameBackgroundColor);
    
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.WEST;
        innerGbc.insets = new Insets(0, 0, 0, 50); 
    
        pinNumberLabel = new JLabel("PIN: ");
        pinDetailsPanel.add(pinNumberLabel, innerGbc);
    
        innerGbc.gridx = 1;
        pinNumberInfoLabel = new JLabel("XXXX");
        pinNumberInfoLabel.setBorder(new EmptyBorder(0, 30, 0, 0));
        pinDetailsPanel.add(pinNumberInfoLabel, innerGbc);
    
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        pinNumberFormatInfoLabel = new JLabel("(4-digit Password)");
        pinDetailsPanel.add(pinNumberFormatInfoLabel, innerGbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,100, 10, 50);
        contentPanel.add(pinDetailsPanel, gbc);
    
        // updatePinDetailsPanel will add it to content panel, no need to call here
    }
    
    private void initializeServiceRequiredSelection(GridBagConstraints gbc) {
        // Create a panel for services with GridBagLayout
        JPanel servicePanel = new JPanel(new GridBagLayout());
        servicePanel.setBackground(Common.FrameBackgroundColor);
    
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.insets = new Insets(10, 20, 10, 20); // Add spacing between components
        innerGbc.anchor = GridBagConstraints.WEST;
    
        // First row: ATM Card and Internet Banking
        ATMCardCheckBox = new JCheckBox("ATM Card");
        ATMCardCheckBox.setFocusable(false);
        ATMCardCheckBox.setBackground(Common.FrameBackgroundColor);
        ATMCardCheckBox.setFont(Common.RalewayBold14);
        servicePanel.add(ATMCardCheckBox, innerGbc);
    
        innerGbc.gridx = 1;
        internetBankingCheckBox = new JCheckBox("Internet Banking");
        internetBankingCheckBox.setFocusable(false);
        internetBankingCheckBox.setBackground(Common.FrameBackgroundColor);
        internetBankingCheckBox.setFont(Common.RalewayBold14);
        servicePanel.add(internetBankingCheckBox, innerGbc);
    
        // Second row: Mobile Banking and Email Alerts
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        mobileBankingCheckBox = new JCheckBox("Mobile Banking");
        mobileBankingCheckBox.setFocusable(false);
        mobileBankingCheckBox.setBackground(Common.FrameBackgroundColor);
        mobileBankingCheckBox.setFont(Common.RalewayBold14);
        servicePanel.add(mobileBankingCheckBox, innerGbc);
    
        innerGbc.gridx = 1;
        emailAlertsCheckBox = new JCheckBox("Email Alerts");
        emailAlertsCheckBox.setFocusable(false);
        emailAlertsCheckBox.setBackground(Common.FrameBackgroundColor);
        emailAlertsCheckBox.setFont(Common.RalewayBold14);
        servicePanel.add(emailAlertsCheckBox, innerGbc);
    
        // Third row: Cheque Book and E-Statement
        innerGbc.gridx = 0;
        innerGbc.gridy = 2;
        chequeBookCheckBox = new JCheckBox("Cheque Book");
        chequeBookCheckBox.setFocusable(false);
        chequeBookCheckBox.setBackground(Common.FrameBackgroundColor);
        chequeBookCheckBox.setFont(Common.RalewayBold14);
        servicePanel.add(chequeBookCheckBox, innerGbc);
    
        innerGbc.gridx = 1;
        eStatementCheckBox = new JCheckBox("E-Statement");
        eStatementCheckBox.setFocusable(false);
        eStatementCheckBox.setBackground(Common.FrameBackgroundColor);
        eStatementCheckBox.setFont(Common.RalewayBold14);
        servicePanel.add(eStatementCheckBox, innerGbc);
    
        // Add the Service Label at the top
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 50, 10, 50);
        JLabel serviceLabel = new JLabel("Services Required: ");
        serviceLabel.setFont(Common.RalewayBold18);
        contentPanel.add(serviceLabel, gbc);
    
        // Add the servicePanel to the content panel
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(servicePanel, gbc);
    }
    

    private void initializeAcceptTermsLabel(GridBagConstraints gbc) {
        legalStaementCheckBox = new JCheckBox(
                "I here by declares that the above entered details correct to the best of my knowledge.");
        legalStaementCheckBox.setBackground(Common.FrameBackgroundColor);
        legalStaementCheckBox.setFocusable(false);
        legalStaementCheckBox.setSelected(true);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 50, 10, 50);
        contentPanel.add(legalStaementCheckBox, gbc);
    }

    private void initializeSubmitButton(GridBagConstraints gbc) {
        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.setForeground(Color.BLACK);
        submitButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 50, 50, 50);
        contentPanel.add(submitButton, gbc);
    }

    private void initializeCancelButton(GridBagConstraints gbc) {

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(this);

        gbc.gridx = 1;
        gbc.gridy = 9;
        contentPanel.add(cancelButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cancelButton) {
            // show a dialog box of all information will be removed and remove data from
            // database
            System.exit(0);
        }

        submitButtonAction();

    }

    private void submitButtonAction() {
        String accountType = extractAccountTypeSelection();
        String cardNo = generateCardNumber();
        String pin = generatePinNumber();
        String facilites = extractFacilitiesSelected();

        if (!validateForm(accountType))
            return;

        insertIntoDatabase(accountType, cardNo, pin, facilites);

        new Deposit(UID);
        dispose();
    }

    private String extractAccountTypeSelection() {
        if (savingAccountRadioButton.isSelected()) {
            return savingAccountRadioButton.getText();
        } else if (currentAccountRadioButton.isSelected()) {
            return currentAccountRadioButton.getText();
        } else if (fixedDepopsiteRadioButton.isSelected()) {
            return fixedDepopsiteRadioButton.getText();
        } else if (recurringDepositeRadioButton.isSelected()) {
            return recurringDepositeRadioButton.getText();
        }

        return null;
    }

    private String generateCardNumber() {
        Random random = new Random();
        long first7 = (random.nextLong() % 90000000L) + 1409963000000000L;
        String cardNo = "" + Math.abs(first7);

        return cardNo;
    }

    private String generatePinNumber() {
        Random random = new Random();
        long first3 = (random.nextLong() % 9000L) + 1000L;
        String pin = "" + Math.abs(first3);

        return pin;
    }

    private String extractFacilitiesSelected() {
        String facilites = "";
        if (ATMCardCheckBox.isSelected()) {
            facilites += ATMCardCheckBox.getText() + ", ";
        }
        if (internetBankingCheckBox.isSelected()) {
            facilites += internetBankingCheckBox.getText() + ", ";
        }
        if (mobileBankingCheckBox.isSelected()) {
            facilites += mobileBankingCheckBox.getText() + ", ";
        }
        if (emailAlertsCheckBox.isSelected()) {
            facilites += emailAlertsCheckBox.getText() + ", ";
        }
        if (chequeBookCheckBox.isSelected()) {
            facilites += chequeBookCheckBox.getText() + ", ";
        }
        if (eStatementCheckBox.isSelected()) {
            facilites += eStatementCheckBox.getText() + ", ";
        }

        // removing additional comma and space
        if (!facilites.isEmpty()) {
            facilites = facilites.substring(0, facilites.length() - 2);
        }

        return facilites;
    }

    private boolean validateForm(String accountType) {
        if (!Common.ValidateString(accountType, "In account type, you must select any one option.")) {
            return false;
        }
        if (!legalStaementCheckBox.isSelected()) {
            Common.ValidateString(null, "Check the box to assure that entered information is legit.");
            return false;
        }

        return true;
    }

    private void insertIntoDatabase(String accountType, String cardNo, String pin, String facilites) {

        try (MyCon con = new MyCon()) {

            insertIntoSignupTable3(con, UID, accountType, cardNo, pin, facilites);
            insertIntoLoginTable(con, UID, cardNo, pin);

            JOptionPane.showMessageDialog(null, "Card Number: " + cardNo + "\nPin: " + pin, "Card information",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception E) {
            E.getStackTrace();
        }
    }

    private void insertIntoSignupTable3(MyCon con, String UID, String accountType, String cardNo, String pin,
            String facilities) {
        String queryForSignupTable3 = "INSERT INTO signupthree VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement preaparedStatementForSignupTable3 = con.connection
                .prepareStatement(queryForSignupTable3)) {
            preaparedStatementForSignupTable3.setString(1, UID);
            preaparedStatementForSignupTable3.setString(2, accountType);
            preaparedStatementForSignupTable3.setString(3, cardNo);
            preaparedStatementForSignupTable3.setString(4, pin);
            preaparedStatementForSignupTable3.setString(5, facilities);

            preaparedStatementForSignupTable3.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoLoginTable(MyCon con, String UID, String cardNo, String pin) {
        String queryForLoginTable = "INSERT INTO login VALUES(?, ?, ?)";

        try (PreparedStatement preaparedStatementForLoginTable = con.connection.prepareStatement(queryForLoginTable)) {
            preaparedStatementForLoginTable.setString(1, UID);
            preaparedStatementForLoginTable.setString(2, cardNo);
            preaparedStatementForLoginTable.setString(3, pin);

            preaparedStatementForLoginTable.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleResizing() {
        // prevents resizing when frame is not visible
        if (!this.isVisible())
            return;

        Dimension size = this.getSize();

        updateFonts(size);

        // Rescale image
        if (topCenterImage.isValid())
            scaleTopCenterImage(size);

        // Revalidate and repaint to apply changes
        this.revalidate();
        this.repaint();
    }

    private void updateFonts(Dimension size) {
        float scaleFactor = size.width / 800.0f;
        updateLabelFonts(scaleFactor);
        updateButtonsFonts(scaleFactor);
        updateCheckBoxFonts(scaleFactor);
    }

    private void updateLabelFonts(float scaleFactor) {
        Font scaledFont22 = Common.RalewayBold22.deriveFont(22 * scaleFactor);
        Font scaledFont18 = Common.RalewayBold18.deriveFont(18 * scaleFactor);
        Font scaledFont14 = Common.RalewayBold14.deriveFont(14 * scaleFactor);
        Font scaledFont12 = new Font("Raleway", Font.BOLD, (int) (12 * scaleFactor));

        pageNumberLabel.setFont(scaledFont22);
        formNoLabel.setFont(scaledFont14);
        accountDetailsLabel.setFont(scaledFont22);
        accountTypeLabel.setFont(scaledFont18);
        cardNumberLabel.setFont(scaledFont18);
        cardNumberInfoLabel.setFont(scaledFont12);
        cardNumberFormatLabel.setFont(scaledFont18);
        cardNumberInfoLabel2.setFont(scaledFont12);
        pinNumberLabel.setFont(scaledFont18);
        pinNumberInfoLabel.setFont(scaledFont18);
        pinNumberFormatInfoLabel.setFont(scaledFont12);
    }

    private void updateButtonsFonts(float scaleFactor) {
        Font scaledFont14 = Common.RalewayBold14.deriveFont(14 * scaleFactor);

        savingAccountRadioButton.setFont(scaledFont14);
        fixedDepopsiteRadioButton.setFont(scaledFont14);
        currentAccountRadioButton.setFont(scaledFont14);
        recurringDepositeRadioButton.setFont(scaledFont14);
        submitButton.setFont(scaledFont14);
        cancelButton.setFont(scaledFont14);
    }

    private void updateCheckBoxFonts(float scaleFactor) {
        Font scaledFont12 = new Font("Raleway", Font.BOLD, (int)(12 * scaleFactor));

        ATMCardCheckBox.setFont(scaledFont12);
        internetBankingCheckBox.setFont(scaledFont12);
        mobileBankingCheckBox.setFont(scaledFont12);
        emailAlertsCheckBox.setFont(scaledFont12);
        chequeBookCheckBox.setFont(scaledFont12);
        eStatementCheckBox.setFont(scaledFont12);
        legalStaementCheckBox.setFont(scaledFont12);
    }

    private void scaleTopCenterImage(Dimension size) {

        // Update GridBagConstraints properties dynamically
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;

        // Remove the old image
        contentPanel.remove(topCenterImage);

        // Create new top-center image with scaled size
        int newImageWidth = (int) (size.width / 8);
        int newImageHeight = (int) (size.height / 6);
        topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", newImageWidth, newImageHeight);

        // Re-add the resized image to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 50, 10, 10);
        contentPanel.add(topCenterImage, gbc);
    }

    public static void main(String[] args) {
        new SignUp3("0000");
    }

}
