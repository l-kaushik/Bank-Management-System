package BankManagementSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.Random;

public class SignUp3 extends ResizableFrame implements ActionListener{

    String formNo = null;

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

    
    SignUp3(String informNo) {

        formNo = informNo;

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
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void initializeComponents(Dimension screenSize) {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Common.FrameBackgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
        gbc.weightx = 1.0;

        // components initialization here

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeTopCenterImage(GridBagConstraints gbc) {
        // top center image
        JLabel topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", 100, 100);
        topCenterImage.setBounds(150, 5, 100, 100);
        add(topCenterImage);
    }

    private void initializePageAndFormNumberLabel(GridBagConstraints gbc) {
        // show page number
        JLabel pageNumberLabel = Common.CreateLabel("Page 3", Common.RalewayBold22, new Rectangle(280, 40, 400, 40));
        add(pageNumberLabel);

        JLabel formNoLabel = Common.CreateLabel("Form No: ", Common.RalewayBold14, new Rectangle(670, 10, 100, 30));
        add(formNoLabel);

        JLabel formNoTextLabel = Common.CreateLabel(formNo, Common.RalewayBold14, new Rectangle(760, 10, 100, 30));
        add(formNoTextLabel);
    }
    
    private void initializeAccountDetailsLabel(GridBagConstraints gbc) {
        // show account detail on page
        JLabel accountDetailsLabel = Common.CreateLabel("Account Details", Common.RalewayBold22,
                new Rectangle(280, 70, 400, 40));
        add(accountDetailsLabel);
    }

    private void initializeAccountTypeSelection(GridBagConstraints gbc) {
        JLabel accountTypeLabel = Common.CreateLabel("Account Type: ", Common.RalewayBold18,
                new Rectangle(100, 140, 200, 30));
        add(accountTypeLabel);

        savingAccountRadioButton = Common.CreateRadioButton("Saving Account", Common.RalewayBold14,
                Common.FrameBackgroundColor, new Rectangle(100, 180, 150, 30));
        add(savingAccountRadioButton);

        fixedDepopsiteRadioButton = Common.CreateRadioButton("Fixed Deposite Account", Common.RalewayBold14,
                Common.FrameBackgroundColor, new Rectangle(350, 180, 300, 30));
        add(fixedDepopsiteRadioButton);

        currentAccountRadioButton = Common.CreateRadioButton("Current Account", Common.RalewayBold14,
                Common.FrameBackgroundColor, new Rectangle(100, 210, 250, 30));
        add(currentAccountRadioButton);

        recurringDepositeRadioButton = Common.CreateRadioButton("Recurring Deposite Account", Common.RalewayBold14,
                Common.FrameBackgroundColor, new Rectangle(350, 210, 300, 30));
        add(recurringDepositeRadioButton);

        ButtonGroup accountTypeButtonGroup = new ButtonGroup();
        accountTypeButtonGroup.add(savingAccountRadioButton);
        accountTypeButtonGroup.add(currentAccountRadioButton);
        accountTypeButtonGroup.add(fixedDepopsiteRadioButton);
        accountTypeButtonGroup.add(recurringDepositeRadioButton);
    }

    private void initializeCardNumberLabels(GridBagConstraints gbc) {

        JLabel cardNumberLabel = Common.CreateLabel("Card Number: ", Common.RalewayBold18,
                new Rectangle(100, 300, 200, 30));
        add(cardNumberLabel);

        JLabel cardNumberInfoLabel = Common.CreateLabel("(Your 16-digit Card Number)",
                new Font("Raleway", Font.BOLD, 12), new Rectangle(100, 330, 200, 20));
        add(cardNumberInfoLabel);

        JLabel cardNumberFormatLabel = Common.CreateLabel("XXXX-XXXX-XXXX-4841", Common.RalewayBold18,
                new Rectangle(330, 300, 250, 30));
        add(cardNumberFormatLabel);

        JLabel cardNumberInfoLabel2 = Common.CreateLabel("(It would appear on atm card/cheque Book and Statements)",
                new Font("Raleway", Font.BOLD, 12), new Rectangle(330, 330, 500, 20));
        add(cardNumberInfoLabel2);
    }

    private void initializePinNumberLabels(GridBagConstraints gbc) {
        JLabel pinNumberLabel = Common.CreateLabel("PIN: ", Common.RalewayBold18,
                new Rectangle(100, 370, 200, 30));
        add(pinNumberLabel);

        JLabel pinNumberInfoLabel = Common.CreateLabel("XXXX", Common.RalewayBold18,
                new Rectangle(330, 370, 200, 30));
        add(pinNumberInfoLabel);

        JLabel pinNumberFormatInfoLabel = Common.CreateLabel("(4-digit Password)",
                new Font("Raleway", Font.BOLD, 12), new Rectangle(100, 400, 200, 20));
        add(pinNumberFormatInfoLabel);
    }

    private void initializeServiceRequiredSelection(GridBagConstraints gbc) {
        JLabel serviceLabel = Common.CreateLabel("Services Required: ", Common.RalewayBold18,
                new Rectangle(100, 450, 200, 30));
        add(serviceLabel);

        ATMCardCheckBox = Common.CreateCheckBox("ATM Card", Common.FrameBackgroundColor, Common.RalewayBold14, 
                new Rectangle(100, 500, 200, 30));
        add(ATMCardCheckBox);

        internetBankingCheckBox = Common.CreateCheckBox("Internet Banking", Common.FrameBackgroundColor, Common.RalewayBold14, 
                new Rectangle(350, 500, 200, 30));
        add(internetBankingCheckBox);
        
        mobileBankingCheckBox = Common.CreateCheckBox("Mobile Banking", Common.FrameBackgroundColor, Common.RalewayBold14, 
                new Rectangle(100, 550, 200, 30));
        add(mobileBankingCheckBox);
        
        emailAlertsCheckBox = Common.CreateCheckBox("Email Alerts", Common.FrameBackgroundColor, Common.RalewayBold14, 
                new Rectangle(350, 550, 200, 30));
        add(emailAlertsCheckBox);
        
        chequeBookCheckBox = Common.CreateCheckBox("Cheque Book", Common.FrameBackgroundColor, Common.RalewayBold14, 
                new Rectangle(100, 600, 200, 30));
        add(chequeBookCheckBox);

        eStatementCheckBox = Common.CreateCheckBox("E-Statement", Common.FrameBackgroundColor, Common.RalewayBold14, 
                new Rectangle(350, 600, 200, 30));
        add(eStatementCheckBox);
    }

    private void initializeAcceptTermsLabel(GridBagConstraints gbc) {
        legalStaementCheckBox = Common.CreateCheckBox("I here by declares that the above entered details correct to the best of my knowledge.",
                Common.FrameBackgroundColor, new Font("Raleway", Font.BOLD, 12), new Rectangle(100, 650, 600, 20));
        legalStaementCheckBox.setSelected(true);
        add(legalStaementCheckBox);
    }

    private void initializeSubmitButton(GridBagConstraints gbc) {
        submitButton = Common.CreateButton("Submit",Common.RalewayBold14, Color.BLACK, 
                new Rectangle(250, 700, 100, 30), this);
        add(submitButton);
    }
    private void initializeCancelButton(GridBagConstraints gbc) {

        cancelButton = Common.CreateButton("Cancel",Common.RalewayBold14, Color.BLACK, 
                new Rectangle(420, 700, 100, 30), this);
        add(cancelButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == cancelButton){
                // show a dialog box of all information will be removed and remove data from database
                System.exit(0);
        }

        String accountType = null;
        if(savingAccountRadioButton.isSelected()){accountType = savingAccountRadioButton.getText();}
        if(currentAccountRadioButton.isSelected()){accountType = currentAccountRadioButton.getText();}
        if(fixedDepopsiteRadioButton.isSelected()){accountType = fixedDepopsiteRadioButton.getText();}
        if(recurringDepositeRadioButton.isSelected()){accountType = recurringDepositeRadioButton.getText();}
        
        Random random = new Random();
        long first7 = (random.nextLong() % 90000000L) + 1409963000000000L;
        String cardNo = "" + Math.abs(first7);
        
        long first3 = (random.nextLong() % 9000L) + 1000L;
        String pin = "" + Math.abs(first3); 

        String facilites = "";
        if(ATMCardCheckBox.isSelected()){facilites += ATMCardCheckBox.getText() + ", ";}
        if(internetBankingCheckBox.isSelected()){facilites += internetBankingCheckBox.getText() + ", ";}
        if(mobileBankingCheckBox.isSelected()){facilites += mobileBankingCheckBox.getText() + ", ";}
        if(emailAlertsCheckBox.isSelected()){facilites += emailAlertsCheckBox.getText() + ", ";}
        if(chequeBookCheckBox.isSelected()){facilites += chequeBookCheckBox.getText() + ", ";}
        if(eStatementCheckBox.isSelected()){facilites += eStatementCheckBox.getText() + ", ";}

        // removing additional comma and space
        if(!facilites.isEmpty()){facilites = facilites.substring(0, facilites.length() - 2);}

        try {
                // validate data
                if(!Common.ValidateString(accountType, "In account type, you must select any one option.")){return;}
                
                // button events
                if(e.getSource() == submitButton){
                        if(!legalStaementCheckBox.isSelected()) {
                                Common.ValidateString(null, "Check the box to assure that entered information is legit.");
                                return;
                        }
                        
                        MyCon con = new MyCon();
        
                        String queryForSignupTable3 = "INSERT INTO signupthree values('"+formNo+"','"+accountType+"','"+cardNo+"','"+pin+"','"+facilites+"')";
                        String queryForLoginTable = "INSERT INTO login values('"+formNo+"','"+cardNo+"','"+pin+"')";
                        con.statement.executeUpdate(queryForSignupTable3);
                        con.statement.executeUpdate(queryForLoginTable);

                        JOptionPane.showMessageDialog(null, "Card Number: "+cardNo+"\nPin: "+pin, "Card information",JOptionPane.INFORMATION_MESSAGE);
                        
                        con.close();
                        new Deposit(pin);
                        dispose();
                }

        } catch (Exception E) {
                E.getStackTrace();
        }
    }

    @Override
    protected void handleResizing() {

    }

    public static void main(String[] args) {
        new SignUp3("0000");
    }

}
