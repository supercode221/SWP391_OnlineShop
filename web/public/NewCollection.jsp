<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>New Collection</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <style>
            .toggle-sidebar-btn {
                display: none;
            }
            
            * a {
                text-decoration: none;
            }

            .product-content {
                transition: transform 0.3s ease-in-out, background-color 0.3s ease-in-out;
            }

            .product-content .card {
                transition: transform 0.3s ease-in-out, background-color 0.3s ease-in-out;
            }

            .product-content:hover .card {
                transform: scale(1.05);
                z-index: 1;
            }

            .product-content .card .card-text {
                transition: color 0.3s ease-in-out;
                color: black;
                white-space: nowrap;       /* Prevents text from wrapping to a new line */
                overflow: hidden;          /* Hides overflow text */
                text-overflow: ellipsis;   /* Displays "..." for truncated text */
            }

            .product-content:hover .card .card-text {
                color: green;
            }

            .feedback-section {
                display: flex;
                align-items: center;
                justify-content: space-between;
                align-content: center;
                margin-top: 20px;
            }

            .sold p {
                margin: 0;
                padding: 0;
                color: #999999;
            }

            .pro {
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />
        <h3 class="mt-4 mb-3 text-center">${requestScope.content}</h3>
        <div class="container">
            <div class="row justify-content-center">

                <c:if test="${not empty newCollect}">
                    <c:forEach items="${newCollect}" var="o">
                        <!-- Reduce the column size for smaller cards -->
                        <div class="col-12 col-md-4 col-lg-3 mb-3 product-content">
                            <a href="productdetail?productId=${o.productId}">
                                <!-- Add smaller padding, font sizes, and image size -->
                                <div class="card h-100" style="padding: 10px; box-shadow: 0 0 5px #999999;">
                                    <img class="card-img-top" src="${o.productThumb}" alt="Product Image" style="height: 150px; object-fit: cover;">
                                    <div class="card-body d-flex flex-column">
                                        <!-- Smaller text size for product name -->
                                        <h3 class="card-text text-center mb-2" style="font-size: 1.1rem;">${o.productName}</h3>
                                        <!-- Smaller description text size -->
                                        <p class="card-text text-muted text-center" style="font-size: 1.3rem;"><fmt:formatNumber value="${o.productPrice}" pattern="###,###,###VND" /></p>
                                        <div class="tag-section text-center">
                                            <div class="main-badge">
                                                <c:forEach items="${o.tagList}" var="tag">
                                                    <span class="badge" style="background-color: ${tag.colorCode};">${tag.name}</span>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="feedback-section">
                                            <div class="mt-auto">
                                                <i class="fa-solid fa-star" style="color: #FFD43B;"> ${o.star} ( ${o.countFeedBack} )</i>
                                            </div>
                                            <div class="sold">
                                                <p>Sold: ${o.soldItem}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </c:if>



                <c:if test="${empty newCollect}">
                    <div class="col-12">
                        <p class="text-center">There are no products available in the new collection at the moment.</p>
                    </div>
                </c:if>

            </div>
        </div>
        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />
    </body>
</html>
