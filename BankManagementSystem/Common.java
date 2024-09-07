package BankManagementSystem;

import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

public class Common {
    /*
     * This class contains all the common functionalities that this project might
     * need in most of classes.
     */

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
        object.setTitle(title);
        object.setLayout(manager);
        object.setSize(size);
        object.setDefaultCloseOperation(defaultCloseOperation);
        object.setLocation(locationOnScreen);
        object.getContentPane().setBackground(backgroundColor);
    }

    public static JButton CreateButton(String buttonText, boolean isFocusable, Font buttonFont, Color foregroundColor,
            Rectangle bounds, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setFocusable(isFocusable);
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
        JLabel label = new JLabel(labelText);
        label.setForeground(foregroundColor);
        label.setFont(font);
        label.setBounds(bounds);

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
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setBounds(bounds);

        return textField;
    }
}
