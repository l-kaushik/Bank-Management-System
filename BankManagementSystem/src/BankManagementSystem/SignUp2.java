package BankManagementSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignUp2 extends ResizableFrame implements ActionListener {

    String formNo = null;

    JComboBox<String> religionComboBox;
    JComboBox<String> categoryComboBox;
    JComboBox<String> incomeComboBox;
    JComboBox<String> educationComboBox;
    JComboBox<String> occupationComboBox;

    JTextField panNumberTextField;
    JTextField aadharNumberTextField;

    JRadioButton yesSeniorCitizenRadioButton;
    JRadioButton noSeniorCitizenRadioButton;
    JRadioButton yesExistingAccountRadioButton;
    JRadioButton noExisitingAccountRadioButton;

    JButton nextButton;
    JPanel contentPanel;

    JLabel topCenterImage;
    JLabel pageNumberLabel;
    JLabel additionalDetailLabel;
    JLabel religionLabel;
    JLabel categoryLabel;
    JLabel incomeLabel;
    JLabel educationLabel;
    JLabel occupationLabel;
    JLabel panNumberLabel;
    JLabel aadharNumberLabel;
    JLabel seniorCitizenLabel;
    JLabel existingAccountLabel;
    JLabel formNoLabel;
    JLabel formNoTextLabel;

    SignUp2(String inFormNo) {

        formNo = inFormNo;
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setupFrame(ScreenSize);
        initializeComponents(ScreenSize);
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
        gbc.weightx = 1.0; // Allow components to expand horizontally

        // component initialization here
        initializeTopCenterImage(gbc, screenSize);
        initializePageAndFormNumberLabel(gbc);
        initializeAdditionDetailLabel(gbc);
        initializeReligionSelection(gbc);
        initializeCategorySelection(gbc);
        initializeIncomeSelection(gbc);
        initializeEducationSelection(gbc);
        initializeOccupationSelection(gbc);
        initializePanField(gbc);
        initializeAadharField(gbc);
        initializeSeniorCitizenSelection(gbc);
        initializeExistingAccountSelection(gbc);
        initializeNextButton(gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

    }

    private void initializeTopCenterImage(GridBagConstraints gbc, Dimension screenSize) {
        // top center image
        topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", 100, 100);
        scaleTopCenterImage(screenSize);
    }

    private void initializePageAndFormNumberLabel(GridBagConstraints gbc) {
        // show page number
        JPanel pageAndFormNumberPanel = new JPanel();
        pageAndFormNumberPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pageAndFormNumberPanel.setBackground(Common.FrameBackgroundColor);


        pageNumberLabel = new JLabel("Page 2");
        pageAndFormNumberPanel.add(pageNumberLabel);

        formNoLabel = new JLabel("Form No: ");
        formNoLabel.setBorder(new EmptyBorder(0, 50, 0, 0));
        pageAndFormNumberPanel.add(formNoLabel);

        formNoTextLabel = new JLabel(formNo);
        pageAndFormNumberPanel.add(formNoTextLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(pageAndFormNumberPanel, gbc);
    }

    private void initializeAdditionDetailLabel(GridBagConstraints gbc) {
        // show additional detail on page
        additionalDetailLabel = new JLabel("Additional Details");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(additionalDetailLabel, gbc);
    }

    private void initializeReligionSelection(GridBagConstraints gbc) {
        // show combo box for religions
        religionLabel = new JLabel("Religion: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(religionLabel, gbc);

        String religions[] = { "Hindu", "Muslim", "Sikh", "Christian", "Others" };
        religionComboBox = new JComboBox<String>(religions);
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(religionComboBox, gbc);
    }

    private void initializeCategorySelection(GridBagConstraints gbc) {
        // show combo box for caste categories
        categoryLabel = new JLabel("Category: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(categoryLabel, gbc);

        String categories[] = { "GEN", "OBC", "SC", "ST", "Others" };
        categoryComboBox = new JComboBox<String>(categories);
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPanel.add(categoryComboBox, gbc);
    }

    private void initializeIncomeSelection(GridBagConstraints gbc) {
        // show combo box for incomes
        incomeLabel = new JLabel("Income: ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(incomeLabel, gbc);

        String incomes[] = { "0", "< 1,50,000", "< 2,50,000", "< 5,00,000", "Upto 10,00,000", "Above 10,00,000" };
        incomeComboBox = new JComboBox<String>(incomes);
        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPanel.add(incomeComboBox, gbc);
    }

    private void initializeEducationSelection(GridBagConstraints gbc) {
        // show combo box for education type
        educationLabel = new JLabel("Education: ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(educationLabel, gbc);

        String educations[] = { "Non-Graduate", "Graduate", "Post-Graduate", "Doctrate", "Others" };
        educationComboBox = new JComboBox<String>(educations);
        gbc.gridx = 1;
        gbc.gridy = 5;
        contentPanel.add(educationComboBox, gbc);
    }

    private void initializeOccupationSelection(GridBagConstraints gbc) {
        // show combo box for occupation type
        occupationLabel = new JLabel("Occupation: ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(occupationLabel, gbc);

        String occupations[] = { "Salaried", "Self-Employed", "Business", "Student", "Retired", "Others" };
        occupationComboBox = new JComboBox<String>(occupations);
        gbc.gridx = 1;
        gbc.gridy = 6;
        contentPanel.add(occupationComboBox, gbc);
    }

    private void initializePanField(GridBagConstraints gbc) {
        // show pan number text field
        panNumberLabel = new JLabel("PAN Number: ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        contentPanel.add(panNumberLabel, gbc);

        panNumberTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 7;
        contentPanel.add(panNumberTextField, gbc);
    }

    private void initializeAadharField(GridBagConstraints gbc) {
        aadharNumberLabel = new JLabel("Aadhar Number: ");
        gbc.gridx = 0;
        gbc.gridy = 8;
        contentPanel.add(aadharNumberLabel, gbc);

        aadharNumberTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 8;
        contentPanel.add(aadharNumberTextField, gbc);
    }

    private void initializeSeniorCitizenSelection(GridBagConstraints gbc) {
        seniorCitizenLabel = new JLabel("Senior Citizen: ");
        gbc.gridx = 0;
        gbc.gridy = 9;
        contentPanel.add(seniorCitizenLabel, gbc);

        JPanel seniorCitizenPanel = new JPanel();
        seniorCitizenPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        seniorCitizenPanel.setBackground(Common.FrameBackgroundColor);

        yesSeniorCitizenRadioButton = new JRadioButton("Yes");
        yesSeniorCitizenRadioButton.setBackground(Common.FrameBackgroundColor);
        yesSeniorCitizenRadioButton.setFocusable(false);
        yesSeniorCitizenRadioButton.setBorder(new EmptyBorder(0, 0, 0, 100));
        seniorCitizenPanel.add(yesSeniorCitizenRadioButton);

        noSeniorCitizenRadioButton = new JRadioButton("No");
        noSeniorCitizenRadioButton.setBackground(Common.FrameBackgroundColor);
        noSeniorCitizenRadioButton.setFocusable(false);
        noSeniorCitizenRadioButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        seniorCitizenPanel.add(noSeniorCitizenRadioButton);

        gbc.gridx = 1;
        gbc.gridy = 9;
        contentPanel.add(seniorCitizenPanel, gbc);

        ButtonGroup seniorCitizeButtonGroup = new ButtonGroup();
        seniorCitizeButtonGroup.add(yesSeniorCitizenRadioButton);
        seniorCitizeButtonGroup.add(noSeniorCitizenRadioButton);
    }

    private void initializeExistingAccountSelection(GridBagConstraints gbc) {
        existingAccountLabel = new JLabel("Existing Account: ");
        gbc.gridx = 0;
        gbc.gridy = 10;
        contentPanel.add(existingAccountLabel, gbc);

        JPanel existingAccountPanel = new JPanel();
        existingAccountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        existingAccountPanel.setBackground(Common.FrameBackgroundColor);

        yesExistingAccountRadioButton = new JRadioButton("Yes");
        yesExistingAccountRadioButton.setBackground(Common.FrameBackgroundColor);
        yesExistingAccountRadioButton.setFocusable(false);
        yesExistingAccountRadioButton.setBorder(new EmptyBorder(0, 0, 0, 100));
        existingAccountPanel.add(yesExistingAccountRadioButton);

        noExisitingAccountRadioButton = new JRadioButton("No");
        noExisitingAccountRadioButton.setBackground(Common.FrameBackgroundColor);
        noExisitingAccountRadioButton.setFocusable(false);
        noExisitingAccountRadioButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        existingAccountPanel.add(noExisitingAccountRadioButton);

        gbc.gridx = 1;
        gbc.gridy = 10;
        contentPanel.add(existingAccountPanel, gbc);

        ButtonGroup existingAccountButtonGroup = new ButtonGroup();
        existingAccountButtonGroup.add(yesExistingAccountRadioButton);
        existingAccountButtonGroup.add(noExisitingAccountRadioButton);
    }

    private void initializeNextButton(GridBagConstraints gbc) {
        nextButton = new JButton("Next");
        nextButton.setFocusable(false);
        nextButton.setForeground(Color.BLACK);
        nextButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 11;
        contentPanel.add(nextButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String religion = religionComboBox.getSelectedItem().toString();
        String category = categoryComboBox.getSelectedItem().toString();
        String income = incomeComboBox.getSelectedItem().toString();
        String education = educationComboBox.getSelectedItem().toString();
        String occupation = occupationComboBox.getSelectedItem().toString();
        String pan = panNumberTextField.getText();
        String aadhar = aadharNumberTextField.getText();
        String seniorCitizen = extractSeniorCitizenPressed();
        String existingAccount = extractExistingAccountPressed();

        // validate inputted values
        if (!validateInputValues(pan, aadhar, seniorCitizen, existingAccount))
            return;

        // insert data into database
        insertIntoDatabase(formNo, religion, category, income, education, occupation, pan, aadhar, seniorCitizen,
                existingAccount);

        // create next window and delete this one
        new SignUp3(formNo);
        dispose();
        ;
    }

    private String extractSeniorCitizenPressed() {
        if (yesSeniorCitizenRadioButton.isSelected()) {
            return yesSeniorCitizenRadioButton.getText();
        } else if (noSeniorCitizenRadioButton.isSelected()) {
            return noSeniorCitizenRadioButton.getText();
        }
        return null;
    }

    private String extractExistingAccountPressed() {
        if (yesExistingAccountRadioButton.isSelected()) {
            return yesExistingAccountRadioButton.getText();
        } else if (noExisitingAccountRadioButton.isSelected()) {
            return noExisitingAccountRadioButton.getText();
        }

        return null;
    }

    private boolean validateInputValues(String pan, String aadhar, String seniorCitizen, String existingAccount) {
        if (!Common.ValidateString(pan, "Pan number should not be empty.")) {
            return false;
        }
        if (!Common.ValidateString(aadhar, "Aadhar number should not be empty.")) {
            return false;
        }
        if (!Common.ValidateString(seniorCitizen, "In senior citizen, any one must be selected.")) {
            return false;
        }
        if (!Common.ValidateString(existingAccount, "In existing account, any one must be selected.")) {
            return false;
        }

        return true;
    }

    private void insertIntoDatabase(String formNo, String religion, String category, String income, String education,
            String occupation, String pan, String aadhar, String seniorCitizen, String existingAccount) {

        try (MyCon con = new MyCon()) {

            String query = "INSERT INTO signuptwo VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.connection.prepareStatement(query);
            preparedStatement.setString(1, formNo);
            preparedStatement.setString(2, religion);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, income);
            preparedStatement.setString(5, education);
            preparedStatement.setString(6, occupation);
            preparedStatement.setString(7, pan);
            preparedStatement.setString(8, aadhar);
            preparedStatement.setString(9, seniorCitizen);
            preparedStatement.setString(10, existingAccount);

            preparedStatement.executeUpdate();

        } catch (Exception E) {
            E.printStackTrace();
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
        updateFieldsFonts(scaleFactor);
        updateComboBoxFonts(scaleFactor);
        updateButtonsFonts(scaleFactor);
    }

    private void updateLabelFonts(float scaleFactor) {
        Font scaledFont22 = Common.RalewayBold22.deriveFont(22 * scaleFactor);
        Font scaledFont18 = Common.RalewayBold18.deriveFont(18 * scaleFactor);

        pageNumberLabel.setFont(scaledFont22);
        additionalDetailLabel.setFont(scaledFont22);
        religionLabel.setFont(scaledFont18);
        categoryLabel.setFont(scaledFont18);
        incomeLabel.setFont(scaledFont18);
        educationLabel.setFont(scaledFont18);
        occupationLabel.setFont(scaledFont18);
        panNumberLabel.setFont(scaledFont18);
        aadharNumberLabel.setFont(scaledFont18);
        seniorCitizenLabel.setFont(scaledFont18);
        existingAccountLabel.setFont(scaledFont18);
        formNoLabel.setFont(scaledFont18);
        formNoTextLabel.setFont(scaledFont18);

    }

    private void updateFieldsFonts(float scaleFactor) {

        Font scaledFont18 = Common.RalewayBold14.deriveFont(18 * scaleFactor);

        panNumberTextField.setFont(scaledFont18);
        aadharNumberTextField.setFont(scaledFont18);
    }

    private void updateComboBoxFonts(float scaleFactor) {
        Font scaledFont14 = Common.RalewayBold14.deriveFont(14 * scaleFactor);

        religionComboBox.setFont(scaledFont14);
        categoryComboBox.setFont(scaledFont14);
        incomeComboBox.setFont(scaledFont14);
        educationComboBox.setFont(scaledFont14);
        occupationComboBox.setFont(scaledFont14);
    }

    private void updateButtonsFonts(float scaleFactor) {
        Font scaledFont14 = Common.RalewayBold14.deriveFont(14 * scaleFactor);

        yesSeniorCitizenRadioButton.setFont(scaledFont14);
        noSeniorCitizenRadioButton.setFont(scaledFont14);
        yesExistingAccountRadioButton.setFont(scaledFont14);
        noExisitingAccountRadioButton.setFont(scaledFont14);
        nextButton.setFont(scaledFont14);
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
        new SignUp2("1111");
    }

}
