package dal;

import dal.codebase.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterUserDAO {

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public void register(String fname, String lname, String email, String encodepass) {
        try {
            connection = dbc.getConnection();

            String sql = "INSERT INTO User(FirstName, LastName, Email, EncodedPassword)\n"
                    + "VALUES (?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, encodepass);
            ps.execute();
        } catch (SQLException ex) {

            System.err.println("SQL Error: " + ex.getMessage());

            throw new RuntimeException(ex);
        } finally {

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
    }

    public void registerGoogle(String fname, String lname, String email, String encodepass, String gender, String phone, String avatar) {
        try {
            connection = dbc.getConnection();

            String sql = "INSERT INTO User(FirstName, LastName, Email, EncodedPassword, Gender, PhoneNumber, AvatarImage)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, encodepass);
            ps.setString(5, gender);
            ps.setString(6, phone);
            ps.setString(7, avatar);
            ps.execute();

        } catch (SQLException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
            throw new RuntimeException(ex);
        } finally {

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
    }

}
