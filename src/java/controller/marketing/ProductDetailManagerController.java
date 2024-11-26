/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.HoangAnhCustomEntityDAO;
import dal.ProductListManagerDAO;
import entity.HoangAnhCustomEntity;
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
@WebServlet(name = "ProductDetailManager", urlPatterns = {"/productdetailmanager"})
public class ProductDetailManagerController extends HttpServlet {

    private final ProductListManagerDAO pDAO = new ProductListManagerDAO();
    private final HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();

    private HttpSession session;

    protected void doGetProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        int uid = (int) session.getAttribute("uid");
        int roleId = (int) session.getAttribute("roleId");

        if (request.getParameter("pid") == null || request.getParameter("pid").isBlank()) {
            this.log(Level.WARNING, "Pid is null, cannot fetch product for product detail manager!", new NullPointerException());
            request.getRequestDispatcher("error/404.jsp").forward(request, response);

        } else {
            int pid = Integer.parseInt(request.getParameter("pid"));
            HoangAnhCustomEntity.ProductListDTO productDetail = pDAO.getProductDetailInformationManageById(pid);
            List<ThanhCustomEntity.ProductImage> imageList = pDAO.getProductImageById(pid);
            List<ThanhCustomEntity.QuantityTracker> attributesList = pDAO.getProductAttributeById(pid);

//            this.log(Level.INFO, "Fetch data for prodct detail id: " + pid + " success!", null);
            request.setAttribute("product", productDetail);
            request.setAttribute("tagList", ceDAO.getAllTags());
            request.setAttribute("cateList", ceDAO.getAllCategories());
            request.setAttribute("statusList", pDAO.getAllStatus());
            request.setAttribute("colorList", pDAO.getAllColor());
            request.setAttribute("sizeList", pDAO.getAllSize());
            request.setAttribute("imageList", imageList);
            request.setAttribute("attributesList", attributesList);
            request.getRequestDispatcher("marketing/ProductDetail.jsp").forward(request, response);
        }
    }

    protected void doPostProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "saveOverallInformation" -> {
                doSaveOverallInformation(request, response);
            }
            case "deleteTag" -> {
                doDeleteTag(request, response);
            }
            case "addTag" -> {
                doAddTag(request, response);
            }
            case "saveAttribute" -> {
                doSaveAttribute(request, response);
            }
            case "deleteAttribute" -> {
                doDeleteAdttribute(request, response);
            }
            case "saveThumbnail" -> {
                doSaveThumbnail(request, response);
            }
            case "saveSubImage" -> {
                doSaveSubImage(request, response);
            }
            case "deleteSubImage" -> {
                doDeleteSubImage(request, response);
            }
            case "addSubImage" -> {
                doAddSubImage(request, response);
            }
            case "addAttr" -> {
                doAddAttribute(request, response);
            }
            default ->
                throw new AssertionError();
        }
    }

    protected void doSaveOverallInformation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        String productName = request.getParameter("productName");
        String productDes = request.getParameter("productDes");
        double price = Double.parseDouble(request.getParameter("productTruePrice"));
        int cateId = Integer.parseInt(request.getParameter("cateId"));
        String status = request.getParameter("status");

        if (pDAO.updateOverallInfomation(pid, productName, productDes, price, cateId, status)) {
            this.log(Level.INFO, "Save overall information for pid: " + pid + " success!", null);
        } else {
            this.log(Level.WARNING, "Save overall information for pid: " + pid + " failed!", null);
        }
    }

    protected void doSaveThumbnail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buildPathThumbnail = getServletContext().getRealPath("asset\\Image\\ProductImage\\Thumbnail");
        String buildPathSubImage = getServletContext().getRealPath("asset\\Image\\ProductImage\\SubImage");
        String realPath = getServletContext().getRealPath("");
        String modifiedPath = realPath.replace("\\build", "");

        String thumbnailPath = modifiedPath + "/asset/Image/ProductImage/Thumbnail";
        String subImagePath = modifiedPath + "/asset/Image/ProductImage/SubImage";

        int pid = Integer.parseInt(request.getParameter("pid"));
        String thumbnail = request.getParameter("thumbnail");

        if (pDAO.updateProductImage(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, pid, thumbnail, "thumbnail", 0)) {
            this.log(Level.INFO, "Success to update product media " + "thumbnail" + " for ProductID: " + pid + " !", null);
        } else {
            this.log(Level.WARNING, "Failed to update product media " + "thumbnail" + " for ProductID: " + pid + " !", null);
        }

    }

    protected void doDeleteSubImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int subImageId = Integer.parseInt(request.getParameter("subImageId"));

        if (pDAO.deleteSubImage(pid, subImageId)) {
            this.log(Level.INFO, "Success to delete sub-image for ProductID: " + pid + " !", null);
        } else {
            this.log(Level.WARNING, "Failed to delete sub-image for ProductID: " + pid + " !", null);
        }
    }

    protected void doSaveSubImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buildPathThumbnail = getServletContext().getRealPath("asset\\Image\\ProductImage\\Thumbnail");
        String buildPathSubImage = getServletContext().getRealPath("asset\\Image\\ProductImage\\SubImage");
        String realPath = getServletContext().getRealPath("");
        String modifiedPath = realPath.replace("\\build", "");

        String thumbnailPath = modifiedPath + "/asset/Image/ProductImage/Thumbnail";
        String subImagePath = modifiedPath + "/asset/Image/ProductImage/SubImage";

        int pid = Integer.parseInt(request.getParameter("pid"));
        int subImageId = Integer.parseInt(request.getParameter("subImageId"));
        String subImage = request.getParameter("subImage");

        if (pDAO.updateProductImage(buildPathSubImage, buildPathThumbnail, thumbnailPath, subImagePath, pid, subImage, "subImage", subImageId)) {
            this.log(Level.INFO, "Success to update product media " + "sub-image" + " for ProductID: " + pid + " !", null);
        } else {
            this.log(Level.WARNING, "Failed to update product media " + "sub-image" + " for ProductID: " + pid + " !", null);
        }
    }

    protected void doAddSubImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buildPathSubImage = getServletContext().getRealPath("asset\\Image\\ProductImage\\SubImage");
        String realPath = getServletContext().getRealPath("");
        String modifiedPath = realPath.replace("\\build", "");

        String subImagePath = modifiedPath + "/asset/Image/ProductImage/SubImage";

        int pid = Integer.parseInt(request.getParameter("pid"));
        String addImage = request.getParameter("addImage");

        if (pDAO.addProductSubImage(buildPathSubImage, subImagePath, pid, addImage)) {
            this.log(Level.INFO, "Success to add product media " + "sub-image" + " for ProductID: " + pid + " !", null);
        } else {
            this.log(Level.WARNING, "Failed to add product media " + "sub-image" + " for ProductID: " + pid + " !", null);
        }
    }

    protected void doAddTag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int tid = Integer.parseInt(request.getParameter("tagId"));
        if (pDAO.addTagForProduct(pid, tid)) {
            this.log(Level.INFO, "Add tid: " + tid + " for pid: " + pid + " success!", null);
        } else {
            this.log(Level.WARNING, "Add tid: " + tid + " for pid: " + pid + " failed!", null);
        }
    }

    protected void doDeleteTag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int tid = Integer.parseInt(request.getParameter("tagId"));
        if (pDAO.deleteTagForProduct(pid, tid)) {
            this.log(Level.INFO, "Delete tid: " + tid + " for pid: " + pid + " success!", null);
        } else {
            this.log(Level.WARNING, "Delete tid :" + tid + " for pid: " + pid + " failed!", null);
        }
    }

    protected void doSaveAttribute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int sid = Integer.parseInt(request.getParameter("sid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (pDAO.updateAttrForProduct(pid, sid, cid, quantity)) {
            this.log(Level.INFO, "Update attribute for pid: " + pid + " success!", null);
        } else {
            this.log(Level.WARNING, "Update attribute for pid: " + pid + " failed!", null);
        }
    }

    protected void doDeleteAdttribute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int sid = Integer.parseInt(request.getParameter("sid"));
        int cid = Integer.parseInt(request.getParameter("cid"));

        if (pDAO.deleteAttrForProduct(pid, sid, cid)) {
            this.log(Level.INFO, "Delete attribute for pid: " + pid + " success!", null);
        } else {
            this.log(Level.WARNING, "Delete attribute for pid: " + pid + " failed!", null);
        }
    }

    protected void doAddAttribute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        int sid = Integer.parseInt(request.getParameter("sid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (pDAO.addAttrForProduct(pid, sid, cid, quantity)) {
            this.log(Level.INFO, "Add attribute for pid: " + pid + " success!", null);
        } else {
            this.log(Level.WARNING, "Add attribute for pid: " + pid + " failed!", null);
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
