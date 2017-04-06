package com.cruiz90.presidencia.views;

import com.cruiz90.presidencia.models.Movement;
import com.cruiz90.presidencia.utils.DateLabelFormatter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class MainView extends JFrame {

    private JDatePickerImpl datePickerFrom, datePickerTo;
    private DefaultTableModel tableModel;
    private JTable movementsTable;
    private MainView parentFrame;
    private JRadioButton allMovementsOption, periodMovementsOption, incomeMovementsOption, expenseMovementsOption;
    private int hashMouseListener;

    public MainView() {
        initComponents();
    }

    private void initComponents() {
        parentFrame = this;
        setTitle("Control de Ingresos y Egresos");
        //size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        setSize(900, 700);
        //available size of the screen
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - taskBarSize - getHeight()) / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        URL iconURL = getClass().getResource("/com/cruiz90/presidencia/images/logo_huaniqueo.png");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());

        JLabel title = new JLabel("H. AYUNTAMIENTO DE HUANIQUEO 2015 - 2018");
        title.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setBounds(100, 35, 790, 40);
        add(title);

        JLabel imageLogo = new JLabel();
        imageLogo.setBounds(30, 10, 80, 103);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(iconURL).getImage().getScaledInstance(imageLogo.getWidth(), imageLogo.getHeight(), Image.SCALE_SMOOTH));
        imageLogo.setIcon(imageIcon);
        add(imageLogo);

        JButton manageUsers = new JButton();
        imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/com/cruiz90/presidencia/images/list.png")).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        manageUsers.setIcon(imageIcon);
        manageUsers.setToolTipText("Administrar usuarios");
        manageUsers.addActionListener(manageUsers());
        manageUsers.setBounds(120, 87, 26, 26);
        add(manageUsers);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(10, 120, 870, 10);
        add(separator);

        JButton addMovement = new JButton("Nuevo movimiento");
        addMovement.addActionListener(showMovememtForm());
        addMovement.setBounds(10, 130, 150, 30);
        add(addMovement);

        allMovementsOption = new JRadioButton("Todos");
        allMovementsOption.setSelected(true);
        allMovementsOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allMovementsOption.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        allMovementsOption.setBounds(240, 130, 70, 30);
        add(allMovementsOption);

        incomeMovementsOption = new JRadioButton("Ingresos");
        incomeMovementsOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        incomeMovementsOption.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        incomeMovementsOption.setBounds(310, 130, 80, 30);
        add(incomeMovementsOption);

        expenseMovementsOption = new JRadioButton("Egresos");
        expenseMovementsOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        expenseMovementsOption.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        expenseMovementsOption.setBounds(390, 130, 80, 30);
        add(expenseMovementsOption);

        periodMovementsOption = new JRadioButton("Periodo del");
        periodMovementsOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        periodMovementsOption.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        periodMovementsOption.setBounds(475, 130, 90, 30);
        periodMovementsOption.addItemListener(periodRadioChange());
        add(periodMovementsOption);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(allMovementsOption);
        radioGroup.add(periodMovementsOption);
        radioGroup.add(incomeMovementsOption);
        radioGroup.add(expenseMovementsOption);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Properties p = new Properties();
        InputStream fileProperties = null;
        try {
            fileProperties = new FileInputStream("src/com/cruiz90/presidencia/properties/Messages.properties");
            p.load(fileProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UtilDateModel modelFrom = new UtilDateModel();
        modelFrom.setDate(year, month, day);
        modelFrom.setSelected(true);
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, p);
        datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
        datePickerFrom.getComponent(1).setEnabled(false);
        datePickerFrom.setBounds(570, 130, 120, 30);
        add(datePickerFrom);
        JLabel to = new JLabel("al");
        to.setHorizontalAlignment(JLabel.CENTER);
        to.setBounds(690, 130, 20, 30);
        add(to);
        UtilDateModel modelTo = new UtilDateModel();
        modelTo.setDate(year, month, day);
        modelTo.setSelected(true);
        JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p);
        datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        datePickerTo.getComponent(1).setEnabled(false);
        datePickerTo.setBounds(710, 130, 120, 30);
        add(datePickerTo);

        JButton applyFilter = new JButton();
        applyFilter.addActionListener(applyFilters());
        imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/com/cruiz90/presidencia/images/find.png")).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        applyFilter.setToolTipText("Aplicar filtros");
        applyFilter.setIcon(imageIcon);
        applyFilter.setBounds(840, 130, 26, 26);
        add(applyFilter);

        // Panel de Movimientos
        JScrollPane scrollPane = new JScrollPane();
        movementsTable = new JTable();
        updateDataTable(Movement.findAll());

        scrollPane.setViewportView(movementsTable);
        scrollPane.setBounds(10, 170, 860, 480);
        add(scrollPane);
    }

    private ActionListener showMovememtForm() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MovementForm mf = new MovementForm(parentFrame, true);
                mf.setDataContainer(parentFrame);
                mf.setVisible(true);
            }
        };
    }

    private ActionListener applyFilters() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (allMovementsOption.isSelected()) {
                    updateDataTable(Movement.findAll());
                } else if (periodMovementsOption.isSelected()) {
                    updateDataTable(Movement.findByPeriod(datePickerFrom.getJFormattedTextField().getText(), datePickerTo.getJFormattedTextField().getText()));
                } else if (incomeMovementsOption.isSelected()) {
                    updateDataTable(Movement.findIncome());
                } else if (expenseMovementsOption.isSelected()) {
                    updateDataTable(Movement.findExpenditures());
                }
            }
        };
    }

    private ItemListener periodRadioChange() {
        return new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    datePickerFrom.getComponent(1).setEnabled(true);
                    datePickerTo.getComponent(1).setEnabled(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    datePickerFrom.getComponent(1).setEnabled(false);
                    datePickerTo.getComponent(1).setEnabled(false);
                }
            }

        };
    }

    public void updateDataTable(List<Movement> movements) {
        if (movements.isEmpty()) {
            ((DefaultTableModel) movementsTable.getModel()).setRowCount(0);
            JLabel label = new JLabel("Sin resultados");
            label.setSize(860, 30);
            label.setHorizontalAlignment(JLabel.CENTER);
            movementsTable.add(label);
            movementsTable.setFillsViewportHeight(true);
            return;
        } else {
            movementsTable.removeAll();
        }
        Object[][] movementsArray = new Object[movements.size()][6];
        int row = 0;
        DecimalFormat df = new DecimalFormat("$#,###,###,##0.00");
        for (Movement app : movements) {
            movementsArray[row][0] = app.getMovementId();
            movementsArray[row][1] = app.getConcept();
            movementsArray[row][2] = df.format(app.getAmount());
            movementsArray[row][3] = app.getDescription();
            movementsArray[row][4] = app.getType() == 1 ? "Ingreso" : "Egreso";
            movementsArray[row][5] = app.getDate().toString();
            row++;
        }

        tableModel = new DefaultTableModel(
                movementsArray,
                new String[]{
                    "Número", "Concepto", "Cantidad", "Descripción", "Tipo", "Fecha"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        movementsTable.setModel(tableModel);
        movementsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evnt) {
                if (evnt.getClickCount() == 2) {
                    if (movementsTable.getSelectedRow() > -1 && evnt.hashCode() != hashMouseListener) {
                        hashMouseListener = evnt.hashCode();
                        ActionForMovement dialog = new ActionForMovement(parentFrame, true, (Integer) movementsTable.getValueAt(movementsTable.getSelectedRow(), 0));
                        dialog.setDataContainer(parentFrame);
                        dialog.setVisible(true);
                    }
                }
            }
        });
        movementsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                if (row % 2 == 0) {
                    setBackground(Color.LIGHT_GRAY);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
    }

    private ActionListener manageUsers() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UsersManager um = new UsersManager(parentFrame, true);
                um.setVisible(true);
            }
        };
    }
}
