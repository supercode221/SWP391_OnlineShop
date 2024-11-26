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
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${blog.title} | Blog Details</title>
        <meta name="description" content="${blog.content}">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">

        <!-- CSS and JS Libraries -->
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css">

        <!-- JS Libraries -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

        <style>
            body {
                background-color: #f8f9fa;
            }

            a {
                text-decoration: none;
                color: black;
            }

            a:hover {
                text-decoration: underline;
            }

            .blog-header-section {
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding-bottom: 20px;
                border-bottom: 1px solid #ddd;
                margin-bottom: 20px;
            }

            .search-box {
                position: relative;
                max-width: 300px;
            }

            .search-box input[type="text"] {
                width: 100%;
                padding: 10px 15px 10px 35px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 1em;
            }

            .search-box i {
                position: absolute;
                left: 10px;
                top: 50%;
                transform: translateY(-50%);
                color: #888;
            }

            /* Blog Detail Section */
            .blog-detail-main {
                padding: 20px;
                background: #ffffff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }

            .blog-detail-main img {
                max-width: 100%;
                border-radius: 8px;
                margin: 20px 0;
            }

            /* Related Blogs Section */
            .side-similar-blog-section {
                background: #ffffff;
                padding: 15px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .side-similar-blog-section h4 {
                font-size: 1.5rem;
                font-weight: bold;
                margin-bottom: 15px;
            }

            .card {
                border: none;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .card:hover {
                transform: scale(1.01);
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            }

            .card img {
                border-radius: 5px;
                width: auto;
                height: 100px;
                align-self: center;
            }

            .card-title {
                font-size: 0.86rem;
                font-weight: bold;
            }

            .card-text {
                font-size: 0.75rem;
                color: #6c757d;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
            }

            .card-footer small {
                color: #888;
            }

            @media (max-width: 768px) {
                .side-similar-blog-section {
                    margin-top: 20px;
                }
            }

            .blog-detail-main img,
            .blog-body img {
                display: block;
                margin: 20px auto;
                max-width: 100%;
                height: auto;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .blog-header {
                display: flex;
                justify-content: space-between;
                align-content: center;
                color: #999999;
            }

            .side-similar-blog-section {
                position: sticky; /* Enables the sticky behavior */
                top: 80px;        /* Distance from the top of the viewport */
                z-index: 1000;    /* Ensures it stays above other content */
            }

            @media (max-width: 768px) {
                .side-similar-blog-section {
                    position: static; /* Disable sticky on smaller screens */
                }
            }
        </style>
    </head>
    <body>
        <!-- Header Section -->
        <jsp:include page="../base-component/header.jsp" />

        <div class="container mt-5">
            <div class="row">
                <!-- Blog Content Section -->
                <div class="col-lg-8 col-md-7">
                    <div class="blog-detail-main">
                        <div class="blog-header mb-4">
                            <p><strong>Posted At:</strong> ${blog.createAt}</p>
                            <p><strong>Author:</strong> ${blog.author.authorName}</p>
                        </div>
                        <div class="blog-body">
                            <h3>${blog.title}</h3>
                            <img src="${blog.getBlogImageList().get(0).link}" alt="Blog thumbnail for ${blog.title}" loading="lazy" width="500">
                            <p>${blog.content}</p>

                            <!-- Blog Subsections -->
                            <c:forEach items="${blogAttrList}" var="attr">
                                <h4>${attr.subTitle}</h4>
                                <c:forEach items="${blogSubMedList}" var="subMed">
                                    <c:if test="${attr.id == subMed.subTitleId}">
                                        <img src="${subMed.link}" alt="${attr.subTitle}" loading="lazy" width="350">
                                    </c:if>
                                </c:forEach>
                                <p>${attr.subContent}</p>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <!-- Related Blogs Section -->
                <div class="col-lg-4 col-md-5">
                    <div class="side-similar-blog-section">
                        <h4>Related Blogs</h4>
                        <div class="row g-3">
                            <c:forEach items="${requestScope.similar}" var="b">
                                <c:if test="${b.id != blog.id}">
                                    <a href="blogdetail?blogId=${b.id}">
                                        <div class="col-12">
                                            <div class="card h-100">
                                                <c:forEach items="${b.blogImageList}" var="bMed">
                                                    <c:if test="${bMed.type == 'thumbnail'}">
                                                        <img src="${bMed.link}" class="card-img-top img-fluid" alt="Related blog image for ${b.title}" loading="lazy">
                                                    </c:if>
                                                </c:forEach>
                                                <div class="card-body">
                                                    <h5 class="card-title">${b.title}</h5>
                                                    <p class="card-text">${b.content}</p>
                                                </div>
                                                <div class="card-footer">
                                                    <small class="text-muted">${b.createAt} by ${b.author.authorName}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer Section -->
        <jsp:include page="../base-component/footer.jsp" />
    </body>
</html>
