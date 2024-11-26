/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.HoangAnhCustomEntity;
import entity.ThanhCustomEntity;
import dal.codebase.DBContext;
import entity.codebaseOld.Color;
import entity.codebaseOld.Size;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class MyOrderDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<HoangAnhCustomEntity.Order> getOrderByBillID(int billId) {
        List<HoangAnhCustomEntity.Order> orderList = new ArrayList<>();

        String sql = "SELECT "
                + " o.ID AS OrderID, "
                + " p.ID AS ProductID, "
                + " p.Name AS ProductName, "
                + " p.ThumbnailImage AS Thumbnail, "
                + " o.Quantity as Quantity, "
                + " o.TotalPrice AS TotalPrice, "
                + " s.ID AS SizeID, "
                + " s.Size AS Size, "
                + " c.ID AS ColorID, "
                + " c.Name AS Color, "
                + " c.ColorCode AS ColorCode, "
                + " o.isFeedbacked as Feedback "
                + " FROM `defaultdb`.`order` o "
                + " JOIN Product p ON p.ID = o.ProductID "
                + " JOIN Size s ON s.ID = o.SizeID "
                + " JOIN Color c ON c.ID = o.ColorID "
                + " WHERE o.BillID = ?";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoangAnhCustomEntity.Order order = new HoangAnhCustomEntity.Order();
                    order.setId(rs.getInt("OrderID"));
                    order.setProductId(rs.getInt("ProductID"));
                    order.setProductName(rs.getString("ProductName"));
                    order.setThumbnailImage(rs.getString("Thumbnail"));
                    order.setQuantity(rs.getInt("Quantity"));
                    order.setTotalPrice(rs.getDouble("TotalPrice"));
                    order.setSize(new Size(rs.getInt("SizeID"), rs.getString("Size")));
                    order.setColor(new Color(rs.getInt("ColorID"), rs.getString("Color"), rs.getString("ColorCode")));
                    order.setIsFeedbacked(rs.getBoolean("Feedback"));
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return orderList;
    }

    public List<ThanhCustomEntity.Bill> getBillByUid(int uid) {
        List<ThanhCustomEntity.Bill> billList = new ArrayList<>();

        String sql = "SELECT \n"
                + "    b.ID AS BillID,\n"
                + "    b.TotalPrice,\n"
                + "    b.PublishDate AS BillPublishDate,\n"
                + "    b.Status AS BillStatus,\n"
                + "    b.CustomerID AS CustomerIdBill,\n"
                + "    b.SalerID AS SalerID,\n"
                + "    b.ShipperID AS ShipperID,\n"
                + "    a.ID AS AddressID,\n"
                + "    a.UserID AS UserIDForAddress,\n"
                + "    a.Country AS UserCountry,\n"
                + "    a.TinhThanhPho AS UserTinhThanhPho,\n"
                + "    a.QuanHuyen AS UserQuanHuyen,\n"
                + "    a.PhuongXa AS UserPhuongXa,\n"
                + "    a.Details AS UserAddressDetails,\n"
                + "    a.Note AS UserAddressNote,\n"
                + "    pm.ID AS PaymentMethodID,\n"
                + "    pm.Name AS PaymentMethodName,\n"
                + "    sm.ID AS ShipMethodID,\n"
                + "    sm.Name AS ShipMethodName,\n"
                + "    b.isFeedbacked AS Feedback,\n"
                + "    b.isCanceledPending AS Cancel\n"
                + "FROM\n"
                + "    Bills b\n"
                + "        JOIN\n"
                + "    Address a ON b.AddressID = a.ID\n"
                + "        JOIN\n"
                + "    paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                + "        JOIN\n"
                + "    shipmethod sm ON sm.ID = b.ShipMethodID\n"
                + "WHERE\n"
                + "    b.CustomerID = ?\n"
                //Order by DESC lấy từ mới nhất - cũ nhất
                + "ORDER BY BillPublishDate DESC;";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, uid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.Bill bill = new ThanhCustomEntity.Bill();
                    bill.setId(rs.getInt("BillID"));
                    bill.setTotalPrice(rs.getDouble("TotalPrice"));
                    bill.setPublishDate(formateDateTime(rs.getTimestamp("BillPublishDate")));
                    bill.setStatus(rs.getString("BillStatus"));
                    bill.setCustomerId(rs.getInt("CustomerIdBill"));
                    bill.setSalerId(rs.getInt("SalerID"));
                    bill.setShipperId(rs.getInt("ShipperID"));
                    bill.setAddress(new HoangAnhCustomEntity.Address(rs.getInt("AddressID"),
                            rs.getInt("UserIDForAddress"),
                            rs.getString("UserCountry"),
                            rs.getString("UserTinhThanhPho"),
                            rs.getString("UserQuanHuyen"),
                            rs.getString("UserPhuongXa"),
                            rs.getString("UserAddressDetails"),
                            rs.getString("UserAddressNote")));
                    bill.setShipMethod(new HoangAnhCustomEntity.ShipMethod(rs.getInt("ShipMethodID"), rs.getString("ShipMethodName")));
                    bill.setPayment(new HoangAnhCustomEntity.PaymentMethod(rs.getInt("PaymentMethodID"), rs.getString("PaymentMethodName")));
                    bill.setIsFeedbacked(rs.getBoolean("Feedback"));
                    bill.setIsCanceled(rs.getBoolean("Cancel"));

                    List<HoangAnhCustomEntity.Order> orderList = getOrderByBillID(rs.getInt("BillID"));
                    bill.setOrder(orderList);

                    billList.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return billList;
    }

    public ThanhCustomEntity.Bill getBillByUidByBid(int uid, int bid) {
        ThanhCustomEntity.Bill bill = new ThanhCustomEntity.Bill();

        String sql = "SELECT \n"
                + "    b.ID AS BillID,\n"
                + "    b.TotalPrice,\n"
                + "    b.PublishDate AS BillPublishDate,\n"
                + "    b.Status AS BillStatus,\n"
                + "    b.CustomerID AS CustomerIdBill,\n"
                + "    b.SalerID AS SalerID,\n"
                + "    b.ShipperID AS ShipperID,\n"
                + "    a.ID AS AddressID,\n"
                + "    a.UserID AS UserIDForAddress,\n"
                + "    a.Country AS UserCountry,\n"
                + "    a.TinhThanhPho AS UserTinhThanhPho,\n"
                + "    a.QuanHuyen AS UserQuanHuyen,\n"
                + "    a.PhuongXa AS UserPhuongXa,\n"
                + "    a.Details AS UserAddressDetails,\n"
                + "    a.Note AS UserAddressNote,\n"
                + "    pm.ID AS PaymentMethodID,\n"
                + "    pm.Name AS PaymentMethodName,\n"
                + "    sm.ID AS ShipMethodID,\n"
                + "    sm.Name AS ShipMethodName,\n"
                + "    b.isFeedbacked AS Feedback,\n"
                + "    b.isCanceledPending AS Cancel\n"
                + "FROM\n"
                + "    Bills b\n"
                + "        JOIN\n"
                + "    Address a ON b.AddressID = a.ID\n"
                + "        JOIN\n"
                + "    paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                + "        JOIN\n"
                + "    shipmethod sm ON sm.ID = b.ShipMethodID\n"
                + "WHERE\n"
                + "    b.ID = ? AND b.CustomerID = ?";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bid);
            ps.setInt(2, uid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bill.setId(rs.getInt("BillID"));
                    bill.setTotalPrice(rs.getDouble("TotalPrice"));
                    bill.setPublishDate(formateDateTime(rs.getTimestamp("BillPublishDate")));
                    bill.setStatus(rs.getString("BillStatus"));
                    bill.setCustomerId(rs.getInt("CustomerIdBill"));
                    bill.setSalerId(rs.getInt("SalerID"));
                    bill.setShipperId(rs.getInt("ShipperID"));
                    bill.setAddress(new HoangAnhCustomEntity.Address(rs.getInt("AddressID"),
                            rs.getInt("UserIDForAddress"),
                            rs.getString("UserCountry"),
                            rs.getString("UserTinhThanhPho"),
                            rs.getString("UserQuanHuyen"),
                            rs.getString("UserPhuongXa"),
                            rs.getString("UserAddressDetails"),
                            rs.getString("UserAddressNote")));
                    bill.setShipMethod(new HoangAnhCustomEntity.ShipMethod(rs.getInt("ShipMethodID"), rs.getString("ShipMethodName")));
                    bill.setPayment(new HoangAnhCustomEntity.PaymentMethod(rs.getInt("PaymentMethodID"), rs.getString("PaymentMethodName")));
                    bill.setIsFeedbacked(rs.getBoolean("Feedback"));
                    bill.setIsCanceled(rs.getBoolean("Cancel"));

                    List<HoangAnhCustomEntity.Order> orderList = getOrderByBillID(rs.getInt("BillID"));
                    bill.setOrder(orderList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return bill;
    }

    public HoangAnhCustomEntity.CustomerInformationForOrderManager getUser(int uid) {
        HoangAnhCustomEntity.CustomerInformationForOrderManager user = new HoangAnhCustomEntity.CustomerInformationForOrderManager();
        String sql = "SELECT \n"
                + "    u.id as uid,\n"
                + "    CONCAT(u.FirstName, ' ', u.Lastname) AS fullName,\n"
                + "    u.PhoneNumber as phone,\n"
                + "    u.Gender as gen,\n"
                + "    u.Email as email\n"
                + "FROM\n"
                + "    defaultdb.user u\n"
                + "WHERE\n"
                + "    u.ID = ?";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, uid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("uid"));
                    user.setFullName(rs.getString("fullName"));
                    user.setMobile(rs.getString("phone"));
                    user.setGender(rs.getString("gen"));
                    user.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return user;
    }

    public HoangAnhCustomEntity.Staff getStaff(int staffId) {
        HoangAnhCustomEntity.Staff staff = null;
        String sql = "SELECT \n"
                + "    u.id as staffId,\n"
                + "    CONCAT(u.FirstName, ' ', u.Lastname) AS fullName,\n"
                + "    u.PhoneNumber as phone\n"
                + "FROM\n"
                + "    defaultdb.user u\n"
                + "WHERE\n"
                + "    u.ID = ?";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = new HoangAnhCustomEntity.Staff(rs.getInt("staffId"), rs.getString("fullName"), rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return staff;
    }

    public boolean cancelBill(int bid, boolean status) {
        String sql = "UPDATE Bills SET isCanceledPending = ? WHERE ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {

            pss.setBoolean(1, status); // Set the value for isCanceledPending
            pss.setInt(2, bid);        // Set the ID for the WHERE clause

            int rowsAffected = pss.executeUpdate();
            return rowsAffected > 0; // If any rows were affected, the update was successful

        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead for production code
        }

        return false;
    }

    public String formateDateTime(java.sql.Timestamp t) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        return dateFormat.format(t);
    }

    public String getSaleEmailByBillId(int bid) {
        String email = null;
        String sql = "SELECT\n"
                + "	u.email\n"
                + "FROM\n"
                + "	Bills b\n"
                + "JOIN User u ON b.SalerID = u.id\n"
                + "WHERE b.ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, bid);        // Set the ID for the WHERE clause

            try (ResultSet rs = pss.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead for production code
        }

        return email;
    }

    public static void main(String[] args) {
        MyOrderDAO d = new MyOrderDAO();
        System.out.println(d.getBillByUid(1));
    }

}
