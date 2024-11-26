<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ page import="entity.codebaseOld.User" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>

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

            .item-card{
                display: flex;
                align-items: center;
                justify-content: space-around;
                border: solid 0.1px black;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                margin: 5px;
                background-color: white;
            }

            .item-card:hover {
                transform: scale(1.01);
                box-shadow: 0 0 10px #aaa;
            }

            .bill-detail-footer button{
                padding: 10px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border-radius: 5px;
                border: none;
            }

            .bill-detail-footer button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
                transform: scale(1.05);
            }

            .bill-detail-main {
                box-shadow: 0 0 10px #ccc;
                margin-bottom: 10px;
                border-radius: 10px;
            }

            .bill-detail-body-information {
                display: flex;
                justify-content: space-evenly;
                background-color: white;
                margin-top: 20px;
            }

            .bill-detail-body-order {
                padding: 10px;
                background-color: #eee;
                margin: auto 10px;
                border-radius: 10px;
                max-height: 360px;
                overflow-y: auto;
            }

            .bill-detail-footer {
                padding: 10px;
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

            .toggle-sidebar-btn {
                display: none;
            }
        </style>
    </head>

    <body>
        <jsp:include page="../base-component/header.jsp" />

        <!-- Main layout container -->
        <div class="container main-user-myorder">
            <!-- Sidebar -->
            <div class="side">
                <a href="userprofilecontroller" class="dropdown-item selected">User Profile</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="billlist" class="dropdown-item">My Orders</a></c:if>
                <c:if test="${sessionScope.roleId == 1}"><a href="refund" class="dropdown-item">Refund Request</a></c:if>
                    <a href="changepassword" class="dropdown-item">Change Password</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="addresses" class="dropdown-item">Addresses</a></c:if>
                    <a href="LogOut" class="dropdown-item">Logout</a>
                </div>

                <!-- User Profile Form Content -->
                <div class="user-myorder container-fluid mt-5">
                    <div class="bill-detail-main">
                        <div class="bill-detail-header bg-dark text-white p-3 d-flex justify-content-between align-items-center rounded-top">
                            <h4 class="mb-0">Bill - #${b.id}</h4>
                        <h5 class="mb-0">Status : ${b.status}</h5>
                        <h5 class="mb-0">${b.publishDate}</h5>
                    </div>
                    <div class="bill-detail-body">
                        <div class="bill-detail-body-information">
                            <div class="shipping-information">
                                <div class="ship">
                                    <div class="title">
                                        <h5>Ship - Information</h5>
                                    </div>
                                    <div class="infor">
                                        <p>Method: ${b.shipMethod.method}</p>
                                        <p>Shipper: ${shipper.name}</p>
                                        <p>Phone: ${shipper.phoneNumber}</p>
                                    </div>
                                </div>
                                <div class="payment">
                                    <div class="title">
                                        <h5>Payment - Information</h5>
                                    </div>
                                    <div class="infor">
                                        <p>Method: ${b.payment.method}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="receiver-information w-75">
                                <div class="title">
                                    <h5>Receiver</h5>
                                </div>
                                <div class="infor">
                                    <p>Name: ${user.fullName}</p>
                                    <p>Mobile: ${user.mobile}</p>
                                    <p class="${empty user.gender ? 'text-danger' : ''}">${empty user.gender ? 'No Gender Provided!' : ''}<c:if test="${user.gender == 'F'}">Gender: Female</c:if><c:if test="${user.gender == 'M'}">Gender: Male</c:if></p>
                                    <p>Email: ${user.email}</p>
                                    <p>Address: ${b.address.country}, ${b.address.tinhThanhPho}, ${b.address.quanHuyen}, ${b.address.getPhuongXa()}, ${b.address.details}</p>
                                    <p>Note: ${b.address.note}</p>
                                </div>
                            </div>
                        </div>
                        <div class="bill-detail-body-order">
                            <c:forEach items="${b.order}" var="o">
                                <a href="productdetail?productId=${o.productId}">
                                    <div class="item-card">
                                        <img src="${o.thumbnailImage}" width="60" height="80">
                                        <p>${o.productName}</p>
                                        <p>${o.size.sizeName}</p>
                                        <p>${o.color.colorName}</p>
                                        <p>${o.quantity}</p>
                                        <p><fmt:formatNumber value="${o.totalPrice}" pattern="###,###,###VND"/></p>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="bill-detail-footer d-flex justify-content-end gap-1">
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
        </div>

        <jsp:include page="../base-component/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            let billIdRebuy = 0;
            let billIdCancel = 0;

            document.addEventListener("DOMContentLoaded", function () {
                window.scrollTo(0, 189);
            });

            window.addEventListener("scroll", function () {
                const side = document.querySelector(".side");
                const scrollPoint = 190; // Adjust based on your layout
                console.log(window.scrollY);

                if (window.scrollY >= scrollPoint) {
                    side.classList.add("fixed");
                } else {
                    side.classList.remove("fixed");
                }
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
                                alert("Bill has been canceled successfully.");
                                location.reload();
                                // Optionally, refresh or update the page to reflect the cancellation
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
