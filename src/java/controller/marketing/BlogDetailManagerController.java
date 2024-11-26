/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.BlogDAO;
import entity.ThanhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "BlogDetailManager", urlPatterns = {"/blogdetailmanager"})
public class BlogDetailManagerController extends HttpServlet {

    BlogDAO bDAO = new BlogDAO();
    HttpSession session;

    protected void doGetProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        int postId = Integer.parseInt(request.getParameter("postId"));
        ThanhCustomEntity.blog blog = bDAO.getBlogInformationById(postId);

        if ((int) session.getAttribute("uid") == blog.getAuthor().getId()) {
            List<ThanhCustomEntity.BlogAttribute> blogAttrList = bDAO.getBlogAttributeListById(postId);
            List<ThanhCustomEntity.BlogSubMedia> blogSubMediaList = bDAO.getBlogSubMediaById(postId);
            List<ThanhCustomEntity.BlogCategory> blogCategoryList = bDAO.getAllBlogCate();
            List<String> statusList = bDAO.getAllStatus();

            request.setAttribute("blog", blog);
            request.setAttribute("blogAttrListSize", blogAttrList.size());
            request.setAttribute("blogAttrList", blogAttrList);
            request.setAttribute("blogSubMediaList", blogSubMediaList);
            request.setAttribute("blogCategoryList", blogCategoryList);
            request.setAttribute("statusList", statusList);
            request.getRequestDispatcher("marketing/blogdetail.jsp").forward(request, response);
        } else if ((int) session.getAttribute("roleId") == 2 || (int) session.getAttribute("roleId") == 5) {
            List<ThanhCustomEntity.BlogAttribute> blogAttrList = bDAO.getBlogAttributeListById(postId);
            List<ThanhCustomEntity.BlogSubMedia> blogSubMediaList = bDAO.getBlogSubMediaById(postId);
            List<ThanhCustomEntity.BlogCategory> blogCategoryList = bDAO.getAllBlogCate();
            List<String> statusList = bDAO.getAllStatus();

            request.setAttribute("blog", blog);
            request.setAttribute("blogAttrListSize", blogAttrList.size());
            request.setAttribute("blogAttrList", blogAttrList);
            request.setAttribute("blogSubMediaList", blogSubMediaList);
            request.setAttribute("blogCategoryList", blogCategoryList);
            request.setAttribute("statusList", statusList);
            request.getRequestDispatcher("marketing/blogdetail.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error/401.jsp").forward(request, response);
        }

    }

    protected void doPostProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            this.log(Level.WARNING, "Action is null", null);
        } else {
            switch (action) {
                case "updateCategory" -> {
                    doUpdateCategoryProcess(request, response);
                }
                case "updateStatus" -> {
                    doUpdateStatusProcess(request, response);
                }
                case "saveChanges" -> {
                    doSaveChangesProcess(request, response);
                }
            }
        }
    }

    protected void doUpdateCategoryProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cateId = Integer.parseInt(request.getParameter("cate"));
        int postId = Integer.parseInt(request.getParameter("postId"));

        if (bDAO.savePostCategory(postId, cateId)) {
            this.log(Level.INFO, "Change Category for Post ID: " + postId + " to " + cateId + " successfully!", null);
        } else {
            this.log(Level.INFO, "Change Category for Post ID: " + postId + " to " + cateId + " failed!", null);
        }
    }

    protected void doUpdateStatusProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        int postId = Integer.parseInt(request.getParameter("postId"));

        if (bDAO.savePostStatus(postId, status)) {
            this.log(Level.INFO, "Change status for Post ID: " + postId + " to " + status + " successfully!", null);
        } else {
            this.log(Level.INFO, "Change status for Post ID: " + postId + " to " + status + " failed!", null);
        }
    }

    protected void doSaveChangesProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String buildPathThumbnail = getServletContext().getRealPath("asset\\Image\\BlogImage\\thumbnail");
        String buildPathSubImage = getServletContext().getRealPath("asset\\Image\\BlogImage\\subImage");
        String realPath = getServletContext().getRealPath("");
        String modifiedPath = realPath.replace("\\build", "");

        String thumbnailPath = modifiedPath + "/asset/Image/BlogImage/thumbnail";
        String subImagePath = modifiedPath + "/asset/Image/BlogImage/subImage";

        // Read the request body for JSON data
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        // Parse the JSON object from the request body
        String jsonString = jsonBuffer.toString();
        JSONObject jsonData = new JSONObject(jsonString);

        int blogId = jsonData.getInt("blogId");
        String mainTitle = jsonData.getString("mainTitle");
        String thumbnail = jsonData.getString("thumbnail");
        String mainContent = jsonData.getString("mainContent");

        if (bDAO.updateBlogMainInformation(mainTitle, mainContent, blogId)) {
            this.log(Level.INFO, "Update blog main information for blog: " + blogId + " successfully!", null);
        } else {
            this.log(Level.WARNING, "Update blog main information for blog: " + blogId + " failed!", null);
        }

        if (bDAO.updateBlogMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, thumbnail, "thumbnail")) {
            this.log(Level.INFO, "Update main thumbnail for blog: " + blogId + " successfully!", null);
        } else {
            this.log(Level.WARNING, "Update main thumbnail for blog: " + blogId + " failed!", null);
        }

        String subTitle1 = jsonData.getString("subTitle1");
        String subImage1 = jsonData.getString("subImage1");
        String content11 = jsonData.getString("content11");

        if (bDAO.isValidSubTitle("subTitle1", blogId)) {
            if (subTitle1 != null && !subTitle1.isBlank()) { // Null check first, then check if not blank
                // Update the subtitle
                if (bDAO.updateBlogSubTitle(blogId, subTitle1, content11, "subTitle1")) {
                    this.log(Level.INFO, "Updated subTitle1 for blog: " + blogId + " successfully!", null);
                } else {
                    this.log(Level.WARNING, "Failed to update subTitle1 for blog: " + blogId, null);
                }

                // Update the sub-image if present
                if (subImage1 != null) {
                    if (bDAO.updateBlogMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, subImage1, "subImage1")) { // Use subImage1, not thumbnail
                        this.log(Level.INFO, "Updated subImage1 for blog: " + blogId + " successfully!", null);
                    } else {
                        this.log(Level.WARNING, "Failed to update subImage1 for blog: " + blogId, null);
                    }
                }
            }
        } else {
            if (bDAO.addNewSubTitle(blogId, "subTitle1", subTitle1, content11)) {
                if (subImage1 != null) {
                    if (bDAO.addNewSubMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, "subImage1", subImage1, "subTitle1")) { // Use subImage1, not thumbnail
                        this.log(Level.INFO, "Add subImage1 for blog: " + blogId + " successfully!", null);
                    } else {
                        this.log(Level.WARNING, "Failed to add subImage1 for blog: " + blogId, null);
                    }
                }
            }
        }

        String subTitle2 = jsonData.getString("subTitle2");
        String subImage2 = jsonData.getString("subImage2");
        String content2 = jsonData.getString("content2");

        if (subTitle2 != null && !subTitle2.isBlank()) {
            if (bDAO.isValidSubTitle("subTitle2", blogId)) {
                // Update subtitle
                if (bDAO.updateBlogSubTitle(blogId, subTitle2, content2, "subTitle2")) {
                    this.log(Level.INFO, "Updated subTitle2 for blog: " + blogId + " successfully!", null);
                } else {
                    this.log(Level.WARNING, "Failed to update subTitle2 for blog: " + blogId, null);
                }

                // Update sub-image
                if (subImage2 != null && !subImage2.isBlank()) { // Ensure subImage2 is valid
                    if (bDAO.updateBlogMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, subImage2, "subImage2")) {
                        this.log(Level.INFO, "Updated subImage2 for blog: " + blogId + " successfully!", null);
                    } else {
                        this.log(Level.WARNING, "Failed to update subImage2 for blog: " + blogId, null);
                    }
                }
            } else {
                // Add new subtitle and media
                if (bDAO.addNewSubTitle(blogId, "subTitle2", subTitle2, content2)) {
                    if (subImage2 != null && !subImage2.isBlank()) {
                        if (bDAO.addNewSubMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, "subImage2", subImage2, "subTitle2")) {
                            this.log(Level.INFO, "Added subImage2 for blog: " + blogId + " successfully!", null);
                        } else {
                            this.log(Level.WARNING, "Failed to add subImage2 for blog: " + blogId, null);
                        }
                    }
                }
            }
        } else {
            this.log(Level.WARNING, "subTitle2 is blank or null for blog: " + blogId, null);
        }

        String subTitle3 = jsonData.getString("subTitle3");
        String subImage3 = jsonData.getString("subImage3");
        String content3 = jsonData.getString("content3");

        if (subTitle3 != null && !subTitle3.isBlank()) {
            if (bDAO.isValidSubTitle("subTitle3", blogId)) {
                // Update subtitle
                if (bDAO.updateBlogSubTitle(blogId, subTitle3, content3, "subTitle3")) {
                    this.log(Level.INFO, "Updated subTitle3 for blog: " + blogId + " successfully!", null);
                } else {
                    this.log(Level.WARNING, "Failed to update subTitle3 for blog: " + blogId, null);
                }

                // Update sub-image
                if (subImage3 != null && !subImage3.isBlank()) { // Ensure subImage3 is valid
                    if (bDAO.updateBlogMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, subImage3, "subImage3")) {
                        this.log(Level.INFO, "Updated subImage3 for blog: " + blogId + " successfully!", null);
                    } else {
                        this.log(Level.WARNING, "Failed to update subImage3 for blog: " + blogId, null);
                    }
                }
            } else {
                // Add new subtitle and media
                if (bDAO.addNewSubTitle(blogId, "subTitle3", subTitle3, content3)) {
                    if (subImage2 != null && !subImage2.isBlank()) {
                        if (bDAO.addNewSubMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, "subImage3", subImage3, "subTitle3")) {
                            this.log(Level.INFO, "Added subImage3 for blog: " + blogId + " successfully!", null);
                        } else {
                            this.log(Level.WARNING, "Failed to add subImage3 for blog: " + blogId, null);
                        }
                    }
                }
            }
        } else {
            this.log(Level.WARNING, "subTitle3 is blank or null for blog: " + blogId, null);
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
//        String test = getServletContext().getRealPath("");
//        this.log(Level.INFO, test, null);
        doGetProcess(request, response);
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
