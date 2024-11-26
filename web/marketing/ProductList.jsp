<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Manager</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css"/>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            * a{
                text-decoration: none;
                color: black;
            }

            h1, h3, h4, h4, h5, h6, p {
                padding: 0;
                margin: 0;
            }

            body {
                background-color: #ffffff;
                font-family: Arial, sans-serif;
            }

            .in-active {
                display: none;
            }

            .success-popup {
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: black; /* Success green color */
                color: white;
                padding: 20px;
                border-radius: 10px;
                display: none; /* Initially hidden */
                text-align: center;
                z-index: 1003;
            }

            .tick-animation {
                font-size: 30px;
                animation: tick-pop 0.5s ease-in-out;
                display: block;
                margin-bottom: 10px;
                color: white;
            }

            @keyframes tick-pop {
                0% {
                    transform: scale(0);
                }
                50% {
                    transform: scale(1.3);
                }
                100% {
                    transform: scale(1);
                }
            }

            .search-section {
                position: relative;
                width: 100%;
                max-width: 300px; /* Adjust as needed */
            }

            .search-section input[type="text"] {
                width: 100%;
                padding: 5px 15px 5px 35px;
                border: 1px solid #ccc;
                border-radius: 5px;
                outline: none;
                font-size: 1em;
                transition: box-shadow 0.2s ease;
            }

            .search-section i {
                position: absolute;
                left: 10px;
                top: 50%;
                transform: translateY(-50%);
                color: #888;
                font-size: 0.9em;
            }

            .search-section input[type="text"]:focus {
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
                border-color: #333;
            }

            .category-section {
                display: flex;
                gap: 10px;
            }

            .category , .status{
                display: flex;
                gap: 5px;
                align-items: center;
            }

            .category button , .status button{
                border: none;
                color: white;
                background-color: black;
                border-radius: 6px;
                transition: ease-in-out 0.2s;
            }

            .category button:hover , .status button:hover{
                color: black;
                background-color: white;
                box-shadow: inset 0 0 0 1px black;
            }

            .filter-section {
                margin-bottom: 15px;
                background-color: white;
                transition: ease-in-out 0.5s;
                position: sticky;
                top: 10px;
                z-index: 1;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px #ccc;
            }

            .filter-main {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            .category-section {
                display: flex;
                gap: 10px;
                /*align-items: center;*/
            }

            .divide {
                background-color: #ccc;
                width: 2px;
            }

            .divide-part {
                background-color: #ccc;
                height: 2px;
                width: auto;
                margin-bottom: 30px;
            }

            .card-custom {
                box-shadow: 0 0 10px #ccc;
                border-radius: 10px;
                margin-bottom: 20px;
            }

            .product-id {
                padding: 10px;
                border-bottom: solid 1px black;
            }

            .product-id i {
                font-size: 15px;
            }

            .product-main {
                display: flex;
                justify-content: space-around;
                align-items: center;
                margin: 10px auto;
                /*border: solid 1px black;*/
                margin: 10px;
                border-radius: 10px;
            }

            .product-name {
                max-width: 600px;
                display: -webkit-box;
                -webkit-box-orient: vertical; /* Sets the box's orientation to vertical */
                -webkit-line-clamp: 2; /* Limits to 2 lines */
                overflow: hidden; /* Hides overflowing content */
                text-overflow: ellipsis; /* Adds the ellipsis */
            }

            .card-footer-custom {
                border-top: solid 1px black;
                background-color: white;
                border-bottom-left-radius: 10px;
                border-bottom-right-radius: 10px;
                transition: ease-in-out 0.2s;
            }

            .card-footer-custom:hover {
                background-color: #ccc;
            }

            .search-section button {
                background-color: black;
                color: white;
                border-radius: 10px;
                border: none;
                padding: 5px 10px;
            }

            .addnewproduct button {
                border: dashed black;
                padding: 5px 10px;
                background-color: white;
                box-shadow: 0 0 10px #ccc;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                transform: scale(1);
            }

            .addnewproduct button:hover {
                transform: scale(1.04);
            }

            .loadmorebtn button {
                padding: 10px 20px;
                color: white;
                background-color: black;
                border: none;
                transition: ease-in-out 0.2s;
                cursor: pointer;
            }

            .loadmorebtn button:hover {
                color: black;
                background-color: white;
                box-shadow: inset 0 0 0 1px black;
            }

            .tag-section {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-top: 10px;
            }

            .tag-section button {
                border: none;
                color: white;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                transform: scale(1);
            }

            .tag-section button:hover{
                transform: scale(1.1);
            }
        </style>
    </head>

    <body>
        <!-- Header import section -->
        <div class="content" id="content">
            <jsp:include page="../base-component/header.jsp" />
        </div>

        <jsp:include page="../base-component/sidebar.jsp" />
        <div class="content1" id="content1">
            <div class="filter-section">
                <div class="filter-main">
                    <div class="category-section">
                        <div class="category">
                            <h5><b>Category: </b></h5>
                            <a href="productlistmanager?type=all"><button>All Category</button></a>
                            <c:forEach items="${cateList}" var="cate">
                                <form action="productlistmanager" method="post">
                                    <input type="hidden" name="action" value="filterProduct">
                                    <input type="hidden" name="type" value="filterCate">
                                    <input type="hidden" name="cateId" value="${cate.id}">
                                    <button type="submit">${cate.name}</button>
                                </form>
                            </c:forEach>
                        </div>
                        <div class="divide"></div>
                        <div class="status">
                            <h5><b>Status: </b></h5>
                            <a href="productlistmanager?type=all"><button>All Status</button></a>
                            <c:forEach items="${statusList}" var="status">
                                <form action="productlistmanager" method="post">
                                    <input type="hidden" name="action" value="filterProduct">
                                    <input type="hidden" name="type" value="filterStatus">
                                    <input type="hidden" name="status" value="${status}">
                                    <button type="submit">${status}</button>
                                </form>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="search-section d-flex gap-2 align-items-center">
                        <form action="productlistmanager" method="post">
                            <input type="hidden" name="action" value="search">
                            <input type="hidden" name="type" value="search">
                            <div class="search-box">
                                <input type="text" placeholder="Search by name..." name="searchProduct" value="${searchProduct}" required="">
                                <i class="fas fa-search"></i>
                            </div>
                        </form>
                        <c:if test="${not empty searchProduct}"><a href="productlistmanager?type=all"><button>Clear</button></a></c:if>
                        </div>
                    </div>
                    <div class="tag-section">
                        <h5><b>Tag: </b></h5>
                        <a href="productlistmanager?type=all"><button style="background-color: black;">All Tag</button></a>
                    <c:forEach items="${tagList}" var="tag">
                        <form action="productlistmanager" method="post">
                            <input type="hidden" name="action" value="filterProduct">
                            <input type="hidden" name="type" value="filterTag">
                            <input type="hidden" name="tagId" value="${tag.id}">
                            <button type="submit" style="background-color: ${tag.colorCode};"> ${tag.getName()}</button>
                        </form>
                    </c:forEach>
                </div>
            </div>

            <div class="divide-part"></div>

            <div class="addnewproduct mb-4">
                <form action="" method="POST">
                    <input type="hidden" name="action" value="addnewproduct">
                    <button>Add new product</button>
                </form>
            </div>

            <div class="product-list-section" id="product-section">
                <c:forEach items="${productList}" var="p">
                    <div class="card-custom">
                        <div class="card-body">
                            <div class="product-id d-flex gap-2">
                                <p><b>ID: ${p.productId}</b></p>
                                <p><i class="fa-solid fa-star" style="color: #FFD43B;"> Star : ${p.star} ( Total Feedback : ${p.countFeedBack} )</i></p>
                                <c:forEach items="${p.tagList}" var="t">
                                    <span class="badge" style="background-color: ${t.colorCode};">${t.name}</span>
                                </c:forEach>
                            </div>
                            <div class="product-main">
                                <div class="product-thumbnail">
                                    <img src="${p.productThumb}" alt="${p.productName}" width="120" height="150">
                                </div>
                                <div class="product-name">
                                    <p><b>Name: </b>${p.productName}</p>
                                </div>
                                <div class="product-price">
                                    <p><b>Price: </b><fmt:formatNumber value="${p.productPrice}" pattern="###,###,###VND"/></p>
                                </div>
                                <div class="post-cate">
                                    <label for="cate"><b>Category: </b></label>
                                    <select id="cate" onchange="saveCateId(this, ${p.productId})">
                                        <c:forEach items="${cateList}" var="c">
                                            <option data-cate-id="${c.id}" <c:if test="${p.subCategoryId == c.id}">selected</c:if>>${c.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="post-status">
                                    <label for="status"><b>Status: </b></label>
                                    <select id="status" onchange="saveStatus(this, ${p.productId})">
                                        <c:forEach items="${statusList}" var="sta">
                                            <option <c:if test="${p.status == sta}">selected</c:if>>${sta}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <a href="productdetailmanager?pid=${p.productId}">
                            <div class="card-footer-custom text-center">
                                <p>View Detail</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <!--Load More Button-->
            <div class="loadmorebtn d-flex justify-content-center align-items-center mt-4">
                <button type="button" class="load-btn" id="load-more">Load More Items</button>
            </div>

            <div id="success-popup-status" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Change status successfully</p>
            </div>

            <div id="success-popup-save" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Save information successfully</p>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
                const sidebar = document.getElementById('sidebar');
                const content = document.getElementById('content');
                const content1 = document.getElementById('content1');
                const navbar = document.getElementById('navbar');

                toggleSidebarBtn.addEventListener('click', () => {
                    sidebar.classList.toggle('collapsed');
                    content.classList.toggle('collapsed');
                    content1.classList.toggle('collapsed');
                    toggleSidebarBtn.classList.toggle('collapsed');
                });

                var activeSideBar = $("#product-list");
                activeSideBar.addClass("active-custom");

                var currentPage = ${page};
                var currentList = '${allList}';

                if (${canLoadMore == false}) {
                    $("#load-more").attr("disabled", true).text("No More Items").css({
                        "background-color": "#333",
                        "cursor": "not-allowed",
                        "opacity": "0.7",
                        "box-shadow": "none",
                        "transform": "none",
                        "padding": "10px"
                    });
                }
                ;

                $(".load-btn").click(function () {
                    console.log(${allList});
                    $.ajax({
                        url: "${pageContext.request.contextPath}/productlistmanager",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: currentPage + 1,
                            allList: JSON.stringify(JSON.parse(currentList)) // ensure it’s a stringified JSON
                        },
                        success: function (response) {
                            $("#product-section").append(response); // Append new feedback items
                            currentPage++;
                            console.log("success");
                            // Check for "load-more" button existence to disable if no more items
                            if ($(document).find('#no-loadmore').length > 0) {
                                console.log("No Load More");
                                $("#load-more").attr("disabled", true).text("No More Items").css({
                                    "background-color": "#333",
                                    "cursor": "not-allowed",
                                    "opacity": "0.7",
                                    "box-shadow": "none",
                                    "transform": "none",
                                    "padding": "10px"
                                });
                            }

                            function saveStatus(select, pid) {
                                let status = $(select).val();
                                console.log(pid + ", " + status);

                                $.ajax({
                                    url: '${pageContext.request.contextPath}/productlistmanager',
                                    type: 'POST',
                                    data: {
                                        action: 'saveStatus',
                                        pid: pid,
                                        status: status
                                    },
                                    success: function (response) {
                                        $("#success-popup-save").fadeIn();

                                        setTimeout(function () {
                                            $("#success-popup-save").fadeOut();
                                        }, 800);
                                    }
                                });
                            }

                            function saveCateId(select, pid) {
                                let cateId = $(select).find(":selected").attr('data-cate-id');
                                console.log(pid + ", " + cateId);

                                $.ajax({
                                    url: '${pageContext.request.contextPath}/productlistmanager',
                                    type: 'POST',
                                    data: {
                                        action: 'saveCateId',
                                        pid: pid,
                                        cateId: cateId
                                    },
                                    success: function (response) {
                                        $("#success-popup-save").fadeIn();

                                        setTimeout(function () {
                                            $("#success-popup-save").fadeOut();
                                        }, 800);
                                    }
                                });
                            }
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });
            });

            function saveStatus(select, pid) {
                let status = $(select).val();
                console.log(pid + ", " + status);

                $.ajax({
                    url: '${pageContext.request.contextPath}/productlistmanager',
                    type: 'POST',
                    data: {
                        action: 'saveStatus',
                        pid: pid,
                        status: status
                    },
                    success: function (response) {
                        $("#success-popup-save").fadeIn();

                        setTimeout(function () {
                            $("#success-popup-save").fadeOut();
                        }, 800);
                    }
                });
            }

            function saveCateId(select, pid) {
                let cateId = $(select).find(":selected").attr('data-cate-id');
                console.log(pid + ", " + cateId);

                $.ajax({
                    url: '${pageContext.request.contextPath}/productlistmanager',
                    type: 'POST',
                    data: {
                        action: 'saveCateId',
                        pid: pid,
                        cateId: cateId
                    },
                    success: function (response) {
                        $("#success-popup-save").fadeIn();

                        setTimeout(function () {
                            $("#success-popup-save").fadeOut();
                        }, 800);
                    }
                });
            }
        </script>
    </body>
</html>

</body>
</html>
