<%-- 
    Document   : BlogList
    Created on : 20 Oct 2024, 00:43:08
    Author     : Lenovo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blogs</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <style>
            * a {
                text-decoration: none;
                color: black;
            }

            .link button {
                padding: 10px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border-radius: 5px;
                border: none;
            }

            .link button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
                transform: scale(1.05);
            }

            .blog-header {
                display: flex;
                justify-content: center;
            }

            .blog-image {
                width: 100%;
                height: 200px;
                object-fit: cover;
                display: block;
                border-radius: 5px;
            }

            .blog-body {
                padding: 10px;
            }

            .blog-title {
                font-size: 1.2em;
                font-weight: bold;
                margin-bottom: 0.5rem;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
            }

            .blog-minides {
                font-size: 0.95em;
                color: #555;
                margin-bottom: 0.75rem;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
            }

            .blog-footer {
                padding-left: 10px;
                padding-right: 10px;
                text-align: left;
            }

            a .blog-footer p {
                width: 100%;
                margin: 0;
                text-align: left;
            }

            .blog-main {
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                box-shadow: 0 0 10px #ccc;
                padding: 20px;
                transition: ease-in-out 0.2s;
                height: 100%;
                border-radius: 5px;
                background-color: #fff;
            }

            .blog-main:hover {
                transform: scale(1.02);
            }

            .date {
                color: grey;
            }

            .feedback-main {
                border: solid 1px #ccc;
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

            .blog-header-section {
                display: flex;
                align-items: center;
                gap: 15px;
                justify-content: space-between;
            }

            .search-box {
                position: relative;
                width: 100%;
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

            .toggle-sidebar-btn {
                display: none;
            }

            .category-section {
                display: flex;
                gap: 5px;
            }
            
            .category-section button{
                border: none;
                color: white;
                background-color: black;
                border-radius: 6px;
                transition: ease-in-out 0.2s;
            }

            .category-section button:hover {
                color: black;
                background-color: white;
                box-shadow: inset 0 0 0 1px black;
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
        </style>
    </head>
    <body>
        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />

        <div class="container mt-5">
            <div class="blog-header-section">
                <h3 class="mb-3">Freeze blog</h3>
                <div class="search-section d-flex gap-2">
                    <form action="bloglist" method="post">
                        <input type="hidden" name="action" value="searchBlog">
                        <div class="search-box">
                            <input type="text" placeholder="Search Blog..." name="searchblog" value="${searchBlogInput}" required="">
                            <i class="fas fa-search"></i>
                        </div>
                    </form>
                    <c:if test="${not empty searchBlogInput}"><button><a href="bloglist">Clear search</a></button></c:if>
                    </div>
                </div>
                <div class="category-section">
                    <a href="bloglist"><button>All blog</button></a>
                <c:forEach items="${blogCate}" var="bc">
                    <form action="bloglist" method="post">
                        <input type="hidden" name="action" value="filterBlog">
                        <input type="hidden" name="cateId" value="${bc.id}">
                        <button type="submit">${bc.name} blog</button>
                    </form>
                </c:forEach>
            </div>
            <div class="row justify-content-center">
                <c:if test="${(requestScope.blog == null || blog.isEmpty()) && empty searchBlogInput}">
                    <div class="container mt-2">
                        <div class="col-12 feedback-main">
                            <!-- Feedback Header -->
                            <div class="feedback-header bg-dark text-white p-3 text-center">
                                <h4 class="mb-0">Feel free to shop, Blog has not been here yet <3</h4>
                            </div>

                            <!-- Feedback Body -->
                            <div class="feedback-body p-4">
                                <div class="link align-items-center d-flex justify-content-center">
                                    <a href="productlist?type="><button>Shop now</button></a>
                                </div>
                            </div>

                            <!-- Feedback Footer -->
                            <div class="feedback-footer bg-light text-black p-3 rounded-top d-flex justify-content-center align-items-center">
                                <p class="text-center text-success">We are Freeze to see you shopping <3</p>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${(requestScope.blog == null || blog.isEmpty()) && not empty searchBlogInput}">
                    <div class="container mt-2">
                        <div class="col-12 feedback-main">
                            <!-- Feedback Header -->
                            <div class="feedback-header bg-dark text-white p-3 text-center">
                                <h4 class="mb-0">Freeze has not found any blog like: [${searchBlogInput}]</h4>
                            </div>

                            <!-- Feedback Body -->
                            <div class="feedback-body p-4">
                                <div class="link align-items-center d-flex justify-content-center">
                                    <a href="productlist?type="><button>Shop now</button></a>
                                </div>
                            </div>

                            <!-- Feedback Footer -->
                            <div class="feedback-footer bg-light text-black p-3 rounded-top d-flex justify-content-center align-items-center">
                                <p class="text-center text-success">We are Freeze to see you shopping <3</p>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${requestScope.blog != null && !blog.isEmpty()}">
                    <div id="blogItem" class="row row-cols-1 row-cols-md-3 g-4">
                        <c:forEach items="${requestScope.blog}" var="b">
                            <div class="col">
                                <div class="blog-main h-100">
                                    <a href="blogdetail?blogId=${b.id}" class="text-decoration-none text-black">
                                        <div class="blog-header">
                                            <c:forEach items="${b.blogImageList}" var="bMed">
                                                <c:if test="${bMed.type == 'thumbnail'}">
                                                    <img src="${bMed.link}" class="blog-image img-fluid">
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <div class="blog-body">
                                            <h4 class="blog-title">${b.title}</h4>
                                            <p class="blog-minides">${b.content}</p>
                                        </div>
                                        <div class="blog-footer">
                                            <p class="date">${b.createAt} <b>~</b> ${b.author.authorName} ~ ${b.cate.name}</p>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <c:if test="${empty searchBlogInput}">
                        <!--Load More Button-->
                        <div class="loadmorebtn d-flex justify-content-center align-items-center mt-4">
                            <button type="button" class="filter-btn" id="load-more" 
                                    <c:if test="${!canLoadMore}">disabled</c:if>>Load More Items</button>
                            </div>
                    </c:if>
                </c:if>
            </div>
        </div>

        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            var currentPage = ${page};

            $(document).ready(function () {
                $("#load-more").click(function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/bloglist",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: currentPage + 1
                        },
                        success: function (response) {
                            $("#blogItem").append(response);
                            currentPage++;

                            // Check if there are more items to load
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
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });
            });
        </script>
    </body>
</html>
