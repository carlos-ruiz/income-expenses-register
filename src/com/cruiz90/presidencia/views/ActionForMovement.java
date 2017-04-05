package com.cruiz90.presidencia.views;

import com.cruiz90.presidencia.models.Movement;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class ActionForMovement extends JDialog {

    private JButton delete, edit;
    JPanel mainPanel;
    private MainView dataContainer;
    private final Integer movementId;
    private Movement movement;

    public ActionForMovement(Frame owner, boolean modal, Integer movementId) {
        super(owner, modal);
        this.movementId = movementId;
        initComponents();
    }

    void setDataContainer(MainView dataContainer) {
        this.dataContainer = dataContainer;
    }

    private void initComponents() {
        setSize(250, 250);
        setTitle("Editar movimiento");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        //available size of the screen
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - taskBarSize - getHeight()) / 2);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        movement = Movement.findById(movementId);

        delete = new JButton("Borrar");
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/com/cruiz90/presidencia/images/delete.png")).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        delete.setIcon(imageIcon);
        delete.addActionListener(delete());
        delete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        delete.setBounds(10, 10, 160, 30);
        mainPanel.add(delete);

        edit = new JButton("Editar");
        imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/com/cruiz90/presidencia/images/edit.png")).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        edit.setIcon(imageIcon);
        edit.addActionListener(edit());
        edit.setBounds(10, 50, 160, 30);
        edit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainPanel.add(edit);

        mainPanel.setBounds(10, 10, 200, 200);
        mainPanel.setSize(200, 200);
        add(mainPanel);
    }

    private ActionListener delete() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea borrar el " + (movement.getType() == 1 ? "Ingreso" : "Egreso") + " por concepto de " + movement.getConcept() + "?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    movement.delete();
                    dataContainer.updateDataTable(Movement.findAll());
                }
                closeDialog();
            }
        };
    }

    private ActionListener edit() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MovementForm mf = new MovementForm(dataContainer, true, movement);
                mf.setDataContainer(dataContainer);
                mf.setVisible(true);

                closeDialog();
            }
        };
    }

    private void closeDialog() {
        setVisible(false);
        dispose();
    }

}
