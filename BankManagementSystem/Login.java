package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel cardNumberLabel;
    JLabel pinLabel;

    JTextField cardNumberTextField;
    JPasswordField passwordField;

    JButton clearButton;
    JButton signInButton;
    JButton signUpButton;

    Login() {

        super("Bank Management System");
        setLayout(null);
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // set the frame location in center
        setLocation((int) (ScreenSize.getWidth() - 100) / 4, (int) ScreenSize.getHeight() / 4);

        // top center image
        JLabel topCenterImage = GetScaledImageWithLabel("icons/bank.png", 100, 100);
        topCenterImage.setBounds((int) this.getWidth() / 2 - 50, 10, 100, 100);
        add(topCenterImage);

        // bottom right card image
        JLabel cardImage = GetScaledImageWithLabel("icons/card.png", 100, 100);
        cardImage.setBounds((int) this.getWidth() - 200, 350, 100, 100);
        add(cardImage);

        // title below top center image
        titleLabel = new JLabel("WELCOME TO ATM");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("AvantGarde", Font.BOLD, 38));
        titleLabel.setBounds(230, 125, 450, 40);
        add(titleLabel);

        // get card number from user
        cardNumberLabel = new JLabel("Card No: ");
        cardNumberLabel.setFont(new Font("Ralway", Font.BOLD, 28));
        cardNumberLabel.setForeground(Color.WHITE);
        cardNumberLabel.setBounds(150, 190, 375, 30);
        add(cardNumberLabel);

        cardNumberTextField = new JTextField(15);
        cardNumberTextField.setBounds(325, 190, 230, 30);
        cardNumberTextField.setFont(new Font("Arial", Font.BOLD, 14));
        add(cardNumberTextField);

        // get pin number from user
        pinLabel = new JLabel("Pin No: ");
        pinLabel.setFont(new Font("Ralway", Font.BOLD, 28));
        pinLabel.setForeground(Color.WHITE);
        pinLabel.setBounds(150, 250, 375, 30);
        add(pinLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(325, 250, 230, 30);
        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        add(passwordField);

        signInButton = new JButton("SIGN IN");
        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        signInButton.setForeground(Color.BLACK);
        signInButton.setFocusable(false);
        signInButton.setBounds(325, 300, 100, 30);
        signInButton.addActionListener(this);
        add(signInButton);

        clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setForeground(Color.BLACK);
        clearButton.setFocusable(false);
        clearButton.setBounds(455, 300, 100, 30);
        clearButton.addActionListener(this);
        add(clearButton);

        signUpButton = new JButton("SIGN UP");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setFocusable(false);
        signUpButton.setBounds(325, 350, 230, 30);
        signUpButton.addActionListener(this);
        add(signUpButton);

        // background image
        JLabel backgroundImage = GetScaledImageWithLabel("icons/backbg.png", this.getWidth(), this.getHeight());
        backgroundImage.setBounds(0, 0, this.getWidth(), this.getHeight());
        add(backgroundImage);

        setVisible(true);
    }

    private JLabel GetScaledImageWithLabel(String ImagePath, int WidthScale, int HeightScale) {
        // get image -> scale it -> get image again
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(ImagePath))
                .getImage().getScaledInstance(WidthScale, HeightScale, Image.SCALE_DEFAULT));

        return new JLabel(imageIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        if(e.getSource() == signInButton){
       
        }
        else if(e.getSource() == signUpButton){

        }
        else if(e.getSource() == clearButton){
            cardNumberTextField.setText("");
            passwordField.setText("");
        }
        
      } catch (Exception E) {
        E.printStackTrace();
      }
    }

    public static void main(String[] args) {
        new Login();
    }

}
