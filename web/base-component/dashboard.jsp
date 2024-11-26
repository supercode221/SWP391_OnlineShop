<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content" id="content">
    <!-- Logo Container -->
    <div class="logo-container" style="background-image: url('${pageContext.request.contextPath}/asset/Image/wall.gif'); background-size: cover; background-position: center; background-repeat: no-repeat;">
        <div class="logo">
            <a href='homecontroller'><img src='${pageContext.request.contextPath}/asset/Image/logotrans3.svg' border='0' alt='Thi-t-k-ch-a-c-t-n'/></a>
        </div>
    </div>

    <!-- Nav and Icon Container -->
    <div class="nav-icon-container">
        <div class="container nav-icon-row">
            <!-- Navigation Links -->
            <nav class="nav-links d-flex justify-content-center">
                <a href="${pageContext.request.contextPath}/homecontroller">Home</a>
                <a href="${pageContext.request.contextPath}/productlist?type=">Shop</a>
                <a href="#" style="pointer-events: none; color: gray;">Blog</a>
                <a href="#" style="pointer-events: none; color: gray;">About Us</a>
            </nav>

            <!-- Icon Bar -->
            <c:choose>
                <c:when test="${sessionScope.uid == null}">
                    <div>
                        <a href="login"><button class="header-btn" style="padding: 10px 20px;">Sign In</button></a>
                        <a href="register"><button class="header-btn" style="padding: 10px 20px;">Sign Up</button></a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="icon-bar d-flex align-items-center">

                        <!-- User Profile Icon -->
                        <a href="userprofilecontroller" style="margin-right: 10px;">
                            <i class="fas fa-user"></i>${sessionScope.username}
                        </a>

                        <!-- Favorites Icon -->
                        <a href="wishlist?action=view">
                            <i class="fas fa-heart"></i>
                        </a>

                        <!-- Cart Icon with Badge -->
                        <div class="cart-badge">
                            <a href="cart">
                                <i class="fas fa-shopping-cart"></i>
                                <span class="badge rounded-pill">${sessionScope.productInCart}</span>
                            </a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>