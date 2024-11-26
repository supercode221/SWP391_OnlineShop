/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.HoangAnhCustomEntityDAO;
import entity.HoangAnhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admindashboard"})
public class AdminDashboardController extends HttpServlet {

    private static HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
    private static final int[] legitRoleId = {2, 6};
    HttpSession session;

    protected void doGetProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        int roleId = (int) session.getAttribute("roleId");

        if (!isValidRoleId(roleId)) {
            request.setAttribute("msg", "You have no permission!");
            request.getRequestDispatcher("homecontroller").forward(request, response);
        } else {
            request.getRequestDispatcher("admin/dashboard.jsp").forward(request, response);
        }
    }

    protected void doPostProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "clickToAjax" ->
                loadAJAXDataProcess(request, response);
            case "fromToFilter" ->
                loadFromToDataProcess(request, response);
            default ->
                throw new AssertionError();
        }
    }

    protected void loadFromToDataProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String filterType = request.getParameter("filterType");
        
        HoangAnhCustomEntity.DashBoardContent dashboardContent = ceDAO.getDashBoardContentByFilterFromTo(filterType, toDate, fromDate);
        
        List<HoangAnhCustomEntity.RevenueChart> revenueChartData = ceDAO.getRevenueChartData();
        List<HoangAnhCustomEntity.RevenueChart> pendingChartData = ceDAO.getPendingChartData();
        List<HoangAnhCustomEntity.RevenueChart> canceledChartData = ceDAO.getRefundChartData();
        List<HoangAnhCustomEntity.RevenueChart> avgChartData = ceDAO.getAVGChartData();
        
        JSONArray revenueArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : revenueChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                revenueArray.put(tmp);
            }
        }
        
        JSONArray pendingArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : pendingChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                pendingArray.put(tmp);
            }
        }
        
        JSONArray refundArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : canceledChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                refundArray.put(tmp);
            }
        }
        
        JSONArray avgArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : avgChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                avgArray.put(tmp);
            }
        }
        
        request.setAttribute("dashboardContent", dashboardContent);
        request.setAttribute("revenueChartData", revenueArray);
        request.setAttribute("pendingChartData", pendingArray);
        request.setAttribute("canceledChartData", refundArray);
        request.setAttribute("avgChartData", avgArray);
        request.setAttribute("revenueChartDataNotJson", revenueChartData);
        request.setAttribute("pendingChartDataNotJson", pendingChartData);
        request.setAttribute("canceledChartDataNotJson", canceledChartData);
        request.setAttribute("avgChartDataNotJson", avgChartData);
        request.setAttribute("billYear", ceDAO.getAllBillYear());
        request.getRequestDispatcher("admin/dashboardajaxcontent.jsp").forward(request, response);
    }
    
    protected void loadAJAXDataProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filterValue = request.getParameter("filterValue");
        HoangAnhCustomEntity.DashBoardContent dashboardContent = new HoangAnhCustomEntity.DashBoardContent();
        HoangAnhCustomEntity.RevenueChart revenueChart = new HoangAnhCustomEntity.RevenueChart();

        List<HoangAnhCustomEntity.RevenueChart> revenueChartData = ceDAO.getRevenueChartData();
        List<HoangAnhCustomEntity.RevenueChart> pendingChartData = ceDAO.getPendingChartData();
        List<HoangAnhCustomEntity.RevenueChart> canceledChartData = ceDAO.getRefundChartData();
        List<HoangAnhCustomEntity.RevenueChart> avgChartData = ceDAO.getAVGChartData();

        if (request.getParameter("filterType").isBlank() || request.getParameter("filterType").isEmpty() || request.getParameter("filterType") == null) {
            dashboardContent = ceDAO.getDashBoardContentByFilter(filterValue, null);
        } else {
            String filterType = request.getParameter("filterType");

            dashboardContent = ceDAO.getDashBoardContentByFilter(filterValue, filterType);
        }

        JSONArray revenueArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : revenueChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                revenueArray.put(tmp);
            }
        }
        
        JSONArray pendingArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : pendingChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                pendingArray.put(tmp);
            }
        }
        
        JSONArray refundArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : canceledChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                refundArray.put(tmp);
            }
        }
        
        JSONArray avgArray = new JSONArray();
        for (HoangAnhCustomEntity.RevenueChart a : avgChartData) {
            for (HoangAnhCustomEntity.YearRevenue b : a.getRevenueByMonth()) {
                JSONObject tmp = new JSONObject();
                tmp.put("year", a.getYear());
                tmp.put("month", b.getMonth());
                tmp.put("revenue", b.getRevenue());
                avgArray.put(tmp);
            }
        }

        request.setAttribute("dashboardContent", dashboardContent);
        request.setAttribute("revenueChartData", revenueArray);
        request.setAttribute("pendingChartData", pendingArray);
        request.setAttribute("canceledChartData", refundArray);
        request.setAttribute("avgChartData", avgArray);
        request.setAttribute("revenueChartDataNotJson", revenueChartData);
        request.setAttribute("pendingChartDataNotJson", pendingChartData);
        request.setAttribute("canceledChartDataNotJson", canceledChartData);
        request.setAttribute("avgChartDataNotJson", avgChartData);
        request.setAttribute("billYear", ceDAO.getAllBillYear());
        request.getRequestDispatcher("admin/dashboardajaxcontent.jsp").forward(request, response);
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
        doGetProcess(request, response);
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

    private boolean isValidRoleId(int roleId) {
        if (roleId == -1) {
            return false;
        } else {
            for (int id : legitRoleId) {
                if (roleId == id) {
                    return true;
                }
            }
        }
        return false;
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
