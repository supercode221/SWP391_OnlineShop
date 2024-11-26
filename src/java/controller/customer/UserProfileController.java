package controller.customer;

import dal.DAO;
import dal.codebase.UserDAO;
import entity.codebaseNew.Address;
import entity.codebaseOld.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet(name = "UserProfileController", urlPatterns = {"/userprofilecontroller"})
public class UserProfileController extends HttpServlet {

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
        
        // Lấy session hiện tại
        HttpSession session = request.getSession();
        
        // Kiểm tra xem người dùng đã đăng nhập hay chưa bằng cách kiểm tra thuộc tính "uid" trong session
        Integer userID = (Integer) session.getAttribute("uid");
        
        if (userID == null) {
            // Nếu chưa đăng nhập, chuyển hướng người dùng về trang đăng nhập
            response.sendRedirect("/freezegroup2/customer/Login.jsp");
            return;
        }
        
        // Sử dụng DAO để lấy thông tin người dùng dựa trên userID từ session
        UserDAO dao = new UserDAO();
        User user = dao.getUserByUserID(userID);
        
        if (user == null) {
            // Nếu không tìm thấy người dùng, chuyển hướng hoặc hiển thị thông báo lỗi
            response.sendRedirect("errorPage.jsp");
            return;
        }
        
        List<Address> addresses = new DAO().getAddressesByUserId(userID);
        
        // Đặt thông tin người dùng vào request để hiển thị trên trang JSP
        request.setAttribute("user", user);
        request.setAttribute("addresses", addresses);
        
        // Chuyển tiếp request tới trang JSP để hiển thị thông tin người dùng
        request.getRequestDispatcher("customer/UserProfile.jsp").forward(request, response);
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
        return "Short description of UserProfileController";
    }

}
