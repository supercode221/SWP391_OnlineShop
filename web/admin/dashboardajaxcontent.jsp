<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="dashboard-main">
    <!--dashboard section display by choosed button above-->
    <div class="dashboard-body">
        <div class="overall-section">
            <div class="overall-main">
                <!--Revenue statistic-->
                <div class="box-main">
                    <div class="statistic-main">
                        <div class="statistic-title">
                            <h5><b>Revenues</b></h5>
                        </div>
                        <div class="statistic-content">
                            <div class="statistic-box">

                                <!--Box for statistic information-->
                                <div class="box">
                                    <div class="box-upper">
                                        <div class="box-content">
                                            <div class="box-title">
                                                <h5>Revenue</h5>
                                                <div class="box-icon">
                                                    <i class="fa-solid fa-money-bill-wave fa-xl"></i>
                                                </div>
                                            </div>
                                            <div class="box-number">
                                                <h4><b><fmt:formatNumber value="${dashboardContent.totalRevenue}" pattern="###,###,###VND"/></b></h4>
                                            </div>
                                            <div class="box-percentage">

                                            </div>
                                        </div>
                                        <div class="box-content">
                                            <div class="box-title">
                                                <h5>Pending Payment</h5>
                                                <div class="box-icon">
                                                    <i class="fa-solid fa-cash-register fa-xl"></i>
                                                </div>
                                            </div>
                                            <div class="box-number">
                                                <h4><b><fmt:formatNumber value="${dashboardContent.pendingPayment}" pattern="###,###,###VND"/></b></h4>
                                            </div>
                                            <div class="box-percentage">

                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-under">
                                        <div class="box-content">
                                            <div class="box-title">
                                                <h5>Refunds</h5>
                                                <div class="box-icon">
                                                    <i class="fa-solid fa-rectangle-xmark fa-xl"></i>
                                                </div>
                                            </div>
                                            <div class="box-number">
                                                <h4><b><fmt:formatNumber value="${dashboardContent.refunds}" pattern="###,###,###VND"/></b></h4>
                                            </div>
                                            <div class="box-percentage">

                                            </div>
                                        </div>
                                        <div class="box-content">
                                            <div class="box-title">
                                                <h5>Average per Order</h5>
                                                <div class="box-icon">
                                                    <i class="fa-solid fa-receipt fa-xl"></i>
                                                </div>
                                            </div>
                                            <div class="box-number">
                                                <h4><b><fmt:formatNumber value="${dashboardContent.avgPerOrder}" pattern="###,###,###VND"/></b></h4>
                                            </div>
                                            <div class="box-percentage">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--End box for statistic information-->

                            </div>
                            <div class="statistic-chart">
                                <div class="chart">
                                    <div class="chart-container">
                                        <div class="chart-title">
                                            <p>Revenue</p>
                                        </div>
                                        <div class="overall-order-chart-main" id="chartRevenue">
                                            <canvas id="overall-revenue-chart"></canvas>
                                        </div>
                                        <div class="filter-chart-section">
                                            <div class="filter-by-year" id="filterYearRevenue">
                                                <select onchange="applyYear(this)" id="selectYearRevenue">
                                                    <c:forEach items="${requestScope.revenueChartDataNotJson}" var="y">
                                                        <option>${y.year}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="filter-by-day">
                                                <button id="default-chart-filter" data-name="revenue" data-filter-type="month" name="chartMonth" value="chartMonth" onclick="applyFilterChart(this)">Month</button>
                                                <button data-name="revenue" data-filter-type="year" name="chartYear" value="chartYear" onclick="applyFilterChart(this)">Year</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--End revenue statistic-->

                <!--Order Statistic-->
                <div class="box-main">
                    <div class="statistic-main">
                        <div class="statistic-title">
                            <h5><b>Bills</b></h5>
                        </div>
                        <div class="statistic-content">
                            <div class="statistic-box">

                                <!--Box for statistic information-->
                                <div class="box" style="display: flex; gap: 20px;">
                                    <div class="box-upper">
                                        <a href="ordermanager?type=all">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Total</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-money-bill-wave fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.totalBills}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </a>
                                        <a href="ordermanager?type=Canceled">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Canceled</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-cash-register fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.canceledBills}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <div class="box-under">
                                        <a href="ordermanager?type=Received">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Success</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-rectangle-xmark fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.successBills}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </a>
                                        <a href="ordermanager?type=OnDelivery">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>On Delivery/ Pending</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-receipt fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.onDeliveryOrPendingBills}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <!--End box for statistic information-->

                            </div>
                        </div>
                    </div>
                </div>
                <!--End order Statistic-->

                <c:if test="${sessionScope.roleId == 2}">
                    <!--Customer statistic-->
                    <div class="box-main">
                        <div class="statistic-main">
                            <div class="statistic-title">
                                <h5><b>Customers</b></h5>
                            </div>
                            <div class="statistic-content">
                                <div class="statistic-box">

                                    <!--Box for statistic information-->
                                    <div class="box" style="display: flex; gap: 20px;">
                                        <div class="box-upper">
                                            <a href="customers">
                                                <div class="box-content">
                                                    <div class="box-title">
                                                        <h5>Total</h5>
                                                        <div class="box-icon">
                                                            <i class="fa-solid fa-money-bill-wave fa-xl"></i>
                                                        </div>
                                                    </div>
                                                    <div class="box-number">
                                                        <h4><b>${dashboardContent.totalCustomer}</b></h4>
                                                    </div>
                                                    <div class="box-percentage">

                                                    </div>
                                                </div>
                                            </a>

                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Newly registered</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-cash-register fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.newlyReg}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>

                                        </div>
                                        <div class="box-under">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Newly bought</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-rectangle-xmark fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.newlyBought}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                            <form action="customers" method="post">
                                                <input type="hidden" name="action" value="filter">
                                                <input type="hidden" name="status" value="banned">
                                                <button style="background-color: transparent; border: none;">
                                                    <div class="box-content">
                                                        <div class="box-title">
                                                            <h5>Banned</h5>
                                                            <div class="box-icon">
                                                                <i class="fa-solid fa-receipt fa-xl"></i>
                                                            </div>
                                                        </div>
                                                        <div class="box-number">
                                                            <h4 style="text-align: left !important;"><b>${dashboardContent.bannedCustomer}</b></h4>
                                                        </div>
                                                        <div class="box-percentage">

                                                        </div>
                                                    </div>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                    <!--End box for statistic information-->

                                </div>
                            </div>
                        </div>
                    </div>
                    <!--End customer statistic-->

                    <!--Feedback statistic-->
                    <div class="box-main">
                        <div class="statistic-main">
                            <div class="statistic-title">
                                <h5><b>Feedbacks</b></h5>
                            </div>
                            <div class="statistic-content">
                                <div class="statistic-box">

                                    <!--Box for statistic information-->
                                    <div class="box" style="display: flex; gap: 20px;">
                                        <div class="box-upper">
                                            <a href="feedbackservlet">
                                                <div class="box-content">
                                                    <div class="box-title">
                                                        <h5>Total product feedback</h5>
                                                        <div class="box-icon">
                                                            <i class="fa-solid fa-money-bill-wave fa-xl"></i>
                                                        </div>
                                                    </div>
                                                    <div class="box-number">
                                                        <h4><b>${dashboardContent.totalProductFeedback}</b></h4>
                                                    </div>
                                                    <div class="box-percentage">

                                                    </div>
                                                </div>
                                            </a>
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Average star</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-cash-register fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.avgProductStar}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </div>
                                        <div class="box-under">
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Total shop feedback</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-cash-register fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.totalShopFeedback}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                            <div class="box-content">
                                                <div class="box-title">
                                                    <h5>Average star</h5>
                                                    <div class="box-icon">
                                                        <i class="fa-solid fa-rectangle-xmark fa-xl"></i>
                                                    </div>
                                                </div>
                                                <div class="box-number">
                                                    <h4><b>${dashboardContent.avgShopStar}</b></h4>
                                                </div>
                                                <div class="box-percentage">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--End box for statistic information-->

                                </div>
                            </div>
                        </div>
                    </div>
                    <!--End feedback statistic-->
                </c:if>
            </div>
        </div>
    </div>
</div>


<script id="script-chart">
    var dataForRevenueChart = ${revenueChartData};
    var dataForPendingChart = ${pendingChartData};
    var dataForRefundChart = ${canceledChartData};
    var dataForAVGChart = ${avgChartData};

    $(document).ready(function () {
        const defaultLoadChartFilter = $("#default-chart-filter");
        defaultLoadChartFilter.trigger('click');

        const defaultLoadChartRevenue = $("#selectYearRevenue");
        defaultLoadChartRevenue.trigger('change');
    });

    function applyYear(selected) {
        $("#chartRevenue")
                .empty()
                .append("<canvas id='overall-revenue-chart'></canvas>");



        const filteredRevenueData = dataForRevenueChart.filter(entry => entry.year == selected.value);

        const revenueData = filteredRevenueData.map(entry => {
            return {month: entry.month, revenue: entry.revenue};
        });
        //

        const filteredPendingData = dataForPendingChart.filter(entry => entry.year == selected.value);

        const pendingData = filteredPendingData.map(entry => {
            return {month: entry.month, revenue: entry.revenue};
        });
        //

        const filteredRefundData = dataForRefundChart.filter(entry => entry.year == selected.value);

        const refundData = filteredRefundData.map(entry => {
            return {month: entry.month, revenue: entry.revenue};
        });
        //

        const filteredAVGData = dataForAVGChart.filter(entry => entry.year == selected.value);

        const avgData = filteredAVGData.map(entry => {
            return {month: entry.month, revenue: entry.revenue};
        });

        // Initialize the chart with a basic configuration on the target canvas
        const ctx = document.getElementById("overall-revenue-chart").getContext("2d");

        let overallRevenueChart = new Chart(ctx, {
            type: 'line',
            data: {labels: [], datasets: []}, // Empty initially, will be updated in loadCharts
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        // Pass the chart instance and data to loadCharts
        loadCharts(overallRevenueChart, revenueData, pendingData, refundData, avgData);
    }

    function applyFilterChart(selected) {
        // Clear previous selections
        const buttons = document.querySelectorAll('.filter-chart-section .filter-by-day button');

        buttons.forEach(btn => btn.classList.remove('selected'));

        // Add selected class or keep input value
        if (selected.tagName === 'BUTTON') {
            selected.classList.add('selected');
            console.log("chart:" + selected.value);
        } else {
            selected.value = selected.value || "";
        }

        if (selected.getAttribute("data-filter-type") === 'month') {
            if (selected.getAttribute("data-name") === 'revenue') {
                // Show the correct filter and hide others
                $("#filterYearRevenue").css("display", "block");

                const defaultLoadChartRevenue = $("#selectYearRevenue");
                defaultLoadChartRevenue.trigger('change');
            }
        }

        if (selected.getAttribute("data-filter-type") === 'year') {
            if (selected.getAttribute("data-name") === 'revenue') {
                $("#filterYearRevenue").css("display", "none");

                // Clear and re-add the chart canvas
                $("#chartRevenue")
                        .empty()
                        .append("<canvas id='overall-revenue-chart'></canvas>");

                // Aggregate data for each type (Revenue, Refund, Pending, AVG)
                const yearlyRevenueData = dataForRevenueChart.reduce((acc, entry) => {
                    acc[entry.year] = (acc[entry.year] || 0) + entry.revenue;
                    return acc;
                }, {});

                const yearlyRefundData = dataForRefundChart.reduce((acc, entry) => {
                    acc[entry.year] = (acc[entry.year] || 0) + entry.revenue;
                    return acc;
                }, {});

                const yearlyPendingData = dataForPendingChart.reduce((acc, entry) => {
                    acc[entry.year] = (acc[entry.year] || 0) + entry.revenue;
                    return acc;
                }, {});

                const yearlyAVGData = dataForAVGChart.reduce((acc, entry) => {
                    acc[entry.year] = (acc[entry.year] || 0) + entry.revenue;
                    return acc;
                }, {});

                // Convert each aggregated object to an array
                const revenueData = Object.keys(yearlyRevenueData).map(year => ({year: parseInt(year), revenue: yearlyRevenueData[year]}));
                const refundData = Object.keys(yearlyRefundData).map(year => ({year: parseInt(year), revenue: yearlyRefundData[year]}));
                const pendingData = Object.keys(yearlyPendingData).map(year => ({year: parseInt(year), revenue: yearlyPendingData[year]}));
                const avgData = Object.keys(yearlyAVGData).map(year => ({year: parseInt(year), revenue: yearlyAVGData[year]}));

                // Define the labels (unique years)
                const billYear = Array.from(new Set([...revenueData, ...refundData, ...pendingData, ...avgData].map(entry => entry.year))).sort();

                // Initialize the chart
                const ctx = document.getElementById("overall-revenue-chart").getContext("2d");
                let overallRevenueChart = new Chart(ctx, {
                    type: 'bar',
                    data: {labels: [], datasets: []},
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });

                // Load data into the chart
                loadChartYears(overallRevenueChart, revenueData, refundData, pendingData, avgData, billYear);
            }
        }
    }

    function loadCharts(chart, revenuedata, pendingdata, canceleddata, avgdata) {
        // Create an array with revenue for each month
        const monthlyRevenue = Array(12).fill(0);

        // Populate the array with revenue data from the original data
        revenuedata.forEach(entry => {
            monthlyRevenue[entry.month - 1] = entry.revenue; // Adjust for 0-based index
        });

        // Create an array with revenue for each month
        const monthlyPending = Array(12).fill(0);

        // Populate the array with revenue data from the original data
        pendingdata.forEach(entry => {
            monthlyPending[entry.month - 1] = entry.revenue; // Adjust for 0-based index
        });

        // Create an array with revenue for each month
        const monthlyRefund = Array(12).fill(0);

        // Populate the array with revenue data from the original data
        canceleddata.forEach(entry => {
            monthlyRefund[entry.month - 1] = entry.revenue; // Adjust for 0-based index
        });

        // Create an array with revenue for each month
        const monthlyAvg = Array(12).fill(0);

        // Populate the array with revenue data from the original data
        avgdata.forEach(entry => {
            monthlyAvg[entry.month - 1] = entry.revenue; // Adjust for 0-based index
        });

        // Update chart data and labels
        chart.data.labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
        chart.data.datasets = [
            {
                label: 'Revenue',
                data: monthlyRevenue,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            },
            {
                label: 'Pending',
                data: monthlyPending,
                backgroundColor: 'rgba(255, 159, 64, 0.2)', // Orange color for Pending
                borderColor: 'rgba(255, 159, 64, 1)',
                borderWidth: 1
            },
            {
                label: 'Refund',
                data: monthlyRefund,
                backgroundColor: 'rgba(54, 162, 235, 0.2)', // Blue color for Refund
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            },
            {
                label: 'AVG',
                data: monthlyAvg,
                backgroundColor: 'rgba(153, 102, 255, 0.2)', // Purple color for AVG
                borderColor: 'rgba(153, 102, 255, 1)',
                borderWidth: 1
            }
        ];

        // Update the chart to reflect the new data
        chart.update();
    }

    // loadChartYears function to set up the datasets and labels
    function loadChartYears(chart, dataRevenue, dataRefund, dataPending, dataAVG, billYear) {
        // Extract revenue data from each dataset
        const revenueData = billYear.map(year => (dataRevenue.find(d => d.year === year) || {revenue: 0}).revenue);
        const refundData = billYear.map(year => (dataRefund.find(d => d.year === year) || {revenue: 0}).revenue);
        const pendingData = billYear.map(year => (dataPending.find(d => d.year === year) || {revenue: 0}).revenue);
        const avgData = billYear.map(year => (dataAVG.find(d => d.year === year) || {revenue: 0}).revenue);

        // Update chart data and labels
        chart.data.labels = billYear;
        chart.data.datasets = [
            {
                label: 'Revenue',
                data: revenueData,
                backgroundColor: 'rgba(75, 192, 192, 1)', // Teal background
                borderColor: 'rgba(0, 128, 128, 1)', // Darker teal border
                borderWidth: 1
            },
            {
                label: 'Pending',
                data: pendingData,
                backgroundColor: 'rgba(255, 159, 64, 1)', // Orange background
                borderColor: 'rgba(204, 102, 0, 1)', // Darker orange border
                borderWidth: 1
            },
            {
                label: 'Refund',
                data: refundData,
                backgroundColor: 'rgba(54, 162, 235, 1)', // Blue background
                borderColor: 'rgba(0, 102, 204, 1)', // Darker blue border
                borderWidth: 1
            },
            {
                label: 'AVG',
                data: avgData,
                backgroundColor: 'rgba(153, 102, 255, 1)', // Purple background
                borderColor: 'rgba(102, 51, 204, 1)', // Darker purple border
                borderWidth: 1
            }
        ];

        // Update the chart to reflect the new data
        chart.update();
    }

</script>