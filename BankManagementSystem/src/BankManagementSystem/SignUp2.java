package BankManagementSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SignUp2 extends ResizableFrame implements ActionListener{

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

    private void setupFrame(Dimension screenSize) {
        setTitle("Application Form");
        setLayout(new BorderLayout());
        setSize((int) (screenSize.width/2), (int) (screenSize.height/2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void initializeComponents(Dimension screenSize){
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Common.FrameBackgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
        gbc.weightx = 1.0; // Allow components to expand horizontally
    
    
        // component initialization here


        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    
    }

    SignUp2(String inFormNo) {

        formNo = inFormNo;

        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setupFrame(ScreenSize);

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

    
    @Override
    public void actionPerformed(ActionEvent e) {
                String religion = religionComboBox.getSelectedItem().toString();
                String category = categoryComboBox.getSelectedItem().toString();
                String income = incomeComboBox.getSelectedItem().toString();
                String education = educationComboBox.getSelectedItem().toString();
                String occupation = occupationComboBox.getSelectedItem().toString();      
                String pan = panNumberTextField.getText();
                String aadhar = aadharNumberTextField.getText();
                String seniorCitizen = null;
                if(yesSeniorCitizenRadioButton.isSelected()){seniorCitizen = yesSeniorCitizenRadioButton.getText();}
                else if(noSeniorCitizenRadioButton.isSelected()){seniorCitizen = noSeniorCitizenRadioButton.getText();}

                String existingAccount = null;
                if(yesExistingAccountRadioButton.isSelected()){existingAccount = yesExistingAccountRadioButton.getText();}
                else if(noExisitingAccountRadioButton.isSelected()){existingAccount = noExisitingAccountRadioButton.getText();}

                try {

                        if(!Common.ValidateString(pan, "Pan number should not be empty.")) {return;} 
                        if(!Common.ValidateString(aadhar, "Aadhar number should not be empty.")) {return;}
                        if(!Common.ValidateString(seniorCitizen,"In senior citizen, any one must be selected.")) {return;}
                        if(!Common.ValidateString(existingAccount, "In existing account, any one must be selected.")) {return;}
                        MyCon con = new MyCon();
                        
                        String query = "INSERT INTO signuptwo values('"+formNo+"','"+religion+"','"+category+"','"+income+"','"+education+"','"+occupation+"','"+pan+"','"+aadhar+"','"+seniorCitizen+"','"+existingAccount+"')";
                        con.statement.executeUpdate(query);
                        
                        con.close();
                        new SignUp3(formNo);
                        dispose();

                } catch (Exception E) {
                        E.printStackTrace();
                }
        }       
        

        public static void main(String[] args) {
            new SignUp2("1111");
        }

        @Override
        protected void handleResizing() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'handleResizing'");
        }
}
