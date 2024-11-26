/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.codebase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Hoang Anh Dep Trai
 */
public class DBContext {

    ResourceBundle bundle = ResourceBundle.getBundle("config.database");
    
    public Connection getConnection() {
        try {
            Class.forName(bundle.getString("drivername"));
            String url = bundle.getString("url");
            String username = bundle.getString("username"); 
            String password = bundle.getString("password");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public static void main(String[] args) throws SQLException {
        DBContext db = new DBContext();
        if(db.getConnection() != null){
            System.out.println("Success");
        }
    }
}
