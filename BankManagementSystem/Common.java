package BankManagementSystem;

import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.JFrame;

import java.awt.Dimension;

public class Common {
    /*
     * This class contains all the common functionalities that this project might need in most of classes. 
     */

    public static void InitializeJFrame(JFrame object, String title, LayoutManager manager, Dimension size, int defaultCloseOperation, boolean isResizeable, Point locationOnScreen){
        object.setTitle(title);
        object.setLayout(manager);
        object.setSize(size);
        object.setDefaultCloseOperation(defaultCloseOperation);
        object.setLocation(locationOnScreen);
    }
}
