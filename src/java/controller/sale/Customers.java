/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import com.google.gson.Gson;
import controller.admin.AdminUsersController;
import controller.admin.UserDAO;
import controller.admin.UserDAO.User;
import dal.codebase.AddressDAO;
import entity.codebaseNew.Address;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class Customers extends HttpServlet {

    /**
     * fixed the front-end that this servlet always forward to
     */
    private final String fixedForwardFrontend = "sale-manager/customers.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        UserDAO dao = new UserDAO();
        int uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());

        int roleId = dao.getUserRoleByUserID(uid).getId();

        // process
        List<User> userList = dao.getUserByFilter(1, "all", "");
        List<String> statusList = dao.getAllUserStatus();

        if (roleId == 4) {
            userList = dao.getCustomerBySalerId(uid);
        }

        List<User> users = dao.getUserPageFromList(0, userList);

        boolean canloadmore = true;
        int usersSize = users == null ? 0 : users.size();
        if (usersSize < AdminUsersController.ITEM_PER_PAGE) {
            canloadmore = false;
        }

        // set data
        request.setAttribute("canloadmore", canloadmore);
        request.setAttribute("users", users);
        request.setAttribute("statusList", statusList);

        // forward
        request.getRequestDispatcher(this.fixedForwardFrontend).forward(request, response);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        String action = request.getParameter("action");

        // process
        switch (action) {
            case "load-more" ->
                processLoadMoreAction(request, response);
            case "filter" ->
                processFilterAction(request, response);
            case "addAddress" ->
                processAddAddressAction(request, response);
            case "load-user-info" ->
                processLoadUserInfoAction(request, response);
            case "update" ->
                processUpdateAction(request, response);
            default ->
                throw new AssertionError();
        }

        // set data
        // forward
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processUpdateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userId_raw = request.getParameter("userId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String addressId_raw = request.getParameter("addressId");

        int userId = Integer.parseInt(userId_raw);
        int addressId = Integer.parseInt(addressId_raw);

        if ("No phone number provided!".equals(phoneNumber)) {
            phoneNumber = "";
        }

        UserDAO dao = new UserDAO();
        boolean isSuccess = dao.updateUserContactInfo(userId, firstName, lastName, phoneNumber);

        response.setContentType("application/json");
        response.getWriter().write("{\"success\":" + isSuccess + "}");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processAddAddressAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userId_raw = request.getParameter("userId");
        int userId = Integer.parseInt(userId_raw);
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String details = request.getParameter("details");
        String note = request.getParameter("note");

        Address address = new Address();
        entity.codebaseNew.User user = new entity.codebaseNew.User();
        user.setId(userId);
        address.setUser(user);
//            address.setDetails(addressLine);
        address.setTinh_thanhpho(city);
        address.setHuyen_quan(district);
        address.setXa_phuong(ward);
        address.setNote(note);
        address.setDetails(details);

        boolean isSaved = new AddressDAO().addAddress(address);
        response.setContentType("application/json");
        response.getWriter().write("{\"success\":" + isSaved + "}");

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processLoadUserInfoAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userId_raw = request.getParameter("userId");
        int userId = Integer.parseInt(userId_raw);
        UserDAO dao = new UserDAO();
        User user = dao.getUserContactInfoById(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(user);
        response.getWriter().write(json);

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processFilterAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        UserDAO dao = new UserDAO();
        String searchInput = request.getParameter("searchInput");
        String status = request.getParameter("status");

        // set data
        List<User> users = new UserDAO().getUserByFilter(1, status, searchInput);
        List<String> statusList = dao.getAllUserStatus();

        // forward
        request.setAttribute("selectingStatus", status);
        request.setAttribute("searchInput", searchInput);
        request.setAttribute("statusList", statusList);
        request.setAttribute("filtering", true);
        request.setAttribute("users", users);
        request.getRequestDispatcher(this.fixedForwardFrontend).forward(request, response);

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processLoadMoreAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        UserDAO dao = new UserDAO();
        String page_raw = request.getParameter("page");

        int page = Integer.parseInt(page_raw);

        // process
        List<User> userList = dao.getUserByFilter(1, "all", "");
        List<String> statusList = dao.getAllUserStatus();

        List<User> users = dao.getUserPageFromList(page, userList);
        boolean canloadmore = true;
        if (users.size() < AdminUsersController.ITEM_PER_PAGE) {
            canloadmore = false;
        }

        // set data
        request.setAttribute("canloadmore", canloadmore);
        request.setAttribute("users", users);
        request.setAttribute("statusList", statusList);

        // forward
        request.getRequestDispatcher("sale-manager/loadMoreCustomer.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGetRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Log what ever you want instead of System.out.println() using
     * java.util.logging.Logger
     *
     * @param level java.util.logging.Level of the log.
     * @param msg optional message for the log
     * @param e optional exception for log
     */
    private void log(Level level, String msg, Throwable e) {
        Logger.getLogger(this.getClass().getName()).log(level, msg, e);
    }

}
