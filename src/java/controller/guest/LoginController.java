/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import dal.MyDAO;
import dal.LoginUserDAO;
import entity.ThanhCustomEntity;
import static codebase.hashFunction.SHA256_Encryptor.sha256Hash;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
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
        // check user session
        // if user exist -> home
        // else
        HttpSession session = request.getSession();

        if (!(session.getAttribute("uid") == null)) {
            response.sendRedirect("homecontroller");
        } else {
            request.getRequestDispatcher("public/Login.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        LoginUserDAO luDAO = new LoginUserDAO();
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String encodePass = sha256Hash(pass);
        String rem = request.getParameter("rem");
        String msg = null;
        ThanhCustomEntity.LoginUserDTO user = new ThanhCustomEntity.LoginUserDTO();
        user = luDAO.getLoginUser(email, encodePass);

        if (user == null) {
            msg = "Incorrect email or password";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("public/Login.jsp").forward(request, response);
        } else {
            if ("on".equals(rem)) {
                Cookie uEmail = new Cookie("uEmail", email);
                Cookie uPass = new Cookie("uPass", encodePass);
                Cookie uRem = new Cookie("uRem", rem);
                int aWeek = 60 * 60 * 24 * 7; // 1 week
                uEmail.setMaxAge(aWeek);
                uPass.setMaxAge(aWeek);
                uRem.setMaxAge(aWeek);
                response.addCookie(uEmail);
                response.addCookie(uPass);
                response.addCookie(uRem);
            }
            if ("active".equalsIgnoreCase(user.getUserStatus())) {
                MyDAO myDAO = new MyDAO();
                int roleId = user.getUserRollID();
                int productInCart = myDAO.getProductCountInCart(user.getUserID());
                session.setAttribute("uid", user.getUserID());
                session.setAttribute("username", luDAO.getUsernameByUid(user.getUserID()));
                session.setAttribute("roleId", roleId);
                session.setAttribute("productInCart", productInCart);
                session.setMaxInactiveInterval(86400);
                switch (roleId) {
                    case 1 -> {
                        response.sendRedirect("homecontroller");
                    }
                    case 2 -> {
                        response.sendRedirect("admindashboard");
                    }
                    case 3 -> {
                        response.sendRedirect("customers");
                    }
                    case 4 -> {
                        response.sendRedirect("ordermanager?type=all");
                    }
                    case 5 -> {
                        response.sendRedirect("marketingdashboard");
                    }
                    case 6 -> {
                        response.sendRedirect("admindashboard");
                    }
                    case 7 -> {
                        response.sendRedirect("ordermanager?type=all");
                    }
                }
            } else {
                msg = "Your account has been banned, contact admin for more infor";
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("public/Login.jsp").forward(request, response);
            }
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

}
