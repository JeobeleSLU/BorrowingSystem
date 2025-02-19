    package Client.View;

    import Common.Model.Transaction;
    import Server.Model.Equipment;

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
    import java.util.ArrayList;
    import java.util.Arrays;
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

        public JLabel getAddItem() {
            return addItem;
        }



        private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
        private final String stamp = LocalDateTime.now().format(dateTimeFormatter);
        public boolean add;
        public boolean anotherBool;
        Client.View.addItem addItemBtn;

        public homePageAdmin() {
            setContentPane(mainPanel);
            setTitle("Admin homepage");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 700);
            setLocationRelativeTo(null);
            setVisible(true);
            setResizable(false);


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
                    addItemBtn = new addItem();
                    //   dispose();
                    add = true;


                }
            });

            //LOGS - borrowed equipment list
            logs.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    showingLogs = true;
                }
            });

            //All finished transactions

            //EQUIPMENT LIST
            equipment.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    showingEquipment = true;
                }
            });
        }
        public void populateEquipmentTable(ArrayList<Equipment> equipmentList) {
            clearTable();
            centerPanel.setLayout(new BorderLayout());

            // Column headers
            String[] columnNames = {"Equipment ID", "Equipment Name", "Action"};

            // Data array
            Object[][] data = new Object[equipmentList.size()][3];

            for (int i = 0; i < equipmentList.size(); i++) {
                Equipment equipment = equipmentList.get(i);
                data[i][0] = equipment.getId();
                data[i][1] = equipment.getName();
                data[i][2] = "Remove"; // Placeholder for button
            }

            // Table model
            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 2; // Only the "Action" column is editable
                }
            };

            equipTable = new JTable(model);
            equipTable.setRowHeight(40);
            equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            // Set column widths
            equipTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            equipTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            equipTable.getColumnModel().getColumn(2).setPreferredWidth(100);

            // Add button functionality
            equipTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
            equipTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), equipTable, equipmentList));

            JScrollPane scrollPane = new JScrollPane(equipTable);
            scrollPane.setPreferredSize(new Dimension(500, 500));

            centerPanel.removeAll();
            centerPanel.add(scrollPane, BorderLayout.CENTER);

            SwingUtilities.invokeLater(() -> {
                equipTable.revalidate();
                equipTable.repaint();
                centerPanel.revalidate();
                centerPanel.repaint();
                mainPanel.revalidate();
                mainPanel.repaint();
            });
        }

        // Custom Button Renderer
        class ButtonRenderer extends JButton implements TableCellRenderer {
            public ButtonRenderer() {
                setOpaque(true);
                setText("Remove");
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return this;
            }
        }

        // Custom Button Editor (Handles Remove action)
        class ButtonEditor extends DefaultCellEditor {
            private JButton button;
            private JTable table;
            private ArrayList<Equipment> equipmentList;
            private int row;

            public ButtonEditor(JCheckBox checkBox, JTable table, ArrayList<Equipment> equipmentList) {
                super(checkBox);
                this.table = table;
                this.equipmentList = equipmentList;

                button = new JButton("Remove");
                button.setOpaque(true);

                button.addActionListener(e -> {
                    fireEditingStopped(); // Stop editing when button is clicked

                    if (row >= 0 && row < equipmentList.size()) {
                        equipmentList.remove(row); // Remove from list
                        ((DefaultTableModel) table.getModel()).removeRow(row); // Remove from table
                    }
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                this.row = row;
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                return "Remove";
            }
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
        public void populateTableList2 (ArrayList<Transaction> transactionList) {
            clearTable();
            centerPanel.removeAll();
            centerPanel.setLayout(new BorderLayout());

            JPanel datePanel = new JPanel();
            centerPanel.add(datePanel, BorderLayout.NORTH);
            datePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 250, 5));

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

        public void clearTable() {
            if (equipTable != null) {
                DefaultTableModel model = (DefaultTableModel) equipTable.getModel();
                model.setRowCount(0);
            }

            centerPanel.removeAll();
            centerPanel.revalidate();
            centerPanel.repaint();
        }

        public JLabel getHistory() {
            return history;
        }

        public JLabel getEquipment() {
            return equipment;
        }


    }
