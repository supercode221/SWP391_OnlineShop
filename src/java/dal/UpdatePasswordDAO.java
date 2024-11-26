/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dal.codebase.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * Hoang Anh Dep Trai
 */
public class UpdatePasswordDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public void changeUserPassword(String encodePass, int uid) {
        try {
            connection = dbc.getConnection();
            String sql = "UPDATE User\n"
                    + "SET EncodedPassword = ?\n"
                    + "WHERE ID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, encodePass);
            ps.setInt(2, uid);
            ps.executeUpdate();

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
    }

    public String getCurrentEncodedPass(int uid) {
        String pass = null;
        try {
            connection = dbc.getConnection();
            String sql = "SELECT EncodedPassword FROM User WHERE ID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            if(rs.next()){
                pass = rs.getString("EncodedPassword");
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
        
        return pass;
    }
    
    public boolean checkCurrentPass(String currentPass, int uid){
        if(getCurrentEncodedPass(uid).equals(currentPass)){
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String[] args) {
        UpdatePasswordDAO udao = new UpdatePasswordDAO();
        System.out.println(udao.checkCurrentPass("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 1));
    }
}
