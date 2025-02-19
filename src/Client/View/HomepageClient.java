package Client.View;

import Common.Model.Transaction;
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
    private JComboBox day;
    private String month;
    private String startTime;
    private String endTime;
    homePageAdmin homePageAdmin;
    private JTextArea receiptArea;
    private boolean showingEquipList = true;
    private boolean showingBorrowed = false;
    private boolean showingSettings = false;
    private boolean isClicked = false;
    private boolean show;
    ButtonEditor editor;
    String itemtoBorrowed;
    JButton borrowButton;
    String dayString;

    public String getStartTime() {
        return startTime;
    }

    public String getMonth() {
        return month;
    }

    public String getEndTime() {
        return endTime;
    }

    public HomepageClient() {
        setContentPane(mainPanel);
        setTitle("Client Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        borrowButton = new JButton("Borrow");
        borrowButton.setVisible(false);

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        JScrollPane receiptScrollPane = new JScrollPane(receiptArea);
        receiptScrollPane.setPreferredSize(new Dimension(400, 500));
        centerPanel.add(receiptScrollPane, BorderLayout.EAST);

        homePageAdmin = new homePageAdmin();
        homePageAdmin.setupHoverEffect(equipLabel);
        homePageAdmin.setupHoverEffect(borrowedItemlbl);
        homePageAdmin.setupHoverEffect(equipment);
        homePageAdmin.setVisible(false);

//        populateTable();

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
            }
        });

        borrowedItemlbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showingBorrowed = true;
            }
        });

        equipment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showingSettings = true;
            }
        });
    }

    //=============================================================================================
    private void filterTableByType(String type) {
        String selectedType = types.getSelectedItem().toString().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) equipTable.getModel();
        model.setRowCount(0);

        Object[][] data = new Object[][] {
                {new ImageIcon("./res/Images/drone.jpg"), "Drones", "4", "add"},
                {new ImageIcon("./res/Images/camera.png"), "Camera", "5", "add"},
                {3, "Stabelizer", "3", "add"},
                {4, "Swtiches", "4", "add"}
        };

        for (Object[] row : data) {
            if (row[1].toString().equalsIgnoreCase(selectedType)) {
                model.addRow(row);
            }
        }
    }


public void populateTableList(ArrayList<Equipment> equipmentList) {
    clearTable();

    centerPanel.removeAll();
    centerPanel.setLayout(new BorderLayout());

    JPanel datePanel = new JPanel();

    datePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 180, 5));

    datePanel.add(borrowButton);
    centerPanel.add(datePanel, BorderLayout.NORTH);
    borrowButton.setVisible(true);

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
    receiptScrollPane.setPreferredSize(new Dimension(430, 500));
    centerPanel.add(receiptScrollPane, BorderLayout.EAST);
        centerPanel.add(receiptScrollPane, BorderLayout.EAST);


    SwingUtilities.invokeLater(() -> {
        equipTable.revalidate();
        equipTable.repaint();
        centerPanel.revalidate();
        centerPanel.repaint();
    });

    setVisible(true);
}

    public void populateTableList2 (ArrayList<Transaction> transactionList) {
        clearTable();
        centerPanel.removeAll();
        centerPanel.setLayout(new BorderLayout());

        JPanel datePanel = new JPanel();
        centerPanel.add(datePanel, BorderLayout.NORTH);
        datePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 250, 5));

        if (borrowButton != null) {
            borrowButton.setVisible(false);
        }

        String[] columnNames = new String[]{"Equipment Name", "Borrowed Time", "Status"};
        Object[][] data = new Object[transactionList.size()][3];

        for (int i = 0; i < transactionList.size(); i++) {
            Transaction transaction = transactionList.get(i);
            data[i] = new Object[]{transaction.getEquipmentName(), transaction.getTime(), "In-Progress"};
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

        JScrollPane scrollPane = new JScrollPane(equipTable);
        scrollPane.setPreferredSize(new Dimension(530, 500));

        centerPanel.add(scrollPane, BorderLayout.WEST);

        receiptArea.setText("");  // Clear receipt when switching views

        equipTable.revalidate();
        equipTable.repaint();
        centerPanel.revalidate();
        centerPanel.repaint();

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
                {new ImageIcon("./res/Images/drone.jpg"), "Drone", "4", "add"},
                {new ImageIcon("./res/Images/camera.png"), "Camera", "5", "add"},
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

    public JButton getBorrowButton() {
        return borrowButton;
    }

    public String getItemToBeBorrowed() {
        return itemtoBorrowed;
    }

    public JLabel getEquipmenmtlabel() {
        return equipLabel;

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
    public class ButtonEditor extends DefaultCellEditor {
        public JButton borrowButton;
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
                String equipmentName = equipTable.getValueAt(selectedRow, 0).toString();
                itemtoBorrowed = equipmentName;
                String quantity = equipTable.getValueAt(selectedRow, 2).toString();
                startTime = (String) startTimeBox.getSelectedItem();
                endTime = (String) endTimeBox.getSelectedItem();
                dayString = (String) day.getSelectedItem();
                month = (String) monthBox.getSelectedItem();


                // Update the receiptArea with the equipment and quantity

                String receiptText = "";
                receiptText += "Equipment: " + equipmentName + "\n";
                receiptText += "Quantity: " + quantity + "\n";
                receiptText += "Start Time: " + startTime + "\n";
                receiptText += "End Time: " + endTime + "\n";
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
    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void clearTable() {
        if (equipTable != null) {
            DefaultTableModel model = (DefaultTableModel) equipTable.getModel();
            model.setRowCount(0);
            equipTable.revalidate();
            equipTable.repaint();
        }
    }


    public JLabel getBorrowedItemlbl() {
        return borrowedItemlbl;
    }

    public String getDayString() {
        return dayString;
    }
    public void showNoEquipment(){
        JOptionPane.showMessageDialog(null, "No Equipment left", "Warning", 2);


    }
    public void showSuccess(){
        JOptionPane.showMessageDialog(null,
                "Successfully reserved", "Warning", 1);

    }
}




