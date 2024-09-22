package BankManagementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Withdrawal extends ResizableFrame implements ActionListener {

    String pin = null;

    JTextField amounTextField;

    JButton withdrawalButton;
    JButton backButton;

    JLabel backgroundImageLabel;
    JLabel withdrawalAmountLabel;

    ImageIcon backgroundIcon;
    Image backgroundImage;

    private void setupFrame(Dimension screeSize) {
        setTitle("Withdrawal Money");
        setLayout(new BorderLayout());
        setSize(screeSize.width, screeSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void setupBackgroundImage(Dimension screenSize) {

        backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.png"));
        if (backgroundIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading the image.");
        }

        backgroundImage = backgroundIcon.getImage().getScaledInstance((int) (screenSize.getWidth()),
                (int) screenSize.getHeight(), Image.SCALE_SMOOTH);
        backgroundImageLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundImageLabel.setLayout(null);
    }

    private void initializeComponents(Dimension screenSize) {
        // components initialization here
        initializeWithdrawalAmountLabel(screenSize);
        // initializeAmountLabel(gbc);
        // initializeAmountTextField(gbc);
        // initializeWithdrawalButton(gbc);
        // initializeBackButton(gbc);

    }

    private void initializeWithdrawalAmountLabel(Dimension screenSize) {
        withdrawalAmountLabel = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        withdrawalAmountLabel.setForeground(Color.WHITE);
        updateWithdrawalAmountLabelLocation(screenSize);
        backgroundImageLabel.add(withdrawalAmountLabel);
    }

    private void initializeAmountLabel(GridBagConstraints gbc) {
        JLabel amountLabel = Common.CreateLabel("PLEASE ENTER YOUR AMOUNT", Color.WHITE,
                Common.SystemBold16, new Rectangle(460, 220, 400, 35));
        backgroundImageLabel.add(amountLabel);
    }

    private void initializeAmountTextField(GridBagConstraints gbc) {
        amounTextField = Common.CreateTextField(Common.RalewayBold22, new Rectangle(460, 270, 320, 25));

        // Add a KeyListener to only allow numeric input
        amounTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // If the character is not a digit, consume the event (ignore input)
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        backgroundImageLabel.add(amounTextField);
    }

    private void initializeWithdrawalButton(GridBagConstraints gbc) {

        withdrawalButton = Common.CreateButton("WITHDRAWAL", Common.RalewayBold14, Color.BLACK,
                new Rectangle(700, 362, 150, 35), this);
        backgroundImageLabel.add(withdrawalButton);
    }

    private void initializeBackButton(GridBagConstraints gbc) {
        backButton = Common.CreateButton("BACK", Common.RalewayBold14, Color.BLACK,
                new Rectangle(700, 406, 150, 35), this);
        backgroundImageLabel.add(backButton);
    }

    Withdrawal(String inPin) {

        pin = inPin;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setupFrame(screenSize);
        setupBackgroundImage(screenSize);
        initializeComponents(screenSize);

        add(backgroundImageLabel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == withdrawalButton) {
            initWithdrawal();
        } else if (e.getSource() == backButton) {
            new AtmWindow(pin);
            dispose();
        }
    }

    private void initWithdrawal() {
        String amount = amounTextField.getText();
        Date date = new Date();

        // perform data validation
        if (!validateAmount(amount))
            return;

        performDatabaseOperations(amount, date);
    }

    private boolean validateAmount(String amount) {

        try {
            if (Integer.parseInt(amount) > 10000) {
                throw new IllegalArgumentException("Exceeds maximum withdrawal limit");
            }
            return true;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "The maximum withdrawal limit is 10,000", "Withdrawal Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    private void performDatabaseOperations(String amount, Date date) {
        try (MyCon con = new MyCon()) {
            int balance = calculateBalance(con);
            if (!isSufficientBalance(balance, amount)) {
                JOptionPane.showMessageDialog(this, "Insufficient Balance");
                return;
            }
            insertWithdrawalTransaction(con, amount, date);
            JOptionPane.showMessageDialog(this, "Rs. " + amount + " Withdrawn Successfully.");
            dispose();
            new AtmWindow(pin);
        } catch (Exception e) {
            handleDatabaseProcessingException(e);
        }
    }

    private int calculateBalance(MyCon con) throws SQLException {
        String queryFetchData = "SELECT * FROM bank WHERE pin = ?";
        int balance = 0;

        try (PreparedStatement preparedStatementFetchData = con.connection.prepareStatement(queryFetchData)) {
            preparedStatementFetchData.setString(1, pin);
            try (ResultSet resultSet = preparedStatementFetchData.executeQuery()) {
                while (resultSet.next()) {
                    int transactionAmount = Integer.parseInt(resultSet.getString("amount"));
                    String transactionType = resultSet.getString("type");
                    balance += "Deposit".equals(transactionType) ? transactionAmount : -transactionAmount;
                }
            }
        }
        return balance;
    }

    private boolean isSufficientBalance(int balance, String amount) {
        return balance >= Integer.parseInt(amount);
    }

    private void insertWithdrawalTransaction(MyCon con, String amount, Date date) throws SQLException {
        String queryInsertData = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatementInsertData = con.connection.prepareStatement(queryInsertData)) {
            preparedStatementInsertData.setString(1, pin);
            preparedStatementInsertData.setString(2, date.toString());
            preparedStatementInsertData.setString(3, "Withdrawal");
            preparedStatementInsertData.setString(4, amount);
            preparedStatementInsertData.executeUpdate();
        }
    }

    private void handleDatabaseProcessingException(Exception e) {
        if (e instanceof SQLException) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        } else if (e instanceof NumberFormatException) {
            JOptionPane.showMessageDialog(this, "Invalid amount format.");
        } else {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
        }
        e.printStackTrace();
    }

    @Override
    protected void handleResizing() {

        // prevents resizing when frame is not visible
        if (!this.isVisible())
            return;

        Dimension size = this.getSize(); // Get the current window size

        updateFonts(size);
        updateLabelsLocations(size);
        scaleBackgroundImage(size);

        // Revalidate and repaint to apply changes
        this.revalidate();
        this.repaint();
    }

    private void updateFonts(Dimension size) {
        float scaleFactor = size.width / 1400.0f;

        updateLabelFonts(scaleFactor);
    }

    private void updateLabelFonts(float scaleFactor) {
        Font scaledFont16 = new Font("System", Font.BOLD, (int) (16 * scaleFactor));

        withdrawalAmountLabel.setFont(scaledFont16);
    }

    private void updateLabelsLocations(Dimension size) {
        updateWithdrawalAmountLabelLocation(size);
    }

    private void updateWithdrawalAmountLabelLocation(Dimension size) {
        int x = (int) (size.width/ 3.35);
        int y = (int) (size.height / 6);
        int width = 700;
        int height = 35;
        withdrawalAmountLabel.setBounds(x, y, width, height);
    }

    private void scaleBackgroundImage(Dimension size) {
        // Offload image scaling to a background thread
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // Heavy operation: scaling the background image
                backgroundImage = backgroundIcon.getImage().getScaledInstance(size.width, size.height,
                        Image.SCALE_SMOOTH);

                return null;
            }

            @Override
            protected void done() {
                // Once the image is scaled, update the GUI
                backgroundImageLabel.setIcon(new ImageIcon(backgroundImage));
                backgroundImageLabel.setPreferredSize(new Dimension(size.width, size.height));
                backgroundImageLabel.revalidate();
            }
        }.execute();
    }

    public static void main(String[] args) {
        new Withdrawal("1111");
    }

}
