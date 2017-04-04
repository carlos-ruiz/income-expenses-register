package com.cruiz90.presidencia.views;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class MainView extends JFrame {

    private JDatePickerImpl datePickerFrom, datePickerTo;

    public MainView() {
        initComponents();
    }

    private void initComponents() {
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

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(10, 120, 870, 10);
        add(separator);

        JButton addMovement = new JButton("Nuevo movimiento");
        addMovement.addActionListener(showMovememtForm());
        addMovement.setBounds(10, 130, 150, 30);
        add(addMovement);

        JRadioButton allMovementsOption = new JRadioButton("Todos");
        allMovementsOption.setSelected(true);
        allMovementsOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allMovementsOption.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        allMovementsOption.setBounds(440, 130, 70, 30);
        add(allMovementsOption);

        JRadioButton periodMovementsOption = new JRadioButton("Periodo del");
        periodMovementsOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        periodMovementsOption.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        periodMovementsOption.setBounds(515, 130, 90, 30);
        periodMovementsOption.addItemListener(periodRadioChange());
        add(periodMovementsOption);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(allMovementsOption);
        radioGroup.add(periodMovementsOption);

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
        datePickerFrom.setBounds(610, 130, 120, 30);
        add(datePickerFrom);
        JLabel to = new JLabel("al");
        to.setHorizontalAlignment(JLabel.CENTER);
        to.setBounds(730, 130, 20, 30);
        add(to);
        UtilDateModel modelTo = new UtilDateModel();
        modelTo.setDate(year, month, day);
        modelTo.setSelected(true);
        JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p);
        datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        datePickerTo.getComponent(1).setEnabled(false);
        datePickerTo.setBounds(750, 130, 120, 30);
        add(datePickerTo);
        // Panel de Movimientos
    }

    private ActionListener showMovememtForm() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        };
    }

    private ActionListener showAllMovements() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
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

    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "dd-MM-yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }
}
