<%-- 
    Document   : Login
    Created on : 21 Sept 2024, 15:52:00
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign Up Page</title>
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
                    <form class="login100-form validate-form" action="${pageContext.request.contextPath}/register" method="post">
                        <a href="homecontroller"><span class="login100-form-title p-b">
                                <img width="250px" src="https://i.postimg.cc/wBGLGSbk/Thi-t-k-ch-a-c-t-n.png">
                            </span></a>
                        <span class="login100-form-title p-b-5" style="font-size: 29px;">
                            Register
                        </span>
                        <c:if test="${requestScope.msg != null}">
                            <span class="login100-form-title p-b-49" style="color: red; font-size: 25px;">
                                ${msg}
                            </span>
                        </c:if>
                        <div class="wrap-input100 validate-input m-b-23" data-validate = "First Name is required">
                            <span class="label-input100">First Name</span>
                            <input class="input100" type="text" name="fname" placeholder="Type your first name">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>
                        <div class="wrap-input100 validate-input m-b-23" data-validate = "Last Name is required">
                            <span class="label-input100">Last Name</span>
                            <input class="input100" type="text" name="lname" placeholder="Type your last name">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>                      
                        <div class="wrap-input100 validate-input m-b-23" data-validate = "Email is required">
                            <span class="label-input100">Email</span>
                            <input class="input100" type="email" name="email" placeholder="Type your email">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>
                        <div class="wrap-input100 validate-input" data-validate="Password is required">
                            <span class="label-input100">Password</span>
                            <input class="input100" type="password" id="password" name="pass" placeholder="Type your password">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>
                        <div class="wrap-input100 validate-input" data-validate="Re-Password is required">
                            <span class="label-input100">Re-Password</span>
                            <input class="input100" type="password" id="repassword" name="re-pass" placeholder="Type your re-password">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>
                        <!-- Validation message -->
                        <div id="password-error" style="color:red; display:none;">Passwords do not match!</div>
                        <div class="container-login100-form-btn p-t-30">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button type="submit" class="login100-form-btn" onclick="validatePassword()">
                                    Sign Up
                                </button>
                            </div>
                        </div>
                    </form>
                    <div class="txt1 text-center p-t-54 p-b-20">
                        <span>
                            Or
                        </span>
                    </div>
                    <div class="flex-col-c p-t-15">
                        <a href="login" class="txt2">
                            Already have an account, login now
                        </a>
                    </div>
                    <div class="flex-col-c p-t-15">
                        <a href="resetpassword" class="txt2">
                            Forgot Password?
                        </a>
                    </div>
                    <div class="flex-col-c p-t-15">
                        <a href="homecontroller" class="txt2">
                            Back to Home Page
                        </a>
                    </div>
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
