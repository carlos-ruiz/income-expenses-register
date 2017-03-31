package com.cruiz90.presidencia.models;

import com.cruiz90.presidencia.utils.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ISC. Carlos Alfredo Ruiz Calderon <car.ruiz90@gmail.com>
 */
public class Movement {

    private Integer movementId;
    private String concept;
    private String description;
    private Float amount;
    private Date date;
    private Integer type; // 1 = Ingreso, 2 = Egreso

    public Movement() {
    }

    public Movement(Integer movementId, String concept, String description, Float amount, Date date, Integer type) {
        this.movementId = movementId;
        this.concept = concept;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public Movement(String concept, String description, Float amount, Date date, Integer type) {
        this.concept = concept;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public void save() {
        Connection conn = new Util().getDatabaseConnection();
        if (conn != null) {
            PreparedStatement ps = null;
            try {
                String query = "INSERT INTO movements (concept, amount, description, date, type) VALUES(?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, concept);
                ps.setFloat(2, amount);
                ps.setString(3, description);
                ps.setDate(4, date);
                ps.setInt(5, type);
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public void delete() {
        Connection conn = new Util().getDatabaseConnection();
        if (conn != null) {
            PreparedStatement ps = null;
            try {
                String query = "DELETE FROM movements WHERE movement_id=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, movementId);
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public void update() {
        Connection conn = new Util().getDatabaseConnection();
        if (conn != null) {
            PreparedStatement ps = null;
            try {
                String query = "UPDATE movements SET"
                        + " concept = ?, amount=?, description=?, date=?, type=?"
                        + " WHERE movement_id=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, concept);
                ps.setFloat(2, amount);
                ps.setString(3, description);
                ps.setDate(4, date);
                ps.setInt(5, type);
                ps.setInt(6, movementId);
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public static List<Movement> getAll() {
        Connection conn = new Util().getDatabaseConnection();
        List<Movement> result = null;
        if (conn != null) {
            Statement ps = null;
            try {
                String query = "SELECT * FROM movements";
                ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(query);
                result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new Movement(rs.getInt("movemet_id"), rs.getString("concept"), rs.getString("description"), rs.getFloat("amount"), rs.getDate("date"), rs.getInt("type")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return result;
    }

    public static List<Movement> findIncome() {
        return findbyType(1);
    }

    public static List<Movement> findExpenditures() {
        return findbyType(2);
    }

    public static List<Movement> findbyType(Integer type) {
        Connection conn = new Util().getDatabaseConnection();
        List<Movement> movements = new ArrayList<>();
        if (conn != null) {
            PreparedStatement ps = null;
            try {
                String query = "SELECT * FROM movements WHERE type=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, type);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    movements.add(new Movement(rs.getInt("movemet_id"), rs.getString("concept"), rs.getString("description"), rs.getFloat("amount"), rs.getDate("date"), rs.getInt("type")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return movements.size() > 0 ? movements : null;
    }
}
