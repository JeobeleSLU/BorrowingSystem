    package Client.View;

    import Common.Model.Transaction;

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

       public void populateTableTransaction(ArrayList<Transaction> transactionList) {
            centerPanel.setLayout(new BorderLayout());

            String[] columnNames;
            Object[][] data;

            if (showingHistory) {
                showingLogs = false;
                showingEquipment = false;
                columnNames = new String[]{"StudentId", "Equipment Name", "Date Borrowed", "Status"};
                data = new Object[transactionList.size()][3];

                for (int i = 0; i < transactionList.size(); i++) {
                    Transaction transaction = transactionList.get(i);

                    String studId = transaction.getUserId();
                    String equipmentName = transaction.getEquipmentName();
                    String dateBorrowed = transaction.getTime();
                    String status = "Returned";

                    data[i] = new Object[]{studId, equipmentName, dateBorrowed, status};
                }
                showingHistory = false;
                anotherBool = false;
            } else if (showingLogs) {
                showingHistory = false;
                showingEquipment = false;
                columnNames = new String[]{"StudentId", "Equipment Name", "Date", "Time", "Status"};
                data = new Object[transactionList.size()][4];

                for (int i = 0; i < transactionList.size(); i++) {
                    Transaction transaction = transactionList.get(i);

                    String studId = transaction.getUserId();
                    String equipmentName = transaction.getEquipmentName();
                    String time = transaction.getTime();
                    String status = "Returned";

                    data[i] = new Object[]{studId, equipmentName, time, status};
                }
                showingLogs = false;
                anotherBool = false;
            } else if (showingEquipment) {
                showingHistory = false;
                showingLogs = false;
                columnNames = new String[]{"Equipment ID", "Equipment Name", "Flag"};
                data = new Object[transactionList.size()][2];
                columnNames = new String[]{"Equipment ID", "Equipment Name", "Action"};

                data = new Object[][]{
                        {"012345", "Drone", null},
                        {"012346", "Camera", null},
                        {"012347", "Switch", null}
                };

                for (int i = 0; i < transactionList.size(); i++) {
                    Transaction transaction = transactionList.get(i);

                    String equipId = transaction.getEquipmentId();
                    String equipmentName = transaction.getEquipmentName();
                    String status = "Returned";

                    data[i] = new Object[]{equipId, equipmentName, status};
                }
                showingEquipment = false;
                showingEquipment = true;
                anotherBool = true;
            } else {
                columnNames = new String[]{};
                data = new Object[][]{};
            }

            if (columnNames.length == 0 || data.length == 0) {
                System.out.println("No data to display.");
                return;
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 5;
//                    return showingEquipment && column == 2; // Only make Action column editable for Equipment
                }

                @Override
                public Class<?> getColumnClass(int column) {
                    // Ensure that the Action column uses JButton class
                    if (showingEquipment && column == 2) {
                        return JButton.class;
                    } else {
                        return String.class;
                    }
                }
            };

            //====================
            if (add && anotherBool) {
                System.out.println("ADD");
//            Client.View.addItem addItem1 = new addItem();
                String[] row = addItemBtn.row;
                System.out.println(Arrays.toString(row));
                model.addRow(row);
//            equipTable.repaint();
//            centerPanel.repaint();
//            mainPanel.repaint();

            equipTable = new JTable(model);
            equipTable.setRowHeight(50);
            equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            // Set ButtonRenderer and ButtonEditor only for showingEquipment
            if (showingEquipment) {
//                equipTable.getColumnModel().getColumn(2).setCellRenderer(new HomepageClient.ButtonRenderer());
//                equipTable.getColumnModel().getColumn(2).setCellEditor(new HomepageClient.ButtonEditor(new JCheckBox(), equipTable));
            }

            JScrollPane scrollPane = new JScrollPane(equipTable);
            scrollPane.setPreferredSize(new Dimension(500, 500));

            equipTable.getColumnModel().getColumn(0).setPreferredWidth(70);
            equipTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            if (showingEquipment) {
                equipTable.getColumnModel().getColumn(2).setPreferredWidth(120);
            }

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


            //======================================================================================================
            // Custom Button Renderer (Displays "Remove" button in the table)
            class ButtonRenderer extends JButton implements TableCellRenderer {
                public ButtonRenderer() {
                    setOpaque(true);
                    setText("Remove"); // Set text to "Remove"
                }

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    return this;
                }
            }

            // Custom Button Editor (Handles "Remove" button clicks)
            class ButtonEditor extends DefaultCellEditor {
                private JButton button;
                private boolean clicked;
                private JTable table;
                private int row;

                public ButtonEditor(JCheckBox checkBox, JTable table) {
                    super(checkBox);
                    this.table = table;
                    button = new JButton("Remove");
                    button.setOpaque(true);

                    // Handle button click event
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            clicked = true;
                            fireEditingStopped(); // Stop editing when button is clicked

                            // Remove the selected row
                            if (table.getModel() instanceof DefaultTableModel) {
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                model.removeRow(row);
                            }

                        }
                    });
                }


                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    this.row = row; // Store the row index for removal
                    return button;
                }

                @Override
                public Object getCellEditorValue() {
                    return "Remove";
                }
            }
        }

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
