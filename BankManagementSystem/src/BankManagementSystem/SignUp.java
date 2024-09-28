package BankManagementSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;

public class SignUp extends ResizableFrame implements ActionListener {

    String UID = UniqueIDGenerator.generateUID();

    JButton nextButton;

    JTextField nameTextField;
    JTextField fatherNameTextField;
    JTextField emailTextField;
    JTextField residentAddressTextField;
    JTextField cityTextField;
    JTextField pinTextField;
    JTextField stateTextField;

    JDateChooser dateChooser;

    JRadioButton maleRadioButton;
    JRadioButton femaleRadioButton;
    JRadioButton othersGenderRadioButton;
    JRadioButton marriedRadioButton;
    JRadioButton unmarriedRadioButton;
    JRadioButton othersMaritalRadioButton;

    JLabel topCenterImage;
    JLabel applicationNumberLabel;
    JLabel pageNumberLabel;
    JLabel personalDetailsLabel;
    JLabel nameLabel;
    JLabel fatherNameLabel;
    JLabel cityLabel;
    JLabel genderLabel;
    JLabel maritalStatusLabel;
    JLabel dateOfBirthLabel;
    JLabel emaiLabel;
    JLabel pinLabel;
    JLabel stateLabel;
    JLabel residentAddressLabel;

    Timer resizeTimer;

    JPanel contentPanel;

    SignUp() {

        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setupFrame(ScreenSize);
        initializeComponents(ScreenSize);
        setVisible(true);
    }

    private void setupFrame(Dimension ScreenSize) {
        // Set up JFrame
        setTitle("Application Form");
        setLayout(new BorderLayout()); // for adding JScrollPane
        setSize(new Dimension((int) (ScreenSize.getWidth()) / 2, (int) (ScreenSize.getHeight() - 100)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null); // centers the frame
    }

    private void initializeComponents(Dimension ScreenSize) {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Common.FrameBackgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
        gbc.weightx = 1.0; // Allow components to expand horizontally

        initializeTopCenterImage(gbc, ScreenSize);
        initializeApplicationNumberLabel(gbc);
        initializePageDetailsPanel(gbc);
        initializeNameField(gbc);
        initializeFatherNameField(gbc);
        initializeGenderSelectionPanel(gbc);
        initializeDateOfBirthField(gbc);
        initializeEmailField(gbc);
        initializeMaritalStatusSelectionPanel(gbc);
        initializeAddressField(gbc);
        initializeNextButton(gbc);

        // Add contentPanel to JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeTopCenterImage(GridBagConstraints gbc, Dimension ScreenSize) {
        // Top center image
        topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", 100, 100);
        scaleTopCenterImage(ScreenSize);
    }

    private void initializeApplicationNumberLabel(GridBagConstraints gbc) {
        // Application number label
        applicationNumberLabel = new JLabel("APPLICATION FORM NO. 1121");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(applicationNumberLabel, gbc);
    }

    private void initializePageDetailsPanel(GridBagConstraints gbc) {
        // page number and page details
        JPanel pageDetailsPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        pageDetailsPanel.setBackground(Common.FrameBackgroundColor);
        pageNumberLabel = new JLabel("Page 1");
        pageNumberLabel.setHorizontalAlignment(JLabel.CENTER);

        personalDetailsLabel = new JLabel("Personal Details");
        personalDetailsLabel.setHorizontalAlignment(JLabel.CENTER);

        pageDetailsPanel.add(pageNumberLabel);
        pageDetailsPanel.add(personalDetailsLabel);

        gbc.gridx = 1; // Align with the center of the layout
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across 2 columns if needed for centering
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(pageDetailsPanel, gbc);
    }

    private void initializeNameField(GridBagConstraints gbc) {
        // Name label and text field
        nameLabel = new JLabel("Name: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(nameLabel, gbc);

        nameTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.7;
        contentPanel.add(nameTextField, gbc);
    }

    private void initializeFatherNameField(GridBagConstraints gbc) {

        // Father name
        fatherNameLabel = new JLabel("Father's Name: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(fatherNameLabel, gbc);

        fatherNameTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(fatherNameTextField, gbc);
    }

    private void initializeGenderSelectionPanel(GridBagConstraints gbc) {
        // Gender selection
        genderLabel = new JLabel("Gender: ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(genderLabel, gbc);

        JPanel genderStatusPanel = new JPanel();
        genderStatusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderStatusPanel.setBackground(Common.FrameBackgroundColor);
        maleRadioButton = new JRadioButton("Male");

        maleRadioButton.setBackground(Common.FrameBackgroundColor);
        maleRadioButton.setFocusable(false);
        maleRadioButton.setBorder(new EmptyBorder(0, 0, 0, 70));
        genderStatusPanel.add(maleRadioButton);

        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBackground(Common.FrameBackgroundColor);
        femaleRadioButton.setFocusable(false);
        femaleRadioButton.setBorder(new EmptyBorder(0, 0, 0, 70));
        genderStatusPanel.add(femaleRadioButton);

        othersGenderRadioButton = new JRadioButton("Others");
        othersGenderRadioButton.setBackground(Common.FrameBackgroundColor);
        othersGenderRadioButton.setFocusable(false);
        genderStatusPanel.add(othersGenderRadioButton);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        contentPanel.add(genderStatusPanel, gbc);

        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        genderButtonGroup.add(othersGenderRadioButton);
    }

    private void initializeDateOfBirthField(GridBagConstraints gbc) {
        // Date of birth
        dateOfBirthLabel = new JLabel("Date Of Birth: ");
        dateOfBirthLabel.setFont(Common.RalewayBold20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        contentPanel.add(dateOfBirthLabel, gbc);

        dateChooser = new JDateChooser();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        contentPanel.add(dateChooser, gbc);
    }

    private void initializeEmailField(GridBagConstraints gbc) {
        // Email address
        emaiLabel = new JLabel("Email Address: ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(emaiLabel, gbc);

        emailTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(emailTextField, gbc);
    }

    private void initializeMaritalStatusSelectionPanel(GridBagConstraints gbc) { // Marital status
        maritalStatusLabel = new JLabel("Marital Status: ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        contentPanel.add(maritalStatusLabel, gbc);

        // Create a panel for the marital status radio buttons
        JPanel maritalStatusPanel = new JPanel();
        maritalStatusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        maritalStatusPanel.setBackground(Common.FrameBackgroundColor);

        // Add radio buttons to the panel
        marriedRadioButton = new JRadioButton("Married");
        marriedRadioButton.setBackground(Common.FrameBackgroundColor);
        marriedRadioButton.setFocusable(false);
        marriedRadioButton.setBorder(new EmptyBorder(0, 0, 0, 50));
        maritalStatusPanel.add(marriedRadioButton);

        unmarriedRadioButton = new JRadioButton("Unmarried");
        unmarriedRadioButton.setBackground(Common.FrameBackgroundColor);
        unmarriedRadioButton.setFocusable(false);
        unmarriedRadioButton.setBorder(new EmptyBorder(0, 0, 0, 50));
        maritalStatusPanel.add(unmarriedRadioButton);

        othersMaritalRadioButton = new JRadioButton("Others");
        othersMaritalRadioButton.setBackground(Common.FrameBackgroundColor);
        othersMaritalRadioButton.setFocusable(false);
        maritalStatusPanel.add(othersMaritalRadioButton);

        // Add the panel to the GridBagLayout
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2; // Span across 3 columns
        contentPanel.add(maritalStatusPanel, gbc);

        ButtonGroup maritalStatusButtonGroup = new ButtonGroup();
        maritalStatusButtonGroup.add(marriedRadioButton);
        maritalStatusButtonGroup.add(unmarriedRadioButton);
        maritalStatusButtonGroup.add(othersMaritalRadioButton);
    }

    private void initializeAddressField(GridBagConstraints gbc) {
        initializeResidentAddressFied(gbc);
        initializeCityField(gbc);
        initializePinField(gbc);
        initializeStateField(gbc);
    }

    private void initializeResidentAddressFied(GridBagConstraints gbc) {
        // Address
        residentAddressLabel = new JLabel("Address: ");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        contentPanel.add(residentAddressLabel, gbc);

        residentAddressTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(residentAddressTextField, gbc);
    }

    private void initializeCityField(GridBagConstraints gbc) {
        // City
        cityLabel = new JLabel("City: ");
        gbc.gridx = 0;
        gbc.gridy = 9;
        contentPanel.add(cityLabel, gbc);

        cityTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(cityTextField, gbc);
    }

    private void initializePinField(GridBagConstraints gbc) {
        // Pin
        pinLabel = new JLabel("Pin: ");

        gbc.gridx = 0;
        gbc.gridy = 10;
        contentPanel.add(pinLabel, gbc);

        pinTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        contentPanel.add(pinTextField, gbc);
    }

    private void initializeStateField(GridBagConstraints gbc) {
        // State
        stateLabel = new JLabel("State: ");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        contentPanel.add(stateLabel, gbc);

        stateTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(stateTextField, gbc);
    }

    private void initializeNextButton(GridBagConstraints gbc) {
        nextButton = new JButton("Next");
        nextButton.setForeground(Color.BLACK);
        nextButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(nextButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Extract data from fields and buttons
        String name = nameTextField.getText();
        String fatherName = fatherNameTextField.getText();
        String dateOfBirth = extractdateOfBirth();
        String gender = extractGenderSelection();
        String email = emailTextField.getText();
        String maritalStatus = extractMaritalSelection();
        String residentAddress = residentAddressTextField.getText();
        String pincode = pinTextField.getText();
        String cityName = cityTextField.getText();
        String stateName = stateTextField.getText();

        // validate data for potential errors
        if (!validateInputValues(name, fatherName, dateOfBirth, gender, email, maritalStatus, residentAddress,
                pincode, cityName, stateName))
            return;

        // insert data into database
        insertIntoDatabase(name, fatherName, dateOfBirth, gender, email,
                maritalStatus, residentAddress, pincode, cityName, stateName);

    }

    private String extractdateOfBirth() {
        if (dateChooser.getDate() != null) {
            return new SimpleDateFormat("dd-MM-YYYY").format(dateChooser.getDate());
        }
        return null;
    }

    private String extractGenderSelection() {
        if (maleRadioButton.isSelected()) {
            return maleRadioButton.getText();
        } else if (femaleRadioButton.isSelected()) {
            return femaleRadioButton.getText();
        } else if (othersGenderRadioButton.isSelected()) {
            return othersGenderRadioButton.getText();
        }

        return null;
    }

    private String extractMaritalSelection() {
        if (marriedRadioButton.isSelected()) {
            return marriedRadioButton.getText();
        } else if (unmarriedRadioButton.isSelected()) {
            return unmarriedRadioButton.getText();
        } else if (othersMaritalRadioButton.isSelected()) {
            return othersMaritalRadioButton.getText();
        }

        return null;
    }

    private void insertIntoDatabase(String name, String fatherName, String dateOfBirth, String gender,
            String email, String maritalStatus, String residentAddress, String pincode, String cityName,
            String stateName) {

        try {
            MyCon con = new MyCon();

            String query = "INSERT INTO signup VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.connection.prepareStatement(query);
            preparedStatement.setString(1, UID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, fatherName);
            preparedStatement.setString(4, dateOfBirth);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, maritalStatus);
            preparedStatement.setString(8, residentAddress);
            preparedStatement.setString(9, cityName);
            preparedStatement.setString(10, pincode);
            preparedStatement.setString(11, stateName);

            preparedStatement.executeUpdate();

            con.close();
            preparedStatement.close();
            new SignUp2(UID);
            dispose();

        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    private boolean validateInputValues(String name, String fatherName, String dateOfBirth,
            String gender, String email, String maritalStatus, String residentAddress,
            String pincode, String cityName, String stateName) {

        StringBuilder errorMessage = new StringBuilder();

        if (UID.isEmpty()){
            JOptionPane.showMessageDialog(null, "Failed to generate UID, Try again later.", "UID Generation Failed",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (name.isEmpty())
            errorMessage.append("Name field can't be empty.\n");
        if (fatherName.isEmpty())
            errorMessage.append("Father name field can't be empty.\n");
        if (dateOfBirth == null || dateOfBirth.isEmpty())
            errorMessage.append("Date of birth can't be empty.\n");
        if (gender == null || gender.isEmpty())
            errorMessage.append("Gender must be selected.\n");
        if (email.isEmpty())
            errorMessage.append("Email field can't be empty.\n");
        if (maritalStatus == null || maritalStatus.isEmpty())
            errorMessage.append("Marital status must be selected.\n");
        if (residentAddress.isEmpty())
            errorMessage.append("Resident address can't be empty.\n");
        if (pincode.isEmpty())
            errorMessage.append("Pincode can't be empty.\n");
        if (cityName.isEmpty())
            errorMessage.append("City name can't be empty.\n");
        if (stateName.isEmpty())
            errorMessage.append("State name can't be empty.\n");

        if (errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(null, errorMessage.toString(), "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    @Override
    protected void handleResizing() {

        // prevents resizing when frame is not visible
        if (!this.isVisible())
            return;

        Dimension size = this.getSize();

        updateFonts(size);
        updateDateChooserSize(size);

        // Rescale image
        if (topCenterImage.isValid())
            scaleTopCenterImage(size);

        // Revalidate and repaint to apply changes
        this.revalidate();
        this.repaint();
    }

    private void updateDateChooserSize(Dimension size) {
        // Calculate the preferred size based on the frame size
        int preferredWidth = (int) (size.width / 4.0);
        int preferredHeight = (int) (size.height / 35.0);

        dateChooser.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }

    private void updateFonts(Dimension size) {
        float scaleFactor = size.width / 800.0f;

        updateLabelFonts(size, scaleFactor);
        updateTextFieldFonts(size, scaleFactor);
        updateButtonFonts(size, scaleFactor);
    }

    private void updateLabelFonts(Dimension size, float scaleFactor) {

        Font scaledFont20 = Common.RalewayBold20.deriveFont(20 * scaleFactor);
        Font scaledFont22 = Common.RalewayBold22.deriveFont(22 * scaleFactor);

        // Update fonts for labels with scaling
        applicationNumberLabel.setFont(new Font("Raleway", Font.BOLD, (int) (28 * scaleFactor)));
        personalDetailsLabel.setFont(scaledFont22);
        pageNumberLabel.setFont(scaledFont22);
        nameLabel.setFont(scaledFont20);
        fatherNameLabel.setFont(scaledFont20);
        genderLabel.setFont(scaledFont20);
        dateOfBirthLabel.setFont(scaledFont20);
        emaiLabel.setFont(scaledFont20);
        maritalStatusLabel.setFont(scaledFont20);
        residentAddressLabel.setFont(scaledFont20);
        cityLabel.setFont(scaledFont20);
        pinLabel.setFont(scaledFont20);
        stateLabel.setFont(scaledFont20);
    }

    private void updateTextFieldFonts(Dimension size, float scaleFactor) {

        Font scaledFont = Common.RalewayBold14.deriveFont(scaleFactor * 14);

        nameTextField.setFont(scaledFont);
        fatherNameTextField.setFont(scaledFont);
        emailTextField.setFont(scaledFont);
        dateChooser.setFont(scaledFont);
        residentAddressTextField.setFont(scaledFont);
        cityTextField.setFont(scaledFont);
        pinTextField.setFont(scaledFont);
        stateTextField.setFont(scaledFont);
    }

    private void updateButtonFonts(Dimension size, float scaleFactor) {

        Font scaledFont = Common.RalewayBold14.deriveFont(scaleFactor * 14);

        maleRadioButton.setFont(scaledFont);
        femaleRadioButton.setFont(scaledFont);
        othersGenderRadioButton.setFont(scaledFont);
        marriedRadioButton.setFont(scaledFont);
        unmarriedRadioButton.setFont(scaledFont);
        othersMaritalRadioButton.setFont(scaledFont);
        nextButton.setFont(scaledFont);
    }

    private void scaleTopCenterImage(Dimension size) {

        // Update GridBagConstraints properties dynamically
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

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
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(topCenterImage, gbc);
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
