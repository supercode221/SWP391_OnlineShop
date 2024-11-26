/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.guest;

import dal.BlogDAO;
import entity.ThanhCustomEntity;
import java.io.IOException;
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
@WebServlet(name = "BlogList", urlPatterns = {"/bloglist"})
public class BlogListController extends HttpServlet {

    private static final int ITEM_PER_PAGE = 4;

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
        BlogDAO bDAO = new BlogDAO();
        List<ThanhCustomEntity.blog> blogList = bDAO.getAllBlog();
        List<ThanhCustomEntity.BlogCategory> blogCateList = bDAO.getAllBlogCate();
        int page = 1;
        boolean canLoadMore = false;

        int blogCount = blogList.size();
        int fromInPage = ITEM_PER_PAGE * (page - 1);
        int toInPage = ITEM_PER_PAGE * page;
        if (blogCount > toInPage) {
            canLoadMore = true;
        } else {
            toInPage = blogCount;
        }

        List<ThanhCustomEntity.blog> mainBlogList = blogList.subList(fromInPage, toInPage);

        request.setAttribute("blog", mainBlogList);
        request.setAttribute("blogCate", blogCateList);
        request.setAttribute("page", page);
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("public/BlogList.jsp").forward(request, response);
    }

    protected void doLoadMore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO bDAO = new BlogDAO();
        List<ThanhCustomEntity.blog> blogList = bDAO.getAllBlog();
        int page = Integer.parseInt(request.getParameter("page"));
        boolean canLoadMore;

        int blogCount = blogList.size();
        int fromInPage = ITEM_PER_PAGE * (page - 1);
        int toInPage = ITEM_PER_PAGE * page;
        if (blogCount > toInPage) {
            canLoadMore = true;
        } else {
            toInPage = blogCount;
            canLoadMore = false;
        }

        List<ThanhCustomEntity.blog> mainBlogList = blogList.subList(fromInPage, toInPage);

        request.setAttribute("blog", mainBlogList);
        request.setAttribute("page", page);
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("customer/MoreItemForBlogList.jsp").forward(request, response);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO bDAO = new BlogDAO();
        String searchInput = request.getParameter("searchblog");

        if (searchInput.isBlank() || searchInput.isEmpty()) {
            request.getRequestDispatcher("bloglist").forward(request, response);
        } else {
            request.setAttribute("blogCate", bDAO.getAllBlogCate());
            request.setAttribute("blog", bDAO.getAllSearchBlog(searchInput));
            request.setAttribute("searchBlogInput", searchInput);
            request.getRequestDispatcher("public/BlogList.jsp").forward(request, response);
        }
    }

    protected void doFilterBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO bDAO = new BlogDAO();
        int cateId = Integer.parseInt(request.getParameter("cateId"));
        
        request.setAttribute("blog", bDAO.getAllBlogByCateId(cateId));
        request.setAttribute("blogCate", bDAO.getAllBlogCate());
        request.getRequestDispatcher("public/BlogList.jsp").forward(request, response);
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
        String action = request.getParameter("action");

        switch (action) {
            case "load-more" ->
                doLoadMore(request, response);
            case "searchBlog" ->
                doSearch(request, response);
            case "filterBlog" ->
                doFilterBlog(request, response);
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
