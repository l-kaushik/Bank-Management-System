package BankManagementSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Deposit extends JFrame implements ActionListener {

    String pin = null;

    JTextField amounTextField;

    JButton depositButton;
    JButton backButton;

    Deposit(String inPin) {

        pin = inPin;

        Common.InitializeJFrame(this, "Deposit Money", null, new Dimension(1550, 1080), JFrame.EXIT_ON_CLOSE, false,
                new Point(0, 0));

        JLabel backgroundImageLabel = Common.GetScaledImageWithLabel("icons/atm2.png", 1550, 830);
        backgroundImageLabel.setBounds(0, 0, 1550, 830);
        add(backgroundImageLabel);

        JLabel depositAmountLabel = Common.CreateLabel("ENTER AMOUNT YOU WANT TO DEPOSIT", Color.WHITE,
                Common.SystemBold16, new Rectangle(460, 180, 400, 35));
        backgroundImageLabel.add(depositAmountLabel);

        amounTextField = Common.CreateTextField(Common.RalewayBold22, new Rectangle(460, 230, 320, 25));

        // Add a KeyListener to only allow numeric input
        amounTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // If the character is not a digit, consume the event (ignore input)
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        backgroundImageLabel.add(amounTextField);

        depositButton = Common.CreateButton("DEPOSIT", Common.RalewayBold18, Color.BLACK,
                new Rectangle(700, 362, 150, 35), this);
        backgroundImageLabel.add(depositButton);

        backButton = Common.CreateButton("BACK", Common.RalewayBold18, Color.BLACK,
                new Rectangle(700, 406, 150, 35), this);
        backgroundImageLabel.add(backButton);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String amount = amounTextField.getText();
            Date date = new Date();

            if (e.getSource() == depositButton) {
                if (!Common.ValidateString(amount, "Please enter the amount you want to deposit."))
                    return;
                else {
                    MyCon con = new MyCon();
                        
                    /*
                    * instead of using pin and putting deposit as a text, get the account type,
                    * user id from database and then store it in table
                    */
                        
                    con.statement.executeUpdate(
                            "INSERT INTO bank VALUES('" + pin + "', '" + date + "','Deposit','" + amount + "')");
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully", "Deposit Success",
                    JOptionPane.INFORMATION_MESSAGE);

                    con.close();
                    setVisible(false);
                    new AtmWindow(pin);
                }
            }
            else if(e.getSource() == backButton){
                setVisible(false);
                new AtmWindow(pin);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Deposit("");
    }

}
