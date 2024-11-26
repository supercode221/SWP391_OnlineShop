<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blogs Manager</title>
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

            .card-custom {
                /*background: white;*/
                box-shadow: 0 0 10px #ccc;
                border-radius: 20px;
                /*padding: 10px;*/
                margin-bottom: 20px;
            }

            .post-title {
                max-width: 700px;
                display: -webkit-box; /* Enables the flex container for multi-line ellipsis */
                -webkit-box-orient: vertical; /* Sets the box's orientation to vertical */
                -webkit-line-clamp: 2; /* Limits to 2 lines */
                overflow: hidden; /* Hides overflowing content */
                text-overflow: ellipsis; /* Adds the ellipsis */
            }

            .card-body {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 20px;
                gap: 30px;
            }

            .post-id {
                font-size: 18px;
                font-weight: bold;
            }

            .card-footer-custom {
                background-color: #ccc;
                border-bottom-left-radius: 10px;
                border-bottom-right-radius: 10px;
                transition: ease-in-out 0.2s;
            }

            .card-footer-custom:hover{
                background-color: #b2b2b2;
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

            .search-box {
                position: relative;
                width: 1000px;
                max-width: 300px; /* Adjust as needed */
            }

            .search-box input[type="text"] {
                width: 100%;
                padding: 10px 15px 10px 35px;
                border: 1px solid #ccc;
                border-radius: 5px;
                outline: none;
                font-size: 1em;
                transition: box-shadow 0.2s ease;
            }

            .search-box i {
                position: absolute;
                left: 10px;
                top: 50%;
                transform: translateY(-50%);
                color: #888;
                font-size: 0.9em;
            }

            .search-box input[type="text"]:focus {
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
                border-color: #333;
            }

            .search-section button {
                border: none;
                padding: 5px 10px;
                background-color: black;
                border-radius: 7px;
            }

            .search-section a {
                color: white;
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
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 15px;
                background-color: white;
                transition: ease-in-out 0.5s;
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

            .filter-section.fixed {
                position: fixed;
                top: 0px; /* Adjust fixed position for when scrolling */
                height: 80px;
                gap: 40px;
                padding-left: 20px;
                padding-right: 20px;
                box-shadow: 0 0 30px #ccc;
                margin-top: 20px;
                border-radius: 10px;
                margin-left: 20px;
            }

            .add-new-post button {
                border: dashed 3px yellowgreen;
                padding: 10px;
                border-radius: 10px;
                background: white;
                box-shadow: 0 0 10px yellowgreen;
                transition: ease-in-out 0.2s;
                transform: scale(1);
            }

            .add-new-post button:hover {
                transform: scale(1.05);
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
            <div class="page-title text-center mb-3">
                <h3><b>Post Manager</b></h3>
            </div>
            <div class="filter-section">
                <div class="category-section">
                    <div class="category">
                        <h5><b>Category: </b></h5>
                        <a href="bloglistmanager?type=all"><button>All blog</button></a>
                        <c:forEach items="${categorylist}" var="bc">
                            <form action="bloglistmanager" method="post">
                                <input type="hidden" name="action" value="filterBlog">
                                <input type="hidden" name="type" value="filter">
                                <input type="hidden" name="cateId" value="${bc.id}">
                                <button type="submit">${bc.name} blog</button>
                            </form>
                        </c:forEach>
                    </div>
                    <div class="divide"></div>
                    <div class="status">
                        <h5><b>Status: </b></h5>
                        <a href="bloglistmanager?type=all"><button>All status</button></a>
                        <c:forEach items="${statuslist}" var="sta">
                            <form action="bloglistmanager" method="post">
                                <input type="hidden" name="action" value="filterBlog">
                                <input type="hidden" name="type" value="filterStatus">
                                <input type="hidden" name="status" value="${sta}">
                                <button type="submit">${sta}</button>
                            </form>
                        </c:forEach>
                    </div>
                </div>
                <div class="search-section d-flex gap-2">
                    <form action="bloglistmanager" method="post">
                        <input type="hidden" name="action" value="search">
                        <input type="hidden" name="type" value="search">
                        <div class="search-box">
                            <input type="text" placeholder="Search Blog..." name="searchblog" value="${searchBlog}" required="">
                            <i class="fas fa-search"></i>
                        </div>
                    </form>
                    <c:if test="${not empty searchBlog}"><button><a href="bloglistmanager?type=all">Clear search</a></button></c:if>
                    </div>
                </div>

                <div class="divide-part"></div>

                <div class="add-new-post mb-4">
                    <form action="addnewpost">
                        <button>Add new post</button>
                    </form>
                </div>

                <div class="blog-list-section" id="blog-section">
                    <!--This section display bloglist-->
                    <div class="blog-list-main" id="post-section">
                    <c:forEach items="${bloglist}" var="b">
                        <div class="card-custom">
                            <div class="card-body">
                                <div class="post-id text-center">
                                    <p>ID:${b.id}</p>
                                </div>
                                <div class="post-thumbnail">
                                    <img src="${b.getBlogImageList().get(0).getLink()}" alt="" width="160" height="150" style="border-radius: 20px;">
                                    <!--<img src="/asset/Image/BlogImage/thumbnail/Thumbnail_Blog_1.jpg" alt="" width="160" height="150" style="border-radius: 20px;">-->
                                </div>
                                <div class="post-title">
                                    <p><b>Title: </b>${b.title}</p>
                                </div>
                                <div class="post-cate">
                                    <p><b>Category: </b>${b.cate.name}</p>
                                </div>
                                <div class="post-author">
                                    <p><b>Author: </b>${b.author.authorName}</p>
                                </div>
                                <div class="post-status">
                                    <label for="status"><b>Status: </b></label>
                                    <select id="status" onchange="saveStatus(this, ${b.id})">
                                        <c:forEach items="${statuslist}" var="sta">
                                            <option <c:if test="${b.status == sta}">selected=""</c:if> >${sta}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <a href="blogdetailmanager?postId=${b.id}">
                                <div class="card-footer-custom text-center">
                                    <p>View Detail</p>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
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

                window.addEventListener("scroll", function () {
                    const filterSection = document.querySelector(".filter-section");
                    const scrollPoint = 314; // Adjust based on your layout

                    if (window.scrollY >= scrollPoint) {
                        filterSection.classList.add("fixed");
                    } else {
                        filterSection.classList.remove("fixed");
                    }
                });

                $(".load-btn").click(function () {
                    console.log(${allList});
                    $.ajax({
                        url: "${pageContext.request.contextPath}/bloglistmanager",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: currentPage + 1,
                            allList: JSON.stringify(JSON.parse(currentList)) // ensure it’s a stringified JSON
                        },
                        success: function (response) {
                            $("#post-section").append(response); // Append new feedback items
                            currentPage++;
                            console.log("success");
                            // Check for "load-more" button existence to disable if no more items
                            if (!$(response).find("#load-more").length) {
                                $("#load-more").attr("disabled", true).text("No More Items").css({
                                    "background-color": "#333",
                                    "cursor": "not-allowed",
                                    "opacity": "0.7",
                                    "box-shadow": "none",
                                    "transform": "none",
                                    "padding": "10px"
                                });
                            }

                            function saveStatus(select, postId) {
                                $.ajax({
                                    url: '${pageContext.request.contextPath}/bloglistmanager',
                                    type: 'POST',
                                    data: {
                                        action: "saveStatus",
                                        status: select.value,
                                        postId: postId
                                    },
                                    success: function (response) {
                                        console.log("success save for post: " + postId + " with status: " + select.value);
                                    }
                                });
                            }
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });

                toggleSidebarBtn.addEventListener('click', () => {
                    sidebar.classList.toggle('collapsed');
                    content.classList.toggle('collapsed');
                    content1.classList.toggle('collapsed');
                    toggleSidebarBtn.classList.toggle('collapsed');
                });

                var activeSideBar = $("#blog-list");
                activeSideBar.addClass("active-custom");
            });

            function saveStatus(select, postId) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/bloglistmanager',
                    type: 'POST',
                    data: {
                        action: "saveStatus",
                        status: select.value,
                        postId: postId
                    },
                    success: function (response) {
                        $('#success-popup-status').fadeIn();

                        setTimeout(function () {
                            $('#success-popup-status').fadeOut();
                        }, 800);
                    }
                });
            }
        </script>
    </body>
</html>

</body>
</html>
