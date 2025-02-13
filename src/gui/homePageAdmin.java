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
                populateTable();
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

        // Define table columns
        String[] columnNames = {"StudentId", "Equipment Name", "Date", "Time", "Status"};

        // Sample Data to populate the table
        Object[][] data = {
                //{getStudId, getEquipName, getDate, getTime, getStatus}
                {"2242815", "Drone", "02-14-25", "1:00-2:00" ,"In progress"}, //will edit status
                {"2240696", "Camera", "02-14-25", "1:00-2:00" ,"Returned"}, //will edit status
                {"2241615", "Switch", "02-14-25", "1:00-2:00" ,"Not claim"}, //will edit status
                {"2241122", "Router", "02-14-25", "1:00-2:00" ,"In progress"}, //will edit status
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        equipTable = new JTable(model);
        equipTable.setRowHeight(50);
        equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Wrap JTable in JScrollPane
        JScrollPane scrollPane = new JScrollPane(equipTable);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        // Set column widths
        equipTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        equipTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        equipTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        equipTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        equipTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        centerPanel.add(scrollPane, BorderLayout.WEST);

        // Ensure UI updates properly
        SwingUtilities.invokeLater(() -> {
            equipTable.revalidate();
            equipTable.repaint();
            centerPanel.revalidate();
            centerPanel.repaint();
        });
       // equipTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
      //  equipTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));
        //equipTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

        setVisible(true);
    }
//===================================================================================================================
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setPreferredSize(new Dimension(50, 10));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value == null) {
                setText("Add");
            } else {
                setText(value.toString());
            }
            return this;
        }
    }

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

            // Handle button click event
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clicked = true;
                    fireEditingStopped();
                    getCellEditorValue();
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                // You can perform any action here like updating or adding new equipment
                // For example, show a message when the button is clicked
                JOptionPane.showMessageDialog(button, "Add button clicked for row: " + row);
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

        // Set the row index where the button was clicked
        public void setRow(int row) {
            this.row = row;
        }
    }
}
