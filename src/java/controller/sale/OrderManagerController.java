/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import dal.HoangAnhCustomEntityDAO;
import entity.HoangAnhCustomEntity;
import dal.LoginUserDAO;
import codebase.EmailSender.Email;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dal.BillDAO;
import java.util.ArrayList;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "OrderManager", urlPatterns = {"/ordermanager"})
public class OrderManagerController extends HttpServlet {

    static final int ITEM_PER_PAGE = 10;
    static HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
    static HttpSession session;

    protected void doGetProcess(HttpServletRequest request, HttpServletResponse response, List l)
            throws ServletException, IOException {
        try {
            LoginUserDAO luDAO = new LoginUserDAO();

            String type = request.getParameter("type");
            boolean canLoadMore = false;
            int page = 1;
            int billCount = l.size();
            int fromInPage = ITEM_PER_PAGE * (page - 1);
            int toInPage = ITEM_PER_PAGE * page;
            if (billCount > toInPage) {
                canLoadMore = true;
            } else {
                toInPage = billCount;
                canLoadMore = false;
            }

            List<HoangAnhCustomEntity.Bill> mainBillList = l.subList(fromInPage, toInPage);
            List<HoangAnhCustomEntity.StaffWithRole> saler = ceDAO.getAllStaffByRoleId(4);
            List<String> status = new BillDAO().getAllStatus();

            request.setAttribute("billList", mainBillList);
            request.setAttribute("allList", new Gson().toJson(l));
            request.setAttribute("page", page);
            request.setAttribute("canLoadMore", canLoadMore);
            request.setAttribute("type", type);
            request.setAttribute("saler", saler);
            request.setAttribute("status", status);
            request.getRequestDispatcher("admin/order.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPostProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "load-more" ->
                processLoadMoreAction(request, response);
            case "save" ->
                processSaveAction(request, response);
            default ->
                throw new AssertionError();
        }
    }

    protected void processSaveAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bid = Integer.parseInt(request.getParameter("bid"));
        int salerId = Integer.parseInt(request.getParameter("salerId"));
        String status = request.getParameter("status");
        String email = request.getParameter("saleEmail");

        if (ceDAO.isValidSaler(salerId, bid) && !status.equals("Received")) {
            Email.sendEmail(email,
                    "Assigned to take care customer for bill id: " + bid,
                    "You have assigned to take care of customer have bill id: " + bid + ", please work with all heart to raise our revenue <3");
        }

        if (status.equalsIgnoreCase("OnDelivery")) {
            ceDAO.sendOnDeliveryEmail(bid);
        }
        
        if (status.equalsIgnoreCase("ReShip")) {
            ceDAO.sendReShipEmail(bid);
        }
        
        if (status.equalsIgnoreCase("Received")) {
            ceDAO.sendReceivedEmail(bid);
        }

        ceDAO.saveBillInfor(bid, status, salerId);
    }

    protected void processLoadMoreAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String allBillListParam = request.getParameter("allList");
            Gson gSon = new Gson();
            List<HoangAnhCustomEntity.BillForOrderManager> billList = gSon.fromJson(allBillListParam, new TypeToken<List<HoangAnhCustomEntity.BillForOrderManager>>() {
            }.getType());
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

            List<HoangAnhCustomEntity.BillForOrderManager> mainBillList = billList.subList(fromInPage, toInPage);
            List<HoangAnhCustomEntity.StaffWithRole> saler = ceDAO.getAllStaffByRoleId(4);
            List<String> status = new ArrayList<>();
            status.add("Pending");
            status.add("OnDelivery");
            status.add("Received");
            status.add("Canceled");

//            if (request.getParameter("fromDate") != null && request.getParameter("toDate") != null) {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
//            }
            request.setAttribute("allList", new Gson().toJson(billList));
            request.setAttribute("billList", mainBillList);
            request.setAttribute("page", page);
            request.setAttribute("saler", saler);
            request.setAttribute("status", status);
            request.setAttribute("canLoadMore", canLoadMore);
            request.getRequestDispatcher("admin/loadMoreOrders.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<HoangAnhCustomEntity.BillForOrderManager> getBillByType(HttpServletRequest request, String type)
            throws ServletException, IOException {
        session = request.getSession();
        int uid = (int) session.getAttribute("uid");
        int roleId = (int) session.getAttribute("roleId");

        if (roleId == 4 || roleId == 6 || roleId == 2 || roleId == 7) {
            switch (type) {
                case "all" -> {
                    return ceDAO.getAllUserBill(uid, roleId);
                }
                case "date" -> {
                    String fromDate = request.getParameter("fromDate");
                    String toDate = request.getParameter("toDate");
                    return ceDAO.getAllUserBillByDate(fromDate, toDate, uid, roleId);
                }
                case "Received" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "Canceled" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "OnDelivery" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "PaymentPending" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "ReShip" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "Comfirmed" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "CanceledAfterReShip" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "Pending" -> {
                    return ceDAO.getAllUserBillByStatus(uid, roleId, type);
                }
                case "search" -> {
                    String inputSearch = request.getParameter("search");
                    return ceDAO.getAllUserBillByCusName(uid, roleId, inputSearch);
                }
            }
        }

        return null;
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
        String type = request.getParameter("type");

        if ("date".equals(type)) {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
        }

        if ("search".equals(type)) {
            String search = request.getParameter("search");
            request.setAttribute("search", search);
        }

        doGetProcess(request, response, getBillByType(request, type));
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
        doPostProcess(request, response);
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
