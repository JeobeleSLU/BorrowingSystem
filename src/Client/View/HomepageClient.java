package Client.View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    homePageAdmin homePageAdmin;



    public HomepageClient() {
        setContentPane(mainPanel);
        setTitle("Client Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        homePageAdmin = new homePageAdmin();
        homePageAdmin.setupHoverEffect(borrowedItemlbl);
        homePageAdmin.setupHoverEffect(equipment);
        homePageAdmin.setVisible(false);



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
    }

    //=============================================================================================
    public void populateTable() {
        // Set BorderLayout for centerPanel
        centerPanel.setLayout(new BorderLayout());

        // Define table columns
        String[] columnNames = {"Image", "Equipment Name", "Quantity", "Avail"};


        ImageIcon droneIcon = new ImageIcon("./res/Images/drone.jpg");
        // Sample Data to populate the table
        Object[][] data = {
                {droneIcon, "Drone", "4", "add"},
                {2, "Camera", "5", "add"},
                {3, "Stabilizer", "3", "add"},
                {4, "Switch", "4", "add"}
        };


        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        equipTable = new JTable(model);
        equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Wrap JTable in JScrollPane
        JScrollPane scrollPane = new JScrollPane(equipTable);

        scrollPane.setPreferredSize(new Dimension(400, 500));


        // Set column widths
       // equipTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        equipTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        equipTable.getColumnModel().getColumn(2).setPreferredWidth(80);
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

        // Set custom renderer and editor for button column
        equipTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        equipTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));
        equipTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
        setVisible(true);
    }
//===================================================================================================================
    class ImageRender extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String photoName = value.toString();
            ImageIcon droneIcon = new ImageIcon(new ImageIcon("./src/gui/drone.jpg" + photoName).getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT));
          return new JLabel(droneIcon);
        }
    }
    //============================================================================================================

    // Custom Button Renderer (Displays buttons in the table)
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
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
        private JButton button;
        private String label;
        private boolean clicked;
        private JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            // Handle button click event
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

//        @Override
//        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//            this.table = table;
//            this.row = row;
//            label = (value == null) ? "Borrow" : value.toString();
//            button.setText(label);
//            clicked = true;
//            return button;
//        }

        @Override
        public Object getCellEditorValue() {
            JTextArea receiptArea = null;
            if (clicked) {
                //TODO: when the button add is clicked it will get the fields and print it in the receipt panel
                receiptArea = new JTextArea();
                receiptArea.setEditable(false);
                receiptArea.setLineWrap(true);
                receiptArea.setWrapStyleWord(true);
                receiptArea.setLocation(500, 500);
                //   receiptArea.setText(receiptModel.get(row));
                receiptArea.setCaretPosition(0);

            }
            clicked = false;
            //    return "Add";
            return receiptArea;
        }


//    @Override
//        public boolean stopCellEditing() {
//            clicked = false;
//            return super.stopCellEditing();
//        }
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
    }

