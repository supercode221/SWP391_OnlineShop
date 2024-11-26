/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.HoangAnhCustomEntityDAO;
import dal.MyDAO;
import dal.ProductListManagerDAO;
import entity.ThanhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer Aspire 7
 */
public class AddToCartController extends HttpServlet {

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
        request.getRequestDispatcher("homecontroller").forward(request, response);
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
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid") == null) {
            request.setAttribute("msg", "You must login before buying!");
            request.getRequestDispatcher("public/Login.jsp").forward(request, response);
            return;
        }

        // Initialize required variables
        int uid = (int) session.getAttribute("uid");
        int productId = Integer.parseInt(request.getParameter("productId"));
        int sizeId = Integer.parseInt(request.getParameter("sizeId"));
        int colorId = Integer.parseInt(request.getParameter("colorId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
        ProductListManagerDAO productDAO = new ProductListManagerDAO();
        MyDAO dao = new MyDAO();

        // Retrieve all sizes and colors
        List<ThanhCustomEntity.Color> colors = productDAO.getAllColor();
        List<ThanhCustomEntity.Size> sizes = productDAO.getAllSize();

        // Retrieve the user's cart details
        MyDAO.CartDetails cartDetails = dao.getCartDetails(uid);
        List<MyDAO.Product> products;
        if (cartDetails != null) {
            products = cartDetails.getProducts();
        } else {
            products = new ArrayList<>();
        }

        // Find matching products in the cart
        for (MyDAO.Product product : products) {
            int sizeIdToCheck = getIdByName(product.getSize(), sizes);
            int colorIdToCheck = getIdByName(product.getColorName(), colors);

            if (product.getId() == productId && sizeId == sizeIdToCheck && colorId == colorIdToCheck) {
                if (quantity + product.getQuantity() > product.getStock()) {
                    String msg = "" + (product.getStock() - product.getQuantity()) + " product(s) have been added into yout cart because of stock. Please check your cart!";
                    dao.updateQuantity(uid, productId, product.getSize(), product.getColorName(), product.getStock());
                    redirectWithMessage(request, response, productId, msg);
                    return;
                } else {
                    if (ceDAO.addToCart(productId, colorId, sizeId, quantity, uid)) {
                        incrementCartCount(session);
                    }
                    redirectWithMessage(request, response, productId, "Product added successfully!");
                    return;
                }
            }
        }

        // Add product to cart if not already present
        if (ceDAO.addToCart(productId, colorId, sizeId, quantity, uid)) {
            incrementCartCount(session);
            redirectWithMessage(request, response, productId, "Product added successfully!");
        } else {
            redirectWithMessage(request, response, productId, "Failed to add product to cart.");
        }
    }

// Utility method to map name to ID
    private int getIdByName(String name, List<?> entities) {
        for (Object entity : entities) {
            if (entity instanceof ThanhCustomEntity.Size && ((ThanhCustomEntity.Size) entity).getSize().equalsIgnoreCase(name)) {
                return ((ThanhCustomEntity.Size) entity).getId();
            } else if (entity instanceof ThanhCustomEntity.Color && ((ThanhCustomEntity.Color) entity).getName().equalsIgnoreCase(name)) {
                return ((ThanhCustomEntity.Color) entity).getId();
            }
        }
        return 0; // Default ID if not found
    }

// Utility method to redirect with encoded message
    private void redirectWithMessage(HttpServletRequest request, HttpServletResponse response, int productId, String message) throws IOException, ServletException {
        String encodedMsg = URLEncoder.encode(message, "UTF-8");
        request.getRequestDispatcher("productdetail?productId=" + productId + "&msg=" + encodedMsg).forward(request, response);
    }

// Increment cart count in session
    private void incrementCartCount(HttpSession session) {
        int productInCart = (session.getAttribute("productInCart") != null)
                ? Integer.parseInt(session.getAttribute("productInCart").toString())
                : 0;
        session.setAttribute("productInCart", productInCart + 1);
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
