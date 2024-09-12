package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AtmWindow extends JFrame implements ActionListener {

    String pin;

    JButton depositButton;
    JButton cashWithdrawlButton;
    JButton fastCashButton;
    JButton miniStatementButton;
    JButton pinChangeButton;
    JButton balanceEnquiryButton;
    JButton exitButton;

    AtmWindow(String inPin) {

        pin = inPin;

        Common.InitializeJFrame(this, "Deposit Money", null, new Dimension(1550, 1080), JFrame.EXIT_ON_CLOSE, false,
                new Point(0, 0));

        JLabel backgroundImageLabel = Common.GetScaledImageWithLabel("icons/atm2.png", 1550, 830);
        backgroundImageLabel.setBounds(0, 0, 1550, 830);
        add(backgroundImageLabel);

        JLabel selectTransactionLabel = Common.CreateLabel("Please Select Your Transaction", Color.WHITE,
                Common.SystemBold28, new Rectangle(430, 180, 700, 35));
        backgroundImageLabel.add(selectTransactionLabel);

        depositButton = Common.CreateButton("DEPOSIT", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 270, 150, 35), this);
        backgroundImageLabel.add(depositButton);

        cashWithdrawlButton = Common.CreateButton("CASH WITHDRAWL", Common.RalewayBold14, Color.BLACK,
                new Rectangle(680, 270, 170, 35), this);
        backgroundImageLabel.add(cashWithdrawlButton);

        fastCashButton = Common.CreateButton("FAST CASH", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 315, 150, 35), this);
        backgroundImageLabel.add(fastCashButton);

        miniStatementButton = Common.CreateButton("MINI STATEMENT", Common.RalewayBold14, Color.BLACK,
                new Rectangle(680, 315, 170, 35), this);
        backgroundImageLabel.add(miniStatementButton);

        pinChangeButton = Common.CreateButton("PIN CHANGE", Common.RalewayBold14, Color.BLACK,
                new Rectangle(410, 360, 150, 35), this);
        backgroundImageLabel.add(pinChangeButton);

        balanceEnquiryButton = Common.CreateButton("BALANCE ENQUIRY", new Font("Raleway", Font.BOLD, 13), Color.BLACK,
                new Rectangle(680, 360, 170, 35), this);
        backgroundImageLabel.add(balanceEnquiryButton);

        exitButton = Common.CreateButton("EXIT", Common.RalewayBold14, Color.BLACK, new Rectangle(680, 405, 170, 35),
                this);
        backgroundImageLabel.add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == depositButton){
            deposit();
        }
        else if(e.getSource() == cashWithdrawlButton){
            cashWithdrawl();
        }
        else if(e.getSource() == fastCashButton){
            fastCash();
        } 
        else if(e.getSource() == miniStatementButton){
            miniStatement();
        }        
        else if(e.getSource() == pinChangeButton){
            pinChange();
        }        
        else if(e.getSource() == balanceEnquiryButton){
            balanceEnquiry();
        }        
        else if(e.getSource() == exitButton){
            System.exit(0);   
        }

    }

    private void deposit(){
        new Deposit("");
        setVisible(false);
    }

    private void cashWithdrawl(){

    }

    private void fastCash(){
        
    }

    private void miniStatement(){
        
    }

    private void pinChange(){
        
    }

    private void balanceEnquiry(){
        
    }


    public static void main(String[] args) {
        new AtmWindow("");
    }

}