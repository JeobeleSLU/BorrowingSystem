package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class homePageAdmin extends JFrame {

    public Label receiptArea;
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JPanel dashboard;
    private JLabel dashboardTxt;
    private JLabel addItem;
    private JLabel logs;
    private JLabel history;
    private JLabel equipment;
    private JTable equipTable;
    private JPanel centerPanel;
    private boolean showingHistory = false;


    public homePageAdmin() {
        setContentPane(mainPanel);
        setTitle("Admin homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        populateTable();

        setupHoverEffect(addItem);
        setupHoverEffect(logs);
        setupHoverEffect(history);
        setupHoverEffect(equipment);

        //ADD ITEM
        addItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new addItem();
                //   dispose();


            }
        });

        //LOGS - borrowed equipment list
        logs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showingHistory = false;  // Set to false to show logs
                populateTable();
            }
        });

        //All finished transactions
        history.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showingHistory = true;  // Set to true to show history
                populateTable();
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

    public void setupHoverEffect(JLabel label) {
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

    void populateTable() {
        centerPanel.setLayout(new BorderLayout());

        String[] columnNames;
        Object[][] data;

        if (showingHistory) {
            columnNames = new String[]{"StudentId", "Equipment Name", "Date Borrowed", "Status"};
            data = new Object[][]{
                    {"2242815", "Drone", "02-14-25", "Returned"},
                    {"2240696", "Camera", "02-14-25", "Returned"},
                    {"2241615", "Switch", "02-14-25", "Returned"},
                    {"2241122", "Router", "02-14-25", "Returned"}
            };
        } else {
            columnNames = new String[]{"StudentId", "Equipment Name", "Date", "Time", "Status"};
            data = new Object[][]{
                    {"2242815", "Drone", "02-14-25", "1:00-2:00", "In progress"},
                    {"2240696", "Camera", "02-14-25", "1:00-2:00", "Returned"},
                    {"2241615", "Switch", "02-14-25", "1:00-2:00", "Not claimed"},
                    {"2241122", "Router", "02-14-25", "1:00-2:00", "In progress"}
            };
        }

        // Create the table model with the updated data
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create the table and configure its properties
        equipTable = new JTable(model);
        equipTable.setRowHeight(50);
        equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Wrap JTable in JScrollPane
        JScrollPane scrollPane = new JScrollPane(equipTable);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        // Set column widths
        equipTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        equipTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        equipTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        equipTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        centerPanel.add(scrollPane, BorderLayout.WEST);

        // Ensure UI updates properly
        SwingUtilities.invokeLater(() -> {
            equipTable.revalidate();
            equipTable.repaint();
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        setVisible(true);
    }
}