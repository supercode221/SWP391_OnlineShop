/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import controller.admin.UserDAO.User;
import entity.codebaseNew.Role;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class AdminUsersController extends HttpServlet {

    public static final int ITEM_PER_PAGE = 4;

    /**
     * fixed the front-end that this servlet always forward to
     */
    private final String fixedForwardFrontend = "admin/users.jsp";

    /**
     * Processes requests for HTTP <code>GET</code> methods.
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
        List<User> users = dao.getUserPage(1);
        List<Role> roleList = dao.getAllRole();
        List<String> statusList = dao.getAllUserStatus();

        // process
        // set data
        request.setAttribute("users", users);
        request.setAttribute("roleList", roleList);
        request.setAttribute("statusList", statusList);

        // forward
        request.getRequestDispatcher(this.fixedForwardFrontend).forward(request, response);
    }

    /**
     * Processes requests for HTTP <code>POST</code> methods.
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

        // process set data and forward
        switch (action) {
            case "update" ->
                this.processUpdateAction(request, response);
            case "filter" ->
                this.processFilterAction(request, response);
            case "load-more" ->
                this.processLoadMoreAction(request, response);
            default ->
                throw new AssertionError();
        }
    }

    /**
     * Processes requests for HTTP <code>POST</code> methods to process
     * load-more-user-info action
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processLoadMoreAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = Integer.parseInt(request.getParameter("page"));

        UserDAO dao = new UserDAO();
        List<User> users = dao.getUserPage(page);
        List<Role> roleList = dao.getAllRole();
        List<String> statusList = dao.getAllUserStatus();

        // process
        // set data
        request.setAttribute("users", users);
        request.setAttribute("roleList", roleList);
        request.setAttribute("statusList", statusList);

        request.getRequestDispatcher("admin/loadMoreUsers.jsp").forward(request, response);

    }

    /**
     * Processes requests for HTTP <code>POST</code> methods to process
     * load-more-user-info action
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processUpdateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            String status = request.getParameter("status");

            if (new UserDAO().updateUser(userId, roleId, status)) {
                this.log("Update user success for userId: " + userId);
            } else {
                this.log(Level.WARNING, "Update failed for userId: " + userId, null);
            }

            response.sendRedirect("users");
        } catch (NumberFormatException e) {
            this.log(Level.WARNING, "Invalid userId or roleId", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
        }

    }

    /**
     * Processes requests for HTTP <code>POST</code> methods to process filter
     * action
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processFilterAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get data
        String raw_roleId = request.getParameter("roleId");
        String status = request.getParameter("status");
        String searchInput = request.getParameter("searchInput");

        // process
        boolean isEmptySearchInput = searchInput == null
                || searchInput.isEmpty()
                || searchInput.isBlank();
        boolean isEmptyRoleId = "all".equalsIgnoreCase(raw_roleId)
                || raw_roleId == null
                || raw_roleId.isEmpty()
                || raw_roleId.isBlank();
        boolean isEmptyStatus = "all".equalsIgnoreCase(status)
                || status == null
                || status.isEmpty()
                || status.isBlank();
        if (isEmptyRoleId
                && isEmptyStatus
                && isEmptySearchInput) {
            response.sendRedirect("users");
            return;
        }

        int roleId = -1;
        String selectingRoleId = null;
        String selectingStatus = null;
        if (!"all".equalsIgnoreCase(raw_roleId)) {
            roleId = Integer.parseInt(raw_roleId);
            selectingRoleId = roleId + "";
        }
        if (!"all".equalsIgnoreCase(status)) {
            selectingStatus = status;
        }

        UserDAO dao = new UserDAO();
        List<User> filter = dao.getUserByFilter(roleId, status, searchInput);
        List<Role> roleList = dao.getAllRole();
        List<String> statusList = dao.getAllUserStatus();

        // set data
        request.setAttribute("filtering", true);
        request.setAttribute("users", filter);
        request.setAttribute("selectingRoleId", selectingRoleId);
        request.setAttribute("selectingStatus", selectingStatus);
        request.setAttribute("searchInput", searchInput);
        request.setAttribute("roleList", roleList);
        request.setAttribute("statusList", statusList);

        // forward
        request.getRequestDispatcher(this.fixedForwardFrontend).forward(request, response);
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
        HttpSession session = request.getSession();

        if ((int)session.getAttribute("roleId") != 2) {
            request.setAttribute("msg", "You have no permission!");
            request.getRequestDispatcher("homecontroller").forward(request, response);
        } else {
            processGetRequest(request, response);
        }
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
    }

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
    }// </editor-fold>

}
