package controller.guest;

import dal.DAO;
import dal.codebase.sliderDAO;
import entity.HoangAnhCustomEntity;
import entity.codebaseOld.Product;
import entity.codebaseOld.Slider;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeController", urlPatterns = {"/homecontroller"})
public class HomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAO dao = new DAO();
        sliderDAO sDAO = new sliderDAO();
        List<HoangAnhCustomEntity.ProductListDTO> pList;
        int sizeHotProduct = dao.getHotProducts().size();
        int sizeNewProduct = dao.getHomepageProductInformation().size();
        if (sizeHotProduct >= 8) {
            pList = dao.getHotProducts().subList(0, 8);
        } else {
            pList = dao.getHotProducts().subList(0, sizeHotProduct);
        }
        List<HoangAnhCustomEntity.ProductListDTO> latestPList;
        if (sizeNewProduct >= 8) {
            latestPList = dao.getHomepageProductInformation().subList(0, 8);
        } else {
            latestPList = dao.getHomepageProductInformation().subList(0, sizeNewProduct);
        }

        List<Slider> sList = sDAO.getAllActiveSlider();

        request.setAttribute("listP", pList);
        request.setAttribute("listS", sList);
        request.setAttribute("latest", latestPList);

        request.getRequestDispatcher("public/HomePage.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
