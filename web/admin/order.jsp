<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:if test="${sessionScope.roleId != 7}">Bills Manager</c:if><c:if test="${sessionScope.roleId == 7}">Deliveries</c:if></title>
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

            .search-section {
                position: relative;
                width: 100%;
                max-width: 300px; /* Adjust as needed */
            }

            .search-section input[type="text"] {
                width: 100%;
                padding: 10px 15px 10px 35px;
                border: 1px solid #ccc;
                border-radius: 5px;
                outline: none;
                font-size: 1em;
                transition: box-shadow 0.2s ease;
            }

            .search-section i {
                position: absolute;
                left: 10px;
                top: 50%;
                transform: translateY(-50%);
                color: #888;
                font-size: 0.9em;
            }

            .search-section input[type="text"]:focus {
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
                border-color: #333;
            }

            .filter-section {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            .sort-section {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .Pending button{
                border: none;
                background-color: red;
                color: white;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
            }

            .Pending button:hover {
                background-color: white;
                color: red;
                box-shadow: inset 0 0 0 1px red;
            }

            .Canceled button {
                border: none;
                background-color: red;
                color: white;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
            }

            .Canceled button:hover {
                background-color: white;
                color: red;
                box-shadow: inset 0 0 0 1px red;
            }

            .OnDelivery button {
                border: none;
                background-color: Blue;
                color: white;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
            }

            .OnDelivery button:hover {
                background-color: white;
                color: Blue;
                box-shadow: inset 0 0 0 1px Blue;
            }

            .ReShip button {
                border: none;
                background-color: #F6B2B2;
                color: white;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
            }

            .ReShip button:hover {
                background-color: white;
                color: #F6B2B2;
                box-shadow: inset 0 0 0 1px #F6B2B2;
            }

            .Comfirmed button {
                border: none;
                background-color: #ffc107;
                color: white;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
            }

            .Comfirmed button:hover {
                background-color: white;
                color: #ffc107;
                box-shadow: inset 0 0 0 1px #ffc107;
            }

            .Received button {
                border: none;
                background-color: #33cc00;
                color: white;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
            }

            .Received button:hover {
                background-color: white;
                color: #33cc00;
                box-shadow: inset 0 0 0 1px #33cc00;
            }

            .sort-main {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .divide {
                background-color: grey;
                height: 27px;
                border: solid 1px grey;
            }

            .sort-date input {
                border: solid 0.1px grey;
                padding: 1px 5px;
                border-radius: 7px;
            }

            .sort-date button {
                border: none;
                color: white;
                background-color: #33cc00;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
                padding: 2px 5px;
            }

            .sort-date button:hover {
                color: #33cc00;
                background-color: white;
                box-shadow: inset 0 0 0 1px #33cc00;
            }

            .sort-date a {
                border: none;
                color: white;
                background-color: red;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
                padding: 6px 5px;
            }

            .sort-date a:hover {
                color: red;
                background-color: white;
                box-shadow: inset 0 0 0 1px red;
            }

            .item-card{
                display: flex;
                align-items: center;
                justify-content: space-around;
                border-left: solid 0.1px #ccc;
                border-right: solid 0.1px #ccc;
                padding: 20px 10px;
            }

            .item-product-card{
                display: flex;
                align-items: center;
                justify-content: space-around;
                border: solid 0.1px black;
                padding: 10px;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                margin-bottom: 10px;
                background-color: white;
            }

            .item-product-card:hover {
                transform: scale(1.01);
                box-shadow: 0 0 10px #aaa;
            }

            .order-wrapper {
                margin: 20px 0 20px 0;
                transition: ease-in-out 0.1s;
                transform: scale(1);
            }

            .order-wrapper:hover {
                transform: scale(1.005);
                box-shadow: 0 0 10px #aaa;
                border-radius: 10px;
            }

            .order-header {
                border-top-left-radius: 10px;
                border-top-right-radius: 10px;
            }

            .order-footer {
                background-color: #e0e0e0;
                cursor: pointer;
            }

            .order-footer:hover {
                background-color: #d2d2d2;
            }

            .detail-information {
                display: flex;
                gap: 200px;
                justify-content: center;
            }

            .detail-information p, .detail-information label {
                margin: 5px 0;
                font-size: 20px;
            }

            #sale-select, #status-select {
                padding: 3px;
                width: 200px;
                border: solid 1px #ccc;
            }

            /*            .customer-detail-upper {
                            display: flex;
                            align-items: center;
                            gap: 20px;
                        }*/

            .detail-item .detail-item-wrapper .title {
                margin: 10px;
            }

            .detail-item-main {
                background-color: #eee;
                padding: 20px;
                border-radius: 10px;
                max-height: 465px; /* Adjust height to fit 4 items */
                overflow-y: auto; /* Allows vertical scrolling */
            }

            /* Hide scrollbar for WebKit browsers */
            body::-webkit-scrollbar {
                display: none;
            }

            .order-details-wrapper {
                border-bottom: 1px solid #ccc;
                border-left: 1px solid #ccc;
                border-right: 1px solid #ccc;
                border-bottom-left-radius: 10px;
                border-bottom-right-radius: 10px;
                padding: 10px;
            }

            .order-details {
                display: none;
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

            .button-detail button {
                display: none;
                border: none;
                padding: 5px 10px;
                border-radius: 10px;
                background-color: black;
                color: white;
                transition: ease-in-out 0.2s;
            }

            .button-detail button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
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

            .sort-box {
                display: flex;
                gap: 5px;
            }

            #all {
                background-color: black;
                color: white;
                padding: 3px 10px;
                border-radius: 10px;
                box-shadow: 0 0 5px #ccc;
                transition: ease-in-out 0.2s;
            }

            #all:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px #333;
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
            <div class="filter-section">
                <div class="sort-section">
                    <div class="filter-title">
                        <h6 style="color: gray;"><b>Filter-by: </b></h6>
                    </div>
                    <div class="sort-main d-flex">
                        <div class="sort-box">
                            <c:forEach items="${status}" var="s">   
                                <form action="ordermanager" method="GET">
                                    <input type="hidden" name="type" value="${s}">
                                    <div class="
                                         <c:if test="${s == 'Received'}">Received</c:if>
                                         <c:if test="${s == 'Canceled'}">Canceled</c:if>
                                         <c:if test="${s == 'OnDelivery'}">OnDelivery</c:if>
                                         <c:if test="${s == 'PaymentPending'}">Canceled</c:if>
                                         <c:if test="${s == 'ReShip'}">ReShip</c:if>
                                         <c:if test="${s == 'Comfirmed'}">Comfirmed</c:if>
                                         <c:if test="${s == 'CanceledAfterReShip'}">Canceled</c:if>
                                         <c:if test="${s == 'Pending'}">Pending</c:if>">
                                        <button>${s}</button>    
                                    </div>
                                </form>
                            </c:forEach>
                        </div>
                        <div class="filter-time">
                            <form action="ordermanager" method="GET">
                                <div class="sort-date">
                                    <input type="hidden" name="type" value="date">
                                    <input type="date" name="fromDate" id="fromDate" value="${fromDate}" required=""> to <input type="date" name="toDate" id="toDate" value="${toDate}" required="">
                                    <button type="submit">Filter</button>
                                    <c:if test="${!fromDate.isEmpty() && fromDate != null && !toDate.isEmpty() && toDate != null}">
                                        <a href="ordermanager?type=all">Clear</a>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <form action="ordermanager">
                    <div class="search-section">
                        <input type="hidden" name="type" value="search">
                        <input type="text" placeholder="Search by customer name" name="search" value="${search}">
                        <i class="fas fa-search"></i>
                    </div>
                </form>
            </div>
            <a id="all" href="ordermanager?type=all">All</a>
            <div class="order-section" id="order-section">
                <c:if test="${not empty billList}">
                    <c:forEach items="${billList}" var="b">
                        <div class="order-section-god">
                            <div class="order-main">
                                <div class="order-wrapper">
                                    <div style="font-size: 13px;" class="order-header bg-dark text-white text-center p-1">
                                        <b>1st Freeze</b>
                                    </div>
                                    <div class="order-body">
                                        <div class="item-card">
                                            <p>ID: <b>${b.id}</b></p>
                                            <p>Date: <b>${b.publishDate}</b></p>
                                            <p>Customer Name: <b>${b.customer.fullName}</b></p>
                                            <p>Total Price: <b><fmt:formatNumber value="${b.totalPrice}" pattern="###,###,###VND" /></b></p>
                                            <p id="status-bill-${b.id}"
                                               <c:choose>
                                                   <c:when test="${b.status.equalsIgnoreCase('Received')}">
                                                       style="color: green;"
                                                   </c:when>
                                                   <c:when test="${b.status.equalsIgnoreCase('OnDelivery')}">
                                                       style="color: Blue;"
                                                   </c:when>
                                                   <c:when test="${b.status.equalsIgnoreCase('ReShip')}">
                                                       style="color: #F6B2B2;"
                                                   </c:when>
                                                   <c:when test="${b.status.equalsIgnoreCase('Comfirmed')}">
                                                       style="color: #ffc107;"
                                                   </c:when>
                                                   <c:otherwise>
                                                       style="color: red;"
                                                   </c:otherwise>
                                               </c:choose>
                                               ><b>${b.status}</b></p>
                                        </div>
                                    </div>
                                    <div class="order-footer text-dark text-center p-1">
                                        <p style="font-size: 14px;">View Details</p>
                                    </div>
                                </div>
                            </div>
                            <div class="order-details">
                                <div class="order-details-wrapper">
                                    <div class="detail-information">
                                        <div class="process-detail">
                                            <div class="title mb-2 mt-2">
                                                <h5><b>Process</b></h5>
                                            </div>
                                            <div class="option-main">
                                                <table>
                                                    <div class="option">
                                                        <tr>
                                                            <td><label for="sale-select">Saler: </label></td>
                                                            <td>
                                                                <select id="sale-select" <c:if test="${sessionScope.roleId == 7 || 
                                                                                                       sessionScope.roleId == 4 ||
                                                                                                       b.status.equalsIgnoreCase('Received') || 
                                                                                                       b.status.equalsIgnoreCase('Canceled') || 
                                                                                                       b.status.equalsIgnoreCase('OnDelivery') || 
                                                                                                       b.status.equalsIgnoreCase('ReShip') || 
                                                                                                       b.status.equalsIgnoreCase('CanceledAfterReShip') || 
                                                                                                       b.status.equalsIgnoreCase('PaymentPending')}">disabled=""</c:if>>
                                                                      <c:forEach items="${saler}" var="s">
                                                                          <option id="saleId" data-sale-email="${s.email}" data-sale-id="${s.id}" <c:if test="${b.saler.name == s.name}">selected</c:if>>${s.name}</option>                                                                    
                                                                      </c:forEach>
                                                                </select>
                                                            </td>    
                                                        </tr>
                                                    </div>
                                                    <div class="option">
                                                        <tr>
                                                            <td><label for="status-select">Status: </label></td>
                                                            <td>
                                                                <select id="status-select" 
                                                                        <c:if test="${b.status.equalsIgnoreCase('Received') || 
                                                                                      b.status.equalsIgnoreCase('Canceled') || 
                                                                                      b.status.equalsIgnoreCase('PaymentPending') || 
                                                                                      b.status.equalsIgnoreCase('CanceledAfterReShip')}">disabled=""</c:if>
                                                                        <c:if test="${(sessionScope.roleId == 7 && b.status.equalsIgnoreCase('Comfirmed')) ||
                                                                                      (sessionScope.roleId == 7 && b.status.equalsIgnoreCase('Pending'))}">disabled=""</c:if>
                                                                              >
                                                                        <c:forEach items="${status}" var="sta">
                                                                            <c:if test="${b.status.equalsIgnoreCase('OnDelivery')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('Received') || sta.equalsIgnoreCase('OnDelivery') || sta.equalsIgnoreCase('ReShip')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                            <c:if test="${b.status.equalsIgnoreCase('ReShip')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('Received') || sta.equalsIgnoreCase('CanceledAfterReShip') || sta.equalsIgnoreCase('ReShip')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                            <c:if test="${b.status.equalsIgnoreCase('Pending')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('Canceled') || sta.equalsIgnoreCase('Comfirmed') || sta.equalsIgnoreCase('Pending')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                            <c:if test="${b.status.equalsIgnoreCase('Comfirmed')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('OnDelivery') || sta.equalsIgnoreCase('Comfirmed')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                            <c:if test="${b.status.equalsIgnoreCase('Received')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('Received')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                            <c:if test="${b.status.equalsIgnoreCase('Canceled')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('Canceled')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                            <c:if test="${b.status.equalsIgnoreCase('PaymentPending')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('PaymentPending')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                            <c:if test="${b.status.equalsIgnoreCase('CanceledAfterReShip')}">
                                                                                <c:if test="${sta.equalsIgnoreCase('CanceledAfterReShip')}">
                                                                                    <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
                                                                                </c:if>                                                                  
                                                                            </c:if>
                                                                        </c:forEach>
                                                                </select>
                                                            </td>    
                                                        </tr>
                                                    </div>
                                                </table>
                                            </div>
                                            <div class="order-method">
                                                <div class="order-method-wrapper">
                                                    <p>Shipper: ${b.shipper.name}</p>
                                                    <p>Ship Method: ${b.shipMethod.method}</p>
                                                    <p>Payment: ${b.payment.method}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="customer-detail">
                                            <div class="title mb-2 mt-2">
                                                <h5><b>Receiver</b></h5>
                                            </div>
                                            <div class="customer-detail-upper">
                                                <div class="customer-detail-right">
                                                    <p>Fullname: ${b.customer.fullName}</p>
                                                    <p>Mobile: ${b.customer.mobile}</p>
                                                </div>
                                                <div class="customer-detail-left">
                                                    <p>Gender: ${b.customer.gender}</p>
                                                    <p>Email: ${b.customer.email}</p>
                                                </div>
                                            </div>
                                            <div class="customer-detail-under">
                                                <p>Address: ${b.address.country}, ${b.address.tinhThanhPho}, ${b.address.quanHuyen}, ${b.address.getPhuongXa()}, ${b.address.details}, ${b.address.note}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-item-wrapper">
                                            <div class="title">
                                                <h5><b>Orders:</b></h5>
                                            </div>
                                            <div class="detail-item-main">
                                                <c:forEach items="${b.order}" var="o">
                                                    <div class="item-product-card">
                                                        <img src="${o.thumbnailImage}" width="60" height="80">
                                                        <p><b>${o.productName}</b></p>
                                                        <p><b>${o.size.sizeName}</b></p>
                                                        <p><b>${o.color.colorName}</b></p>
                                                        <p><b>${o.quantity}</b></p>
                                                        <p><b><fmt:formatNumber value="${o.totalPrice}" pattern="###,###,###VND"/></b></p>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="button-detail d-flex justify-content-lg-end mt-3 gap-2">
                                        <button id="cancel-btn">Cancel</button>
                                        <button id="save-btn" data-bill-id="${b.id}">Save</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty billList}">
                    <center>
                        <h3>You do not have any bills to manages!</h3>
                    </center>
                </c:if>
                <div id="results">
                    <!-- Filtered results will be displayed here -->
                </div>
            </div>

            <!--Load More Button-->
            <div class="loadmorebtn d-flex justify-content-center align-items-center mt-4">
                <button type="button" class="filter-btn" id="load-more">Load More Items</button>
            </div>

            <div id="success-popup" class="success-popup">
                <span class="tick-animation">✔</span>
                <p>Feedback submitted successfully!</p>
            </div>
        </div>



        <script>
            $(document).ready(function () {
                var bid = 0;
                var status = null;
                var salerId = 0;
                var staffEmail = null;
                const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
                const sidebar = document.getElementById('sidebar');
                const content = document.getElementById('content');
                const content1 = document.getElementById('content1');
                const navbar = document.getElementById('navbar');
                var currentPage = ${page};
                var currentList = '${allList}';
                var activeSideBar = $("#order-manager");
                activeSideBar.addClass("active-custom");

                if (${canLoadMore == false}) {
                    $("#load-more").attr("disabled", true).text("No More Items").css({
                        "background-color": "#333",
                        "cursor": "not-allowed",
                        "opacity": "0.7",
                        "box-shadow": "none",
                        "transform": "none",
                        "padding": "10px"
                    });
                }
                ;

                $(".filter-btn").click(function () {
                    console.log(${allList});
                    $.ajax({
                        url: "${pageContext.request.contextPath}/ordermanager",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: currentPage + 1,
                            allList: JSON.stringify(JSON.parse(currentList)) // ensure it’s a stringified JSON
                        },
                        success: function (response) {
                            $("#order-section").append(response); // Append new feedback items
                            currentPage++;
                            console.log("success");
                            // Check for "load-more" button existence to disable if no more items
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

                $(document).on('click', '.order-footer', function () {
                    // Find the closest order-wrapper to this order-footer and toggle its order-details
                    $(this).closest('.order-section-god').find('.order-details').fadeToggle('fast');
                    // Optional: Reset margin-bottom for the specific order-wrapper
                    $(this).closest('.order-wrapper').css("margin-bottom", "0");
                });

                toggleSidebarBtn.addEventListener('click', () => {
                    console.log(${page});
                    sidebar.classList.toggle('collapsed');
                    content.classList.toggle('collapsed');
                    content1.classList.toggle('collapsed');
                    toggleSidebarBtn.classList.toggle('collapsed');
                });

                $(document).on('change', '#sale-select', function () {
                    $(this).closest('.order-details-wrapper').find('#save-btn').css("display", "block");
                    $(this).closest('.order-details-wrapper').find('#cancel-btn').css("display", "block");
                });

                $(document).on('change', '#status-select', function () {
                    $(this).closest('.order-details-wrapper').find('#save-btn').css("display", "block");
                    $(this).closest('.order-details-wrapper').find('#cancel-btn').css("display", "block");
                });

                $(document).on('click', '#save-btn', function () {
                    let bid = $(this).attr("data-bill-id");
                    let status = $(this).closest('.order-details-wrapper').find('#status-select').val();
                    let salerId = $(this).closest('.order-details-wrapper').find('#sale-select option:selected').data("sale-id");
                    let staffEmail = $(this).closest('.order-details-wrapper').find('#sale-select option:selected').data("sale-email");
                    let saleName = $(this).closest('.order-details-wrapper').find('#sale-select option:selected').val();

                    console.log(bid + ' ' + status + ' ' + salerId + ' ' + staffEmail);

                    let $thisButton = $(this);  // Store the button context for later use in success

                    $.ajax({
                        url: '${pageContext.request.contextPath}/ordermanager',
                        method: 'POST',
                        data: {
                            action: "save",
                            bid: bid,
                            status: status,
                            salerId: salerId,
                            saleEmail: staffEmail
                        },
                        success: function (response) {
                            $('#success-popup').fadeIn();

                            setTimeout(function () {
                                $('#success-popup').fadeOut();
                            }, 1000);

                            // Use the stored button context to access elements
                            $thisButton.closest('.order-details-wrapper').find('#status-select').val(status);
                            $thisButton.closest('.order-details-wrapper').find('#sale-select').val(saleName);
                            $('#status-bill-' + bid).text(status).css({
                                'color': getStatusColor(status),
                                'font-weight': 'bold'
                            });

                            if (status === "Received") {
                                $thisButton.closest('.order-details-wrapper').find('#status-select').prop('disabled', true);
                                $thisButton.closest('.order-details-wrapper').find('#sale-select').prop('disabled', true);
                            }

                            setTimeout(function () {
                                location.reload();
                            }, 1000);
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });

                // Function to get color based on status
                function getStatusColor(status) {
                    if (status === "Received")
                        return "green";
                    else if (status === "OnDelivery")
                        return "blue";
                    else if (status === "Comfirmed")
                        return "#ffc107";
                    else if (status === "ReShip")
                        return "#F6B2B2";
                    return "red";
                }

                $(document).on('click', '#cancel-btn', function () {
                    $(this).closest('.order-section-god').find('.order-details').fadeOut('fast');
                });

                const fromDateInput = document.getElementById("fromDate");
                const toDateInput = document.getElementById("toDate");

                fromDateInput.addEventListener("change", function () {

                    // Set the minimum date for toDate to be the same as fromDate
                    toDateInput.min = fromDateInput.value;

                    // Optional: Clear any previously selected toDate if it is now invalid
                    if (toDateInput.value && toDateInput.value < fromDateInput.value) {
                        toDateInput.value = "";
                    }
                });
            });
        </script>
    </body>
</html>

</body>
</html>
