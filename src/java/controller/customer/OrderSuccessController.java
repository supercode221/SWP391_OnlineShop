/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.DAO;
import dal.MyDAO;
import entity.codebaseOld.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "OrderSuccess", urlPatterns = {"/success"})
public class OrderSuccessController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
        DAO haiDAO = new DAO();
        MyDAO myDAO = new MyDAO();
        HttpSession session = request.getSession();

        int uid = (int) session.getAttribute("uid");

        // Fetch user information
        User user = haiDAO.getUserByID(uid);

        // Fetch cart details
        MyDAO.CartDetails cartDetails = myDAO.getCartDetails(uid);

        // Calculate cart total
        double cartTotal = 0;
        if (cartDetails != null && cartDetails.getProducts() != null) {
            for (MyDAO.Product product : cartDetails.getProducts()) {
                if (product.getIsChecked() == 1 && product.getQuantity() > 0) {
                    cartTotal += product.getPrice() * product.getQuantity();
                }
            }
        }
         List<MyDAO.Product> products = cartDetails.getProducts();
         
        request.setAttribute("cartDetails", cartDetails);
        request.setAttribute("cartTotal", cartTotal);
        request.getRequestDispatcher("/customer/OrderSuccess.jsp").forward(request, response);
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
        processRequest(request, response);
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
