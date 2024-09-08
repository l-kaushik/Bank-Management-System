package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;

public class SignUp extends JFrame implements ActionListener{

    Random random = new Random();
    long first4 = (random.nextLong() % 9000L) + 1000L;
    String first = " " + Math.abs(first4);

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

    SignUp() {

        Font RalewayBold22 = new Font("Raleway", Font.BOLD, 22);
        Font RalewayBold20 = new Font("Raleway", Font.BOLD, 20);
        Font RalewayBold14 = new Font("Raleway", Font.BOLD, 14);

        Color FrameBackgroundColor = new Color(222, 255, 228);

        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Common.InitializeJFrame(this, "APPLICATION FORM", null, new Dimension(850, 800), JFrame.EXIT_ON_CLOSE, false,
                new Point((int) (ScreenSize.getWidth() - 100) / 4, 20),
                FrameBackgroundColor);

        // top center image
        JLabel topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", 100, 100);
        topCenterImage.setBounds(25, 10, 100, 100);
        add(topCenterImage);

        // show application number
        JLabel applicationNumberLabel = Common.CreateLabel("APPLICATION FORM NO. " + first,
                new Font("Raleway", Font.BOLD, 38), new Rectangle(160, 20, 600, 40));
        add(applicationNumberLabel);

        // show page number
        JLabel pageNumberLabel = Common.CreateLabel("Page 1", RalewayBold22, new Rectangle(330, 70, 600, 30));
        add(pageNumberLabel);

        // show personal details
        JLabel personalDetailsLabel = Common.CreateLabel("Personal Details", RalewayBold22,
                new Rectangle(290, 90, 600, 30));
        add(personalDetailsLabel);

        // show name label and get name
        JLabel nameLabel = Common.CreateLabel("Name: ", RalewayBold20, new Rectangle(100, 190, 100, 30));
        add(nameLabel);

        nameTextField = Common.CreateTextField(RalewayBold14, new Rectangle(300, 190, 400, 30));
        add(nameTextField);

        // show father name label and get it
        JLabel fatherNameLabel = Common.CreateLabel("Father's Name: ", RalewayBold20, new Rectangle(100, 240, 200, 30));
        add(fatherNameLabel);

        fatherNameTextField = Common.CreateTextField(RalewayBold14, new Rectangle(300, 240, 400, 30));
        add(fatherNameTextField);

        // show buttons for gender selection
        JLabel genderLabel = Common.CreateLabel("Gender: ", RalewayBold20, new Rectangle(100, 290, 200, 30));
        add(genderLabel);

        maleRadioButton = Common.CreateRadioButton("Male", RalewayBold14, FrameBackgroundColor,
                new Rectangle(300, 290, 60, 30));
        add(maleRadioButton);

        femaleRadioButton = Common.CreateRadioButton("Female", RalewayBold14, FrameBackgroundColor,
                new Rectangle(450, 290, 90, 30));
        add(femaleRadioButton);

        othersGenderRadioButton = Common.CreateRadioButton("Others", RalewayBold14, FrameBackgroundColor,
                new Rectangle(600, 290, 120, 30));
        add(othersGenderRadioButton);

        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        genderButtonGroup.add(othersGenderRadioButton);

        // show calender to get DOB
        JLabel dateOfBirthLabel = Common.CreateLabel("Date Of Birth: ", RalewayBold20,
                new Rectangle(100, 340, 200, 30));
        add(dateOfBirthLabel);

        dateChooser = new JDateChooser();
        dateChooser.setForeground(new Color(105, 105, 105));
        dateChooser.setBounds(300, 340, 400, 30);
        add(dateChooser);

        // show and get email address
        JLabel emaiLabel = Common.CreateLabel("Email Address: ", RalewayBold20, new Rectangle(100, 390, 200, 30));
        add(emaiLabel);

        emailTextField = Common.CreateTextField(RalewayBold14, new Rectangle(300, 390, 400, 30));
        add(emailTextField);

        // show and get marital status
        JLabel maritalStatusLabel = Common.CreateLabel("Marital Status: ", RalewayBold20,
                new Rectangle(100, 440, 200, 30));
        add(maritalStatusLabel);

        marriedRadioButton = Common.CreateRadioButton("Married", RalewayBold14, FrameBackgroundColor,
                new Rectangle(300, 440, 90, 30));
        add(marriedRadioButton);

        unmarriedRadioButton = Common.CreateRadioButton("Unmarried", RalewayBold14, FrameBackgroundColor,
                new Rectangle(450, 440, 120, 30));
        add(unmarriedRadioButton);

        othersMaritalRadioButton = Common.CreateRadioButton("Others", RalewayBold14, FrameBackgroundColor,
                new Rectangle(600, 440, 150, 30));
        add(othersMaritalRadioButton);

        ButtonGroup maritalStatusButtonGroup = new ButtonGroup();
        maritalStatusButtonGroup.add(marriedRadioButton);
        maritalStatusButtonGroup.add(unmarriedRadioButton);
        maritalStatusButtonGroup.add(othersMaritalRadioButton);

        // show and get residential address
        JLabel residentAddressLabel = Common.CreateLabel("Address: ", RalewayBold20, new Rectangle(100, 490, 200, 30));
        add(residentAddressLabel);

        residentAddressTextField = Common.CreateTextField(RalewayBold14, new Rectangle(300, 490, 400, 30));
        add(residentAddressTextField);

        // show and get city name
        JLabel cityLabel = Common.CreateLabel("City: ", RalewayBold20, new Rectangle(100, 540, 200, 30));
        add(cityLabel);

        cityTextField = Common.CreateTextField(RalewayBold14, new Rectangle(300, 540, 400, 30));
        add(cityTextField);

        // show and get city name
        JLabel pinLabel = Common.CreateLabel("Pin: ", RalewayBold20, new Rectangle(100, 590, 200, 30));
        add(pinLabel);

        pinTextField = Common.CreateTextField(RalewayBold14, new Rectangle(300, 590, 400, 30));
        add(pinTextField);

        // show and get state name
        JLabel stateLabel = Common.CreateLabel("State: ", RalewayBold20, new Rectangle(100, 640, 200, 30));
        add(stateLabel);

        stateTextField = Common.CreateTextField(RalewayBold14, new Rectangle(300, 640, 400, 30));
        add(stateTextField);

        nextButton = Common.CreateButton("Next", RalewayBold14, Color.BLACK, new Rectangle(620, 710, 80, 30), this);
        add(nextButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            String formNo = first;
            String name = nameTextField.getText();
            
            String fatherName = fatherNameTextField.getText();
            
            String dateOfBirth = null;
            if(dateChooser.getDate() != null){dateOfBirth = new SimpleDateFormat("dd-MM-YYYY").format(dateChooser.getDate());}
            
            String gender = null;
            if(maleRadioButton.isSelected()){gender = maleRadioButton.getText();}
            else if(femaleRadioButton.isSelected()){gender = femaleRadioButton.getText();}
            else if(othersGenderRadioButton.isSelected()){gender = othersGenderRadioButton.getText();}
            
            String email = emailTextField.getText();
            
            String maritalStatus = null;
            if(marriedRadioButton.isSelected()){maritalStatus = marriedRadioButton.getText();}
            else if(unmarriedRadioButton.isSelected()){maritalStatus = unmarriedRadioButton.getText();}
            else if(othersMaritalRadioButton.isSelected()){maritalStatus = othersMaritalRadioButton.getText();}
            
            String residentAddress = residentAddressTextField.getText();
            String pincode = pinTextField.getText();
            String cityName = cityTextField.getText();
            String stateName = stateTextField.getText();

            try {
                    
                    if(validateForm(name, fatherName, dateOfBirth, gender, email, maritalStatus, residentAddress, pincode, cityName, stateName)){
                        MyCon con = new MyCon();
                        
                        String query = "INSERT INTO signup values('"+formNo+"','"+name+"','"+fatherName+"','"+dateOfBirth+"','"+gender+"','"+email+"','"+maritalStatus+"','"+residentAddress+"','"+cityName+"','"+pincode+"','"+stateName+"')";
                        con.statement.executeUpdate(query);

                        new SignUp2();
                        setVisible(false);
                }
                
        } catch (Exception E) {
                E.printStackTrace();
        }
    }

    private static boolean validateForm(String name, String fatherName, String dateOfBirth,
                                     String gender, String email, String maritalStatus, String residentAddress,
                                     String pincode, String cityName, String stateName) {

        StringBuilder errorMessage = new StringBuilder();

        if (name.isEmpty()) errorMessage.append("Name field can't be empty.\n");
        if (fatherName.isEmpty()) errorMessage.append("Father name field can't be empty.\n");
        if (dateOfBirth == null || dateOfBirth.isEmpty()) errorMessage.append("Date of birth can't be empty.\n");
        if (gender == null || gender.isEmpty()) errorMessage.append("Gender must be selected.\n");
        if (email.isEmpty()) errorMessage.append("Email field can't be empty.\n");
        if (maritalStatus == null || maritalStatus.isEmpty()) errorMessage.append("Marital status must be selected.\n");
        if (residentAddress.isEmpty()) errorMessage.append("Resident address can't be empty.\n");
        if (pincode.isEmpty()) errorMessage.append("Pincode can't be empty.\n");
        if (cityName.isEmpty()) errorMessage.append("City name can't be empty.\n");
        if (stateName.isEmpty()) errorMessage.append("State name can't be empty.\n");

        if (errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(null, errorMessage.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }

        return true;
    }
    
    public static void main(String[] args) {
        new SignUp();
    }

}
