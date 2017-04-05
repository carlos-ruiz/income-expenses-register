package com.cruiz90.presidencia.views;

import com.cruiz90.presidencia.models.User;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class UsersManager extends JDialog {

    private List<User> users;
    private JFrame parentFrame;
    private JPanel mainPanel;

    public UsersManager() {
        initComponents();
    }

    public UsersManager(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Administracion de usuarios");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        //available size of the screen
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - taskBarSize - getHeight()) / 2);
        mainPanel = new JPanel(null);
        parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        JButton add = new JButton("Nuevo usuario");
        add.setBounds(10, 20, 200, 50);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/com/cruiz90/presidencia/images/add.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        add.setIcon(imageIcon);
        add.setToolTipText("Nuevo usuario");
        add.addActionListener(addUserListener());
        add(add);

        setUpPanel();

        mainPanel.setBounds(0, 75, 400, 300);
        add(mainPanel);
    }

    private ActionListener deleteUserListener() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                User user = users.get(Integer.parseInt(e.getActionCommand()));
                int response = JOptionPane.showConfirmDialog(null, "¿Seguro que desa eliminar " + user.getName() + "?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    user.delete();
                    updateView();
                }
            }
        };
    }

    private ActionListener editUserListener() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addUser(new UserForm(parentFrame, true, users.get(Integer.parseInt(e.getActionCommand()))));
            }
        };
    }

    private ActionListener addUserListener() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addUser(new UserForm(parentFrame, true));
            }
        };
    }

    private void addUser(UserForm uf) {
        uf.setDataContainer(this);
        uf.setVisible(true);
    }

    public void updateView() {
        mainPanel.removeAll();
        setUpPanel();
        mainPanel.validate();
        mainPanel.repaint();
    }

    private void setUpPanel() {
        int x, y, height;
        x = 10;
        y = 10;
        height = 30;
        ImageIcon deleteImage = new ImageIcon(new ImageIcon(getClass().getResource("/com/cruiz90/presidencia/images/delete.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon editImage = new ImageIcon(new ImageIcon(getClass().getResource("/com/cruiz90/presidencia/images/edit.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        users = User.getAll();
        int listPosition = 0;
//        for (int i = 0; i < 6; i++) {
        for (User user : users) {
            JLabel userName = new JLabel(user.getName());
            JButton deleteUser = new JButton(deleteImage);
            deleteUser.setActionCommand(listPosition + "");
            deleteUser.setToolTipText("Eliminar " + user.getName());
            deleteUser.addActionListener(deleteUserListener());
            JButton editUser = new JButton(editImage);
            editUser.setActionCommand(listPosition + "");
            editUser.setToolTipText("Editar " + user.getName());
            editUser.addActionListener(editUserListener());
            if (y > 365) {
                x += 300;
                y = 40;
            }
            userName.setBounds(x, y, 150, height);
            mainPanel.add(userName);
            deleteUser.setBounds(x + 160, y, 30, height);
            mainPanel.add(deleteUser);
            editUser.setBounds(x + 200, y, 30, height);
            mainPanel.add(editUser);
            y += 40;
            listPosition++;
        }
    }

}
