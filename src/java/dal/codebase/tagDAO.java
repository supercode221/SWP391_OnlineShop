/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.codebase;

import entity.codebaseOld.Product;
import entity.codebaseOld.Tag;
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
public class tagDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<Tag> getAllTag() {
        List<Tag> tagList = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT DISTINCT\n"
                    + "	tp.TagID,\n"
                    + "    t.Name as TagName,\n"
                    + "    t.Color,\n"
                    + "    tp.ProductID\n"
                    + "FROM \n"
                    + "	TagProduct tp\n"
                    + "JOIN \n"
                    + "	Tag t ON t.ID = tp.TagID;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Tag t = new Tag();
                t.setId(rs.getInt(1));
                t.setName(rs.getString(2));
                t.setColorCode(rs.getString(3));
                tagList.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return tagList;
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
}
