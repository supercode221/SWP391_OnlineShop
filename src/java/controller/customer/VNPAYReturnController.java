/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.BillDAO;
import dal.HoangAnhCustomEntityDAO;
import dal.codebase.OrderDAO;
import entity.HoangAnhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet(name = "VNPAYReturnController", urlPatterns = {"/vnpayReturn"})
public class VNPAYReturnController extends HttpServlet {

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
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        OrderDAO orderDao = new OrderDAO();
        HttpSession session = request.getSession();
        int billId = (int) session.getAttribute("billId");
        if (transactionStatus.equals("00")) {
            HoangAnhCustomEntityDAO dao = new HoangAnhCustomEntityDAO();
            dao.saveBillStatus(billId, "Pending");
            request.setAttribute("billId", billId);
            request.getRequestDispatcher("customer/orderSuccess.jsp").forward(request, response);
        } else {
//             orderDao.deleteOrderByBillId(billId);
//             orderDao.deleteBillById(billId);
            request.getRequestDispatcher("customer/orderFail.jsp").forward(request, response);
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

}
