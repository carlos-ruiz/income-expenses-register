package com.cruiz90.presidencia.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class Util {

    public Connection getDatabaseConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/controlingresos", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed! Check output console");
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos\nAsegurate de tener corriendo el programa XAMPP", "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
