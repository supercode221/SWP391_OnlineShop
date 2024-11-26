package controller.marketing;

import dal.codebase.sliderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteSlider", urlPatterns = {"/deleteslider"})
public class DeleteSliderManagerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int sliderId = Integer.parseInt(request.getParameter("id"));
            sliderDAO sliderDAO = new sliderDAO();

            boolean isDeleted = sliderDAO.deleteSliderById(sliderId);

            if (isDeleted) {
                request.setAttribute("message", "Slider deleted successfully.");
            } else {
                request.setAttribute("error", "Failed to delete slider.");
            }

            // Redirect to the slider list page after deletion
            response.sendRedirect("sliderlist");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid slider ID.");
            response.sendRedirect("sliderlist");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles deletion of a slider.";
    }
}
