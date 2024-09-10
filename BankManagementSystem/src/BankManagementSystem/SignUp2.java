package BankManagementSystem;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

public class SignUp2 extends JFrame implements ActionListener{

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

    SignUp2(String formNo) {

        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Common.InitializeJFrame(this, "APPLICATION FORM", null, new Dimension(850, 700), JFrame.EXIT_ON_CLOSE, false,
                new Point((int) (ScreenSize.getWidth()) / 4, 40),
                Common.FrameBackgroundColor);

        // top center image
        JLabel topCenterImage = Common.GetScaledImageWithLabel("icons/bank.png", 100, 100);
        topCenterImage.setBounds(150, 5, 100, 100);
        add(topCenterImage);

        // show page number
        JLabel pageNumberLabel = Common.CreateLabel("Page 2", Common.RalewayBold22, new Rectangle(300, 30, 600, 30));
        add(pageNumberLabel);

        // show additional detail on page
        JLabel additionalDetailLabel = Common.CreateLabel("Additional Details", Common.RalewayBold22,
                new Rectangle(300, 60, 600, 40));
        add(additionalDetailLabel);

        // show combo box for religions
        JLabel religionLabel = Common.CreateLabel("Religion: ", Common.RalewayBold18, new Rectangle(100, 120, 100, 30));
        add(religionLabel);

        String religions[] = { "Hindu", "Muslim", "Sikh", "Christian", "Others" };
        religionComboBox = Common.CreateComboBox(religions, Common.RalewayBold14,
                new Rectangle(350, 120, 320, 30));
        add(religionComboBox);

        // show combo box for caste categories
        JLabel categoryLabel = Common.CreateLabel("Category: ", Common.RalewayBold18, new Rectangle(100, 170, 100, 30));
        add(categoryLabel);

        String categories[] = { "GEN", "OBC", "SC", "ST", "Others" };
        categoryComboBox = Common.CreateComboBox(categories, Common.RalewayBold14,
                new Rectangle(350, 170, 320, 30));
        add(categoryComboBox);

        // show combo box for incomes
        JLabel incomLabel = Common.CreateLabel("Income: ", Common.RalewayBold18, new Rectangle(100, 220, 100, 30));
        add(incomLabel);

        String incomes[] = { "0", "< 1,50,000", "< 2,50,000", "< 5,00,000", "Upto 10,00,000", "Above 10,00,000" };
        incomeComboBox = Common.CreateComboBox(incomes, Common.RalewayBold14,
                new Rectangle(350, 220, 320, 30));
        add(incomeComboBox);

        // show combo box for education type
        JLabel educationLabel = Common.CreateLabel("Education: ", Common.RalewayBold18,
                new Rectangle(100, 270, 150, 30));
        add(educationLabel);

        String educations[] = { "Non-Graduate", "Graduate", "Post-Graduate", "Doctrate", "Others" };
        educationComboBox = Common.CreateComboBox(educations, Common.RalewayBold14,
                new Rectangle(350, 270, 320, 30));
        add(educationComboBox);

        // show combo box for occupation type
        JLabel occupationLabel = Common.CreateLabel("Occupation: ", Common.RalewayBold18,
                new Rectangle(100, 320, 150, 30));
        add(occupationLabel);

        String occupations[] = { "Salaried", "Self-Employed", "Business", "Student", "Retired", "Others" };
        occupationComboBox = Common.CreateComboBox(occupations, Common.RalewayBold14,
                new Rectangle(350, 320, 320, 30));
        add(occupationComboBox);

        // show pan number text field
        JLabel panNumberLabel = Common.CreateLabel("PAN Number: ", Common.RalewayBold18,
                new Rectangle(100, 370, 150, 30));
        add(panNumberLabel);

        panNumberTextField = Common.CreateTextField(Common.RalewayBold18, new Rectangle(350, 370, 320, 30));
        add(panNumberTextField);

        JLabel aadharNumberLabel = Common.CreateLabel("Aadhar Number: ", Common.RalewayBold18,
                new Rectangle(100, 420, 180, 30));
        add(aadharNumberLabel);

        aadharNumberTextField = Common.CreateTextField(Common.RalewayBold18, new Rectangle(350, 420, 320, 30));
        add(aadharNumberTextField);

        JLabel seniorCitizenLabel = Common.CreateLabel("Senior Citizen: ", Common.RalewayBold18,
                new Rectangle(100, 470, 180, 30));
        add(seniorCitizenLabel);

        yesSeniorCitizenRadioButton = Common.CreateRadioButton("Yes", Common.RalewayBold14, Common.FrameBackgroundColor,
                new Rectangle(350, 470, 100, 30));
        add(yesSeniorCitizenRadioButton);

        noSeniorCitizenRadioButton = Common.CreateRadioButton("No", Common.RalewayBold14, Common.FrameBackgroundColor,
                new Rectangle(460, 470, 100, 30));
        add(noSeniorCitizenRadioButton);

        ButtonGroup seniorCitizeButtonGroup = new ButtonGroup();
        seniorCitizeButtonGroup.add(yesSeniorCitizenRadioButton);
        seniorCitizeButtonGroup.add(noSeniorCitizenRadioButton);

        JLabel existingAccountLabel = Common.CreateLabel("Existing Account: ", Common.RalewayBold18,
                new Rectangle(100, 520, 180, 30));
        add(existingAccountLabel);

        yesExistingAccountRadioButton = Common.CreateRadioButton("Yes", Common.RalewayBold14,
                Common.FrameBackgroundColor, new Rectangle(350, 520, 100, 30));
        add(yesExistingAccountRadioButton);

        noExisitingAccountRadioButton = Common.CreateRadioButton("No", Common.RalewayBold14,
                Common.FrameBackgroundColor, new Rectangle(460, 520, 100, 30));
        add(noExisitingAccountRadioButton);

        ButtonGroup existingAccountButtonGroup = new ButtonGroup();
        existingAccountButtonGroup.add(yesExistingAccountRadioButton);
        existingAccountButtonGroup.add(noExisitingAccountRadioButton);

        JLabel formNoLabel = Common.CreateLabel("Form No: ", Common.RalewayBold18, new Rectangle(670, 10, 100, 30));
        add(formNoLabel);

        JLabel formNoTextLabel = Common.CreateLabel(formNo, Common.RalewayBold18, new Rectangle(760, 10, 100, 30));
        add(formNoTextLabel);

        nextButton = Common.CreateButton("Next", Common.RalewayBold14, Color.BLACK, new Rectangle(570, 600, 100, 30),
                this);
        add(nextButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUp2("0000");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        } catch (Exception E) {
           
        }
    }

}
