package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientHomePage extends JFrame {
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JPanel dashboard;
    private JLabel dashboardTxt;
    private JLabel borrowedItemlbl;
    private JLabel equipment;
    private JPanel centerPanel;
    private JTable equipTable;
    private JPanel receiptPanel;
    private JTextArea borrowListTF;
    homePageAdmin homePageAdmin;

    private DefaultListModel<String> receiptModel;
    private JList<String> receiptList;


    public ClientHomePage() {
        setContentPane(mainPanel);
        setTitle("Client Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        homePageAdmin = new homePageAdmin();
        homePageAdmin.setupHoverEffect(borrowedItemlbl);
        homePageAdmin.setupHoverEffect(equipment);
        populateTable();

    }
//=============================================================================================
    public void populateTable() {
        // Set BorderLayout for centerPanel
        centerPanel.setLayout(new BorderLayout());

        // Define table columns
        String[] columnNames = {"Image", "Equipment Name", "Quantity", "Avail"};

        // Sample Data to populate the table
        Object[][] data = {
                {1, "Drone", "4", "add"},
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
        equipTable.getColumnModel().getColumn(0).setPreferredWidth(50);
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
        setVisible(true);
    }

 //============================================================================================================

    // Custom Button Renderer (Displays buttons in the table)
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Borrow" : value.toString());
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
            if (clicked) {
                //TODO: when the button add is clicked it will get the fields and print it in the receipt panel
            }
            clicked = false;
            return "Add";
        }


//    @Override
//        public boolean stopCellEditing() {
//            clicked = false;
//            return super.stopCellEditing();
//        }
    }

}
