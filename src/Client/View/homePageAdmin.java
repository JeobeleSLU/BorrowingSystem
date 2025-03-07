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
        private String itemToRemove;

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
         //   setupHoverEffect(logs);
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
        public void populateEquipmentTable(ArrayList<Equipment> equipmentList, ActionListener removeActionListener) {
            clearTable();
            centerPanel.setLayout(new BorderLayout());

            String[] columnNames = {"Equipment ID", "Equipment Name", "Type", "Remaining", "Action"};
            Object[][] data = new Object[equipmentList.size()][5];

            for (int i = 0; i < equipmentList.size(); i++) {
                Equipment equipment = equipmentList.get(i);
                data[i][0] = equipment.getId();
                data[i][1] = equipment.getName();
                data[i][2] = equipment.getType();
                data[i][3] = equipment.getQuantity();
                data[i][4] = "Remove"; // Placeholder for button
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 4; // Only the "Action" column is editable
                }
            };

            equipTable = new JTable(model);
            equipTable.setRowHeight(40);
            equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            equipTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            equipTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            equipTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            equipTable.getColumnModel().getColumn(3).setPreferredWidth(150);
            equipTable.getColumnModel().getColumn(4).setPreferredWidth(100);

            equipTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
            equipTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), equipTable, equipmentList, removeActionListener, null));

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
        class ButtonEditor extends DefaultCellEditor {
            private JButton button;
            private JTable table;
            private ArrayList<?> list; // Use generic type to handle both Equipment and Transaction
            private int row;
            private ActionListener removeActionListener;
            private ActionListener returnActionListener;
            private String action;

            public ButtonEditor(JCheckBox checkBox, JTable table, ArrayList<?> list, ActionListener removeActionListener, ActionListener returnActionListener) {
                super(checkBox);
                this.table = table;
                this.list = list;
                this.removeActionListener = removeActionListener;
                this.returnActionListener = returnActionListener;

                button = new JButton();
                button.setOpaque(true);

                button.addActionListener(e -> {
                    fireEditingStopped(); // Stop editing when button is clicked
                    if (row >= 0 && row < list.size()) {
                        if ("Remove".equals(action) && removeActionListener != null) {
                            String equipmentName = table.getValueAt(row, 1).toString();
                            list.remove(row);
                            ((DefaultTableModel) table.getModel()).removeRow(row);
                            removeActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, equipmentName));
                        } else if ("Return".equals(action) && returnActionListener != null) {
                            String equipmentName = table.getValueAt(row, 0).toString(); // Adjust column index as needed
                            // Update status or perform return logic here if needed
                            returnActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, equipmentName));
                        }
                    }
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                this.row = row;
                this.action = value.toString(); // "Remove" or "Return"
                button.setText(action);
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                return action;
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
            //System.out.println("Showing logs: " + showing);
            System.out.println("Showing history: " + showingHistory);
            System.out.println("Showing equipment: " + showingEquipment);

        }
        public void populateTableList2(ArrayList<Transaction> transactionList, ActionListener returnActionListener) {
            clearTable();
            centerPanel.removeAll();
            centerPanel.setLayout(new BorderLayout());

            JPanel datePanel = new JPanel();
            centerPanel.add(datePanel, BorderLayout.NORTH);
            datePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 250, 5));

            String[] columnNames = new String[]{"Equipment Name", "Date Borrowed", "Time Borrowed", "Status", "Return"};
            Object[][] data = new Object[transactionList.size()][5];

            for (int i = 0; i < transactionList.size(); i++) {
                Transaction transaction = transactionList.get(i);
                data[i] = new Object[]{transaction.getEquipmentName(), transaction.getDate(), transaction.getTime(), "In-Progress", "Return"};
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 4; // Only the "Return" column is editable
                }
            };

            equipTable = new JTable(model);
            equipTable.setRowHeight(50);
            equipTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            // Set column widths
            equipTable.getColumnModel().getColumn(0).setPreferredWidth(140);
            equipTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            equipTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            equipTable.getColumnModel().getColumn(3).setPreferredWidth(90);
            equipTable.getColumnModel().getColumn(4).setPreferredWidth(90);

            // Add button renderer and editor for "Return" column
            equipTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
            equipTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), equipTable, transactionList, null, returnActionListener));

            JScrollPane scrollPane = new JScrollPane(equipTable);
            scrollPane.setPreferredSize(new Dimension(600, 500));

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

        public String getItemToRemove() {
            return itemToRemove;
        }
        public void showInvalidEnter(){
            JOptionPane.showMessageDialog(null,
                    "Can't add the equipment to server please contact Jesus ", "Invalid insertion", 2);
            this.dispose();

        }
        public void showValid(String itemToRemove){
            JOptionPane.showMessageDialog(null,
                    "Successfully Removed: " + itemToRemove, "Removed To server", 1);
         //   this.dispose();
        }
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

    }
