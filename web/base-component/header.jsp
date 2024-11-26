<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Logo Container -->
<div class="logo-container" style="background-image: url('${pageContext.request.contextPath}/asset/Image/wall.gif'); background-size: cover; background-position: center; background-repeat: no-repeat;">
    <div class="logo">
        <a href='homecontroller' <c:if test="${sessionScope.roleId != 1}">style="pointer-events: none !important; color: gray !important;"</c:if>><img src='${pageContext.request.contextPath}/asset/Image/logotrans3.svg' border='0' alt='Thi-t-k-ch-a-c-t-n'/></a>
        </div>
    </div>

    <!-- Nav and Icon Container -->
    <div class="nav-icon-container">
        <div class="container nav-icon-row">
        <c:if test="${sessionScope.roleId != 1}">
            <i  class="fa-solid fa-bars toggle-sidebar-btn" id="toggleSidebarBtn" style="cursor: pointer"></i>
        </c:if>

        <!-- Navigation Links -->
        <nav class="nav-links d-flex justify-content-center">
            <!--<i class="fa-solid fa-bars" style="color: #000000;"></i>-->
        <c:if test="${sessionScope.roleId == 1 || sessionScope.roleId == -1}">
                <a href="${pageContext.request.contextPath}/homecontroller">Home</a>
                <a href="${pageContext.request.contextPath}/productlist?type=">Shop</a>
                <a href="${pageContext.request.contextPath}/bloglist">Blog</a>
                    <a href="#" style="pointer-events: none !important; color: gray !important;">About Us</a>
            </c:if>
        </nav>

        <!-- Icon Bar -->

        <c:choose>

            <c:when test="${sessionScope.uid == null}">
                <style>
                    .search-box-header {
                        position: relative;
                        width: 100%;
                        max-width: 300px; /* Adjust as needed */
                    }

                    .search-box-header input[type="text"] {
                        width: 95%;
                        padding: 5px 5px 5px 35px;
                        border: 1px solid #ccc;
                        border-radius: 5px;
                        outline: none;
                        font-size: 0.8em;
                        transition: box-shadow 0.2s ease;
                    }

                    .search-box-header i {
                        position: absolute;
                        left: 10px;
                        top: 50%;
                        transform: translateY(-50%);
                        color: #888;
                        font-size: 0.8em;
                    }

                    .search-box-header input[type="text"]:focus {
                        box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
                        border-color: #333;
                    }
                </style>
                <div class="d-flex align-items-center gap-3">
                    <form action="productlist" method="get">
                        <div class="search-box-header">
                            <input type="text" placeholder="Search product ..." name="searchproduct" value="${searchInput}" required="">
                            <i class="fas fa-search"></i>
                        </div>
                    </form>
                    <a href="login"><button class="header-btn" style="padding: 10px 20px;">Sign In</button></a>
                    <a href="register"><button class="header-btn" style="padding: 10px 20px;">Sign Up</button></a>
                </div>
            </c:when>
            <c:otherwise>

                <div class="icon-bar d-flex align-items-center">
                    <style>
                        .search-box-header {
                            position: relative;
                            width: 100%;
                            max-width: 300px; /* Adjust as needed */
                        }

                        .search-box-header input[type="text"] {
                            width: 95%;
                            padding: 5px 5px 5px 35px;
                            border: 1px solid #ccc;
                            border-radius: 5px;
                            outline: none;
                            font-size: 0.8em;
                            transition: box-shadow 0.2s ease;
                        }

                        .search-box-header i {
                            position: absolute;
                            left: 10px;
                            top: 50%;
                            transform: translateY(-50%);
                            color: #888;
                            font-size: 0.8em;
                        }

                        .search-box-header input[type="text"]:focus {
                            box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
                            border-color: #333;
                        }
                    </style>
                    <c:if test="${sessionScope.roleId == 1}">
                        <form action="productlist" method="get">
                            <div class="search-box-header">
                                <input type="text" placeholder="Search product ..." name="searchproduct" value="${searchInput}" required="">
                                <i class="fas fa-search"></i>
                            </div>
                        </form>
                    </c:if>

                    <!-- User Profile Dropdown Container -->
                    <div class="profile-dropdown-container" 
                         onmouseenter="showDropdown()" onmouseleave="hideDropdown()" 
                         style="position: relative; display: inline-block; margin-right: 10px;">

                        <!-- Profile Icon -->
                        <a href="userprofilecontroller" class="profile-icon" >
                            <i class="fas fa-user"></i> ${sessionScope.username}
                        </a>

                        <!-- Dropdown Menu -->
                        <div id="profileDropdownMenu" class="profile-dropdown-menu">
                            <a href="userprofilecontroller" class="dropdown-item">User Profile</a>
                            <c:if test="${sessionScope.roleId != 1}">
                                <a 
                                    <c:if test="${sessionScope.roleId == 2}"> href="${pageContext.request.contextPath}/admindashboard" </c:if>
                                    <c:if test="${sessionScope.roleId == 3}"> href="${pageContext.request.contextPath}/customers" </c:if>
                                    <c:if test="${sessionScope.roleId == 4}"> href="${pageContext.request.contextPath}/ordermanager?type=all" </c:if>
                                    <c:if test="${sessionScope.roleId == 5}"> href="${pageContext.request.contextPath}/marketingdashboard" </c:if>
                                    <c:if test="${sessionScope.roleId == 6}"> href="${pageContext.request.contextPath}/admindashboard" </c:if>
                                    <c:if test="${sessionScope.roleId == 7}"> href="${pageContext.request.contextPath}/ordermanager?type=all" </c:if>
                                        class="dropdown-item">
                                    <c:if test="${sessionScope.roleId == 1 || sessionScope.roleId == null}">Back to Homepage</c:if>
                                    <c:if test="${sessionScope.roleId == 2}">Admin Dashboard</c:if>
                                    <c:if test="${sessionScope.roleId == 3}">Customers Manager</c:if>
                                    <c:if test="${sessionScope.roleId == 4}">Bills Manager</c:if>
                                    <c:if test="${sessionScope.roleId == 5}">Marketing Dashboard</c:if>
                                    <c:if test="${sessionScope.roleId == 6}">Sale Dashboard</c:if>
                                    <c:if test="${sessionScope.roleId == 7}">Deliveries Manager</c:if>
                                    </a>
                            </c:if>
                            <c:if test="${sessionScope.roleId == 1}"><a href="billlist" class="dropdown-item">My Orders</a></c:if>
                            <c:if test="${sessionScope.roleId == 1}"><a href="refund" class="dropdown-item">Refund Request</a></c:if>
                                <a href="changepassword" class="dropdown-item">Change Password</a>
                            <c:if test="${sessionScope.roleId == 1}"><a href="addresses" class="dropdown-item">Addresses</a></c:if>
                                <a href="LogOut" class="dropdown-item">Logout</a>
                            </div>
                        </div>
                        <style>

                            /* Dropdown Menu Styling */
                            .profile-dropdown-menu {
                                position: absolute;
                                right: 0;
                                background-color: white;
                                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
                                min-width: 160px;
                                z-index: 1;
                                border-radius: 4px;
                                display: none; /* Hidden by default */
                            }

                            .profile-dropdown-menu .dropdown-item {
                                padding: 10px 20px;
                                text-decoration: none;
                                color: black;
                                display: block;
                                font-size: 0.9rem;
                                transition: background-color 0.2s ease;
                            }

                            .profile-dropdown-menu .dropdown-item:hover {
                                background-color: #f1f1f1;
                            }
                        </style>
                        <script>
                            // Show the dropdown menu
                            function showDropdown() {
                                document.getElementById("profileDropdownMenu").style.display = "block";
                            }

                            // Hide the dropdown menu
                            function hideDropdown() {
                                document.getElementById("profileDropdownMenu").style.display = "none";
                            }
                        </script>


                        <!-- Favorites Icon -->
                        <a href="wishlist?action=view" <c:if test="${sessionScope.roleId != 1}">style="pointer-events: none !important; color: gray !important;"</c:if>>
                            <i class="fas fa-heart"></i>
                        </a>

                        <!-- Star icon for feedback -->
                        <div class="feedback-header">
                            <a href="feedback" <c:if test="${sessionScope.roleId != 1}">style="pointer-events: none !important; color: gray !important;"</c:if>>
                                <i class="fa-solid fa-star"></i>
                            </a>
                        </div>

                        <!-- Cart Icon with Badge -->
                    <c:choose>
                        <c:when test="${sessionScope.productInCart != 0}">
                            <div class="cart-badge">
                                <a href="cart" <c:if test="${sessionScope.roleId != 1}">style="pointer-events: none !important; color: gray !important;"</c:if>>
                                        <i class="fas fa-shopping-cart"></i>
                                        <span class="badge rounded-pill">${sessionScope.productInCart}</span>
                                </a>
                            </div>       
                        </c:when>
                        <c:otherwise>
                            <div class="cart-badge">
                                <a href="javascript:alert(`There're no product in your cart`)" <c:if test="${sessionScope.roleId != 1}">style="pointer-events: none !important; color: gray !important;"</c:if>>
                                        <i class="fas fa-shopping-cart"></i>
                                        <span class="badge rounded-pill">${sessionScope.productInCart}</span>
                                </a>
                            </div>       
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>