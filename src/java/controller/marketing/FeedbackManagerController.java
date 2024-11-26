package controller.marketing;

import dal.codebase.FeedbackDAO;
import entity.codebaseOld.Feedback;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="FeedbackServlet", urlPatterns={"/feedbackservlet"})
public class FeedbackManagerController extends HttpServlet { // Renamed class to FeedbackManagerController
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() {
        feedbackDAO = new FeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("./marketing/feedbacks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int offset = Integer.parseInt(request.getParameter("offset"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        String statusFilter = request.getParameter("status");
        String productFilter = request.getParameter("product");
        String starFilter = request.getParameter("star");
        String searchQuery = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        List<Feedback> feedbackList = feedbackDAO.getFilteredFeedbacks(offset, limit, statusFilter, productFilter, starFilter, searchQuery, sortBy, sortOrder);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(feedbackList);
        response.getWriter().write(json);
    }
}
