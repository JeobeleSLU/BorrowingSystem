package Client.View;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

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
    homePageAdmin homePageAdmin;
    private JTextArea receiptArea;
    private boolean showingEquipList = true;
    private boolean showingBorrowed = false;
    private boolean showingSettings = false;


    public HomepageClient() {
        setContentPane(mainPanel);
        setTitle("Client Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        homePageAdmin = new homePageAdmin();
        homePageAdmin.setupHoverEffect(equipLabel);
        homePageAdmin.setupHoverEffect(borrowedItemlbl);
        homePageAdmin.setupHoverEffect(equipment);

        populateTable();

        types.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = types.getSelectedItem().toString();


            }
        });
        setVisible(true);

        //SEARCH FIELD FOR SEARCHING
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
    public void populateTable() {
        //centerPanel.removeAll();
        // Set BorderLayout for centerPanel
        centerPanel.setLayout(new BorderLayout());
        // Define table columns
        String[] columnNames = new String[0];
        Object [][] data = new Object[0][];
        
        //TODO: Connect it to admin homepage
        ImageIcon droneIcon = new ImageIcon("./src/gui/drone.jpg");
        ImageIcon cameraIcon = new ImageIcon("./src/gui/camera.png");

        //User will use this table for borrowing the equipment
        if (showingEquipList) {
            showingBorrowed = false;
            showingSettings = false;
            columnNames = new String[]{"Image", "Equipment Name", "Quantity", "Avail"};
            data = new Object[][] {
                    {droneIcon, "Drone", "4", "add"},
                    {cameraIcon, "Camera", "5", "add"},
                    {3, "Stabilizer", "3", "add"},
                    {4, "Switch", "4", "add"}


            };
            showingEquipList = false;
            // Shows the user the borrowed item
        } else if (showingBorrowed) {
            showingEquipList = false;
            showingSettings = false;
            
            columnNames = new String[] {"Equipment Name", "Borrowed Date", "Status"};
            data = new Object[][] {
                    {"Drone", "12-01-2025", "Returned"},
                    {"Switch", "02-27-2026", "In Progress"}
            };
            showingBorrowed = false;
            //Profile Settings
        } else if (showingSettings) {
            showingEquipList = false;
            showingBorrowed = false;
            
        } else {
            columnNames = new String[]{};
            data = new Object[][]{};
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


        // Wrap JTable in JScrollPane
        JScrollPane scrollPane = new JScrollPane(equipTable);
        scrollPane.setPreferredSize(new Dimension(500, 500));


        if (showingEquipList){
            System.out.println("umabot");
            equipTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
            equipTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
            equipTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));
            equipTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

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
        receiptScrollPane.setPreferredSize(new Dimension(330, 500));
        centerPanel.add(receiptScrollPane, BorderLayout.EAST);
        //===========================

        // Ensure UI updates properly
        SwingUtilities.invokeLater(() -> {
            equipTable.revalidate();
            equipTable.repaint();
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        setVisible(true);
    }
//===================================================================================================================

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
            System.out.println("asdadasdad");
            return this;
        }
    }

    //================================================================================================================
    // Custom Button Editor (Handles button clicks)
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setPreferredSize(new Dimension(50, 10));
            button.setLayout(new GridBagLayout());
            receiptArea.setText("===== Receipt =====\n");

            // Handle button click event
            button.addActionListener(new ActionListener() {
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
//            if (clicked) {
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
//=============================================================================================
//   private void creatDropDown() {
// Initialize the JComboBox with equipment types
//        String[] typesArray = {"Camera", "Drones", "Stabilizer", "Switches", "Routers"};


//        types = new JComboBox<>(typesArray);
//
//        // Add action listener to the JComboBox
//        types.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // When a new type is selected, dynamically create a dropdown
//                String selectedType = (String) types.getSelectedItem();
//                createDropdownForSelectedType(selectedType);
//            }
//        });
//
//        // Add the types JComboBox to the mainPanel (top of the layout)
//        // mainPanel is using GridLayout, so it will be added in the first cell (0,0).
//        mainPanel.add(types); // No need to specify BorderLayout, just add it to the mainPanel.
//
//        // Update the layout and repaint
//        mainPanel.revalidate();
//        mainPanel.repaint();
//    }

//    public void createDropdownForSelectedType(String selectedType) {
//        // Remove the existing dropdown (if any) before creating a new one
//        if (centerPanel.getComponentCount() > 0) {
//            centerPanel.removeAll(); // Clear the center panel
//        }
//
//        // Create a new JComboBox based on the selected type
//        String[] options;
//        switch (selectedType) {
//            case "Camera":
//                options = new String[]{"Canon", "Nikon", "Sony"};
//                break;
//            case "Drones":
//                options = new String[]{"DJI", "Parrot", "Yuneec"};
//                break;
//            case "Stabilizer":
//                options = new String[]{"GoPro", "DJI", "Zhiyun"};
//                break;
//            case "Switches":
//                options = new String[]{"TP-Link", "Cisco", "Netgear"};
//                break;
//            case "Routers":
//                options = new String[]{"TP-Link", "Netgear", "Asus"};
//                break;
//            default:
//                options = new String[]{};
//        }
//
//        JComboBox<String> newDropdown = new JComboBox<>(options);
//
//        // Add newDropdown to centerPanel (center position of BorderLayout)
//        centerPanel.setLayout(new BorderLayout());  // Ensure BorderLayout is in use
//        centerPanel.add(newDropdown, BorderLayout.CENTER);
//
//        // Revalidate and repaint the panel to make the dropdown visible
//        centerPanel.revalidate();
//        centerPanel.repaint();
//    }

