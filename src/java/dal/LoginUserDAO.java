package dal;

import entity.ThanhCustomEntity;
import dal.codebase.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUserDAO {

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    public static void main(String[] args) {
        LoginUserDAO lDAO = new LoginUserDAO();
        System.out.println(lDAO.getLoginUserByEmail("hoanganh220104@gmail.com"));
    }
    
    public ThanhCustomEntity.LoginUserDTO getLoginUserByEmail(String email) {
        ThanhCustomEntity.LoginUserDTO user = null;

        try {
            connection = dbc.getConnection();

            String sql = "SELECT\n"
                    + "    u.ID,\n"
                    + "    u.Email,\n"
                    + "    u.EncodedPassword,\n"
                    + "    u.RoleID,\n"
                    + "    u.Status\n"
                    + "FROM \n"
                    + "    User u\n"
                    + "WHERE \n"
                    + "    u.Email = ?;";
            ps = connection.prepareStatement(sql);

            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new ThanhCustomEntity.LoginUserDTO();
                user.setUserID(rs.getInt("ID"));
                user.setUserEmail(rs.getString("Email"));
                user.setUserPass(rs.getString("EncodedPassword"));
                user.setUserRollID(rs.getInt("RoleID"));
                user.setUserStatus(rs.getString("Status"));
            }

        } catch (SQLException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing ResultSet: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing statement: " + ex.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
        return user;
    }
    
    public ThanhCustomEntity.LoginUserDTO getLoginUserById(int id) {
        ThanhCustomEntity.LoginUserDTO user = null;

        try {
            connection = dbc.getConnection();

            String sql = "SELECT\n"
                    + "    u.ID,\n"
                    + "    u.Email,\n"
                    + "    u.EncodedPassword,\n"
                    + "    u.RoleID,\n"
                    + "    u.Status\n"
                    + "FROM \n"
                    + "    User u\n"
                    + "WHERE \n"
                    + "    u.ID = ?;";
            ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new ThanhCustomEntity.LoginUserDTO();
                user.setUserID(rs.getInt("ID"));
                user.setUserEmail(rs.getString("Email"));
                user.setUserPass(rs.getString("EncodedPassword"));
                user.setUserRollID(rs.getInt("RoleID"));
                user.setUserStatus(rs.getString("Status"));
            }

        } catch (SQLException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing ResultSet: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing statement: " + ex.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
        return user;
    }
    
    public ThanhCustomEntity.LoginUserDTO getLoginUser(String email, String encodepass) {
        ThanhCustomEntity.LoginUserDTO user = null;

        try {
            connection = dbc.getConnection();

            String sql = "SELECT\n"
                    + "    u.ID,\n"
                    + "    u.Email,\n"
                    + "    u.EncodedPassword,\n"
                    + "    u.RoleID,\n"
                    + "    u.Status\n"
                    + "FROM \n"
                    + "    User u\n"
                    + "WHERE \n"
                    + "    u.Email = ? \n"
                    + "    AND u.EncodedPassword = ?;";
            ps = connection.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, encodepass);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new ThanhCustomEntity.LoginUserDTO();
                user.setUserID(rs.getInt("ID"));
                user.setUserEmail(rs.getString("Email"));
                user.setUserPass(rs.getString("EncodedPassword"));
                user.setUserRollID(rs.getInt("RoleID"));
                user.setUserStatus(rs.getString("Status"));
            }

        } catch (SQLException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing ResultSet: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing statement: " + ex.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
        return user;
    }

    public String getUsernameByUid(int uid) {
        String username = null;

        try {
            connection = dbc.getConnection();

            String sql = "SELECT\n"
                    + "    u.LastName,\n"
                    + "    u.FirstName\n"
                    + "FROM \n"
                    + "    User u\n"
                    + "WHERE \n"
                    + "    u.ID = ? \n";

            ps = connection.prepareStatement(sql);

            ps.setInt(1, uid);
            rs = ps.executeQuery();

            if (rs.next()) {
                username = rs.getString("LastName") + " " + rs.getString("FirstName");
            }

        } catch (SQLException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing ResultSet: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing statement: " + ex.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
        return username;
    }

    public String getUserPhoneById(int uid) {
        String userphone = null;

        try {
            connection = dbc.getConnection();

            String sql = "SELECT\n"
                    + "    u.PhoneNumber\n"
                    + "FROM \n"
                    + "    User u\n"
                    + "WHERE \n"
                    + "    u.ID = ? \n";

            ps = connection.prepareStatement(sql);

            ps.setInt(1, uid);
            rs = ps.executeQuery();

            if (rs.next()) {
                userphone = rs.getString("PhoneNumber");
            }

        } catch (SQLException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing ResultSet: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing statement: " + ex.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
        return userphone;
    }

    public boolean isValidEmail(String email) {
        String sql = "SELECT * FROM user WHERE Email = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return false;
    }
}
