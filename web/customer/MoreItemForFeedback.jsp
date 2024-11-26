<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:forEach items="${requestScope.billList}" var="b">
    <div class="col-12 feedback-main">
        <!-- Feedback Header -->
        <div class="feedback-header bg-dark text-white p-3 d-flex justify-content-between align-items-center">
            <h4 class="mb-0">Freeze Feedback - Bill #${b.id}</h4>
            <h5 class="mb-0">${b.publishDate}</h5>
        </div>

        <!-- Feedback Body -->
        <div class="feedback-body border p-4">
            <div class="feedback-body-slogan">
                <p>Thanks, you have just help us grown quickly, feedback for more service in the future!</p>
            </div>
            <c:forEach items="${b.order}" var="o">

                <div class="item-card">
                    <img src="${o.thumbnailImage}" width="70" height="100">
                    <p>${o.productName}</p>
                    <p>${o.quantity}</p>
                    <p><fmt:formatNumber value="${o.totalPrice}" pattern="###,###,###VND"/></p>
                    <p>${o.color.colorName}</p>
                    <p>${o.productName}</p>
                    <p>${o.size.sizeName}</p>
                    <c:if test="${o.isFeedbacked == false}">
                        <button class="product-feedback-btn disable-btn-${o.id}" data-order-id="${o.id}" data-product-id="${o.productId}">Feedback</button>
                    </c:if>
                    <c:if test="${o.isFeedbacked == true}">
                        <button class="disable-custom">Thanks for your feedback</button>
                    </c:if>
                </div>

            </c:forEach>
        </div>

        <!-- Feedback Footer -->
        <div class="feedback-footer bg-light text-black p-3 rounded-top d-flex justify-content-between align-items-center">
            <div class="user-information">
                <p class="mb-2">${requestScope.username}</p>
                <p class="mb-2">${requestScope.phonenumber}</p>
                <p class="mb-2">${b.address.country}, ${b.address.tinhThanhPho}, ${b.address.quanHuyen}, ${b.address.getPhuongXa()}, ${b.address.details}, ${b.address.note}</p>
            </div>
            <div class="end-footer">
                <p></p>
                <p><strong>Total Price:</strong> <fmt:formatNumber value="${b.totalPrice}" pattern="###,###,###VND"/></p>
                <p><strong>Status:</strong> ${b.status}</p>
                <c:if test="${b.isFeedbacked == true}">
                    <button class="feedback-bill disable-custom">Thanks for your feedback</button>
                </c:if>
                <c:if test="${b.isFeedbacked == false}">
                    <button class="feedback-bill bill-feedback-btn disable-btn-bill-${b.id}" data-bill-id="${b.id}">Feedback for Freeze Shop</button>
                </c:if>
            </div>
        </div>
    </div>
</c:forEach>