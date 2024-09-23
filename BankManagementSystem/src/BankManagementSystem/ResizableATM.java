package BankManagementSystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class ResizableATM extends ResizableFrame {

    protected ImageIcon backgroundIcon;
    protected Image backgroundImage;
    protected JLabel backgroundImageLabel;

    ResizableATM(String title) {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle(title);
        setLayout(new BorderLayout());
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        setupBackgroundImage();

        add(backgroundImageLabel, BorderLayout.CENTER);

        setVisible(true);
    }

    protected void setupBackgroundImage() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();;

        backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icons/atm2.png"));
        if (backgroundIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading the image.");
        }

        backgroundImage = backgroundIcon.getImage().getScaledInstance((int) (screenSize.getWidth()),
                (int) screenSize.getHeight(), Image.SCALE_SMOOTH);
        backgroundImageLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundImageLabel.setLayout(null);
    }

    @Override
    protected void handleResizing() {

        if(!isVisible()) return;

        scaleBackgroundImage();
    }

    protected void scaleBackgroundImage() {

        Dimension size = getSize();
        // Offload image scaling to a background thread
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // Heavy operation: scaling the background image
                backgroundImage = backgroundIcon.getImage().getScaledInstance(size.width, size.height,
                        Image.SCALE_SMOOTH);

                return null;
            }

            @Override
            protected void done() {
                // Once the image is scaled, update the GUI
                backgroundImageLabel.setIcon(new ImageIcon(backgroundImage));
                backgroundImageLabel.setPreferredSize(new Dimension(size.width, size.height));
                backgroundImageLabel.revalidate();
            }
        }.execute();
    }
}
