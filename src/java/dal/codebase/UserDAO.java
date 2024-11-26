package dal.codebase;

import codebase.FileHandler.FileInput;
import entity.HoangAnhCustomEntity;
import entity.codebaseOld.User;
import entity.codebaseNew.Role;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO extends DBContext {
    
    String statusFilePath = "Status\\UserStatus\\UserStatus.txt";

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    
    public List<String> getAllStatus() {
        List<String> allUserStatus = null;
        
        String sql = "SELECT Name FROM Status WHERE Type = 'User';";

        // Using try-with-resources for better resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (allUserStatus == null) {
                        allUserStatus = new ArrayList<>();
                    }
                    allUserStatus.add(rs.getString("Name"));
                }
            }

        } catch (Exception e) {
            // Use logging frameworks like Log4j for better log management
            e.printStackTrace();
        }
        
        return allUserStatus;
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User;";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int role = rs.getInt("roleID");
                int changeLogID = rs.getInt("changeLogID");
                String first_name = rs.getString("FirstName");
                String status = rs.getString("Status");
                String encoded_password = rs.getString("EncodedPassword");
                String phone_number = rs.getString("PhoneNumber");
                String email = rs.getString("Email");
                String last_name = rs.getString("LastName");
                String description = rs.getString("Description");
                String avatar_image = rs.getString("AvatarImage");

                User user = new User(id, first_name, last_name, email, encoded_password,
                        phone_number, description, avatar_image, changeLogID, role, status);

                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    // Cập nhật thông tin người dùng
    public int updateProfileUser(User user) {
        int rowsAffected = 0;

        String sql = "UPDATE User "
                + "SET FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ?, "
                + "AvatarImage = ?, RoleID = ?, Status = ? "
                + "WHERE ID = ?;";

        try (Connection connection = getConnection(); PreparedStatement pre = connection.prepareStatement(sql)) {

            pre.setString(1, user.getFirstName());
            pre.setString(2, user.getLastName());
            pre.setString(3, user.getEmail());
            pre.setString(4, user.getPhoneNumber());
            pre.setString(5, user.getAvatarImage());
            pre.setInt(6, user.getRoleId());
            pre.setString(7, user.getStatus());
            pre.setInt(8, user.getId());

            rowsAffected = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowsAffected;
    }

    // ham nay o thich update gif cung duoc, co gia tri nao thay doi thi no tu dong update, khong co thi no giu nguyen ban dau oceee
    public int updatePatial(User user) {
        int n = 0;

        StringBuilder sql = new StringBuilder("Update User set ");
        List<Object> listParams = new ArrayList<>();

        if (user.getChangeLodID() != -1) {
            sql.append("ChangeLogID = ?, "); // bat buoc
            listParams.add(user.getChangeLodID());
        }

        // 1 - user
        sql.append("RoleID = ?, "); // bat buoc
        listParams.add(user.getRoleId());

        if (user.getStatus() != null && !user.getStatus().isEmpty()) {
            sql.append(" Status = ?, ");
            listParams.add(user.getStatus());
        }

        if (user.getEncodedPassword() != null && !user.getEncodedPassword().isEmpty()) {
            sql.append(" EncodedPassword = ?, ");
            listParams.add(user.getEncodedPassword());
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            sql.append("Email = ?, ");
            listParams.add(user.getEmail());
        }

        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            sql.append("PhoneNumber = ?, ");
            listParams.add(user.getPhoneNumber());
        }

        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            sql.append("LastName = ?, ");
            listParams.add(user.getLastName());
        }

        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            sql.append("FirstName = ?, ");
            listParams.add(user.getFirstName());
        }

        if (user.getDescription() != null && !user.getDescription().isEmpty()) {
            sql.append("Description = ?, ");
            listParams.add(user.getDescription());
        }

        if (user.getAvatarImage() != null && !user.getAvatarImage().isEmpty()) {
            sql.append("AvatarImage = ?, ");
            listParams.add(user.getAvatarImage());
        }

        if (sql.length() > 0) {
            sql.setLength(sql.length() - 2);
        }

        sql.append(" Where ID = ?");
        listParams.add(user.getId());

        Connection connection = getConnection();
        try {
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            for (int i = 0; i < listParams.size(); i++) {
                pre.setObject(i + 1, listParams.get(i));
            }
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    // Lấy thông tin người dùng dựa trên userID
    public User getUserByUserID(int userID) {
        User user = null;

        String sql = "SELECT * FROM User WHERE ID = ?";

        try (Connection connection = getConnection(); PreparedStatement pre = connection.prepareStatement(sql)) {

            pre.setInt(1, userID);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int role = rs.getInt("roleID");
                int changeLogID = rs.getInt("changeLogID");
                String first_name = rs.getString("FirstName");
                String status = rs.getString("Status");
                String encoded_password = rs.getString("EncodedPassword");
                String phone_number = rs.getString("PhoneNumber");
                String email = rs.getString("Email");
                String last_name = rs.getString("LastName");
                String description = rs.getString("Description");
                String avatar_image = rs.getString("AvatarImage");

                user = new User(id, first_name, last_name, email, encoded_password,
                        phone_number, description, avatar_image, changeLogID, role, status);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public List<HoangAnhCustomEntity.Address> getUserAddressById(int uid) {
        List<HoangAnhCustomEntity.Address> userAddress = new ArrayList<>();

        String sql = "SELECT\n"
                + "	*\n"
                + "FROM\n"
                + "	address\n"
                + "WHERE\n"
                + "	UserID = ?;";

        try (Connection connection = getConnection(); PreparedStatement pre = connection.prepareStatement(sql)) {

            pre.setInt(1, uid);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                HoangAnhCustomEntity.Address a = new HoangAnhCustomEntity.Address(
                        rs.getInt("ID"), 
                        rs.getInt("UserID"), 
                        rs.getString("Country"), 
                        rs.getString("TinhThanhPho"), 
                        rs.getString("QuanHuyen"), 
                        rs.getString("PhuongXa"), 
                        rs.getString("Details"), 
                        rs.getString("Note")
                );
                
                userAddress.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userAddress;
    }

    public boolean checkEmailExists(String email) {
        boolean emailExists = false;
        String sql = "select * from User where Email = ?";  // Modify 'users' to your actual table name

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    emailExists = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailExists;
    }

    public boolean isValidEmail(String email) {
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * FROM User WHERE Email = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
        return false;
    }

    public int updatePassword(String email, String newPassword) {
        int rowsAffected = 0;

        // Query to get the user ID based on the email
        String selectQuery = "SELECT ID FROM User WHERE Email = ?";

        // Query to update the password using the retrieved user ID
        String updateQuery = "UPDATE User SET EncodedPassword = ? WHERE ID = ?";

        try (Connection conn = getConnection(); PreparedStatement selectStmt = conn.prepareStatement(selectQuery); PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // First, get the user ID based on the email
            selectStmt.setString(1, email);
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    int userID = rs.getInt("ID");

                    // Now update the password for the retrieved user ID
                    updateStmt.setString(1, newPassword);  // Set the new password
                    updateStmt.setInt(2, userID);  // Set the user ID

                    // Execute the update
                    rowsAffected = updateStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public Role getUserRoleByUserID(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        UserDAO uDAO = new UserDAO();
        System.out.println(uDAO.getUserAddressById(1));
    }
}
