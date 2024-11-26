package dal.codebase;

import codebase.FileHandler.FileInput;
import entity.codebaseOld.Slider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Hoang Anh Dep Trai
 */
public class sliderDAO {

    String statusFilePath = "Status\\SliderStatus\\SliderStatus.txt";

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<String> getAllStatus() {
        List<String> allSliderStatus = null;

        String sql = "SELECT Name FROM Status WHERE Type = 'Slider';";

        // Using try-with-resources for better resource management
        try (Connection connection = dbc.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (allSliderStatus == null) {
                        allSliderStatus = new ArrayList<>();
                    }
                    allSliderStatus.add(rs.getString("Name"));
                }
            }

        } catch (Exception e) {
            // Use logging frameworks like Log4j for better log management
            e.printStackTrace();
        }

        return allSliderStatus;
    }

    // Method to get active sliders (only those with status "active")
    public List<Slider> getAllActiveSlider() {
        List<Slider> sList = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT s.ID, s.Content, s.Link, s.status, s.backLink FROM Slider s WHERE s.Status = 'active';";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                sList.add(new Slider(rs.getInt("ID"),
                        rs.getString("Content"),
                        rs.getString("Link"),
                        rs.getString("status"),
                        rs.getString("backLink")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return sList;
    }

    // Method to get all sliders (both active and inactive)
    public List<Slider> getAllSliders() {
        List<Slider> sList = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT s.ID, s.Content, s.Link, s.status, s.backLink FROM Slider s;";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                sList.add(new Slider(rs.getInt("ID"),
                        rs.getString("Content"),
                        rs.getString("Link"),
                        rs.getString("status"),
                        rs.getString("backLink")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return sList;
    }

    // Method to get sliders by their status
    public List<Slider> getSlidersByStatus(String status) {
        List<Slider> sList = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT s.ID, s.Content, s.Link, s.status, s.backLink FROM Slider s WHERE s.status = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                sList.add(new Slider(rs.getInt("ID"),
                        rs.getString("Content"),
                        rs.getString("Link"),
                        rs.getString("status"),
                        rs.getString("backLink")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return sList;
    }

    // Method to get a slider by its ID
    public Slider getSliderById(int sliderId) {
        Slider slider = null;
        try {
            connection = dbc.getConnection();
            String sql = "SELECT s.ID, s.Content, s.Link, s.status, s.backLink FROM Slider s WHERE s.ID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, sliderId); // Set the slider ID parameter
            rs = ps.executeQuery();
            if (rs.next()) {
                slider = new Slider(rs.getInt("ID"),
                        rs.getString("Content"),
                        rs.getString("Link"),
                        rs.getString("status"),
                        rs.getString("backLink")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return slider; // Returns null if not found
    }

    // Method to close resources
    private void closeResources() {
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
            ex.printStackTrace();
        }
    }

    public boolean deleteSliderById(int sliderId) {
        boolean isDeleted = false;
        try {
            connection = dbc.getConnection();

            // Step 1: Delete the slider
            String sqlDelete = "SELECT Status FROM Slider WHERE ID = ?";
            ps = connection.prepareStatement(sqlDelete);
            ps.setInt(1, sliderId);
            rs = ps.executeQuery();
            String status = null;
            while (rs.next()) {
                status = rs.getString("Status");
            }
            if (status == null) {
                return false;
            }
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, status);

            if ("active".equalsIgnoreCase(status)) {
                String sqlUpdate = "UPDATE Slider SET Status = 'inactive' WHERE ID = ?";
                ps = connection.prepareStatement(sqlUpdate);
                ps.setInt(1, sliderId);
                ps.executeUpdate();

            } else if ("inactive".equalsIgnoreCase(status)) {
                String sqlUpdate = "UPDATE Slider SET Status = 'active' WHERE ID = ?";
                ps = connection.prepareStatement(sqlUpdate);
                ps.setInt(1, sliderId);
                ps.executeUpdate();

            } else {
                isDeleted = false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResources();
        }
        return isDeleted;
    }

    public boolean addSlider(Slider slider) {
        boolean isAdded = false;

        // Check for required fields
        if (slider.getContent().trim().isEmpty() || slider.getLink().trim().isEmpty() || slider.getBackLink().trim().isEmpty()) {
            return false;
        }

        try {
            connection = dbc.getConnection();

            // Step 1: Retrieve the maximum ID
            String sqlMaxId = "SELECT MAX(ID) AS maxId FROM Slider;";
            ps = connection.prepareStatement(sqlMaxId);
            rs = ps.executeQuery();

            int newId = 1; // Default ID if the table is empty
            if (rs.next()) {
                newId = rs.getInt("maxId") + 1; // Increment max ID by 1
            }

            // Step 2: Insert the new slider with the new ID
            String sqlInsert = "INSERT INTO Slider (ID, Content, Link, status, backLink) VALUES (?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sqlInsert);
            ps.setInt(1, newId); // Set new ID
            ps.setString(2, slider.getContent());
            ps.setString(3, slider.getLink());
            ps.setString(4, slider.getStatus());
            ps.setString(5, slider.getBackLink());

            isAdded = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return isAdded;
    }

    public boolean updateSlider(Slider slider) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isUpdated = false;

        try {
            conn = dbc.getConnection();  // Assuming you have a method to get DB connection
            String sql = "UPDATE sliders SET content = ?, link = ?, status = ?, backLink = ? WHERE sliderId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, slider.getContent());
            stmt.setString(2, slider.getLink());
            stmt.setString(3, slider.getStatus());
            stmt.setString(4, slider.getBackLink());
            stmt.setInt(5, slider.getSliderId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;  // Update was successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isUpdated;  // Return true if update was successful, false otherwise
    }

    // Method to search sliders by part of their content or backlink
    public List<Slider> searchSliders(String searchTerm) {
        List<Slider> sList = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            String sql = "SELECT s.ID, s.Content, s.Link, s.status, s.backLink FROM Slider s "
                    + "WHERE s.Content LIKE ? OR s.backLink LIKE ?;";
            ps = connection.prepareStatement(sql);
            String likeTerm = "%" + searchTerm + "%"; // Add wildcards for partial matching
            ps.setString(1, likeTerm);
            ps.setString(2, likeTerm);
            rs = ps.executeQuery();
            while (rs.next()) {
                sList.add(new Slider(rs.getInt("ID"),
                        rs.getString("Content"),
                        rs.getString("Link"),
                        rs.getString("status"),
                        rs.getString("backLink")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return sList;
    }

    // Test the methods
    public static void main(String[] args) {
        sliderDAO sDAO = new sliderDAO();

        // Test getAllActiveSlider method
        System.out.println("Active Sliders:");
        List<Slider> activeSliders = sDAO.getAllActiveSlider();
        for (Slider slider : activeSliders) {
            System.out.println(slider);
        }

        // Test getAllSliders method
        System.out.println("\nAll Sliders:");
        List<Slider> allSliders = sDAO.getAllSliders();
        for (Slider slider : allSliders) {
            System.out.println(slider);
        }

        // Test getSlidersByStatus method
        String testStatus = "active"; // Replace with "inactive" or "" for testing
        System.out.println("\nSliders with status " + testStatus + ":");
        List<Slider> slidersByStatus = sDAO.getSlidersByStatus(testStatus);
        for (Slider slider : slidersByStatus) {
            System.out.println(slider);
        }

    }

}
