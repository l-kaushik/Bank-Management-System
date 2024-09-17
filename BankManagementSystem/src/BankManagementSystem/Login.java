package BankManagementSystem;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Login extends JFrame implements ActionListener, ComponentListener {

    JLabel titleLabel;
    JLabel cardNumberLabel;
    JLabel pinLabel;
    JLabel backgroundLabel;
    JLabel cardImage;
    JLabel topCenterImage;

    ImageIcon backgroundIcon;

    Image backgroundImage;

    JTextField cardNumberTextField;
    JPasswordField passwordField;

    JButton clearButton;
    JButton signInButton;
    JButton signUpButton;

    Timer resizeTimer;

    Login() {
        setupFrameAndBackgroundImage();
        initializeComponents();
        setVisible(true);
    }

    private void setupFrameAndBackgroundImage() {
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Load background image
        backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icons/backbg.png"));
        if (backgroundIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading the image.");
        }

        backgroundImage = backgroundIcon.getImage().getScaledInstance((int) (ScreenSize.getWidth()) / 2,
                (int) ScreenSize.getHeight() / 2, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setLayout(new GridBagLayout()); // Allow components to be added

        // Set up JFrame
        setTitle("Bank Management System");
        setLayout(new BorderLayout());
        setSize(new Dimension((int) (ScreenSize.getWidth()) / 2, (int) ScreenSize.getHeight() / 2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null); // centers the frame

        // Make the JFrame responsive to resizing
        setMinimumSize(new Dimension(500, 300));
    }

    private void initializeComponents() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // padding around components

        // Add the top-center image (bank image)
        topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", 100, 100);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        backgroundLabel.add(topCenterImage, gbc);

        cardImage = Common.GetScaledImageWithLabel("icons/card.png", 100, 100);
        cardImage.setBounds(getWidth() - 120, getHeight() - 120, 100, 100);
        add(cardImage);

        // Title label
        titleLabel = new JLabel("WELCOME TO ATM");
        titleLabel.setFont(new Font("AvantGarde", Font.BOLD, 38));
        titleLabel.setForeground(Color.BLACK);
        this.addComponentListener(this);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        backgroundLabel.add(titleLabel, gbc);

        // Card number label and text field
        cardNumberLabel = new JLabel("Card No: ");
        cardNumberLabel.setFont(new Font("Ralway", Font.BOLD, 28));
        cardNumberLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        backgroundLabel.add(cardNumberLabel, gbc);

        cardNumberTextField = new JTextField(15);
        cardNumberTextField.setFont(Common.ArialBold14);
        gbc.gridx = 1;
        gbc.gridy = 1;
        backgroundLabel.add(cardNumberTextField, gbc);

        // Pin number label and password field
        pinLabel = new JLabel("Pin No: ");
        pinLabel.setFont(new Font("Ralway", Font.BOLD, 28));
        pinLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        backgroundLabel.add(pinLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(Common.ArialBold14);
        gbc.gridx = 1;
        gbc.gridy = 2;
        backgroundLabel.add(passwordField, gbc);

        // Buttons for Sign In, Clear, and Sign Up
        signInButton = new JButton("SIGN IN");
        signInButton.setFont(Common.ArialBold14);
        signInButton.setForeground(Color.BLACK);
        signInButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        backgroundLabel.add(signInButton, gbc);

        clearButton = new JButton("CLEAR");
        clearButton.setFont(Common.ArialBold14);
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        backgroundLabel.add(clearButton, gbc);

        signUpButton = new JButton("SIGN UP");
        signUpButton.setFont(Common.ArialBold14);
        signUpButton.setForeground(Color.BLACK);
        signUpButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        backgroundLabel.add(signUpButton, gbc);

        // Add the background label to the JFrame
        add(backgroundLabel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == signInButton) {
                signInAction();
            } else if (e.getSource() == signUpButton) {
                new SignUp();
                dispose();
            } else if (e.getSource() == clearButton) {
                cardNumberTextField.setText("");
                passwordField.setText("");
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    private void signInAction() {
        String cardNumber = cardNumberTextField.getText();
        String pin = new String(passwordField.getPassword());

        validateInputValues(cardNumber, pin);
        verifyDataWithDatabase(cardNumber, pin);
    }

    private void validateInputValues(String cardNumber, String pin) {
        if (cardNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter your card number");
            return;
        }
        if (pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter your pin number");
            return;
        }
    }

    private void verifyDataWithDatabase(String cardNumber, String pin) {
        String query = "SELECT * FROM login WHERE card_number = ? AND pin = ? ";

        try (MyCon con = new MyCon();
                PreparedStatement preparedStatement = con.connection.prepareStatement(query)) {

            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    new AtmWindow(pin);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid card number or pin.");
                }
            }
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (resizeTimer != null) {
            resizeTimer.stop(); // Stop the previous timer
        }

        // Start a new timer with a small delay (100ms)
        resizeTimer = new Timer(100, ae -> {
            handleResizing(); // Perform resizing tasks
        });
        resizeTimer.setRepeats(false); // Ensure it only triggers once
        resizeTimer.start();
    }

    private void handleResizing() {
        Dimension size = this.getSize(); // Get the current window size

        // Scale the font size based on the window width
        double widthToMarginRatio = 0.03;
        int newFontSize = (int) (size.width * widthToMarginRatio);
        updateLabelFonts(newFontSize);

        double widthToMarginRatioForFieldsAndButton = 0.025;
        int newSizeForFieldsAndButton = (int)(size.width * widthToMarginRatioForFieldsAndButton);
        updateFieldsAndButtonsFonts(newSizeForFieldsAndButton);

        scaleFieldsAndButtonsSize(size);

        // Rescale images
        if (topCenterImage.isValid())
            scaleTopCenterImage(size);
        if (cardImage.isValid())
            scaleCardImage(size);
        scaleBackgroundImage(size);

        // Revalidate and repaint to apply changes
        this.revalidate();
        this.repaint();
    }

    private void scaleFieldsAndButtonsSize(Dimension size) {
        // Dynamically scale the preferred size of buttons and text fields
        Dimension scaledButtonSize = new Dimension(size.width / 4, size.height / 15);
        Dimension scaledFieldSize = new Dimension(size.width / 4, size.height / 15);

        cardNumberTextField.setPreferredSize(scaledFieldSize);
        passwordField.setPreferredSize(scaledFieldSize);
        clearButton.setPreferredSize(scaledButtonSize);
        signInButton.setPreferredSize(scaledButtonSize);
        signUpButton.setPreferredSize(scaledButtonSize);
    }

    private void updateLabelFonts(int newFontSize) {
        // Update the font for labels
        titleLabel.setFont(new Font("AvantGarde", Font.BOLD, newFontSize));
        cardNumberLabel.setFont(new Font("Ralway", Font.BOLD, newFontSize));
        pinLabel.setFont(new Font("Ralway", Font.BOLD, newFontSize));
    }

    private void updateFieldsAndButtonsFonts(int newFontSize) {
        // Update the font and preferred size for buttons, text fields, and password
        // fields
        Font scaledFont = new Font("SansSerif", Font.PLAIN, newFontSize);
        cardNumberTextField.setFont(scaledFont);
        passwordField.setFont(scaledFont);
        clearButton.setFont(scaledFont);
        signInButton.setFont(scaledFont);
        signUpButton.setFont(scaledFont);
    }

    private void scaleTopCenterImage(Dimension size) {

        // Update GridBagConstraints properties dynamically
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(size.height / 100, size.width / 100, size.height / 100, size.width / 100); // Adjust
                                                                                                           // padding
        // Remove the old image
        backgroundLabel.remove(topCenterImage);

        // Create new top-center image with scaled size
        int newImageWidth = (int) (size.width / 10);
        int newImageHeight = (int) (size.height / 6);
        topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", newImageWidth, newImageHeight);

        // Re-add the resized image to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        backgroundLabel.add(topCenterImage, gbc);
    }

    private void scaleCardImage(Dimension size) {
        JLabel tempImagLabel = Common.GetScaledImageWithLabel("icons/card.png", (int) (size.width / 10),
                (int) (size.height / 6));
        cardImage.setIcon(tempImagLabel.getIcon());
        // Update bounds to position it at the bottom right
        cardImage.setBounds(size.width - (int) (size.width / 10) - 20, size.height - (int) (size.height / 6) - 20,
                (int) (size.width / 10), (int) (size.height / 6));
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
                backgroundLabel.setIcon(new ImageIcon(backgroundImage));
                backgroundLabel.setPreferredSize(new Dimension(size.width, size.height));
                backgroundLabel.revalidate();
            }
        }.execute();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    public static void main(String[] args) {
        new Login();
    }
}
