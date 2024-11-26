package dal;

import dal.codebase.DBContext;
import entity.HoangAnhCustomEntity;
import entity.codebaseOld.Product;
import entity.codebaseOld.User;
import entity.codebaseNew.Address;
import entity.codebaseOld.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public boolean saveStatusFeedback(int fid, String status) {
        String sql = "UPDATE defaultdb.feedback\n"
                + "SET status = ?\n"
                + "WHERE ID = ?";
        try {
            connection = new DBContext().getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, fid);

            int result = ps.executeUpdate();

            if (result > 1) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Tag> getTagListByProductId(int pid) throws SQLException {
        List<Tag> tList = new ArrayList<>();
        String sql = "SELECT \n"
                + "    t.ID as TagID,\n"
                + "    t.Name as TagName,\n"
                + "    t.Color as TagColorCode\n"
                + "FROM\n"
                + "    TagProduct tp\n"
                + "        JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "WHERE\n"
                + "    tp.ProductID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, pid);
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    tList.add(new Tag(rss.getInt("TagID"), rss.getString("TagName"), rss.getString("TagColorCode")));
                }
            }
        }

        return tList;
    }

    //Method to get full list of product for product list page
    //Method to get full list of product for product list page
    public List<HoangAnhCustomEntity.ProductListDTO> getHomepageProductInformation() {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();

        // Base query
        String sql = "SELECT \n"
                + "                    p.ID AS productID,\n"
                + "                    p.Name AS ProductName,\n"
                + "                    p.Price AS ProductPrice,\n"
                + "                    p.ThumbnailImage AS ProductImage,\n"
                + "                    s.Name AS subCategory,\n"
                + "                    s.ID AS subCategoryId,\n"
                + "                    t.ID AS tagID,\n"
                + "                    t.Name AS TagName,\n"
                + "                    t.Color AS TagColorCode,\n"
                + "                    p.Star AS ProductStar,\n"
                + "                    p.status,\n"
                + "                    COUNT(f.ID) AS FeedbackCount\n"
                + "                FROM\n"
                + "                    Product p\n"
                + "                        JOIN\n"
                + "                    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "                        LEFT JOIN\n"
                + "                    TagProduct tp ON tp.ProductID = p.ID\n"
                + "                        JOIN\n"
                + "                    Tag t ON t.ID = tp.TagID AND t.ID = 5\n"
                + "                        LEFT JOIN\n"
                + "                    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "                WHERE p.status = 'Available'\n"
                + "                GROUP BY \n"
                + "                    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "                ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                HoangAnhCustomEntity.ProductListDTO product;

                // Create a new product entry
                product = new HoangAnhCustomEntity.ProductListDTO();
                product.setProductId(productId);
                product.setProductName(rs.getString("ProductName"));
                product.setProductPrice(rs.getDouble("ProductPrice"));
                product.setProductThumb(rs.getString("ProductImage"));
                product.setStar(rs.getDouble("ProductStar"));
                product.setSubCategoryName(rs.getString("subCategory"));
                product.setCountFeedBack(rs.getInt("FeedbackCount"));
                product.setSubCategoryId(rs.getInt("subCategoryId"));
                product.setStatus(rs.getString("status"));
                product.setTagList(getTagListByProductId(productId));
                product.setSoldItem(getSoldItemByProductId(productId));

                productList.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return productList;
    }

    public int getSoldItemByProductId(int id) {
        String sql = "SELECT SUM(Quantity) AS Sold FROM defaultdb.order WHERE ProductID = ?;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id); // Set the ProductID parameter

            ResultSet rss = pstm.executeQuery();

            if (rss.next()) {
                return rss.getInt("Sold"); // Use rss instead of rs
            }
        } catch (Exception e) {
            e.printStackTrace(); // Optional: add this to see any exceptions
        }
        return -1; // Return -1 if no result found or exception occurred
    }

    public List<Integer> getHotProductId() throws SQLException {
        List<Integer> listP = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.ID\n"
                + "FROM \n"
                + "    defaultdb.order o \n"
                + "JOIN \n"
                + "    Product p ON p.ID = o.ProductID \n"
                + "GROUP BY \n"
                + "    p.ID \n"
                + "HAVING \n"
                + "    SUM(o.Quantity) > 10;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql); ResultSet rss = pss.executeQuery()) {
            while (rss.next()) {
                listP.add(rss.getInt("ID"));
            }
        }

        return listP;
    }

    public List<HoangAnhCustomEntity.ProductListDTO> getHotProducts() {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();

        // Base query
        String sql = "SELECT\n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.status,\n"
                + "    COUNT(f.ID) AS FeedbackCount\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "WHERE\n"
                + "    p.ID = ? AND p.status = 'Available'\n"
                + "GROUP BY p.ID , p.Name , p.Price , p.ThumbnailImage , s.Name , p.Star\n"
                + "ORDER BY p.price DESC;";

        try {
            List<Integer> pid = getHotProductId();
            for (Integer i : pid) {
                try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
                    pss.setInt(1, i);
                    try (ResultSet rss = pss.executeQuery()) {
                        while (rss.next()) {
                            int productId = rss.getInt("productID");
                            HoangAnhCustomEntity.ProductListDTO product;

                            // Create a new product entry
                            product = new HoangAnhCustomEntity.ProductListDTO();
                            product.setProductId(productId);
                            product.setProductName(rss.getString("ProductName"));
                            product.setProductPrice(rss.getDouble("ProductPrice"));
                            product.setProductThumb(rss.getString("ProductImage"));
                            product.setStar(rss.getDouble("ProductStar"));
                            product.setSubCategoryName(rss.getString("subCategory"));
                            product.setCountFeedBack(rss.getInt("FeedbackCount"));
                            product.setSubCategoryId(rss.getInt("subCategoryId"));
                            product.setStatus(rss.getString("status"));
                            product.setTagList(getTagListByProductId(productId));
                            product.setSoldItem(getSoldItemByProductId(productId));

                            productList.add(product);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<HoangAnhCustomEntity.ProductListDTO> getProductsByTag(String tag) {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.ProductListDTO> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "                    p.ID AS productID,\n"
                + "                    p.Name AS ProductName,\n"
                + "                    p.Price AS ProductPrice,\n"
                + "                    p.ThumbnailImage AS ProductImage,\n"
                + "                    s.Name AS subCategory,\n"
                + "                    s.ID AS subCategoryId,\n"
                + "                    t.ID AS tagID,\n"
                + "                    t.Name AS TagName,\n"
                + "                    t.Color AS TagColorCode,\n"
                + "                    p.Star AS ProductStar,\n"
                + "                    p.status,\n"
                + "                    COUNT(f.ID) AS FeedbackCount\n"
                + "                FROM\n"
                + "                    Product p\n"
                + "                        JOIN\n"
                + "                    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "                        LEFT JOIN\n"
                + "                    TagProduct tp ON tp.ProductID = p.ID\n"
                + "                        JOIN\n"
                + "                    Tag t ON t.ID = tp.TagID AND t.Name = ?\n"
                + "                        LEFT JOIN\n"
                + "                    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "                WHERE p.status = 'Available'\n"
                + "                GROUP BY \n"
                + "                    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "                ORDER BY p.ID ASC;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, tag);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                HoangAnhCustomEntity.ProductListDTO product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
                } else {
                    // Create a new product entry
                    product = new HoangAnhCustomEntity.ProductListDTO();
                    product.setProductId(productId);
                    product.setProductName(rs.getString("ProductName"));
                    product.setProductPrice(rs.getDouble("ProductPrice"));
                    product.setProductThumb(rs.getString("ProductImage"));
                    product.setStar(rs.getDouble("ProductStar"));
                    product.setSubCategoryName(rs.getString("subCategory"));
                    product.setCountFeedBack(rs.getInt("FeedbackCount"));
                    product.setSubCategoryId(rs.getInt("subCategoryId"));
                    product.setStatus(rs.getString("status"));

                    // Initialize the tag list with the first tag
                    List<Tag> tList = new ArrayList<>();
                    tList.add(new Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
                    product.setTagList(tList);

                    product.setSoldItem(getSoldItemByProductId(productId));

                    // Add the product to the map
                    productMap.put(productId, product);
                }
            }

            // Convert the map values to a list
            productList.addAll(productMap.values());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return productList;
    }

    public User getUserByID(int userID) {
        User user = null;
        String sql = "SELECT * FROM User WHERE ID = ?";

        try {
            connection = new DBContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<Product> getLatestProducts(int numberOfProducts) {
        List<Product> newProductList = new ArrayList();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * \n"
                    + "FROM Product \n"
                    + "ORDER BY id DESC \n"
                    + "LIMIT ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, numberOfProducts);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("ID"));
                p.setName(rs.getString("Name"));
                p.setDescription(rs.getString("Description"));
                p.setPrice(rs.getDouble("price"));
                p.setThumbnailImage(rs.getString("ThumbnailImage"));
                newProductList.add(p);
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

        return newProductList;
    }

    public List<Address> getAddressesByUserId(int userId) {
        List<Address> addresses = new ArrayList<>();
        String query = "SELECT * FROM Address WHERE userId = ?";
        connection = dbc.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setCountry(resultSet.getString("country"));
                address.setTinh_thanhpho(resultSet.getString("TinhThanhPho"));
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

    public Address getAllAddressesByUserID(int userID) {
        Address address = null;
        String sql = "SELECT * FROM Address WHERE UserID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                address = new Address();
                address.setId(rs.getInt("ID"));
                address.setCountry(rs.getString("Country"));
                address.setTinh_thanhpho(rs.getString("TinhThanhPho"));
                address.setHuyen_quan(rs.getString("QuanHuyen"));
                address.setXa_phuong(rs.getString("PhuongXa"));
                address.setDetails(rs.getString("Details"));
                address.setNote(rs.getString("Note"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return address;
    }

    public static void main(String[] args) {
        DAO d = new DAO();
        System.out.println(d.getHotProducts());
//        System.out.println(d.getSoldItemByProductId(1));
    }
}
