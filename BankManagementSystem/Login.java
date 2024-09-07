package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
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
        titleLabel = CreateLabel("WELCOME TO ATM", Color.WHITE, new Font("AvantGarde", Font.BOLD, 38),
                new Rectangle(230, 125, 450, 40));
        add(titleLabel);

        // get card number from user
        cardNumberLabel = CreateLabel("Card No: ", Color.WHITE, new Font("Ralway", Font.BOLD, 28),
                new Rectangle(150, 190, 375, 30));
        add(cardNumberLabel);

        cardNumberTextField = new JTextField(15);
        cardNumberTextField.setBounds(325, 190, 230, 30);
        cardNumberTextField.setFont(new Font("Arial", Font.BOLD, 14));
        add(cardNumberTextField);

        // get pin number from user
        pinLabel = CreateLabel("Pin No: ", Color.WHITE, new Font("Ralway", Font.BOLD, 28),
                new Rectangle(150, 250, 375, 30));
        add(pinLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(325, 250, 230, 30);
        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        add(passwordField);

        // buttons
        signInButton = CreateButton("SIGN IN", false, new Font("Arial", Font.BOLD, 14), Color.BLACK,
                new Rectangle(325, 300, 100, 30), this);
        add(signInButton);

        clearButton = CreateButton("CLEAR", false, new Font("Arial", Font.BOLD, 14), Color.BLACK,
                new Rectangle(455, 300, 100, 30), this);
        add(clearButton);

        signUpButton = CreateButton("SIGN UP", false, new Font("Arial", Font.BOLD, 14), Color.BLACK,
                new Rectangle(325, 350, 230, 30), this);
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

    private JButton CreateButton(String buttonText, boolean isFocusable, Font buttonFont, Color foregroundColor,
            Rectangle bounds, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setFocusable(isFocusable);
        button.setFont(buttonFont);
        button.setForeground(foregroundColor);
        button.setBounds(bounds);
        button.addActionListener(actionListener);

        return button;
    }

    private JLabel CreateLabel(String labelText, Color foregroundColor, Font font, Rectangle bounds) {
        JLabel label = new JLabel(labelText);
        label.setForeground(foregroundColor);
        label.setFont(font);
        label.setBounds(bounds);

        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == signInButton) {

            } else if (e.getSource() == signUpButton) {

            } else if (e.getSource() == clearButton) {
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
