/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import dal.BlogDAO;
import entity.ThanhCustomEntity;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "BlogDetail", urlPatterns = {"/blogdetail"})
public class BlogDetailController extends HttpServlet {

    BlogDAO bDAO = new BlogDAO();

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
        int blogId = Integer.parseInt(request.getParameter("blogId"));

        if (bDAO.isValidBlog(blogId)) {

            ThanhCustomEntity.blog blog = bDAO.getBlogInformationById(blogId);

            if (blog.getStatus().equalsIgnoreCase("Active")) {
                List<ThanhCustomEntity.BlogAttribute> blogAttrList = bDAO.getBlogAttributeListById(blogId);
                List<ThanhCustomEntity.BlogSubMedia> blogSubMediaList = bDAO.getBlogSubMediaById(blogId);

                request.setAttribute("blog", blog);
                request.setAttribute("blogAttrList", blogAttrList);
                request.setAttribute("blogSubMedList", blogSubMediaList);
                request.setAttribute("similar", bDAO.getAllSimilarBlog(blog.getCate().getId()));
                request.getRequestDispatcher("public/BlogDetail.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("error/404.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("error/404.jsp").forward(request, response);
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
    }// </editor-fold>

}
