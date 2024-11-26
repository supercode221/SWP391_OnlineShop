/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.DAO;
import dal.MyDAO;
import dal.codebase.OrderDAO;
import entity.codebaseOld.Bill;
import entity.codebaseOld.Order;
import entity.codebaseOld.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
@WebServlet(name = "CheckoutUserController", urlPatterns = {"/checkoutUser"})
public class CheckoutUserController extends HttpServlet {

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
        HttpSession session = request.getSession();
        int uid = (int) session.getAttribute("uid");

        // Process payment and order submission
        String paymentMethod = request.getParameter("paymentMethod");
        String address = request.getParameter("address");
        String addressAddressID = request.getParameter("address");

        MyDAO myDAO = new MyDAO();
        MyDAO.CartDetails cartDetails = myDAO.getCartDetails(uid);
        double cartTotal = Double.parseDouble(request.getParameter("cartTotal"));
        OrderDAO orderDao = new OrderDAO();

        int saler = orderDao.getRandomStaffByRoleId(4).getId();
        int shipper = orderDao.getRandomStaffByRoleId(7).getId();
        
        Bill bill = new Bill();
        bill.setTotalPrice(cartTotal);
        bill.setCustomerId(uid);
        bill.setAddressId(Integer.parseInt(addressAddressID));
        bill.setPaymentMethodId(paymentMethod.equals("VNPAY") ? 1 : 2);
        bill.setShipperId(shipper);
        bill.setSalerId(saler);
        bill.setShipMethodId(1);
        bill.setStatus(paymentMethod.equals("VNPAY") ? "PaymentPending" : "Pending");
        int billId = orderDao.createBill(bill);
        if (billId > 0) {
            for (MyDAO.Product product : cartDetails.getProducts()) {
                if (product.getIsChecked() == 1 && product.getQuantity() > 0) {
                    Order order = new Order();
                    order.setProductId(product.getId());
                    order.setQuantity(product.getQuantity());
                    order.setTotalPrice(product.getPrice() * product.getQuantity());
                    order.setColorId(orderDao.getColorByCode(product.getColorCode()).getColorId());
                    order.setSizeId(orderDao.getSizeByName(product.getSize()).getSizeId());
                    order.setBillId(billId);
                    int colorId = orderDao.getColorByCode(product.getColorCode()).getColorId();
                    int sizeId = orderDao.getSizeByName(product.getSize()).getSizeId();
                    try {
                        orderDao.reduceQuantity(product.getId(), colorId, sizeId, product.getQuantity());
                    } catch (SQLException ex) {
                        Logger.getLogger(CheckoutUserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    orderDao.createOrder(order);
                    
                    int productInCart = Integer.parseInt(session.getAttribute("productInCart").toString());
                    productInCart--;
                    session.setAttribute("productInCart", productInCart);
                    
                    orderDao.deleteProductFromCartByProductIdAndUserId(product.getId(), uid, orderDao.getColorByCode(product.getColorCode()).getColorId(), orderDao.getSizeByName(product.getSize()).getSizeId());
                }
            }
            if (paymentMethod.equals("VNPAY")) {
                session.setAttribute("billId", billId);
                response.sendRedirect("vnpayajax?cartTotal=" + Math.round(cartTotal));
            } else {
                session.setAttribute("billId", billId);
                request.getRequestDispatcher("customer/orderSuccess.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("customer/orderFail.jsp");
        }
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
