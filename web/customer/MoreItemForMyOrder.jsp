<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
                <c:if test="${b.status != 'Received' && b.status != 'OnDelivery'}">
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