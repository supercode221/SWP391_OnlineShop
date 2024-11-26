<%-- 
    Document   : UserFeedbackPage
    Created on : Oct 18, 2024, 10:07:37 PM
    Author     : Acer Aspire 7
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <style>
            * a{
                text-decoration: none;
                color: black;
            }

            .item-card{
                display: flex;
                align-items: center;
                justify-content: space-around;
                border: solid 0.1px black;
                border-radius: 10px;
                transition: ease-in-out 0.2s;
                margin-bottom: 10px;
            }

            .item-card:hover {
                transform: scale(1.01);
                box-shadow: 0 0 10px #aaa;
            }

            .feedback-main {
                box-shadow: 0 0 10px #aaa;
                padding: 0;
                margin-bottom: 30px;
            }

            .item-card button{
                padding: 10px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border: none;
                border-radius: 5px;
            }

            .item-card button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
                transform: scale(1.05);
            }

            .feedback-footer button{
                padding: 10px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border-radius: 5px;
                border: none;
            }

            .feedback-footer button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
                transform: scale(1.05);
            }

            .user-information p {
                font-size: 20px;
            }

            .link a button {
                padding: 10px 20px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                border: none;
            }

            .link a button:hover {
                color: black;
                box-shadow: inset 0 0 0 1px black;
                background-color: white;
                transform: scale(1.1);
            }

            .product-feedback-popup {
                box-shadow: 0 0 1000px black;
                background-color: white;
            }

            .image-upload-container {
                display: flex;
                gap: 10px;
            }

            .image-upload-wrapper {
                position: relative;
                width: 100px;
                height: 100px;
                border: 2px dashed #ccc;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                border-radius: 5px;
                transition: border 0.3s ease;
            }

            .image-upload-wrapper:hover {
                border-color: #999;
            }

            .image-upload-wrapper:before {
                content: "+";
                font-size: 35px;
                color: #aaa;
            }

            /* Hide the actual input field */
            .image-upload-main {
                position: absolute;
                width: 100%;
                height: 100%;
                opacity: 0;
                cursor: pointer;
            }

            /* Style the image inside the upload box */
            .uploaded-image {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .product-feedback-body {
                display: flex;
                justify-content: center;
                gap: 100px;
                align-items: center;
                border-left: solid 1px #ccc;
                border-right: solid 1px #ccc;
            }

            .product-feedback-body textarea {
                width: 750px;
                height: 100px;
                resize: none;
                padding: 10px 0 0 10px;
            }

            .product-feedback-header {
                color: white;
                background-color: black;
                border-left: solid 1px black;
                border-right: solid 1px black;
            }

            .product-feedback-footer {
                border-left: solid 1px #ccc;
                border-right: solid 1px #ccc;
            }

            .product-feedback-footer button{
                color: white;
                background-color: black;
                border: none;
                padding: 10px 20px;
                font-size: 20px;
                transition: ease-in-out 0.2s;
                border-radius: 7px;
            }

            .product-feedback-footer button:hover{
                color: black;
                background-color: white;
                box-shadow: inset 0 0 0 1px black;
                transform: scale(1.05);
            }

            .star{
                display: flex;
                gap: 10px;
            }

            .star-wrapper {
                cursor: pointer;
            }

            .popup-product {
                display: none;
            }

            .delete-image-btn {
                z-index: 1001;
                cursor: pointer;
                /*color: black;  Change color of the close button */
                margin-left: 50px; /* Space between image and close button */
                margin-bottom: 70px;
            }

            .overlay {
                display: none; /* Hide the overlay by default */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                /*background-color: rgba(0, 0, 0, 0.5);  Semi-transparent black */
                z-index: 1001; /* Just below the popup */
            }

            .success-popup {
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: black; /* Success green color */
                color: white;
                padding: 20px;
                border-radius: 10px;
                display: none; /* Initially hidden */
                text-align: center;
                z-index: 1003;
            }

            .tick-animation {
                font-size: 30px;
                animation: tick-pop 0.5s ease-in-out;
                display: block;
                margin-bottom: 10px;
                color: white;
            }

            @keyframes tick-pop {
                0% {
                    transform: scale(0);
                }
                50% {
                    transform: scale(1.3);
                }
                100% {
                    transform: scale(1);
                }
            }

            .disable-custom {
                color: gray !important;
                background-color: #333 !important;
                cursor: not-allowed !important;
                opacity: 0.7 !important;
                box-shadow: none !important;
                transform: none !important;
                padding: 10px !important;
            }

            .toggle-sidebar-btn {
                display: none;
            }

            .loadmorebtn button {
                padding: 10px 20px;
                color: white;
                background-color: black;
                border: none;
                transition: ease-in-out 0.2s;
                cursor: pointer;
            }

            .loadmorebtn button:hover {
                color: black;
                background-color: white;
                box-shadow: inset 0 0 0 1px black;
            }
        </style>
    </head>
    <body>
        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />

        <div class="container mt-5">
            <div class="row justify-content-center">
                <c:if test="${requestScope.billList == null || billList.isEmpty()}">
                    <div class="container">
                        <div class="col-12 feedback-main">
                            <!-- Feedback Header -->
                            <div class="feedback-header bg-dark text-white p-3 text-center">
                                <h4 class="mb-0">Feel free, shopping for your first freeze bill <3</h4>
                            </div>

                            <!-- Feedback Body -->
                            <div class="feedback-body border p-4">
                                <div class="feedback-body-slogan align-items-center d-flex justify-content-center">
                                    <img src="https://st4.depositphotos.com/17342290/41330/v/450/depositphotos_413307834-stock-illustration-shopping-quotes-slogan-good-shirt.jpg" width="400">
                                </div>
                                <div class="link align-items-center d-flex justify-content-center">
                                    <a href="productlist?type="><button>Shop now</button></a>
                                </div>
                            </div>

                            <!-- Feedback Footer -->
                            <div class="feedback-footer bg-light text-black p-3 rounded-top d-flex justify-content-center align-items-center">
                                <p class="text-center text-success">We are Freeze to see you shopping <3</p>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${requestScope.billList != null && !billList.isEmpty()}">
                    <div id="feedbackItem">
                        <c:forEach items="${requestScope.billList}" var="b">
                            <div class="col-12 feedback-main">
                                <!-- Feedback Header -->
                                <div class="feedback-header bg-dark text-white p-3 d-flex justify-content-between align-items-center">
                                    <h4 class="mb-0">Freeze Feedback - Bill #${b.id}</h4>
                                    <h5 class="mb-0">${b.publishDate}</h5>
                                </div>

                                <!-- Feedback Body -->
                                <div class="feedback-body border p-4">
                                    <div class="feedback-body-slogan">
                                        <p>Thanks, you have just help us grown quickly, feedback for more service in the future!</p>
                                    </div>
                                    <c:forEach items="${b.order}" var="o">
                                        <div class="item-card">
                                            <img src="${o.thumbnailImage}" width="70" height="100">
                                            <p>${o.productName}</p>
                                            <p>${o.quantity}</p>
                                            <p><fmt:formatNumber value="${o.totalPrice}" pattern="###,###,###VND"/></p>
                                            <p>${o.color.colorName}</p>
                                            <p>${o.size.sizeName}</p>
                                            <c:if test="${o.isFeedbacked == false}">
                                                <button class="product-feedback-btn disable-btn-${o.id}" data-order-id="${o.id}" data-product-id="${o.productId}">Feedback</button>
                                            </c:if>
                                            <c:if test="${o.isFeedbacked == true}">
                                                <button class="disable-custom">Thanks for your feedback</button>
                                            </c:if>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Feedback Footer -->
                                <div class="feedback-footer bg-light text-black p-3 rounded-top d-flex justify-content-between align-items-center">
                                    <div class="user-information">
                                        <p class="mb-2">${requestScope.username}</p>
                                        <p class="mb-2">${requestScope.phonenumber}</p>
                                        <p class="mb-2">${b.address.country}, ${b.address.tinhThanhPho}, ${b.address.quanHuyen}, ${b.address.getPhuongXa()}, ${b.address.details}, ${b.address.note}</p>
                                    </div>
                                    <div class="end-footer">
                                        <p></p>
                                        <p><strong>Total Price:</strong> <fmt:formatNumber value="${b.totalPrice}" pattern="###,###,###VND"/></p>
                                        <p><strong>Status:</strong> ${b.status}</p>
                                        <c:if test="${b.isFeedbacked == true}">
                                            <button class="feedback-bill disable-custom">Thanks for your feedback</button>
                                        </c:if>
                                        <c:if test="${b.isFeedbacked == false}">
                                            <button class="feedback-bill bill-feedback-btn disable-btn-bill-${b.id}" data-bill-id="${b.id}">Feedback for Freeze Shop</button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="overlay" style="display: none;"></div>
                    <div class="popup-product"></div>

                    <div class="popup-bill"></div>
                </c:if>
            </div>
        </div>

        <!--Load More Button-->
        <div class="loadmorebtn d-flex justify-content-center align-items-center mt-4">
            <button 
                type="button" 
                class="filter-btn" 
                id="load-more" 
                <c:if test="${!canLoadMore}">
                    disabled
                </c:if>>
                Load More Items
            </button>
        </div>

        <!-- Success Animation -->
        <div id="success-popup" class="success-popup">
            <span class="tick-animation">✔</span>
            <p>Feedback submitted successfully!</p>
        </div>

        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />

        <script>
            var pid = 0;
            var oid = 0;
            var currentPage = ${page};

            // load more
            $(document).ready(function () {
                $("#load-more").click(function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/feedback",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: currentPage + 1
                        },
                        success: function (response) {
                            $("#feedbackItem").append(response);  // Append new feedback items
                            currentPage++;

                            // Check for "load-more" button existence to disable if no more items
                            if (!$(response).find("#load-more").length) {
                                $("#load-more").attr("disabled", true).text("No More Items").css({
                                    "background-color": "#333",
                                    "cursor": "not-allowed",
                                    "opacity": "0.7",
                                    "box-shadow": "none",
                                    "transform": "none",
                                    "padding": "10px"
                                });
                            }

                            // Reinitialize handlers for new elements
                            attachImageUploadListener();
                            attachStarRatingHandler();
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });

                // Handle product feedback button click
                $(document).on("click", ".product-feedback-btn", function () {
                    pid = $(this).attr("data-product-id");
                    oid = $(this).attr("data-order-id");
                    console.log("Product ID: " + pid + ", OrderID: " + oid);

                    // AJAX call to load feedback form of product write by hoanganhhhhhhhh
                    $.ajax({
                        url: "${pageContext.request.contextPath}/feedback",
                        type: 'POST',
                        data: {
                            action: "getproductforfeedback",
                            pid: pid
                        },
                        success: function (response) {
                            $(".popup-product").html(response); // Insert response into the popup-product

                            // Show the feedback popup and attach handlers
                            $(".overlay").css("display", "block");
                            $(".popup-product").css({
                                "display": "block",
                                "position": "fixed",
                                "top": "50%",
                                "left": "50%",
                                "transform": "translate(-50%, -50%)",
                                "z-index": "1002",
                                "padding": "20px",
                                "width": "1000px"
                            });

                            attachImageUploadListener();  // Attach image upload event listeners to newly loaded content
                            attachStarRatingHandler();    // Attach star rating handler to newly loaded content
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });

                $(document).on("click", ".bill-feedback-btn", function () {
                    bid = $(this).attr("data-bill-id");
                    console.log("Bill ID: " + bid);

                    $.ajax({
                        url: "${pageContext.request.contextPath}/feedback",
                        type: 'POST',
                        data: {
                            action: "getbillforfeedback",
                            bid: bid
                        },
                        success: function (response) {
                            $(".popup-bill").html(response); // Insert response into the popup-product

                            // Show the feedback popup and attach handlers
                            $(".overlay").css("display", "block");
                            $(".popup-bill").css({
                                "display": "block",
                                "position": "fixed",
                                "top": "50%",
                                "left": "50%",
                                "transform": "translate(-50%, -50%)",
                                "z-index": "1002",
                                "padding": "20px",
                                "width": "1000px"
                            });

                            attachImageUploadListener();  // Attach image upload event listeners to newly loaded content
                            attachStarRatingHandler();    // Attach star rating handler to newly loaded content
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });



                // Close popup-product handler
                $(document).on('click', '.btn-close', function () {
                    $(".popup-product").css("display", "none");
                    $(".overlay").css("display", "none");
                });

                $(document).on('click', function (event) {
                    if (!$(event.target).closest('.popup-product').length && !$(event.target).closest('.product-feedback-btn').length) {
                        $(".popup-product").css("display", "none");
                        $(".overlay").css("display", "none");
                    }
                });
                // Close popup-product handler
                $(document).on('click', '.btn-close', function () {
                    $(".popup-bill").css("display", "none");
                    $(".overlay").css("display", "none");
                });

                $(document).on('click', function (event) {
                    if (!$(event.target).closest('.popup-bill').length && !$(event.target).closest('.bill-feedback-btn').length) {
                        $(".popup-bill").css("display", "none");
                        $(".overlay").css("display", "none");
                    }
                });

                // Function to attach image upload preview event listeners
                function attachImageUploadListener() {
                    document.querySelectorAll('.image-upload-main').forEach(input => {
                        input.addEventListener('change', function () {
                            const file = this.files[0];

                            // Validate if a file is selected
                            if (file) {
                                // File size validation (5MB limit in this example)
                                if (file.size > 5 * 1024 * 1024) { // 5 MB
                                    alert('File size exceeds 5MB');
                                    return;
                                }

                                // MIME type validation
                                if (!file.type.startsWith('image/')) {
                                    alert('Only image files are allowed!');
                                    return;
                                }

                                // Create a FileReader to read the file as DataURL (Base64 string)
                                const reader = new FileReader();
                                reader.onload = function (e) {
                                    const wrapper = input.closest('.image-upload-wrapper');

                                    // Remove existing image and delete button if present
                                    wrapper.querySelector('img')?.remove();
                                    wrapper.querySelector('.delete-image-btn')?.remove();

                                    // Create new image element
                                    const img = document.createElement('img');
                                    img.src = e.target.result;
                                    img.classList.add('uploaded-image');

                                    // Create close button (✖)
                                    const closeButton = document.createElement('span');
                                    closeButton.textContent = '✖';
                                    closeButton.classList.add('delete-image-btn');

                                    // Append the image and close button to the wrapper
                                    wrapper.appendChild(img);
                                    wrapper.appendChild(closeButton);

                                    // Add event listener to the close button to remove image and reset input
                                    closeButton.addEventListener('click', function (event) {
                                        event.stopPropagation();
                                        img.remove();
                                        closeButton.remove();
                                        input.value = ''; // Reset the file input
                                    });
                                };

                                // Read the file as a Data URL
                                reader.readAsDataURL(file);
                            }
                        });
                    });
                }

                // Function to handle star rating system
                function attachStarRatingHandler() {
                    $(".star-wrapper").click(function () {
                        const starIndex = $(this).index(); // Get the clicked star index
                        // Reset all stars before the selected one to filled state
                        $(".star-wrapper").each(function (index) {
                            if (index <= starIndex) {
                                $(this).find('i').addClass('text-warning'); // Fill star color
                            } else {
                                $(this).find('i').removeClass('text-warning'); // Unfill star color
                            }
                        });
                    });
                }
            });

            function handleCommentChange() {
                // Get the textarea and its value
                const commentTextArea = document.getElementById('comment');
                const inputText = commentTextArea.value;

                // Count characters including spaces
                const charCount = inputText.length;

                // Update the displayed character count
                const charCountDisplay = document.getElementById('charCount');
                const charCountMainDisplay = document.getElementById('charCountMain');
                charCountDisplay.textContent = charCount;

                // Change color to red if character count is 100
                if (charCount >= 100) {
                    charCountMainDisplay.style.color = 'red'; // Change to red
                } else {
                    charCountMainDisplay.style.color = 'black'; // Reset to black
                }

                // If the character count exceeds 100, prevent further typing and trim the input
                if (charCount > 100) {
                    // Trim the input to the first 100 characters
                    const trimmedText = inputText.slice(0, 100);
                    commentTextArea.value = trimmedText; // Update the textarea value
                    charCountDisplay.textContent = 100; // Set character count to 100
                }
            }

            function getDataFeedback() {
                let imgList = [];

                // Collect the sources of uploaded images
                document.querySelectorAll('.uploaded-image').forEach(img => {
                    imgList.push(img.src);
                });

                let starCount = document.querySelectorAll('.text-warning').length;
                let textValue = document.querySelector('#comment').value;

                // Check if at least one star has been rated
                if (starCount <= 0) {
                    // Show the error message
                    const msgElement = document.getElementById('msg');
                    msgElement.textContent = 'Please rate me first!';
                    msgElement.style.color = 'red';
                    msgElement.style.display = 'block'; // Make sure it is displayed

                    // Wait for 3 seconds (3000ms) and then hide the message
                    setTimeout(() => {
                        $('#msg').fadeOut('fast'); // Using fadeOut for smooth disappearance
                    }, 3000);
                    return null; // Return null if there's an error
                }

                return {imgList, starCount, textValue}; // Return an object containing the feedback data
            }

            function getProductDataFeedback() {
                const feedbackData = getDataFeedback(); // Get feedback data

                // Check if feedbackData is null (indicating an error)
                if (!feedbackData)
                    return;

                const {imgList, starCount, textValue} = feedbackData; // Destructure values

                console.log(oid + ", " + starCount + ", " + textValue);

                // AJAX request to submit the feedback
                $.ajax({
                    url: "${pageContext.request.contextPath}/feedback?action=productfeedbacksent",
                    type: 'POST',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    data: JSON.stringify({
                        imgList: imgList,
                        starCount: starCount,
                        comment: textValue,
                        productId: pid,
                        orderId: oid
                    }),
                    success: function (response) {
                        // Show the success popup
                        $('#success-popup').fadeIn();
                        console.log("Feedback submitted successfully!");

                        let button = $(".disable-btn-" + oid);
                        console.log("Selected button:", button);

                        // Check if the button exists
                        if (button.length > 0) {
                            button.text("Thanks for your feedback").prop("disabled", true).addClass("disable-custom");
                        } else {
                            console.error("Button not found for oid:", oid);
                        }

                        // Hide the success popup after 2 seconds
                        setTimeout(function () {
                            $('#success-popup').fadeOut();
                        }, 2000); // 2000ms = 2 seconds

                        // Show the feedback popup and attach handlers
                        $(".overlay").css("display", "none");
                        $(".popup-product").css({
                            "display": "none",
                            "position": "fixed",
                            "top": "50%",
                            "left": "50%",
                            "transform": "translate(-50%, -50%)",
                            "z-index": "1002",
                            "padding": "20px",
                            "width": "1000px"
                        });
                    },
                    error: function (xhr, status, error) {
                        console.log("Error: " + error);
                    }
                });
            }

            function getBillDataFeedback() {
                const feedbackData = getDataFeedback(); // Get feedback data


                // Check if feedbackData is null (indicating an error)
                if (!feedbackData)
                    return;

                const {imgList, starCount, textValue} = feedbackData; // Destructure values

                console.log(bid + ", " + textValue + ", " + starCount);

                // AJAX request to submit the feedback
                $.ajax({
                    url: "${pageContext.request.contextPath}/feedback?action=billfeedbacksent",
                    type: 'POST',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    data: JSON.stringify({
                        imgList: imgList,
                        starCount: starCount,
                        comment: textValue,
                        billId: bid
                    }),
                    success: function (response) {
                        // Show the success popup
                        $('#success-popup').fadeIn();
                        console.log("Feedback submitted successfully!");

                        let button = $(".disable-btn-bill-" + bid);
                        console.log("Selected button:", button);

                        // Check if the button exists
                        if (button.length > 0) {
                            button.text("Thanks for your feedback")
                                    .prop("disabled", true)
                                    .addClass("disable-custom");

                        } else {
                            console.error("Button not found for oid:", oid);
                        }

                        // Hide the success popup after 2 seconds
                        setTimeout(function () {
                            $('#success-popup').fadeOut();
                        }, 2000); // 2000ms = 2 seconds

                        // Show the feedback popup and attach handlers
                        $(".overlay").css("display", "none");
                        $(".popup-bill").css({
                            "display": "none",
                            "position": "fixed",
                            "top": "50%",
                            "left": "50%",
                            "transform": "translate(-50%, -50%)",
                            "z-index": "1002",
                            "padding": "20px",
                            "width": "1000px"
                        });


                    },
                    error: function (xhr, status, error) {
                        console.log("Error: " + error);
                    }
                });
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
