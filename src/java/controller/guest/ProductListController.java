package controller.guest;

import dal.HoangAnhCustomEntityDAO;
import entity.HoangAnhCustomEntity;
import entity.codebaseOld.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListController extends HttpServlet {

    private static final int ITEM_PER_PAGE = 20;
    static HoangAnhCustomEntityDAO ceDAO = new HoangAnhCustomEntityDAO();

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, List<HoangAnhCustomEntity.ProductListDTO> list)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Get filter parameters
            String tags = request.getParameter("tags");
            String categories = request.getParameter("categories");
            String minPrice = request.getParameter("minPrice");
            String maxPrice = request.getParameter("maxPrice");
            String search = request.getParameter("searchproduct");

            // Set filter attributes for the JSP to display
            request.setAttribute("selectedTags", tags);
            request.setAttribute("selectedCategories", categories);
            request.setAttribute("selectedMinPrice", minPrice);
            request.setAttribute("selectedMaxPrice", maxPrice);
            request.setAttribute("searchInput", search);

            int page = 1;
            boolean canLoadMore = false;
            int productCount = list.size();
            int fromInPage = ITEM_PER_PAGE * (page - 1);
            int toInPage = ITEM_PER_PAGE * page;
            if (productCount > toInPage) {
                canLoadMore = true;
            } else {
                toInPage = productCount;
            }

            List<HoangAnhCustomEntity.ProductListDTO> mainPList = list.subList(fromInPage, toInPage);

            request.setAttribute("max", ceDAO.getMaxPrice());
            request.setAttribute("min", ceDAO.getMinPrice());
            request.setAttribute("products", mainPList);
            request.setAttribute("productCount", productCount);
            request.setAttribute("tList", ceDAO.getAllTags());
            request.setAttribute("cList", ceDAO.getAllCategories());
            request.setAttribute("page", page);
            request.setAttribute("canLoadMore", canLoadMore);
            request.getRequestDispatcher("public/productList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        switch (action) {
            case "load-more" ->
                processLoadMoreAction(request, response);
            default ->
                throw new AssertionError();
        }
    }

    protected void processLoadMoreAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int page = Integer.parseInt(request.getParameter("page"));
            boolean canLoadMore = false;

            List<HoangAnhCustomEntity.ProductListDTO> pList = ceDAO.getProductListInformation();

            int productCount = pList.size();
            int fromInPage = ITEM_PER_PAGE * (page - 1);
            int toInPage = ITEM_PER_PAGE * page;
            if (productCount > toInPage) {
                canLoadMore = true;
            } else {
                toInPage = productCount;
            }

            List<HoangAnhCustomEntity.ProductListDTO> mainPList = pList.subList(fromInPage, toInPage);

            request.setAttribute("products", mainPList);
            request.setAttribute("page", page);
            request.setAttribute("canLoadMore", canLoadMore);
            request.getRequestDispatcher("public/MoreItemForProductList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<HoangAnhCustomEntity.ProductListDTO> getProductList(HttpServletRequest request, String type)
            throws ServletException, IOException {
        String tagString = request.getParameter("tags");
        String categoryString = request.getParameter("categories");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String search = request.getParameter("searchproduct");

        // Filter the product list based on the remaining filters
        List<Integer> tags = (tagString != null && !tagString.trim().isEmpty())
                ? Arrays.stream(tagString.split(",")).map(String::trim).filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(Collectors.toList())
                : null;

        List<Integer> categories = (categoryString != null && !categoryString.trim().isEmpty())
                ? Arrays.stream(categoryString.split(",")).map(String::trim).filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(Collectors.toList())
                : null;

        List<HoangAnhCustomEntity.ProductListDTO> allProducts = ceDAO.getProductListInformation();
        return filteredList(allProducts, tags, categories, minPrice, maxPrice, search);
    }

    private List<HoangAnhCustomEntity.ProductListDTO> filteredList(List<HoangAnhCustomEntity.ProductListDTO> allProducts,
            List<Integer> tags,
            List<Integer> categories,
            String minPrice,
            String maxPrice,
            String search) {

        if (tags != null && !tags.isEmpty()) {
            allProducts = allProducts.stream()
                    .filter(product -> {
                        List<Integer> productTagIds = product.getTagList().stream()
                                .map(Tag::getId)
                                .collect(Collectors.toList());
                        return productTagIds.stream().anyMatch(tags::contains);
                    })
                    .collect(Collectors.toList());
        }

        if (categories != null && !categories.isEmpty()) {
            allProducts = allProducts.stream()
                    .filter(product -> categories.contains(product.getSubCategoryId()))
                    .collect(Collectors.toList());
        }

        if (search != null && !search.isBlank()) {
            allProducts = allProducts.stream()
                    .filter(product -> product.getProductName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return allProducts.stream()
                .filter(product -> {
                    double price = product.getProductPrice();
                    boolean withinPriceRange = true;

                    if (minPrice != null && !minPrice.trim().isEmpty()) {
                        try {
                            withinPriceRange = price >= Double.parseDouble(minPrice.trim());
                        } catch (NumberFormatException e) {
                            withinPriceRange = false;
                        }
                    }

                    if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                        try {
                            withinPriceRange = withinPriceRange && price <= Double.parseDouble(maxPrice.trim());
                        } catch (NumberFormatException e) {
                            withinPriceRange = false;
                        }
                    }

                    return withinPriceRange;
                })
                .collect(Collectors.toList());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        processGetRequest(request, response, getProductList(request, type));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Product List Servlet with filters and pagination";
    }
}
