/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.codebase;

import entity.codebaseOld.Feedback;
import entity.codebaseOld.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FeedbackDAO extends DBContext {
    
    String statusFilePath = "Status\\FeedbackStatus\\FeedbackStatus.txt";
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    
    public List<String> getAllStatus() {
        List<String> allFeedbackStatus = null;
        
        String sql = "SELECT Name FROM Status WHERE Type = 'Feedback';";

        // Using try-with-resources for better resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (allFeedbackStatus == null) {
                        allFeedbackStatus = new ArrayList<>();
                    }
                    allFeedbackStatus.add(rs.getString("Name"));
                }
            }

        } catch (Exception e) {
            // Use logging frameworks like Log4j for better log management
            e.printStackTrace();
        }
        
        return allFeedbackStatus;
    }

    public List<Feedback> getFilteredFeedbacks(int offset, int limit, String status, String product, String star, String search, String sortBy, String sortOrder) {
    List<Feedback> feedbackList = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT f.ID, f.OrderID, f.UserID, f.ProductID, f.Star, f.Comment, u.FirstName, u.LastName, c.Name as Category, p.Name AS ProductName " +
            "FROM Feedback f " +
            "JOIN User u ON f.UserID = u.ID " +
            "JOIN Product p ON f.ProductID = p.ID " +
            "JOIN SubCategory c ON c.ID = p.SubCategoryID " +
            "WHERE 1=1");

    // Adding filters based on provided parameters
    if (status != null && !status.isEmpty()) {
        query.append(" AND f.Status = ?");
    }
    if (product != null && !product.isEmpty()) {
        query.append(" AND f.ProductID = ?");
    }
    if (star != null && !star.isEmpty()) {
        query.append(" AND f.Star = ?");
    }
    if (search != null && !search.isEmpty()) {
        query.append(" AND (u.FirstName LIKE ? OR u.LastName LIKE ? OR f.Comment LIKE ?)");
    }

    String orderByColumn = sortBy != null ? sortBy : "f.ID";
    if ("fullName".equalsIgnoreCase(sortBy)) {
        query.append(" ORDER BY u.FirstName, u.LastName ").append(sortOrder != null ? sortOrder : "ASC");
    } else {
        query.append(" ORDER BY ").append(orderByColumn).append(" ").append(sortOrder != null ? sortOrder : "ASC");
    }

    // Adding pagination
    query.append(" LIMIT ? OFFSET ?");
    
    // Getting the database connection
    Connection connection = getConnection();
    try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
        int paramIndex = 1;

        // Setting parameters
        if (status != null && !status.isEmpty()) {
            preparedStatement.setString(paramIndex++, status);
        }
        if (product != null && !product.isEmpty()) {
            preparedStatement.setInt(paramIndex++, Integer.parseInt(product)); // Assuming product is an ID
        }
        if (star != null && !star.isEmpty()) {
            preparedStatement.setInt(paramIndex++, Integer.parseInt(star));
        }
        if (search != null && !search.isEmpty()) {
            preparedStatement.setString(paramIndex++, "%" + search + "%");
            preparedStatement.setString(paramIndex++, "%" + search + "%");
            preparedStatement.setString(paramIndex++, "%" + search + "%");
        }

        // Setting limit and offset for pagination
        preparedStatement.setInt(paramIndex++, limit);
        preparedStatement.setInt(paramIndex, offset);

        // Executing the query and processing results
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Feedback feedback = new Feedback();
            feedback.setFeedbackId(resultSet.getInt("ID")); // Assuming "ID" is the primary key in Feedback table
            feedback.setUserName(resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
            feedback.setOrderId(resultSet.getInt("OrderID")); // Retrieved from the Product table
            feedback.setProduct(new Product(0, 0, resultSet.getString("Category"), "", 0, 0, "", "")); // Retrieved from the Product table
            feedback.setProductName(resultSet.getString("ProductName")); // Retrieved from the Product table
            feedback.setStar(resultSet.getInt("Star"));
            feedback.setComment(resultSet.getString("Comment"));
            feedbackList.add(feedback);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return feedbackList;
}


    public List<String> getMediaLinks(int feedbackId) {
        List<String> mediaLinks = new ArrayList<>();
        String query = "SELECT Link FROM feedbackmedia WHERE FeedbackID = ?";
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, feedbackId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                mediaLinks.add(resultSet.getString("Link"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mediaLinks;
    }
    public Feedback getFeedbackById(int feedbackId) {
    Feedback feedback = null;
    String query = "SELECT f.ID, f.UserID, f.OrderID, f.ProductID, f.Star, f.Comment, u.FirstName, u.LastName, p.Name AS ProductName, f.status, c.Name as Category " +
                   "FROM Feedback f " +
                   "JOIN User u ON f.UserID = u.ID " +
                   "JOIN Product p ON f.ProductID = p.ID " +
                   "JOIN SubCategory c ON c.ID = p.SubCategoryID " +
                   "WHERE f.ID = ?";

    Connection connection = getConnection();
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, feedbackId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            feedback = new Feedback();
            feedback.setFeedbackId(resultSet.getInt("ID"));
            feedback.setUserName(resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
            feedback.setOrderId(resultSet.getInt("OrderID"));
            feedback.setProduct(new Product().setName(resultSet.getString("Category")));
            feedback.setProductName(resultSet.getString("ProductName"));
            feedback.setStar(resultSet.getInt("Star"));
            feedback.setComment(resultSet.getString("Comment"));
            feedback.setStatus(resultSet.getString("status"));

            // Optionally, retrieve media links associated with this feedback
            feedback.setLinkToMedia(getMediaLinks(feedbackId));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return feedback;
}

}
