/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.HoangAnhCustomEntityDAO;
import entity.HoangAnhCustomEntity;
import dal.LoginUserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "Feedback", urlPatterns = {"/feedback"})
public class FeedbackController extends HttpServlet {

    HttpSession session;
    static final int ITEM_PER_PAGE = 4;
    private static final Logger logger = Logger.getLogger(FeedbackController.class.getName());

    //default value cho trang feedback
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
        session = request.getSession();
        LoginUserDAO luDAO = new LoginUserDAO();
        int uid = (int) session.getAttribute("uid");
        List<HoangAnhCustomEntity.Bill> billList = ceDAO.getBillByUid(uid);

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

        List<HoangAnhCustomEntity.Bill> mainBillList = billList.subList(fromInPage, toInPage);

        request.setAttribute("billList", mainBillList);
        request.setAttribute("phonenumber", luDAO.getUserPhoneById(uid));
        request.setAttribute("username", session.getAttribute("username"));
        request.setAttribute("page", page);
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("customer/UserFeedbackPage.jsp").forward(request, response);
    }

    //Dieu huong function
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "getproductforfeedback" ->
                processProductFeedbackRequest(request, response);
            case "getbillforfeedback" ->
                processBillFeedbackRequest(request, response);
            case "productfeedbacksent" ->
                processProductFeedbackSentRequest(request, response);
            case "billfeedbacksent" ->
                processBillFeedbackSentRequest(request, response);
            case "load-more" ->
                processLoadMore(request, response);
            default ->
                throw new AssertionError();
        }
    }

    protected void processLoadMore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
        session = request.getSession();
        LoginUserDAO luDAO = new LoginUserDAO();
        int uid = (int) session.getAttribute("uid");
        List<HoangAnhCustomEntity.Bill> billList = ceDAO.getBillByUid(uid);
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

        List<HoangAnhCustomEntity.Bill> mainBillList = billList.subList(fromInPage, toInPage);

        request.setAttribute("billList", mainBillList);
        request.setAttribute("phonenumber", luDAO.getUserPhoneById(uid));
        request.setAttribute("username", session.getAttribute("username"));
        request.setAttribute("page", page);
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("customer/MoreItemForFeedback.jsp").forward(request, response);
    }

    //Thuc hien insert du lieu vao database cua bill
    protected void processBillFeedbackSentRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Session management and DAO
        session = request.getSession();
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();

        // Read the request body for JSON data
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        // Parse the JSON object from the request body
        String jsonString = jsonBuffer.toString();
        JSONObject jsonData = new JSONObject(jsonString);

        // Fetch parameters from the JSON object
        int uid = (int) session.getAttribute("uid");
        int bid = jsonData.getInt("billId");
        int star = jsonData.getInt("starCount");
        String comment = jsonData.optString("comment", ""); // Use optString to avoid null

        // Retrieving the image list from JSON
        JSONArray imgListArray = jsonData.getJSONArray("imgList");
        String[] imageArray = new String[imgListArray.length()];

        for (int i = 0; i < imgListArray.length(); i++) {
            imageArray[i] = imgListArray.getString(i); // Get each image string (e.g., Base64 encoded or URL)
        }

        // Now insert feedback and feedback media into the database
        ceDAO.insertBillFeedback(uid, bid, star, comment);
        ceDAO.insertBillFeedbackMedia(uid, bid, imageArray);

        // Respond back with success
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\": \"success\"}");
    }

    //Thuc hien insert du lieu vao database cho feedback cua product
    protected void processProductFeedbackSentRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Session management and DAO
        session = request.getSession();
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();

        // Read the request body for JSON data
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        // Parse the JSON object from the request body
        String jsonString = jsonBuffer.toString();
        JSONObject jsonData = new JSONObject(jsonString);

        // Fetch parameters from the JSON object
        int uid = (int) session.getAttribute("uid");
        int pid = jsonData.getInt("productId");
        int oid = jsonData.getInt("orderId");
        int star = jsonData.getInt("starCount");
        String comment = jsonData.optString("comment", ""); // Use optString to avoid null

        // Retrieving the image list from JSON
        JSONArray imgListArray = jsonData.getJSONArray("imgList");
        String[] imageArray = new String[imgListArray.length()];

        for (int i = 0; i < imgListArray.length(); i++) {
            imageArray[i] = imgListArray.getString(i); // Get each image string (e.g., Base64 encoded or URL)
        }

        // Now insert feedback and feedback media into the database
        ceDAO.insertProductFeedback(uid, oid, pid, star, comment);
        ceDAO.insertProductFeedbackMedia(uid, oid, pid, imageArray);

        // Respond back with success
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\": \"success\"}");
    }

    //Thuc hien lay thong tin product de feedback
    protected void processProductFeedbackRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
        int pid = Integer.parseInt(request.getParameter("pid"));

        request.setAttribute("product", ceDAO.getDetailProductbyId(pid));

        request.getRequestDispatcher("customer/ProductFeedbackPopUp.jsp").forward(request, response);
    }

    //Thuc hien lay thong tin bill de feedback
    protected void processBillFeedbackRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
        int bid = Integer.parseInt(request.getParameter("bid"));

        session = request.getSession();

        request.setAttribute("bill", ceDAO.getBillByUidAndBillID((int) session.getAttribute("uid"), bid));

        request.getRequestDispatcher("customer/BillFeedbackPopUp.jsp").forward(request, response);
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
