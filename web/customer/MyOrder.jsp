<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ page import="entity.codebaseOld.User" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${sessionScope.username}'s Profile</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css" />
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">

        <style>
            /* Layout styling */
            body, html {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
            }

            * a {
                text-decoration: none;
                color: black;
            }

            .side {
                position: absolute; /* Set initially to absolute */
                top: 320px; /* Initial top position */
                left: 57px;
                width: 200px;
                z-index: 1000;
                background-color: white;
                box-shadow: 0 0 5px #ccc;
                padding: 20px;
                border-radius: 10px;
            }

            .side.fixed {
                position: fixed;
                top: 120px; /* Adjust fixed position for when scrolling */
            }

            .side a{
                padding: 5px 20px;
                font-weight: bold;
                border: solid 1px black;
                margin-bottom: 10px;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                transform: scale(1);
            }

            .side a:hover{
                background-color: black;
                color: white;
                transform: scale(1.005);
            }

            .selected {
                background-color: black;
                color: white;
            }

            .main-user-myorder {
                margin: 48px auto;
                display: flex;
                /*justify-content: space-around;*/
                gap: 20px;
            }

            .toggle-sidebar-btn {
                display: none;
            }

            .item-card{
                display: flex;
                align-items: center;
                justify-content: space-around;
                border: solid 0.1px black;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                margin-bottom: 10px;
            }

            .item-card:hover {
                transform: scale(1.01);
                box-shadow: 0 0 10px #aaa;
            }

            .myorder-main {
                box-shadow: 0 0 10px #aaa;
                padding: 0;
                margin-bottom: 30px;
            }

            .item-card button{
                padding: 10px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border: none;
                border-radius: 5px;
            }

            .item-card button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
                transform: scale(1.05);
            }

            .myorder-footer button{
                padding: 10px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border-radius: 5px;
                border: none;
            }

            .myorder-footer button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
                transform: scale(1.05);
            }

            .user-information p {
                font-size: 17px;
            }

            .link a button {
                padding: 10px 20px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border: none;
            }

            .link a button:hover {
                color: black;
                box-shadow: inset 0 0 0 1px black;
                background-color: white;
                transform: scale(1.1);
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

            .disable-custom {
                color: gray !important;
                background-color: #333 !important;
                cursor: not-allowed !important;
                opacity: 0.7 !important;
                box-shadow: none !important;
                transform: none !important;
                padding: 10px !important;
            }
        </style>
    </head>

    <body>
        <jsp:include page="../base-component/header.jsp" />

        <!-- Main layout container -->
        <div class="container main-user-myorder">
            <!-- Sidebar -->
            <div class="side">
                <a href="userprofilecontroller" class="dropdown-item">User Profile</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="billlist" class="dropdown-item selected">My Orders</a></c:if>
                <c:if test="${sessionScope.roleId == 1}"><a href="refund" class="dropdown-item">Refund Request</a></c:if>
                    <a href="changepassword" class="dropdown-item">Change Password</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="addresses" class="dropdown-item">Addresses</a></c:if>
                    <a href="LogOut" class="dropdown-item">Logout</a>
                </div>

            <c:if test="${requestScope.billList == null || billList.isEmpty()}">
                <div class="container">
                    <div class="col-12 myorder-main">
                        <!-- Feedback Header -->
                        <div class="myorder-header bg-dark text-white p-3 text-center">
                            <h4 class="mb-0">Feel free, shopping for your first freeze bill <3</h4>
                        </div>

                        <!-- Feedback Body -->
                        <div class="myorder-body border p-4">
                            <div class="myorder-body-slogan align-items-center d-flex justify-content-center">
                                <img src="https://st4.depositphotos.com/17342290/41330/v/450/depositphotos_413307834-stock-illustration-shopping-quotes-slogan-good-shirt.jpg" width="400">
                            </div>
                            <div class="link align-items-center d-flex justify-content-center">
                                <a href="productlist?type="><button>Shop now</button></a>
                            </div>
                        </div>

                        <!-- Feedback Footer -->
                        <div class="myorder-footer bg-light text-black p-3 rounded-top d-flex justify-content-center align-items-center">
                            <p class="text-center text-success">We are Freeze to see you shopping <3</p>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${requestScope.billList != null && !billList.isEmpty()}">
                <!-- User Profile Form Content -->
                <div class="user-myorder container-fluid">
                    <div id="billItem">
                        <c:forEach items="${requestScope.billList}" var="b">
                            <div class="col-12 myorder-main rounded">
                                <!-- Feedback Header -->
                                <div class="myorder-header bg-dark text-white p-3 d-flex justify-content-between align-items-center rounded-top">
                                    <h4 class="mb-0">Bill #${b.id}</h4>
                                    <h5 class="mb-0">Status : ${b.status}</h5>
                                    <h5 class="mb-0">${b.publishDate}</h5>
                                </div>

                                <!-- Feedback Body -->
                                <div class="myorder-body border p-4">
                                    <c:forEach items="${b.order}" var="o">
                                        <a href="productdetail?productId=${o.productId}">
                                            <div class="item-card">
                                                <img src="${o.thumbnailImage}" width="70" height="100">
                                                <p>${o.productName}</p>
                                                <p>${o.quantity}</p>
                                                <p><fmt:formatNumber value="${o.totalPrice}" pattern="###,###,###VND"/></p>
                                                <p>${o.color.colorName}</p>
                                                <p>${o.size.sizeName}</p>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>

                                <!-- Feedback Footer -->
                                <div class="myorder-footer bg-light text-black p-3 rounded-top d-flex justify-content-between align-items-center rounded-bottom">
                                    <div class="user-information">
                                        <p><strong>Total Price:</strong> <fmt:formatNumber value="${b.totalPrice}" pattern="###,###,###VND"/></p>
                                    </div>
                                    <div class="end-footer">
                                        <button class="myorder-bill-detail bill-myorder-btn" data-bill-id-detail="${b.id}">Details</button>
                                        <button class="myorder-bill-rebuy bill-myorder-btn" data-bill-id-rebuy="${b.id}">Re-buy</button>
                                        <c:if test="${b.status != 'Received' && b.status != 'OnDelivery' && b.status != 'Canceled'}">
                                            <c:if test="${b.isCanceled == false}">
                                                <button class="myorder-bill-cancel bill-myorder-btn" data-bill-id-cancel="${b.id}">Cancel order</button>
                                            </c:if>
                                            <c:if test="${b.isCanceled == true}">
                                                <button class="myorder-bill-cancel bill-myorder-btn disable-custom" disabled="">Your canceled is pending</button>
                                            </c:if>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!--Load More Button-->
                    <div class="loadmorebtn d-flex justify-content-center align-items-center mt-4">
                        <button type="button" class="filter-btn" id="load-more" <c:if test="${!canLoadMore}">disabled</c:if>>Load More Items</button>
                        </div>
                    </div>
            </c:if>
        </div>

        <jsp:include page="../base-component/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            var currentPage = ${page};
            let billIdRebuy = 0;
            let billIdDetail = 0;
            let billIdCancel = 0;

            $("#load-more").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/billlist",
                    type: "POST",
                    data: {
                        action: "load-more",
                        page: currentPage + 1
                    },
                    success: function (response) {
                        $("#billItem").append(response);  // Append new feedback items
                        currentPage++;

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

                        $(document).ready(function () {
                            $(".myorder-bill-detail.bill-myorder-btn").on("click", function () {
                                billIdDetail = $(this).data("bill-id-detail");
                                console.log("Detail: " + billIdDetail);

                                window.location.href = "billdetail?billId=" + billIdDetail;
                            });
                        });

                        $(document).ready(function () {
                            $(".myorder-bill-rebuy.bill-myorder-btn").on("click", function () {
                                billIdRebuy = $(this).data("bill-id-rebuy");
                                console.log("Rebuy: " + billIdRebuy);

                                $.ajax({
                                    url: '${pageContext.request.contextPath}/billlist',
                                    type: 'POST',
                                    data: {
                                        action: "rebuy",
                                        bidRebuy: billIdRebuy
                                    },
                                    success: function (response) {
                                        window.location.href = "cart";
                                    }
                                });
                            });
                        });

                        $(document).ready(function () {
                            $(".myorder-bill-cancel.bill-myorder-btn").on("click", function () {
                                billIdCancel = $(this).data("bill-id-cancel");
                                console.log("Cancel: " + billIdCancel);

                                // Confirm before sending the request
                                if (confirm("Are you sure you want to cancel this bill?")) {
                                    $.ajax({
                                        url: '${pageContext.request.contextPath}/billlist',
                                        type: 'POST',
                                        data: {
                                            action: "cancel",
                                            bidCancel: billIdCancel
                                        },
                                        success: function (response) {
                                            alert("Bill has been pending canceled, please wait our staff working, thanks!");
                                            // Optionally, refresh or update the page to reflect the cancellation
                                            location.reload();
                                        },
                                        error: function (xhr, status, error) {
                                            console.error("Error canceling bill:", error);
                                            alert("Failed to cancel the bill. Please try again later.");
                                        }
                                    });
                                }
                            });
                        });
                    },
                    error: function (xhr, status, error) {
                        console.log("Error: " + error);
                    }
                });
            });

            window.addEventListener("scroll", function () {
                const side = document.querySelector(".side");
                const scrollPoint = 190; // Adjust based on your layout

                if (window.scrollY >= scrollPoint) {
                    side.classList.add("fixed");
                } else {
                    side.classList.remove("fixed");
                }
            });

            $(document).ready(function () {
                $(".myorder-bill-detail.bill-myorder-btn").on("click", function () {
                    billIdDetail = $(this).data("bill-id-detail");
                    console.log("Detail: " + billIdDetail);

                    //Trỏ về servlet BillDetailsController
                    window.location.href = "billdetail?billId=" + billIdDetail;
                });
            });

            $(document).ready(function () {
                $(".myorder-bill-rebuy.bill-myorder-btn").on("click", function () {
                    billIdRebuy = $(this).data("bill-id-rebuy");
                    console.log("Rebuy: " + billIdRebuy);

                    //Trỏ về servlet MyOrderController
                    $.ajax({
                        url: '${pageContext.request.contextPath}/billlist',
                        type: 'POST',
                        data: {
                            action: "rebuy",
                            bidRebuy: billIdRebuy
                        },
                        success: function (response) {
                            window.location.href = "cart";
                        }
                    });
                });
            });

            $(document).ready(function () {
                $(".myorder-bill-cancel.bill-myorder-btn").on("click", function () {
                    billIdCancel = $(this).data("bill-id-cancel");
                    console.log("Cancel: " + billIdCancel);

                    // Confirm before sending the request
                    if (confirm("Are you sure you want to cancel this bill?")) {
                        //Trỏ về servlet MyOrderController
                        $.ajax({
                            url: '${pageContext.request.contextPath}/billlist',
                            type: 'POST',
                            data: {
                                action: "cancel",
                                bidCancel: billIdCancel
                            },
                            success: function (response) {
                                alert("Bill has been pending canceled, please wait our staff working, thanks!");
                                // Optionally, refresh or update the page to reflect the cancellation
                                location.reload();
                            },
                            error: function (xhr, status, error) {
                                console.error("Error canceling bill:", error);
                                alert("Failed to cancel the bill. Please try again later.");
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>
