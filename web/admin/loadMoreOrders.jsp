<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${billList}" var="b">
    <c:if test="${sessionScope.roleId == 3 || sessionScope.roleId == 2}">
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
                                                <select id="sale-select" <c:if test="${b.status.equalsIgnoreCase('Received')}">disabled=""</c:if>>
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
                                                <select id="status-select" <c:if test="${b.status.equalsIgnoreCase('Received')}">disabled=""</c:if>>
                                                    <c:forEach items="${status}" var="sta">
                                                        <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
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
    </c:if>
    <c:if test="${sessionScope.roleId == 7 && b.shipper.id == sessionScope.uid}">
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
                                                <select id="sale-select" <c:if test="${b.status.equalsIgnoreCase('Received')}">disabled=""</c:if>>
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
                                                <select id="status-select" <c:if test="${b.status.equalsIgnoreCase('Received')}">disabled=""</c:if>>
                                                    <c:forEach items="${status}" var="sta">
                                                        <option <c:if test="${b.status == sta}">selected</c:if>>${sta}</option>                                                                    
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
    </c:if>
</c:forEach>