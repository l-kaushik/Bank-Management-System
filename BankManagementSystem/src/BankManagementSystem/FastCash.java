package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class FastCash extends ResizableATM implements ActionListener {

    String pin;

    JButton oneHundredsButton;
    JButton fiveHundredsButton;
    JButton oneThousandsButton;
    JButton twoThousandsButton;
    JButton fiveThousandsButton;
    JButton tenThousandsButton;
    JButton backtButton;

    JLabel selectTransactionLabel;

    FastCash(String inPin) {
        super("Fast Cash");

        pin = inPin;
        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {

        initializeSelectTransactionLabel();
        initializeOneHundredsButton();
        initializeFiveHundredsButton();
        initializeOneThousandsButton();
        initializeTwoThousandsButton();
        initializeFiveThousandsButton();
        initializeTenThousandsButton();
        initializeBacktButton();

    }

    private void initializeSelectTransactionLabel() {
        selectTransactionLabel = new JLabel("SELECT WITHDRAWAL AMOUNT");
        selectTransactionLabel.setForeground(Color.WHITE);
        backgroundImageLabel.add(selectTransactionLabel);
    }

    private void initializeOneHundredsButton() {
        oneHundredsButton = new JButton("Rs. 100");
        oneHundredsButton.setForeground(Color.BLACK);
        oneHundredsButton.addActionListener(this);
        oneHundredsButton.setFocusable(false);
        backgroundImageLabel.add(oneHundredsButton);
    }

    private void initializeFiveHundredsButton() {
        fiveHundredsButton = new JButton("Rs. 500");
        Common.setButtonAttributes(fiveHundredsButton, Color.BLACK, this);
        backgroundImageLabel.add(fiveHundredsButton);
    }

    private void initializeOneThousandsButton() {
        oneThousandsButton = new JButton("Rs. 1000");
        Common.setButtonAttributes(oneThousandsButton, Color.BLACK, this);
        backgroundImageLabel.add(oneThousandsButton);
    }

    private void initializeTwoThousandsButton() {
        twoThousandsButton = new JButton("Rs. 2000");
        Common.setButtonAttributes(twoThousandsButton, Color.BLACK, this);
        backgroundImageLabel.add(twoThousandsButton);
    }

    private void initializeFiveThousandsButton() {

        fiveThousandsButton = new JButton("Rs. 5000");
        Common.setButtonAttributes(fiveThousandsButton, Color.BLACK, this);
        backgroundImageLabel.add(fiveThousandsButton);
    }

    private void initializeTenThousandsButton() {
        tenThousandsButton = new JButton("Rs. 10,000");
        Common.setButtonAttributes(tenThousandsButton, Color.BLACK, this);
        backgroundImageLabel.add(tenThousandsButton);
    }

    private void initializeBacktButton() {
        backtButton = new JButton("Back");
        Common.setButtonAttributes(backtButton, Color.BLACK, this);
        backgroundImageLabel.add(backtButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backtButton) {
            new AtmWindow(pin);
            dispose();
        }

        String amount = ((JButton) e.getSource()).getText().substring(4);

        if(WithdrawalFacade.performDatabaseOperations(pin, amount)) {
            dispose();
            new AtmWindow(pin);
        }

    }
    
    @Override
    protected void handleResizing() {
        super.handleResizing();

        if(!isVisible()) return;
        
        Dimension size = getSize();

        updateSelectTransactionLabel(size);
        updateButtonsFonts(size);
        updateButtons(size);


    }

    private void updateSelectTransactionLabel(Dimension size) {
        Font scaledFont22 = new Font("System", Font.BOLD, (int) (size.width/1400.0f * 22));
        selectTransactionLabel.setFont(scaledFont22);   

        int x = (int) (size.width / 3.6);
        int y = (int) (size.height / 6);
        int width = (int) (size.width / 2.21);
        int height = (int) (size.height / 10);
        selectTransactionLabel.setBounds(x, y, width, height);
    }

    private void updateButtonsFonts(Dimension size) {
        Font scaledFont14 = Common.RalewayBold14.deriveFont(size.width/1400.0f * 14);

        oneHundredsButton.setFont(scaledFont14);
        fiveHundredsButton.setFont(scaledFont14);
        oneThousandsButton.setFont(scaledFont14);
        twoThousandsButton.setFont(scaledFont14);
        fiveThousandsButton.setFont(scaledFont14);
        tenThousandsButton.setFont(scaledFont14);
        backtButton.setFont(scaledFont14);
    }

    private void updateButtons(Dimension size) {
        updateOneHundredsButton(size);
        updateFiveHundredsButton(size);
        updateOneThousandsButton(size);
        updateTwoThousandsButton(size);
        updateFiveThousandsButton(size);
        updateTenThousandsButton(size);
        updatebackButton(size);
    }

    private void updateOneHundredsButton(Dimension size) {
        int x = (int) (size.width / 3.78);
        int y = (int) (size.height / 3.3);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        oneHundredsButton.setBounds(x, y, width, height);
    }

    private void updateFiveHundredsButton(Dimension size) {
        int x = (int) (size.width / 2.28);
        int y = (int) (size.height / 3.3);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        fiveHundredsButton.setBounds(x, y, width, height);
    }

    private void updateOneThousandsButton(Dimension size) {
        int x = (int) (size.width / 3.78);
        int y = (int) (size.height / 2.8);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        oneThousandsButton.setBounds(x, y, width, height);
    }

    private void updateTwoThousandsButton(Dimension size) {
        int x = (int) (size.width / 2.28);
        int y = (int) (size.height / 2.8);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        twoThousandsButton.setBounds(x, y, width, height);
    }

    private void updateFiveThousandsButton(Dimension size) {
        int x = (int) (size.width / 3.78);
        int y = (int) (size.height / 2.4);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        fiveThousandsButton.setBounds(x, y, width, height);
    }

    private void updateTenThousandsButton(Dimension size) {
        int x = (int) (size.width / 2.28);
        int y = (int) (size.height / 2.4);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);
        tenThousandsButton.setBounds(x, y, width, height);
    }

    private void updatebackButton(Dimension size) {
        int x = (int) (size.width / 2.28);
        int y = (int) (size.height / 2.1);
        int width = (int) (size.width / 10);
        int height = (int) (size.height / 25);;
        backtButton.setBounds(x, y, width, height);
    }
    public static void main(String[] args) {
        new FastCash("1111");
    }

}
