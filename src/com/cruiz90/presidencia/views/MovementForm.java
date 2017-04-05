package com.cruiz90.presidencia.views;

import com.cruiz90.presidencia.models.Movement;
import com.cruiz90.presidencia.utils.DateLabelFormatter;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class MovementForm extends JDialog {

    private Movement movement;
    private JLabel conceptLabel, amountLabel, descriptionLabel, typeLabel, dateLabel;
    private JTextField concept;
    private JFormattedTextField amount;
    private JRadioButton income, expense;
    private JDatePickerImpl date;
    private JTextArea description;
    private MainView dataContainer;
    private boolean isNew = false;

    public MovementForm(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
    }

    public MovementForm(Frame owner, boolean modal, Movement movement) {
        super(owner, modal);
        this.movement = movement;
        initComponents();
    }

    public void setDataContainer(MainView dataContainer) {
        this.dataContainer = dataContainer;
    }

    private void initComponents() {
        setSize(350, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        //available size of the screen
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - taskBarSize - getHeight()) / 2);

        if (movement == null) {
            movement = new Movement();
            isNew = true;
        }
        setTitle((isNew ? "Agregar" : "Editar") + " Movimiento");

        conceptLabel = new JLabel("Concepto");
        amountLabel = new JLabel("Cantidad");
        descriptionLabel = new JLabel("Descripción");
        typeLabel = new JLabel("Tipo");
        dateLabel = new JLabel("Fecha");
        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        concept = new JTextField(movement.getConcept());
        amount = new JFormattedTextField(amountFormat);
        amount.setText(movement.getAmount() == null ? "" : movement.getAmount() + "");
        description = new JTextArea(movement.getDescription());

        boolean incomeSelected;
        if (movement.getType() == null) {
            incomeSelected = true;
        } else {
            incomeSelected = movement.getType() == 1;
        }
        income = new JRadioButton("Ingreso", incomeSelected);
        income.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        income.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        expense = new JRadioButton("Egreso", !incomeSelected);
        expense.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        expense.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(income);
        radioGroup.add(expense);

        java.util.Date now = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Properties p = new Properties();
        InputStream fileProperties;
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
        date = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 350, 200);
        panel.setSize(350, 300);
        conceptLabel.setBounds(10, 10, 80, 30);
        conceptLabel.setLabelFor(concept);
        panel.add(conceptLabel);
        concept.setBounds(90, 10, 200, 30);
        panel.add(concept);

        amountLabel.setBounds(10, 50, 80, 30);
        amountLabel.setLabelFor(amount);
        panel.add(amountLabel);
        amount.setBounds(90, 50, 200, 30);
        panel.add(amount);

        descriptionLabel.setBounds(10, 90, 80, 30);
        descriptionLabel.setLabelFor(description);
        panel.add(descriptionLabel);
        description.setBounds(90, 90, 200, 60);
        panel.add(description);

        typeLabel.setBounds(10, 160, 80, 30);
        typeLabel.setLabelFor(income);
        panel.add(typeLabel);
        income.setBounds(90, 160, 100, 30);
        panel.add(income);
        expense.setBounds(190, 160, 100, 30);
        panel.add(expense);

        dateLabel.setBounds(10, 200, 80, 30);
        dateLabel.setLabelFor(date);
        panel.add(dateLabel);
        date.setBounds(90, 200, 200, 30);
        panel.add(date);

        JButton save = new JButton("Guardar");
        save.setBounds((getWidth() - 100) / 2, 250, 100, 30);
        save.addActionListener(addListener());
        panel.add(save);
        add(panel);
    }

    private ActionListener addListener() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String errors = validateFields();
                if (!errors.isEmpty()) {
                    JOptionPane.showMessageDialog(dataContainer, errors, "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date dateObj;
                try {
                    dateObj = formater.parse(date.getJFormattedTextField().getText());
                } catch (ParseException ex) {
                    dateObj = new java.util.Date();
                }
                String amountVar = amount.getText().replace(",", "");
                movement.setConcept(concept.getText());
                movement.setDescription(description.getText());
                movement.setAmount(Float.parseFloat(amountVar));
                movement.setDate(new Date(dateObj.getTime()));
                movement.setType(income.isSelected() ? 1 : 2);
                if (isNew) {
                    movement.save();
                } else {
                    movement.update();
                }
                dataContainer.updateDataTable(Movement.findAll());
                setVisible(false);
            }
        };
    }

    private String validateFields() {
        String errors = "";
        if (concept.getText().isEmpty()) {
            errors += "El campo concepto es obligatorio\n";
        }
        if (amount.getText().isEmpty()) {
            errors += "El campo cantidad es obligatorio\n";
        }
        return errors;
    }
}
