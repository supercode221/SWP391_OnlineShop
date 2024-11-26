/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.codebase;

import entity.HoangAnhCustomEntity;
import dal.MyDAO;
import entity.codebaseOld.Bill;
import entity.codebaseOld.Color;
import entity.codebaseOld.Order;
import entity.codebaseOld.Size;
import entity.codebaseNew.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class OrderDAO extends DBContext {

    private Connection conn;
    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    public OrderDAO() {
        this.conn = getConnection();
    }

    public List<Order> getOrdersByBillId(int billId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order` WHERE BillID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                // Set fields from rs to order
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean createOrder(Order order) {
        String sql = "INSERT INTO `order` (ProductID, Quantity, TotalPrice, ColorID, SizeID, BillID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getProductId());
            stmt.setInt(2, order.getQuantity());
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setInt(4, order.getColorId());
            stmt.setInt(5, order.getSizeId());
            stmt.setInt(6, order.getBillId());

            // Thực thi câu lệnh
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Color getColorByCode(String colorCode) {
        String sql = "SELECT * FROM color WHERE colorCode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, colorCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Color color = new Color();
                color.setColorId(rs.getInt("ID"));
                color.setColorName(rs.getString("Name"));
                color.setColorCode(rs.getString("colorCode"));
                return color;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Size getSizeByName(String sizeName) {
        String sql = "SELECT * FROM size WHERE Size = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sizeName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Size size = new Size();
                size.setSizeId(rs.getInt("ID"));
                size.setSizeName(rs.getString("Size"));
                return size;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int generateUniqueId() throws SQLException {
        Random random = new Random();
        int newId;

        do {
            newId = random.nextInt(1000000);
        } while (checkIdExists(newId));

        return newId;
    }

    private boolean checkIdExists(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bills WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public int createBill(Bill bill) {

        String sql = "INSERT INTO `bills` (TotalPrice, Status, CustomerID, AddressID, ShipMethodID, PaymentMethodID, ID, SalerID, ShipperID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int uniqueId = generateUniqueId();
            int index = 1;
            stmt.setDouble(index++, bill.getTotalPrice());
            stmt.setString(index++, bill.getStatus());
            stmt.setInt(index++, bill.getCustomerId());
            stmt.setInt(index++, bill.getAddressId());
            stmt.setInt(index++, bill.getShipMethodId());
            stmt.setInt(index++, bill.getPaymentMethodId());
            stmt.setInt(index++, uniqueId);
            stmt.setInt(index++, bill.getSalerId());
            stmt.setInt(index++, bill.getShipperId());
            stmt.executeUpdate();
            return uniqueId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean deleteOrderByBillId(int billId) {
        String sql = "DELETE FROM `order` WHERE BillID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBillById(int billId) {
        String sql = "DELETE FROM `bills` WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reduceQuantity(int productId, int colorId, int sizeId, int quantityToSubtract) throws SQLException {
        String query = "UPDATE product_color_size SET Quantity = Quantity - ? "
                + "WHERE ProductID = ? AND ColorID = ? AND SizeID = ? AND Quantity >= ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, quantityToSubtract);
            statement.setInt(2, productId);
            statement.setInt(3, colorId);
            statement.setInt(4, sizeId);
            statement.setInt(5, quantityToSubtract);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public int deleteProductFromCartByProductIdAndUserId(int productId, int userId, int colorId, int sizeId) {
        // get cart id

        // find in cartproduct table, where that cart id and product id found
        // delete it by add deleted logid with query update
        // query "DELETE FROM CartProduct WHERE CartID = ? AND ProductID = ?"
        int effectedRow = -1;
        try {
            // SQL query to get cart ID and product details
            String sql = ""
                    + "DELETE cp "
                    + "FROM CartProduct cp "
                    + "JOIN Cart c ON cp.CartID = c.ID "
                    + "JOIN Color col ON cp.ColorID = col.ID "
                    + "JOIN Size s ON cp.SizeID = s.ID "
                    + "WHERE c.UserID = ? "
                    + "AND cp.ProductID = ? "
                    + "AND col.ID = ? "
                    + "AND s.ID = ? "
                    + ";";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, colorId);
            ps.setInt(4, sizeId);
            effectedRow = ps.executeUpdate();
        } catch (SQLException e) {
            effectedRow = -1;
            e.printStackTrace();
        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (ps != null) try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return effectedRow;
    }

    public HoangAnhCustomEntity.StaffWithRole getRandomStaffByRoleId(int roleId) {
        List<HoangAnhCustomEntity.StaffWithRole> staffList = new ArrayList<>();

        String sql = "SELECT\n"
                + "    u.ID AS StaffId,\n"
                + "    CONCAT(u.LastName, ' ', u.Firstname) AS StaffName,\n"
                + "    u.PhoneNumber AS StaffPhone,\n"
                + "    u.Email AS StaffEmail,\n"
                + "    r.ID AS StaffRoleId,\n"
                + "    r.Name AS StaffRoleName\n"
                + "FROM\n"
                + "    `defaultdb`.`user` u\n"
                + "    JOIN role r ON r.ID = u.RoleID\n"
                + "WHERE u.RoleID = ?";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, roleId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoangAnhCustomEntity.StaffWithRole s = new HoangAnhCustomEntity.StaffWithRole();
                    s.setId(rs.getInt("StaffId"));
                    s.setName(rs.getString("StaffName"));
                    s.setPhoneNumber(rs.getString("StaffPhone"));
                    s.setEmail(rs.getString("StaffEmail"));
                    s.setRole(new Role(rs.getInt("StaffRoleId"), rs.getString("StaffRoleName")));

                    staffList.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }

        // Select a random staff member if the list is not empty
        if (!staffList.isEmpty()) {
            Random random = new Random();
            return staffList.get(random.nextInt(staffList.size()));
        }

        return null; // Return null if no staff found for the given roleId
    }

    public static void main(String[] args) {
        System.out.println(new OrderDAO().getRandomStaffByRoleId(4));
    }
}
