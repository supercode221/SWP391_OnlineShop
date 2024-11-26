/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import dal.RegisterUserDAO;
import dal.codebase.UserDAO;
import static codebase.hashFunction.SHA256_Encryptor.sha256Hash;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        request.getRequestDispatcher("public/Register.jsp").forward(request, response);
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
        UserDAO uDAO = new UserDAO();
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String decodePass = request.getParameter("pass");
        String encodePass = sha256Hash(decodePass);

        RegisterUserDAO regisDAO = new RegisterUserDAO();

        if (!uDAO.isValidEmail(email)) {
            regisDAO.register(fname, lname, email, encodePass);
            request.setAttribute("SuccessMsg", "Register successfully!");
            request.getRequestDispatcher("public/Login.jsp").forward(request, response);
        } else {
            request.setAttribute("msg", "Your email is already registered, please try another email!");
            request.getRequestDispatcher("public/Register.jsp").forward(request, response);
        }
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

    public static void main(String[] args) {
        RegisterUserDAO regisDAO = new RegisterUserDAO();
        regisDAO.register("CuuBao", "Hoang", "marketing1@gmail.com", sha256Hash("1"));
//        regisDAO.register("TuanThanh", "Nguyen", "sale@gmail.com", sha256Hash("1"));
//        regisDAO.register("NhatMinh", "Nguyen", "salemanager@gmail.com", sha256Hash("1"));
//        regisDAO.register("DucHai", "Dang", "marketing@gmail.com", sha256Hash("1"));
//        regisDAO.register("HoangAnh", "Do", "marketingmanager@gmail.com", sha256Hash("1"));
//        regisDAO.register("TuanThanh", "Nguyen", "user@gmail.com", sha256Hash("1"));
//        regisDAO.register("NhatMinh", "Nguyen", "shipper@gmail.com", sha256Hash("1"));
    }
}
