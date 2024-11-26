/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal.codebase;

import entity.codebaseNew.Address;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class AddressDAO extends DBContext {
    public boolean addAddress(Address address) {
        String query = "INSERT INTO Address (userId, country, TinhThanhPho, QuanHuyen, PhuongXa, details, note) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, address.getUser().getId());
            preparedStatement.setString(2, "Viet Nam");
            preparedStatement.setString(3, address.getTinh_thanhpho());
            preparedStatement.setString(4, address.getHuyen_quan());
            preparedStatement.setString(5, address.getXa_phuong());
            preparedStatement.setString(6, address.getDetails());
            preparedStatement.setString(7, address.getNote());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Address> getAddressesByUserId(int userId) {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM Address WHERE userId = ?";
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setCountry(resultSet.getString("country"));
                address.setTinh_thanhpho(resultSet.getString("ThanhPho"));
                address.setHuyen_quan(resultSet.getString("QuanHuyen"));
                address.setXa_phuong(resultSet.getString("PhuongXa"));
                address.setDetails(resultSet.getString("details"));
                address.setNote(resultSet.getString("note"));
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

}
