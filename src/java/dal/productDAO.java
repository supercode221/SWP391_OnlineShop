/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import codebase.FileHandler.FileInput;
import dal.codebase.DBContext;
import dal.codebase.UserDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Hoang Anh Dep Trai
 */
public class productDAO {
    
    String statusFilePath = "Status\\ProductStatus\\ProductStatus.txt";

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;
    
    public List<String> getAllStatus() {
        List<String> allProductStatus = null;
        
        String sql = "SELECT Name FROM Status WHERE Type = 'Product';";

        // Using try-with-resources for better resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (allProductStatus == null) {
                        allProductStatus = new ArrayList<>();
                    }
                    allProductStatus.add(rs.getString("Name"));
                }
            }

        } catch (Exception e) {
            // Use logging frameworks like Log4j for better log management
            e.printStackTrace();
        }
        
        return allProductStatus;
    }

    public void changeStatus(int pid, String status) {
        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "UPDATE product\n"
                    + "SET status = ?\n"
                    + "WHERE ID = ?";
            ps = connection.prepareStatement(checkSql);
            ps.setString(1, status);
            ps.setInt(2, pid);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
    }
}
