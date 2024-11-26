<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            * a {
                text-decoration: none;
                color: black;
            }

            .card {
                padding: 20px;
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
        </style>
    </head>
    <body>
        <div class="content" id="content">
            <jsp:include page="../base-component/header.jsp" />
        </div>

        <jsp:include page="../base-component/sidebar.jsp" />

        <!-- Centered Content Container -->
        <div class="content1" id="content1">
            <div class="card">
                <h2 class="mb-4 text-center">Feedback Details</h2>
                <div class="container">
                    <div class="row">

                        <div class="col-md-6">
                            <p><strong>Feedback ID:</strong> ${feedback.feedbackId}</p>
                            <p><strong>Customer:</strong> ${feedback.userName}</p>
                            <p><strong>Order ID:</strong> ${feedback.orderId}</p>
                            <p><strong>Category:</strong> ${feedback.product.name}</p>
                            <p><strong>Product:</strong> ${feedback.productName}</p>
                            <p><strong>Stars:</strong> ${feedback.star}</p>
                        </div>


                        <div class="col-md-6">
                            <p class="${feedback.comment == '' ? 'text-danger' : ''}">
                                <strong class="text-black">Comment:</strong> ${feedback.comment == '' ? 'No Comment' : feedback.comment}
                            </p>
                            <p><strong>Images:</strong></p>
                            <div>
                                <c:forEach var="mediaLink" items="${feedback.linkToMedia}">
                                    <img src="${mediaLink}" alt="Feedback Image" style="width:100px; height:auto; margin:5px; border-radius:5px;" />
                                </c:forEach>
                            </div>

                            <div class="form flex mt-4">
                                <label class="form-label flex-grow-1" for="status">Change Status:</label>
                                <select class="form-select w-50 flex-grow-1" name="status" id="status" onchange="changeFeedbackStatus()">
                                    <option value="Show" ${feedback.status == 'Show' ? 'selected' : ''}>Show</option>
                                    <option value="Hidden" ${feedback.status == 'Hidden' ? 'selected' : ''}>Hidden</option>
                                </select>
                            </div>
                        </div>
                    </div>traitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitraitrai
                    <hr/>


                    <div class="row mt-3">
                        <div class="col-12 text-center">
                            <a href="feedbackservlet" style="color: blue">Back to Feedback Manager</a>
                        </div>
                    </div>
                </div>

            </div>

            <div id="success-popup" class="success-popup"> 
                <span class="tick-animation">✔</span>
                <p>Change status successfully</p>
            </div>
        </div>
        <script>
            const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
            const sidebar = document.getElementById('sidebar');
            const content = document.getElementById('content');
            const content1 = document.getElementById('content1');
            const navbar = document.getElementById('navbar');

            $(document).ready(function () {
                var activeSideBar = $("#feedback-list");
                activeSideBar.addClass("active-custom");
            });

            toggleSidebarBtn.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
                content.classList.toggle('collapsed');
                content1.classList.toggle('collapsed');
                navbar.classList.toggle('collapsed');
                toggleSidebarBtn.classList.toggle('collapsed');
            });

            function changeFeedbackStatus() {
                let status = $("#status").val();

                $.ajax({
                    url: '${pageContext.request.contextPath}/feedbacklist',
                    type: 'POST',
                    data: {
                        statusFeedback: status,
                        feedbackId: ${feedback.feedbackId}
                    },
                    success: function (response) {
                        $('#success-popup').fadeIn();

                        setTimeout(function () {
                            $('#success-popup').fadeOut();
                        }, 800);
                    }
                });
            }
            ;
        </script>
    </body>
</html>
