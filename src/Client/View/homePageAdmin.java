package Client.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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
    private boolean showingLogs = true;
    private boolean showingEquipment = false;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
    private final String stamp = LocalDateTime.now().format(dateTimeFormatter);


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

        System.out.println(stamp);


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
                showingLogs = true;
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
                showingEquipment = true;
                populateTable();
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
        System.out.println("Showing logs: " + showingLogs);
        System.out.println("Showing history: " + showingHistory);
        System.out.println("Showing equipment: " + showingEquipment);

    }

    void populateTable() {
        centerPanel.setLayout(new BorderLayout());

        String[] columnNames;
        Object[][] data;

        if (showingHistory) {
            showingLogs = false;
            showingEquipment = false;
            columnNames = new String[]{"StudentId", "Equipment Name", "Date Borrowed", "Status"};
            data = new Object[][]{
                    {"2242815", "Drone", "02-14-25", "Returned"},
                    {"2240696", "Camera", "02-14-25", "Returned"},
                    {"2241615", "Switch", "02-14-25", "Returned"},
                    {"2241122", "Router", "02-14-25", "Returned"}
            };
            showingHistory = false;
        } else if (showingLogs) {
            showingHistory = false;
            showingEquipment = false;
            columnNames = new String[]{"StudentId", "Equipment Name", "Date", "Time", "Status"};
            data = new Object[][]{
                    {"2242815", "Drone", "02-14-25", "1:00-2:00", "In progress"},
                    {"2240696", "Camera", "02-14-25", "1:00-2:00", "Returned"},
                    {"2241615", "Switch", "02-14-25", "1:00-2:00", "Not claimed"},
                    {"2241122", "Router", "02-14-25", "1:00-2:00", "In progress"}
            };
            showingLogs = false;
        } else if (showingEquipment) {
            showingHistory = false;
            showingLogs = false;
            columnNames = new String[]{"Equipment ID", "Equipment Name", "Flag"};
            data = new Object[][]{
                    {"012345", "Drone", "( removed button)"},
                    {"012346", "Camera", "( removed button)"},
                    {"012347", "Switch", "( removed button)"},
            };
            showingEquipment = false;
        } else {
            columnNames = new String[]{};
            data = new Object[][]{};
        }

        // Check if columnNames or data is empty before creating the table
        if (columnNames.length == 0 || data.length == 0) {
            System.out.println("No data to display.");
            return; // Skip populating the table
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        centerPanel.removeAll();

        equipTable = new JTable(model);
        equipTable.setRowHeight(50);
        equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(equipTable);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        equipTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        equipTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        equipTable.getColumnModel().getColumn(2).setPreferredWidth(120);
//        equipTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
            equipTable.revalidate();
            equipTable.repaint();
            centerPanel.revalidate();
            centerPanel.repaint();
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        setVisible(true);
    }
}