<%-- 
    Document   : productList
    Created on : Sep 20, 2024, 11:59:37 PM
    Author     : Acer Aspire 7
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/productList.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
    </head>
    <body>

        <!-- Main Product List section -->
        <div class="row row-cols-1 row-cols-md-5 g-4" style="margin-top: 10px;">
            <c:choose>
                <c:when test="${empty requestScope.products}">
                    <div class="d-flex justify-content-center align-items-center" style="height: 100%;">
                        <h5 class="card-title text-center">Products unavailable now, try again</h5>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${requestScope.products}" var="pro">
                        <a href="productdetail?productId=${pro.productId}" style="text-decoration: none;">
                            <div class="col">
                                <div class="card h-100">
                                    <div class="image-container">
                                        <img src="${pro.productThumb}" class="card-img-top" alt="${pro.productName}" width="100">
                                    </div>
                                    <div class="card-body">
                                        <!-- Display product tags -->
                                        <div class="badge-container">
                                            <div class="main-badge">
                                                <c:forEach items="${pro.tagList}" var="tag">
                                                    <span class="badge" style="background-color: ${tag.colorCode};">${tag.name}</span>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <h5 class="card-title product-name">${pro.productName}</h5>
                                        <!-- Display sub-category name -->
                                        <p class="card-text">${pro.subCategoryName}</p>
                                        <p class="card-text"><fmt:formatNumber value="${pro.productPrice}" pattern="###,###,###VND" /></p>
                                        <div class="mt-auto">
                                            <i class="fa-solid fa-star" style="color: #FFD43B;"> ${pro.star} ( ${pro.countFeedBack} )</i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </body>