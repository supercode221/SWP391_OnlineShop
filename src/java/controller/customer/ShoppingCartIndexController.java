/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customer;

import dal.MyDAO;
import dal.MyDAO.CartDetails;
import dal.MyDAO.Product;
import dal.codebase.categoryDAO;
import dal.codebase.tagDAO;
import entity.codebaseOld.Category;
import entity.codebaseOld.Tag;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyễn Nhật Minh
 */
@WebServlet(name = "ShoppingCartIndex", urlPatterns = {"/cart"})
public class ShoppingCartIndexController extends HttpServlet {

    private static int ITEM_PER_PAGE = 4;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // check if user exist
        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            request.setAttribute("msg", "You have to login first!");
            request.getRequestDispatcher("login").forward(request, response);

        } else {
            try {
                int userID = Integer.parseInt(request.getSession().getAttribute("uid").toString());
                int page = 1;
                boolean canLoadMore = false;

                MyDAO dao = new MyDAO();
                CartDetails cartDetails = dao.getCartDetails(userID);
                List<Product> products = cartDetails.getProducts();
                int cartProductCount = products.size();
                int fromInPage = ITEM_PER_PAGE * (page - 1);
                int toInPage = ITEM_PER_PAGE * page;
                if (cartProductCount > toInPage) {
                    canLoadMore = true;
                } else {
                    toInPage = cartProductCount;
                }

                cartDetails.setProducts(products.subList(fromInPage, toInPage));

                // get tag list in case of cart
                List<Tag> tags = dao.getTagListFromACart(cartDetails);

                // get category list in case of cart
                List<Category> categories = dao.getCategoryListFromACart(cartDetails);

                request.setAttribute("tags", tags);
                request.setAttribute("categories", categories);
                request.setAttribute("cart", cartDetails);
                request.setAttribute("page", page);
                request.setAttribute("canLoadMore", canLoadMore);
                request.getRequestDispatcher("customer/CartDetail.jsp").forward(request, response);

            } catch (Exception e) {
                this.log(Level.WARNING, "", e);
            }
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // check if user exist
        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            response.sendRedirect("homecontroller");

        } else {
            String action = request.getParameter("action");
            switch (action) {
                case "load-more" ->
                    processLoadMoreAction(request, response);
                case "delete" ->
                    processDeleteAction(request, response);
                case "search" ->
                    processSearchAction(request, response);
                case "update-quantity" ->
                    processUpdateQuantityAction(request, response);
                case "update-checkbox" ->
                    processUpdateCheckboxAction(request, response);
                case "similar-items" ->
                    processSimilarProductAction(request, response);
                case "buy" ->
                    processBuyAction(request, response);
                default ->
                    throw new AssertionError();
            }

        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processUpdateCheckboxAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userID = Integer.parseInt(request.getSession().getAttribute("uid").toString());

            int productId = Integer.parseInt(request.getParameter("itemId"));
            String size = request.getParameter("size");
            String colorName = request.getParameter("colorName");
            int isChecked = Integer.parseInt(request.getParameter("isChecked"));

            if (new MyDAO().updateCheckbox(userID, productId, size, colorName, isChecked)) {
                this.log(Level.INFO, "success", null);
            } else {
                this.log(Level.INFO, "fail", null);
            }

            // ajax async, non forward
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processLoadMoreAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userID = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            int page = Integer.parseInt(request.getParameter("page"));
            boolean canLoadMore = false;

            MyDAO dao = new MyDAO();
            CartDetails cartDetails = dao.getCartDetails(userID);
            List<Product> products = cartDetails.getProducts();
            int cartProductCount = products.size();
            int fromInPage = ITEM_PER_PAGE * (page - 1);
            int toInPage = ITEM_PER_PAGE * page;
            if (cartProductCount > toInPage) {
                canLoadMore = true;
            } else {
                toInPage = cartProductCount;
            }
            cartDetails.setProducts(products.subList(fromInPage, toInPage));

            request.setAttribute("cart", cartDetails);
            request.setAttribute("page", page);
            request.setAttribute("canLoadMore", canLoadMore);
            request.getRequestDispatcher("customer/MoreItemForCartDetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processDeleteAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int userID = Integer.parseInt(session.getAttribute("uid").toString());
            int productId = Integer.parseInt(request.getParameter("productId"));
            String size = request.getParameter("size");
            String colorName = request.getParameter("colorName");

            MyDAO dao = new MyDAO();
            int effectedRow = dao.deleteProductFromCartByProductIdAndUserId(productId, userID, colorName, size);
            if (effectedRow > 0) {
                int productInCart = Integer.parseInt(session.getAttribute("productInCart").toString());
                productInCart--;
                session.setAttribute("productInCart", productInCart);
            }

            if (dao.getProductCountInCart(userID) < 1) {
                response.sendRedirect("productlist");
                return;
            }
            
            response.sendRedirect("cart");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processSearchAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int userID = Integer.parseInt(session.getAttribute("uid").toString());
            String tag = request.getParameter("tag");
            String category = request.getParameter("category");
            String searchInput = request.getParameter("searchInput");

            boolean isNoParam = true;
            String realSearchingKeyword = "";
            if (!"all".equalsIgnoreCase(tag)) {
                tagDAO dao = new tagDAO();
                Tag t = dao.getTagById(Integer.parseInt(tag));
                realSearchingKeyword += t.getName();
                isNoParam = false;
            }
            if (!"all".equalsIgnoreCase(category)) {
                categoryDAO dao = new categoryDAO();
                Category c = dao.getCategoryById(Integer.parseInt(category));
                realSearchingKeyword += " " + c.getName();
                isNoParam = false;
            }
            if (!searchInput.isBlank() && !searchInput.isEmpty() && searchInput != null) {
                realSearchingKeyword += " " + searchInput;
                isNoParam = false;
            } else {
                searchInput = "";
            }
            realSearchingKeyword = realSearchingKeyword.trim();

            if (isNoParam) {
                response.sendRedirect("cart");
                return;
            }

            // run a logical query then check if it's ok
            MyDAO dao = new MyDAO();
            CartDetails cartDetails1 = dao.doSearchAndFilterLogic(userID, category, tag, searchInput);
            CartDetails cartDetails = dao.getCartDetails(userID);

            // get tag and category list in case of a cart
            List<Tag> tags = dao.getTagListFromACart(cartDetails);
            List<Category> categories = dao.getCategoryListFromACart(cartDetails);

            // forward data
            request.setAttribute("realSearchingKeyword", realSearchingKeyword);
            request.setAttribute("isSearching", "true");
            request.setAttribute("cart", cartDetails1);

            request.setAttribute("tags", tags);
            request.setAttribute("categories", categories);

            request.setAttribute("searchInput", searchInput);
            request.setAttribute("selectingCategoryId", category.equalsIgnoreCase("all") ? 0 : category);
            request.setAttribute("selectingTagId", tag.equalsIgnoreCase("all") ? 0 : tag);
            request.getRequestDispatcher("customer/CartDetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processSimilarProductAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getSession().getAttribute("uid").toString());
            int productId = Integer.parseInt(request.getParameter("productId"));

            MyDAO dao = new MyDAO();
            CartDetails cartDetails = dao.getCartDetails(userId);
            Product product = null;
            for (Product p : cartDetails.getProducts()) {
                if (p.getId() == productId) {
                    product = p;
                    break;
                }
            }
            if (product == null) {
                response.sendRedirect("homecontroller");
            }
            
            StringBuilder keywordBuilder = new StringBuilder("productlist?tags=");
            //define concatnation string
            String concat = "%2C";
            
            // append tags
            keywordBuilder.append(product.getTagList().get(0).getId());
            if (product.getTagList().size() > 1) {
                for (int i = 1; i < product.getTagList().size(); i++) {
                    keywordBuilder.append(concat);
                    keywordBuilder.append(product.getTagList().get(i).getId());
                }
            }
            
            // append category
            keywordBuilder.append("&categories=");
            keywordBuilder.append(dao.getCategoryByProductId(productId));
            
            // last append
            keywordBuilder.append("&minPrice=100000&maxPrice=10000000&type=filter");
            
            // end build
            String keyword = keywordBuilder.toString();
            // formular = productlist?tags=3%2C4&categories=&minPrice=100000&maxPrice=10000000&type=filter

            // forward
            response.sendRedirect(keyword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processBuyAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int userID = Integer.parseInt(session.getAttribute("uid").toString());
            String selectedProductIds = request.getParameter("selectedProductIds");
            String quantities = request.getParameter("quantities");
            String allDisplayedItemIds = request.getParameter("displayedItem");

            String[] checkedItemIdsArray = selectedProductIds.split(",");
            String[] quantitiesArray = quantities.split(",");
            String[] displayedItemIdArray = allDisplayedItemIds.split(",");

            if (displayedItemIdArray.length != quantitiesArray.length) {
                //Bug track
//                Writer out = response.getWriter();
//                out.write(checkedItemIdsArray.toString());
//                out.write(quantitiesArray.toString());
//                out.write(displayedItemIdArray.toString());
                response.sendRedirect("cart"); //missing msg box
            }

//            MyDAO dao = new MyDAO();
//            if (dao.updateQuantityAndCheckedProduct(userID, displayedItemIdArray, quantitiesArray, checkedItemIdsArray)) {
//                System.out.println("updateQuantityAndCheckedProduct done");
//            } else {
//                System.err.println("updateQuantityAndCheckedProduct error");
//                PrintWriter out = response.getWriter();
//                out.println(Arrays.toString(checkedItemIdsArray));
//                out.println(Arrays.toString(quantitiesArray));
//                out.println(Arrays.toString(displayedItemIdArray));
//            }

            response.sendRedirect("cartcontact");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processUpdateQuantityAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int userID = Integer.parseInt(session.getAttribute("uid").toString());

            int productId = Integer.parseInt(request.getParameter("productId"));
            int newQuantity = Integer.parseInt(request.getParameter("quantity"));
            String size = request.getParameter("size");
            String colorName = request.getParameter("colorName");

            if (new MyDAO().updateQuantity(userID, productId, size, colorName, newQuantity)) {
                this.log(Level.INFO, "update quantity success with parameters: userId = " + userID + ", productId = " + productId + ", size = " + size + ", color = " + colorName + ", new quantity = " + newQuantity + ".", null);
            } else {
                this.log(Level.INFO, "update quantity failed with parameters: userId = " + userID + ", productId = " + productId + ", size = " + size + ", color = " + colorName + ", new quantity = " + newQuantity + ".", null);
            }

            // async ajax, non-forward
        } catch (Exception e) {
            e.printStackTrace();
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
        processGetRequest(request, response);
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
        processPostRequest(request, response);
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
    }// </editor-fold

}
