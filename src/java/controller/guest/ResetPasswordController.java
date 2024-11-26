/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import codebase.EmailSender.Email;
import dal.codebase.UserDAO;
import codebase.hashFunction.SHA256_Encryptor;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "ResetPassword", urlPatterns = {"/resetpassword"})
public class ResetPasswordController extends HttpServlet {

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
            out.println("<title>Servlet ResetPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPassword at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            if (email != null && uDAO.isValidEmail(email)) {
                request.setAttribute("otpConfirm", "confirm");
                request.setAttribute("email", email);
                int otp = otpGenerater();
                Email.sendEmail(email, "The Freeze Reset Password",
                        "You have a reset password request. Do not share this OTP with anyone! Your OTP is: " + otp);
                session.setAttribute("otp", otp);
                session.setAttribute("email", email);
                request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "Invalid Email!");
                request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
            }
        } else if ("otpVerify".equals(action)) {

            String otpInput = request.getParameter("otp");
            Integer sessionOtp = (Integer) session.getAttribute("otp");
            String sessionEmail = (String) session.getAttribute("email");
            Long otpTime = (Long) session.getAttribute("otpTime");

            long currentTime = System.currentTimeMillis();
            if (otpTime != null && (currentTime - otpTime) > 300000) {
                session.invalidate();
                request.setAttribute("msg", "OTP expired! Please request a new OTP.");
                request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
                return;
            }
            Integer otpAttempts = (Integer) session.getAttribute("otpAttempts");
            if (otpAttempts == null) {
                otpAttempts = 0;
            }

            if (otpAttempts >= 3) {
                session.invalidate();
                request.removeAttribute("action");
                request.setAttribute("msg", "OTP verify failed!");
                request.getRequestDispatcher("public/Login.jsp").forward(request, response);
                return;
            }

            if (otpInput != null && sessionOtp != null && sessionEmail != null) {
                try {
                    int otpUserInput = Integer.parseInt(otpInput);
                    if (sessionOtp == otpUserInput) {
                        session.removeAttribute("otpAttempts");
                        request.removeAttribute("otpConfirm");
                        session.removeAttribute("otpConfirm");
                        session.removeAttribute("action");
                        request.setAttribute("action", "otpVerified");
                        request.setAttribute("email", sessionEmail);

                        request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
                    } else {
                        otpAttempts++;
                        session.setAttribute("otpConfirm", "confirm");
                        session.setAttribute("otpAttempts", otpAttempts);
                        request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    otpAttempts++;
                    session.setAttribute("otpAttempts", otpAttempts);
                    session.setAttribute("otpConfirm", "confirm");
                    request.setAttribute("msg", "Invalid OTP format! You have " + (3 - otpAttempts) + " attempts left.");
                    request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "Session expired. Please try again.");
                request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
            }

        } else if ("final".equals(action) && "final".equals(request.getParameter("final"))) {
            String newPass = request.getParameter("newpass");
            String reNewPass = request.getParameter("re-newpass");
            String sessionEmail = (String) session.getAttribute("email"); // Retrieve Email from Session

            if (newPass == null || reNewPass == null || sessionEmail == null) {
                request.setAttribute("msg", "All fields are required!");
                request.setAttribute("email", sessionEmail); // Retain email in the form
                request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
                return;
            }

            if (!newPass.equals(reNewPass)) {
                request.setAttribute("msg", "Passwords do not match!");
                request.setAttribute("action", "otpVerified"); // Retain email in the form
                request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
                return;
            }

            try {
                String encryptPass = SHA256_Encryptor.sha256Hash(newPass);
                uDAO.updatePassword(sessionEmail, encryptPass);
                session.invalidate();
                request.setAttribute("SuccessMsg", "Password updated successfully! Please log in.");
                request.getRequestDispatcher("public/Login.jsp").forward(request, response); // Ensure "login.jsp" is correct
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", "An error occurred while updating the password. Please try again.");
                request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("msg", "Invalid request. Please try again.");
            request.getRequestDispatcher("public/ResetPassword.jsp").forward(request, response);
        }
    }

    private int otpGenerater() {
        return 100000 + new Random().nextInt(900000); // Generates a number between 100000 and 999999
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
