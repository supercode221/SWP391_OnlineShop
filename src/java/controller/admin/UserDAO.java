/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin;

import dal.codebase.DBContext;
import entity.codebaseNew.Address;
import entity.codebaseNew.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class UserDAO {

    /**
     * using logger
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    PreparedStatement ps = null;
    DBContext dbc = new DBContext();
    Connection connection = null;
    ResultSet rs = null;

    public List<String> getAllUserStatus() {
        List<String> statusList = new ArrayList<>();
        statusList.add("active");
        statusList.add("pending");
        statusList.add("banned");
        return statusList;
    }

    public Role getUserRoleByUserID(int userID) {
        Role role = null;
        String sql = ""
                + "SELECT "
                + "    u.ID as UserID,"
                + "    u.Email as UserEmail,"
                + "    r.ID as RoleID,"
                + "    r.Name as RoleName,"
                + "    r.Weight as RoleWeight "
                + "FROM "
                + "    User u "
                + "    JOIN Role r on r.id = u.RoleID "
                + "WHERE "
                + "    u.ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            if (rs.next()) {
                role = new Role()
                        .setId(rs.getInt("RoleID"))
                        .setName(rs.getString("RoleName"))
                        .setWeight(rs.getInt("RoleWeight"))
                        .setPermissionList(null);
            }

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } catch (Exception e) {
            String msg = "A Random and Unexpected Exception have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from getUserRoleByUserID(int) function "
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

        return role;
    }

    List<Role> getAllRole() {
        List<Role> roles = null;
        String sql = ""
                + "SELECT "
                + "    r.ID as RoleID,"
                + "    r.Name as RoleName,"
                + "    r.Weight as RoleWeight "
                + "FROM "
                + "    Role r";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (roles == null) {
                    roles = new ArrayList<>();
                }
                Role role = new Role()
                        .setId(rs.getInt("RoleID"))
                        .setName(rs.getString("RoleName"))
                        .setWeight(rs.getInt("RoleWeight"))
                        .setPermissionList(null);
                roles.add(role);
            }

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } catch (Exception e) {
            String msg = "A Random and Unexpected Exception have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from getUserRoleByUserID(int) function "
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

        return roles;
    }

    public boolean updateUser(int userId, int roleId, String status) {
        if (roleId == 2) {
            return false;
        }

        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "    User "
                + "SET "
                + "    RoleID = ?,"
                + "    Status = ? "
                + "WHERE "
                + "    ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, roleId);
            ps.setString(2, status);
            ps.setInt(3, userId);
            isSuccess = ps.executeUpdate() == 1;

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from updateUser(int,int,String) function "
                    + "while execusing task to update user role and status data.";
            this.log(Level.WARNING, msg, e);
            isSuccess = false;

        } catch (Exception e) {
            String msg = "A Random and Unexpected Exception have been throw "
                    + "from updateUser(int,int,String) function "
                    + "while execusing task to update user role and status data.";
            this.log(Level.WARNING, msg, e);
            isSuccess = false;

        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from updateUser(int,int,String) function "
                        + "while trying to close Result Set.";
                this.log(Level.WARNING, msg, e);
                isSuccess = false;
            }

            if (ps != null) try {
                ps.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from updateUser(int,int,String) function "
                        + "while trying to close Prepared Statement.";
                this.log(Level.WARNING, msg, e);
                isSuccess = false;
            }

            if (connection != null) try {
                connection.close();
                connection = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from updateUser(int,int,String) function "
                        + "while trying to close Connection.";
                this.log(Level.WARNING, msg, e);
                isSuccess = false;
            }
        }

        return isSuccess;
    }

    public List<User> getUserPageFromList(int page, List<User> users) {
        if (page < 1) {
            page = 1;
        }
        final int ITEM_PER_PAGE = AdminUsersController.ITEM_PER_PAGE;
        List<User> allUser = users;
        if (users == null) {
            return null;
        }
        int maxPage = (allUser.size() / ITEM_PER_PAGE) + 1;
        if (allUser.size() % ITEM_PER_PAGE == 0) {
            maxPage -= 1;
        }
        if (page > maxPage) {
            return null;
        }
        int fromItemIndex = ITEM_PER_PAGE * (page - 1);
        int toItemIndex = ITEM_PER_PAGE * page;
        if (toItemIndex > allUser.size()) {
            toItemIndex = allUser.size();
        }
        return allUser.subList(fromItemIndex, toItemIndex);
    }

    public List<User> getUserPage(int page) {
        if (page < 1) {
            page = 1;
        }
        final int ITEM_PER_PAGE = AdminUsersController.ITEM_PER_PAGE;
        List<User> allUser = this.getAllUser();
        int maxPage = (allUser.size() / ITEM_PER_PAGE) + 1;
        if (allUser.size() % ITEM_PER_PAGE == 0) {
            maxPage -= 1;
        }
        if (page > maxPage) {
            return null;
        }
        int fromItemIndex = ITEM_PER_PAGE * (page - 1);
        int toItemIndex = ITEM_PER_PAGE * page;
        if (toItemIndex > allUser.size()) {
            toItemIndex = allUser.size();
        }
        return allUser.subList(fromItemIndex, toItemIndex);
    }

    public List<User> getUserByFilter(int roleId, String status, String searchInput) {
        List<User> userList = null;
        String sql = ""
                + "SELECT "
                + "    u.ID as UserID,"
                + "    u.AvatarImage as UserAvatarImage,"
                + "    u.FirstName as UserFirstName,"
                + "    u.LastName as UserLastName,"
                + "    u.Email as UserEmail,"
                + "    u.PhoneNumber as UserPhoneNumber,"
                + "    u.Status as UserStatus,"
                + "    r.id as RoleID,"
                + "    r.Name as RoleName,"
                + "    a.ID as AddressID,"
                + "    a.Country as AddressCountry,"
                + "    a.TinhThanhPho as AddressTinhThanhPho,"
                + "    a.QuanHuyen as AddressQuanHuyen,"
                + "    a.PhuongXa as AddressPhuongXa,"
                + "    a.Details as AddressDetails "
                + "FROM "
                + "    User u "
                + "    JOIN Role r on r.id = u.RoleID "
                + "    LEFT JOIN Address a on a.UserID = u.id "
                + "WHERE "
                + "    1 = 1 ";

        boolean filterHaveRoleId = roleId != -1;
        boolean filterHaveStatus = !"all".equalsIgnoreCase(status);
        StringBuilder sqlBuilder = new StringBuilder(sql);
        int parametersIndex = 0;
        if (filterHaveRoleId) {
            sqlBuilder.append(" AND u.RoleID = ? ");
        }
        if (filterHaveStatus) {
            sqlBuilder.append(" AND u.Status = ? ");
        }
        sql = sqlBuilder.toString();

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            if (filterHaveRoleId) {
                ps.setInt(++parametersIndex, roleId);
            }
            if (filterHaveStatus) {
                ps.setString(++parametersIndex, status);
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                if (userList == null) {
                    userList = new ArrayList<>();
                }

                User u = new User()
                        .setId(rs.getInt("UserID"))
                        .setFirstName(rs.getString("UserFirstName"))
                        .setLastName(rs.getString("UserLastName"))
                        .setEmail(rs.getString("UserEmail"))
                        .setPhone(rs.getString("UserPhoneNumber"))
                        .setRole(new Role()
                                .setId(rs.getInt("RoleID"))
                                .setName(rs.getString("RoleName"))
                        )
                        .setStatus(rs.getString("UserStatus"))
                        .setAvatarImage(rs.getString("UserAvatarImage"))
                        .setAddressList(new ArrayList<>());
                if (!userList.contains(u)) {
                    String addressId = rs.getString("AddressID");
                    if (addressId != null && !addressId.isEmpty()) {
                        u.getAddressList().add(new Address()
                                .setId(rs.getInt("AddressID"))
                                .setCountry(rs.getString("AddressCountry"))
                                .setTinh_thanhpho(rs.getString("AddressTinhThanhPho"))
                                .setHuyen_quan(rs.getString("AddressQuanHuyen"))
                                .setXa_phuong(rs.getString("AddressPhuongXa"))
                                .setDetails(rs.getString("AddressDetails"))
                        );
                    } else {
                        u.setAddressList(null);
                    }
                    userList.add(u);
                } else {
                    int userListSize = userList.size();
                    userList.get(userListSize - 1).getAddressList().add(new Address()
                            .setId(rs.getInt("AddressID"))
                            .setCountry(rs.getString("AddressCountry"))
                            .setTinh_thanhpho(rs.getString("AddressTinhThanhPho"))
                            .setHuyen_quan(rs.getString("AddressQuanHuyen"))
                            .setXa_phuong(rs.getString("AddressPhuongXa"))
                            .setDetails(rs.getString("AddressDetails"))
                    );
                }
            }

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from getAllUser function "
                    + "while execusing task to get all user data";
            this.log(Level.WARNING, msg, e);

        } catch (Exception e) {
            String msg = "A Random and Unexpected Exception have been throw "
                    + "from getAllUser function "
                    + "while execusing task to get all user data";
            this.log(Level.WARNING, msg, e);

        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw from getAllUser function "
                        + "while trying to close Result Set";
                this.log(Level.WARNING, msg, e);
            }

            if (ps != null) try {
                ps.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw from getAllUser function "
                        + "while trying to close Prepared Statement";
                this.log(Level.WARNING, msg, e);
            }

            if (connection != null) try {
                connection.close();
                connection = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw from getAllUser function "
                        + "while trying to close Connection";
                this.log(Level.WARNING, msg, e);
            }
        }

        Set<User> users = new HashSet<>();
        if (userList == null) {
            return null;
        }
        for (User u : userList) {
            if (u.getFullName() != null && u.getFullName().contains(searchInput)) {
                users.add(u);
            }
        }
        for (User u : userList) {
            if (u.getEmail() != null && u.getEmail().contains(searchInput)) {
                users.add(u);
            }
        }
        for (User u : userList) {
            if (u.getPhone() != null && u.getPhone().contains(searchInput)) {
                users.add(u);
            }
        }

        userList = new ArrayList<>(users);

        return userList;
    }

    public User getUserContactInfoById(int userId) {
        User u = null;
        String sql = ""
                + "SELECT "
                + "    u.ID as UserID, "
                + "    u.FirstName as UserFirstName, "
                + "    u.LastName as UserLastName, "
                + "    u.Email as UserEmail, "
                + "    u.PhoneNumber as UserPhoneNumber, "
                + "    a.ID as AddressID, "
                + "    a.Country as AddressCountry, "
                + "    a.TinhThanhPho as AddressTinhThanhPho, "
                + "    a.QuanHuyen as AddressHuyenQuan, "
                + "    a.PhuongXa as AddressXaPhuong, "
                + "    a.Details as AddressDetails, "
                + "    a.Note as AddressNote "
                + "FROM "
                + "    User u "
                + "    LEFT JOIN Address a ON a.UserID = u.ID "
                + "WHERE "
                + "    u.ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (u == null) {
                    u = new User()
                            .setId(userId)
                            .setFirstName(rs.getString("UserFirstName"))
                            .setLastName(rs.getString("UserLastName"))
                            .setEmail(rs.getString("UserEmail"))
                            .setPhone(rs.getString("UserPhoneNumber"))
                            .setAddressList(new ArrayList<>());
                }
                if (rs.getString("AddressID") != null) {
                    u.getAddressList()
                            .add(new Address()
                                    .setId(rs.getInt("AddressID"))
                                    .setCountry(rs.getString("AddressCountry"))
                                    .setTinh_thanhpho(rs.getString("AddressTinhThanhPho"))
                                    .setHuyen_quan(rs.getString("AddressHuyenQuan"))
                                    .setXa_phuong(rs.getString("AddressXaPhuong"))
                                    .setDetails(rs.getString("AddressDetails"))
                                    .setNote(rs.getString("AddressNote"))
                                    .setFormat()
                            );
                }
            }

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } catch (Exception e) {
            String msg = "A Random and Unexpected Exception have been throw "
                    + "from getUserRoleByUserID(int) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from getUserRoleByUserID(int) function "
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

        return u;
    }

    public boolean updateUserContactInfo(int userId, String firstName, String lastName, String phoneNumber) {
        boolean isSuccess = false;
        String sql = ""
                + "UPDATE "
                + "    User "
                + "SET "
                + "    FirstName = ?, "
                + "    LastName = ?, "
                + "    PhoneNumber = ? "
                + "WHERE "
                + "    ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNumber);
            ps.setInt(4, userId);
            int affectedLine = ps.executeUpdate();

            if (affectedLine == 1) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from updateUserContactInfo(int, String, String, String) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } catch (Exception e) {
            String msg = "A Random and Unexpected Exception have been throw "
                    + "from updateUserContactInfo(int, String, String, String) function "
                    + "while execusing task to get user role data.";
            this.log(Level.WARNING, msg, e);

        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from updateUserContactInfo(int, String, String, String) function "
                        + "while trying to close Result Set.";
                this.log(Level.WARNING, msg, e);
            }

            if (ps != null) try {
                ps.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from updateUserContactInfo(int, String, String, String) function "
                        + "while trying to close Prepared Statement.";
                this.log(Level.WARNING, msg, e);
            }

            if (connection != null) try {
                connection.close();
                connection = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw "
                        + "from updateUserContactInfo(int, String, String, String) function "
                        + "while trying to close Connection.";
                this.log(Level.WARNING, msg, e);
            }
        }

        return isSuccess;
    }

    public String getRefundAmountById(int userId) {
        String refundAmount = null;
        String sql = "Select * FROM User WHERE ID = " + userId + ";";
        
        try {
            rs = new SettingDAO().getDataFromQuery(sql);
            
            if (rs.next()) {
                refundAmount = rs.getString("RefundAmount");
            }
            
        } catch (Exception e) {
            this.log(Level.SEVERE, "nah", e);
            
        } finally {
            new SettingDAO().closeResourse();
        }
        
        return refundAmount;
    }

    public List<User> getCustomerBySalerId(int uid) {
        List<User> userList = null;
        String sql = ""
                + "SELECT "
                + "    u.ID as UserID,"
                + "    u.AvatarImage as UserAvatarImage,"
                + "    u.FirstName as UserFirstName,"
                + "    u.LastName as UserLastName,"
                + "    u.Email as UserEmail,"
                + "    u.PhoneNumber as UserPhoneNumber,"
                + "    u.Status as UserStatus,"
                + "    r.id as RoleID,"
                + "    r.Name as RoleName,"
                + "    a.ID as AddressID,"
                + "    a.Country as AddressCountry,"
                + "    a.TinhThanhPho as AddressTinhThanhPho,"
                + "    a.QuanHuyen as AddressQuanHuyen,"
                + "    a.PhuongXa as AddressPhuongXa,"
                + "    a.Details as AddressDetails "
                + "FROM "
                + "    User u "
                + "    JOIN Role r on r.id = u.RoleID "
                + "    LEFT JOIN Address a on a.UserID = u.id "
                + "    JOIN Bills b ON b.SalerID = u.ID "
                + "    JOIN User s ON b.CustomerID = s.ID "
                + "WHERE "
                + "    s.ID = " + uid + " "
                + "    AND r.ID = 1;";
        this.log(Level.SEVERE, sql, null);
        
        try {
            rs = new SettingDAO().getDataFromQuery(sql);

            while (rs.next()) {
                if (userList == null) {
                    userList = new ArrayList<>();
                }

                User u = new User()
                        .setId(rs.getInt("UserID"))
                        .setFirstName(rs.getString("UserFirstName"))
                        .setLastName(rs.getString("UserLastName"))
                        .setEmail(rs.getString("UserEmail"))
                        .setPhone(rs.getString("UserPhoneNumber"))
                        .setRole(new Role()
                                .setId(rs.getInt("RoleID"))
                                .setName(rs.getString("RoleName"))
                        )
                        .setStatus(rs.getString("UserStatus"))
                        .setAvatarImage(rs.getString("UserAvatarImage"))
                        .setAddressList(new ArrayList<>());
                if (!userList.contains(u)) {
                    String addressId = rs.getString("AddressID");
                    if (addressId != null && !addressId.isEmpty()) {
                        u.getAddressList().add(new Address()
                                .setId(rs.getInt("AddressID"))
                                .setCountry(rs.getString("AddressCountry"))
                                .setTinh_thanhpho(rs.getString("AddressTinhThanhPho"))
                                .setHuyen_quan(rs.getString("AddressQuanHuyen"))
                                .setXa_phuong(rs.getString("AddressPhuongXa"))
                                .setDetails(rs.getString("AddressDetails"))
                        );
                    } else {
                        u.setAddressList(null);
                    }
                    userList.add(u);
                } else {
                    int userListSize = userList.size();
                    userList.get(userListSize - 1).getAddressList().add(new Address()
                            .setId(rs.getInt("AddressID"))
                            .setCountry(rs.getString("AddressCountry"))
                            .setTinh_thanhpho(rs.getString("AddressTinhThanhPho"))
                            .setHuyen_quan(rs.getString("AddressQuanHuyen"))
                            .setXa_phuong(rs.getString("AddressPhuongXa"))
                            .setDetails(rs.getString("AddressDetails"))
                    );
                }
            }
            
        } catch (Exception e) {
            
        } finally {
            new SettingDAO().closeResourse();
        }
        
        return userList;
    }

    public class User {

        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private transient Role role;
        private String status;
        private transient String avatarImage;
        private List<Address> addressList;

        public User() {
        }

        public String getAvatarImage() {
            return avatarImage;
        }

        public User setAvatarImage(String avatarImage) {
            this.avatarImage = avatarImage;
            return this;
        }

        public int getId() {
            return id;
        }

        public User setId(int id) {
            this.id = id;
            return this;
        }

        public String getFirstName() {
            return firstName;
        }

        public User setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public User setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public User setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getPhone() {
            return phone;
        }

        public User setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Role getRole() {
            return role;
        }

        public User setRole(Role role) {
            this.role = role;
            return this;
        }

        public String getStatus() {
            return status;
        }

        public User setStatus(String status) {
            this.status = status;
            return this;
        }

        public List<Address> getAddressList() {
            return addressList;
        }

        public User setAddressList(List<Address> addressList) {
            this.addressList = addressList;
            return this;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + this.id;
            hash = 79 * hash + Objects.hashCode(this.email);
            hash = 79 * hash + Objects.hashCode(this.phone);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final User other = (User) obj;
            if (this.id != other.id) {
                return false;
            }
            if (!Objects.equals(this.email, other.email)) {
                return false;
            }
            return Objects.equals(this.phone, other.phone);
        }

        @Override
        public String toString() {
            return "User{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", role=" + role + ", status=" + status + '}';
        }

        public String getFullName() {
            return this.lastName + " " + this.firstName;
        }

    }

    public List<User> getAllUser() {
        List<User> userList = null;
        String sql = ""
                + "SELECT "
                + "    u.ID as UserID,"
                + "    u.AvatarImage as UserAvatarImage,"
                + "    u.FirstName as UserFirstName,"
                + "    u.LastName as UserLastName,"
                + "    u.Email as UserEmail,"
                + "    u.PhoneNumber as UserPhoneNumber,"
                + "    u.Status as UserStatus,"
                + "    r.id as RoleID,"
                + "    r.Name as RoleName,"
                + "    a.ID as AddressID,"
                + "    a.Country as AddressCountry,"
                + "    a.TinhThanhPho as AddressTinhThanhPho,"
                + "    a.QuanHuyen as AddressQuanHuyen,"
                + "    a.PhuongXa as AddressPhuongXa,"
                + "    a.Details as AddressDetails "
                + "FROM "
                + "    User u "
                + "    JOIN Role r on r.id = u.RoleID"
                + "    LEFT JOIN Address a on a.UserID = u.id;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (userList == null) {
                    userList = new ArrayList<>();
                }

                User u = new User()
                        .setId(rs.getInt("UserID"))
                        .setFirstName(rs.getString("UserFirstName"))
                        .setLastName(rs.getString("UserLastName"))
                        .setEmail(rs.getString("UserEmail"))
                        .setPhone(rs.getString("UserPhoneNumber"))
                        .setRole(new Role()
                                .setId(rs.getInt("RoleID"))
                                .setName(rs.getString("RoleName"))
                        )
                        .setStatus(rs.getString("UserStatus"))
                        .setAvatarImage(rs.getString("UserAvatarImage"))
                        .setAddressList(new ArrayList<>());
                if (!userList.contains(u)) {
                    String addressId = rs.getString("AddressID");
                    if (addressId != null && !addressId.isEmpty()) {
                        u.getAddressList().add(new Address()
                                .setId(rs.getInt("AddressID"))
                                .setCountry(rs.getString("AddressCountry"))
                                .setTinh_thanhpho(rs.getString("AddressTinhThanhPho"))
                                .setHuyen_quan(rs.getString("AddressQuanHuyen"))
                                .setXa_phuong(rs.getString("AddressPhuongXa"))
                                .setDetails(rs.getString("AddressDetails"))
                        );
                    } else {
                        u.setAddressList(null);
                    }
                    userList.add(u);
                } else {
                    int userListSize = userList.size();
                    userList.get(userListSize - 1).getAddressList().add(new Address()
                            .setId(rs.getInt("AddressID"))
                            .setCountry(rs.getString("AddressCountry"))
                            .setTinh_thanhpho(rs.getString("AddressTinhThanhPho"))
                            .setHuyen_quan(rs.getString("AddressQuanHuyen"))
                            .setXa_phuong(rs.getString("AddressPhuongXa"))
                            .setDetails(rs.getString("AddressDetails"))
                    );
                }
            }

        } catch (SQLException e) {
            String msg = "A SQLException have been throw "
                    + "from getAllUser function "
                    + "while execusing task to get all user data";
            this.log(Level.WARNING, msg, e);

        } catch (Exception e) {
            String msg = "A Random and Unexpected Exception have been throw "
                    + "from getAllUser function "
                    + "while execusing task to get all user data";
            this.log(Level.WARNING, msg, e);

        } finally {
            // Close resources
            if (rs != null) try {
                rs.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw from getAllUser function "
                        + "while trying to close Result Set";
                this.log(Level.WARNING, msg, e);
            }

            if (ps != null) try {
                ps.close();
                ps = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw from getAllUser function "
                        + "while trying to close Prepared Statement";
                this.log(Level.WARNING, msg, e);
            }

            if (connection != null) try {
                connection.close();
                connection = null;

            } catch (SQLException e) {
                String msg = "A SQLException have been throw from getAllUser function "
                        + "while trying to close Connection";
                this.log(Level.WARNING, msg, e);
            }
        }

        return userList;
    }

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
        System.out.println(new UserDAO().getUserContactInfoById(17));
    }

}
