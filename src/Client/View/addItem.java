package Client.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class addItem extends javax.swing.JFrame {

    private JPanel mainPanel;
    private JLabel equipName;
    private JLabel equipCode;
    private JButton addButton;
    private JTextField equipNameFld;
    private JTextField equipCodeFld;
    private JTextField typefld;
    private JTextField qtyfld;
    private JLabel type;
    public String[] row;


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


        //ADD BUTTON
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                row = newRow();
                System.out.println("ROW: " + Arrays.toString(row));

            }
        });
        qtyfld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JTextField getEquipNameFld() {
        return equipNameFld;
    }

    public JTextField getEquipCodeFld() {
        return equipCodeFld;
    }

    public JTextField getTypefld() {
        return typefld;
    }

    public JTextField getQtyfld() {
        return qtyfld;
    }

    String[] newRow() {
        return new String[]{equipCodeFld.getText(), equipNameFld.getText(), null};
    }

    public static void main(String[] args) {
        new addItem();
    }
}
