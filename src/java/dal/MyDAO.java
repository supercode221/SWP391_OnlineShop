package dal;

import dal.codebase.DBContext;
import entity.codebaseOld.Category;
import entity.codebaseOld.Tag;
import entity.codebaseOld.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class MyDAO extends DBContext {

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    public int deleteProductFromCartByProductIdAndUserId(int productId, int userId, String colorName, String size) {
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
                    + "AND col.Name = ? "
                    + "AND s.Size = ? "
                    + ";";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setString(3, colorName);
            ps.setString(4, size);
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

    public List<Tag> getTagListFromACart(CartDetails cartDetails) {
        List<Tag> tags = new LinkedList<>();

        try {
            String sql = ""
                    + "SELECT DISTINCT "
                    + "     t.ID as TagID, "
                    + "     t.Name as TagName, "
                    + "     t.Color as TagColorCode "
                    + "FROM "
                    + "     CartProduct cp "
                    + "     JOIN Product p on p.ID = cp.ProductID "
                    + "     JOIN TagProduct tp on p.ID = tp.ProductID "
                    + "     JOIN Tag t on t.ID = tp.TagID "
                    + "WHERE "
                    + "     cp.CartID = ?; ";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cartDetails.cartId);
            rs = ps.executeQuery();
            while (rs.next()) {
                tags.add(new Tag(rs.getInt("TagID"), rs.getString("TagName"), rs.getString("TagColorCode")));
            }
        } catch (SQLException e) {
            tags = null;
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

        return tags;
    }

    public List<Category> getCategoryListFromACart(CartDetails cartDetails) {
        List<Category> categories = new LinkedList<>();

        try {
            String sql = ""
                    + "SELECT DISTINCT "
                    + "     cat.ID as CategoryID, "
                    + "     cat.Name as CategoryName "
                    + "FROM "
                    + "     CartProduct cp "
                    + "     JOIN Product p on p.ID = cp.ProductID "
                    + "     JOIN SubCategory cat on cat.ID = p.SubCategoryID "
                    + "WHERE "
                    + "     cp.CartID = ?; ";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cartDetails.cartId);
            rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        null)
                );
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

    public CartDetails doSearchAndFilterLogic(int userID, String category, String tag, String searchInput) {
        CartDetails cartDetails = new CartDetails();
        List<Product> productList = new LinkedList<>();
        int cartId = -1;

        try {
            String sql = """
                         SELECT 
                             c.ID as CartID,
                             p.ID as ProductID,
                             p.Name as ProductName,
                             p.ThumbnailImage as ProductThumbnailImage,
                             p.Price as ProductPrice,
                             co.Name as ColorName,
                             co.ColorCode as ColorCode,
                             s.Size as SizeName,
                             cp.Quantity as ProductQuantity,
                             t.Name as ProductTagName,
                             t.Color as ProductTagColorCode, 
                             cp.Checked as Checked, 
                             pcs.Quantity as Stock 
                         FROM Cart c 
                             JOIN CartProduct cp ON c.ID = cp.CartID  
                             JOIN Color co ON co.ID = cp.ColorID 
                             JOIN Size s ON s.ID = cp.SizeID 
                             JOIN Product p ON cp.ProductID = p.ID 
                             JOIN TagProduct tp ON p.ID = tp.ProductID 
                             JOIN Tag t ON tp.TagID = t.ID    
                             LEFT JOIN product_color_size pcs ON pcs.ProductID = cp.ProductID 
                                                              AND pcs.ColorID = cp.ColorID 
                                                              AND pcs.SizeID = cp.SizeID 
                         WHERE 
                             c.UserID = ? 
                             AND p.Name LIKE ? """;

            // check and add param
            boolean haveTag = !"all".equalsIgnoreCase(tag) && tag != null && !tag.isBlank() && !tag.isEmpty();
            boolean haveCategory = !"all".equalsIgnoreCase(category) && category != null && !category.isEmpty() && !category.isBlank();
            boolean haveSearchInput = searchInput != null && !searchInput.isEmpty() && !searchInput.isBlank();

            StringBuilder sqlBuilder = new StringBuilder(sql);
            if (haveTag) {
                sqlBuilder.append(" AND t.ID = ? ");
            }
            if (haveCategory) {
                sqlBuilder.append(" AND p.SubCategoryID = ? ");
            }
            if (!haveSearchInput) {
                searchInput = "";
            }
            sqlBuilder.append(";");

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sqlBuilder.toString());

            int index = 0;
            ps.setInt(++index, userID);
            ps.setString(++index, "%" + searchInput + "%");
            if (haveTag) {
                ps.setInt(++index, Integer.parseInt(tag));
            }
            if (haveCategory) {
                ps.setInt(++index, Integer.parseInt(category));
            }

            // process results
            rs = ps.executeQuery();
            while (rs.next()) {
                if (cartId == -1) {
                    // Set cart ID once (from the first result)
                    cartId = rs.getInt("CartID");
                }

                // Create product object and set details
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setColorName(rs.getString("ColorName"));
                product.setSize(rs.getString("SizeName"));
                if (productList.size() != 0) {
                    boolean isDuplicatedProduct = false;
                    for (Product p : productList) {
                        if (p.equals(product)) {
                            isDuplicatedProduct = true;
                            break;
                        }
                    }
                    if (!isDuplicatedProduct) {
                        product.setIsChecked(rs.getInt("Checked"));
                        product.setThumbnailImage(rs.getString("ProductThumbnailImage"));
                        product.setName(rs.getString("ProductName"));
                        product.setPrice(rs.getDouble("ProductPrice"));
                        product.setColorCode(rs.getString("ColorCode"));
                        product.setQuantity(rs.getInt("ProductQuantity"));
                        String raw_stock = rs.getString("Stock");
                        if (raw_stock == null || raw_stock.isEmpty() || raw_stock.isBlank()) {
                            product.setStock(0);
                        } else {
                            product.setStock(rs.getInt("Stock"));
                        }
                        Tag t = new Tag();
                        t.setName(rs.getString("ProductTagName"));
                        t.setColorCode(rs.getString("ProductTagColorCode"));
                        List<Tag> tags = new LinkedList<>();
                        tags.add(t);
                        product.setTagList(tags);
                        productList.add(product); //add product to the list
                    } else {
                        Tag t = new Tag();
                        t.setName(rs.getString("ProductTagName"));
                        t.setColorCode(rs.getString("ProductTagColorCode"));
                        int indexOfLastItem = productList.size() - 1;
                        productList.get(indexOfLastItem).getTagList().add(t);
                    }
                } else {
                    product.setIsChecked(rs.getInt("Checked"));
                    product.setThumbnailImage(rs.getString("ProductThumbnailImage"));
                    product.setName(rs.getString("ProductName"));
                    product.setPrice(rs.getDouble("ProductPrice"));
                    product.setColorCode(rs.getString("ColorCode"));
                    product.setQuantity(rs.getInt("ProductQuantity"));
                    Tag t = new Tag();
                    t.setName(rs.getString("ProductTagName"));
                    t.setColorCode(rs.getString("ProductTagColorCode"));
                    List<Tag> tags = new LinkedList<>();
                    tags.add(t);
                    product.setTagList(tags);
                    productList.add(product); //add product to the list
                }
            }

            if (cartId != -1) {
                cartDetails = new CartDetails(cartId, productList);
            }
            // dont worry, it's works

        } catch (SQLException e) {
            productList = null;
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

        return cartDetails;
    }

    public String getKeywordForProductId(int productId) {
        String keyword = new String();

        try {
            String sql = ""
                    + "SELECT "
                    + "     p.Name as ProductName, "
                    + "     t.Name as TagName, "
                    + "     c.Name as CategoryName "
                    + "FROM "
                    + "     Product p "
                    + "     JOIN TagProduct tp on p.ID = tp.ProductID "
                    + "     JOIN Tag t on t.ID = tp.TagID"
                    + "     JOIN SubCategory c on c.ID = p.SubCategoryID "
                    + "WHERE "
                    + "     p.ID = ?; ";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                keyword += rs.getString("TagName") + " ";
                keyword += rs.getString("CategoryName");
            }
        } catch (SQLException e) {
            keyword = null;
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

        return keyword;
    }

    public boolean saveCheckedProduct(int userID, StringBuilder completedParam) {
        boolean isSuccess = false;

        try {
            String sql = ""
                    + "UPDATE "
                    + "     CartProduct "
                    + "SET "
                    + "     Checked = 1 "
                    + "WHERE "
                    + "     CartID = ("
                    + "         SELECT "
                    + "             ID "
                    + "         FROM "
                    + "             Cart c "
                    + "         WHERE "
                    + "             c.UserID = ?"
                    + "     ) "
                    + "     AND ProductID IN " + completedParam + ";";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            if (ps.executeUpdate() < 1) {
                throw new Exception();
            }

            sql = ""
                    + "UPDATE "
                    + "     CartProduct "
                    + "SET "
                    + "     Checked = 0 "
                    + "WHERE "
                    + "     CartID = ("
                    + "         SELECT "
                    + "             ID "
                    + "         FROM "
                    + "             Cart c "
                    + "         WHERE "
                    + "             c.UserID = ?"
                    + "     ) "
                    + "     AND ProductID NOT NOT IN " + completedParam + ";";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            if (ps.executeUpdate() > 0) {
                isSuccess = true;
            }

        } catch (Exception e) {
            isSuccess = false;
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
            } catch (SQLException sqlEx) {
                isSuccess = false;
                sqlEx.printStackTrace();
            }
        }

        return isSuccess;
    }

    public boolean saveQuantityProduct(int userID, String[] productIdsArray, String[] quantitiesArray) {
        boolean isSuccess = false;

        if (productIdsArray.length != quantitiesArray.length) {
            return false;
        }

        try {
            connection = dbc.getConnection();
            connection.setAutoCommit(false);
            String sql = ""
                    + "UPDATE "
                    + "     CartProduct "
                    + "SET "
                    + "     Quantity = ? "
                    + "WHERE "
                    + "     CartID = ("
                    + "         SELECT "
                    + "             ID "
                    + "         FROM "
                    + "             Cart "
                    + "         WHERE UserID = ?"
                    + "     ) "
                    + "     AND ProductID = ?;";
            try {
                ps = connection.prepareStatement(sql);
                for (int i = 0; i < productIdsArray.length; i++) {
                    int quantity = Integer.parseInt(quantitiesArray[i]);
                    int productId = Integer.parseInt(productIdsArray[i]);

                    ps.setInt(1, quantity);
                    ps.setInt(2, userID);
                    ps.setInt(3, productId);

                    ps.addBatch();
                }

                int[] results = ps.executeBatch();

                if (results.length == productIdsArray.length) {
                    connection.commit();
                    isSuccess = true;

                } else {
                    connection.rollback();
                }

            } catch (Exception e) {
                isSuccess = false;
                connection.rollback();
            }

        } catch (Exception e) {
            isSuccess = false;
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
            } catch (SQLException sqlEx) {
                isSuccess = false;
                sqlEx.printStackTrace();
            }
        }

        return isSuccess;
    }

    public boolean updateQuantityAndCheckedProduct(int userID, String[] displayedItemIdArray, String[] quantitiesArray, String[] checkedItemIdsArray) {
        boolean isSuccess = false;

        String[] checkMap = new String[displayedItemIdArray.length];
        List<String> checkItemIdList = new ArrayList<>();
        for (int i = 0; i < checkedItemIdsArray.length; i++) {
            checkItemIdList.add(checkedItemIdsArray[i]);
        }
        for (int i = 0; i < displayedItemIdArray.length; i++) {
            if (checkItemIdList.contains(displayedItemIdArray[i])) {
                checkMap[i] = "1";
            } else {
                checkMap[i] = "0";
            }
        }

        try {
            connection = dbc.getConnection();
            connection.setAutoCommit(false);
            try {
                String sql = ""
                        + "UPDATE "
                        + "     CartProduct "
                        + "SET "
                        + "     Quantity = ?, "
                        + "     Checked = ? "
                        + "WHERE "
                        + "     CartID = ("
                        + "         SELECT "
                        + "             ID "
                        + "         FROM "
                        + "             Cart "
                        + "         WHERE UserID = ?"
                        + "     ) "
                        + "     AND ProductID = ?;";

                ps = connection.prepareStatement(sql);
                for (int i = 0; i < displayedItemIdArray.length; i++) {
                    int itemId = Integer.parseInt(displayedItemIdArray[i]);
                    int itemQuantity = Integer.parseInt(quantitiesArray[i]);
                    int itemIsChecked = Integer.parseInt(checkMap[i]);

                    ps.setInt(1, itemQuantity);
                    ps.setInt(2, itemIsChecked);
                    ps.setInt(3, userID);
                    ps.setInt(4, itemId);

                    ps.addBatch();
                }

                int[] results = ps.executeBatch();
                if (results.length != displayedItemIdArray.length) {
                    isSuccess = false;
                    connection.rollback();
                } else {
                    connection.commit();
                    isSuccess = true;
                }

            } catch (Exception e) {
                isSuccess = false;
                connection.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            isSuccess = false;
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
            } catch (SQLException sqlEx) {
                isSuccess = false;
                sqlEx.printStackTrace();
            }
        }

        return isSuccess;
    }

    public boolean updateQuantity(int userID, int productId, String size, String colorName, int newQuantity) {
        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "	cartproduct "
                + "SET "
                + "	Quantity = ? "
                + "WHERE "
                + "	CartID = ( "
                + "		SELECT "
                + "			CartID "
                + "		FROM "
                + "			cart "
                + "		WHERE "
                + "			UserID = ? "
                + "    ) "
                + "    AND ProductID = ? "
                + "    AND SizeID = ( "
                + "		SELECT "
                + "			ID "
                + "		FROM "
                + "			size "
                + "		WHERE "
                + "			Size = ? "
                + "    ) "
                + "    AND ColorID = ( "
                + "		SELECT "
                + "			ID "
                + "		FROM "
                + "			color "
                + "		WHERE "
                + "			Name = ? "
                + "    );";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, newQuantity);
            ps.setInt(2, userID);
            ps.setInt(3, productId);
            ps.setString(4, size);
            ps.setString(5, colorName);
            if (ps.executeUpdate() == 1) {
                isSuccess = true;
            }

        } catch (Exception e) {
            isSuccess = false;
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
            } catch (SQLException sqlEx) {
                isSuccess = false;
                sqlEx.printStackTrace();
            }
        }

        return isSuccess;
    }

    public boolean updateCheckbox(int userID, int productId, String size, String colorName, int checked) {
        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "	cartproduct "
                + "SET "
                + "	Checked = ? "
                + "WHERE "
                + "	CartID = ( "
                + "		SELECT "
                + "			CartID "
                + "		FROM "
                + "			cart "
                + "		WHERE "
                + "			UserID = ? "
                + "    ) "
                + "    AND ProductID = ? "
                + "    AND SizeID = ( "
                + "		SELECT "
                + "			ID "
                + "		FROM "
                + "			size "
                + "		WHERE "
                + "			Size = ? "
                + "    ) "
                + "    AND ColorID = ( "
                + "		SELECT "
                + "			ID "
                + "		FROM "
                + "			color "
                + "		WHERE "
                + "			Name = ? "
                + "    );";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, checked);
            ps.setInt(2, userID);
            ps.setInt(3, productId);
            ps.setString(4, size);
            ps.setString(5, colorName);
            if (ps.executeUpdate() == 1) {
                isSuccess = true;
            }

        } catch (Exception e) {
            isSuccess = false;
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
            } catch (SQLException sqlEx) {
                isSuccess = false;
                sqlEx.printStackTrace();
            }
        }

        return isSuccess;
    }

    public int getCategoryByProductId(int productId) {
        int categoryId = -1;
        String sql = ""
                + "SELECT "
                + "     SubCategoryID as CategoryID "
                + "FROM "
                + "     Product "
                + "WHERE "
                + "     ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                categoryId = rs.getInt("CategoryID");
            }

        } catch (Exception e) {
            categoryId = -1;
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
            } catch (SQLException sqlEx) {
                categoryId = -1;
                sqlEx.printStackTrace();
            }
        }

        return categoryId;
    }

    // Class to represent Product in cart
    public class Product {

        private int id;
        private String thumbnailImage;
        private String name;
        private double price;
        private int quantity;
        private String size;
        private String colorName;
        private String colorCode;
        private List<Tag> tagList;
        private int isChecked;
        private int stock;
        private String des;

        public Product setDes(String des) {
            this.des = des;
            return this;
        }

        public String getDes() {
            return des;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public void setIsChecked(int isChecked) {
            this.isChecked = isChecked;
        }

        public int getIsChecked() {
            return isChecked;
        }

        @Override
        public boolean equals(Object otherProduct) {
            if (!(otherProduct instanceof Product)) {
                return false;
            }
            Product o = (Product) otherProduct;
            return this.id == o.id && this.size.equalsIgnoreCase(o.size) && this.colorName.equalsIgnoreCase(o.colorName);
        }

        public List<Tag> getTagList() {
            return tagList;
        }

        public void setTagList(List<Tag> tagList) {
            this.tagList = tagList;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        // Getters and Setters
        public int getId() {
            return id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getColorName() {
            return colorName;
        }

        public void setColorName(String colorName) {
            this.colorName = colorName;
        }

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getThumbnailImage() {
            return thumbnailImage;
        }

        public void setThumbnailImage(String thumbnailImage) {
            this.thumbnailImage = thumbnailImage;
        }

        @Override
        public String toString() {
            return "Product{" + "id=" + id + ", thumbnailImage=" + thumbnailImage + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", size=" + size + ", colorName=" + colorName + ", colorCode=" + colorCode + ", tagList=" + tagList + '}';
        }
    }

    // Class to hold CartID and Products
    public class CartDetails {

        private int cartId;
        private List<Product> products;

        // Constructor
        public CartDetails() {
        }

        public CartDetails(int cartId, List<Product> products) {
            this.cartId = cartId;
            this.products = products;
        }

        // Getters
        public int getCartId() {
            return cartId;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        @Override
        public String toString() {
            return "CartDetails{" + "cartId=" + cartId + ", products=" + products + '}';
        }
    }

    // Function to retrieve the Cart ID and its products for a user
    public CartDetails getCartDetails(int userID) {
        CartDetails cartDetails = null;
        List<Product> productList = new LinkedList<>();
        int cartId = -1;

        try {
            // SQL query to get cart ID and product details
            String sql = """
                         SELECT 
                             c.ID as CartID,
                             p.ID as ProductID,
                             p.Name as ProductName,
                             p.ThumbnailImage as ProductThumbnailImage,
                             p.Price as ProductPrice,
                             co.Name as ColorName,
                             co.ColorCode as ColorCode,
                             s.Size as SizeName,
                             cp.Quantity as ProductQuantity,
                             t.ID as TagID, 
                             t.Name as ProductTagName,
                             t.Color as ProductTagColorCode, 
                             cp.Checked as Checked, 
                             p.Descriptionas ProductDescription, 
                             pcs.Quantity as Stock
                         FROM Cart c
                             JOIN CartProduct cp ON c.ID = cp.CartID 
                             JOIN Color co ON co.ID = cp.ColorID
                             JOIN Size s ON s.ID = cp.SizeID
                             JOIN Product p ON cp.ProductID = p.ID 
                             JOIN TagProduct tp ON p.ID = tp.ProductID 
                             JOIN Tag t ON tp.TagID = t.ID    
                             LEFT JOIN product_color_size pcs ON pcs.ProductID = cp.ProductID 
                                                              AND pcs.ColorID = cp.ColorID 
                                                              AND pcs.SizeID = cp.SizeID
                         WHERE
                             c.UserID = ?;""";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (cartId == -1) {
                    // Set cart ID once (from the first result)
                    cartId = rs.getInt("CartID");
                }

                // Create product object and set details
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setColorName(rs.getString("ColorName"));
                product.setSize(rs.getString("SizeName"));
                if (productList.size() != 0) {
                    boolean isDuplicatedProduct = false;
                    for (Product p : productList) {
                        if (p.equals(product)) {
                            isDuplicatedProduct = true;
                            break;
                        }
                    }
                    if (!isDuplicatedProduct) {
                        product.setIsChecked(rs.getInt("Checked"));
                        product.setThumbnailImage(rs.getString("ProductThumbnailImage"));
                        product.setName(rs.getString("ProductName"));
                        product.setPrice(rs.getDouble("ProductPrice"));
                        product.setColorCode(rs.getString("ColorCode"));
                        product.setDes(rs.getString("Description"));
                        product.setQuantity(rs.getInt("ProductQuantity"));
                        String raw_stock = rs.getString("Stock");
                        if (raw_stock == null || raw_stock.isEmpty() || raw_stock.isBlank()) {
                            product.setStock(0);
                        } else {
                            product.setStock(rs.getInt("Stock"));
                        }
                        Tag t = new Tag();
                        t.setId(rs.getInt("TagID"));
                        t.setName(rs.getString("ProductTagName"));
                        t.setColorCode(rs.getString("ProductTagColorCode"));
                        List<Tag> tags = new LinkedList<>();
                        tags.add(t);
                        product.setTagList(tags);
                        productList.add(product); //add product to the list
                    } else {
                        Tag t = new Tag();
                        t.setId(rs.getInt("TagID"));
                        t.setName(rs.getString("ProductTagName"));
                        t.setColorCode(rs.getString("ProductTagColorCode"));
                        int indexOfLastItem = productList.size() - 1;
                        productList.get(indexOfLastItem).getTagList().add(t);
                    }
                } else {
                    product.setIsChecked(rs.getInt("Checked"));
                    product.setThumbnailImage(rs.getString("ProductThumbnailImage"));
                    product.setName(rs.getString("ProductName"));
                    product.setPrice(rs.getDouble("ProductPrice"));
                    product.setColorCode(rs.getString("ColorCode"));
                    product.setDes(rs.getString("Description"));
                    product.setQuantity(rs.getInt("ProductQuantity"));
                    String raw_stock = rs.getString("Stock");
                    if (raw_stock == null || raw_stock.isEmpty() || raw_stock.isBlank()) {
                        product.setStock(0);
                    } else {
                        product.setStock(rs.getInt("Stock"));
                    }
                    Tag t = new Tag();
                    t.setId(rs.getInt("TagID"));
                    t.setName(rs.getString("ProductTagName"));
                    t.setColorCode(rs.getString("ProductTagColorCode"));
                    List<Tag> tags = new LinkedList<>();
                    tags.add(t);
                    product.setTagList(tags);
                    productList.add(product); //add product to the list
                }
            }

            // Create cart details object with cart ID and product list
            if (cartId != -1) {
                cartDetails = new CartDetails(cartId, productList);
            }
        } catch (SQLException e) {
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

        return cartDetails;
    }

    public int getProductCountInCart(int userID) {
        int productCount = 0;

        try {
            // SQL query to count the number of products in the user's cart
            String sql = "SELECT COUNT(cp.ProductID) AS ProductCount "
                    + "FROM Cart c "
                    + "JOIN CartProduct cp ON c.ID = cp.CartID "
                    + "WHERE c.UserID = ?";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            if (rs.next()) {
                productCount = rs.getInt("ProductCount"); // Get the product count
            }
        } catch (SQLException e) {
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

        return productCount;
    }

    public User getUserByID(int id) {
        User u = null;
        try {
            // SQL query to get cart ID and product details
            String sql = ""
                    + "SELECT \n"
                    + "	   u.ID as ID,\n"
                    + "    u.RoleID as RoldID,\n"
                    + "    u.Status as Status\n"
                    + "FROM `User` as u\n"
                    + "WHERE u.ID = ?";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("ID"));
                u.setRoleId(rs.getInt("RoleID"));
                u.setStatus(rs.getString("Status"));
            }
        } catch (SQLException e) {
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
        return u;
    }

    public int getCartIDByID(int id) {
        int cartID = -1;
        try {
            // SQL query to get cart ID and product details
            String sql = ""
                    + "SELECT "
                    + "     c.ID "
                    + "FROM "
                    + "     Cart c "
                    + "WHERE "
                    + "     UserID = ?";

            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
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
        return cartID;
    }

    public static void main(String[] args) {
        new MyDAO().deleteProductFromCartByProductIdAndUserId(0, 11, "Black", "S");
    }
}
