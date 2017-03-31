package com.cruiz90.presidencia.views;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class MainView extends JFrame {

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
        setSize(screenSize.width, screenSize.height - taskBarSize);
        //available size of the screen
        setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        URL iconURL = getClass().getResource("/com/cruiz90/presidencia/images/logo_huaniqueo.png");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());
    }

}
