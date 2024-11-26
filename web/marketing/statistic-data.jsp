<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Statistic Start -->
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
                        <a href="bloglistmanager?type=all">
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
                        </a>
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
<script>
    chartData = ${chart};
    console.log(chartData);
    updateChart(chartData);
</script>