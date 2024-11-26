/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.HoangAnhCustomEntityDAO;
import entity.HoangAnhCustomEntity;
import dal.LoginUserDAO;
import dal.MyOrderDAO;
import entity.ThanhCustomEntity;
import codebase.EmailSender.Email;
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
 * @author Lenovo
 */
@WebServlet(name = "MyOrderController", urlPatterns = {"/billlist"})
public class MyOrderController extends HttpServlet {

    HttpSession session;
    static final int ITEM_PER_PAGE = 3;
    MyOrderDAO oDAO = new MyOrderDAO();

    //default value cho trang feedback
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        session = request.getSession();
        LoginUserDAO luDAO = new LoginUserDAO();
        int uid = (int) session.getAttribute("uid");
        //lấy list sắp xếp bill từ mới nhất đến cũ nhất
        List<ThanhCustomEntity.Bill> billList = oDAO.getBillByUid(uid);

        boolean canLoadMore = false;
        int page = 1;
        int feedbackCount = billList.size();
        int fromInPage = ITEM_PER_PAGE * (page - 1);
        int toInPage = ITEM_PER_PAGE * page;
        if (feedbackCount > toInPage) {
            canLoadMore = true;
        } else {
            toInPage = feedbackCount;
            canLoadMore = false;
        }

        List<ThanhCustomEntity.Bill> mainBillList = billList.subList(fromInPage, toInPage);

        request.setAttribute("billList", mainBillList);
        request.setAttribute("phonenumber", luDAO.getUserPhoneById(uid));
        request.setAttribute("username", session.getAttribute("username"));
        request.setAttribute("page", page);
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("customer/MyOrder.jsp").forward(request, response);
    }

    //Dieu huong function
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "load-more" ->
                processLoadMore(request, response);
            case "rebuy" ->
                processRebuy(request, response);
            case "cancel" ->
                processCancel(request, response);
            default ->
                throw new AssertionError();
        }
    }

    protected void processRebuy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        int uid = (int) session.getAttribute("uid");
        int bid = Integer.parseInt(request.getParameter("bidRebuy"));
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
        List<HoangAnhCustomEntity.Order> oList = oDAO.getOrderByBillID(bid);
        for (HoangAnhCustomEntity.Order o : oList) {
            int productId = o.getProductId();
            int sizeId = o.getSize().getSizeId();
            int colorId = o.getColor().getColorId();
            int quantity = o.getQuantity();

            if (ceDAO.addToCart(productId, colorId, sizeId, quantity, uid)) {
                int productInCart = Integer.parseInt(session.getAttribute("productInCart").toString());
                productInCart++;
                session.setAttribute("productInCart", productInCart);
            }
        }
    }

    protected void processCancel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        int uid = (int) session.getAttribute("uid");
        int bid = Integer.parseInt(request.getParameter("bidCancel"));
        String email = oDAO.getSaleEmailByBillId(bid);

        if (oDAO.cancelBill(bid, true)) {
            Email.sendEmail(email,
                    "Bill id: [" + bid + "] has been cancel by customer!",
                    "You have pending cancel id: [" + bid + "], please update information or contact user!");
        }
    }

    protected void processLoadMore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        LoginUserDAO luDAO = new LoginUserDAO();
        int uid = (int) session.getAttribute("uid");
        List<ThanhCustomEntity.Bill> billList = oDAO.getBillByUid(uid);
        int page = Integer.parseInt(request.getParameter("page"));

        boolean canLoadMore = false;
        int feedbackCount = billList.size();
        int fromInPage = ITEM_PER_PAGE * (page - 1);
        int toInPage = ITEM_PER_PAGE * page;
        if (feedbackCount > toInPage) {
            canLoadMore = true;
        } else {
            toInPage = feedbackCount;
            canLoadMore = false;
        }

        List<ThanhCustomEntity.Bill> mainBillList = billList.subList(fromInPage, toInPage);

        request.setAttribute("billList", mainBillList);
        request.setAttribute("phonenumber", luDAO.getUserPhoneById(uid));
        request.setAttribute("username", session.getAttribute("username"));
        request.setAttribute("page", page);
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("customer/MoreItemForMyOrder.jsp").forward(request, response);
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
