<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${sessionScope.username}'s Wish List</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <!-- Custom Styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/icon.png" type="image/png">
        <style>
            .toggle-sidebar-btn {
                display: none;
            }
            
            * a {
                text-decoration: none;
                color: black;
            }
        </style>
    </head>
    <body>
        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />

        <!-- Main Wishlist section -->
        <div class="container my-5">
            <h2 class="text-center mb-4">Your Wishlist</h2>

            <!-- Check if wishlist is not empty -->
            <c:choose>
                <c:when test="${not empty wishList}">
                    <!-- Loop through each product in the wishlist -->
                    <c:forEach var="product" items="${wishList}">
                        <div class="row mb-5 p-3 rounded shadow">
                            <!-- Product Image -->
                            <div class="col-md-3">
                                <img src="${product.productThumb}" class="img-fluid rounded" alt="${product.productName}" width="150" height="350"/>
                            </div>

                            <!-- Product Details (Vertically Centered) -->
                            <div class="col-md-6 d-flex align-items-center">
                                <div>
                                    <h5>${product.productName}</h5>
                                    <p>Price: <fmt:formatNumber value="${product.productPrice}" pattern="###,###,###VND"/></p>
                                </div>
                            </div>

                            <!-- Actions (View Details / Remove) -->
                            <div class="col-md-3 d-flex align-items-center">
                                <div class="btn">
                                    <a href="productdetail?productId=${product.productId}"><button>View Details</button></a>
                                    <a href="wishlist?action=deleteInView&productId=${product.productId}">
                                        <button class="deleteButton"><i class="fas fa-trash-alt"></i> Remove</button>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!-- Show message if the wishlist is empty -->
                    <div class="alert alert-info text-center" role="alert">
                        Your wishlist is empty. Start adding items to your wishlist!
                    </div>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            .btn button{
                border: none;
                background-color: black;
                color: white;
                transition: ease-in-out 0.2s;
                padding: 10px;
            }

            .btn button:hover{
                background-color: white;
                color: black;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
        </style>
    </body>
</html>
