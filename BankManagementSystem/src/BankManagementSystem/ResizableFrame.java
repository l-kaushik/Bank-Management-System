package BankManagementSystem;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public abstract class ResizableFrame extends JFrame {

    private Timer resizeTimer;

    public ResizableFrame() {
        // Add this frame as a component listener to itself
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (resizeTimer != null) {
                    resizeTimer.stop(); // Stop the previous timer
                }
                // Start a new timer with a small delay (100ms)
                resizeTimer = new Timer(100, ae -> handleResizing()); // Perform resizing tasks
                resizeTimer.setRepeats(false); // Ensure it only triggers once
                resizeTimer.start();
            }
        });
    }

    // Abstract method to be implemented by subclasses
    protected abstract void handleResizing();
}

