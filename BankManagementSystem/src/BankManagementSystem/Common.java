package BankManagementSystem;

import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

public class Common {
    /*
     * This class contains all the common functionalities that this project might
     * need in most of classes.
     */

    public static Color FrameBackgroundColor = new Color(222, 255, 228);

    public static Font RalewayBold22 = new Font("Raleway", Font.BOLD, 22);
    public static Font RalewayBold20 = new Font("Raleway", Font.BOLD, 20);
    public static Font RalewayBold14 = new Font("Raleway", Font.BOLD, 14);
    public static Font RalewayBold18 = new Font("Raleway", Font.BOLD, 18);

    public static Font SystemBold16 = new Font("System", Font.BOLD, 16);

    public static void InitializeJFrame(JFrame object, String title, LayoutManager manager, Dimension size,
            int defaultCloseOperation, boolean isResizeable, Point locationOnScreen) {
        object.setTitle(title);
        object.setLayout(manager);
        object.setSize(size);
        object.setDefaultCloseOperation(defaultCloseOperation);
        object.setLocation(locationOnScreen);
    }

    public static void InitializeJFrame(JFrame object, String title, LayoutManager manager, Dimension size,
            int defaultCloseOperation, boolean isResizeable, Point locationOnScreen, Color backgroundColor) {
        InitializeJFrame(object, title, manager, size, defaultCloseOperation, isResizeable, locationOnScreen);
        object.getContentPane().setBackground(backgroundColor);
    }

    public static JButton CreateButton(String buttonText, Font buttonFont, Color foregroundColor,
            Rectangle bounds, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setFocusable(false);
        button.setFont(buttonFont);
        button.setForeground(foregroundColor);
        button.setBounds(bounds);
        button.addActionListener(actionListener);

        return button;
    }
    public static JLabel CreateLabel(String labelText, Font font, Rectangle bounds) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        label.setBounds(bounds);

        return label;
    }

    public static JLabel CreateLabel(String labelText, Color foregroundColor, Font font, Rectangle bounds) {

        JLabel label = CreateLabel(labelText, font, bounds);
        label.setForeground(foregroundColor);

        return label;
    }

    public static JLabel GetScaledImageWithLabel(String ImagePath, int WidthScale, int HeightScale) {
        // get image -> scale it -> get image again
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource(ImagePath))
                .getImage().getScaledInstance(WidthScale, HeightScale, Image.SCALE_DEFAULT));

        return new JLabel(imageIcon);
    }

    public static JTextField CreateTextField(int columns, Font font, Rectangle bounds){
        JTextField textField = new JTextField(columns);
        textField.setFont(font);
        textField.setBounds(bounds);

        return textField;
    }
    public static JTextField CreateTextField(Font font, Rectangle bounds){
        return CreateTextField(0, font, bounds);
    }

    public static JRadioButton CreateRadioButton(String text, Font font, Color backgroundColor, Rectangle bounds){
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(font);
        radioButton.setBounds(bounds);
        radioButton.setFocusable(false);
        radioButton.setBackground(backgroundColor);

        return radioButton;
    }

    public static <T> JComboBox<T> CreateComboBox(T[] items, Color backgroundColor, Font font, Rectangle bounds){
        JComboBox<T> comboBox = new JComboBox<T>(items);
        comboBox.setBackground(backgroundColor);
        comboBox.setFont(font);
        comboBox.setBounds(bounds);

        return comboBox;
    }

    public static <T> JComboBox<T> CreateComboBox(T[] items, Font font, Rectangle bounds){
        return CreateComboBox(items, new Color(227, 223, 222), font, bounds);
    }

    public static JCheckBox CreateCheckBox(String text, Color backgorundColor, Font font, Rectangle bounds){
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setBackground(backgorundColor);
        checkBox.setFont(font);
        checkBox.setBounds(bounds);
        checkBox.setFocusable(false);

        return checkBox;
    }

    public static boolean ValidateString(String str, String message){
        if(str == null || str.isEmpty()){
            JOptionPane.showMessageDialog(null, message.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true;
    }
}
