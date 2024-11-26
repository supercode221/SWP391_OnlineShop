<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <style>
            .toggle-sidebar-btn {
                display: none;
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
        </style>
    </head>
    <body>
        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />

        <div class="container mt-5">

            <!-- Sidebar -->
            <div class="side">
                <a href="userprofilecontroller" class="dropdown-item">User Profile</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="billlist" class="dropdown-item">My Orders</a></c:if>
                <c:if test="${sessionScope.roleId == 1}"><a href="refund" class="dropdown-item">Refund Request</a></c:if>
                    <a href="changepassword" class="dropdown-item selected">Change Password</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="addresses" class="dropdown-item">Addresses</a></c:if>
                    <a href="LogOut" class="dropdown-item">Logout</a>
                </div>

                <div class="container-fluid">
                    <h2>Change Password</h2>

                    <!-- Message display section -->
                <c:if test="${not empty requestScope.msg}">
                    <div 
                        class="alert
                        ${requestScope.msg == 'Wrong current password!' ? 'alert-danger' : 'alert-success'}
                        " 
                        role="alert"
                        >
                        ${requestScope.msg}
                    </div>
                </c:if>

                <form id="changePasswordForm" action="changepassword" method="post" onsubmit="return validateForm()" class="row g-3">
                    <!-- Dòng 1: Current Password và Re-enter Current Password -->
                    <div class="col-md-6 w-100">
                        <label for="currentPassword" class="form-label">Current Password</label>
                        <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                    </div>
                    <!--                    <div class="col-md-6">
                                            <label for="reEnterCurrentPassword" class="form-label">Re-enter Current Password</label>
                                            <input type="password" class="form-control" id="reEnterCurrentPassword" name="reEnterCurrentPassword" required>
                                        </div>-->

                    <!-- Dòng 2: New Password và Re-enter New Password -->
                    <div class="col-md-6">
                        <label for="newPassword" class="form-label">New Password</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="col-md-6">
                        <label for="reEnterNewPassword" class="form-label">Re-enter New Password</label>
                        <input type="password" class="form-control" id="reEnterNewPassword" name="reEnterNewPassword" required>
                    </div>

                    <!-- Dòng 3: Submit Button -->
                    <div class="col-md-6 w-100 mt-4">
                        <button type="submit" class="btn btn-primary w-100">Change Password</button>
                    </div>
                    <!--                    <div class="col-md-6">
                                            <button type="reset" class="btn btn-secondary w-100">Reset</button>
                                        </div>-->
                </form>

            </div>
        </div>
        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                    function validateForm() {
                        const newPassword = document.getElementById('newPassword').value;
                        const confirmPassword = document.getElementById('confirmPassword').value;

                        if (newPassword !== confirmPassword) {
                            alert("New Password and Confirm Password do not match.");
                            return false;
                        }
                        return true; // Allow form submission
                    }
        </script>
    </body>
</html>
