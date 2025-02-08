package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class clientHomePage extends JFrame {
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JPanel dashboard;
    private JLabel dashboardTxt;
    private JLabel logs;
    private JLabel equipment;
    private JPanel tablePanel;

    public clientHomePage() {
        setContentPane(mainPanel);
        setTitle("Cline Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);


        dashboardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

            }
        });
    }
}
