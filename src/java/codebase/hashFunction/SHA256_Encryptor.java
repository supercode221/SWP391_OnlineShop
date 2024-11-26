/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codebase.hashFunction;

/**
 *
 * @author Nguyễn Nhật Minh
 */
import dal.codebase.DBContext;
import java.sql.Connection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Java program to calculate SHA hash value
public class SHA256_Encryptor {

  //NHẬP INPUT LÀ STRING SAU ĐÓ HÀM SẼ TRẢ VỀ MỘT STRING ĐƯỢC MÃ HÓA BẰNG THUẬT TOÁN SHA-256
    public static String sha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // CHUYỂN ĐỔI TỪNG BYTE THÀNH MỘT STRING HEXADECIMAL
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) {
        String originPassword = "1";
        String email = "minhnnhe161060";
        
        String encodedPassword = SHA256_Encryptor.sha256Hash(originPassword);
        String sql = ""
                + "UPDATE User "
                + "SET "
                + "     EncodedPassword = ? "
                + "WHERE "
                + "     Email LIKE ?; ";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            DBContext dbc = new DBContext();
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, encodedPassword);
            ps.setString(2, "%" + email + "%");
            int affectedLine = ps.executeUpdate();
            if (affectedLine != 1) {
                throw new Exception("affected line != 1");
            }
            System.out.println("Affected Line = " + affectedLine);
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            
        }
    }
    
}
