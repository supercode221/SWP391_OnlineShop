/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;


import dal.codebase.AddressDAO;
import entity.codebaseNew.Address;
import entity.codebaseNew.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet("/saveAddress")
public class AddressController extends HttpServlet {

    private AddressDAO addressDAO;

    @Override
    public void init() throws ServletException {
        addressDAO = new AddressDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("uid") != null) {
            int userId = (int) session.getAttribute("uid");
//            String addressLine = request.getParameter("addressLine");
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String details = request.getParameter("details");
            String note = request.getParameter("note");

            Address address = new Address();
            User user = new User();
            user.setId(userId);
            address.setUser(user);
//            address.setDetails(addressLine);
            address.setTinh_thanhpho(city);
            address.setHuyen_quan(district);
            address.setXa_phuong(ward);
            address.setNote(note);
            address.setDetails(details);

            boolean isSaved = addressDAO.addAddress(address);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":" + isSaved + "}");
        }
    }
}
