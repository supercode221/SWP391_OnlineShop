<%-- 
    Document   : Login
    Created on : 21 Sept 2024, 15:52:00
    Author     : Lenovo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Font/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Font/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Vendor/animate/animate.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Vendor/css-hamburgers/hamburgers.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Vendor/animsition/css/animsition.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Vendor/select2/select2.min.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/Vendor/daterangepicker/daterangepicker.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/CSS/util.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/CSS/main.css">
        <!--===============================================================================================-->
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
    </head>
    <body>
        <div class="limiter">
            <div class="container-login100" style="background-image: url('${pageContext.request.contextPath}/asset/Image/preppy-aesthetic-fashion-collage-desktop-wallpaper-4k.jpg');">
                <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
                    <form class="login100-form validate-form" action="resetpassword" method="post">
                        <a href="homecontroller"><span class="login100-form-title p-b">
                                <img width="250px" src="https://i.postimg.cc/wBGLGSbk/Thi-t-k-ch-a-c-t-n.png">
                            </span></a>
                        <span class="login100-form-title p-b-49" style="font-size: 25px;">
                            Reset Password
                        </span>

                        <!-- **Display Error Message (if any) -->
                        <c:if test="${not empty msg}">
                            <div class="alert alert-danger" role="alert">
                                ${msg}
                            </div>
                        </c:if>

                        <!-- **Email Input Section (Initial Step) -->
                        <c:if test="${empty otpConfirm and empty action}">
                            <div class="wrap-input100 validate-input m-b-23" data-validate="Email is required">
                                <span class="label-input100">Email</span>
                                <input class="input100" type="email" name="email" placeholder="Type your email" value="${email}">
                                <span class="focus-input100" data-symbol="&#xf206;"></span>
                            </div>
                        </c:if>

                        <!-- **OTP Input Section (After Email Validation) -->
                        <c:if test="${otpConfirm == 'confirm'}">
                            <div class="wrap-input100 validate-input m-b-23" data-validate="OTP is required">
                                <span class="label-input100">OTP</span>
                                <input class="input100" type="text" name="otp" placeholder="Enter OTP" required>
                                <span class="focus-input100" data-symbol="&#xf206;"></span>
                            </div>

                            <!-- **Hidden Field to Indicate OTP Verification Action -->
                            <input type="hidden" name="action" value="otpVerify">

                            <!-- **Display Remaining Attempts -->
                            <c:set var="otpAttempts" value="${sessionScope.otpAttempts != null ? sessionScope.otpAttempts : 0}"/>
                            <c:choose>
                                <c:when test="${otpAttempts >= 3}">
                                    <div class="alert alert-danger" role="alert">
                                        You have exceeded the maximum number of OTP attempts. Please try again later.
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-warning" role="alert">
                                        You have <strong>${3 - otpAttempts}</strong> OTP attempts remaining.
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>


                        <!-- **New Password Input Section (After OTP Verification) -->
                        <c:if test="${action == 'otpVerified'}">
                            <div class="wrap-input100 validate-input m-b-23" data-validate="New Password is required">
                                <span class="label-input100">New Password</span>
                                <input class="input100" type="password" id="password" name="newpass" placeholder="Type your new password" required>
                                <span class="focus-input100" data-symbol="&#xf190;"></span>
                            </div>
                            <div class="wrap-input100 validate-input m-b-23" data-validate="Re-New Password is required">
                                <span class="label-input100">Re-New Password</span>
                                <input class="input100" type="password" id="repassword" name="re-newpass" placeholder="Re-type your new password" required>
                                <span class="focus-input100" data-symbol="&#xf190;"></span>
                            </div>
                            <!-- **Hidden Fields to Indicate Final Action -->
                            <input type="hidden" name="action" value="final">
                            <input type="hidden" name="final" value="final">
                            <!-- **Optionally, Include Email as a Hidden Field (if not using Session) -->
                            <!-- <input type="hidden" name="email" value="${email}"> -->
                        </c:if>

                        <!-- **Submit Button -->
                        <div class="container-login100-form-btn p-t-30">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button type="submit" class="login100-form-btn" onclick="validatePassword()">
                                    Reset
                                </button>
                            </div>
                        </div>
                        <div class="txt1 text-center p-t-54 p-b-20">
                            <span>
                                Or
                            </span>
                        </div>
                        <div class="flex-col-c p-t-15">
                            <a href="login" class="txt2">
                                Login
                            </a>
                        </div>
                        <div class="flex-col-c p-t-15">
                            <a href="register" class="txt2">
                                Sign Up
                            </a>
                        </div>
                        <div class="flex-col-c p-t-15">
                            <a href="homecontroller" class="txt2">
                                Back to Home Page
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div id="dropDownSelect1"></div>
        <script>
            function validatePassword(password) {
                // Password must be at least 6 characters, with at least one uppercase letter, one number, and one special character
                var regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!#%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
                return regex.test(password);
            }
        </script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/asset/Vendor/jquery/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/asset/Vendor/animsition/js/animsition.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/asset/Vendor/bootstrap/js/popper.js"></script>
        <script src="${pageContext.request.contextPath}/asset/Vendor/bootstrap/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/asset/Vendor/select2/select2.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/asset/Vendor/daterangepicker/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/asset/Vendor/daterangepicker/daterangepicker.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/asset/Vendor/countdowntime/countdowntime.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/asset/JS/main.js"></script>
    </body>
</html>
