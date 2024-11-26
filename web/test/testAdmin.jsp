<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                background-color: #ffffff;
                font-family: Arial, sans-serif;
            }

            .sidebar {
                background-image: linear-gradient(135deg, #00c070, #0050c0);
                height: 100%;
                padding: 20px;
                position: fixed;
                top: 0;
                left: 0;
                width: 250px;
                transition: width 0.3s;
            }

            .active-custom {
                background-image: linear-gradient(115deg, #FF5050, #FA8080, #F6B2B2);
                background-size: 200% 100%;  /* Enlarging the background to create a smooth effect */
                background-position: right;   /* Initial background position */
                border-radius: 10px;
                margin-bottom: 15px;
                transition: background-position 0.5s ease-in-out;  /* Smooth transition on hover */
                transform: scale(1);
            }

            .active-custom:hover {
                background-position: left;  /* Change the background position on hover */
                border-radius: 10px;
                margin-bottom: 15px;
                transform: scale(1.05);
            }


            .hover{
                transition: ease-in-out 0.3s;
            }

            .hover:hover {
                background-image: linear-gradient(115deg, #FF5050, #FA8080, #F6B2B2);
            }

            .sidebar.collapsed {
                width: 0px;
                padding: 0;
            }

            .sidebar.collapsed a {
                /*width: 0px;*/
                padding: 0;
            }

            .sidebar a {
                color: #fff;
                text-decoration: none;
                display: block;
                padding: 10px 0;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                border-radius: 10px;
            }

            .sidebar p {
                color: #fff;
                text-decoration: none;
                display: block;
                padding: 10px 0;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

            .sidebar a:hover {

                border-radius: 10px;
            }

            .content {
                margin-left: 250px;
                /*padding: 20px;*/
                transition: margin-left 0.3s;
                /*margin-top: 50px;  Adjust for fixed navbar */
            }

            .content.collapsed {
                margin-left: 0;
            }

            .content1 {
                margin-left: 250px;
                padding: 20px;
                transition: margin-left 0.3s;
                /*margin-top: 50px;  Adjust for fixed navbar */
            }

            .content1.collapsed {
                margin-left: 0;
            }

            .console {
                background-color: #162447;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 20px;
            }

            .console .command-input {
                background-color: #1a1a2e;
                border: none;
                color: #fff;
                padding: 10px;
                width: 100%;
            }

            .console .command-input:focus {
                outline: none;
            }

            .console .command-output {
                background-color: #1a1a2e;
                padding: 10px;
                height: 200px;
                overflow-y: auto;
                margin-bottom: 10px;
            }

            .console .command-output span {
                display: block;
            }

            .console .buttons {
                display: flex;
                justify-content: space-between;
            }

            .console .buttons button {
                background-color: #e94560;
                border: none;
                padding: 10px 20px;
                color: #fff;
                border-radius: 5px;
            }

            .console .buttons button:hover {
                background-color: #d83450;
            }

            .metrics {
                display: flex;
                justify-content: space-between;
            }

            .metrics .metric {
                /*background-color: #e7e7e7;*/
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                width: 32%;
                box-shadow: 0 0 10px #ccc;
            }

            .metrics .metric h5 {
                color: #e94560;
                margin-bottom: 20px;
            }

            .metrics .metric .chart {
                background-color: #f3f3f3;
                height: 150px;
                border-radius: 5px;
                /*                box-shadow: 0 0 10px #ccc;*/
                border:  solid 1px black;
                padding: 5px;
            }

            .sidebar-title{
                padding-left: 10px;
                font-weight: bold;
            }

            .sidebar a {
                padding: 10px;
            }

            .table-custom {
                width: 100%;
                margin: 20px 0;
                border-collapse: collapse;
            }

            .table-custom table {
                width: 100%;
                border: 1px solid #ddd;
                border-radius: 5px;
                overflow: hidden;
            }

            .table-custom td {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
                color: black;
            }

            .table-custom th {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
                color: #e94560;
            }

            .table-custom th {
                background-color: #f4f4f4;
                font-weight: bold;
            }

            .table-custom tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .table-custom tr:hover {
                background-color: #f1f1f1;
            }

            .table-custom td {
                border-right: 1px solid #ddd;
            }

            .table-custom td:last-child {
                border-right: none;
            }

            .btn button{
                border: none;
                background-color: black;
                color: white;
                transition: ease-in-out 0.2s;
                padding: 10px;
                border-radius: 5px;
            }

            .btn button:hover{
                background-color: white;
                color: black;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            .test p {
                margin-bottom: 2px;
            }

            .input-group {
                margin-bottom: 20px;
            }

            .details-content {
                overflow: hidden;
                height: 0;
                transition: height 0.5s ease;
            }

            .modal-content {
                transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
            }

            .modal.fade .modal-content {
                transform: scale(0.9);
                opacity: 0;
            }

            .modal.show .modal-content {
                transform: scale(1);
                opacity: 1;
            }

        </style>
    </head>

    <body>
        <!-- Header import section -->
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
                        <i  class="fa-solid fa-bars toggle-sidebar-btn" id="toggleSidebarBtn" style="cursor: pointer"></i>
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

        <div class="sidebar" id="sidebar">
            <p class=" sidebar-title">
                FREEZE MANAGEMENT
            </p>
            <a class="active-custom hover" href="#">
                Example
            </a>
            <a class="hover" href="#">
                Example
            </a>
        </div>

        <div class="content1" id="content1">
            <div class="metrics">
                <div class="metric">
                    <h5>
                        Example
                    </h5>
                    <canvas id="memoryChart" class="chart"></canvas>
                </div>
                <div class="metric">
                    <h5>
                        Example
                    </h5>
                    <canvas id="cpuChart" class="chart"></canvas>
                </div>
                <div class="metric">
                    <h5>
                        Example
                    </h5>
                    <canvas id="networkChart" class="chart"></canvas>
                </div>
            </div>
            <div class="table-custom">
                <form class="input-group">
                    <button class="btn btn-outline-dark">Sort</button>
                    <select class="form-select">
                        <option>ASC</option>
                        <option>DSC</option>
                    </select>
                    <select class="form-select">
                        <option>ASC</option>
                        <option>DSC</option>
                    </select>
                    <select class="form-select">
                        <option>ASC</option>
                        <option>DSC</option>
                    </select>
                    <select class="form-select">
                        <option>ASC</option>
                        <option>DSC</option>
                    </select>
                </form>
                <div class="row mb-4 p-3 rounded shadow">
                    <!-- Product Image -->
                    <div class="col-md-3">
                        <img src="${pageContext.request.contextPath}/asset/Image/logotrans2.svg" class="img-fluid rounded" alt="" width="150" height="350"/>
                    </div>

                    <!-- Product Details (Vertically Centered) -->
                    <div class="test col-md-6 d-flex align-items-center">
                        <div>
                            <h5>Name: Hoang Anh</h5>
                            <p>Example</p>
                            <p>Example</p>
                            <p>Example</p>
                            <p>Example</p>
                            <p>Example</p>
                        </div>
                    </div>

                    <!-- Actions (View Details / Remove) -->
                    <div class="col-md-3 d-flex align-items-center">
                        <div class="btn">
                            <a href="javascript:void(0);" onclick="toggleDetails(this);">
                                <button>Detail</button>
                            </a>
                            <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editModal">
                                <button class="editBtn">Edit</button>
                            </a>
                        </div>
                    </div>

                    <!-- Hidden details section -->
                    <div class="details-content" style="height: 0; overflow: hidden; transition: height 0.5s ease; margin-top: 10px;">
                        <div class="row">
                            <div class="col-3">
                                <img src="${pageContext.request.contextPath}/asset/Image/preppy-aesthetic-fashion-collage-desktop-wallpaper-4k.jpg" class="img-fluid rounded" alt="" width="150" height="350"/>
                            </div>
                            <div class="col-6">
                                <h5>Name: Hoang Anh</h5>
                                <p>Example</p>
                                <p>Example</p>
                                <p>Example</p>
                                <p>Example</p>
                                <p>Example</p>
                            </div>
                        </div>
                    </div>

                    <!-- Bootstrap Modal for Editing User Info -->
                    <!-- Modal Structure -->
                    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editModalLabel">Edit User Information</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <!-- Your form for editing user information -->
                                    <form>
                                        <div class="mb-3">
                                            <label for="username" class="form-label">Username</label>
                                            <input type="text" class="form-control" id="username" value="${sessionScope.username}">
                                        </div>
                                        <div class="mb-3">
                                            <label for="email" class="form-label">Email address</label>
                                            <input type="email" class="form-control" id="email" value="${sessionScope.email}">
                                        </div>
                                        <!-- Add more input fields as needed -->
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary">Save changes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            const memoryCtx = document.getElementById('memoryChart').getContext('2d');
            const cpuCtx = document.getElementById('cpuChart').getContext('2d');
            const networkCtx = document.getElementById('networkChart').getContext('2d');

            const memoryChart = new Chart(memoryCtx, {
                type: 'line',
                data: {
                    labels: ['8:19', '8:20', '8:21', '8:22', '8:23', '8:24', '8:25', '8:26', '8:27', '8:28'],
                    datasets: [{
                            label: 'Memory Usage',
                            data: [5000, 10000, 15000, 20000, 25000, 30000, 35000, 30000, 25000, 20000],
                            backgroundColor: 'rgba(233, 69, 96, 0.2)',
                            borderColor: 'rgba(233, 69, 96, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            const cpuChart = new Chart(cpuCtx, {
                type: 'line',
                data: {
                    labels: ['8:19', '8:20', '8:21', '8:22', '8:23', '8:24', '8:25', '8:26', '8:27', '8:28'],
                    datasets: [{
                            label: 'CPU Usage',
                            data: [200, 400, 600, 800, 600, 400, 200, 400, 600, 800],
                            backgroundColor: 'rgba(233, 69, 96, 0.2)',
                            borderColor: 'rgba(233, 69, 96, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            const networkChart = new Chart(networkCtx, {
                type: 'line',
                data: {
                    labels: ['8:19', '8:20', '8:21', '8:22', '8:23', '8:24', '8:25', '8:26', '8:27', '8:28'],
                    datasets: [{
                            label: 'Network Usage',
                            data: [2, 4, 6, 8, 10, 12, 10, 8, 6, 4],
                            backgroundColor: 'rgba(233, 69, 96, 0.2)',
                            borderColor: 'rgba(233, 69, 96, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            function toggleDetails(button) {
                const detailsContent = button.closest('.row').querySelector('.details-content');

                if (detailsContent.style.height === '0px' || detailsContent.style.height === '') {
                    detailsContent.style.height = detailsContent.scrollHeight + 'px';
                } else {
                    detailsContent.style.height = '0px';
                }
            }

            // Example of showing the modal manually using JS (optional)
            function showModal() {
                var modal = new bootstrap.Modal(document.getElementById('editModal'), {
                    keyboard: false
                });
                modal.show();
            }

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
    </body>
</html>
