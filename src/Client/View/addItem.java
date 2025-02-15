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
    private JTextField qtyFld;
    private JTextField qty;
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
        qtyFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        //ADD BUTTON
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                row = newRow();
                System.out.println("ROW: " + Arrays.toString(row));

            }
        });
    }


    String[] newRow() {
        return new String[]{equipCodeFld.getText(), equipNameFld.getText(), null};
    }
}
