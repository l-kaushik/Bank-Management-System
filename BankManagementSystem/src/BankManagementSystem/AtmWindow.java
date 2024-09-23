package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AtmWindow extends ResizableATM implements ActionListener {

    String pin;

    JButton depositButton;
    JButton cashWithdrawlButton;
    JButton fastCashButton;
    JButton miniStatementButton;
    JButton pinChangeButton;
    JButton balanceEnquiryButton;
    JButton exitButton;

    AtmWindow(String inPin) {

        super("ATM");

        pin = inPin;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        initializeComponents(screenSize);

        setVisible(true);
    }

    private void initializeComponents(Dimension screenSize) {

        initializeSelectTransactionLabel(screenSize);
        initializeDepositButton(screenSize);
        initializeCasthWithdrawalButton(screenSize);
        initializeFastCastButton(screenSize);
        initializeMiniStatementButton(screenSize);
        initializePinChangeButton(screenSize);
        initializeBalanceEnquiry(screenSize);
        initializeExitButton(screenSize);
    }

    private void initializeSelectTransactionLabel(Dimension screenSize) {
        JLabel selectTransactionLabel = Common.CreateLabel("Please Select Your Transaction", Color.WHITE,
                Common.SystemBold28, new Rectangle(430, 180, 700, 35));
        backgroundImageLabel.add(selectTransactionLabel);
    }

    private void initializeDepositButton(Dimension screenSize) {
        depositButton = Common.CreateButton("DEPOSIT", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 270, 150, 35), this);
        backgroundImageLabel.add(depositButton);
    }

    private void initializeCasthWithdrawalButton(Dimension screenSize) {
        cashWithdrawlButton = Common.CreateButton("CASH WITHDRAWL", Common.RalewayBold14, Color.BLACK,
                new Rectangle(680, 270, 170, 35), this);
        backgroundImageLabel.add(cashWithdrawlButton);
    }

    private void initializeFastCastButton(Dimension screenSize) {
        fastCashButton = Common.CreateButton("FAST CASH", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 315, 150, 35), this);
        backgroundImageLabel.add(fastCashButton);
    }

    private void initializeMiniStatementButton(Dimension screenSize) {
        miniStatementButton = Common.CreateButton("MINI STATEMENT", Common.RalewayBold14, Color.BLACK,
                new Rectangle(680, 315, 170, 35), this);
        backgroundImageLabel.add(miniStatementButton);
    }

    private void initializePinChangeButton(Dimension screenSize) {

        pinChangeButton = Common.CreateButton("PIN CHANGE", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 360, 150, 35), this);
        backgroundImageLabel.add(pinChangeButton);
    }

    private void initializeBalanceEnquiry(Dimension screenSize) {
        balanceEnquiryButton = Common.CreateButton("BALANCE ENQUIRY", new Font("Raleway", Font.BOLD, 13), Color.BLACK,
                new Rectangle(680, 360, 170, 35), this);
        backgroundImageLabel.add(balanceEnquiryButton);
    }

    private void initializeExitButton(Dimension screenSize) {
        exitButton = Common.CreateButton("EXIT", Common.RalewayBold14, Color.BLACK, new Rectangle(680, 405, 170, 35),
                this);
        backgroundImageLabel.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == depositButton){
            new Deposit(pin);
            dispose();
        }
        else if(e.getSource() == cashWithdrawlButton){
            new Withdrawal(pin);
            dispose();
        }
        else if(e.getSource() == fastCashButton){
            new FastCash(pin);
            dispose();
        } 
        else if(e.getSource() == miniStatementButton){
            new MiniStatement(pin);
        }        
        else if(e.getSource() == pinChangeButton){
            new Pin(pin);
            dispose();
        }        
        else if(e.getSource() == balanceEnquiryButton){
            new BalanceEnquiry(pin);
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
    }

    public static void main(String[] args) {
        new AtmWindow("");
    }

}
