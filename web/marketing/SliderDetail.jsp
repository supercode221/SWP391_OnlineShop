<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Slider Detail</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css"/>
        <style>
            .info-section {
                background-color: #fff; 
                border-radius: 8px; 
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
                margin: 20px 0; 
            }

            .card-text {
                font-size: 1.1rem; 
                line-height: 1.6; 
            }

            .card-text strong {
                font-weight: bold; 
                color: #333; 
            }

            .card-text span {
                color: #555; 
            }

            .card-text br {
                margin-bottom: 10px; 
            }

            
            .backlink-text {
                color: #007bff; 
                text-decoration: underline;
                cursor: pointer;
            }

            .backlink-text:hover {
                text-decoration: none; 
                color: #0056b3; 
            }

            
            .alert-warning {
                padding: 15px;
                font-size: 1rem;
                margin-top: 20px;
                border-radius: 8px;
            }

            body {
                background-color: #f8f9fa;
            }
            .center-container {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }
            .card {
                max-width: 800px;
                width: 100%;
                padding: 20px;
                border: none;
                box-shadow: none;
            }
            .slider-image {
                max-width: 100%;
                height: auto;
                display: block;
                margin: 15px auto;
                border-radius: 8px;
            }
            * a {
                text-decoration: none;
                color: black;
            }
            .info-section {
                flex: 0 0 25%;
            }
            .image-section {
                flex: 0 0 75%;
            }
            .d-flex-custom {
                display: flex;
            }
            .input-group {
                width: 100%;
            }
            #backLinkContent {
                min-height: 100px;
                padding: 15px;
                border-radius: 5px;
                background-color: #f1f1f1;
                margin-top: 15px;
            }
        </style>
    </head>
    <body>
        <div class="content" id="content">
            <jsp:include page="../base-component/header.jsp" />
        </div>
        <jsp:include page="../base-component/sidebar.jsp" />

        <!-- Main Content -->
        <div class="content1" id="content1">
            <div class="">
                <h2 class="mb-4 text-center">Slider Details</h2>
                <div class="form-control d-flex-custom">
                    <!-- Info Section (25%) -->
                    <div class="info-section mt-4 p-3">
                        <c:if test="${not empty slider}">
                            <p class="card-text">
                                <strong>Slider ID:</strong> <span>${slider.sliderId}</span> <br>
                                <strong>Content:</strong> <span>${slider.content}</span> <br>
                                <strong>Status:</strong> 
                                <span class="text-light badge ${slider.status == 'active' ? 'bg-success' : 'bg-secondary'}">${slider.status}</span><br>
                                <strong>BackLink:</strong> 
                                <span class="backlink-text">${slider.backLink}</span>
                            </p>
                        </c:if>

                        <c:if test="${empty slider}">
                            <div class="alert alert-warning" role="alert">
                                No slider found with the specified ID.
                            </div>
                        </c:if>
                    </div>


                    <!-- Image Section (75%) -->
                    <div class="image-section">
                        <c:if test="${not empty slider}">
                            <img src="${slider.link}" alt="Slider Image" class="slider-image">
                        </c:if>
                    </div>
                </div>

                <div class="text-center mt-4">
                    <a href="sliderlist" class="btn btn-outline-dark">Back to Slider List</a>
                </div>

                <!-- Box for BackLink Content -->
                <div class="mt-5">
                    <h5>Load BackLink Content</h5>
                    <div class="input-group mb-3">
                        <input type="text" id="ajaxBackLink" class="form-control" placeholder="Enter BackLink URL" value="${slider.backLink}" />
                        <button id="loadBackLinkContent" class="btn btn-primary" onclick="fetchPageContent()">Load Content</button>
                    </div>
                    <div id="backLinkContent">
                        <!-- Backlink content will be loaded here -->
                    </div>
                </div>

            </div>
        </div>

        <script>
            $(document).ready(function () {
                var activeSideBar = $("#slider-list");
                activeSideBar.addClass("active-custom");

                // Load content via AJAX when button is clicked
                $('#loadBackLinkContent').click(function () {
                    const backlinkUrl = $('#ajaxBackLink').val().trim();

                    if (!backlinkUrl) {
                        alert('Please enter a valid BackLink URL.');
                        return;
                    }

                    // Make AJAX GET request
                    $.ajax({
                        url: backlinkUrl,
                        method: 'GET',
                        success: function (response) {
                            // Display content in the box
                            $('#backLinkContent').append(response);
                        },
                        error: function (xhr, status, error) {
                            $('#backLinkContent').html(
                                    `<div class="alert alert-danger">
                                    Failed to load content: ${xhr.statusText} (${xhr.status})
                                </div>`
                                    );
                        }
                    });
                });
            });

            function fetchPageContent() {
                $.ajax({
                    url: "https://www.example.com", // URL bạn muốn lấy nội dung
                    type: "GET",
                    success: function (response) {
                        // Chèn nội dung vào một phần tử trong trang của bạn
                        $('#content-container').html(response);
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: " + error);
                    }
                });
            }

        </script>
    </body>
</html>
