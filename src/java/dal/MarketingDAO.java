/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dal.codebase.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class MarketingDAO {

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    /**
     * using logger
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * java.util.logging.Logger to log issues or debug
     *
     * @param level java.util.loggin.Level
     * @param msg optional mesage
     * @param e optional exception
     */
    private void log(Level level, String msg, Throwable e) {
        this.logger.log(level, msg, e);
    }

    /**
     * Mostly just a test function
     *
     * @param args
     */
    public static void main(String[] args) {
        int year = LocalDate.now().minusDays(7).getYear();
        int month = LocalDate.now().minusDays(7).getMonthValue();
        int day = LocalDate.now().minusDays(7).getDayOfMonth();
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(year - 1900, month - 1, day));
        String toDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(new MarketingDAO().getChart(fromDate, toDate));
    }

    private void closeResource() {
        // Close resources
        if (rs != null) try {
            rs.close();
            ps = null;

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "while trying to close Result Set.";
            this.log(Level.WARNING, msg, e);
        }

        if (ps != null) try {
            ps.close();
            ps = null;

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "while trying to close Prepared Statement.";
            this.log(Level.WARNING, msg, e);
        }

        if (connection != null) try {
            connection.close();
            connection = null;

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "while trying to close Connection.";
            this.log(Level.WARNING, msg, e);
        }
    }

    public MarketingStatistic getStatistic(String fromDate, String toDate) {
        MarketingStatistic statistic = null;
        String[] sql = {
            "SELECT count(ID) FROM blog;",
            "SELECT count(ID) FROM product;",
            "SELECT count(ID) FROM user "
            + "WHERE RoleID = 1;",
            "SELECT count(ID) FROM feedback "
            + "WHERE CreateAt BETWEEN '" + fromDate + "' AND '" + toDate + "';"
        };

        try {
            connection = dbc.getConnection();

            int[] result = new int[sql.length];
            int count = 0;
            boolean fail = false;
            for (String s : sql) {
                ps = connection.prepareStatement(s);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result[count++] = rs.getInt(1);
                } else {
                    fail = true;
                }
            }

            if (!fail) {
                statistic = new MarketingStatistic();
                statistic.setPostsNumber(result[0]);
                statistic.setProductsNumber(result[1]);
                statistic.setCustomersNumber(result[2]);
                statistic.setFeedbacksNumber(result[3]);
            } else {
                statistic = null;
            }

        } catch (Exception e) {
            statistic = null;
            String msg = "Exception catched "
                    + "from function getStatistic(String, String) "
                    + "while execute query.";
            this.log(Level.WARNING, msg, e);
        } finally {
            this.closeResource();
        }

        return statistic;
    }

    public MarketingChart getChart(String fromDate, String toDate) {
        MarketingChart chart = null;
        String sql = null;
        this.log(Level.INFO, "Debug: {\"fromDate\"=" + fromDate  + ", \"toDate\"=" + toDate + "}", null);
        try {
            connection = dbc.getConnection();
            sql = ""
                    + "WITH RECURSIVE DateRange AS (\n"
                    + "    SELECT DATE(?) AS Date\n"
                    + "    UNION ALL\n"
                    + "    SELECT Date + INTERVAL 1 DAY\n"
                    + "    FROM DateRange\n"
                    + "    WHERE Date + INTERVAL 1 DAY <= ?\n"
                    + ")\n"
                    + "SELECT\n"
                    + "    DateRange.Date as Date,\n"
                    + "    COUNT(feedback.ID) AS FeedbackNumber,\n"
                    + "    COUNT(user.ID) AS NewCustomers,\n"
                    + "    COUNT(blog.ID) AS NewPosts\n"
                    + "FROM\n"
                    + "    DateRange\n"
                    + "LEFT JOIN\n"
                    + "    feedback ON DATE(feedback.CreateAt) = DateRange.Date\n"
                    + "LEFT JOIN\n" 
                    + "    blog ON DATE(blog.CreateAt) = DateRange.Date\n"
                    + "LEFT JOIN\n" 
                    + "    user ON DATE(user.CreateAt) = DateRange.Date\n"
                    + "            AND user.RoleID = 1\n"
                    + "            AND user.Status LIKE '%active%'"
                    + "GROUP BY\n"
                    + "    DateRange.Date\n"
                    + "ORDER BY\n"
                    + "    DateRange.Date ASC;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (chart == null) {
                    chart = new MarketingChart();
                    
                    DataSet dsFeedback = new DataSet();
                    dsFeedback.setColor("255, 0, 0");
                    dsFeedback.setLabel("New Feedbacks");
                    chart.getDatasets().add(dsFeedback);
                    
                    DataSet dsCustomer = new DataSet();
                    dsCustomer.setColor("0, 255, 0");
                    dsCustomer.setLabel("New Customers");
                    chart.getDatasets().add(dsCustomer);
                    
                    DataSet dsPost = new DataSet();
                    dsPost.setColor("0, 0, 255");
                    dsPost.setLabel("New Posts");
                    chart.getDatasets().add(dsPost);
                    
//                    DataSet dsProduct = new DataSet();
//                    dsProduct.setColor("64, 64, 64");
//                    dsProduct.setLabel("New Product");
//                    chart.getDatasets().add(dsProduct);
                }
                chart.getLabels().add(rs.getString("Date"));
                chart.getDatasets().get(0).getData().add(rs.getDouble("FeedbackNumber"));
                chart.getDatasets().get(1).getData().add(rs.getDouble("NewCustomers"));
                chart.getDatasets().get(2).getData().add(rs.getDouble("NewPosts"));
            }

        } catch (Exception e) {
            chart = null;
            String msg = "Exception catched "
                    + "from function getChart(String, String) "
                    + "while execute query.";
            this.log(Level.WARNING, msg, e);
        } finally {
            this.closeResource();
        }

        return chart;
    }

    private static class DataSet {

        private String label;
        private List<Double> data;
        private String backgroundColor;
        private String borderColor;
        private boolean fill;
        private double tension; // độ cong
        private int borderWidth;
        
        public JSONObject toJSON() {
            JSONObject main = new JSONObject();
            main.append("label", label);
            main.append("data", data);
            main.append("background-color", backgroundColor);
            main.append("border-color", borderColor);
            main.append("fill", fill);
            main.append("tension", tension);
            main.append("border-width", borderWidth);
            return main;
        }

        public int getBorderWidth() {
            return borderWidth;
        }

        public void setBorderWidth(int borderWidth) {
            this.borderWidth = borderWidth;
        }

        public DataSet() {
            this.fill = true;
            this.tension = 0.0;
            this.borderWidth = 1;
            this.backgroundColor = "rgba(128, 128, 0, 0.2)";
            this.borderColor = "rgba(128, 128, 0, 1)";
            this.label = "";
            this.data = new ArrayList<>();
        }
        
        public void setColor(String color) {
            this.backgroundColor = "rgba(" + color + ", 0.2)";
            this.borderColor = "rgba(" + color + ", 0.1)";
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<Double> getData() {
            return data;
        }

        public void setData(List<Double> data) {
            this.data = data;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public boolean isFill() {
            return fill;
        }

        public void setFill(boolean fill) {
            this.fill = fill;
        }

        public double getTension() {
            return tension;
        }

        public void setTension(double tension) {
            this.tension = tension;
        }

        @Override
        public String toString() {
            return "{"
                    + "label: \"" + this.label + "\", "
                    + "data: " + Arrays.toString(this.data.toArray()) + ", "
                    + "backgroundColor: \"" + this.backgroundColor + "\", "
                    + "borderColor: \"" + this.borderColor + "\", "
                    + "borderWidth: " + this.borderWidth + ", "
                    + "fill: " + this.fill + ", "
                    + "tension: " + this.tension + " "
                    + "}";
        }

    }

    public class MarketingChart {

        private List<String> labels;
        private List<DataSet> datasets;
        private String outputData;

        public MarketingChart() {
            labels = new ArrayList<>();
            datasets = new ArrayList<>();
        }

        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }

        public List<DataSet> getDatasets() {
            return datasets;
        }

        public void setDatasets(List<DataSet> datasets) {
            this.datasets = datasets;
        }

        public String getOutputData() {
            return outputData;
        }

        public void setOutputData(String outputData) {
            this.outputData = outputData;
        }
        
        public String getLabelArray() {
            String toRet = "[\"" + this.labels.get(0) + "\"";
            for (int i = 1; i < this.labels.size(); i++) {
                toRet += ", ";
                toRet += "\"" + this.labels.get(i) + "\"";
            }
            toRet += "]";
            return toRet;
        }
        
        public JSONObject toJSON() {
            JSONObject main = new JSONObject();
            
            main.append("labels", labels);
            JSONArray datasetsArray = new JSONArray();
            for (DataSet d : datasets) {
                datasetsArray.put(d.toJSON());
            }
            main.append("datasets", datasetsArray);
            
            return main;
        }

        @Override
        public String toString() {
            return "{"
                    + "labels: " + this.getLabelArray() + ", "
                    + "datasets: " + Arrays.toString(this.datasets.toArray()) + ""
                    + "}";
        }

    }

    public class MarketingStatistic {

        private int postsNumber;
        private int productsNumber;
        private int customersNumber;
        private int feedbacksNumber;

        public int getPostsNumber() {
            return postsNumber;
        }

        public void setPostsNumber(int postsNumber) {
            this.postsNumber = postsNumber;
        }

        public int getProductsNumber() {
            return productsNumber;
        }

        public void setProductsNumber(int productsNumber) {
            this.productsNumber = productsNumber;
        }

        public int getCustomersNumber() {
            return customersNumber;
        }

        public void setCustomersNumber(int customersNumber) {
            this.customersNumber = customersNumber;
        }

        public int getFeedbacksNumber() {
            return feedbacksNumber;
        }

        public void setFeedbacksNumber(int feedbacksNumber) {
            this.feedbacksNumber = feedbacksNumber;
        }

    }

}
