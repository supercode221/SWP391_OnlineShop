/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.HoangAnhCustomEntityDAO;
import entity.HoangAnhCustomEntity;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "WishList", urlPatterns = {"/wishlist"})
public class WishListController extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("action");
            switch (action) {
                case "view" ->
                    viewWishList(request, response);
                case "add" -> {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    addWishList(request, response, productId);
                }
                case "delete" -> {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    deleteWishList(request, response, productId);
                }
                case "deleteInView" -> {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    deleteWishListInWLView(request, response, productId);
                }
                default ->
                    throw new AssertionError();
            }
        }
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
    protected void deleteWishListInWLView(HttpServletRequest request, HttpServletResponse response, int productId)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int uid = (int) session.getAttribute("uid");
        HoangAnhCustomEntityDAO dao = new HoangAnhCustomEntityDAO();
        dao.deleteItemInUserWishList(uid, productId);
        viewWishList(request, response);
    }

    protected void deleteWishList(HttpServletRequest request, HttpServletResponse response, int productId)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            request.setAttribute("msg", "You must login to delete your wish list.");
            request.getRequestDispatcher("login").forward(request, response);
        } else {
            int uid = (int) session.getAttribute("uid");
            HoangAnhCustomEntityDAO dao = new HoangAnhCustomEntityDAO();
            dao.deleteItemInUserWishList(uid, productId);
            request.getRequestDispatcher("productdetail?productId=" + productId).forward(request, response);
        }
    }

    protected void viewWishList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int uid = (int) session.getAttribute("uid");
        HoangAnhCustomEntityDAO dao = new HoangAnhCustomEntityDAO();
        request.setAttribute("wishList", dao.getWishListByUserId(uid));
        request.getRequestDispatcher("customer/WishList.jsp").forward(request, response);
    }

    protected void addWishList(HttpServletRequest request, HttpServletResponse response, int productId)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        HoangAnhCustomEntityDAO dao = new HoangAnhCustomEntityDAO();
        if (session.getAttribute("uid") == null) {
            request.setAttribute("msg", "You must login before wish something ");
            request.getRequestDispatcher("login").forward(request, response);
        } else {
            int uid = (int) session.getAttribute("uid");
            if (dao.addToWishList(uid, productId)) {
                request.getRequestDispatcher("productdetail?productId=" + productId).forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
