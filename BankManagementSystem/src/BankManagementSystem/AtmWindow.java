package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class AtmWindow extends ResizableATM implements ActionListener {

    String UID;

    JButton depositButton;
    JButton cashWithdrawlButton;
    JButton fastCashButton;
    JButton miniStatementButton;
    JButton pinChangeButton;
    JButton balanceEnquiryButton;
    JButton exitButton;

    JLabel selectTransactionLabel;

    AtmWindow(String inUID) {

        super("ATM");

        UID = inUID;

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        initializeSelectTransactionLabel();
        initializeDepositButton();
        initializeCasthWithdrawalButton();
        initializeFastCastButton();
        initializeMiniStatementButton();
        initializePinChangeButton();
        initializeBalanceEnquiryButton();
        initializeExitButton();
    }

    private void initializeSelectTransactionLabel() {
        selectTransactionLabel = new JLabel("Please Select Your Transaction");
        selectTransactionLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(selectTransactionLabel);
    }

    private void initializeDepositButton() {
        depositButton = new JButton("DEPOSIT");
        depositButton.setForeground(Color.BLACK);
        depositButton.addActionListener(this);
        depositButton.setFocusable(false);
        backgroundImageLabel.add(depositButton);
    }

    private void initializeCasthWithdrawalButton() {
        cashWithdrawlButton = new JButton("CASH WITHDRAWL");
        Common.setButtonAttributes(cashWithdrawlButton, Color.BLACK, this);
        backgroundImageLabel.add(cashWithdrawlButton);
    }

    private void initializeFastCastButton() {
        fastCashButton = new JButton("FAST CASH");
        Common.setButtonAttributes(fastCashButton, Color.BLACK, this);
        backgroundImageLabel.add(fastCashButton);
    }

    private void initializeMiniStatementButton() {
        miniStatementButton = new JButton("MINI STATEMENT");
        Common.setButtonAttributes(miniStatementButton, Color.BLACK, this);
        backgroundImageLabel.add(miniStatementButton);
    }

    private void initializePinChangeButton() {

        pinChangeButton = new JButton("PIN CHANGE");
        Common.setButtonAttributes(pinChangeButton, Color.BLACK, this);
        backgroundImageLabel.add(pinChangeButton);
    }

    private void initializeBalanceEnquiryButton() {
        balanceEnquiryButton = new JButton("BALANCE ENQUIRY");
        Common.setButtonAttributes(balanceEnquiryButton, Color.BLACK, this);
        backgroundImageLabel.add(balanceEnquiryButton);
    }

    private void initializeExitButton() {
        exitButton = new JButton("EXIT");
        Common.setButtonAttributes(exitButton, Color.BLACK, this);
        backgroundImageLabel.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == depositButton){
            new Deposit(UID);
            dispose();
        }
        else if(e.getSource() == cashWithdrawlButton){
            new Withdrawal(UID);
            dispose();
        }
        else if(e.getSource() == fastCashButton){
            new FastCash(UID);
            dispose();
        } 
        else if(e.getSource() == miniStatementButton){
            new MiniStatement(UID);
        }        
        else if(e.getSource() == pinChangeButton){
            new Pin(UID);
            dispose();
        }        
        else if(e.getSource() == balanceEnquiryButton){
            new BalanceEnquiry(UID);
            dispose();
        }        
        else if(e.getSource() == exitButton){
            System.exit(0);   
        }

    }

    @Override
    protected void handleResizing() {
        super.handleResizing();

        if(!isVisible()) return;

        Dimension size = getSize();

        updateFonts(size);
        updateLabelsLocation(size);
        updateButtonsLocation(size);
    }

    private void updateFonts(Dimension size) {
        float scaleFactor = size.width / 1400.0f;

        updateLabelsFont(scaleFactor);
        updateButtonsFont(scaleFactor);
    }

    private void updateLabelsFont(float scaleFactor) {
        Font scaledFont24 = new Font("System", Font.BOLD, (int) (24 * scaleFactor));

        selectTransactionLabel.setFont(scaledFont24);   
    }

    private void updateButtonsFont(float scaleFactor) {
        Font scaledFont14 = Common.RalewayBold14.deriveFont(14 * scaleFactor);

        depositButton.setFont(scaledFont14);
        cashWithdrawlButton.setFont(scaledFont14);
        fastCashButton.setFont(scaledFont14);
        miniStatementButton.setFont(scaledFont14);
        pinChangeButton.setFont(scaledFont14);
        balanceEnquiryButton.setFont(scaledFont14);
        exitButton.setFont(scaledFont14);
    }

    private void updateLabelsLocation(Dimension size) {
        updateSelectTransactionLabelLocation(size);
    }

    private void updateSelectTransactionLabelLocation(Dimension size) {
        int x = (int) (size.width / 3.6);
        int y = (int) (size.height / 6);
        int width = (int) (size.width / 2.21);
        int height = (int) (size.height / 10);
        selectTransactionLabel.setBounds(x, y, width, height);
    }

    private void updateButtonsLocation(Dimension size) {
        updateDepositButtonLocation(size);
        updateCashWithdrawalButtonLocation(size);
        updateFastCashButtonLocation(size);
        updateMiniStatementButtonLocation(size);
        updatePinChangeButtonLocation(size);
        updateBalanceEnquiryButtonLocation(size);
        updateExitButtonLocation(size);
    }

    private void updateDepositButtonLocation(Dimension size) {
        int x = (int) (size.width / 3.78);
        int y = (int) (size.height / 3.3);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        depositButton.setBounds(x, y, width, height);
    }

    private void updateCashWithdrawalButtonLocation(Dimension size) {
        int x = (int) (size.width / 2.4);
        int y = (int) (size.height / 3.3);
        int width = (int) (size.width / 8);
        int height = (int) (size.height / 25);
        cashWithdrawlButton.setBounds(x, y, width, height);
    }

    private void updateFastCashButtonLocation(Dimension size) {
        int x = (int) (size.width / 3.78);
        int y = (int) (size.height / 2.8);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        fastCashButton.setBounds(x, y, width, height);
    }

    private void updateMiniStatementButtonLocation(Dimension size) {
        int x = (int) (size.width / 2.4);
        int y = (int) (size.height / 2.8);
        int width = (int) (size.width / 8);
        int height = (int) (size.height / 25);
        miniStatementButton.setBounds(x, y, width, height);
    }

    private void updatePinChangeButtonLocation(Dimension size) {
        int x = (int) (size.width / 3.78);
        int y = (int) (size.height / 2.4);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        pinChangeButton.setBounds(x, y, width, height);
    }

    private void updateBalanceEnquiryButtonLocation(Dimension size) {
        int x = (int) (size.width / 2.4);
        int y = (int) (size.height / 2.4);
        int width = (int) (size.width / 8);
        int height = (int) (size.height / 25);
        balanceEnquiryButton.setBounds(x, y, width, height);
    }

    private void updateExitButtonLocation(Dimension size) {
        int x = (int) (size.width / 2.4);
        int y = (int) (size.height / 2.1);
        int width = (int) (size.width / 8);
        int height = (int) (size.height / 25);;
        exitButton.setBounds(x, y, width, height);
    }

    public static void main(String[] args) {
        new AtmWindow("");
    }

}
