/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static codebase.ImageSaveHandler.ImageSaver.saveBase64toImageFile;
import entity.ThanhCustomEntity;
import dal.codebase.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class BlogDAO {

    String statusFilePath = "Status\\BlogStatus\\BlogStatus.txt";

    String thumbnailFilePath = "web/asset/Image/BlogImage/thumbnail";
    String subImageFilePath = "web/asset/Image/BlogImage/subImage";

    public List<String> getAllStatus() {
        List<String> allBlogStatus = null;

        String sql = "SELECT Name FROM Status WHERE Type = 'Blog';";

        // Using try-with-resources for better resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (allBlogStatus == null) {
                        allBlogStatus = new ArrayList<>();
                    }
                    allBlogStatus.add(rs.getString("Name"));
                }
            }

        } catch (Exception e) {
            // Use logging frameworks like Log4j for better log management
            e.printStackTrace();
        }

        return allBlogStatus;
    }

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    public List<ThanhCustomEntity.blogMedia> getBlogThumbnailByBlogId(int blogId) {
        List<ThanhCustomEntity.blogMedia> bMedList = new ArrayList<>();

        String sql = "SELECT * FROM blogmedia WHERE BlogID = ? AND Type = 'thumbnail';";

        // Using try-with-resources for better resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, blogId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.blogMedia bMed = new ThanhCustomEntity.blogMedia();
                    bMed.setId(rs.getInt("ID"));
                    bMed.setBlogId(rs.getInt("BlogID"));
                    bMed.setLink(rs.getString("Link"));
                    bMed.setType(rs.getString("Type"));

                    bMedList.add(bMed);
                }
            }

        } catch (Exception e) {
            // Use logging frameworks like Log4j for better log management
            e.printStackTrace();
        }

        return bMedList;
    }

    public List<ThanhCustomEntity.blog> getAllBlog() {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        String sql = "SELECT \n"
                + "                    u.ID as authorId,\n"
                + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "                    b.ID AS BlogId,\n"
                + "                    b.Title as BlogTitle,\n"
                + "                    b.Content as BlogMainContent,\n"
                + "                    b.MiniDescription as BlogDes,\n"
                + "                    b.CreateAt AS BlogCreateAt,\n"
                + "                    b.status,\n"
                + "                    bc.ID as CateId,\n"
                + "                    bc.Name as CateName\n"
                + "                FROM\n"
                + "                    blog b\n"
                + "                    JOIN\n"
                + "                         user u ON u.ID = b.UserID\n"
                + "					JOIN\n"
                + "						blogcategory bc ON bc.ID = b.CategoryId\n"
                + "WHERE b.status = 'Active'\n"
                + "                ORDER BY b.CreateAt DESC;";

        // Using try-with-resources for connection, prepared statement, and result set
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                b.setId(rs.getInt("BlogId"));
                b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                b.setTitle(rs.getString("BlogTitle"));
                b.setContent(rs.getString("BlogMainContent"));
                b.setMiniDescription(rs.getString("BlogDes"));
                b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                b.setStatus(rs.getString("status"));

                // Fetch the related media using the blog ID
                List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                b.setBlogImageList(blogMediaList);

                blogList.add(b);
            }

        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
        }

        return blogList;
    }

    public List<ThanhCustomEntity.blog> getAllManagedBlog(int roleId, int uid) {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        if (roleId == 2 || roleId == 5) {
            String sql = "SELECT \n"
                    + "                    u.ID as authorId,\n"
                    + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                    + "                    b.ID AS BlogId,\n"
                    + "                    b.Title as BlogTitle,\n"
                    + "                    b.Content as BlogMainContent,\n"
                    + "                    b.MiniDescription as BlogDes,\n"
                    + "                    b.CreateAt AS BlogCreateAt,\n"
                    + "                    b.status,\n"
                    + "                    bc.ID as CateId,\n"
                    + "                    bc.Name as CateName\n"
                    + "                FROM\n"
                    + "                    blog b\n"
                    + "                    JOIN\n"
                    + "                         user u ON u.ID = b.UserID\n"
                    + "					JOIN\n"
                    + "						blogcategory bc ON bc.ID = b.CategoryId\n"
                    + "                ORDER BY b.CreateAt DESC;";

            // Using try-with-resources for connection, prepared statement, and result set
            try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                    b.setId(rs.getInt("BlogId"));
                    b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                    b.setTitle(rs.getString("BlogTitle"));
                    b.setContent(rs.getString("BlogMainContent"));
                    b.setMiniDescription(rs.getString("BlogDes"));
                    b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                    b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                    b.setStatus(rs.getString("status"));

                    // Fetch the related media using the blog ID
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                    b.setBlogImageList(blogMediaList);

                    blogList.add(b);
                }

            } catch (Exception e) {
                // Log the error for debugging purposes
                e.printStackTrace();
            }
        } else if (roleId == 3) {
            String sql = "SELECT \n"
                    + "                    u.ID as authorId,\n"
                    + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                    + "                    b.ID AS BlogId,\n"
                    + "                    b.Title as BlogTitle,\n"
                    + "                    b.Content as BlogMainContent,\n"
                    + "                    b.MiniDescription as BlogDes,\n"
                    + "                    b.CreateAt AS BlogCreateAt,\n"
                    + "                    b.status,\n"
                    + "                    bc.ID as CateId,\n"
                    + "                    bc.Name as CateName\n"
                    + "                FROM\n"
                    + "                    blog b\n"
                    + "                    JOIN\n"
                    + "                         user u ON u.ID = b.UserID\n"
                    + "					JOIN\n"
                    + "						blogcategory bc ON bc.ID = b.CategoryId\n"
                    + "WHERE u.ID = ?\n"
                    + "                ORDER BY b.CreateAt DESC;";

            // Using try-with-resources for connection, prepared statement, and result set
            try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {
                ps.setInt(1, uid);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                        b.setId(rs.getInt("BlogId"));
                        b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                        b.setTitle(rs.getString("BlogTitle"));
                        b.setContent(rs.getString("BlogMainContent"));
                        b.setMiniDescription(rs.getString("BlogDes"));
                        b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                        b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                        b.setStatus(rs.getString("status"));

                        // Fetch the related media using the blog ID
                        List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                        b.setBlogImageList(blogMediaList);

                        blogList.add(b);
                    }
                }

            } catch (Exception e) {
                // Log the error for debugging purposes
                e.printStackTrace();
            }
        }

        return blogList;
    }

    public List<ThanhCustomEntity.blog> getAllBlogByCateId(int cateId) {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        String sql = "SELECT \n"
                + "                    u.ID as authorId,\n"
                + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "                    b.ID AS BlogId,\n"
                + "                    b.Title as BlogTitle,\n"
                + "                    b.Content as BlogMainContent,\n"
                + "                    b.MiniDescription as BlogDes,\n"
                + "                    b.CreateAt AS BlogCreateAt,\n"
                + "                    b.status,\n"
                + "                    bc.ID as CateId,\n"
                + "                    bc.Name as CateName\n"
                + "                FROM\n"
                + "                    blog b\n"
                + "                    JOIN\n"
                + "                         user u ON u.ID = b.UserID\n"
                + "					JOIN\n"
                + "						blogcategory bc ON bc.ID = b.CategoryId\n"
                + "WHERE b.status = 'Active' AND bc.ID = ?\n"
                + "                ORDER BY b.CreateAt DESC;";

        // Using try-with-resources for connection, prepared statement, and result set
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cateId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                    b.setId(rs.getInt("BlogId"));
                    b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                    b.setTitle(rs.getString("BlogTitle"));
                    b.setContent(rs.getString("BlogMainContent"));
                    b.setMiniDescription(rs.getString("BlogDes"));
                    b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                    b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                    b.setStatus(rs.getString("status"));

                    // Fetch the related media using the blog ID
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                    b.setBlogImageList(blogMediaList);

                    blogList.add(b);
                }
            }

        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
        }

        return blogList;
    }

    public List<ThanhCustomEntity.blog> getAllManagerBlogByCateId(int cateId) {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        String sql = "SELECT \n"
                + "                    u.ID as authorId,\n"
                + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "                    b.ID AS BlogId,\n"
                + "                    b.Title as BlogTitle,\n"
                + "                    b.Content as BlogMainContent,\n"
                + "                    b.MiniDescription as BlogDes,\n"
                + "                    b.CreateAt AS BlogCreateAt,\n"
                + "                    b.status,\n"
                + "                    bc.ID as CateId,\n"
                + "                    bc.Name as CateName\n"
                + "                FROM\n"
                + "                    blog b\n"
                + "                    JOIN\n"
                + "                         user u ON u.ID = b.UserID\n"
                + "					JOIN\n"
                + "						blogcategory bc ON bc.ID = b.CategoryId\n"
                + "WHERE bc.ID = ?\n"
                + "                ORDER BY b.CreateAt DESC;";

        // Using try-with-resources for connection, prepared statement, and result set
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cateId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                    b.setId(rs.getInt("BlogId"));
                    b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                    b.setTitle(rs.getString("BlogTitle"));
                    b.setContent(rs.getString("BlogMainContent"));
                    b.setMiniDescription(rs.getString("BlogDes"));
                    b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                    b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                    b.setStatus(rs.getString("status"));

                    // Fetch the related media using the blog ID
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                    b.setBlogImageList(blogMediaList);

                    blogList.add(b);
                }
            }

        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
        }

        return blogList;
    }

    public List<ThanhCustomEntity.BlogCategory> getAllBlogCate() {
        List<ThanhCustomEntity.BlogCategory> blogCateList = new ArrayList<>();

        // Modify the SQL to allow partial matching of the blog title and sort by date
        String sql = "SELECT * FROM BlogCategory;";

        // Use try-with-resources for efficient resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            // Execute query and process result set
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.BlogCategory blogCate = new ThanhCustomEntity.BlogCategory();
                    blogCate.setId(rs.getInt("ID"));
                    blogCate.setName(rs.getString("Name"));

                    blogCateList.add(blogCate); // Add each populated blog entity to the list
                }
            }

        } catch (Exception e) {
            // Use a logger instead of e.printStackTrace for better production error tracking
        }

        return blogCateList;
    }

    public List<ThanhCustomEntity.blog> getAllSearchBlog(String searchInput) {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        // Modify the SQL to allow partial matching of the blog title and sort by date
        String sql = "SELECT \n"
                + "    u.ID AS authorId,\n"
                + "    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "    b.ID AS blogId,\n"
                + "    b.Title,\n"
                + "    b.Content,\n"
                + "    b.MiniDescription,\n"
                + "    b.CreateAt\n"
                + "FROM\n"
                + "    blog b\n"
                + "    JOIN user u ON u.ID = b.UserID\n"
                + "WHERE b.Title LIKE ?\n"
                + // Allows partial matching on blog title
                "ORDER BY b.CreateAt DESC;";

        // Use try-with-resources for efficient resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            // Set the parameter for the prepared statement with partial matching
            ps.setString(1, "%" + searchInput + "%");

            // Execute query and process result set
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Map each row to a blog entity
                    ThanhCustomEntity.blog blog = new ThanhCustomEntity.blog();
                    blog.setId(rs.getInt("blogId"));
                    blog.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                    blog.setTitle(rs.getString("Title"));
                    blog.setContent(rs.getString("Content"));
                    blog.setMiniDescription(rs.getString("MiniDescription"));
                    blog.setCreateAt(formateDateTime(rs.getTimestamp("CreateAt")));

                    // Fetch related media using a more efficient batch method (optimized suggestion below)
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(blog.getId());
                    blog.setBlogImageList(blogMediaList);

                    blogList.add(blog); // Add each populated blog entity to the list
                }
            }

        } catch (Exception e) {
            // Use a logger instead of e.printStackTrace for better production error tracking
        }

        return blogList;
    }

    public ThanhCustomEntity.blog getBlogById(int blogId) {
        ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();

        try {
            connection = dbc.getConnection();
            String sql = "SELECT \n"
                    + "                    u.ID as authorId,\n"
                    + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                    + "                    b.ID AS BlogId,\n"
                    + "                    b.Title as BlogTitle,\n"
                    + "                    b.Content as BlogMainContent,\n"
                    + "                    b.MiniDescription as BlogDes,\n"
                    + "                    b.CreateAt AS BlogCreateAt,\n"
                    + "                    b.status,\n"
                    + "                    bc.ID as CateId,\n"
                    + "                    bc.Name as CateName\n"
                    + "                FROM\n"
                    + "                    blog b\n"
                    + "                    JOIN\n"
                    + "                        user u ON u.ID = b.UserID\n"
                    + "                    JOIN\n"
                    + "                        blogcategory bc ON bc.ID = b.CategoryId\n"
                    + "                WHERE \n"
                    + "                    b.ID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, blogId);
            rs = ps.executeQuery();

            if (rs.next()) {
                b.setId(rs.getInt("BlogId"));
                b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                b.setTitle(rs.getString("BlogTitle"));
                b.setContent(rs.getString("BlogMainContent"));
                b.setMiniDescription(rs.getString("BlogDes"));
                b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                b.setStatus(rs.getString("status"));

                // Fetch the related media using the blog ID
                List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                b.setBlogImageList(blogMediaList);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Use logging here in production
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
                ex.printStackTrace(); // Use logging here in production
            }
        }
        return b;
    }

    public List<ThanhCustomEntity.blog> getAllSearchedBlogByTitle(String searchInput) {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        String sql = "SELECT \n"
                + "    u.ID as authorId,\n"
                + "    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "    b.ID AS BlogId,\n"
                + "    b.Title as BlogTitle,\n"
                + "    b.Content as BlogMainContent,\n"
                + "    b.MiniDescription as BlogDes,\n"
                + "    b.CreateAt AS BlogCreateAt,\n"
                + "    b.status,\n"
                + "    bc.ID as CateId,\n"
                + "    bc.Name as CateName\n"
                + "FROM\n"
                + "    blog b\n"
                + "    JOIN\n"
                + "        user u ON u.ID = b.UserID\n"
                + "    JOIN\n"
                + "        blogcategory bc ON bc.ID = b.CategoryId\n"
                + "WHERE \n"
                + "    b.Title LIKE ?\n"
                + "ORDER BY \n"
                + "    b.CreateAt DESC;";

        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            // Set the search input with wildcard for LIKE
            ps.setString(1, "%" + searchInput + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                    b.setId(rs.getInt("BlogId"));
                    b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                    b.setTitle(rs.getString("BlogTitle"));
                    b.setContent(rs.getString("BlogMainContent"));
                    b.setMiniDescription(rs.getString("BlogDes"));
                    b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                    b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                    b.setStatus(rs.getString("status"));

                    // Fetch the related media using the blog ID
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                    b.setBlogImageList(blogMediaList);

                    blogList.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return blogList;
    }

    public List<ThanhCustomEntity.blog> getAllBlogByStatus(String status) {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        String sql = "SELECT \n"
                + "                    u.ID as authorId,\n"
                + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "                    b.ID AS BlogId,\n"
                + "                    b.Title as BlogTitle,\n"
                + "                    b.Content as BlogMainContent,\n"
                + "                    b.MiniDescription as BlogDes,\n"
                + "                    b.CreateAt AS BlogCreateAt,\n"
                + "                    b.status,\n"
                + "                    bc.ID as CateId,\n"
                + "                    bc.Name as CateName\n"
                + "                FROM\n"
                + "                    blog b\n"
                + "                    JOIN\n"
                + "                         user u ON u.ID = b.UserID\n"
                + "					JOIN\n"
                + "						blogcategory bc ON bc.ID = b.CategoryId\n"
                + "WHERE b.status = ?\n"
                + "                ORDER BY b.CreateAt DESC;";

        // Using try-with-resources for connection, prepared statement, and result set
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                    b.setId(rs.getInt("BlogId"));
                    b.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                    b.setTitle(rs.getString("BlogTitle"));
                    b.setContent(rs.getString("BlogMainContent"));
                    b.setMiniDescription(rs.getString("BlogDes"));
                    b.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                    b.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                    b.setStatus(rs.getString("status"));

                    // Fetch the related media using the blog ID
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                    b.setBlogImageList(blogMediaList);

                    blogList.add(b);
                }
            }

        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
        }

        return blogList;
    }

    public boolean savePostStatus(int postId, String status) {
        String sql = "UPDATE blog\n"
                + "SET Status = ?\n"
                + "WHERE ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, status);
            pss.setInt(2, postId);

            int rowAffected = pss.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean savePostCategory(int postId, int cateId) {
        String sql = "UPDATE blog\n"
                + "SET CategoryId = ?\n"
                + "WHERE ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, cateId);
            pss.setInt(2, postId);

            int rowAffected = pss.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getIdForAddNew() {
        int id = -1;
        String sql = "SELECT MAX(ID) AS ID FROM blog";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql); ResultSet rss = pss.executeQuery()) {
            if (rss.next()) {
                id = rss.getInt("ID") + 1;
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Can not get count total blog!", e);
        }

        return id;
    }

    public ThanhCustomEntity.blog getBlogInformationById(int blogId) {
        ThanhCustomEntity.blog blog = new ThanhCustomEntity.blog();

        String sql = "SELECT \n"
                + "                    u.ID as authorId,\n"
                + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "                    b.ID AS BlogId,\n"
                + "                    b.Title as BlogTitle,\n"
                + "                    b.Content as BlogMainContent,\n"
                + "                    b.MiniDescription as BlogDes,\n"
                + "                    b.CreateAt AS BlogCreateAt,\n"
                + "                    b.status,\n"
                + "                    bc.ID as CateId,\n"
                + "                    bc.Name as CateName\n"
                + "                FROM\n"
                + "                    blog b\n"
                + "                    JOIN\n"
                + "                        user u ON u.ID = b.UserID\n"
                + "                    JOIN\n"
                + "                        blogcategory bc ON bc.ID = b.CategoryId\n"
                + "                WHERE \n"
                + "                    b.ID = ?;";

        // Using try-with-resources for connection, prepared statement, and result set
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, blogId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    blog.setId(rs.getInt("BlogId"));
                    blog.setAuthor(new ThanhCustomEntity.blogAuthor(rs.getInt("authorId"), rs.getString("authorName")));
                    blog.setTitle(rs.getString("BlogTitle"));
                    blog.setContent(rs.getString("BlogMainContent"));
                    blog.setMiniDescription(rs.getString("BlogDes"));
                    blog.setCreateAt(formateDateTime(rs.getTimestamp("BlogCreateAt")));
                    blog.setCate(new ThanhCustomEntity.BlogCategory(rs.getInt("CateId"), rs.getString("CateName")));
                    blog.setStatus(rs.getString("status"));

                    // Fetch the related media using the blog ID
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(blog.getId());
                    blog.setBlogImageList(blogMediaList);
                }
            }

        } catch (Exception e) {
            // Log the error for debugging purposes
            this.log(Level.WARNING, "Failed to get blog information by id, msg: " + e.getMessage(), e);
        }

        return blog;
    }

    public List<ThanhCustomEntity.BlogSubMedia> getBlogSubMediaById(int blogId) {
        List<ThanhCustomEntity.BlogSubMedia> blogSubMedList = new LinkedList<>();
        String sql = "SELECT * FROM blogmedia WHERE BlogID = ? AND Type != 'thumbnail';";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, blogId);
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    ThanhCustomEntity.BlogSubMedia b = new ThanhCustomEntity.BlogSubMedia();
                    b.setBlogId(rss.getInt("BlogId"));
                    b.setId(rss.getInt("ID"));
                    b.setLink(rss.getString("Link"));
                    b.setSubTitleId(rss.getInt("SubTitleId"));
                    b.setType(rss.getString("Type"));

                    blogSubMedList.add(b);
                }
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to get blog sub media for id: " + blogId, e);
        }

        return blogSubMedList;
    }

    public List<ThanhCustomEntity.BlogAttribute> getBlogAttributeListById(int blogId) {
        List<ThanhCustomEntity.BlogAttribute> blogAttrList = new ArrayList<>();
        String sql = "SELECT * FROM blogsubatribute WHERE BlogId = ?;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, blogId);
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    ThanhCustomEntity.BlogAttribute blogAttr = new ThanhCustomEntity.BlogAttribute();
                    blogAttr.setBlogId(rss.getInt("BlogId"));
                    blogAttr.setId(rss.getInt("ID"));
                    blogAttr.setSubContent(rss.getString("SubContent"));
                    blogAttr.setSubTitle(rss.getString("SubTitle"));
                    blogAttr.setType(rss.getString("Type"));

                    blogAttrList.add(blogAttr);
                }
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to get blog sub attribute for id: " + blogId, e);
        }

        return blogAttrList;
    }

    public boolean isValidSubTitle(String type, int blogId) {
        String sql = "SELECT * FROM blogsubatribute WHERE BlogId = ? AND Type = ?;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, blogId);
            pss.setString(2, type);
            try (ResultSet rss = pss.executeQuery()) {
                if (rss.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to check valid subtitle for blogId " + blogId + " !", e);
        }

        return false;
    }

    public boolean isValidSubImage(String type, int blogId) {
        String sql = "SELECT * FROM blogmedia WHERE BlogID = ? AND Type = ?;";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, blogId);
            pss.setString(2, type);
            try (ResultSet rss = pss.executeQuery()) {
                if (rss.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to check valid subimage for blogId " + blogId + " !", e);
        }

        return false;
    }

    public int getSubTitleIdByBlogIdAndType(int blogId, String type) {
        int id = -1; // Default value if no ID is found
        String sql = "SELECT ID FROM blogsubatribute WHERE BlogID = ? AND Type = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {

            pss.setInt(1, blogId);
            pss.setString(2, type);

            try (ResultSet rss = pss.executeQuery()) {
                if (rss.next()) { // Check if a row exists
                    id = rss.getInt("ID");
                }
            }
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to get SubTitle ID", e);
        }

        return id;
    }

    public boolean addNewSubTitle(int blogId, String type, String subTitle, String content) {
        String sql = "INSERT INTO blogsubatribute(SubTitle, SubContent, BlogId, Type) VALUE (?, ?, ?, ?)";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, subTitle);
            pss.setString(2, content);
            pss.setInt(3, blogId);
            pss.setString(4, type);

            return pss.executeUpdate() > 0;

        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to add new subtitle for blogId " + blogId + " !", e);
        }

        return false;
    }

    public boolean addNewSubMedia(String buildFilePathSubImage, String buildFilePathTumbnail, String filePathThumbnail, String filePathSubImage, int blogId, String typeMedia, String link, String typeSubTitle) {
        String sql = "INSERT INTO blogmedia(BlogID, Link, Type, SubTitleId) VALUE (?, ?, ?, ?)";
        int subTitleId = getSubTitleIdByBlogIdAndType(blogId, typeSubTitle);

        String filePath;

        if (typeMedia.equalsIgnoreCase("thumbnail")) {
            filePath = saveBase64toImageFile(link, filePathThumbnail, "ThumbnailBlog" + blogId);
            saveBase64toImageFile(link, buildFilePathTumbnail, "ThumbnailBlog" + blogId);
        } else {
            filePath = saveBase64toImageFile(link, filePathSubImage, typeMedia + blogId);
            saveBase64toImageFile(link, buildFilePathSubImage, typeMedia + blogId);
        }

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, blogId);
            pss.setString(2, filePath);
            pss.setString(3, typeMedia);
            pss.setInt(4, subTitleId);
            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to add new submedia for subtitle id " + subTitleId + " for blogId " + blogId + " !", e);
        }

        return false;
    }

    public boolean updateBlogMedia(String buildFilePathSubImage, String buildFilePathTumbnail, String filePathThumbnail, String filePathSubImage, int blogId, String link, String type) {
        String sql = "UPDATE blogmedia\n"
                + "SET Link = ?\n"
                + "WHERE BlogID = ? AND Type = ?";

        String filePath;

        if (type.equalsIgnoreCase("thumbnail")) {
            filePath = saveBase64toImageFile(link, filePathThumbnail, "ThumbnailBlog_blogId_" + blogId);
            saveBase64toImageFile(link, buildFilePathTumbnail, "ThumbnailBlog_blogId_" + blogId);
        } else {
            filePath = saveBase64toImageFile(link, filePathSubImage, type + "_BlogId_" + blogId);
            saveBase64toImageFile(link, buildFilePathSubImage, type + "_BlogId_" + blogId);
        }

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, filePath);
            pss.setInt(2, blogId);
            pss.setString(3, type);
            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to update blog media " + type + " for blogId " + blogId + " !", e);
        }
        return false;
    }

    public boolean updateBlogSubTitle(int blogId, String subTitle, String content, String type) {
        String sql = "UPDATE blogsubatribute\n"
                + "SET SubTitle = ?,\n"
                + "    SubContent = ?\n"
                + "WHERE BlogID = ? AND Type = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, subTitle);
            pss.setString(2, content);
            pss.setInt(3, blogId);
            pss.setString(4, type);
            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to update blog " + type + " for blogId " + blogId + " !", e);
        }

        return false;
    }

    public boolean updateBlogMainInformation(String mainTitle, String mainContent, int blogId) {
        String sql = "UPDATE blog\n"
                + "SET Title = ?,\n"
                + "    Content = ?\n"
                + "WHERE ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setString(1, mainTitle);
            pss.setString(2, mainContent);
            pss.setInt(3, blogId);
            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to update main blog information for blog " + blogId + " !", e);
        }

        return false;
    }

    public boolean addNewBlogMainInformation(int blogId, int cateId, int uid, String mainTitle, String mainContent, String status) {
        String sql = "INSERT INTO blog(ID, CategoryId, UserID, Title, Content, Status) VALUE (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, blogId);
            pss.setInt(2, cateId);
            pss.setInt(3, uid);
            pss.setString(4, mainTitle);
            pss.setString(5, mainContent);
            pss.setString(6, status);
            int rowsAffected = pss.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            this.log(Level.WARNING, "Failed to add new post!", e);
        }

        return false;
    }

    public boolean isValidBlog(int blogId) {
        String sql = "SELECT * FROM blog WHERE ID = ?";

        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql)) {
            pss.setInt(1, blogId);
            try (ResultSet rss = pss.executeQuery()) {
                if (rss.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            this.log(Level.WARNING, "Can not define if blog id valid or not", e);
        }

        return false;
    }

    public List<ThanhCustomEntity.blog> getAllSimilarBlog(int cateId) {
        List<ThanhCustomEntity.blog> blogList = new ArrayList<>();

        String sql = "SELECT \n"
                + "                    u.ID as authorId,\n"
                + "                    CONCAT(u.FirstName, ' ', u.LastName) AS authorName,\n"
                + "                    b.ID AS BlogId,\n"
                + "                    b.Title as BlogTitle,\n"
                + "                    b.Content as BlogMainContent,\n"
                + "                    b.MiniDescription as BlogDes,\n"
                + "                    b.CreateAt AS BlogCreateAt,\n"
                + "                    b.status,\n"
                + "                    bc.ID as CateId,\n"
                + "                    bc.Name as CateName\n"
                + "                FROM\n"
                + "                    blog b\n"
                + "                    JOIN\n"
                + "                         user u ON u.ID = b.UserID\n"
                + "					JOIN\n"
                + "						blogcategory bc ON bc.ID = b.CategoryId\n"
                + "WHERE b.status = 'Active' AND bc.ID = ?\n"
                + "                ORDER BY b.CreateAt DESC;";

        // Using try-with-resources for connection, prepared statement, and result set
        try (Connection conn = dbc.getConnection(); PreparedStatement pss = conn.prepareStatement(sql);) {
            pss.setInt(1, cateId);
            try (ResultSet rss = pss.executeQuery()) {
                while (rss.next()) {
                    ThanhCustomEntity.blog b = new ThanhCustomEntity.blog();
                    b.setId(rss.getInt("BlogId"));
                    b.setAuthor(new ThanhCustomEntity.blogAuthor(rss.getInt("authorId"), rss.getString("authorName")));
                    b.setTitle(rss.getString("BlogTitle"));
                    b.setContent(rss.getString("BlogMainContent"));
                    b.setMiniDescription(rss.getString("BlogDes"));
                    b.setCreateAt(formateDateTime(rss.getTimestamp("BlogCreateAt")));
                    b.setCate(new ThanhCustomEntity.BlogCategory(rss.getInt("CateId"), rss.getString("CateName")));
                    b.setStatus(rss.getString("status"));

                    // Fetch the related media using the blog ID
                    List<ThanhCustomEntity.blogMedia> blogMediaList = getBlogThumbnailByBlogId(b.getId());
                    b.setBlogImageList(blogMediaList);

                    blogList.add(b);
                }
            }

        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
        }

        return blogList;
    }

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private void log(Level level, String msg, Throwable e) {
        this.logger.log(level, msg, e);
    }

    public String formateDateTime(java.sql.Timestamp t) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        return dateFormat.format(t);
    }

    public static void main(String[] args) {
        BlogDAO bDAO = new BlogDAO();
        System.out.println(bDAO.getAllSimilarBlog(2));
//        System.out.println(saveBase64toImageFile("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wIAAgkA/b7y6AAAAABJRU5ErkJggg==", bDAO.thumbnailFilePath, "thumbnail"));
//        System.out.println(bDAO.updateBlogMedia(2, "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wIAAgkA/b7y6AAAAABJRU5ErkJggg==", "thumbnail"));
    }
}
