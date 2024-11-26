<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Slider Manager</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css"/>
        <style>
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }
            .link-cell {
                max-width: 200px; /* Set a maximum width */
                white-space: nowrap; /* Prevent line breaks */
                overflow: hidden; /* Hide overflow */
                text-overflow: ellipsis; /* Show ellipsis for overflowing text */
            }
            * a {
                text-decoration: none;
                color: black;
            }
            .btn-dark {
                background-color: #343a40; /* Dark background */
                color: white; /* White text */
            }
            .btn-dark:hover {
                background-color: #23272b; /* Darker shade on hover */
                color: white; /* Maintain white text on hover */
            }

            /* Border lines for the light section */
            .table-light tr td {
                border: 1px solid black; /* Black border lines */
            }

            /* Border lines for the dark section */
            .table-dark tr th {
                border: 1px solid white; /* White border lines */
            }

            /* Ensure borders appear between cells */
            .table {
                border-collapse: collapse; /* Merge table borders for a seamless look */
            }

            .table td, .table th {
                padding: 8px; /* Adjust padding inside table cells for alignment */
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

            <!-- Filter and Search Form -->
            <form action="sliderlist" method="get" class="row gy-3 mb-4 align-items-end">
                <div class="col-md-4">
                    <label for="status" class="form-label">Filter by Status</label>
                    <select name="status" class="form-select" id="status">
                        <option value="" <c:if test="${empty param.status}">selected</c:if>>All</option>
                        <option value="active" <c:if test="${param.status == 'active'}">selected</c:if>>Active</option>
                        <option value="inactive" <c:if test="${param.status == 'inactive'}">selected</c:if>>Inactive</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label for="search" class="form-label">Search</label>
                        <input type="text" name="search" class="form-control" id="search" placeholder="Search by Name Tag" value="${param.search}">
                </div>

                <div class="col-md-2">
                    <button type="submit" class="btn btn-dark w-100">Search</button>
                </div>
            </form>


            <!-- Add Slider Button aligned to the right -->
            <div class="text-end mb-4">
                <a href="addslider" class="btn btn-dark">Add Slider</a> <!-- Changed to btn-dark -->
            </div>

            <!-- Slider List Table -->
            <div class="table-responsive" id="main-response">
                <table class="table table-striped table-hover align-middle" id="table-data">
                    <thead class="table-dark  border-primary" id="table-title">
                        <tr>
                            <!--                            <th>ID</th>-->
                            <th>Name Tag</th>
                            <th>Thumbnail Image</th>
                            <th>Status</th>
                            <th class="w-50">BackLink</th>
                            <th>Actions</th> <!-- Added Actions header -->
                        </tr>
                    </thead>
                    <tbody class="table-light border-primary" id="table-data">
                        <c:forEach var="slider" items="${slider}">
                            <tr>
                                <!--<td>${slider.sliderId}</td>-->
                                <td>${slider.content}</td>
                                <td class="link-cell">
                                    <img src="${slider.link}" alt="Slider Image" style="max-width: 100px; height: auto;">
                                </td>

                                <td>
                                    <span class="badge
                                          <c:choose>
                                              <c:when test="${slider.status == 'active'}">bg-success</c:when>
                                              <c:when test="${slider.status == 'inactive'}">bg-secondary</c:when>
                                              <c:otherwise>bg-light text-dark</c:otherwise>
                                          </c:choose>">
                                        ${slider.status}
                                    </span>
                                </td>
                                <td class="link-cell">${slider.backLink}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="sliderdetail?id=${slider.sliderId}" class="btn btn-outline-info btn-sm">View</a>
                                        <c:if test="${slider.status == 'active'}">
                                            <a href="deleteslider?id=${slider.sliderId}" class="btn btn-outline-danger btn-sm">Inactive</a>
                                        </c:if>
                                        <c:if test="${slider.status == 'inactive'}">
                                            <a href="deleteslider?id=${slider.sliderId}" class="btn btn-outline-success btn-sm">Active</a>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <script>

            $(document).ready(function () {
                // active sidebar
                var activeSideBar = $("#slider-list");
                activeSideBar.addClass("active-custom");

                // 
            });

            // process sidebar
            const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
            const sidebar = document.getElementById('sidebar');
            const content = document.getElementById('content');
            const content1 = document.getElementById('content1');
            const navbar = document.getElementById('navbar');

            toggleSidebarBtn.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
                content.classList.toggle('collapsed');
                content1.classList.toggle('collapsed');
                navbar.classList.toggle('collapsed');
                toggleSidebarBtn.classList.toggle('collapsed');
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
