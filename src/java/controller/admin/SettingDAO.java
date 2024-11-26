/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin;

import dal.codebase.DBContext;
import entity.codebaseNew.Category;
import entity.codebaseNew.Color;
import entity.codebaseNew.Tag;
import entity.codebaseNew.Size;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class SettingDAO {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    String[] settingList = {
        "status"
    };

    public boolean removeSliderStatus(String name) {
        if ("Active".equalsIgnoreCase(name)
                || "Hidden".equalsIgnoreCase(name)) {
            return false;
        }
        return this.removeStatusFor("Slider", name);
    }

    public boolean removeFeedbackStatus(String name) {
        if ("Show".equalsIgnoreCase(name)
                || "Hidden".equalsIgnoreCase(name)) {
            return false;
        }
        return this.removeStatusFor("Feedback", name);
    }

    public boolean removeBlogStatus(String name) {
        if ("Active".equalsIgnoreCase(name)
                || "Hidden".equalsIgnoreCase(name)) {
            return false;
        }
        return this.removeStatusFor("Blog", name);
    }

    public boolean removeProductStatus(String name) {
        if ("Available".equalsIgnoreCase(name)
                || "SoldOut".equalsIgnoreCase(name)
                || "Hidden".equalsIgnoreCase(name)) {
            return false;
        }
        return this.removeStatusFor("Product", name);
    }

    private boolean removeStatusFor(String type, String name) {
        boolean isSuccess = false;
        String sql = ""
                + "DELETE FROM "
                + "    Status "
                + "WHERE "
                + "    Type = '" + type + "'"
                + "    AND Name = '" + name + "';";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean addSliderStatus(String name) {
        if (this.getAllSliderStatus().contains(name)) {
            return false;
        }
        return this.addStatusFor("Slider", name);
    }

    public boolean addFeedbackStatus(String name) {
        if (this.getAllFeedbackStatus().contains(name)) {
            return false;
        }
        return this.addStatusFor("Feedback", name);
    }

    public boolean addBlogStatus(String name) {
        if (this.getAllBlogStatus().contains(name)) {
            return false;
        }
        return this.addStatusFor("Blog", name);
    }

    public boolean addProductStatus(String name) {
        if (this.getAllProductStatus().contains(name)) {
            return false;
        }
        return this.addStatusFor("Product", name);
    }

    private boolean addStatusFor(String type, String name) {
        boolean isSuccess = false;
        String sql = ""
                + "INSERT INTO "
                + "    Status(Type, Name) "
                + "VALUES "
                + "    (" + type + ", " + name + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public List<String> getAllBillStatus() {
        return this.getAllStatusFor("Bill");
    }

    public List<String> getAllSliderStatus() {
        return this.getAllStatusFor("Slider");
    }

    public List<String> getAllFeedbackStatus() {
        return this.getAllStatusFor("Feedback");
    }

    public List<String> getAllBlogStatus() {
        return this.getAllStatusFor("Blog");
    }

    public List<String> getAllProductStatus() {
        return this.getAllStatusFor("Product");
    }

    private List<String> getAllStatusFor(String type) {
        List<String> status = null;
        String sql = "SELECT Name FROM Status WHERE Type = '" + type + "';";

        try {
            rs = this.getDataFromQuery(sql);

            while (rs.next()) {
                if (status == null) {
                    status = new ArrayList<>();
                }

                status.add(rs.getString("Name"));
            }

        } catch (SQLException e) {
            status = null;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return status;
    }

    public boolean removeColor(Color color) {
        boolean isSuccess = false;
        String sql = ""
                + "DELETE FROM "
                + "    Color "
                + "WHERE "
                + "    ID = " + color.getId() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean addColor(Color color) {
        boolean isSuccess = false;
        String sql = ""
                + "INSERT INTO "
                + "    Color(Name, ColorCode) "
                + "VALUES "
                + "    (" + color.getName() + ", " + color.getColorCode() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean editColor(Color color) {
        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "    Color "
                + "SET "
                + "    Name = " + color.getName() + ", "
                + "    ColorCode = " + color.getColorCode() + " "
                + "WHERE "
                + "    ID = " + color.getId() + ";";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public List<Color> getAllColor() {
        List<Color> colors = null;
        String sql = "SELECT * FROM Color;";

        try {
            rs = this.getDataFromQuery(sql);

            while (rs.next()) {
                if (colors == null) {
                    colors = new ArrayList<>();
                }

                colors.add(new Color()
                        .setId(rs.getInt("ID"))
                        .setName(rs.getString("Name"))
                        .setColorCode(rs.getString("ColorCode"))
                );
            }

        } catch (SQLException e) {
            colors = null;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return colors;
    }

    public boolean removeSize(Size size) {
        boolean isSuccess = false;
        String sql = ""
                + "DELETE FROM "
                + "    Size "
                + "WHERE "
                + "    ID = " + size.getId() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean addSize(Size size) {
        boolean isSuccess = false;
        String sql = ""
                + "INSERT INTO "
                + "    Size(Size) "
                + "VALUES "
                + "    (" + size.getSize() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean editSize(Size size) {
        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "    Size "
                + "SET "
                + "    Size = " + size.getSize() + " "
                + "WHERE "
                + "    ID = " + size.getId() + ";";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public List<Size> getAllSize() {
        List<Size> sizies = null;
        String sql = "SELECT * FROM Size;";

        try {
            rs = this.getDataFromQuery(sql);

            while (rs.next()) {
                if (sizies == null) {
                    sizies = new ArrayList<>();
                }

                sizies.add(new Size()
                        .setId(rs.getInt("ID"))
                        .setSize(rs.getString("Size"))
                );
            }

        } catch (SQLException e) {
            sizies = null;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return sizies;
    }

    public boolean removeCategory(Category category) {
        boolean isSuccess = false;
        String sql = ""
                + "DELETE FROM "
                + "    SubCategory "
                + "WHERE "
                + "    ID = " + category.getId() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean addTag(Category category) {
        boolean isSuccess = false;
        String sql = ""
                + "INSERT INTO "
                + "    SubCategory(Name, OriginalType) "
                + "VALUES "
                + "    (" + category.getName() + ", " + category.getOriginalType() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean editTag(Category category) {
        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "    SubCategory "
                + "SET "
                + "    Name = " + category.getName() + ", "
                + "    OriginalType = " + category.getOriginalType() + " "
                + "WHERE "
                + "    ID = " + category.getId() + ";";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = null;
        String sql = "SELECT * FROM SubCategory;";

        try {
            rs = this.getDataFromQuery(sql);

            while (rs.next()) {
                if (categories == null) {
                    categories = new ArrayList<>();
                }

                categories.add(new Category()
                        .setId(rs.getInt("ID"))
                        .setName(rs.getString("Name"))
                        .setOriginalType(rs.getString("OriginalType"))
                );
            }

        } catch (SQLException e) {
            categories = null;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return categories;
    }

    public boolean removeTag(Tag tag) {
        boolean isSuccess = false;
        String sql = ""
                + "DELETE FROM "
                + "    Tag "
                + "WHERE "
                + "    ID = " + tag.getId() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean addTag(Tag tag) {
        boolean isSuccess = false;
        String sql = ""
                + "INSERT INTO "
                + "    Tag(Name, Color) "
                + "VALUES "
                + "    (" + tag.getName() + ", " + tag.getColor() + ");";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public boolean editTag(Tag tag) {
        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "    Tag "
                + "SET "
                + "    Name = " + tag.getName() + ", "
                + "    Color = " + tag.getColor() + " "
                + "WHERE "
                + "    ID = " + tag.getId() + ";";

        try {
            int affectedLine = this.getLineAffectedFromQuery(sql);

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            isSuccess = false;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return isSuccess;
    }

    public List<Tag> getAllTags() {
        List<Tag> tags = null;
        String sql = "SELECT * FROM Tag;";

        try {
            rs = this.getDataFromQuery(sql);

            while (rs.next()) {
                if (tags == null) {
                    tags = new ArrayList<>();
                }

                tags.add(new Tag()
                        .setId(rs.getInt("ID"))
                        .setName(rs.getString("Name"))
                        .setColor(rs.getString("Color"))
                );
            }

        } catch (SQLException e) {
            tags = null;
            String msg = "";
            this.log(Level.WARNING, msg, e);

        } finally {
            closeResourse();
        }

        return tags;
    }

    public void closeResourse() {
        if (rs != null) try {
            rs.close();
            ps = null;

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from boolean closeResource() function "
                    + "while trying to close Result Set.";
            this.log(Level.WARNING, msg, e);
        }

        if (ps != null) try {
            ps.close();
            ps = null;

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while trying to close Prepared Statement.";
            this.log(Level.WARNING, msg, e);
        }

        if (connection != null) try {
            connection.close();
            connection = null;

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while trying to close Connection.";
            this.log(Level.WARNING, msg, e);
        }
    }

    public ResultSet getDataFromQuery(String sql) throws SQLException {
        connection = dbc.getConnection();
        ps = connection.prepareStatement(sql);
        return ps.executeQuery();
    }

    public int getLineAffectedFromQuery(String sql) throws SQLException {
        connection = dbc.getConnection();
        ps = connection.prepareStatement(sql);
        return ps.executeUpdate();
    }

    private void log(Level level, String msg, Throwable e) {
        this.logger.log(level, msg, e);
    }

    public static void main(String[] args) {

    }

}
