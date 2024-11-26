/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dal.codebase.DBContext;
import entity.codebaseOld.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Hoang Anh Dep Trai
 */
public class tagDAO {

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    public void deleteTagDetail(int pid, int tid) {
        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "DELETE FROM tagproduct\n"
                    + "WHERE TagID = ? AND ProductID = ?";
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, tid);
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

    public void addTagDetail(int pid, int tid) {
        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "INSERT INTO tagproduct(TagID, ProductID) VALUES (? , ?);";
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, tid);
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

    public Tag getTagById(int id) {
        Tag t = null;
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * FROM Tag t WHERE t.ID = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                t = new Tag();
                t.setId(rs.getInt(1));
                t.setName(rs.getString(2));
                t.setColorCode(rs.getString(3));
            }
        } catch (SQLException ex) {
            t = null;
            System.out.println(ex);
        }
        return t;
    }

    public static void main(String[] args) {
        new tagDAO().addTagDetail(1, 1);
    }
}
