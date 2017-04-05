package com.cruiz90.presidencia.views;

import com.cruiz90.presidencia.models.User;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class UserForm extends JDialog implements ActionListener {

    private JTextField userName, name;
    private JPasswordField password, matchPassword;
    private User user;
    private boolean isNew;
    private UsersManager dataContainer;

    public UserForm(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
    }

    public UserForm(Frame owner, boolean modal, User user) {
        super(owner, modal);
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        if (user == null) {
            user = new User();
            isNew = true;
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        //available size of the screen
        setSize(330, 300);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - taskBarSize - getHeight()) / 2);
        setTitle("Nuevo usuario");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel userNameLabel = new JLabel("Nombre de Usuario");
        JLabel passwordLabel = new JLabel("Contraseña");
        JLabel matchPasswordLabel = new JLabel("Repite Contraseña");
        JLabel nameLabel = new JLabel("Nombre");
        userName = new JTextField(user.getUserName());
        name = new JTextField(user.getName());
        password = new JPasswordField();
        matchPassword = new JPasswordField();
        JButton save = new JButton("Guardar");
        save.addActionListener(this);

        nameLabel.setBounds(20, 20, 120, 30);
        add(nameLabel);
        name.setBounds(150, 20, 150, 30);
        add(name);
        userNameLabel.setBounds(20, 60, 120, 30);
        add(userNameLabel);
        userName.setBounds(150, 60, 150, 30);
        add(userName);
        passwordLabel.setBounds(20, 100, 120, 30);
        add(passwordLabel);
        password.setBounds(150, 100, 150, 30);
        add(password);
        matchPasswordLabel.setBounds(20, 140, 120, 30);
        add(matchPasswordLabel);
        matchPassword.setBounds(150, 140, 150, 30);
        add(matchPassword);
        save.setBounds(100, 200, 100, 30);
        add(save);
    }

    public void setDataContainer(UsersManager dataContainer) {
        this.dataContainer = dataContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean hasErrors = false;
        if (userName.getText().length() < 4) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario debe ser de al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            hasErrors = true;
        }
        String pass = new String(password.getPassword());
        String passMatch = new String(matchPassword.getPassword());

        if (pass.isEmpty() || passMatch.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Favor de escribir la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            hasErrors = true;
        } else if (!pass.equals(passMatch)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            hasErrors = true;
        }

        if (hasErrors) {
            return;
        }
        if (isNew) {
            user = new User(name.getText(), userName.getText(), pass);
            user.save();
        } else {
            user.setUserName(userName.getText());
            user.setName(name.getText());
            user.setPassword(pass);
            user.update();
        }
        if (dataContainer != null) {
            dataContainer.updateView();
        }
        dispose();
    }

}
