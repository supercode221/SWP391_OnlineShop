<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>401 - Unauthorized</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .error-container {
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
                text-align: center;
                color: #555;
            }
            .error-code {
                font-size: 6rem;
                font-weight: bold;
                color: #f0ad4e;
            }
            .error-message {
                font-size: 1.5rem;
                margin-bottom: 20px;
            }
            .login-btn {
                text-decoration: none;
                color: #fff;
                background-color: #0275d8;
                padding: 10px 20px;
                border-radius: 5px;
            }
            .login-btn:hover {
                background-color: #025aa5;
            }
        </style>
    </head>
    <body>
        <div class="error-container">
            <div>
                <div class="error-code">401</div>
                <p class="error-message">Unauthorized Access! Please check your role!</p>
                <a 
                    <c:if test="${sessionScope.roleId == 1 || sessionScope.roleId == null || sessionScope.roleId == -1}"> href="${pageContext.request.contextPath}/homecontroller" </c:if>
                    <c:if test="${sessionScope.roleId == 2}"> href="${pageContext.request.contextPath}/admindashboard" </c:if>
                    <c:if test="${sessionScope.roleId == 3}"> href="${pageContext.request.contextPath}/customers" </c:if>
                    <c:if test="${sessionScope.roleId == 4}"> href="${pageContext.request.contextPath}/ordermanager?type=all" </c:if>
                    <c:if test="${sessionScope.roleId == 5}"> href="${pageContext.request.contextPath}/marketingdashboard" </c:if>
                    <c:if test="${sessionScope.roleId == 6}"> href="${pageContext.request.contextPath}/admindashboard" </c:if>
                    <c:if test="${sessionScope.roleId == 7}"> href="${pageContext.request.contextPath}/ordermanager?type=all" </c:if>
                        class="login-btn"><i class="fa fa-home"></i>
                    <c:if test="${sessionScope.roleId == 1 || sessionScope.roleId == null || sessionScope.roleId == -1}">Back to Homepage</c:if>
                    <c:if test="${sessionScope.roleId == 2}">Back to Admin Dashboard</c:if>
                    <c:if test="${sessionScope.roleId == 3}">Back to Customers Manager</c:if>
                    <c:if test="${sessionScope.roleId == 4}">Back to Bills Manager</c:if>
                    <c:if test="${sessionScope.roleId == 5}">Back to Marketing Dashboard</c:if>
                    <c:if test="${sessionScope.roleId == 6}">Back to Sale Dashboard</c:if>
                    <c:if test="${sessionScope.roleId == 7}">Back to Deliveries Manager</c:if>
                </a>
            </div>
        </div>
    </body>
</html>
