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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <div class="limiter">
            <div class="container-login100" style="background-image: url('${pageContext.request.contextPath}/asset/Image/preppy-aesthetic-fashion-collage-desktop-wallpaper-4k.jpg');">
                <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
                    <form class="login100-form validate-form" action="${pageContext.request.contextPath}/login" method="post">
                        <a href="homecontroller"><span class="login100-form-title p-b">
                                <img width="250px" src="https://i.postimg.cc/wBGLGSbk/Thi-t-k-ch-a-c-t-n.png">
                            </span></a>
                        <span class="login100-form-title p-b-49" style="font-size: 29px;">
                            Login
                        </span>
                        <c:if test="${requestScope.msg != null}">
                            <div class="alert alert-danger" role="alert">
                                ${msg}
                            </div>
                        </c:if>
                        <c:if test="${requestScope.SuccessMsg != null}">
                            <div class="alert alert-success" role="alert">
                                ${SuccessMsg}
                            </div>
                        </c:if>
                        <div class="wrap-input100 validate-input m-b-23" data-validate = "Email is required">
                            <span class="label-input100">Email</span>
                            <input class="input100" type="email" name="email" placeholder="Type your email" required="">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>
                        <div class="wrap-input100 validate-input" data-validate="Password is required">
                            <span class="label-input100">Password</span>
                            <input class="input100" type="password" name="pass" placeholder="Type your password" required="">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>
                        <div class="d-flex justify-content-between align-items-center p-t-15 p-b-31">
                            <div>
                                <input type="checkbox" id="rememberme" name="rem" value="on">
                                <label for="rememberme" style="font-family: Poppins-Bold;">Remember me</label>
                            </div>
                            <a href="resetpassword">Forgot password?</a>
                        </div>

                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn">
                                    Login
                                </button>
                            </div>
                        </div>
                        <div class="txt1 text-center p-t-40 p-b-20">
                            <span>
                                <a href="https://accounts.google.com/o/oauth2/auth?scope=profile%20email%20openid%20
                                   https://www.googleapis.com/auth/userinfo.profile%20
                                   https://www.googleapis.com/auth/userinfo.email%20
                                   https://www.googleapis.com/auth/user.phonenumbers.read%20
                                   https://www.googleapis.com/auth/user.gender.read
                                   &redirect_uri=http://localhost:9999/freezegroup2/logingooglecontroller&response_type=code&client_id=40994517015-qt891oiig6p5bce29fci3vtp7j41n2b1.apps.googleusercontent.com&approval_prompt=force">
                                    <i class="fa-brands fa-google fa-2xl" style="color: red;"> oogle</i>
                                </a>
                            </span>
                        </div>
                        <div class="txt1 text-center p-t-10 p-b-10">
                            <span>
                                Or
                            </span>
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
