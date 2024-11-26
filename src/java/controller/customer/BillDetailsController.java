/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import entity.HoangAnhCustomEntity;
import dal.LoginUserDAO;
import dal.MyOrderDAO;
import entity.ThanhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "BillDetailsController", urlPatterns = {"/billdetail"})
public class BillDetailsController extends HttpServlet {

    HttpSession session;

    //default value cho trang feedback
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MyOrderDAO oDAO = new MyOrderDAO();
        session = request.getSession();
        LoginUserDAO luDAO = new LoginUserDAO();
        int uid = (int) session.getAttribute("uid");
        int bid = Integer.parseInt(request.getParameter("billId"));
        ThanhCustomEntity.Bill bill = oDAO.getBillByUidByBid(uid, bid);
        HoangAnhCustomEntity.CustomerInformationForOrderManager user = oDAO.getUser(uid);
        HoangAnhCustomEntity.Staff shipper = oDAO.getStaff(bill.getShipperId());

        request.setAttribute("b", bill);
        request.setAttribute("phonenumber", luDAO.getUserPhoneById(uid));
        request.setAttribute("user", user);
        request.setAttribute("shipper", shipper);
        request.getRequestDispatcher("customer/MyOrderDetail.jsp").forward(request, response);
    }

    //Dieu huong function
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
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
}
