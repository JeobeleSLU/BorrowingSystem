package gui;

import javax.swing.*;

public class homePage extends JFrame {
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JPanel dashboard;
    private JLabel dashboardTxt;
    private JLabel addItem;
    private JLabel logs;
    private JLabel history;
    private JLabel equipment;
    private JPanel tablePanel;

    public homePage() {
        setContentPane(mainPanel);
        setTitle("Cline Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
