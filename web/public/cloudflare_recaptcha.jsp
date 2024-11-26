<%-- 
    Document   : cloudflare_recaptcha
    Created on : Nov 16, 2024, 8:56:12 PM
    Author     : Nguyễn Nhật Minh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>ReCaptcha</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.2.1/css/bootstrap.min.css" integrity="sha512-siwe/oXMhSjGCwLn+scraPOWrJxHlUgMBMZXdPe2Tnk3I0x3ESCoLz7WZ5NTH6SZrywMY+PB1cjyqJ5jAluCOg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.9.1/font/bootstrap-icons.min.css" integrity="sha512-5PV92qsds/16vyYIJo3T/As4m2d8b6oWYfoqV+vtizRB6KhF1F9kYzWzQmsO6T3z3QG2Xdhrx7FQ+5R1LiQdUA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="https://challenges.cloudflare.com/turnstile/v0/api.js" defer></script>
        <style>
            html,
            body {
                height: 100%;
            }

            body {
                display: flex;
                align-items: center;
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #fefefe;
            }

            .form-signin {
                width: 100%;
                max-width: 330px;
                padding: 15px;
                margin: auto;
            }

            .form-signin .checkbox {
                font-weight: 400;
            }

            .form-signin .form-floating:focus-within {
                z-index: 2;
            }

            .form-signin input[type="text"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }

            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
        </style>
        <script>
            // Tự động submit form khi Turnstile hoàn thành xác thực
            function onTurnstileSuccess(token) {
                document.getElementById("captcha-form").submit();
            }
        </script>
    </head>
    <body>
        <main class="form-signin">
            <div style="display: block; flex-flow: row;">
                <form id="captcha-form" action="recaptcha" method="post">
                    <!-- Turnstile CAPTCHA -->
                    <div class="cf-turnstile"
                         data-sitekey="${sitekey}"  
                         data-callback="onTurnstileSuccess" 
                         data-expired-callback="onTurnstileExpired"
                         data-size="flexible"
                         data-language="vi-vn"
                         ></div>
                </form>
            </div>
        </main>
    </body>
</html>
