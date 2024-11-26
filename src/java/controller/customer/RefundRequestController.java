/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.customer;

import codebase.EmailSender.Email;
import controller.admin.UserDAO;
import controller.admin.UserDAO.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Nguyễn Nhật Minh
 */
public class RefundRequestController extends HttpServlet {
   
    /**
     * fixed the front-end that this servlet always forward to
     */
    private final String fixedForwardFrontend = "customer/RefundRequest.jsp";
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            
            // process
            
            // set data
            
            // forward
            request.getRequestDispatcher(this.fixedForwardFrontend).forward(request, response);
    } 
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            int userId = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            User user = new UserDAO().getUserContactInfoById(userId);
            String refundAmount = new UserDAO().getRefundAmountById(userId);
            if (Integer.parseInt(refundAmount) == 0) {
                request.setAttribute("msg", "Your account have not any refund amount to create refund request.");
                request.getRequestDispatcher("refund").forward(request, response);
                return;
            }
            
            String bank = request.getParameter("bank");
            String account = request.getParameter("account");
            String name = request.getParameter("name");
            
            // process
            String customerEmail = user.getEmail();
            String toCustomer = "Bạn đã gửi thành công xác nhận yêu cầu hoàn tiền. "
                    + "Hãy chú ý hộp thư điện tử của bạn, yêu cầu của bạn sẽ được "
                    + "các nhân viên chăm sóc khách hàng liên hệ xác nhận trực tiếp "
                    + "trong vài ngày tới.";
            Email.sendEmail(customerEmail, "Xác nhận yêu cầu hoàn tiền.", toCustomer);
            
            String systemEmail = ResourceBundle.getBundle("config.database").getString("email");
            String toSystem = "Yêu cầu hoàn tiền từ khách hàng" + user.getLastName() + " " + user.getLastName() + ", User ID: " + userId + ". "
                    + "Ngân hàng: " + bank + ", "
                    + "Số tài khoản: " + account + ", "
                    + "Chủ tài khoản: " + name + ", "
                    + "Số tiền yêu cầu hoàn trả: " + refundAmount + ", "
                    + "Hãy chú ý kiểm tra và xử lý trong thời gian ngắn.";
            Email.sendEmail(systemEmail, "Yêu cầu Hoàn tiền.", toSystem);
            
            // set data
            
            // forward

    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Log what ever you want instead of System.out.println()
     * using java.util.logging.Logger
     * 
     * @param level java.util.logging.Level of the log.
     * @param msg optional message for the log
     * @param e optional exception for log
     */
    private void log(Level level, String msg, Throwable e) {
        Logger.getLogger(this.getClass().getName()).log(level, msg, e);
    }

}
