/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "AddNewPost", urlPatterns = {"/addnewpost"})
public class AddNewPostManagerController extends HttpServlet {

    BlogDAO bDAO = new BlogDAO();
    HttpSession session;

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
        request.setAttribute("statusList", bDAO.getAllStatus());
        request.setAttribute("blogCategoryList", bDAO.getAllBlogCate());
        request.setAttribute("blogId", bDAO.getIdForAddNew());
        request.getRequestDispatcher("marketing/addpost.jsp").forward(request, response);
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

        session = request.getSession();
        int blogId = bDAO.getIdForAddNew();

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

        int cateId = jsonData.getInt("cate");
        String status = jsonData.getString("status");

        String mainTitle = jsonData.getString("mainTitle");
        String thumbnail = jsonData.getString("thumbnail");
        String mainContent = jsonData.getString("mainContent");

        String buildPathThumbnail = getServletContext().getRealPath("asset\\Image\\BlogImage\\thumbnail");
        String buildPathSubImage = getServletContext().getRealPath("asset\\Image\\BlogImage\\subImage");
        String realPath = getServletContext().getRealPath("");
        String modifiedPath = realPath.replace("\\build", "");

        String thumbnailPath = modifiedPath + "/asset/Image/BlogImage/thumbnail";
        String subImagePath = modifiedPath + "/asset/Image/BlogImage/subImage";

        if (bDAO.addNewBlogMainInformation(blogId, cateId, (int) session.getAttribute("uid"), mainTitle, mainContent, status)) {
            this.log(Level.INFO, "Add blog successfully!", null);
        } else {
            this.log(Level.WARNING, "Add blog failed!", null);
        }

        if (bDAO.addNewSubMedia(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, blogId, "thumbnail", thumbnail, "")) {
            this.log(Level.INFO, "Add main thumbnail for blog: " + blogId + " successfully!", null);
        } else {
            this.log(Level.WARNING, "Add main thumbnail for blog: " + blogId + " failed!", null);
        }

        String subTitle1 = jsonData.getString("subTitle1");
        String subImage1 = jsonData.getString("subImage1");
        String content11 = jsonData.getString("content11");

        if (subTitle1 != null && !subTitle1.isBlank()) {
            if (bDAO.isValidSubTitle("subTitle1", blogId)) {
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
        }

        String subTitle2 = jsonData.getString("subTitle2");
        String subImage2 = jsonData.getString("subImage2");
        String content2 = jsonData.getString("content2");

        if (bDAO.isValidSubTitle("subTitle1", blogId)) {
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
        }

        String subTitle3 = jsonData.getString("subTitle3");
        String subImage3 = jsonData.getString("subImage3");
        String content3 = jsonData.getString("content3");

        if (bDAO.isValidSubTitle("subTitle2", blogId)) {
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