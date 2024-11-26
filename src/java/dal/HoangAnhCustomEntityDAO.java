/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import codebase.EmailSender.Email;
import entity.HoangAnhCustomEntity;
import dal.codebase.DBContext;
import entity.codebaseOld.Category;
import entity.codebaseOld.Color;
import entity.codebaseOld.Size;
import entity.codebaseOld.Tag;
import entity.codebaseNew.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Hoang Anh Dep Trai
 */
public class HoangAnhCustomEntityDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public double getMaxPrice() {
        double max = -1;
        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "SELECT MAX(Price) as max FROM product;";
            ps = connection.prepareStatement(checkSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                max = rs.getDouble("max");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }

        return max;
    }

    public double getMinPrice() {
        double min = -1;
        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "SELECT MIN(Price) as min FROM product;";
            ps = connection.prepareStatement(checkSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                min = rs.getDouble("min");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }

        return min;
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
    public List<HoangAnhCustomEntity.ProductListDTO> getProductListInformation() {
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
                + "WHERE p.status = 'Available'\n"
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

    public int countSearchedProduct(String searchProductName, String tag, String category) {
        int totalCount = 0;

        // Base query for counting products
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(DISTINCT p.ID) AS totalCount ");
        query.append("FROM Product p ");
        query.append("JOIN SubCategory s ON s.ID = p.SubCategoryID ");
        query.append("JOIN TagProduct tp ON tp.ProductID = p.ID ");
        query.append("JOIN Tag t ON t.ID = tp.TagID ");
        query.append("WHERE t.Name LIKE ? ");

        // Dynamically add filters if provided
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasTag = tag != null && !tag.isEmpty();
        boolean hasProductName = searchProductName != null && !searchProductName.isEmpty();

        if (hasCategory) {
            query.append("OR s.Name LIKE ? ");
        }
        if (hasProductName) {
            query.append("OR p.Name LIKE ? ");
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(query.toString());

            int paramIndex = 1;
            ps.setString(paramIndex++, "%" + tag + "%"); // Use wildcards for tag

            if (hasCategory) {
                ps.setString(paramIndex++, "%" + category + "%"); // Use wildcards for category
            }
            if (hasProductName) {
                ps.setString(paramIndex++, "%" + searchProductName + "%"); // Use wildcards for product name
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("totalCount");
            }
        } catch (Exception e) {
            // Handle SQL exception properly
            e.printStackTrace(); // Replace with proper logging
        } finally {
            // Close resources in the correct order and check for null
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
                ex.printStackTrace(); // Handle errors while closing resources
            }
        }

        return totalCount;
    }

    //Method to get all details of product by ID
    public HoangAnhCustomEntity.ProductDetailDTO getDetailProductbyId(int productId) {
        HoangAnhCustomEntity.ProductDetailDTO pd = new HoangAnhCustomEntity.ProductDetailDTO();
        Map<Integer, HoangAnhCustomEntity.ProductDetailDTO> productMap = new HashMap<>(); // Map to track products by ID
        List<String> imgList = getProductImageById(productId);  // Assuming this returns a list of image URLs

        try {
            connection = dbc.getConnection();
            String sql = "SELECT \n"
                    + "    p.ID AS productID,\n"
                    + "    p.Name AS ProductName,\n"
                    + "    p.Price AS ProductPrice,\n"
                    + "    p.ThumbnailImage AS ProductImage,\n"
                    + "    p.Description AS ProductDescription,\n"
                    + "    s.Name AS subCategory,\n"
                    + "    t.ID AS tagID,\n"
                    + "    t.Name AS TagName,\n"
                    + "    t.Color AS TagColorCode,\n"
                    + "    p.Star AS ProductStar,\n"
                    + "    COUNT(f.ID) AS FeedbackCount\n"
                    + "FROM\n"
                    + "    Product p\n"
                    + "    JOIN SubCategory s ON s.ID = p.SubCategoryID\n"
                    + "    JOIN TagProduct tp ON tp.ProductID = p.ID\n"
                    + "    JOIN Tag t ON t.ID = tp.TagID\n"
                    + "    LEFT JOIN feedback f ON f.ProductID = p.ID AND f.status = 'show'\n"
                    + "WHERE \n"
                    + "    p.ID = ?\n"
                    + "GROUP BY \n"
                    + "    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star, t.ID\n"
                    + "ORDER BY \n"
                    + "    p.ID;";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);  // Set the product ID
            rs = ps.executeQuery();

            while (rs.next()) {
                int productIdQuery = rs.getInt("productID");
                HoangAnhCustomEntity.ProductDetailDTO product;

                // Check if the product already exists in the map
                if (productMap.containsKey(productIdQuery)) {
                    // Product exists, just add the new tag
                    product = productMap.get(productIdQuery);
                    product.getTagList().add(new Tag(rs.getInt("tagID"), rs.getString("TagName"), rs.getString("TagColorCode")));
                } else {
                    // Create a new product entry
                    product = new HoangAnhCustomEntity.ProductDetailDTO();
                    product.setProductId(productIdQuery);
                    product.setProductName(rs.getString("ProductName"));
                    product.setProductPrice(rs.getDouble("ProductPrice"));
                    product.setProductThumb(rs.getString("ProductImage"));
                    product.setStar(rs.getDouble("ProductStar"));
                    product.setSubCategoryName(rs.getString("subCategory"));
                    product.setCountFeedBack(rs.getInt("FeedbackCount"));
                    product.setProductDescription(rs.getString("ProductDescription"));

                    // Initialize the tag list with the first tag
                    List<Tag> tList = new ArrayList<>();
                    tList.add(new Tag(rs.getInt("tagID"), rs.getString("TagName"), rs.getString("TagColorCode")));
                    product.setTagList(tList);

                    // Add the image list (if required)
                    product.setProductImage(imgList);

                    // Add the product to the map
                    productMap.put(productIdQuery, product);
                }
            }

            // Return the product for the given productId
            pd = productMap.get(productId);  // Retrieve the product from the map

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
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
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }

        return pd;
    }

    //Method to get all product image links by id
    public List getProductImageById(int productId) {
        List imgList = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * FROM ProductImage WHERE ProductID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                imgList.add(rs.getString("Link"));
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
        return imgList;
    }

    // Method to get the total number of products (for pagination metadata)
    public int getTotalProductCount(String[] categories, Double minPrice, Double maxPrice) {
        int totalCount = 0;

        // Base query for counting the products
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) as total FROM Product p ");
        query.append("JOIN SubCategory s ON s.ID = p.SubCategoryID ");

        // Create boolean conditions
        boolean filterCate = categories != null && categories.length > 0 && !categories[0].isEmpty();
        boolean filterPrice = minPrice != null && maxPrice != null;

        // Dynamically add filters if provided
        boolean hasFilter = false;
        if (filterCate) {
            query.append("WHERE s.Name IN (");
            for (int i = 0; i < categories.length; i++) {
                query.append("?");
                if (i < categories.length - 1) {
                    query.append(", ");
                }
            }
            query.append(") ");
            hasFilter = true;
        }

        if (filterPrice) {
            if (hasFilter) {
                query.append("AND p.Price BETWEEN ? AND ? ");
            } else {
                query.append("WHERE p.Price BETWEEN ? AND ? ");
            }
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(query.toString());

            // Set the parameters for category and price filters
            int paramIndex = 1;
            if (filterCate) {
                for (String category : categories) {
                    ps.setString(paramIndex++, category);  // Set each category parameter
                }
            }
            if (filterPrice) {
                ps.setDouble(paramIndex++, minPrice);
                ps.setDouble(paramIndex++, maxPrice);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("total");
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
        return totalCount;
    }

    //Method to get product attribute  by product ID
    public HoangAnhCustomEntity.ProductDetailAddOnAttribute getProductAttributeById(int productId) {
        HoangAnhCustomEntity.ProductDetailAddOnAttribute pdao = null;
        try {
            connection = dbc.getConnection();
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
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (pdao == null) {
                    pdao = new HoangAnhCustomEntity.ProductDetailAddOnAttribute();
                }
                pdao.getQuantity().add(
                        new HoangAnhCustomEntity.QuantityTracker(
                                rs.getInt("ColorID"),
                                rs.getInt("SizeID"),
                                rs.getInt("ProductQuantity"))
                );

                Color c = new Color(
                        rs.getInt("ColorID"),
                        rs.getString("ColorName"),
                        rs.getString("ColorCode")
                );

                Size s = new Size(
                        rs.getInt("SizeID"),
                        rs.getString("SizeName")
                );

                if (pdao.getProductColor().size() == 0) {
                    pdao.getProductColor().add(c);
                } else {
                    if (!pdao.getProductColor().contains(c)) {
                        pdao.getProductColor().add(c);
                    }
                }

                if (pdao.getProductSize().size() == 0) {
                    pdao.getProductSize().add(s);
                } else {
                    if (!pdao.getProductSize().contains(s)) {
                        pdao.getProductSize().add(s);
                    }
                }
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

        return pdao;
    }

    public int getCartIdByUserId(int uid) {
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * FROM Cart WHERE UserID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
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
        return -1;
    }

    public void handleCartForUser(int uid) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = dbc.getConnection();

            if (connection == null || connection.isClosed()) {
                throw new Exception("Failed to establish a database connection.");
            }

            // If user doesn't have a cart, create one
            if (getCartIdByUserId(uid) == -1) {
                String sql = "INSERT INTO Cart(UserID) VALUES (?);";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, uid);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
        return;
    }

    // Method to add chosen product to user's cart
    public boolean addToCart(int productId, int colorId, int sizeId, int quantity, int uid) {
        boolean isSuccess = false;

        // Ensure user has a cart
        handleCartForUser(uid);
        try {
            // Re-fetch the cartId after potentially creating a new cart
            int cartId = getCartIdByUserId(uid);  // Must fetch after ensuring cart exists

            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "SELECT Quantity FROM CartProduct WHERE CartID = ? AND ProductID = ? AND SizeID = ? AND ColorID = ?;";
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, sizeId);
            ps.setInt(4, colorId);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Step 2: If the exact product (with matching size and color) exists, update the quantity
                int existingQuantity = rs.getInt("Quantity");
                int newQuantity = existingQuantity + quantity;

                // Update the quantity for the existing item
                String updateSql = "UPDATE CartProduct SET Quantity = ? WHERE CartID = ? AND ProductID = ? AND SizeID = ? AND ColorID = ?;";
                ps = connection.prepareStatement(updateSql);
                ps.setInt(1, newQuantity);
                ps.setInt(2, cartId);
                ps.setInt(3, productId);
                ps.setInt(4, sizeId);
                ps.setInt(5, colorId);
                int lineEffected = ps.executeUpdate();
                if (lineEffected > 0) {
                    isSuccess = true;
                }

                // Debug log to see if update happens
                System.out.println("Updated quantity for product ID: " + productId + " in cart.");
            } else {
                // Step 3: If the product is not found with the same size and color, insert a new product entry
                String insertSql = "INSERT INTO CartProduct(CartID, ProductID, Quantity, SizeID, ColorID) VALUES (?, ?, ?, ?, ?);";
                ps = connection.prepareStatement(insertSql);
                ps.setInt(1, cartId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.setInt(4, sizeId);
                ps.setInt(5, colorId);
                int lineEffected = ps.executeUpdate();
                if (lineEffected > 0) {
                    isSuccess = true;
                }

                // Debug log to see if insert happens
                System.out.println("Inserted new product ID: " + productId + " in cart with new color and/or size.");
            }

        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                isSuccess = false;
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
        return isSuccess;
    }

    public boolean addToWishList(int uid, int pid) {
        boolean isSuccess = false;

        try {
            connection = dbc.getConnection();

            String checkSql = "INSERT INTO WishListUserProduct(UserID, ProductID) VALUE (?, ?);";
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, uid);
            ps.setInt(2, pid);
            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                isSuccess = false;
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
        return isSuccess;
    }

    public List<HoangAnhCustomEntity.ProductListDTO> getWishListByUserId(int uid) {
        List<HoangAnhCustomEntity.ProductListDTO> wList = new ArrayList<>();
        Map<Integer, HoangAnhCustomEntity.ProductListDTO> productMap = new HashMap<>();

        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "SELECT \n"
                    + "                        p.ID AS productID,\n"
                    + "                        p.Name AS ProductName,\n"
                    + "                        p.Price AS ProductPrice,\n"
                    + "                        p.ThumbnailImage AS ProductImage,\n"
                    + "                        s.Name AS subCategory,\n"
                    + "                        s.ID AS subCategoryId,\n"
                    + "                        t.ID AS tagID,\n"
                    + "                        t.Name AS TagName,\n"
                    + "                        t.Color AS TagColorCode,\n"
                    + "                        p.Star AS ProductStar\n"
                    + "                    FROM \n"
                    + "                    	WishListUserProduct wl\n"
                    + "                        JOIN Product p ON p.ID = wl.ProductID\n"
                    + "                        JOIN SubCategory s ON s.Id = p.SubCategoryID\n"
                    + "                        JOIN TagProduct tp ON tp.ProductID = p.ID\n"
                    + "                        JOIN Tag t ON t.ID = tp.TagID\n"
                    + "                    WHERE\n"
                    + "                    	wl.UserID = ?";
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, uid);
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
                    product.setSubCategoryId(rs.getInt("subCategoryId"));

                    // Initialize the tag list with the first tag
                    List<Tag> tList = new ArrayList<>();
                    tList.add(new Tag(rs.getInt("tagID"), rs.getString("tagName"), rs.getString("TagColorCode")));
                    product.setTagList(tList);

                    // Add the product to the map
                    productMap.put(productId, product);
                }
            }

            // Convert the map values to a list
            wList.addAll(productMap.values());

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
        return wList;
    }

    public boolean isValidWishListItem(int pid, int uid) {
        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "SELECT \n"
                    + "	p.ID as ProductID\n"
                    + "FROM \n"
                    + "	WishListUserProduct wl\n"
                    + "    JOIN Product p ON p.ID = wl.ProductID\n"
                    + "    JOIN SubCategory s ON s.Id = p.SubCategoryID\n"
                    + "    JOIN TagProduct tp ON tp.ProductID = p.ID\n"
                    + "    JOIN Tag t ON t.ID = tp.TagID\n"
                    + "WHERE\n"
                    + "	p.ID = ? AND wl.UserID = ?";
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, pid);
            ps.setInt(2, uid);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
        return false;
    }

    public void deleteItemInUserWishList(int uid, int productId) {
        try {
            connection = dbc.getConnection();

            // Step 1: Check if the exact product with same productId, colorId, and sizeId exists in the cart
            String checkSql = "DELETE FROM WishListUserProduct \n"
                    + "WHERE UserID = ? AND ProductID = ?;";
            ps = connection.prepareStatement(checkSql);
            ps.setInt(1, uid);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
    }

    public List<Tag> getTagsFromProductList(List<HoangAnhCustomEntity.ProductListDTO> pList) {
        List<Tag> tags = new LinkedList<>();

        try {
            // SQL query to fetch distinct tags associated with a product
            String sql = ""
                    + "SELECT\n"
                    + "    t.ID as TagID,\n"
                    + "    t.Name as TagName,\n"
                    + "    t.Color as TagColorCode\n"
                    + "FROM\n"
                    + "    Product p\n"
                    + "    JOIN TagProduct pt ON pt.ProductID = p.ID\n"
                    + "    JOIN Tag t ON t.ID = pt.TagID\n"
                    + "WHERE\n"
                    + "    p.ID = ?  -- Filter by ProductID for each product\n"
                    + "ORDER BY t.ID;";

            // Establish the database connection
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            // Loop through each product in pList and get associated tags
            for (HoangAnhCustomEntity.ProductListDTO product : pList) {
                ps.setInt(1, product.getProductId());  // Set the ProductID in the query
                rs = ps.executeQuery();

                while (rs.next()) {
                    String tagName = rs.getString("TagName");
                    boolean exists = tags.stream().anyMatch(tag -> tag.getName().equals(tagName));
                    // Create Tag object and add to the tags list
                    if (!exists) {
                        tags.add(new Tag(rs.getInt("TagID"), rs.getString("TagName"), rs.getString("TagColorCode")));
                    }
                }
            }
        } catch (SQLException e) {
            tags = null;
            e.printStackTrace();
        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (ps != null) try {
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return tags;
    }
    
    public List<Tag> getAllTags() {
        List<Tag> tags = new LinkedList<>();

        try {
            // SQL query to fetch distinct tags associated with a product
            String sql = "SELECT * FROM Tag;";

            // Establish the database connection
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                tags.add(new Tag(rs.getInt("ID"), rs.getString("Name"), rs.getString("Color")));
            }

        } catch (SQLException e) {
            tags = null;
            e.printStackTrace();
        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (ps != null) try {
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return tags;
    }

    public List<Category> getCategoriesFromProductList(List<HoangAnhCustomEntity.ProductListDTO> pList) {
        List<Category> categories = new LinkedList<>();

        try {
            String sql = ""
                    + "SELECT DISTINCT\n"
                    + "    s.ID as CategoryID,\n"
                    + "    s.Name as CategoryName,\n"
                    + "    s.OriginalType\n"
                    + "FROM\n"
                    + "	Product p\n"
                    + "    JOIN SubCategory s ON s.ID = p. SubCategoryID\n"
                    + "WHERE p.ID = ?";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            for (HoangAnhCustomEntity.ProductListDTO product : pList) {
                ps.setInt(1, product.getProductId());  // Set the ProductID in the query
                rs = ps.executeQuery();

                while (rs.next()) {
                    String cateName = rs.getString("CategoryName");
                    boolean exists = categories.stream().anyMatch(cate -> cate.getName().equals(cateName));
                    // Create Tag object and add to the tags list
                    if (!exists) {
                        categories.add(new Category(rs.getInt("CategoryID"), rs.getString("CategoryName"), rs.getString("OriginalType")));
                    } else {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            categories = null;
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

        return categories;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new LinkedList<>();

        try {
            String sql = "SELECT * FROM subcategory;";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("ID"), rs.getString("Name"), rs.getString("OriginalType")));
            }
        } catch (SQLException e) {
            categories = null;
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

        return categories;
    }

    public List<HoangAnhCustomEntity.FeedbackDetails> getFeedbackDetailsListByProductId(int productId) {
        List<HoangAnhCustomEntity.FeedbackDetails> fList = new ArrayList<>();

        try {
            connection = dbc.getConnection();
            String sql = "SELECT \n"
                    + "    f.ID as FeedbackId,\n"
                    + "    f.ProductID as ProductId,\n"
                    + "    CONCAT(u.FirstName, ' ', u.LastName) AS Username,\n"
                    + "    f.Star as ProductStar,\n"
                    + "    f.Comment as Comment,\n"
                    + "    f.CreateAt as CreateAt, \n"
                    + "    f.status as status\n"
                    + "FROM \n"
                    + "    Feedback f\n"
                    + "JOIN \n"
                    + "    User u ON f.UserID = u.ID\n"
                    + "WHERE \n"
                    + "    f.ProductID = ? AND f.status = 'show';";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoangAnhCustomEntity.FeedbackDetails newF = new HoangAnhCustomEntity.FeedbackDetails();
                newF.setId(rs.getInt("FeedbackId"));
                newF.setUsername(rs.getString("Username"));
                newF.setStar(rs.getInt("ProductStar"));
                newF.setComment(rs.getString("Comment"));
                newF.setCreateAt(rs.getString("CreateAt"));
                newF.setStatus(rs.getString("status"));

                fList.add(newF);
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
        return fList;
    }

    public List<HoangAnhCustomEntity.FeedbackMedia> getFeedbackMediaByProductId(int productId) {
        List<HoangAnhCustomEntity.FeedbackMedia> fMedList = new ArrayList<>();

        try {
            connection = dbc.getConnection();
            String sql = "SELECT\n"
                    + "	f.*\n"
                    + " FROM \n"
                    + "	feedbackmedia f \n"
                    + "    JOIN feedback fe ON fe.ID = f.FeedbackID\n"
                    + "WHERE fe.ProductID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoangAnhCustomEntity.FeedbackMedia newFMed = new HoangAnhCustomEntity.FeedbackMedia();
                newFMed.setId(rs.getInt("ID"));
                newFMed.setFeedbackId(rs.getInt("FeedbackID"));
                newFMed.setLink(rs.getString("Link"));
                fMedList.add(newFMed);
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
        if (!fMedList.isEmpty() || fMedList.size() != 0) {
            return fMedList;
        }

        return null;
    }

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

    public List<HoangAnhCustomEntity.Bill> getBillByUid(int uid) {
        List<HoangAnhCustomEntity.Bill> billList = new ArrayList<>();

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
                + "    b.isFeedbacked AS Feedback\n"
                + "FROM\n"
                + "    Bills b\n"
                + "        JOIN\n"
                + "    Address a ON b.AddressID = a.ID\n"
                + "        JOIN\n"
                + "    paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                + "        JOIN\n"
                + "    shipmethod sm ON sm.ID = b.ShipMethodID\n"
                + "WHERE\n"
                + "    b.CustomerID = ? AND b.Status = 'Received'";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, uid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoangAnhCustomEntity.Bill bill = new HoangAnhCustomEntity.Bill();
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

    public HoangAnhCustomEntity.Bill getBillByUidAndBillID(int uid, int bid) {
        HoangAnhCustomEntity.Bill bill = new HoangAnhCustomEntity.Bill();

        String sql = "SELECT "
                + " b.ID AS BillID, "
                + " b.TotalPrice, "
                + " b.PublishDate AS BillPublishDate, "
                + " b.Status AS BillStatus, "
                + " b.CustomerID AS CustomerIdBill, "
                + " b.SalerID AS SalerID, "
                + " b.ShipperID AS ShipperID, "
                + " a.ID AS AddressID, "
                + " a.UserID AS UserIDForAddress, "
                + " a.Country AS UserCountry, "
                + " a.TinhThanhPho AS UserTinhThanhPho, "
                + " a.QuanHuyen AS UserQuanHuyen, "
                + " a.PhuongXa AS UserPhuongXa, "
                + " a.Details AS UserAddressDetails, "
                + " a.Note AS UserAddressNote, "
                + " pm.ID AS PaymentMethodID, "
                + " pm.Name AS PaymentMethodName, "
                + " sm.ID AS ShipMethodID, "
                + " sm.Name AS ShipMethodName "
                + " FROM Bills b "
                + " JOIN Address a ON b.AddressID = a.ID "
                + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID "
                + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID "
                + " WHERE b.CustomerID = ? AND b.ID = ?";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, uid);
            ps.setInt(2, bid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bill.setId(rs.getInt("BillID"));
                    bill.setTotalPrice(rs.getDouble("TotalPrice"));
                    bill.setPublishDate(rs.getString("BillPublishDate"));
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

                    List<HoangAnhCustomEntity.Order> orderList = getOrderByBillID(rs.getInt("BillID"));
                    bill.setOrder(orderList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
        return bill;
    }

    public boolean insertProductFeedback(int uid, int oid, int pid, int star, String comment) {
        try {
            connection = dbc.getConnection();
            String sql = "INSERT INTO feedback (UserID, OrderID, ProductID, Star, Comment) VALUES\n"
                    + "(?, ?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, oid);
            ps.setInt(3, pid);
            ps.setInt(4, star);
            ps.setString(5, comment);
            int rowAffected = ps.executeUpdate();

            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return false;
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
    }

    public boolean insertBillFeedback(int uid, int bid, int star, String comment) {
        try {
            connection = dbc.getConnection();
            String sql = "INSERT INTO billfeedback (UserID, BillID, Star, Comment) VALUES (?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, bid);
            ps.setInt(3, star);
            ps.setString(4, comment);
            int rowAffected = ps.executeUpdate();

            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return false;
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
    }

    public int getFeedbackProductID(int uid, int oid, int pid) {
        int fid = 0;

        try {
            connection = dbc.getConnection();
            String sql = "SELECT ID From feedback WHERE UserID = ? AND OrderID = ? AND ProductID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, oid);
            ps.setInt(3, pid);
            rs = ps.executeQuery();
            if (rs.next()) {
                fid = rs.getInt("ID");
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
        return fid;
    }

    public int getFeedbackBillID(int uid, int bid) {
        int bfid = 0;

        try {
            connection = dbc.getConnection();
            String sql = "SELECT ID From billfeedback WHERE UserID = ? AND BillID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, bid);
            rs = ps.executeQuery();
            if (rs.next()) {
                bfid = rs.getInt("ID");
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
        return bfid;
    }

    public boolean insertProductFeedbackMedia(int uid, int oid, int pid, String[] imgList) {
        int fid = getFeedbackProductID(uid, oid, pid);  // Retrieve the feedback ID based on user, order, and product

        String sql = "INSERT INTO feedbackmedia (FeedbackID, Link) VALUES (?, ?);";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            // Loop through imgList and insert each image link
            for (String base64 : imgList) {
                ps.setInt(1, fid);                      // Set FeedbackID (retrieved earlier)
                ps.setString(2, base64); // Convert StringBuilder to String and set image link

                ps.addBatch();  // Add to batch for execution
            }

            // Execute all batched statements in one go
            int[] rowAffected = ps.executeBatch();

            // Check if all rows were inserted successfully
            return rowAffected.length == imgList.length; // Return true if all were inserted

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return false;
        }
    }

    public boolean insertBillFeedbackMedia(int uid, int bid, String[] imgList) {
        int bfid = getFeedbackBillID(uid, bid);  // Retrieve the feedback ID based on user, order, and product

        String sql = "INSERT INTO billfeedbackmedia (BillID, Link) VALUES (?, ?);";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            // Loop through imgList and insert each image link
            for (String base64 : imgList) {
                ps.setInt(1, bfid);                      // Set FeedbackID (retrieved earlier)
                ps.setString(2, base64); // Convert StringBuilder to String and set image link

                ps.addBatch();  // Add to batch for execution
            }

            // Execute all batched statements in one go
            int[] rowAffected = ps.executeBatch();

            // Check if all rows were inserted successfully
            return rowAffected.length == imgList.length; // Return true if all were inserted

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return false;
        }
    }

    public List<HoangAnhCustomEntity.BillForOrderManager> getAllUserBill(int uid, int roleId) {
        List<HoangAnhCustomEntity.BillForOrderManager> billList = new ArrayList<>();

        String sql;

        if (roleId == 2 || roleId == 6) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "ORDER BY b.PublishDate DESC";
        } else if (roleId == 4) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE b.SalerID = ?\n"
                    + "ORDER BY b.PublishDate DESC";
        } else {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE b.ShipperID = ?\n"
                    + "ORDER BY b.PublishDate DESC";
        }

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql);) {
            if (roleId == 4 || roleId == 7) {
                pss.setInt(1, uid);
            }
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    HoangAnhCustomEntity.BillForOrderManager bill = new HoangAnhCustomEntity.BillForOrderManager();
                    bill.setId(rss.getInt("BillID"));
                    bill.setTotalPrice(rss.getDouble("TotalPrice"));
                    bill.setPublishDate(formateDateTime(rss.getTimestamp("BillPublishDate")));
                    bill.setStatus(rss.getString("BillStatus"));
                    bill.setCustomer(new HoangAnhCustomEntity.CustomerInformationForOrderManager(
                            rss.getInt("CustomerIdBill"),
                            rss.getString("customerName"),
                            rss.getString("CustomerPhone"),
                            rss.getString("CustomerGender"),
                            rss.getString("CustomerEmail")
                    ));
                    bill.setSaler(new HoangAnhCustomEntity.Staff(
                            rss.getInt("SalerID"),
                            rss.getString("SalerName"),
                            rss.getString("SalerPhone")
                    ));
                    bill.setShipper(new HoangAnhCustomEntity.Staff(
                            rss.getInt("ShipperID"),
                            rss.getString("ShipperName"),
                            rss.getString("ShipperPhone")
                    ));
                    bill.setAddress(new HoangAnhCustomEntity.Address(
                            rss.getInt("AddressID"),
                            rss.getInt("UserIDForAddress"),
                            rss.getString("UserCountry"),
                            rss.getString("UserTinhThanhPho"),
                            rss.getString("UserQuanHuyen"),
                            rss.getString("UserPhuongXa"),
                            rss.getString("UserAddressDetails"),
                            rss.getString("UserAddressNote")
                    ));
                    bill.setShipMethod(new HoangAnhCustomEntity.ShipMethod(
                            rss.getInt("ShipMethodID"),
                            rss.getString("ShipMethodName")
                    ));
                    bill.setPayment(new HoangAnhCustomEntity.PaymentMethod(
                            rss.getInt("PaymentMethodID"),
                            rss.getString("PaymentMethodName")
                    ));
                    bill.setIsFeedbacked(rss.getBoolean("Feedback"));

                    // Fetch orders inline for each bill
                    List<HoangAnhCustomEntity.Order> orderList = getOrderByBillID(rss.getInt("BillID"));

                    bill.setOrder(orderList);

                    billList.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        }
        return billList;
    }

    public List<HoangAnhCustomEntity.BillForOrderManager> getAllUserBillByStatus(int uid, int roleId, String status) {
        List<HoangAnhCustomEntity.BillForOrderManager> billList = new ArrayList<>();

        String sql;

        if (roleId == 2 || roleId == 6) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE b.Status = ?\n"
                    + "ORDER BY b.PublishDate DESC";
        } else if (roleId == 4) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE b.Status = ? AND b.SalerID = ?\n"
                    + "ORDER BY b.PublishDate DESC";
        } else {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE b.Status = ? AND b.ShipperID = ?\n"
                    + "ORDER BY b.PublishDate DESC";
        }

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql);) {
            pss.setString(1, status);
            if (roleId == 4 || roleId == 7) {
                pss.setInt(2, uid);
            }
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    HoangAnhCustomEntity.BillForOrderManager bill = new HoangAnhCustomEntity.BillForOrderManager();
                    bill.setId(rss.getInt("BillID"));
                    bill.setTotalPrice(rss.getDouble("TotalPrice"));
                    bill.setPublishDate(formateDateTime(rss.getTimestamp("BillPublishDate")));
                    bill.setStatus(rss.getString("BillStatus"));
                    bill.setCustomer(new HoangAnhCustomEntity.CustomerInformationForOrderManager(
                            rss.getInt("CustomerIdBill"),
                            rss.getString("customerName"),
                            rss.getString("CustomerPhone"),
                            rss.getString("CustomerGender"),
                            rss.getString("CustomerEmail")
                    ));
                    bill.setSaler(new HoangAnhCustomEntity.Staff(
                            rss.getInt("SalerID"),
                            rss.getString("SalerName"),
                            rss.getString("SalerPhone")
                    ));
                    bill.setShipper(new HoangAnhCustomEntity.Staff(
                            rss.getInt("ShipperID"),
                            rss.getString("ShipperName"),
                            rss.getString("ShipperPhone")
                    ));
                    bill.setAddress(new HoangAnhCustomEntity.Address(
                            rss.getInt("AddressID"),
                            rss.getInt("UserIDForAddress"),
                            rss.getString("UserCountry"),
                            rss.getString("UserTinhThanhPho"),
                            rss.getString("UserQuanHuyen"),
                            rss.getString("UserPhuongXa"),
                            rss.getString("UserAddressDetails"),
                            rss.getString("UserAddressNote")
                    ));
                    bill.setShipMethod(new HoangAnhCustomEntity.ShipMethod(
                            rss.getInt("ShipMethodID"),
                            rss.getString("ShipMethodName")
                    ));
                    bill.setPayment(new HoangAnhCustomEntity.PaymentMethod(
                            rss.getInt("PaymentMethodID"),
                            rss.getString("PaymentMethodName")
                    ));
                    bill.setIsFeedbacked(rss.getBoolean("Feedback"));

                    // Fetch orders inline for each bill
                    List<HoangAnhCustomEntity.Order> orderList = getOrderByBillID(rss.getInt("BillID"));

                    bill.setOrder(orderList);

                    billList.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        }
        return billList;
    }

    public List<HoangAnhCustomEntity.BillForOrderManager> getAllUserBillByCusName(int uid, int roleId, String search) {
        List<HoangAnhCustomEntity.BillForOrderManager> billList = new ArrayList<>();

        String sql;

        if (roleId == 2 || roleId == 6) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE CONCAT(ucus.LastName, ' ', ucus.FirstName) like ?\n"
                    + "ORDER BY b.PublishDate DESC";
        } else if (roleId == 4) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE CONCAT(ucus.LastName, ' ', ucus.FirstName) like ? AND b.SalerID = ?\n"
                    + "ORDER BY b.PublishDate DESC";
        } else {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE CONCAT(ucus.LastName, ' ', ucus.FirstName) like ? AND b.ShipperID = ?\n"
                    + "ORDER BY b.PublishDate DESC";
        }

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql);) {
            pss.setString(1, "%" + search + "%");
            if (roleId == 4 || roleId == 7) {
                pss.setInt(2, uid);
            }
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    HoangAnhCustomEntity.BillForOrderManager bill = new HoangAnhCustomEntity.BillForOrderManager();
                    bill.setId(rss.getInt("BillID"));
                    bill.setTotalPrice(rss.getDouble("TotalPrice"));
                    bill.setPublishDate(formateDateTime(rss.getTimestamp("BillPublishDate")));
                    bill.setStatus(rss.getString("BillStatus"));
                    bill.setCustomer(new HoangAnhCustomEntity.CustomerInformationForOrderManager(
                            rss.getInt("CustomerIdBill"),
                            rss.getString("customerName"),
                            rss.getString("CustomerPhone"),
                            rss.getString("CustomerGender"),
                            rss.getString("CustomerEmail")
                    ));
                    bill.setSaler(new HoangAnhCustomEntity.Staff(
                            rss.getInt("SalerID"),
                            rss.getString("SalerName"),
                            rss.getString("SalerPhone")
                    ));
                    bill.setShipper(new HoangAnhCustomEntity.Staff(
                            rss.getInt("ShipperID"),
                            rss.getString("ShipperName"),
                            rss.getString("ShipperPhone")
                    ));
                    bill.setAddress(new HoangAnhCustomEntity.Address(
                            rss.getInt("AddressID"),
                            rss.getInt("UserIDForAddress"),
                            rss.getString("UserCountry"),
                            rss.getString("UserTinhThanhPho"),
                            rss.getString("UserQuanHuyen"),
                            rss.getString("UserPhuongXa"),
                            rss.getString("UserAddressDetails"),
                            rss.getString("UserAddressNote")
                    ));
                    bill.setShipMethod(new HoangAnhCustomEntity.ShipMethod(
                            rss.getInt("ShipMethodID"),
                            rss.getString("ShipMethodName")
                    ));
                    bill.setPayment(new HoangAnhCustomEntity.PaymentMethod(
                            rss.getInt("PaymentMethodID"),
                            rss.getString("PaymentMethodName")
                    ));
                    bill.setIsFeedbacked(rss.getBoolean("Feedback"));

                    // Fetch orders inline for each bill
                    List<HoangAnhCustomEntity.Order> orderList = getOrderByBillID(rss.getInt("BillID"));

                    bill.setOrder(orderList);

                    billList.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        }
        return billList;
    }

    public List<HoangAnhCustomEntity.StaffWithRole> getAllStaffByRoleId(int roleId) {
        List<HoangAnhCustomEntity.StaffWithRole> staffList = new ArrayList<>();

        try {
            connection = dbc.getConnection();
            String sql = "SELECT\n"
                    + "	u.ID AS StaffId,\n"
                    + "	CONCAT(u.LastName, ' ', u.Firstname) AS StaffName,\n"
                    + "    u.PhoneNumber AS StaffPhone,\n"
                    + "    u.Email AS StaffEmail,\n"
                    + "    r.ID AS StaffRoleId,\n"
                    + "    r.Name AS StaffRoleName\n"
                    + "FROM\n"
                    + "	`defaultdb`.`user` u\n"
                    + "    JOIN role r ON r.ID = u.RoleID\n"
                    + "WHERE u.RoleID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoangAnhCustomEntity.StaffWithRole s = new HoangAnhCustomEntity.StaffWithRole();
                s.setId(rs.getInt("StaffId"));
                s.setName(rs.getString("StaffName"));
                s.setPhoneNumber(rs.getString("StaffPhone"));
                s.setEmail(rs.getString("StaffEmail"));
                s.setRole(new Role(rs.getInt("StaffRoleId"), rs.getString("StaffRoleName")));

                staffList.add(s);
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
        return staffList;
    }

    public void saveBillInfor(int bid, String status, int salerId) {
        try {
            connection = dbc.getConnection();
            String sql = "UPDATE Bills SET Status = ?, SalerID = ? WHERE ID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, salerId);
            ps.setInt(3, bid);
            ps.executeUpdate();  // Use executeUpdate for data modification queries

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
    }

    public void saveBillStatus(int bid, String status) {
        try {
            connection = dbc.getConnection();
            String sql = "UPDATE Bills SET Status = ? WHERE ID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, bid);
            ps.executeUpdate();  // Use executeUpdate for data modification queries

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }
    }

    public String getShipperEmail(int bid) {
        String sEmail = null;
        String sql = "SELECT \n"
                + "    uship.Email as ShipperEmail\n"
                + "FROM\n"
                + "    bills b\n"
                + "        JOIN\n"
                + "    user uship ON uship.ID = b.ShipperID\n"
                + "WHERE b.ID = ?;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, bid);
            try (ResultSet rss = pss.executeQuery()) {
                if (rss.next()) {
                    sEmail = rss.getString("ShipperEmail");
                }
            }
        } catch (Exception e) {
            System.err.println("getShipperEmail function in HoangAnhCustomEntityDAO get e: " + e.getMessage());
        }

        return sEmail;
    }

    public String getCustomerEmail(int bid) {
        String cEmail = null;
        String sql = "SELECT \n"
                + "    ucus.Email as CustomerEmail\n"
                + "FROM\n"
                + "    bills b\n"
                + "    JOIN\n"
                + "    user ucus ON ucus.ID = b.CustomerID\n"
                + "WHERE b.ID = ?;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, bid);
            try (ResultSet rss = pss.executeQuery()) {
                if (rss.next()) {
                    cEmail = rss.getString("CustomerEmail");
                }
            }
        } catch (Exception e) {
            System.err.println("getShipperEmail function in HoangAnhCustomEntityDAO get e: " + e.getMessage());
        }

        return cEmail;
    }

    public void sendOnDeliveryEmail(int bid) {
        String cusEmail = getCustomerEmail(bid);
        String shipEmail = getShipperEmail(bid);

        String customerEmailContent
                = "<html>"
                + "<head>"
                + "<style>"
                + "  body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f7f7f7; }"
                + "  .email-container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }"
                + "  .header { text-align: center; font-size: 24px; color: #333; }"
                + "  .content { font-size: 16px; color: #555; line-height: 1.6; }"
                + "  .footer { font-size: 14px; text-align: center; margin-top: 20px; color: #999; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "  <div class='email-container'>"
                + "    <div class='header'>Your Order is On the Way!</div>"
                + "    <div class='content'>"
                + "      <p>Dear Customer,</p>"
                + "      <p>Your bill <strong>" + bid + "</strong> is currently on delivery. Please make sure to pick up your phone when the shipper calls.</p>"
                + "      <p>Thank you for shopping with us!</p>"
                + "    </div>"
                + "    <div class='footer'>Freeze Team</div>"
                + "  </div>"
                + "</body>"
                + "</html>";
        Email.sendEmail(cusEmail, "Your Order is Coming!", customerEmailContent);

        String shipperEmailContent
                = "<html>"
                + "<head>"
                + "<style>"
                + "  body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f7f7f7; }"
                + "  .email-container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }"
                + "  .header { text-align: center; font-size: 24px; color: #333; }"
                + "  .content { font-size: 16px; color: #555; line-height: 1.6; }"
                + "  .footer { font-size: 14px; text-align: center; margin-top: 20px; color: #999; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "  <div class='email-container'>"
                + "    <div class='header'>Delivery Task Notification</div>"
                + "    <div class='content'>"
                + "      <p>Dear Shipper,</p>"
                + "      <p>You have a new delivery task for bill <strong>" + bid + "</strong>. Please make sure to deliver it on time and contact the customer when needed.</p>"
                + "      <p>Thank you for your dedication!</p>"
                + "    </div>"
                + "    <div class='footer'>Freeze Team</div>"
                + "  </div>"
                + "</body>"
                + "</html>";
        Email.sendEmail(shipEmail, "Your Delivery Task!", shipperEmailContent);
    }

    public void sendReShipEmail(int bid) {
        String cusEmail = getCustomerEmail(bid);

        String customerEmailContent
                = "<html>"
                + "<head>"
                + "<style>"
                + "  body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f7f7f7; }"
                + "  .email-container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }"
                + "  .header { text-align: center; font-size: 24px; color: #333; }"
                + "  .content { font-size: 16px; color: #555; line-height: 1.6; }"
                + "  .footer { font-size: 14px; text-align: center; margin-top: 20px; color: #999; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "  <div class='email-container'>"
                + "    <div class='header'>Your Order is On the Way of Re-Shipping!</div>"
                + "    <div class='content'>"
                + "      <p>Dear Customer,</p>"
                + "      <p>Your bill <strong>" + bid + "</strong> is currently on delivery of Re-Shipping. Please make sure to pick up your phone when the shipper calls. If you do not receive this time, the bill will be canceled!</p>"
                + "      <p>Thank you for shopping with us!</p>"
                + "    </div>"
                + "    <div class='footer'>Freeze Team</div>"
                + "  </div>"
                + "</body>"
                + "</html>";
        Email.sendEmail(cusEmail, "Your Order is Re-Ship to you!", customerEmailContent);
    }

    public void sendReceivedEmail(int bid) {
        String cusEmail = getCustomerEmail(bid);

        String customerEmailContent
                = "<html>"
                + "<head>"
                + "<style>"
                + "  body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f7f7f7; }"
                + "  .email-container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }"
                + "  .header { text-align: center; font-size: 24px; color: #333; }"
                + "  .content { font-size: 16px; color: #555; line-height: 1.6; }"
                + "  .footer { font-size: 14px; text-align: center; margin-top: 20px; color: #999; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "  <div class='email-container'>"
                + "    <div class='header'>Your Order is successfully completed!</div>"
                + "    <div class='content'>"
                + "      <p>Dear Customer,</p>"
                + "      <p>Your bill <strong>" + bid + "</strong> is completed. If you like it, feedback for us in here <a href='http://localhost:9999/freezegroup2/feedback' class='button'>Feedback Now!</a></p>"
                + "      <p>Thank you for shopping with us!</p>"
                + "    </div>"
                + "    <div class='footer'>Freeze Team</div>"
                + "  </div>"
                + "</body>"
                + "</html>";
        Email.sendEmail(cusEmail, "Your bill is complete!", customerEmailContent);
    }

    public boolean isValidSaler(int sid, int bid) {
        try {
            connection = dbc.getConnection();
            String sql = "SELECT * From Bills WHERE ID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bid);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (sid == rs.getInt("SalerID")) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            // Close resources properly
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();  // Handle any errors while closing resources
            }
        }

        return true;
    }

    public List<HoangAnhCustomEntity.BillForOrderManager> getAllUserBillByDate(String fromDate, String toDate, int uid, int roleId) {
        List<HoangAnhCustomEntity.BillForOrderManager> billList = new ArrayList<>();

        String sql;

        if (roleId == 2 || roleId == 6) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE DATE(b.PublishDate) BETWEEN ? AND ?\n"
                    + "ORDER BY b.PublishDate DESC;";
        } else if (roleId == 4) {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE DATE(b.PublishDate) BETWEEN ? AND ? AND b.SalerID = ?\n"
                    + "ORDER BY b.PublishDate DESC;";
        } else {
            sql = "SELECT \n"
                    + " b.ID AS BillID,\n"
                    + " b.TotalPrice,\n"
                    + " b.PublishDate AS BillPublishDate,\n"
                    + " b.Status AS BillStatus,\n"
                    + " b.CustomerID AS CustomerIdBill,\n"
                    + " CONCAT(ucus.LastName, ' ', ucus.FirstName) AS customerName,\n"
                    + " ucus.PhoneNumber AS CustomerPhone,\n"
                    + " ucus.Gender AS CustomerGender,\n"
                    + " ucus.Email AS CustomerEmail,\n"
                    + " b.SalerID AS SalerID,\n"
                    + " CONCAT(usale.LastName, ' ', usale.FirstName) AS SalerName,\n"
                    + " usale.PhoneNumber AS SalerPhone,\n"
                    + " b.ShipperID AS ShipperID,\n"
                    + " CONCAT(uship.LastName, ' ', uship.FirstName) AS ShipperName,\n"
                    + " uship.PhoneNumber AS ShipperPhone,\n"
                    + " a.ID AS AddressID,\n"
                    + " a.UserID AS UserIDForAddress,\n"
                    + " a.Country AS UserCountry,\n"
                    + " a.TinhThanhPho AS UserTinhThanhPho,\n"
                    + " a.QuanHuyen AS UserQuanHuyen,\n"
                    + " a.PhuongXa AS UserPhuongXa,\n"
                    + " a.Details AS UserAddressDetails,\n"
                    + " a.Note AS UserAddressNote,\n"
                    + " pm.ID AS PaymentMethodID,\n"
                    + " pm.Name AS PaymentMethodName,\n"
                    + " sm.ID AS ShipMethodID,\n"
                    + " sm.Name AS ShipMethodName,\n"
                    + " b.isFeedbacked AS Feedback\n"
                    + "FROM Bills b\n"
                    + " JOIN Address a ON b.AddressID = a.ID\n"
                    + " JOIN paymentmethod pm ON pm.ID = b.PaymentMethodID\n"
                    + " JOIN shipmethod sm ON sm.ID = b.ShipMethodID\n"
                    + " JOIN `defaultdb`.`user` usale ON usale.ID = b.SalerID\n"
                    + " JOIN `defaultdb`.`user` uship ON uship.ID = b.ShipperID\n"
                    + " JOIN `defaultdb`.`user` ucus ON ucus.ID = b.CustomerID\n"
                    + "WHERE DATE(b.PublishDate) BETWEEN ? AND ? AND b.ShipperID = ?\n"
                    + "ORDER BY b.PublishDate DESC;";
        }

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, fromDate);
            pss.setString(2, toDate);

            if (roleId == 4) {
                pss.setInt(3, uid);
            }

            if (roleId == 7) {
                pss.setInt(3, uid);
            }

            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    HoangAnhCustomEntity.BillForOrderManager bill = new HoangAnhCustomEntity.BillForOrderManager();
                    bill.setId(rss.getInt("BillID"));
                    bill.setTotalPrice(rss.getDouble("TotalPrice"));
                    bill.setPublishDate(formateDateTime(rss.getTimestamp("BillPublishDate")));
                    bill.setStatus(rss.getString("BillStatus"));
                    bill.setCustomer(new HoangAnhCustomEntity.CustomerInformationForOrderManager(
                            rss.getInt("CustomerIdBill"),
                            rss.getString("customerName"),
                            rss.getString("CustomerPhone"),
                            rss.getString("CustomerGender"),
                            rss.getString("CustomerEmail")
                    ));
                    bill.setSaler(new HoangAnhCustomEntity.Staff(
                            rss.getInt("SalerID"),
                            rss.getString("SalerName"),
                            rss.getString("SalerPhone")
                    ));
                    bill.setShipper(new HoangAnhCustomEntity.Staff(
                            rss.getInt("ShipperID"),
                            rss.getString("ShipperName"),
                            rss.getString("ShipperPhone")
                    ));
                    bill.setAddress(new HoangAnhCustomEntity.Address(
                            rss.getInt("AddressID"),
                            rss.getInt("UserIDForAddress"),
                            rss.getString("UserCountry"),
                            rss.getString("UserTinhThanhPho"),
                            rss.getString("UserQuanHuyen"),
                            rss.getString("UserPhuongXa"),
                            rss.getString("UserAddressDetails"),
                            rss.getString("UserAddressNote")
                    ));
                    bill.setShipMethod(new HoangAnhCustomEntity.ShipMethod(
                            rss.getInt("ShipMethodID"),
                            rss.getString("ShipMethodName")
                    ));
                    bill.setPayment(new HoangAnhCustomEntity.PaymentMethod(
                            rss.getInt("PaymentMethodID"),
                            rss.getString("PaymentMethodName")
                    ));
                    bill.setIsFeedbacked(rss.getBoolean("Feedback"));

                    // Fetch orders inline for each bill
                    List<HoangAnhCustomEntity.Order> orderList = getOrderByBillID(rss.getInt("BillID"));

                    bill.setOrder(orderList);

                    billList.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        }
        return billList;
    }

    public String formateDateTime(java.sql.Timestamp t) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        return dateFormat.format(t);
    }

    public HoangAnhCustomEntity.DashBoardContent getDashBoardContentByFilter(String filter, String date) {
        HoangAnhCustomEntity.DashBoardContent content = new HoangAnhCustomEntity.DashBoardContent();

        // Example SQL query templates
        StringBuilder revenueQueryBuilder = new StringBuilder();
        revenueQueryBuilder.append("SELECT SUM(TotalPrice) AS Revenue FROM bills WHERE Status = 'Received'");

        StringBuilder pendingPaymentQueryBuilder = new StringBuilder();
        pendingPaymentQueryBuilder.append("SELECT SUM(TotalPrice) AS PendingRevenue FROM bills WHERE Status IN ('OnDelivery', 'Pending')");

        StringBuilder refundsQueryBuilder = new StringBuilder();
        refundsQueryBuilder.append("SELECT SUM(TotalPrice) AS Refunds FROM bills WHERE Status = 'Canceled'");

        StringBuilder avgPerOrderQueryBuilder = new StringBuilder();
        avgPerOrderQueryBuilder.append("SELECT AVG(TotalPrice) AS AvgPerOrder FROM bills");

        StringBuilder totalBillsQueryBuilder = new StringBuilder();
        totalBillsQueryBuilder.append("SELECT COUNT(ID) AS TotalBills FROM bills");

        StringBuilder canceledBillsQueryBuilder = new StringBuilder();
        canceledBillsQueryBuilder.append("SELECT COUNT(ID) AS CanceledBills FROM bills WHERE Status = 'Canceled'");

        StringBuilder onDeliveryOrPendingBillsQueryBuilder = new StringBuilder();
        onDeliveryOrPendingBillsQueryBuilder.append("SELECT COUNT(ID) AS PendingBills FROM bills WHERE Status IN ('OnDelivery', 'Pending')");

        StringBuilder successBillsQueryBuilder = new StringBuilder();
        successBillsQueryBuilder.append("SELECT COUNT(ID) AS SuccessBills FROM bills WHERE Status = 'Received'");

        StringBuilder totalCustomerQueryBuilder = new StringBuilder();
        totalCustomerQueryBuilder.append("SELECT COUNT(ID) AS TotalCustomer FROM user WHERE RoleID = 1");

        StringBuilder newlyRegQueryBuilder = new StringBuilder();
        newlyRegQueryBuilder.append("SELECT COUNT(ID) AS NewlyReg FROM user WHERE RoleID = 1");

        StringBuilder newlyBoughtQueryBuilder = new StringBuilder();
        newlyBoughtQueryBuilder.append("SELECT COUNT(u.ID) AS NewlyBought FROM bills b JOIN user u ON b.CustomerID = u.ID WHERE u.RoleID = 1");

        StringBuilder bannedCustomerQueryBuilder = new StringBuilder();
        bannedCustomerQueryBuilder.append("SELECT COUNT(ID) AS BannedCustomer FROM user WHERE RoleID = 1 AND Status = 'banned'");

        StringBuilder totalProductFeedbackQueryBuilder = new StringBuilder();
        totalProductFeedbackQueryBuilder.append("SELECT COUNT(ID) AS TotalFeedback FROM feedback");

        StringBuilder avgProductStarQueryBuilder = new StringBuilder();
        avgProductStarQueryBuilder.append("SELECT AVG(Star) AS AvgStar FROM feedback");

        StringBuilder totalShopFeedbackQueryBuilder = new StringBuilder();
        totalShopFeedbackQueryBuilder.append("SELECT COUNT(ID) AS TotalFeedback FROM billfeedback");

        StringBuilder avgShopStarQueryBuilder = new StringBuilder();
        avgShopStarQueryBuilder.append("SELECT AVG(Star) AS AvgStar FROM billfeedback");

        switch (filter) {
            case "all" -> {
                newlyRegQueryBuilder.append(" AND DATE(CreateAt) = CURDATE();");
                newlyBoughtQueryBuilder.append(" AND DATE(b.PublishDate) = CURDATE();");
            }
            case "7days" -> {
                revenueQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                pendingPaymentQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                refundsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                avgPerOrderQueryBuilder.append(" WHERE PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                totalBillsQueryBuilder.append(" WHERE PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                canceledBillsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                onDeliveryOrPendingBillsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                successBillsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 7 DAY;");
                totalCustomerQueryBuilder.append(" AND CreateAt >= CURDATE() - INTERVAL 7 DAY;");
                newlyRegQueryBuilder.append(" AND CreateAt >= CURDATE() - INTERVAL 7 DAY;");
                newlyBoughtQueryBuilder.append(" AND CreateAt >= CURDATE() - INTERVAL 7 DAY;");
                totalProductFeedbackQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 7 DAY;");
                avgProductStarQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 7 DAY;");
                totalShopFeedbackQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 7 DAY;");
                avgShopStarQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 7 DAY;");
            }
            case "30days" -> {
                revenueQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                pendingPaymentQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                refundsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                avgPerOrderQueryBuilder.append(" WHERE PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                totalBillsQueryBuilder.append(" WHERE PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                canceledBillsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                onDeliveryOrPendingBillsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                successBillsQueryBuilder.append(" AND PublishDate >= CURDATE() - INTERVAL 30 DAY;");
                totalCustomerQueryBuilder.append(" AND CreateAt >= CURDATE() - INTERVAL 30 DAY;");
                newlyRegQueryBuilder.append(" AND CreateAt >= CURDATE() - INTERVAL 30 DAY;");
                newlyBoughtQueryBuilder.append(" AND CreateAt >= CURDATE() - INTERVAL 30 DAY;");
                totalProductFeedbackQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 30 DAY;");
                avgProductStarQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 30 DAY;");
                totalShopFeedbackQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 30 DAY;");
                avgShopStarQueryBuilder.append(" WHERE CreateAt >= CURDATE() - INTERVAL 30 DAY;");
            }
            case "date" -> {
                revenueQueryBuilder.append(" AND DATE(PublishDate) = ?;");
                pendingPaymentQueryBuilder.append(" AND DATE(PublishDate) = ?;");
                refundsQueryBuilder.append(" AND DATE(PublishDate) = ?;");
                avgPerOrderQueryBuilder.append(" WHERE DATE(PublishDate) = ?;");
                totalBillsQueryBuilder.append(" WHERE DATE(PublishDate) = ?;");
                canceledBillsQueryBuilder.append(" AND DATE(PublishDate) = ?;");
                onDeliveryOrPendingBillsQueryBuilder.append(" AND DATE(PublishDate) = ?;");
                successBillsQueryBuilder.append(" AND DATE(PublishDate) = ?;");
                totalCustomerQueryBuilder.append(" AND DATE(CreateAt) = ?;");
                newlyRegQueryBuilder.append(" AND DATE(CreateAt) = ?;");
                newlyBoughtQueryBuilder.append(" AND DATE(CreateAt) = ?;");
                totalProductFeedbackQueryBuilder.append(" WHERE DATE(CreateAt) = ?;");
                avgProductStarQueryBuilder.append(" WHERE DATE(CreateAt) = ?;");
                totalShopFeedbackQueryBuilder.append(" WHERE DATE(CreateAt) = ?;");
                avgShopStarQueryBuilder.append(" WHERE DATE(CreateAt) = ?;");
            }
            case "month" -> {
                revenueQueryBuilder.append(" AND MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                pendingPaymentQueryBuilder.append(" AND MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                refundsQueryBuilder.append(" AND MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                avgPerOrderQueryBuilder.append(" WHERE MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                totalBillsQueryBuilder.append(" WHERE MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                canceledBillsQueryBuilder.append(" AND MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                onDeliveryOrPendingBillsQueryBuilder.append(" AND MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                successBillsQueryBuilder.append(" AND MONTH(PublishDate) = ? AND YEAR(PublishDate) = ?;");
                totalCustomerQueryBuilder.append(" AND MONTH(CreateAt) = ? AND YEAR(CreateAt) = ?;");
                newlyRegQueryBuilder.append(" AND MONTH(CreateAt) = ? AND YEAR(CreateAt) = ?;");
                newlyBoughtQueryBuilder.append(" AND MONTH(CreateAt) = ? AND YEAR(CreateAt) = ?;");
                totalProductFeedbackQueryBuilder.append(" WHERE MONTH(CreateAt) = ? AND YEAR(CreateAt) = ?;");
                avgProductStarQueryBuilder.append(" WHERE MONTH(CreateAt) = ? AND YEAR(CreateAt) = ?;");
                totalShopFeedbackQueryBuilder.append(" WHERE MONTH(CreateAt) = ? AND YEAR(CreateAt) = ?;");
                avgShopStarQueryBuilder.append(" WHERE MONTH(CreateAt) = ? AND YEAR(CreateAt) = ?;");
            }
        }

        try (Connection con = dbc.getConnection(); PreparedStatement revenueStmt = con.prepareStatement(revenueQueryBuilder.toString()); PreparedStatement pendingPaymentStmt = con.prepareStatement(pendingPaymentQueryBuilder.toString()); PreparedStatement refundsStmt = con.prepareStatement(refundsQueryBuilder.toString()); PreparedStatement avgPerOrderStmt = con.prepareStatement(avgPerOrderQueryBuilder.toString()); PreparedStatement totalBillsStmt = con.prepareStatement(totalBillsQueryBuilder.toString()); PreparedStatement canceledBillsStmt = con.prepareStatement(canceledBillsQueryBuilder.toString()); PreparedStatement onDeliveryOrPendingBillsStmt = con.prepareStatement(onDeliveryOrPendingBillsQueryBuilder.toString()); PreparedStatement successBillsStmt = con.prepareStatement(successBillsQueryBuilder.toString()); PreparedStatement totalCustomerStmt = con.prepareStatement(totalCustomerQueryBuilder.toString()); PreparedStatement newlyRegStmt = con.prepareStatement(newlyRegQueryBuilder.toString()); PreparedStatement newlyBoughtStmt = con.prepareStatement(newlyBoughtQueryBuilder.toString()); PreparedStatement bannedCustomerStmt = con.prepareStatement(bannedCustomerQueryBuilder.toString()); PreparedStatement totalProductFeedbackStmt = con.prepareStatement(totalProductFeedbackQueryBuilder.toString()); PreparedStatement avgProductStarStmt = con.prepareStatement(avgProductStarQueryBuilder.toString()); PreparedStatement totalShopFeedbackStmt = con.prepareStatement(totalShopFeedbackQueryBuilder.toString()); PreparedStatement avgShopStarStmt = con.prepareStatement(avgShopStarQueryBuilder.toString())) {

            if (filter.equals("date") || filter.equals("month")) {
                // Assuming 'date' is passed in 'yyyy-mm-dd' format for date filter
                if (filter.equals("date")) {
                    revenueStmt.setString(1, date);
                    pendingPaymentStmt.setString(1, date);
                    refundsStmt.setString(1, date);
                    avgPerOrderStmt.setString(1, date);
                    totalBillsStmt.setString(1, date);
                    canceledBillsStmt.setString(1, date);
                    onDeliveryOrPendingBillsStmt.setString(1, date);
                    successBillsStmt.setString(1, date);
                    totalCustomerStmt.setString(1, date);
                    newlyRegStmt.setString(1, date);
                    newlyBoughtStmt.setString(1, date);
                    totalProductFeedbackStmt.setString(1, date);
                    avgProductStarStmt.setString(1, date);
                    totalShopFeedbackStmt.setString(1, date);
                    avgShopStarStmt.setString(1, date);
                } else {
                    String[] dateParts = date.split("-");
                    String year = dateParts[0];
                    String month = dateParts[1];

                    System.out.println("year: " + year + ", Month: " + month);

                    // For month filter, use the provided date as both month and year
                    revenueStmt.setString(1, month);
                    pendingPaymentStmt.setString(1, month);
                    refundsStmt.setString(1, month);
                    avgPerOrderStmt.setString(1, month);
                    totalBillsStmt.setString(1, month);
                    canceledBillsStmt.setString(1, month);
                    onDeliveryOrPendingBillsStmt.setString(1, month);
                    successBillsStmt.setString(1, month);
                    totalCustomerStmt.setString(1, month);
                    newlyRegStmt.setString(1, month);
                    newlyBoughtStmt.setString(1, month);
                    totalProductFeedbackStmt.setString(1, month);
                    avgProductStarStmt.setString(1, month);
                    totalShopFeedbackStmt.setString(1, month);
                    avgShopStarStmt.setString(1, month);
                    // The same date parameter for all statements
                    revenueStmt.setString(2, year);
                    pendingPaymentStmt.setString(2, year);
                    refundsStmt.setString(2, year);
                    avgPerOrderStmt.setString(2, year);
                    totalBillsStmt.setString(2, year);
                    canceledBillsStmt.setString(2, year);
                    onDeliveryOrPendingBillsStmt.setString(2, year);
                    successBillsStmt.setString(2, year);
                    totalCustomerStmt.setString(2, year);
                    newlyRegStmt.setString(2, year);
                    newlyBoughtStmt.setString(2, year);
                    totalProductFeedbackStmt.setString(2, year);
                    avgProductStarStmt.setString(2, year);
                    totalShopFeedbackStmt.setString(2, year);
                    avgShopStarStmt.setString(2, year);
                }
            }

            // Execute statements and set results to content object
            try (ResultSet rs = revenueStmt.executeQuery()) {
                if (rs.next()) {
                    content.setTotalRevenue(rs.getDouble("Revenue"));
                }
            }

            try (ResultSet rs = pendingPaymentStmt.executeQuery()) {
                if (rs.next()) {
                    content.setPendingPayment(rs.getDouble("PendingRevenue"));
                }
            }

            try (ResultSet rs = refundsStmt.executeQuery()) {
                if (rs.next()) {
                    content.setRefunds(rs.getDouble("Refunds"));
                }
            }

            try (ResultSet rs = avgPerOrderStmt.executeQuery()) {
                if (rs.next()) {
                    content.setAvgPerOrder(rs.getDouble("AvgPerOrder"));
                }
            }

            try (ResultSet rs = totalBillsStmt.executeQuery()) {
                if (rs.next()) {
                    content.setTotalBills(rs.getInt("TotalBills"));
                }
            }

            try (ResultSet rs = canceledBillsStmt.executeQuery()) {
                if (rs.next()) {
                    content.setCanceledBills(rs.getInt("CanceledBills"));
                }
            }

            try (ResultSet rs = onDeliveryOrPendingBillsStmt.executeQuery()) {
                if (rs.next()) {
                    content.setOnDeliveryOrPendingBills(rs.getInt("PendingBills"));
                }
            }

            try (ResultSet rs = successBillsStmt.executeQuery()) {
                if (rs.next()) {
                    content.setSuccessBills(rs.getInt("SuccessBills"));
                }
            }

            try (ResultSet rs = totalCustomerStmt.executeQuery()) {
                if (rs.next()) {
                    content.setTotalCustomer(rs.getInt("TotalCustomer"));
                }
            }

            try (ResultSet rs = newlyRegStmt.executeQuery()) {
                if (rs.next()) {
                    content.setNewlyReg(rs.getInt("NewlyReg"));
                }
            }

            try (ResultSet rs = newlyBoughtStmt.executeQuery()) {
                if (rs.next()) {
                    content.setNewlyBought(rs.getInt("NewlyBought"));
                }
            }

            try (ResultSet rs = bannedCustomerStmt.executeQuery()) {
                if (rs.next()) {
                    content.setBannedCustomer(rs.getInt("BannedCustomer"));
                }
            }

            try (ResultSet rs = totalProductFeedbackStmt.executeQuery()) {
                if (rs.next()) {
                    content.setTotalProductFeedback(rs.getInt("TotalFeedback"));
                }
            }

            try (ResultSet rs = avgProductStarStmt.executeQuery()) {
                if (rs.next()) {
                    content.setAvgProductStar(rs.getDouble("AvgStar"));
                }
            }

            try (ResultSet rs = totalShopFeedbackStmt.executeQuery()) {
                if (rs.next()) {
                    content.setTotalShopFeedback(rs.getInt("TotalFeedback"));
                }
            }

            try (ResultSet rs = avgShopStarStmt.executeQuery()) {
                if (rs.next()) {
                    content.setAvgShopStar(rs.getDouble("AvgStar"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return content;
    }

    public HoangAnhCustomEntity.DashBoardContent getDashBoardContentByFilterFromTo(String filter, String dateFrom, String dateTo) {
        HoangAnhCustomEntity.DashBoardContent content = new HoangAnhCustomEntity.DashBoardContent();

        // SQL query templates
        StringBuilder revenueQueryBuilder = new StringBuilder("SELECT SUM(TotalPrice) AS Revenue FROM bills WHERE Status = 'Received'");
        StringBuilder pendingPaymentQueryBuilder = new StringBuilder("SELECT SUM(TotalPrice) AS PendingRevenue FROM bills WHERE Status IN ('OnDelivery', 'Pending')");
        StringBuilder refundsQueryBuilder = new StringBuilder("SELECT SUM(TotalPrice) AS Refunds FROM bills WHERE Status = 'Canceled'");
        StringBuilder avgPerOrderQueryBuilder = new StringBuilder("SELECT AVG(TotalPrice) AS AvgPerOrder FROM bills");
        StringBuilder totalBillsQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS TotalBills FROM bills");
        StringBuilder canceledBillsQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS CanceledBills FROM bills WHERE Status = 'Canceled'");
        StringBuilder onDeliveryOrPendingBillsQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS PendingBills FROM bills WHERE Status IN ('OnDelivery', 'Pending')");
        StringBuilder successBillsQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS SuccessBills FROM bills WHERE Status = 'Received'");
        StringBuilder totalCustomerQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS TotalCustomer FROM user WHERE RoleID = 1");
        StringBuilder newlyRegQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS NewlyReg FROM user WHERE RoleID = 1");
        StringBuilder newlyBoughtQueryBuilder = new StringBuilder("SELECT COUNT(u.ID) AS NewlyBought FROM bills b JOIN user u ON b.CustomerID = u.ID WHERE u.RoleID = 1");
        StringBuilder bannedCustomerQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS BannedCustomer FROM user WHERE RoleID = 1 AND Status = 'banned'");
        StringBuilder totalProductFeedbackQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS TotalFeedback FROM feedback");
        StringBuilder avgProductStarQueryBuilder = new StringBuilder("SELECT AVG(Star) AS AvgStar FROM feedback");
        StringBuilder totalShopFeedbackQueryBuilder = new StringBuilder("SELECT COUNT(ID) AS TotalFeedback FROM billfeedback");
        StringBuilder avgShopStarQueryBuilder = new StringBuilder("SELECT AVG(Star) AS AvgStar FROM billfeedback");

        // Modify queries if filter is "fromTo"
        if ("fromTo".equals(filter)) {
            revenueQueryBuilder.append(" AND DATE(PublishDate) BETWEEN ? AND ?");
            pendingPaymentQueryBuilder.append(" AND DATE(PublishDate) BETWEEN ? AND ?");
            refundsQueryBuilder.append(" AND DATE(PublishDate) BETWEEN ? AND ?");
            avgPerOrderQueryBuilder.append(" WHERE DATE(PublishDate) BETWEEN ? AND ?");
            totalBillsQueryBuilder.append(" WHERE DATE(PublishDate) BETWEEN ? AND ?");
            canceledBillsQueryBuilder.append(" AND DATE(PublishDate) BETWEEN ? AND ?");
            onDeliveryOrPendingBillsQueryBuilder.append(" AND DATE(PublishDate) BETWEEN ? AND ?");
            successBillsQueryBuilder.append(" AND DATE(PublishDate) BETWEEN ? AND ?");
            totalCustomerQueryBuilder.append(" AND DATE(CreateAt) BETWEEN ? AND ?");
            newlyRegQueryBuilder.append(" AND DATE(CreateAt) BETWEEN ? AND ?");
            newlyBoughtQueryBuilder.append(" AND DATE(CreateAt) BETWEEN ? AND ?");
            totalProductFeedbackQueryBuilder.append(" WHERE DATE(CreateAt) BETWEEN ? AND ?");
            avgProductStarQueryBuilder.append(" WHERE DATE(CreateAt) BETWEEN ? AND ?");
            totalShopFeedbackQueryBuilder.append(" WHERE DATE(CreateAt) BETWEEN ? AND ?");
            avgShopStarQueryBuilder.append(" WHERE DATE(CreateAt) BETWEEN ? AND ?");
        }

        try (Connection con = dbc.getConnection()) {
            // Define and execute each query, setting date parameters if necessary
            try (PreparedStatement revenueStmt = con.prepareStatement(revenueQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    revenueStmt.setString(1, dateFrom);
                    revenueStmt.setString(2, dateTo);
                }
                try (ResultSet rs = revenueStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setTotalRevenue(rs.getDouble("Revenue"));
                    }
                }
            }

            try (PreparedStatement pendingPaymentStmt = con.prepareStatement(pendingPaymentQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    pendingPaymentStmt.setString(1, dateFrom);
                    pendingPaymentStmt.setString(2, dateTo);
                }
                try (ResultSet rs = pendingPaymentStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setPendingPayment(rs.getDouble("PendingRevenue"));
                    }
                }
            }

            try (PreparedStatement refundsStmt = con.prepareStatement(refundsQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    refundsStmt.setString(1, dateFrom);
                    refundsStmt.setString(2, dateTo);
                }
                try (ResultSet rs = refundsStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setRefunds(rs.getDouble("Refunds"));
                    }
                }
            }

            try (PreparedStatement avgPerOrderStmt = con.prepareStatement(avgPerOrderQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    avgPerOrderStmt.setString(1, dateFrom);
                    avgPerOrderStmt.setString(2, dateTo);
                }
                try (ResultSet rs = avgPerOrderStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setAvgPerOrder(rs.getDouble("AvgPerOrder"));
                    }
                }
            }

            try (PreparedStatement totalBillsStmt = con.prepareStatement(totalBillsQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    totalBillsStmt.setString(1, dateFrom);
                    totalBillsStmt.setString(2, dateTo);
                }
                try (ResultSet rs = totalBillsStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setTotalBills(rs.getInt("TotalBills"));
                    }
                }
            }

            try (PreparedStatement canceledBillsStmt = con.prepareStatement(canceledBillsQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    canceledBillsStmt.setString(1, dateFrom);
                    canceledBillsStmt.setString(2, dateTo);
                }
                try (ResultSet rs = canceledBillsStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setCanceledBills(rs.getInt("CanceledBills"));
                    }
                }
            }

            try (PreparedStatement onDeliveryOrPendingBillsStmt = con.prepareStatement(onDeliveryOrPendingBillsQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    onDeliveryOrPendingBillsStmt.setString(1, dateFrom);
                    onDeliveryOrPendingBillsStmt.setString(2, dateTo);
                }
                try (ResultSet rs = onDeliveryOrPendingBillsStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setOnDeliveryOrPendingBills(rs.getInt("PendingBills"));
                    }
                }
            }

            try (PreparedStatement successBillsStmt = con.prepareStatement(successBillsQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    successBillsStmt.setString(1, dateFrom);
                    successBillsStmt.setString(2, dateTo);
                }
                try (ResultSet rs = successBillsStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setSuccessBills(rs.getInt("SuccessBills"));
                    }
                }
            }

            try (PreparedStatement totalCustomerStmt = con.prepareStatement(totalCustomerQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    totalCustomerStmt.setString(1, dateFrom);
                    totalCustomerStmt.setString(2, dateTo);
                }
                try (ResultSet rs = totalCustomerStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setTotalCustomer(rs.getInt("TotalCustomer"));
                    }
                }
            }

            try (PreparedStatement newlyRegStmt = con.prepareStatement(newlyRegQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    newlyRegStmt.setString(1, dateFrom);
                    newlyRegStmt.setString(2, dateTo);
                }
                try (ResultSet rs = newlyRegStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setNewlyReg(rs.getInt("NewlyReg"));
                    }
                }
            }

            try (PreparedStatement newlyBoughtStmt = con.prepareStatement(newlyBoughtQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    newlyBoughtStmt.setString(1, dateFrom);
                    newlyBoughtStmt.setString(2, dateTo);
                }
                try (ResultSet rs = newlyBoughtStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setNewlyBought(rs.getInt("NewlyBought"));
                    }
                }
            }

            try (PreparedStatement bannedCustomerStmt = con.prepareStatement(bannedCustomerQueryBuilder.toString())) {
                try (ResultSet rs = bannedCustomerStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setBannedCustomer(rs.getInt("BannedCustomer"));
                    }
                }
            }

            try (PreparedStatement totalProductFeedbackStmt = con.prepareStatement(totalProductFeedbackQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    totalProductFeedbackStmt.setString(1, dateFrom);
                    totalProductFeedbackStmt.setString(2, dateTo);
                }
                try (ResultSet rs = totalProductFeedbackStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setTotalProductFeedback(rs.getInt("TotalFeedback"));
                    }
                }
            }

            try (PreparedStatement avgProductStarStmt = con.prepareStatement(avgProductStarQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    avgProductStarStmt.setString(1, dateFrom);
                    avgProductStarStmt.setString(2, dateTo);
                }
                try (ResultSet rs = avgProductStarStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setAvgProductStar(rs.getDouble("AvgStar"));
                    }
                }
            }

            try (PreparedStatement totalShopFeedbackStmt = con.prepareStatement(totalShopFeedbackQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    totalShopFeedbackStmt.setString(1, dateFrom);
                    totalShopFeedbackStmt.setString(2, dateTo);
                }
                try (ResultSet rs = totalShopFeedbackStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setTotalShopFeedback(rs.getInt("TotalFeedback"));
                    }
                }
            }

            try (PreparedStatement avgShopStarStmt = con.prepareStatement(avgShopStarQueryBuilder.toString())) {
                if ("fromTo".equals(filter)) {
                    avgShopStarStmt.setString(1, dateFrom);
                    avgShopStarStmt.setString(2, dateTo);
                }
                try (ResultSet rs = avgShopStarStmt.executeQuery()) {
                    if (rs.next()) {
                        content.setAvgShopStar(rs.getDouble("AvgStar"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return content;
    }

    public List<HoangAnhCustomEntity.RevenueChart> getRevenueChartData() {
        List<HoangAnhCustomEntity.RevenueChart> revenueChart = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.RevenueChart> revenueChartMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    YEAR(PublishDate) AS Year,\n"
                + "    MONTH(PublishDate) AS Month,\n"
                + "    SUM(TotalPrice) AS MonthlyRevenue\n"
                + "FROM \n"
                + "    bills\n"
                + "WHERE\n"
                + "	Status = 'Received'\n"
                + "GROUP BY \n"
                + "    YEAR(PublishDate), MONTH(PublishDate)\n"
                + "ORDER BY \n"
                + "    Year, Month;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int year = rs.getInt("Year");
                HoangAnhCustomEntity.RevenueChart revenue;

                // Check if the product already exists in the map
                if (revenueChartMap.containsKey(year)) {
                    // Product exists, just add the new tag
                    revenue = revenueChartMap.get(year);
                    revenue.getRevenueByMonth().add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("MonthlyRevenue")));
                } else {
                    // Create a new product entry
                    revenue = new HoangAnhCustomEntity.RevenueChart();
                    revenue.setYear(rs.getInt("Year"));

                    // Initialize the tag list with the first tag
                    List<HoangAnhCustomEntity.YearRevenue> yList = new ArrayList<>();
                    yList.add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("MonthlyRevenue")));
                    revenue.setRevenueByMonth(yList);

                    // Add the product to the map
                    revenueChartMap.put(year, revenue);
                }
            }

            // Convert the map values to a list
            revenueChart.addAll(revenueChartMap.values());

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
        return revenueChart;
    }

    public List<HoangAnhCustomEntity.RevenueChart> getPendingChartData() {
        List<HoangAnhCustomEntity.RevenueChart> revenueChart = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.RevenueChart> revenueChartMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    YEAR(PublishDate) AS Year,\n"
                + "    MONTH(PublishDate) AS Month,\n"
                + "    SUM(TotalPrice) AS MonthlyRevenue\n"
                + "FROM \n"
                + "    bills\n"
                + "WHERE\n"
                + "	Status = 'Pending'\n"
                + "GROUP BY \n"
                + "    YEAR(PublishDate), MONTH(PublishDate)\n"
                + "ORDER BY \n"
                + "    Year, Month;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int year = rs.getInt("Year");
                HoangAnhCustomEntity.RevenueChart revenue;

                // Check if the product already exists in the map
                if (revenueChartMap.containsKey(year)) {
                    // Product exists, just add the new tag
                    revenue = revenueChartMap.get(year);
                    revenue.getRevenueByMonth().add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("MonthlyRevenue")));
                } else {
                    // Create a new product entry
                    revenue = new HoangAnhCustomEntity.RevenueChart();
                    revenue.setYear(rs.getInt("Year"));

                    // Initialize the tag list with the first tag
                    List<HoangAnhCustomEntity.YearRevenue> yList = new ArrayList<>();
                    yList.add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("MonthlyRevenue")));
                    revenue.setRevenueByMonth(yList);

                    // Add the product to the map
                    revenueChartMap.put(year, revenue);
                }
            }

            // Convert the map values to a list
            revenueChart.addAll(revenueChartMap.values());

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
        return revenueChart;
    }

    public List<HoangAnhCustomEntity.RevenueChart> getRefundChartData() {
        List<HoangAnhCustomEntity.RevenueChart> revenueChart = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.RevenueChart> revenueChartMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    YEAR(PublishDate) AS Year,\n"
                + "    MONTH(PublishDate) AS Month,\n"
                + "    SUM(TotalPrice) AS MonthlyRevenue\n"
                + "FROM \n"
                + "    bills\n"
                + "WHERE\n"
                + "	Status = 'Canceled'\n"
                + "GROUP BY \n"
                + "    YEAR(PublishDate), MONTH(PublishDate)\n"
                + "ORDER BY \n"
                + "    Year, Month;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int year = rs.getInt("Year");
                HoangAnhCustomEntity.RevenueChart revenue;

                // Check if the product already exists in the map
                if (revenueChartMap.containsKey(year)) {
                    // Product exists, just add the new tag
                    revenue = revenueChartMap.get(year);
                    revenue.getRevenueByMonth().add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("MonthlyRevenue")));
                } else {
                    // Create a new product entry
                    revenue = new HoangAnhCustomEntity.RevenueChart();
                    revenue.setYear(rs.getInt("Year"));

                    // Initialize the tag list with the first tag
                    List<HoangAnhCustomEntity.YearRevenue> yList = new ArrayList<>();
                    yList.add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("MonthlyRevenue")));
                    revenue.setRevenueByMonth(yList);

                    // Add the product to the map
                    revenueChartMap.put(year, revenue);
                }
            }

            // Convert the map values to a list
            revenueChart.addAll(revenueChartMap.values());

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
        return revenueChart;
    }

    public List<HoangAnhCustomEntity.RevenueChart> getAVGChartData() {
        List<HoangAnhCustomEntity.RevenueChart> revenueChart = new LinkedList<>();
        Map<Integer, HoangAnhCustomEntity.RevenueChart> revenueChartMap = new HashMap<>(); // Map to track products by ID

        // Base query
        String sql = "SELECT \n"
                + "    YEAR(PublishDate) AS Year,\n"
                + "    MONTH(PublishDate) AS Month,\n"
                + "    AVG(TotalPrice) AS AVG\n"
                + "FROM \n"
                + "    bills\n"
                + "GROUP BY \n"
                + "    YEAR(PublishDate), \n"
                + "    MONTH(PublishDate)\n"
                + "ORDER BY \n"
                + "    year, \n"
                + "    month;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int year = rs.getInt("Year");
                HoangAnhCustomEntity.RevenueChart revenue;

                // Check if the product already exists in the map
                if (revenueChartMap.containsKey(year)) {
                    // Product exists, just add the new tag
                    revenue = revenueChartMap.get(year);
                    revenue.getRevenueByMonth().add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("AVG")));
                } else {
                    // Create a new product entry
                    revenue = new HoangAnhCustomEntity.RevenueChart();
                    revenue.setYear(rs.getInt("Year"));

                    // Initialize the tag list with the first tag
                    List<HoangAnhCustomEntity.YearRevenue> yList = new ArrayList<>();
                    yList.add(new HoangAnhCustomEntity.YearRevenue(rs.getInt("Month"), rs.getInt("AVG")));
                    revenue.setRevenueByMonth(yList);

                    // Add the product to the map
                    revenueChartMap.put(year, revenue);
                }
            }

            // Convert the map values to a list
            revenueChart.addAll(revenueChartMap.values());

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
        return revenueChart;
    }

    public List<Integer> getAllBillYear() {
        List<Integer> year = new LinkedList<>();

        // Base query
        String sql = "SELECT DISTINCT\n"
                + "    YEAR(PublishDate) AS Year\n"
                + "FROM\n"
                + "    defaultdb.bills;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                year.add(rs.getInt("Year"));
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
        return year;
    }

    public static void main(String[] args) throws SQLException {
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
//        System.out.println(ceDAO.getProductListInformation(1, 9).get(0));
//        HoangAnhCustomEntity.ProductDetailDTO pd = ceDAO.getDetailProductbyId(1);
//        System.out.println(pd);
//        List cedao = ceDAO.getProductAttributeById(1);
//        System.out.println(cedao.get(1));
//        System.out.println(ceDAO.validUserCart(1));
//        List<StringBuilder> img = new ArrayList<>();
//        StringBuilder imgBuild = new StringBuilder();

//        img.add(imgBuild);
//        System.out.println(ceDAO.getFeedbackBillID(1, 1));
//        String[] imgList = new String[2];
//        imgList[0] = "testfunction";
//        ceDAO.insertBillFeedbackMedia(1, 1, imgList);
//        System.out.println(ceDAO.getAllStaffByRoleId(2));
//        ceDAO.saveBillInfor(4, "OnDelivery", 3);
//        System.out.println(ceDAO.getDashBoardContentByFilterFromTo("fromTo", "2024-01-10", "2024-03-31"));
//        System.out.println(ceDAO.getOrderByBillID(1));
//        System.out.println(ceDAO.getFeedbackProductID(1, ));
//        ceDAO.getDetailProductbyId(1).getProductDescription());
        System.out.println(ceDAO.getProductAttributeById(48));
    }
}
