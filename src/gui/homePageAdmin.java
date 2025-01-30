package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class homePageAdmin extends JFrame {

    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JPanel dashboard;
    private JLabel dashboardTxt;
    private JLabel addItem;
    private JLabel logs;
    private JLabel history;
    private JLabel equipment;


    public homePageAdmin() {
        setContentPane(mainPanel);
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);


        setupHoverEffect(addItem);
        setupHoverEffect(logs);
        setupHoverEffect(history);
        setupHoverEffect(equipment);

        //ADD ITEM
        addItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        //LOGS - borrowed equipment list
        logs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        //All finished transactions
        history.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        //EQUIPMENT LIST
        equipment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    private void setupHoverEffect(JLabel label) {
        // Save the default background and foreground colors
        Color defaultBackground = new Color(14, 40, 90);
       // Color hoverBackground = new Color(0, 161, 173); //  lighter shade for hover
        Color defaultForeground = label.getForeground();
        Color hoverForeground = Color.black;

        label.setOpaque(true);
        label.setBackground(defaultBackground);

        //mouse listener
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
           //     label.setBackground(hoverBackground);
                label.setForeground(hoverForeground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(defaultBackground);
                label.setForeground(defaultForeground);
            }
        });
    }
    public static void main(String[] args) {
        new homePageAdmin();
    }
}
