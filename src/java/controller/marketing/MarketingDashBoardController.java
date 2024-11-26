/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.MarketingDAO;
import dal.MarketingDAO.MarketingChart;
import dal.MarketingDAO.MarketingStatistic;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Nguyễn Nhật Minh
 */
@WebServlet(name = "MarketingDashBoard", urlPatterns = {"/marketingdashboard"})
public class MarketingDashBoardController extends HttpServlet {

    /**
     * fixed the front-end that this servlet always forward to
     */
    private final String fixedForwardFrontend = "marketing/dashboard.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        // session data
        HttpSession session = request.getSession();
        int roleId = Integer.parseInt(session.getAttribute("roleId").toString());
        int userId = Integer.parseInt(session.getAttribute("uid").toString());
        // init data
        int year = LocalDate.now().minusDays(7).getYear();
        int month = LocalDate.now().minusDays(7).getMonthValue();
        int day = LocalDate.now().minusDays(7).getDayOfMonth();
        String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(year - 1900, month - 1, day));
        String toDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // process
        // authorization
        if (roleId != 2 && roleId != 3) {
            response.sendRedirect("homecontroller");
            return;
        }
        // process statistic data within fromDate and toDate
        MarketingDAO dao = new MarketingDAO();
        MarketingStatistic statistic = dao.getStatistic(fromDate, toDate);
        MarketingChart chart = dao.getChart(fromDate, toDate);

        // set data
        // sticking data
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        // statistic data
        request.setAttribute("statistic", statistic);
        request.setAttribute("chart", chart.toString());

        // forward
        request.getRequestDispatcher(this.fixedForwardFrontend).forward(request, response);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        // action
        String action = request.getParameter("action");

        // process
        // switch action
        switch (action) {
            case "load-chart" ->
                processLoadChartAction(request, response);
            case "load-statistic-data" ->
                processLoadSatisticDataAction(request, response);
            default ->
                throw new AssertionError();
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processLoadSatisticDataAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        // process
        // bypass
        // set data
        // sticking data
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        // statistic data
        request.setAttribute("statistic", new MarketingDAO().getStatistic(fromDate, toDate));
        request.setAttribute("chart", new MarketingDAO().getChart(fromDate, toDate));
        
        // forward
        request.getRequestDispatcher("marketing/statistic-data.jsp").forward(request, response);

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processLoadChartAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // get data
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        // process
        // by pass
        // set data
        // sticking data
        // statistic data
        Writer out = response.getWriter();
        MarketingChart main = new MarketingDAO().getChart(fromDate, toDate);
        out.write(main.toString());
        this.log(main.toString());

        // forward
        // ajax to get chart data only
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

    /**
     * Log what ever you want instead of System.out.println() using
     * java.util.logging.Logger
     *
     * @param level java.util.logging.Level of the log.
     * @param msg optional message for the log
     * @param e optional exception for log
     */
    private void log(Level level, String msg, Throwable e) {
        Logger.getLogger(this.getClass().getName()).log(level, msg, e);
    }

}
