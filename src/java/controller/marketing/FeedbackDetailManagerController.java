/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.codebase.FeedbackDAO;
import entity.codebaseOld.Feedback;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "FeedbackDetail", urlPatterns = {"/feedbackdetail"})
public class FeedbackDetailManagerController extends HttpServlet {
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy feedbackId từ tham số request
        String feedbackIdParam = request.getParameter("feedbackId");

        // Kiểm tra nếu feedbackId không phải là null và hợp lệ
        if (feedbackIdParam != null) {
            int feedbackId = Integer.parseInt(feedbackIdParam); // Chuyển đổi thành int

            FeedbackDAO feedbackDAO = new FeedbackDAO();

            // Lấy feedback theo feedbackId
            Feedback feedback = feedbackDAO.getFeedbackById(feedbackId);

            // Nếu feedback không null, lấy media links
            if (feedback != null) {
                feedback.setLinkToMedia(feedbackDAO.getMediaLinks(feedbackId));
            }

            // Lưu feedback vào request để chuyển qua JSP
            request.setAttribute("feedback", feedback);

            // Chuyển tiếp tới trang JSP
            request.getRequestDispatcher("/marketing/FeedbackDetail.jsp").forward(request, response);
        } else {
            // Nếu feedbackId không hợp lệ, chuyển hướng về trang lỗi hoặc danh sách phản hồi
            response.sendRedirect("errorPage.jsp");
        }
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
