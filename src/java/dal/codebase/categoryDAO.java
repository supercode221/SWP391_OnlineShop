/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.codebase;

import entity.codebaseOld.Category;
import entity.codebaseOld.Product;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Hoang Anh Dep Trai
 */
public class categoryDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public ArrayList<Category> getAllCate() throws IOException {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * FROM SubCategory;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setOriginalType(rs.getString(3));
                categories.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return categories;
    }

    public Category getCategoryById(int id) throws IOException {
        Category c = null;
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * FROM SubCategory sc WHERE sc.ID = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                c = new Category();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setOriginalType(rs.getString(3));
            }
        } catch (SQLException ex) {
            c = null;
            System.out.println(ex);
        }
        return c;
    }
}
