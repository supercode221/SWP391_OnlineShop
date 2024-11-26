/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.DAO;
import dal.MyDAO;
import entity.codebaseOld.User;
import entity.codebaseNew.Address;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.Writer;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CartContactServlet", urlPatterns = {"/cartcontact"})
public class CartContactController extends HttpServlet {

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
        Writer out = response.getWriter();
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
        
        if (cartTotal == 0) {
            response.sendRedirect("cart");
        }

        // Fetch address information for the user
        Address address = haiDAO.getAllAddressesByUserID(uid);
        List<Address>  addressList = haiDAO.getAddressesByUserId(uid);
        // Set attributes for JSP
        request.setAttribute("user", user);
        request.setAttribute("cartDetails", cartDetails);
        request.setAttribute("cartTotal", cartTotal);
        request.setAttribute("addresses", addressList); 
        request.setAttribute("addressList", addressList); 
        // Forward to CartContactController.jsp
        request.getRequestDispatcher("/customer/CartContact.jsp").forward(request, response);
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
