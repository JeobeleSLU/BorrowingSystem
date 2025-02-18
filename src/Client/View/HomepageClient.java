package Client.View;

import Server.Model.Equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HomepageClient extends JFrame {
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JPanel dashboard;
    private JLabel dashboardTxt;
    private JLabel borrowedItemlbl;
    private JLabel equipment;
    private JPanel centerPanel;
    private JTable equipTable;
    private JComboBox types;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel equipLabel;
    private JComboBox monthBox;
    private JComboBox startTimeBox;
    private JComboBox endTimeBox;
    homePageAdmin homePageAdmin;
    private JTextArea receiptArea;
    private boolean showingEquipList = true;
    private boolean showingBorrowed = false;
    private boolean showingSettings = false;
    private boolean isClicked = false;
    private boolean show;
    ButtonEditor editor;





    public HomepageClient() {
        setContentPane(mainPanel);
        setTitle("Client Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);


        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        JScrollPane receiptScrollPane = new JScrollPane(receiptArea);
        receiptScrollPane.setPreferredSize(new Dimension(350, 500));
        centerPanel.add(receiptScrollPane, BorderLayout.EAST);

        homePageAdmin = new homePageAdmin();
        homePageAdmin.setupHoverEffect(equipLabel);
        homePageAdmin.setupHoverEffect(borrowedItemlbl);
        homePageAdmin.setupHoverEffect(equipment);
        homePageAdmin.setVisible(false);

        populateTable();

        types.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = types.getSelectedItem().toString();
                filterTableByType(type);

            }
        });
        setVisible(true);

        //SEARCH FIELD FOR SEARCHING
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndUpdateTable();

            }
        });
        //Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndUpdateTable();

            }
        });
        equipLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showingEquipList = true;
                populateTable();
            }
        });

        borrowedItemlbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showingBorrowed = true;
                populateTable();
            }
        });

        equipment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showingSettings = true;
                populateTable();
            }
        });
    }

    //=============================================================================================
    private void filterTableByType(String type) {
        String selectedType = types.getSelectedItem().toString().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) equipTable.getModel();
        model.setRowCount(0);

        Object[][] data = new Object[][] {
                {new ImageIcon("./src/gui/drone.jpg"), "Drones", "4", "add"},
                {new ImageIcon("./src/gui/camera.png"), "Camera", "5", "add"},
                {3, "Stabelizer", "3", "add"},
                {4, "Swtiches", "4", "add"}
        };

        for (Object[] row : data) {
            if (row[1].toString().equalsIgnoreCase(selectedType)) {
                model.addRow(row);
            }
        }
    }
    public void populateTable() {
        // Clear previous components and set layout
        centerPanel.removeAll();
        centerPanel.setLayout(new BorderLayout());
//=================================================================================================================
        //FOR DATE AND TIME
        // Create a panel for JDateChooser & JSpinner (Time Chooser)
        JPanel datePanel = new JPanel();







        //=================================================================================================
        //JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 100, 5)); // Adds 20px horizontal gap

        JButton borrowButton = new JButton("Borrow");
        datePanel.add(borrowButton);

        centerPanel.add(datePanel, BorderLayout.NORTH);


        //BORROW BUTTON FOR CONFIRMATION SO THE RESERVATION WILL GO TO ADMIN
        //todo: get the receipt area details to finalize the reservation
        borrowButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            }
        });



        //======================================================================================================
        // Add the date and time panel to the top of the center panel
     //   centerPanel.add(datePanel, BorderLayout.NORTH);
//=====================================================================================================================
        // Define table columns and data
        String[] columnNames;
        Object[][] data;

        // Example equipment images
        ImageIcon droneIcon = new ImageIcon("./src/gui/drone.jpg");
        ImageIcon cameraIcon = new ImageIcon("./src/gui/camera.png");

        if (showingEquipList) {
            showingBorrowed = false;
            showingSettings = false;
            columnNames = new String[]{"Image", "Equipment Name", "Quantity", "Avail"};
            data = new Object[][]{
                    {droneIcon, "Drone", "4", "add"},
                    {cameraIcon, "Camera", "5", "add"},
                    {3, "Stabilizer", "3", "add"},
                    {4, "Switch", "4", "add"}
            };
            showingEquipList = false;
            show = true;

        } else if (showingBorrowed) {
            showingEquipList = false;
            showingSettings = false;
            columnNames = new String[]{"Equipment Name", "Borrowed Date", "Status"};
            data = new Object[][]{
                    {"Drone", "12-01-2025", "Returned"},
                    {"Switch", "02-27-2026", "In Progress"}
            };

            showingBorrowed = false;
            show = false;

        } else if (showingSettings) {
            showingEquipList = false;
            showingBorrowed = false;
            showingSettings = false;
            show = false;

            // Display user profile settings
            centerPanel.setLayout(new GridLayout(10, 50, 200, 2200));

            JLabel firstNameLabel = new JLabel("First Name: ");
            JLabel firstName = new JLabel("John");

            JLabel lastNameLabel = new JLabel("Last Name: ");
            JLabel lastName = new JLabel("Doe");

            JLabel emailLabel = new JLabel("Email: ");
            JLabel email = new JLabel("john.doe@example.com");

            // Add labels to the panel
            centerPanel.add(firstNameLabel);
            centerPanel.add(firstName);
            centerPanel.add(lastNameLabel);
            centerPanel.add(lastName);
            centerPanel.add(emailLabel);
            centerPanel.add(email);

            centerPanel.revalidate();
            centerPanel.repaint();
            return; // Exit early to avoid setting up the table
        } else {
            columnNames = new String[]{};
            data = new Object[][]{};
        }


        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
            centerPanel.removeAll();

        equipTable = new JTable(model);
        equipTable.setRowHeight(50);
        equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        // Wrap JTable in JScrollPane
        JScrollPane scrollPane = new JScrollPane(equipTable);
        scrollPane.setPreferredSize(new Dimension(530, 500));

        System.out.println("aabot ata dito");
        if (show){
            System.out.println(" pero dito hindi umabot");
            equipTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
            equipTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
            equipTable.getColumnModel().getColumn(3).setCellEditor(this.editor = new ButtonEditor(new JCheckBox()));
            equipTable.getColumnModel().getColumn(0).setPreferredWidth(140);
            equipTable.getColumnModel().getColumn(1).setPreferredWidth(160);
            equipTable.getColumnModel().getColumn(2).setPreferredWidth(90);
            equipTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        centerPanel.add(scrollPane, BorderLayout.WEST);

        //============================
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        JScrollPane receiptScrollPane = new JScrollPane(receiptArea);
        receiptScrollPane.setPreferredSize(new Dimension(300, 500));
        centerPanel.add(receiptScrollPane, BorderLayout.EAST);
        //===========================

        // Ensure UI updates properly
        equipTable.revalidate();
        equipTable.repaint();
        SwingUtilities.invokeLater(() -> {
            equipTable.revalidate();
            equipTable.repaint();
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        setVisible(false);
    }


//===================================================================================================================
public void populateTableList(ArrayList<Equipment> equipmentList) {
        equipmentList.forEach(e-> System.out.println(e.getName()));
//    equipmentList.add(new Equipment(true, new AtomicInteger(4), "Drone", "E001", "Drones"));
//    equipmentList.add(new Equipment(true, new AtomicInteger(5), "Camera", "E002", "Cameras"));
//    equipmentList.add(new Equipment(false, new AtomicInteger(3), "Stabilizer", "E003", "Accessories"));
//    equipmentList.add(new Equipment(true, new AtomicInteger(4), "Switch", "E004", "Electronics"));

    centerPanel.setLayout(new BorderLayout());

    String[] columnNames = new String[]{"Equipment Name", "Equipment Type", "Quantity", "Avail"};
    Object[][] data = new Object[equipmentList.size()][4];

    for (int i = 0; i < equipmentList.size(); i++) {
        Equipment equipment = equipmentList.get(i);

        String equipmentName = equipment.getName();
        String type  = equipment.getType();
        String quantity = String.valueOf(equipment.getQuantity());
        String availability = "add";

        data[i] = new Object[]{equipmentName,type, quantity, availability};
    }
    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };
    equipTable = new JTable(model);
    equipTable.setRowHeight(50);
    equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    equipTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
    equipTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
    equipTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));

    equipTable.getColumnModel().getColumn(0).setPreferredWidth(140);
    equipTable.getColumnModel().getColumn(1).setPreferredWidth(160);
    equipTable.getColumnModel().getColumn(2).setPreferredWidth(90);
    equipTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    JScrollPane scrollPane = new JScrollPane(equipTable);
    scrollPane.setPreferredSize(new Dimension(530, 500));

    centerPanel.add(scrollPane, BorderLayout.WEST);

    receiptArea = new JTextArea();
    receiptArea.setEditable(false);
    JScrollPane receiptScrollPane = new JScrollPane(receiptArea);
    receiptScrollPane.setPreferredSize(new Dimension(300, 500));
    centerPanel.add(receiptScrollPane, BorderLayout.EAST);

    SwingUtilities.invokeLater(() -> {
        equipTable.revalidate();
        equipTable.repaint();
        centerPanel.revalidate();
        centerPanel.repaint();
    });

    setVisible(true);
}

private void searchAndUpdateTable() {
    String searchTerm = searchField.getText().trim().toLowerCase();
    DefaultTableModel model = (DefaultTableModel) equipTable.getModel();
    model.setRowCount(0);

    Object[][] data;
    if (isClicked) {
        data = new Object[][]{
                {new ImageIcon("./src/gui/drone.jpg"), "Drone", "4", "add"},
                {new ImageIcon("./src/gui/camera.png"), "Camera", "5", "add"},
                {3, "Stabilizer", "3", "add"},
                {4, "Switch", "4", "add"}
        };
    } else {
        data = new Object[][]{
                {"Drone", "12-01-2025", "Returned"},
                {"Switch", "02-27-2026", "In Progress"}
        };
    }


    for (Object[] row : data) {
        for (Object cell : row) {
            if (cell != null && cell.toString().toLowerCase().contains(searchTerm)) {
                model.addRow(row);
                break;
            }
        }
    }
}


//=============================================================================================
    class ImageRender extends DefaultTableCellRenderer {
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (value instanceof ImageIcon) {
                            ImageIcon imageIcon = (ImageIcon) value;
                            Image image = imageIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(image));
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    //============================================================================================================

    // Custom Button Renderer (Displays buttons in the table)
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setPreferredSize(new Dimension(50, 10));
            setLayout(new GridBagLayout());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Borrowed" : value.toString());
            return this;
        }
    }

    //================================================================================================================
    // Custom Button Editor (Handles button clicks)
    class ButtonEditor extends DefaultCellEditor {
        private JButton borrowButton;
        private String label;
        private boolean clicked;
        private JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            borrowButton = new JButton();
            borrowButton.setOpaque(true);
            borrowButton.setPreferredSize(new Dimension(50, 10));
            borrowButton.setLayout(new GridBagLayout());
            receiptArea.setText("===== Receipt =====\n");

            // Handle button click event
            borrowButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clicked = true;
                    fireEditingStopped();
                    getCellEditorValue();
                    System.out.println("Clicked");
                }
            });
        }

        public Object getCellEditorValue() {
            System.out.println("HELLO");

            int selectedRow = equipTable.getSelectedRow();
            if (selectedRow != -1) {
                String equipmentName = equipTable.getValueAt(selectedRow, 1).toString();
                String quantity = equipTable.getValueAt(selectedRow, 2).toString();

                // Update the receiptArea with the equipment and quantity

                String receiptText = "";
                receiptText += "Equipment: " + equipmentName + "\n";
                receiptText += "Quantity: " + quantity + "\n";
                receiptText += "==================\n";


                receiptArea.append(receiptText);

                System.out.println("Receipt updated: " + receiptText); // Debug statement
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
        }
    }




