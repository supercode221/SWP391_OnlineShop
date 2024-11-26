<%-- 
    Document   : MoreItemForCartDetail
    Created on : Sep 24, 2024, 7:55:00 PM
    Author     : Nguyễn Nhật Minh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--Displays Items-->
<c:forEach var="item" items="${cart.products}">
    <input class="displayed-item" id="displayed-item" type="hidden" name="displayed-item" value="${item.id}" />
    <div class="p-4">
        <div class="d-flex align-items-center">

            <!--Checkbox for individual items-->
            <input 
                id="checkbox-${item.id}" 
                name="checkbox" 
                class="me-4 form-check-input item-checkbox" 
                type="checkbox" 
                value="${item.id}" 
                data-id="${item.id}" 
                data-size="${item.size}" 
                data-color="${item.colorName}" 
                onchange="updateCheckBox(this, '${item.id}', '${item.size}', '${item.colorName}')" 
                <c:if test="${item.isChecked == '1'}">
                    checked
                </c:if> 
                />

            <div class="d-flex align-items-center flex-grow-1">

                <!--Item's Image-->
                <img alt="Placeholder image" class="me-4" height="100" src="${item.thumbnailImage}" width="100"/>
                <div>

                    <!--Item's Name-->
                    <a class="h5 text-primary fw-bold text-decoration-none" href="productdetail?productId=${item.id}">${item.name}</a>

                    <!--Item's Tag List-->
                    <div class="mt-2">
                        <c:forEach var="tag" items="${item.tagList}">
                            <span class="badge" style="background-color: ${tag.colorCode}; margin-top: 7px; margin-bottom: 7px">${tag.name}</span>
                        </c:forEach>
                    </div>

                    <!--Item's Price-->
                    <div class="mt-2 h6 fw-bold">
                        <fmt:formatNumber value="${item.price}" pattern="###,###,###VND"/>
                    </div>
                    <div class="mt-2 h6 fw-bold">
                        <p>Size: ${item.getSize()}</p>
                    </div>
                    <div class="mt-2 h6 fw-bold">
                        <p>Color: ${item.getColorName()} 
                            <span class="badge" style="background-color: ${item.getColorCode()};
                                  display: inline-block;
                                  width: 15px;
                                  height: 15px;
                                  border: 1px solid #000;
                                  margin-left: 5px;
                                  box-shadow: 0 0 5px #ccc"></span>
                        </p>
                    </div>

                    <!--Quantity Process-->
                    <div class="d-flex align-items-center mt-2">
                        <button class="btn btn-light" onclick="decreaseQuantity(this,${item.stock})">-</button>
                        <input 
                            id="quantity" 
                            class="mx-2 form-control text-center quantity-input" 
                            style="width: 70px;" 
                            type="number" 
                            min="0" 
                            max="<c:if test="${item.stock > 0}">${item.stock}</c:if><c:if test="${item.stock == 0}">0</c:if>" 
                            value="<c:if test="${item.quantity < item.stock}">${item.quantity}</c:if><c:if test="${item.stock == 0}">0</c:if>" 
                            onchange="quantityChange(this,${item.id}, '${item.size}', '${item.colorName}')"/>
                        <button class="btn btn-light" onclick="increaseQuantity(this,${item.stock})">+</button>
                        <c:if test="${item.stock == 0}">
                            <p class="text-danger text-success align-items-center ms-1 mt-3">Out of stock!</p>
                        </c:if>
                        <c:if test="${item.stock != 0}">
                            <p class="text-warning align-items-center ms-1 mt-3">Stock Left: ${item.stock}</p>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="d-flex flex-column gap-2 d-none edit-buttons">
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="colorName" value="${item.getColorName()}">
                    <input type="hidden" name="size" value="${item.getSize()}">
                    <button class="btn btn-danger w-100" name="productId" value="${item.id}">Delete</button>
                </form>
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="similar-items">
                    <button class="btn btn-dark w-100" name="productId" value="${item.id}">Similar Products</button>
                </form>
            </div>
        </div>
    </div>
</c:forEach>

<script>
    canLoadMore = ${canLoadMore};
</script>