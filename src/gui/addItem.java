package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addItem extends javax.swing.JFrame {

    private JPanel mainPanel;
    private JLabel equipName;
    private JLabel equipCode;
    private JLabel qty;
    private JButton addButton;
    private JTextField equipNameFld;
    private JTextField equipCodeFld;
    private JTextField qtyFld;

    public addItem() {
        setContentPane(mainPanel);
        setTitle("Menu");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        //Equipment Name JtextField
        equipNameFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Equipment CODE JtextField
        equipCodeFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Equipment QUANTITY JtextField
        qtyFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //ADD BUTTON
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
