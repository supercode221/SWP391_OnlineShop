<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Marketing Dashboard</title>
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

            .box {
                margin-top: 20px;
                margin-left: 20px;
            }

            .box-upper, .box-under {
                display: flex;
                gap: 20px;
                margin-bottom: 20px;
            }

            .box-content {
                width: 325px;
                display: grid;
                /*justify-items: center;*/
                padding: 15px 30px;
                /*box-shadow: 0 0 15px #ccc;*/
                border: solid 1px black;
                gap: 10px;
                border-radius: 10px;
                transform: scale(1);
                transition: ease-in-out 0.2s;
            }

            .box-content:hover {
                transform: scale(1.005);
                box-shadow: 0 0 10px #ccc;
                cursor: pointer;
            }

            .box-title {
                display: flex;
                align-items: center;
                justify-content: space-between
            }

            .box-title .box-icon {
                background: black;
                width: 50px;
                height: 50px;
                align-items: center;
                justify-content: center;
                display: flex;
                border-radius: 50%;
            }

            .box-title .box-icon i {
                color: white;
            }

            /*            .box-percentage {
                            background-color: #eee;
                            width: fit-content;
                            padding: 2px 5px;
                            border-radius: 15%;
                        }*/

            .chart {
                padding: 20px;
            }

            .chart-title {
                font-weight: bold;
                color: black;
            }

            .chart-container {
                width: 750px;
                /*box-shadow: 0 0 10px #ccc;*/
                padding: 20px 10px 10px 20px;
                border-radius: 10px;
                border: solid 1px black;
            }

            .filter-chart-section {
                display: flex;
                align-items: center;
                justify-content: end;
                gap: 7px;
                margin: 10px;
            }

            .filter-by-year select{
                padding: 2px 10px;
            }

            .filter-by-year {
                display: none;
            }

            .filter-by-day button {
                border: none;
                font-weight: bold;
                color: grey;
                font-size: 13px;
                padding: 2px 4px;
                margin: 0 2px;
            }

            .filter-by-datepicker input, .filter-by-month input {
                font-size: 13px;
                border: solid 1px grey;
                color: grey;
                font-weight: bold;
                padding: 1px 4px;
            }

            .filter-by-month-selected {
                font-size: 13px !important;
                border: solid 3px black !important;
                color: black !important;
                font-weight: bold !important;
                padding: 1px 4px !important;
            }

            .box-filter {
                display: flex;
                align-items: center;
                gap: 10px;
                justify-content: center;
            }

            .loader {
                border: 4px solid #f3f3f3; /* Light grey */
                border-top: 4px solid #3498db; /* Blue */
                border-radius: 50%;
                width: 30px;
                height: 30px;
                animation: spin 1s linear infinite;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                /*                background-color: #ccc;
                                opacity: 0.7;*/
            }

            @keyframes spin {
                0% {
                    transform: rotate(0deg);
                }
                100% {
                    transform: rotate(360deg);
                }
            }

            .selected {
                background-color: black;
                color: #fff !important  ;
            }

            .box-number {
                margin-bottom: 10px;
            }

            .filter-by-datepicker form button {
                border: none;
                background-color: black;
                color: white;
                font-weight: bold;
                font-size: 14px;
                transition: ease-in-out 0.2s;
            }

            .filter-by-datepicker form button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
            }

            .divide {
                height: 20px;
                background-color: gray;
                width: 2px;
            }

            .statistic-content {
                display: flex;
                align-items: center;
                justify-content: space-around;
            }

            .statistic-title {
                /*background-color: black;*/
                width: fit-content;
                padding: 10px;
                /*color: white;*/
                border-radius: 10px;
            }

            .box-main {
                background-color: white;
                padding: 20px;
                margin-top: 20px;
                box-shadow: 0 0 10px #ccc;
                border-radius: 10px;
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
                <p class="fs-5"><b>Statistic</b></p>
            </div>

            <div class="box-filter">
                <div class="filter-by-datepicker">
                    <form action="#">
                        From: <input class="fromDate" id="day1" type="date" name="fromDate" value="${fromDate}"> 
                        To: <input class="toDate" id="day2" type="date" name="toDate" value="${toDate}">
                        <button type="submit" id="check-btn">Check</button>
                    </form>
                </div>
            </div>

            <div id="statistic-data">
                <div class="box-main">
                    <div class="statistic-main">

                        <!-- Label -->
                        <div class="statistic-title" id="statistic-title">
                            <h5><b>Statistic</b></h5>
                        </div>

                        <!-- Data -->
                        <div class="statistic-content">

                            <!--Box for statistic information-->
                            <div class="statistic-box">
                                <div class="box" id="statistic-list">
                                    <div class="box-upper">
                                        <div class="box-content">
                                            <div class="box-title">
                                                <h5>Posts</h5>
                                                <div class="box-icon">
                                                    <i class="fa-solid fa-receipt fa-xl"></i>
                                                </div>
                                            </div>
                                            <div class="box-number">
                                                <h4><b><fmt:formatNumber value="${statistic.postsNumber}" pattern="###,###,###"/></b></h4>
                                            </div>
                                            <div class="box-percentage">

                                            </div>
                                        </div>
                                        <a href="productlistmanager?type=all">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Products</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-box-open fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b><fmt:formatNumber value="${statistic.productsNumber}" pattern="###,###,###"/></b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </a>
                                    </div>

                                    <div class="box-upper">
                                        <a href="customers">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Customers</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-user fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b><fmt:formatNumber value="${statistic.customersNumber}" pattern="###,###,###"/></b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </a>
                                        <a href="feedbackservlet">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Feedbacks</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-receipt fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b><fmt:formatNumber value="${statistic.feedbacksNumber}" pattern="###,###,###"/></b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <!--End box for statistic information-->

                            <div class="statistic-chart">
                                <div class="chart">
                                    <div class="chart-container" id="chart-container">
                                        <div class="chart-title" id="chart-title">
                                            <p class="text-capitalize">daily statistical chart</p>
                                        </div>
                                        <div id="chart-box">
                                            <canvas id="chart">

                                            </canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--End statistic-->

            </div>
        </div>

        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <!-- Date Adapter for Chart js -->
        <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.min.js" integrity="sha512-L0Shl7nXXzIlBSUUPpxrokqq4ojqgZFQczTYlGjzONGTDAcLremjwaWv5A+EDLnxhQzY5xUZPWLOLqYRkY0Cbw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script>

            let chart;
            let chartData;

            $(document).ready(function () {

                chartData = ${chart};
                updateChart(chartData);

                var activeSideBar = $("#marketing-dashboard");
                activeSideBar.addClass("active-custom");

                const today = new Date();
                const formattedDate = today.toISOString().split("T")[0];
                const formattedMonth = today.toISOString().slice(0, 7);

                $("#day").attr("max", formattedDate);
                $("#day1").attr("max", formattedDate);
                $("#day2").attr("max", formattedDate);
                $("#month").attr("max", formattedMonth);

                const fromDateInput = document.getElementById("day1");
                const toDateInput = document.getElementById("day2");

                fromDateInput.addEventListener("change", function () {

                    // Set the minimum date for toDate to be the same as fromDate
                    toDateInput.min = fromDateInput.value;

                    // Optional: Clear any previously selected toDate if it is now invalid
                    if (toDateInput.value && toDateInput.value < fromDateInput.value) {
                        toDateInput.value = "";
                    }
                });

                toDateInput.addEventListener("change", function () {

                    // Set the minimum date for toDate to be the same as fromDate
                    fromDateInput.max = toDateInput.value;

                    // Optional: Clear any previously selected toDate if it is now invalid
                    if (fromDateInput.value && fromDateInput.value > toDateInput.value) {
                        fromDateInput.value = "";
                    }
                });

                $('#check-btn').on('click', function (e) {
                    e.preventDefault(); // Ngừng form gửi tự động

                    // Lấy giá trị từ các ô input ngày
                    var fromDate = $('#day1').val();
                    var toDate = $('#day2').val();

                    // Kiểm tra ngày
                    if (!fromDate || !toDate) {
                        alert('Please select both dates.');
                        return;
                    }

                    if (new Date(fromDate) > new Date(toDate)) {
                        alert('From date must be earlier than To date.');
                        return;
                    }

                    // Gửi yêu cầu AJAX
                    $.ajax({
                        url: "${pageContext.request.contextPath}/marketingdashboard", // Đổi thành URL API của bạn
                        method: 'POST',
                        data: {
                            action: 'load-statistic-data',
                            fromDate: fromDate,
                            toDate: toDate
                        },
                        success: function (response) {
                            // Cập nhật lại #statistic-data với dữ liệu mới
                            $('#statistic-data').empty();
                            $('#statistic-data').append(response);
                        },
                        error: function () {
                            alert('Error occurred while fetching data 1.');
                        }
                    });
                });

            });

            function updateChart(chartData) {
                $("#chart-box")
                        .empty()
                        .append("<canvas id='chart'> </canvas>");
                const ctx = document.getElementById('chart').getContext('2d');
                chart = new Chart(ctx, {
                    type: 'line', // Hoặc kiểu khác tùy thuộc vào yêu cầu
                    data: chartData,
                    options: {
                        responsive: true,
                        scales: {
                            x: {
                                type: 'time',
                                time: {
                                    unit: 'day'
                                }
                            }
                        }
                    }
                });
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