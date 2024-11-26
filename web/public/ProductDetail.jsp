<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${productDetail.getProductName()}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/ProductDetails.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <style>
            .toggle-sidebar-btn {
                display: none;
            }
        </style>
        <style>
            * a{
                text-decoration: none;
                color: black;
            }

            /* General feedback card styling */
            .feedback-card {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                max-width: 1200px;
                margin: 0 auto;
                cursor: pointer;
            }

            .feedback-main {
                box-shadow: 0 0 10px #ccc;
                margin: 20px;
                padding: 20px;
                border-radius: 8px;
                background-color: #fff;
                transition: transform 0.3s ease;
                display: flex;
                gap: 20px;
            }

            .feedback-main:hover {
                transform: scale(1.05);
            }

            .feedback-media {
                display: grid;
                grid-template-columns: repeat(2, 1fr); /* Two images per row */
                grid-template-rows: repeat(2, 1fr);
                gap: 5px;
                width: 100px;
                height: auto;
                position: relative;
            }

            .feedback-media img {
                width: 100%;
                height: 100%;
                object-fit: cover; /* Makes the images square and fills the space */
                border-radius: 5px;
            }

            .more-images {
                position: absolute;
                bottom: 0;
                right: 0;
                background-color: rgba(0, 0, 0, 0.5);
                color: white;
                font-size: 14px;
                padding: 10px;
                border-radius: 5px;
            }

            /* Hide extra images after the 4th */
            .feedback-media img:nth-child(n+5) {
                display: none;
            }

            .feedback-content {
                margin-top: 10px;
                max-width: 300px; /* Adjust this width based on your design */
            }

            /* Make username, star, and timestamp display on one line */
            .feedback-content h5,
            .feedback-content p:first-of-type,
            .feedback-content .fa-star {
                white-space: nowrap; /* Prevent line breaks */
                overflow: hidden; /* Hide overflow */
                text-overflow: ellipsis; /* Add ellipsis if text is too long */
                display: block;
            }

            /* Special handling for the comment to allow wrapping */
            .feedback-content p.comment {
                white-space: normal; /* Allow line breaks for the comment */
            }

            /* Optional: Text styling for better readability */
            .feedback-content h5 {
                font-weight: bold;
                color: #333;
            }

            .feedback-content p:first-of-type {
                font-size: 0.9rem;
                color: #666;
            }

            .feedback-section {
                margin: 20px;
            }

            .feedback-card-main {
                display: flex;
                width: 100%;
            }

            .pagination-controls button {
                padding: 10px;
                color: white;
                background-color: black;
                transition: ease-in-out 0.2s;
                font-weight: bold;
                border: none;
            }

            .pagination-controls button:hover {
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
            <div class="row align-items-center">
                <div class="col-md-6">
                    <c:forEach items="${productDetail.getTagList()}" var="tag">
                        <span class="badge" style="background-color: ${tag.colorCode}; margin-top: 7px; margin-bottom: 7px">${tag.name}</span>
                    </c:forEach>
                    <div class="slideshow">
                        <span id="prevThumbnailBtn" class="arrow arrow-left" onclick="previousThumbnailPage()">&#10094;</span>
                        <img id="mainImage" alt="Main Product" class="product-image" src="${productDetail.getProductImage().get(0)}" />
                        <span id="nextThumbnailBtn" class="arrow arrow-right" onclick="nextThumbnailPage()">&#10095;</span>
                    </div>
                    <div class="d-flex mt-3 gap-3 imageforslideshow">
                        <div id="thumbnailsContainer" class="d-flex gap-3"></div>
                    </div>
                </div> 

                <!--Product detail section-->
                <div class="col-md-6 product-info">
                    <div class="mt-auto">
                        <i class="fa-solid fa-star" style="color: #FFD43B;"> ${productDetail.star} ( ${productDetail.countFeedBack} )</i>
                    </div>
                    <h2>${productDetail.getProductName()}</h2>
                    <div class="price">
                        <h4><fmt:formatNumber value="${productDetail.getProductPrice()}" pattern="###,###,###VND"/></h4>
                    </div>
                    <p class="mt-3">
                        ${productDetail.getProductDescription()}
                    </p>
                    <div class="mt-3">
                        <a class="text-decoration-none" href="#">
                            <i class="fas fa-ruler-combined"></i> SIZE GUIDE
                        </a>
                    </div>
                    <div class="mt-3">
                        <h5>Select Size:</h5>
                        <div class="btn-group" role="group">
                            <c:forEach items="${requestScope.attributeList.productSize}" var="pa">
                                <button class="btn btn-outline-secondary size-option" 
                                        type="button" 
                                        data-size-id="${pa.sizeId}" 
                                        data-size-name="${pa.sizeName}"
                                        onclick="selectSize(this)">${pa.sizeName}</button>
                            </c:forEach>
                        </div>
                        <p class="mt-3">Selected Size: <span id="selectedSizeName">None</span></p>
                    </div>
                    <div class="mt-3 color-choose">
                        <h5>Select Color:</h5>
                        <div class="btn-group" role="group" aria-label="Color choices">
                            <c:forEach items="${requestScope.attributeList.productColor}" var="pa">
                                <button class="btn btn-outline-dark color-option" 
                                        type="button" 
                                        data-color-id="${pa.colorId}" 
                                        data-color-name="${pa.getColorName()}"
                                        style="background-color: ${pa.getColorCode()};"
                                        onclick="selectColor(this)"></button>
                            </c:forEach>
                        </div>
                        <p class="mt-3">Selected Color: <span id="selectedColorName">None</span></p>
                    </div>
                    <div class="quantitySection">
                        <p class="mt-3">Available Quantity: <span id="availableQuantity">0</span></p>
                    </div>
                    <form action="addtocart" method="post">
                        <input type="hidden" name="productId" value="${requestScope.productId}" />
                        <input type="hidden" id="selectedSizeId" name="sizeId" />
                        <input type="hidden" id="selectedColorId" name="colorId" />

                        <div class="mt-3 d-flex align-items-center">
                            <input class="form-control" style="width: 80px;" type="number" id="quantity" name="quantity" min="1" max=""/>
                            <button id="add-to-cart-btn" class="btn btn-danger ms-3" type="submit">ADD TO CART</button>
                            <!-- Out of Stock message -->
                            <span id="outOfStockMsg" class="ms-3" style="color: red; display: none;">Out of Stock</span>
                        </div>
                    </form>
                    <div class="mt-3">
                        <a href="wishlist?productId=${requestScope.productId}&action=add" id="wishlistLink">
                            <button class="btn btn-light" onclick="handleWishlistAction(event)">
                                <i class="fa-solid fa-heart" id="wishlistHeart" style="${requestScope.validWish == 'valid' ? 'color: #ff0000;' : ''}"></i>
                            </button>
                        </a>
                    </div>
                    <div class="mt-3">
                        <a class="text-decoration-none me-3" href="#">
                            <i class="fas fa-share"></i> Share
                        </a>
                        <a class="text-decoration-none me-3" href="#">
                            <i class="fas fa-share"></i> Facebook
                        </a>
                    </div>
                </div>

                <!--Feed back section-->
                <div class="col-4 feedback-section">
                    <h4>Feedback (<c:if test="${productDetail.countFeedBack != 0}">${productDetail.countFeedBack}</c:if><c:if test="${productDetail.countFeedBack == 0 || productDetail.countFeedBack == null}">0</c:if>)</h4>

                        <!-- Check if feedback is null -->
                    <c:if test="${requestScope.feedback == null || requestScope.feedback.isEmpty()}">
                        <p>This product has no feedback yet!</p>
                    </c:if>

                    <!-- Only display feedback and pagination if feedback exists -->
                    <c:if test="${requestScope.feedback != null && !requestScope.feedback.isEmpty()}">
                        <div class="feedback-card">
                            <div class="feedback-card-main" id="feedbackContainer">
                                <c:forEach items="${requestScope.feedback}" var="fe">
                                    <c:if test="${fe.status == 'Show'}">
                                        <div class="feedback-main">
                                            <div class="feedback-media">
                                                <c:forEach items="${requestScope.feedbackMedia}" var="feMed">
                                                    <c:if test="${feMed.feedbackId == fe.id}">
                                                        <img src="${feMed.link}" alt="${fe.username} feedback">
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                            <div class="feedback-content">
                                                <div class="mt-auto">
                                                    <i class="fa-solid fa-star" style="color: #FFD43B;"> ${fe.star}</i>
                                                </div>
                                                <h5>${fe.username}</h5>
                                                <p>${fe.createAt}</p>
                                                <p class="comment">${fe.comment}</p>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>

                            <!-- Pagination feedback controls -->
                            <div class="pagination-controls">
                                <button id="prevBtn" onclick="changeFeedback(-1)">Previous</button>
                                <span id="pageInfo">1 / <span id="totalPages"></span></span>
                                <button id="nextBtn" onclick="changeFeedback(1)">Next</button>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- Popup Message -->
            <div id="popupMsg" class="popup-msg" style="display:none;">
                <p id="popupText"></p>
                <button onclick="continueShopping()">Continue shopping</button>
                <button onclick="goToCart()">View your Cart</button>
            </div>
        </div>

        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />

        <script>
            var images = [];
            var colorImageMapping = {
                "color1": 0, // First color maps to the first image in the array
                "color2": 2, // Second color maps to the second image
                "color3": 1, // First color maps to the first image in the array
                "color4": 3, // Second color maps to the second image
            };

            <c:forEach var="image" items="${productDetail.getProductImage()}">
            images.push("${image}");
            </c:forEach>

            var currentIndex = 0;
            var thumbnailIndex = 0;
            var thumbnailsPerPage = 5;

            function changeImage(src) {
                var mainImage = document.getElementById('mainImage');
                mainImage.classList.add('fade-out');

                setTimeout(function () {
                    mainImage.src = src;
                    mainImage.classList.remove('fade-out');
                    mainImage.classList.add('fade-in');

                    setTimeout(function () {
                        mainImage.classList.remove('fade-in');
                    }, 200);
                }, 200);
            }

            function nextImage() {
                if (currentIndex < images.length - 1) {
                    currentIndex++;  // Move to the next image
                    changeImage(images[currentIndex]);  // Update the main image

                    // If we're on the last image of the current thumbnail page, go to the next page
                    if (currentIndex >= thumbnailIndex + thumbnailsPerPage) {
                        nextThumbnailPage();  // Advance thumbnail page
                    }
                    updateThumbnails();  // Update the thumbnails display
                }
            }

            function previousImage() {
                if (currentIndex > 0) {
                    currentIndex--;  // Move to the previous image
                    changeImage(images[currentIndex]);  // Update the main image

                    // If we're on the first image of the current thumbnail page, go to the previous page
                    if (currentIndex < thumbnailIndex) {
                        previousThumbnailPage();  // Move back a thumbnail page
                    }
                    updateThumbnails();  // Update the thumbnails display
                }
            }

            function nextThumbnailPage() {
                if (thumbnailIndex + thumbnailsPerPage < images.length) {
                    thumbnailIndex += thumbnailsPerPage;
                    updateThumbnails();
                }
            }

            function previousThumbnailPage() {
                if (thumbnailIndex > 0) {
                    thumbnailIndex -= thumbnailsPerPage;
                    updateThumbnails();
                }
            }

            document.getElementById('prevThumbnailBtn').onclick = previousImage;
            document.getElementById('nextThumbnailBtn').onclick = nextImage;

            function updateThumbnails() {
                var thumbnailsContainer = document.getElementById('thumbnailsContainer');
                thumbnailsContainer.innerHTML = '';  // Clear previous thumbnails

                // Generate thumbnails only for the current page
                var start = thumbnailIndex;
                var end = Math.min(thumbnailIndex + thumbnailsPerPage, images.length);  // Ensure we don't go out of bounds

                for (var i = start; i < end; i++) {
                    var img = document.createElement('img');
                    img.src = images[i];
                    img.alt = 'Thumbnail';
                    img.className = 'thumbnail';
                    img.height = 80;
                    img.width = 60;

                    if (i === currentIndex) {
                        img.classList.add('active-thumbnail');  // Highlight the active thumbnail
                    }

                    img.onclick = (function (src, index) {
                        return function () {
                            currentIndex = index;  // Update currentIndex to the clicked thumbnail
                            changeImage(src);
                            updateThumbnails();  // Re-render thumbnails with the active one highlighted
                        };
                    })(images[i], i);

                    thumbnailsContainer.appendChild(img);
                }

                // Enable or disable the previous/next buttons based on the current page
                document.getElementById('prevThumbnailBtn').disabled = thumbnailIndex === 0;
                document.getElementById('nextThumbnailBtn').disabled = thumbnailIndex + thumbnailsPerPage >= images.length;
            }

            function selectColor(button) {
                const selectedColorId = button.getAttribute('data-color-id');
                const selectedColorName = button.getAttribute('data-color-name');

                document.getElementById('selectedColorName').innerText = selectedColorName;
                document.getElementById('selectedColorId').value = selectedColorId;

                // Update current image based on color selection
                const colorKey = "color" + selectedColorId;  // Assuming color IDs are "1", "2", etc.
                if (colorImageMapping[colorKey] !== undefined) {
                    currentIndex = colorImageMapping[colorKey];
                    changeImage(images[currentIndex]);  // Change the main image to the one mapped to the selected color
                    updateThumbnails();
                }

                updateQuantity();
            }

            function getColorID() {
                return document.getElementById('selectedColorId');
            }

            function getSizeID() {
                return document.getElementById('selectedSizeId');
            }

            var quantityTracker = [];

            <c:forEach items="${requestScope.attributeList.quantity}" var="pa">
            quantityTracker.push({
                colorId: '${pa.colorId}',
                sizeId: '${pa.sizeId}',
                quantity: ${pa.quantity}
            });
            </c:forEach>

            function updateQuantity() {
                let availableQuantity = 0;
                var sizeID = getSizeID().value;
                var colorID = getColorID().value;

                // Iterate over the attributeList to find the matching size and color
                quantityTracker.forEach(pa => {
                    if (sizeID === pa.sizeId && colorID === pa.colorId) {
                        availableQuantity = pa.quantity; // Set the available quantity
                    }
                });

                // Update the available quantity in the UI
                document.getElementById('availableQuantity').innerText = availableQuantity;

                // Get the quantity input field, add-to-cart button, and the out-of-stock message
                const quantityInput = document.getElementById('quantity');
                const addToCartButton = document.getElementById('add-to-cart-btn');
                const outOfStockMsg = document.getElementById('outOfStockMsg');

                if (availableQuantity === 0) {
                    // Disable quantity input and add-to-cart button
                    quantityInput.disabled = true;
                    addToCartButton.disabled = true;
                    // Show the "Out of Stock" message
                    outOfStockMsg.style.display = 'inline';
                } else {
                    // Enable quantity input and add-to-cart button
                    quantityInput.disabled = false;
                    addToCartButton.disabled = false;
                    // Hide the "Out of Stock" message
                    outOfStockMsg.style.display = 'none';

                    // Set min and max for the quantity input
                    quantityInput.min = 1;
                    quantityInput.max = availableQuantity;
                    quantityInput.value = 1;  // Set a default value within the range
                }
            }

            function selectSize(button) {
                // Remove 'active-size' class from all size buttons
                const sizeButtons = document.querySelectorAll('.size-option');
                sizeButtons.forEach(btn => btn.classList.remove('active-size'));

                // Add 'active-size' class to the clicked button
                button.classList.add('active-size');

                // Get the size ID and name from the clicked button
                const selectedSizeId = button.getAttribute('data-size-id');
                const selectedSizeName = button.getAttribute('data-size-name');

                // Display the selected size in the paragraph
                document.getElementById('selectedSizeName').innerText = selectedSizeName;
                document.getElementById('selectedSizeId').value = selectedSizeId;  // Store the size ID

                updateQuantity();
            }


            function showPopupMessage(message) {
                var popup = document.getElementById("popupMsg");
                var popupText = document.getElementById("popupText");
                popupText.innerText = message; // Set the message in the popup
                popup.style.display = "block"; // Show the popup
            }

            function continueShopping() {
                window.location.href = "productlist?type=";
            }

            function goToCart() {
                window.location.href = "cart";
            }

            <c:if test="${not empty requestScope.msg}">
            showPopupMessage('<c:out value="${requestScope.msg}" />'); // Show the popup with the message
            </c:if>

            function handleWishlistAction(event) {
                // Prevent the default behavior of the link
                event.preventDefault();

                // Get the heart icon
                var heartIcon = document.getElementById('wishlistHeart');

                // Check the color of the heart icon
                if (heartIcon.style.color === 'rgb(255, 0, 0)') { // Red heart
                    // Redirect to the wishlist servlet for removing from the wishlist
                    window.location.href = "wishlist?productId=${requestScope.productId}&action=delete";
                } else {
                    // Redirect to the href defined in the <a> tag for adding to the wishlist
                    window.location.href = document.getElementById('wishlistLink').href;
                }
            }


            // Automatically select the first color and size
            function initializeDefaults() {
                // Select the first color by triggering the selectColor function
                const firstColorButton = document.querySelector('.color-option');
                if (firstColorButton) {
                    selectColor(firstColorButton);  // Automatically selects the first color
                }

                // Select the first size by triggering the selectSize function
                const firstSizeButton = document.querySelector('.size-option');
                if (firstSizeButton) {
                    selectSize(firstSizeButton);  // Automatically selects the first size
                }
            }

            updateThumbnails();
            // Call this function when the page is loaded
            document.addEventListener("DOMContentLoaded", function () {
                initializeDefaults();
            });

            let currentFeedbackPage = 1;
            const feedbackPerPage = 4;

            // Function to display the feedback items based on the current page
            function displayFeedback(page) {
                const feedbackItems = document.querySelectorAll('.feedback-main');
                const totalItems = feedbackItems.length;
                const totalPages = Math.ceil(totalItems / feedbackPerPage);

                // Show or hide the item based on the current page
                feedbackItems.forEach((item, index) => {
                    const startIndex = (page - 1) * feedbackPerPage;
                    const endIndex = startIndex + feedbackPerPage;

                    item.style.display = (index >= startIndex && index < endIndex) ? 'flex' : 'none';
                });

                // Disable buttons if on the first or last page
                document.getElementById('prevBtn').disabled = page === 1;
                document.getElementById('nextBtn').disabled = page === totalPages;

                // Show total pages in the UI
                document.getElementById('totalPages').innerText = totalPages;
            }

            // Function to change the current page
            function changeFeedback(direction) {
                currentFeedbackPage += direction;
                displayFeedback(currentFeedbackPage);
            }

            document.addEventListener('DOMContentLoaded', () => {
                // Initial display of feedback
                displayFeedback(currentFeedbackPage);
            });

        </script>

        <style>
            .active-thumbnail {
                border: 2px solid red;
            }

            .color-option {
                width: 40px;
                height: 40px;
                margin-right: 10px;
                border-radius: 50%;
            }

            .active-color {
                border: 3px solid black;
            }

            .active-size {
                background-color: #007bff;
                color: white;
            }

            .popup-msg {
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: white;
                border: 1px solid #ccc;
                padding: 20px;
                z-index: 1000;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                text-align: center;
            }

            .popup-msg button{
                border: none;
                background-color: black;
                color: white;
                transition: ease-in-out 0.2s;
                padding: 10px;
            }

            .popup-msg button:hover{
                background-color: white;
                color: black;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            input:disabled, button:disabled {
                background-color: #e0e0e0;
                cursor: not-allowed;
            }

            #outOfStockMsg {
                font-weight: bold;
                font-size: 14px;
            }

            .slideshow, .imageforslideshow {
                position: relative;
                display: flex;
                justify-content: center;
            }

            .slideshow img {
                width: 350px;
                height: 500px;
            }
        </style>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
