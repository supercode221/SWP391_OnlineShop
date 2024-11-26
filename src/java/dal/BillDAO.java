/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal;

import dal.codebase.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class BillDAO {
    
    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    java.sql.Connection connection = null;
    ResultSet rs = null;
    
    String statusFilePath = "Status\\BillStatus\\BillStatus.txt";
    
    public List<String> getAllStatus() {
        List<String> allBillStatus = null;
        
        String sql = "SELECT Name FROM Status WHERE Type = 'Bill';";

        // Using try-with-resources for better resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (allBillStatus == null) {
                        allBillStatus = new ArrayList<>();
                    }
                    allBillStatus.add(rs.getString("Name"));
                }
            }

        } catch (Exception e) {
            // Use logging frameworks like Log4j for better log management
            e.printStackTrace();
        }
        
        return allBillStatus;
    }
    
    /**
     * using logger
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * java.util.logging.Logger to log issues or debug
     * 
     * @param level java.util.loggin.Level
     * @param msg optional mesage
     * @param e optional exception
     */
    private void log(Level level, String msg, Throwable e) {
        this.logger.log(level, msg, e);
    }
    
    /**
     * Mostly just a test function
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
    }

}
