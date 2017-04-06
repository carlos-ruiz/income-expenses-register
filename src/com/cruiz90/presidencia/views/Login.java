package com.cruiz90.presidencia.views;

import com.cruiz90.presidencia.models.User;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class Login extends JFrame implements ActionListener {

    private JTextField userName;
    private JPasswordField password;

    public Login() {
        initComponents();
    }

    private void initComponents() {
        if (User.getAll().isEmpty()) {
            UserForm uf = new UserForm(this, true);
            uf.setVisible(true);
        }
        setSize(330, 200);
        setLayout(null);
        setTitle("Control de ingresos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        JLabel userNameLabel = new JLabel("Nombre de usuario");
        JLabel passwordLabel = new JLabel("Contraseña");
        userName = new JTextField();
        password = new JPasswordField();
        JButton login = new JButton("Entrar");
        login.addActionListener(this);

        userNameLabel.setBounds(20, 20, 120, 30);
        add(userNameLabel);
        userName.setBounds(150, 20, 150, 30);
        add(userName);
        passwordLabel.setBounds(20, 60, 120, 30);
        add(passwordLabel);
        password.setBounds(150, 60, 150, 30);
        add(password);
        login.setBounds(100, 110, 100, 30);
        add(login);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (userName.getText().isEmpty() || password.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre de usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String pass = new String(password.getPassword());
        User user = User.findByUserName(userName.getText());
        if (user != null && user.comparePassword(pass)) {
            MainView mainView = new MainView();
            mainView.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Nombre de usuario y/o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
