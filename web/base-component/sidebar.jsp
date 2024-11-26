<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="sidebar" id="sidebar">
    <p class="sidebar-title">
        FREEZE MANAGEMENT
    </p>
    <a id="admin-dashboard" class="hover" href="admindashboard" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 6}">style="display: none;"</c:if>>
           <c:if test="${sessionScope.roleId == 2}">Admin Dashboard</c:if>
           <c:if test="${sessionScope.roleId == 6}">Sale Dashboard</c:if>
    </a>
    <a id="marketing-dashboard" class="hover" href="marketingdashboard" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 3}">style="display: none;"</c:if>>
        Marketing DashBoard
    </a>
    <a id="setting-list" class="hover" href="settings" 
       <c:if test="${sessionScope.roleId != 2}">style="display: none;"</c:if>>
           Settings
    </a>
    <a id="user-list" class="hover" href="users" 
       <c:if test="${sessionScope.roleId != 2}">style="display: none;"</c:if>>
           Users
    </a>
    <a id="customer-list" class="hover" href="customers" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 4 && sessionScope.roleId != 6}">style="display: none;"</c:if>>
           Customers
    </a>
    <a id="order-manager" class="hover" href="ordermanager?type=all" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 4 && sessionScope.roleId != 7 && sessionScope.roleId != 6}">style="display: none;"</c:if>>
           <c:if test="${sessionScope.roleId == 2 || sessionScope.roleId == 4 || sessionScope.roleId == 6}">Bills</c:if>
           <c:if test="${sessionScope.roleId == 7}">Deliveries</c:if>
    </a>
    <a id="product-list" class="hover" href="productlistmanager?type=all" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 3}">style="display: none;"</c:if>>
        Products
    </a>
    <a id="feedback-list" class="hover" href="feedbackservlet" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 3}">style="display: none;"</c:if>>
        Feedbacks
    </a>
    <a id="slider-list" class="hover" href="sliderlist" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 3}">style="display: none;"</c:if>>
        Sliders
    </a>
    <a id="blog-list" class="hover" href="bloglistmanager?type=all" 
       <c:if test="${sessionScope.roleId != 2 && sessionScope.roleId != 3}">style="display: none;"</c:if>>
        Blogs
    </a>
</div>
