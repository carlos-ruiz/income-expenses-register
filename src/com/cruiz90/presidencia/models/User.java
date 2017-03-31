package com.cruiz90.presidencia.models;

import com.cruiz90.presidencia.utils.Util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
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
public class User {

    private Integer userId;
    private String name;
    private String userName;
    private String password;

    public User() {

    }

    public User(Integer userId, String name, String userName, String password) {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = encryptPassword(password);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }

    private String encryptPassword(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return password;
        }
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public boolean comparePassword(String passwordToCheck) {
        return password.equals(encryptPassword(passwordToCheck));
    }

    public void save() {
        Connection conn = new Util().getDatabaseConnection();
        if (conn != null) {
            PreparedStatement ps = null;
            try {
                String query = "INSERT INTO users (name, username, password) VALUES(?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, userName);
                ps.setString(3, password);
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
                String query = "DELETE FROM users WHERE user_id=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, userId);
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
                String query = "UPDATE users SET"
                        + " name = ?, userName=?, password=?"
                        + " WHERE user_id=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, userName);
                ps.setString(3, password);
                ps.setInt(4, userId);
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

    public static List<User> getAll() {
        Connection conn = new Util().getDatabaseConnection();
        List<User> result = null;
        if (conn != null) {
            Statement ps = null;
            try {
                String query = "SELECT * FROM users";
                ps = conn.createStatement();
                ResultSet rs = ps.executeQuery(query);
                result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("userName"), rs.getString("password")));
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

    public static User findByUserName(String userName) {
        Connection conn = new Util().getDatabaseConnection();
        User user = null;
        if (conn != null) {
            PreparedStatement ps = null;
            try {
                String query = "SELECT * FROM users WHERE username=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, userName);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    user = new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("username"), rs.getString("password"));
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
        return user;
    }
}
