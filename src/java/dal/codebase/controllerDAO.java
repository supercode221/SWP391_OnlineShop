/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.codebase;

import entity.codebaseOld.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class controllerDAO {

    DBContext dbc = new DBContext();
    Connection connection = null;
    //Tìm kiếm sản phẩm theo tên

    public List<Product> searchProductByName(String name) throws SQLException {
        List<Product> products = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT p.*, s.Size, c.Name as Color, pcs.Quantity "
                    + "FROM Product_Color_Size pcs "
                    + "JOIN Product p ON p.ID = pcs.ProductID "
                    + "JOIN Size s ON pcs.SizeID = s.ID "
                    + "JOIN Color c ON c.ID = pcs.ColorID "
                    + "WHERE p.Name LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + name + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setSubCategoryId(rs.getInt(2));
                p.setPrice(rs.getDouble(3));
                p.setStar(rs.getFloat(4));
                p.setName(rs.getString(5));
                p.setDescription(rs.getString(6));
                p.setThumbnailImage(rs.getString(7));
                p.setStatus(rs.getString(8));
                products.add(p);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return products;
    }

    //Lọc sản phẩm theo giá
    public List<Product> getProductFilterPrice(double priceLow, double priceHigh) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT \n"
                    + "	p.*,\n"
                    + "    s.Size,\n"
                    + "    c.Name as Color,\n"
                    + "    pcs.Quantity\n"
                    + "FROM \n"
                    + "	Product_Color_Size pcs\n"
                    + "JOIN \n"
                    + "	Product p ON p.ID = pcs.ProductID\n"
                    + "JOIN\n"
                    + "    Size s ON pcs.SizeID = s.ID\n"
                    + "JOIN\n"
                    + "	Color c ON c.ID = pcs.ColorID\n"
                    + "WHERE p.Price >= ? AND p.Price <= ? \n"
                    + "ORDER BY p.ID ASC";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDouble(1, priceLow);
            stm.setDouble(2, priceHigh);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setSubCategoryId(rs.getInt(2));
                p.setPrice(rs.getDouble(3));
                p.setStar(rs.getFloat(4));
                p.setName(rs.getString(5));
                p.setDescription(rs.getString(6));
                p.setThumbnailImage(rs.getString(7));
                p.setStatus(rs.getString(8));
                products.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return products;
    }

    // Lấy danh sách sản phẩm theo phân trang
    public List<Product> getListByPage(List<Product> productList, int start, int end) {
        List<Product> productsPerPage = new ArrayList<>();
        for (int i = start; i < end; i++) {
            productsPerPage.add(productList.get(i));
        }
        return productsPerPage;
    }

    public static void main(String[] args) {
        controllerDAO dao = new controllerDAO();

        try {
            // Tạo dữ liệu mẫu để test
            System.out.println("Test tìm kiếm sản phẩm theo tên:");
            List<Product> productsByName = dao.searchProductByName("T-shirt");
            printProductList(productsByName);

            System.out.println("\nTest lọc sản phẩm theo khoảng giá:");
            List<Product> productsByPrice = dao.getProductFilterPrice(100.0, 500.0);
            printProductList(productsByPrice);

            System.out.println("\nTest phân trang:");
            int start = 0; // Số bắt đầu của trang
            int end = 3; // Số sản phẩm trên mỗi trang (giả sử 3 sản phẩm cho mỗi trang)
            List<Product> paginatedProducts = dao.getListByPage(productsByPrice, start, end);
            printProductList(paginatedProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tiện ích để in danh sách sản phẩm
    public static void printProductList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
            return;
        }
        for (Product p : products) {
            System.out.println("Product ID: " + p.getId() + ", Name: " + p.getName() + ", Price: " + p.getPrice());
        }
    }
}
