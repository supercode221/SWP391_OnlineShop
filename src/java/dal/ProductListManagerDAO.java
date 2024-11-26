/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static codebase.ImageSaveHandler.ImageSaver.saveBase64toImageFile;
import codebase.random.Random;
import entity.ThanhCustomEntity;
import entity.ThanhCustomEntity.Color;
import entity.ThanhCustomEntity.Size;
import entity.ThanhCustomEntity.Tag;
import dal.codebase.DBContext;
import entity.HoangAnhCustomEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ProductListManagerDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    Random ran = new Random();

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private void log(Level level, String msg, Throwable e) {
        this.logger.log(level, msg, e);
    }

    public static void main(String[] args) {
        ProductListManagerDAO managerDAO = new ProductListManagerDAO();
//        System.out.println(managerDAO.getProductListInformationManage());
//        System.out.println("\n");
        System.out.println(managerDAO.getProductIdToAdd());
    }

    public boolean addNewProduct(String fileThumbnailpath, String buildThumbnailPath, int pid, int cateId, String status, String name, String des, double price, String thumbnail) {
        String sql = "INSERT INTO product(ID, SubCategoryID, Price, Name, Description, ThumbnailImage, status) VALUES (?, ?, ?, ?, ?, ?, ?);";
        String filePath;

        filePath = saveBase64toImageFile(thumbnail, fileThumbnailpath, "Thumbnail_Product_" + ran.getString(6, ""));
        saveBase64toImageFile(thumbnail, buildThumbnailPath, "Thumbnail_Product_" + ran.getString(6, ""));

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, pid);
            pss.setInt(2, cateId);
            pss.setDouble(3, price);
            pss.setString(4, name);
            pss.setString(5, des);
            pss.setString(6, filePath);
            pss.setString(7, status);

            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to add product !", e);
        }
        return false;
    }

    public int getProductIdToAdd() {
        int pid = -1;
        String sql = "SELECT MAX(ID) AS ID FROM product;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql); ResultSet rss = pss.executeQuery()) {
            if (rss.next()) {
                return rss.getInt("ID") + 1;
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to get product id to add new!", e);
        }

        return pid;
    }

    public boolean addAttrForProduct(int pid, int sid, int cid, int quantity) {
        String sql = "INSERT INTO product_color_size(ProductID, ColorID, SizeID, Quantity) VALUES (?, ?, ?, ?);";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.setInt(2, cid);
            ps.setInt(3, sid);
            ps.setInt(4, quantity);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                this.log(Level.INFO, "Add attribute for pid: " + pid + " success!", null);
                return true;
            } else {
                this.log(Level.WARNING, "Add attribute for pid: " + pid + " failed!", null);
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to Add attribute for pid: " + pid, e);
        }

        return false;
    }

    public List<entity.ThanhCustomEntity.Size> getAllSize() {
        List<entity.ThanhCustomEntity.Size> sList = new ArrayList<>();
        String sql = "SELECT * FROM defaultdb.size;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql); ResultSet rss = pss.executeQuery()) {
            while (rss.next()) {
                sList.add(new Size(rss.getInt("ID"), rss.getString("Size")));
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to get size list!", e);
        }

        return sList;
    }

    public List<entity.ThanhCustomEntity.Color> getAllColor() {
        List<entity.ThanhCustomEntity.Color> cList = new ArrayList<>();
        String sql = "SELECT * FROM defaultdb.color;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql); ResultSet rss = pss.executeQuery()) {
            while (rss.next()) {
                cList.add(new Color(rss.getInt("ID"), rss.getString("Name"), rss.getString("ColorCode")));
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to get color list!", e);
        }

        return cList;
    }

    public int getIdSubImageToAdd() {
        String sql = "SELECT MAX(ID) as ID FROM productimage;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql); ResultSet rss = pss.executeQuery()) {
            if (rss.next()) {
                return rss.getInt("ID") + 1;
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to get sub-image id to add!", e);
        }

        return -1;
    }

    public boolean addProductSubImage(String buildPathSubImage, String filePathSubImage, int pid, String linkImage) {
        String sql = "INSERT INTO productimage(ProductID, Link) VALUES (?, ?);";
        String filePath;

        filePath = saveBase64toImageFile(linkImage, filePathSubImage, "SubImage_" + getIdSubImageToAdd() + "_Product_" + ran.getString(6, ""));
        saveBase64toImageFile(linkImage, buildPathSubImage, "SubImage_" + getIdSubImageToAdd() + "_Product_" + ran.getString(6, ""));

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, pid);
            pss.setString(2, filePath);

            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to add product sub-image for ProductID: " + pid + " !", e);
        }
        return false;
    }

    public boolean deleteSubImage(int pid, int subImageId) {
        String sql = "DELETE FROM productimage\n"
                + "WHERE ProductID = ? AND ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.setInt(2, subImageId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                this.log(Level.INFO, "Delete sub-image for pid: " + pid + " success!", null);
                return true;
            } else {
                this.log(Level.WARNING, "Delete sub-image for pid: " + pid + " failed!", null);
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to delete sub-image for pid: " + pid, e);
        }

        return false;
    }

    public boolean updateProductImage(
            String buildPathSubImage, String buildPathThumbnail,
            String filePathThumbnail, String filePathSubImage,
            int pid, String linkImage, String type, int subImageId) {
        String sql;

        String filePath;

        if (type.equalsIgnoreCase("thumbnail")) {
            sql = "UPDATE product\n"
                    + "SET ThumbnailImage = ?\n"
                    + "WHERE ID = ?";

            filePath = saveBase64toImageFile(linkImage, filePathThumbnail, "Thumbnail_Product_" + ran.getString(6, ""));
            saveBase64toImageFile(linkImage, buildPathThumbnail, "Thumbnail_Product_" + ran.getString(6, ""));
        } else {
            sql = "UPDATE productimage\n"
                    + "SET Link = ?\n"
                    + "WHERE ProductID = ? AND ID = ?";

            filePath = saveBase64toImageFile(linkImage, filePathSubImage, "SubImage_" + subImageId + "_Product_" + ran.getString(6, ""));
            saveBase64toImageFile(linkImage, buildPathSubImage, "SubImage_" + subImageId + "_Product_" + ran.getString(6, ""));
        }

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, filePath);
            pss.setInt(2, pid);

            if (!type.equalsIgnoreCase("thumbnail")) {
                pss.setInt(3, subImageId);
            }

            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to update product media " + type + " for ProductID: " + pid + " !", e);
        }
        return false;
    }

    public boolean deleteAttrForProduct(int pid, int sid, int cid) {
        String sql = "DELETE FROM product_color_size\n"
                + "WHERE ColorID = ? AND ProductID = ? AND SizeID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, pid);
            ps.setInt(3, sid);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                this.log(Level.INFO, "Delete attribute for pid: " + pid + " success!", null);
                return true;
            } else {
                this.log(Level.WARNING, "Delete attribute for pid: " + pid + " failed!", null);
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to delete attribute for pid: " + pid, e);
        }

        return false;
    }

    public boolean updateAttrForProduct(int pid, int sid, int cid, int quantity) {
        String sql = "UPDATE product_color_size\n"
                + "SET Quantity = ?\n"
                + "WHERE ColorID = ? AND ProductID = ? AND SizeID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, cid);
            ps.setInt(3, pid);
            ps.setInt(4, sid);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                this.log(Level.INFO, "Update attribute for pid: " + pid + " success!", null);
                return true;
            } else {
                this.log(Level.WARNING, "Update attribute for pid: " + pid + " failed!", null);
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to update attribute for pid: " + pid, e);
        }

        return false;
    }

    public boolean deleteTagForProduct(int pid, int tid) {
        String sql = "DELETE FROM tagproduct\n"
                + "WHERE TagID = ? AND ProductID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, tid);
            ps.setInt(2, pid);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                this.log(Level.INFO, "Delete tid + " + tid + " for pid: " + pid + " success!", null);
                return true;
            } else {
                this.log(Level.WARNING, "Delete tid + " + tid + " for pid: " + pid + " failed!", null);
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to delete tag id: " + tid + " for pid: " + pid, e);
        }

        return false;
    }

    public boolean addTagForProduct(int pid, int tid) {
        String sql = "INSERT INTO tagproduct(TagID, ProductID) VALUES (?, ?);";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, tid);
            ps.setInt(2, pid);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                this.log(Level.INFO, "Add tid + " + tid + " for pid: " + pid + " success!", null);
                return true;
            } else {
                this.log(Level.WARNING, "Add tid + " + tid + " for pid: " + pid + " failed!", null);
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Add to delete tag id: " + tid + " for pid: " + pid, e);
        }

        return false;
    }

    public boolean updateOverallInfomation(int pid, String name, String des, double price, int cateId, String status) {
        String sql = "UPDATE product\n"
                + "SET SubCategoryID = ?,\n"
                + "	Price = ?,\n"
                + "    Name = ?,\n"
                + "    Description = ?,\n"
                + "    status = ?\n"
                + "WHERE ID = ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cateId);
            ps.setDouble(2, price);
            ps.setString(3, name);
            ps.setString(4, des);
            ps.setString(5, status);
            ps.setInt(6, pid);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                this.log(Level.INFO, "Save overall information for pid: " + pid + " success!", null);
                return true;
            } else {
                this.log(Level.WARNING, "Save overall information for pid: " + pid + " failed!", null);
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to save overall information for pid: " + pid, e);
        }

        return false;
    }

    public HoangAnhCustomEntity.ProductListDTO getProductDetailInformationManageById(int pid) {
        HoangAnhCustomEntity.ProductListDTO product = new HoangAnhCustomEntity.ProductDetailDTO();

        // Base query
        String sql = "SELECT\n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    p.Description,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS TagName,\n"
                + "    t.Color AS TagColorCode,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.status,\n"
                + "    COUNT(f.ID) AS FeedbackCount\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    TagProduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID\n"
                + "WHERE\n"
                + "    p.ID = ?\n"
                + "GROUP BY p.ID , p.Name , p.Price , p.ThumbnailImage , s.Name , t.Name , t.Color , p.Star , t.ID\n"
                + "LIMIT 1;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);
            rs = ps.executeQuery();

            while (rs.next()) {
                product.setProductId(pid);
                product.setProductName(rs.getString("ProductName"));
                product.setProductPrice(rs.getDouble("ProductPrice"));
                product.setProductThumb(rs.getString("ProductImage"));
                product.setStar(rs.getDouble("ProductStar"));
                product.setSubCategoryName(rs.getString("subCategory"));
                product.setCountFeedBack(rs.getInt("FeedbackCount"));
                product.setSubCategoryId(rs.getInt("subCategoryId"));
                product.setStatus(rs.getString("status"));
                product.setDes(rs.getString("Description"));

                // Initialize the tag list with the first tag
                List<entity.codebaseOld.Tag> tList = getProductTagListByID(pid);
                product.setTagList(tList);

                product.setSoldItem(getSoldItemByProductId(pid));

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
        return product;
    }

    public boolean updateProductStatus(int pid, String status) {
        String sql = "UPDATE product\n"
                + "SET status = ?\n"
                + "WHERE ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, status);
            pss.setInt(2, pid);

            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to save status for pid: " + pid + " to " + status, e);
        }

        return false;
    }

    public boolean updateProductCategory(int pid, int cateId) {
        String sql = "UPDATE product\n"
                + "SET SubCategoryID = ?\n"
                + "WHERE ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, cateId);
            pss.setInt(2, pid);

            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to save category id for pid: " + pid + " to " + cateId, e);
        }

        return false;
    }

    public List<String> getAllStatus() {
        List<String> staList = new LinkedList<>();
        String sql = "SELECT * FROM status WHERE Type = 'Product'";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                staList.add(rs.getString("Name"));
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Can not fetch all product status", e);
        }

        return staList;
    }

    //Method to get full list of product for product list page
    public List<HoangAnhCustomEntity.ProductListDTO> getProductListInformationManage() {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.ProductListDTO> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS TagName,\n"
                + "    t.Color AS TagColorCode,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.status,\n"
                + "    COUNT(f.ID) AS FeedbackCount\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    TagProduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "GROUP BY \n"
                + "    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                HoangAnhCustomEntity.ProductListDTO product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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
                    List<entity.codebaseOld.Tag> tList = new ArrayList<>();
                    tList.add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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

    public List<HoangAnhCustomEntity.ProductListDTO> getProductListInformationByCateId(int cateId) {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.ProductListDTO> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS TagName,\n"
                + "    t.Color AS TagColorCode,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.status,\n"
                + "    COUNT(f.ID) AS FeedbackCount\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    TagProduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "WHERE s.ID = ?\n"
                + "GROUP BY \n"
                + "    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cateId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                HoangAnhCustomEntity.ProductListDTO product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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
                    List<entity.codebaseOld.Tag> tList = new ArrayList<>();
                    tList.add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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

    public List<HoangAnhCustomEntity.ProductListDTO> getProductListInformationByTagId(int tagId) {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.ProductListDTO> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS TagName,\n"
                + "    t.Color AS TagColorCode,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.status,\n"
                + "    COUNT(f.ID) AS FeedbackCount\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    TagProduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "WHERE t.ID = ?\n"
                + "GROUP BY \n"
                + "    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, tagId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                HoangAnhCustomEntity.ProductListDTO product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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

                    product.setTagList(getProductTagListByID(productId));

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

    public List<entity.codebaseOld.Tag> getProductTagListByID(int pid) {
        List<entity.codebaseOld.Tag> tList = new ArrayList<>();
        String sql = "SELECT \n"
                + "    t.ID AS tagID, t.Name AS TagName, t.Color AS TagColorCode\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    TagProduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "WHERE\n"
                + "    p.ID = ?\n"
                + "GROUP BY p.ID , p.Name , p.Price , p.ThumbnailImage , t.Name , t.Color , p.Star , t.ID\n"
                + "ORDER BY p.ID;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, pid);
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    tList.add(new entity.codebaseOld.Tag(rss.getInt("tagID"), rss.getString("TagName"), rss.getString("TagColorCode")));
                }
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to get tag list for product id " + pid, e);
        }

        return tList;
    }

    public List<HoangAnhCustomEntity.ProductListDTO> getProductListInformationByStatus(String status) {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.ProductListDTO> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS TagName,\n"
                + "    t.Color AS TagColorCode,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.status,\n"
                + "    COUNT(f.ID) AS FeedbackCount\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    TagProduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "WHERE p.status = ?\n"
                + "GROUP BY \n"
                + "    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                HoangAnhCustomEntity.ProductListDTO product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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
                    List<entity.codebaseOld.Tag> tList = new ArrayList<>();
                    tList.add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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

    public List<HoangAnhCustomEntity.ProductListDTO> getProductListInformationByName(String name) {
        List<HoangAnhCustomEntity.ProductListDTO> productList = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.ProductListDTO> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS TagName,\n"
                + "    t.Color AS TagColorCode,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.status,\n"
                + "    COUNT(f.ID) AS FeedbackCount\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    SubCategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    TagProduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    Tag t ON t.ID = tp.TagID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                + "WHERE p.Name like ?\n"
                + "GROUP BY \n"
                + "    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                HoangAnhCustomEntity.ProductListDTO product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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
                    List<entity.codebaseOld.Tag> tList = new ArrayList<>();
                    tList.add(new entity.codebaseOld.Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
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

    //Method to get full list of product for product list page
    public List<ThanhCustomEntity.Product> getProductListInformation() {
        List<ThanhCustomEntity.Product> productList = new LinkedList<>();
        Map<Integer, ThanhCustomEntity.Product> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Description AS pDes,\n"
                + "    p.status AS pStatus,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    s.OriginalType AS originalType,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS TagName,\n"
                + "    t.Color AS TagColorCode\n"
                + "FROM\n"
                + "    Product p\n"
                + "        JOIN\n"
                + "    subcategory s ON s.ID = p.SubCategoryID\n"
                + "        LEFT JOIN\n"
                + "    tagproduct tp ON tp.ProductID = p.ID\n"
                + "        LEFT JOIN\n"
                + "    tag t ON t.ID = tp.TagID\n"
                + "        LEFT JOIN\n"
                + "    feedback f ON f.ProductID = p.ID\n"
                + "GROUP BY \n"
                + "    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                + "ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                ThanhCustomEntity.Product product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
                } else {
                    // Create a new product entry
                    product = new ThanhCustomEntity.Product();
                    product.setId(productId);
                    product.setName(rs.getString("ProductName"));
                    product.setPrice(rs.getDouble("ProductPrice"));
                    product.setThumbnail(rs.getString("ProductImage"));
                    product.setStar(rs.getDouble("ProductStar"));
                    product.setSubCate(new ThanhCustomEntity.Subcategory(rs.getInt("subCategoryId"), rs.getString("subCategory"), rs.getString("originalType")));
                    product.setDes(rs.getString("pDes"));
                    product.setStatus(rs.getString("pStatus"));

                    // Initialize the tag list with the first tag
                    List<Tag> tList = new ArrayList<>();
                    tList.add(new Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
                    product.setTagList(tList);

                    product.setQuantityTracker(getProductAttributeById(productId));
                    product.setImageList(getProductImageById(productId));

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

    public List<ThanhCustomEntity.Product> searchProductListInformationbySubcatogoryandProductName(String search, String subCategory) {
        List<ThanhCustomEntity.Product> productList = new LinkedList<>();
        Map<Integer, ThanhCustomEntity.Product> productMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    p.ID AS productID,\n"
                + "    p.Name AS ProductName,\n"
                + "    p.Description AS pDes,\n"
                + "    p.status AS pStatus,\n"
                + "    p.Price AS ProductPrice,\n"
                + "    p.Star AS ProductStar,\n"
                + "    p.ThumbnailImage AS ProductImage,\n"
                + "    s.Name AS subCategory,\n"
                + "    s.ID AS subCategoryId,\n"
                + "    s.OriginalType AS originalType,\n"
                + "    t.ID AS tagID,\n"
                + "    t.Name AS tagName,\n"
                + "    t.Color AS tagColorCode\n"
                + "FROM\n"
                + "    product p\n"
                + "    JOIN subcategory s ON s.ID = p.subcategoryID\n"
                + "    LEFT JOIN tagproduct tp ON tp.productID = p.ID\n"
                + "    LEFT JOIN tag t ON t.ID = tp.tagID\n"
                + "    LEFT JOIN feedback f ON f.productID = p.ID\n"
                + "    where s.name like ? and p.name like ?\n"
                + "GROUP BY \n"
                + "    p.ID, p.Name, p.Description, p.status, p.Price, p.Star, p.ThumbnailImage, \n"
                + "    s.Name, s.ID, s.OriginalType, t.ID, t.Name, t.Color\n"
                + "ORDER BY p.ID;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + subCategory + "%");
            ps.setString(2, "%" + search + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productID");
                ThanhCustomEntity.Product product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productId)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productId);
                    product.getTagList().add(new Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
                } else {
                    // Create a new product entry
                    product = new ThanhCustomEntity.Product();
                    product.setId(productId);
                    product.setName(rs.getString("ProductName"));
                    product.setPrice(rs.getDouble("ProductPrice"));
                    product.setThumbnail(rs.getString("ProductImage"));
                    product.setStar(rs.getDouble("ProductStar"));
                    product.setSubCate(new ThanhCustomEntity.Subcategory(rs.getInt("subCategoryId"), rs.getString("subCategory"), rs.getString("originalType")));
                    product.setDes(rs.getString("pDes"));
                    product.setStatus(rs.getString("pStatus"));

                    // Initialize the tag list with the first tag
                    List<Tag> tList = new ArrayList<>();
                    tList.add(new Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
                    product.setTagList(tList);

                    product.setQuantityTracker(getProductAttributeById(productId));
                    product.setImageList(getProductImageById(productId));

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
            }
        }
        return productList;
    }

    public List<ThanhCustomEntity.QuantityTracker> getProductAttributeById(int productId) {
        List<ThanhCustomEntity.QuantityTracker> pdao = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.ID AS ProductId,\n"
                + "    c.ID AS ColorID,\n"
                + "    c.Name AS ColorName,\n"
                + "    c.ColorCode AS ColorCode,\n"
                + "    s.ID AS SizeID,\n"
                + "    s.Size AS SizeName,\n"
                + "    pcs.Quantity AS ProductQuantity\n"
                + "FROM\n"
                + "    Product_Color_Size pcs\n"
                + "        JOIN\n"
                + "    Product p ON p.ID = pcs.ProductID\n"
                + "        JOIN\n"
                + "    Color c ON c.ID = pcs.ColorID\n"
                + "        JOIN\n"
                + "    Size s ON s.ID = pcs.SizeID\n"
                + "WHERE\n"
                + "    p.ID = ?\n"
                + "ORDER BY p.ID;";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.QuantityTracker q = new ThanhCustomEntity.QuantityTracker();
                    q.setColor(new Color(rs.getInt("ColorID"), rs.getString("ColorName"), rs.getString("ColorCode")));
                    q.setSize(new Size(rs.getInt("SizeID"), rs.getString("SizeName")));
                    q.setQuantity(rs.getInt("ProductQuantity"));
                    pdao.add(q);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }

        return pdao;
    }

    // Method to get all product image links by ID
    public List<ThanhCustomEntity.ProductImage> getProductImageById(int productId) {
        List<ThanhCustomEntity.ProductImage> imgList = new ArrayList<>();
        String sql = "SELECT * FROM ProductImage WHERE ProductID = ?;";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.ProductImage img = new ThanhCustomEntity.ProductImage();
                    img.setId(rs.getInt("ID"));
                    img.setLink(rs.getString("Link"));
                    img.setProductId(productId);
                    imgList.add(img);  // Add image to list
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }

        return imgList;
    }

}
