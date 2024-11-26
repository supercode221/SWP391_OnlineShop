<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${productList}" var="p">
    <div class="card-custom">
        <div class="card-body">
            <div class="product-id d-flex gap-2">
                <p><b>ID: ${p.productId}</b></p>
                <p><i class="fa-solid fa-star" style="color: #FFD43B;"> Star : ${p.star} ( Total Feedback : ${p.countFeedBack} )</i></p>
                <c:forEach items="${p.tagList}" var="t">
                    <span class="badge" style="background-color: ${t.colorCode};">${t.name}</span>
                </c:forEach>
            </div>
            <div class="product-main">
                <div class="product-thumbnail">
                    <img src="${p.productThumb}" alt="${p.productName}" width="120" height="150">
                </div>
                <div class="product-name">
                    <p><b>Name: </b>${p.productName}</p>
                </div>
                <div class="product-price">
                    <p><b>Price: </b><fmt:formatNumber value="${p.productPrice}" pattern="###,###,###VND"/></p>
                </div>
                <div class="post-cate">
                    <label for="cate"><b>Category: </b></label>
                    <select id="cate" onchange="saveCateId(this, ${p.productId})">
                        <c:forEach items="${cateList}" var="c">
                            <option data-cate-id="${c.id}" <c:if test="${p.subCategoryId == c.id}">selected</c:if>>${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="post-status">
                    <label for="status"><b>Status: </b></label>
                    <select id="status" onchange="saveStatus(this, ${p.productId})">
                        <c:forEach items="${statusList}" var="sta">
                            <option <c:if test="${p.status == sta}">selected</c:if>>${sta}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <a href="productdetailmanager?pid=${p.productId}">
            <div class="card-footer-custom text-center">
                <p>View Detail</p>
            </div>
        </a>
    </div>
</c:forEach>
<c:if test="${!canLoadMore}">
    <div class="no-more-items" id="no-loadmore" data-load="false"></div> <!-- Mark the end of items -->
</c:if>