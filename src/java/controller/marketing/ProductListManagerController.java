/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dal.HoangAnhCustomEntityDAO;
import dal.ProductListManagerDAO;
import entity.HoangAnhCustomEntity;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer Aspire 7
 */
@WebServlet(name = "ProductListManagerController", urlPatterns = {"/productlistmanager"})
public class ProductListManagerController extends HttpServlet {

    private static final int ITEM_PER_PAGE = 10;
    static HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();
    static ProductListManagerDAO pDAO = new ProductListManagerDAO();

    protected void doGetProcess(HttpServletRequest request, HttpServletResponse response, List<HoangAnhCustomEntity.ProductListDTO> l)
            throws ServletException, IOException {
        int page = 1;
        boolean canLoadMore = false;
        int productCount = l.size();
        int fromInPage = ITEM_PER_PAGE * (page - 1);
        int toInPage = ITEM_PER_PAGE * page;
        if (productCount > toInPage) {
            canLoadMore = true;
        } else {
            toInPage = productCount;
        }

        List<HoangAnhCustomEntity.ProductListDTO> mainPList = l.subList(fromInPage, toInPage);

        if (request.getParameter("searchProduct") != null) {
            request.setAttribute("searchProduct", request.getParameter("searchProduct"));
        }

        request.setAttribute("productList", mainPList);
        request.setAttribute("tagList", ceDAO.getAllTags());
        request.setAttribute("cateList", ceDAO.getAllCategories());
        request.setAttribute("statusList", pDAO.getAllStatus());
        request.setAttribute("allList", new Gson().toJson(l));
        request.setAttribute("page", page);
        request.setAttribute("canLoadMore", canLoadMore);
        request.getRequestDispatcher("marketing/ProductList.jsp").forward(request, response);
    }

    protected void doPostProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "load-more" -> {
                doLoadMore(request, response);
            }
            case "addnewproduct" -> {
                doAddProductForward(request, response);
            }
            case "add" -> {
                doAddProduct(request, response);
            }
            case "filterProduct" -> {
                doGetProcess(request, response, getProductList(request));
            }
            case "search" -> {
                doGetProcess(request, response, getProductList(request));
            }
            case "saveCateId" -> {
                int pid = Integer.parseInt(request.getParameter("pid"));
                int cateId = Integer.parseInt(request.getParameter("cateId"));

                if (pDAO.updateProductCategory(pid, cateId)) {
                    this.log(Level.INFO, "Update category id for pid " + pid + " to " + cateId + " successfully!", null);
                } else {
                    this.log(Level.INFO, "Update category id for pid " + pid + " to " + cateId + " failed!", null);
                }
            }
            case "saveStatus" -> {
                int pid = Integer.parseInt(request.getParameter("pid"));
                String status = request.getParameter("status");

                if (pDAO.updateProductStatus(pid, status)) {
                    this.log(Level.INFO, "Update status for pid " + pid + " to " + status + " successfully!", null);
                } else {
                    this.log(Level.WARNING, "Update status for pid " + pid + " to " + status + " failed!", null);
                }
            }
            default ->
                throw new AssertionError();
        }
    }

    protected void doAddProductForward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tagList", ceDAO.getAllTags());
        request.setAttribute("cateList", ceDAO.getAllCategories());
        request.setAttribute("statusList", pDAO.getAllStatus());
        request.setAttribute("colorList", pDAO.getAllColor());
        request.setAttribute("sizeList", pDAO.getAllSize());
        request.setAttribute("pid", pDAO.getProductIdToAdd());
        request.getRequestDispatcher("marketing/AddProduct.jsp").forward(request, response);
    }

    protected void doAddProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buildPathThumbnail = getServletContext().getRealPath("asset\\Image\\ProductImage\\Thumbnail");
        String realPath = getServletContext().getRealPath("");
        String modifiedPath = realPath.replace("\\build", "");

        String thumbnailPath = modifiedPath + "/asset/Image/ProductImage/Thumbnail";

        int pid = Integer.parseInt(request.getParameter("pid"));
        int cateId = Integer.parseInt(request.getParameter("cateId"));
        String status = request.getParameter("status");
        String name = request.getParameter("name");
        String des = request.getParameter("des");
        double price = Double.parseDouble(request.getParameter("price"));
        String thumbnail = request.getParameter("thumbnail");

        if (pDAO.addNewProduct(thumbnailPath, buildPathThumbnail, pid, cateId, status, name, des, price, thumbnail)) {
            this.log(Level.INFO, "Add product successfully!", null);
        } else {
            this.log(Level.WARNING, "Add product failed!", null);
        }
    }

    protected void doLoadMore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String allProductListParam = request.getParameter("allList");
            Gson gSon = new Gson();
            List<HoangAnhCustomEntity.ProductListDTO> productList = gSon.fromJson(allProductListParam, new TypeToken<List<HoangAnhCustomEntity.ProductListDTO>>() {
            }.getType());
            int page = Integer.parseInt(request.getParameter("page"));

            boolean canLoadMore = false;
            int productCount = productList.size();
            int fromInPage = ITEM_PER_PAGE * (page - 1);
            int toInPage = ITEM_PER_PAGE * page;
            if (productCount > toInPage) {
                canLoadMore = true;
            } else {
                toInPage = productCount;
            }

            List<HoangAnhCustomEntity.ProductListDTO> mainPList = productList.subList(fromInPage, toInPage);

            request.setAttribute("productList", mainPList);
            request.setAttribute("allList", new Gson().toJson(productList));
            request.setAttribute("cateList", ceDAO.getAllCategories());
            request.setAttribute("statusList", pDAO.getAllStatus());
            request.setAttribute("page", page);
            request.setAttribute("canLoadMore", canLoadMore);
            request.getRequestDispatcher("marketing/loadMoreProductList.jsp").forward(request, response);
        } catch (Exception e) {
            this.log(Level.WARNING, "Failed to paging", e);
        }
    }

    private List<HoangAnhCustomEntity.ProductListDTO> getProductList(HttpServletRequest request) {
        String type = request.getParameter("type");
        switch (type) {
            case "all" -> {
                return pDAO.getProductListInformationManage();
            }
            case "search" -> {
                String searchInput = request.getParameter("searchProduct");
                return pDAO.getProductListInformationByName(searchInput);
            }
            case "filterStatus" -> {
                String status = request.getParameter("status");
                return pDAO.getProductListInformationByStatus(status);
            }
            case "filterCate" -> {
                int cateId = Integer.parseInt(request.getParameter("cateId"));
                return pDAO.getProductListInformationByCateId(cateId);
            }
            case "filterTag" -> {
                int tagId = Integer.parseInt(request.getParameter("tagId"));
                return pDAO.getProductListInformationByTagId(tagId);
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
        doGetProcess(request, response, getProductList(request));
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
