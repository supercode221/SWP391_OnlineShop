<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:if test="${sessionScope.roleId == 2}">Admin Dashboard</c:if><c:if test="${sessionScope.roleId == 4 || sessionScope.roleId == 6}">Sale Dashboard</c:if></title>
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

            .filter-by-datepicker .form button {
                border: none;
                background-color: black;
                color: white;
                font-weight: bold;
                font-size: 14px;
                transition: ease-in-out 0.2s;
            }

            .filter-by-datepicker .form button:hover {
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
                <div class="filter-by-day">
                    <button name="7day" value="7days" onclick="applyFilter(this)">7 day</button>
                    <button name="30day" value="30days" onclick="applyFilter(this)">30 day</button>
                    <button id="default-filter" name="alltime" value="all" onclick="applyFilter(this)">All Time</button>
                </div>
                <div class="filter-by-datepicker">
                    <input id="day" data-type-filter="date" type="date" onchange="applyFilter(this)" max="">
                    <input id="month" data-type-filter="month" type="month" onchange="applyFilter(this)" max="">
                </div>
                <div class="divide"></div>
                <div class="filter-by-datepicker">
                    <div class="form">
                        From: <input class="fromDate" id="day1" type="date" name="fromDate"> To: <input class="toDate" id="day2" type="date" name="toDate">
                        <button onclick="doFilter()">Check</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.min.js" integrity="sha512-L0Shl7nXXzIlBSUUPpxrokqq4ojqgZFQczTYlGjzONGTDAcLremjwaWv5A+EDLnxhQzY5xUZPWLOLqYRkY0Cbw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script>
                            $(document).ready(function () {
                                var activeSideBar = $("#admin-dashboard");
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
                                    toDateInput.max = fromDateInput.value;

                                    // Optional: Clear any previously selected toDate if it is now invalid
                                    if (toDateInput.value && toDateInput.value < fromDateInput.value) {
                                        toDateInput.value = "";
                                    }
                                });

                                const defaultLoadAllFilter = $("#default-filter");
                                defaultLoadAllFilter.addClass('selected');

                                // Manually trigger the click event if defaultLoad is a button
                                defaultLoadAllFilter.trigger('click');
                            });


                            const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
                            const sidebar = document.getElementById('sidebar');
                            const content = document.getElementById('content');
                            const content1 = document.getElementById('content1');
                            const navbar = document.getElementById('navbar');

                            function doFilter() {
                                let dayFrom = $("#day1").val();
                                let dayTo = $("#day2").val();

                                console.log(dayFrom + " " + dayTo);

                                const data = {
                                    action: "fromToFilter",
                                    filterType: "fromTo",
                                    fromDate: dayFrom,
                                    toDate: dayTo
                                };

                                makeAjaxRequest(data);
                            }

                            toggleSidebarBtn.addEventListener('click', () => {
                                sidebar.classList.toggle('collapsed');
                                content.classList.toggle('collapsed');
                                content1.classList.toggle('collapsed');
                                navbar.classList.toggle('collapsed');
                                toggleSidebarBtn.classList.toggle('collapsed');
                            });

                            function applyFilter(selected) {
                                // Get all filter buttons
                                const buttons = document.querySelectorAll('.box-filter .filter-by-day button');

                                // Clear selections if a button is clicked
                                if (selected.tagName === 'BUTTON') {
                                    buttons.forEach(btn => {
                                        // Clear selection on all buttons except the clicked one
                                        if (btn !== selected) {
                                            btn.classList.remove('selected');
                                        }
                                    });

                                    // Clear inputs when a button is clicked
                                    const inputs = document.querySelectorAll('.box-filter .filter-by-datepicker input');
                                    inputs.forEach(input => {
                                        if (input.value) {
                                            input.value = ""; // Clear the input value
                                            input.classList.remove('filter-by-month-selected');
                                        }
                                    });

                                    selected.classList.add('selected'); // Highlight the selected button
                                    console.log(selected.value); // Log the selected button value only

                                    const data = {
                                        action: "clickToAjax",
                                        filterValue: selected.value,
                                        filterType: null
                                    };

                                    makeAjaxRequest(data);

                                } else if (selected.tagName === 'INPUT') {
                                    // Clear button selections if an input is changed
                                    const inputs = document.querySelectorAll('.box-filter .filter-by-datepicker input');
                                    inputs.forEach(input => {
                                        if (input !== selected && input.value) {
                                            input.value = ""; // Clear the other input values
                                            input.classList.remove('filter-by-month-selected');
                                        }
                                    });

                                    // Clear button selections
                                    buttons.forEach(btn => btn.classList.remove('selected'));

                                    // Retrieve the data-type-filter value
                                    const filterType = selected.getAttribute('data-type-filter'); // Use native method
                                    console.log(filterType); // Log the selected input's data-type-filter value

                                    selected.classList.add("filter-by-month-selected");

                                    // Log the selected input value only if it is not empty
                                    if (selected.value) {
                                        console.log(selected.value); // Log the selected input value
                                    }

                                    const data = {
                                        action: "clickToAjax",
                                        filterValue: filterType,
                                        filterType: selected.value
                                    };

                                    makeAjaxRequest(data);
                                }
                            }

                            function makeAjaxRequest(data) {
                                $.ajax({
                                    url: "${pageContext.request.contextPath}/admindashboard",
                                    type: "POST",
                                    data: data,
                                    success: function (response) {
                                        if ($(".dashboard-main").length) { // Check if any elements with the class 'dashboard-main' exist
                                            $(".dashboard-main").remove(); // Remove all elements with the class 'dashboard-main'
                                        }

                                        if ($("#script-chart").length) {
                                            $("#script-chart").remove();
                                        }

                                        $("#content1").append(response);
//                                    overallOrderChart.update();
                                    },
                                    error: function (xhr, status, error) {
                                        console.log("Error: " + error);
                                    }
                                });
                            }
        </script>
    </body>
</html>

</body>
</html>
