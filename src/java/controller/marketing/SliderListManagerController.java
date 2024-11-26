package controller.marketing;

import dal.codebase.sliderDAO;
import entity.codebaseOld.Slider;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SliderList", urlPatterns = {"/sliderlist"})
public class SliderListManagerController extends HttpServlet {

    @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sliderDAO sliderDAO = new sliderDAO();

        String status = request.getParameter("status");
        String search = request.getParameter("search");
        List<Slider> sliders;

        // Apply search filter
        if (search != null && !search.isEmpty()) {
            sliders = sliderDAO.searchSliders(search);
        } else if (status != null && !status.isEmpty()) {
            // Apply status filter
            sliders = sliderDAO.getSlidersByStatus(status);
        } else {
            // Get all sliders if no filter is provided
            sliders = sliderDAO.getAllSliders();
        }

        // Set request attributes for use in the JSP
        request.setAttribute("slider", sliders);
        request.setAttribute("search", search);
        request.setAttribute("status", status); // Add the status attribute for use in the JSP
        request.getRequestDispatcher("/marketing/SliderList.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
