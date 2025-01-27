import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class signUpPage extends JFrame {


    private JPanel loginPanel;
    private JPanel labelPanel;
    private JTextField unField;
    private JTextField passField;
    private JButton signUpBtn;
    private JTextField emailField;

    public signUpPage() {
        setContentPane(loginPanel);
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        //Username Field
        unField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //PASSWORD FIELD
        passField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        //SIGN UP BUTTON
        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //EMAIL FIELD
        emailField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
