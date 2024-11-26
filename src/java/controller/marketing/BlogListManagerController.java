/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dal.BlogDAO;
import entity.ThanhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "BlogListManager", urlPatterns = {"/bloglistmanager"})
public class BlogListManagerController extends HttpServlet {

    BlogDAO bDAO = new BlogDAO();
    private static final int ITEM_PER_PAGE = 4;
    HttpSession session;

    protected void doGetProcess(HttpServletRequest request, HttpServletResponse response, List<ThanhCustomEntity.blog> l)
            throws ServletException, IOException {
        int page = 1;
        boolean canLoadMore = false;
        int blogCount = l.size();
        int fromInPage = ITEM_PER_PAGE * (page - 1);
        int toInPage = ITEM_PER_PAGE * page;
        if (blogCount > toInPage) {
            canLoadMore = true;
        } else {
            toInPage = blogCount;
        }

        List<ThanhCustomEntity.blog> mainBList = l.subList(fromInPage, toInPage);

        if (request.getParameter("searchblog") != null) {
            request.setAttribute("searchBlog", request.getParameter("searchblog"));
        }

        request.setAttribute("bloglist", mainBList);
        request.setAttribute("allList", new Gson().toJson(l));
        request.setAttribute("statuslist", new BlogDAO().getAllStatus());
        request.setAttribute("page", page);
        request.setAttribute("categorylist", bDAO.getAllBlogCate());
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("marketing/bloglist.jsp").forward(request, response);
    }

    protected void doPostProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action != null){
            switch (action) {
                case "load-more" ->
                    processLoadMoreAction(request, response);
                case "search" ->
                    doGetProcess(request, response, getListBlog(request));
                case "filterBlog" ->
                    doGetProcess(request, response, getListBlog(request));
                case "saveStatus" ->
                    processSaveStatusAction(request, response);
            }
        } else {
            this.log(Level.WARNING, "Action is null", new NullPointerException());
        }
    }

    protected void processSaveStatusAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        int postId = Integer.parseInt(request.getParameter("postId"));

        if (bDAO.savePostStatus(postId, status)) {
            this.log(Level.INFO, "Change status for Post ID: " + postId + " to " + status + " successfully!", null);
        } else {
            this.log(Level.INFO, "Change status for Post ID: " + postId + " to " + status + " failed!", null);
        }
    }

    protected void processLoadMoreAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String allPostListParam = request.getParameter("allList");
            Gson gSon = new Gson();
            List<ThanhCustomEntity.blog> blogList = gSon.fromJson(allPostListParam, new TypeToken<List<ThanhCustomEntity.blog>>() {
            }.getType());
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

            List<ThanhCustomEntity.blog> mainBList = blogList.subList(fromInPage, toInPage);

            request.setAttribute("bloglist", mainBList);
            request.setAttribute("allList", new Gson().toJson(blogList));
            request.setAttribute("statuslist", new BlogDAO().getAllStatus());
            request.setAttribute("page", page);
            request.setAttribute("categorylist", bDAO.getAllBlogCate());
            request.setAttribute("canLoadMore", canLoadMore);
            request.getRequestDispatcher("marketing/loadmorebloglist.jsp").forward(request, response);
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to paging", e);
        }
    }

    private List<ThanhCustomEntity.blog> getListBlog(HttpServletRequest request) {
        String type = request.getParameter("type");
        session = request.getSession();
        int uid = (int) session.getAttribute("uid");
        int roleId = (int) session.getAttribute("roleId");
        switch (type) {
            case "all" -> {
                return bDAO.getAllManagedBlog(roleId, uid);
            }
            case "search" -> {
                String searchInput = request.getParameter("searchblog");
                return bDAO.getAllSearchedBlogByTitle(searchInput);
            }
            case "filter" -> {
                int cateId = Integer.parseInt(request.getParameter("cateId"));
                return bDAO.getAllManagerBlogByCateId(cateId);
            }
            case "filterStatus" -> {
                String status = request.getParameter("status");
                return bDAO.getAllBlogByStatus(status);
            }
        }

        return null;
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
        doGetProcess(request, response, getListBlog(request));
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
        doPostProcess(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private void log(Level level, String msg, Throwable e) {
        this.logger.log(level, msg, e);
    }// </editor-fold>
}
