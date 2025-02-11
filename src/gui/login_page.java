package gui;

import javax.swing.*;
import java.awt.event.*;

public class login_page extends JFrame {
    private JPanel loginPanel;
    private JTextField unField;
    private JTextField passField;
    private JButton loginBtn;
    private JPanel labelPanel;
    private JLabel signUpLbl;
    private signUpPage signUpPage;
    private ClientHomePage clientHomePage;



    public login_page() {
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

        //Password Field
        passField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Login Button listener
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();

            }
        });

        //go to sign up field
        signUpLbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                signUpPage = new signUpPage();
                signUpPage.setVisible(true);
                setVisible(false);
            }
        });
    }

    public void login() {
        if (unField.getText().equals("ad") && passField.getText().equals("ad")) {
            homePageAdmin homePageAdmin = new homePageAdmin();
            homePageAdmin.setVisible(true);
            setVisible(false);
        } else if (unField.getText().equals("un") && passField.getText().equals("un")) {
            clientHomePage = new ClientHomePage();
            clientHomePage.setVisible(true);
            setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Warning", 2);
        }
    }
    public static void main(String[] args) {
        new login_page();
    }
}

