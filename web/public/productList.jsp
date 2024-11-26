<%-- 
    Document   : productList
    Created on : Sep 20, 2024, 11:59:37 PM
    Author     : Acer Aspire 7
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products <c:if test="${not empty searchInput}">| ${searchInput}</c:if></title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/productList.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <style>
            * a{
                text-decoration: none !important;
                color: black !important;
            }

            /* Custom modal positioning with transition effect */
            #filterModal {
                position: absolute;
                top: 0; /* Initially, the modal is slightly above its final position */
                left: 0;
                opacity: 0; /* Start with the modal being invisible */
                display: none; /* Hidden initially */
                transition: top 0.5s ease, opacity 0.5s ease; /* Smooth drop and fade-in effect */
                width: 600px;
            }

            #filterModal.show {
                opacity: 1; /* Fade in */
            }

            .category-filter-btn-end {
                color: white;
                background-color: black;
                transition: ease-in-out 0.3s;
            }

            .category-filter-btn-end:hover{
                color: black;
                background-color: white;
                border: solid 0.1px black;
            }

            .category-filter-btn-cate {
                background-color: white;
                color: black;
                box-shadow: 0 0 10px #ccc;
                transition: ease-in-out 0.3s;
            }

            .category-filter-btn-cate:hover {
                background-color: black;
                color: white;
            }

            .category-filter-btn {
                box-shadow:  0 0 10px #ccc;
            }

            .filter-btn {
                box-shadow: 0 0 10px #ccc;
            }

            #price-range-slider {
                width: 100%;
                margin-top: 10px;
            }

            .my-custom-active-class {
                color: black; /* Example active text color */
                background-color: white !important;
                border: 2px solid black; /* Example border */
                transform: scale(1.05);
            }

            .toggle-sidebar-btn {
                display: none;
            }

            .filter-mother {
                display: flex;
                flex-wrap: wrap; /* Enable wrapping if content overflows */
                gap: 10px;
            }

            .search-box {
                position: relative;
                width: 100%;
                max-width: 300px; /* Adjust as needed */
            }

            .search-box input[type="text"] {
                width: 100%;
                padding: 10px 15px 10px 35px;
                border: 1px solid #ccc;
                border-radius: 5px;
                outline: none;
                font-size: 1em;
                transition: box-shadow 0.2s ease;
            }

            .search-box i {
                position: absolute;
                left: 10px;
                top: 50%;
                transform: translateY(-50%);
                color: #888;
                font-size: 0.9em;
            }

            .search-box input[type="text"]:focus {
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
                border-color: #333;
            }

            .filter-god {
                display: flex;
                flex-wrap: wrap; /* Ensure wrapping across rows */
                align-items: flex-start;
                justify-content: space-between;
                gap: 15px; /* Add spacing between child elements */
            }

            .selected-filters {
                display: flex;
                flex-wrap: wrap; /* Enable wrapping for filters */
                gap: 8px; /* Add spacing between filters */
                max-width: 900px; /* Ensure space for the search box */
                overflow: hidden; /* Prevent overflow */
            }

            .pro {
                padding-right: 0;
            }

            #product-list-item {
                padding-right: 0;
                margin-left: 4px;
            }

            .search-box-header {
                display: none;
            }
        </style>
    </head>
    <body>
        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />

        <!-- Main Product List section -->
        <div class="container">
            <div class="container-fluid mt-4">
                <div class="row align-items-center">
                    <!-- Filter Popup Section -->
                    <div class="filter-god">
                        <div class="filter-mother">
                            <div class="filter-section col-auto" id="filterBtn">
                                <button class="filter-btn">
                                    <i class="fa-solid fa-filter"></i> Filter
                                </button>
                            </div>

                            <div class="col-auto">
                                <div class="selected-filters">
                                    <c:if test="${not empty selectedTags}">
                                        <c:forEach items="${requestScope.tList}" var="t">
                                            <c:forEach items="${selectedTags}" var="selectedTag">
                                                <c:if test="${t.id == selectedTag}">
                                                    <span class="btn category-filter-btn" style="background-color: ${t.colorCode};">Tags: ${t.name}</span>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${not empty selectedCategories}">
                                        <c:forEach items="${requestScope.cList}" var="category">
                                            <c:forEach items="${selectedCategories}" var="selectedCategory">
                                                <c:if test="${category.id == selectedCategory}">
                                                    <span class="btn category-filter-btn" style="background-color: #ccc;">Categories: ${category.name}</span>
                                                </c:if>                                                
                                            </c:forEach>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${not empty selectedMinPrice}">
                                        <span class="btn category-filter-btn" style="background-color: #ccc;">Min Price: <fmt:formatNumber value="${selectedMinPrice}" pattern="###,###,###VND" /></span>
                                    </c:if>
                                    <c:if test="${not empty selectedMaxPrice}">
                                        <span class="btn category-filter-btn" style="background-color: #ccc;">Max Price: <fmt:formatNumber value="${selectedMaxPrice}" pattern="###,###,###VND" /></span>
                                    </c:if>
                                    <c:if test="${not empty searchInput}">
                                        <span class="btn category-filter-btn" style="background-color: #ccc;">Search: ${searchInput}</span>
                                    </c:if>
                                    <c:if test="${not empty selectedMaxPrice || not empty selectedMinPrice || not empty selectedCategories || not empty selectedTags || not empty searchInput}">
                                        <a class="btn category-filter-btn" style="background-color: #ccc;" href="?removeFilter=minPrice">Remove all</a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <form action="productlist" method="get">
                            <div class="search-box">
                                <input type="text" placeholder="Search..." name="searchproduct" value="${searchInput}" required="">
                                <i class="fas fa-search"></i>
                            </div>
                        </form>
                    </div>

                    <!-- Filter Modal -->
                    <div class="modal fade" id="filterModal" tabindex="-1" aria-labelledby="filterModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="filterModalLabel">Filter</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form id="filterForm" action="productlist?type=filter" method="get">
                                        <!-- Tag Section -->
                                        <div class="row mb-4">
                                            <div class="col-12">
                                                <h6>Tag</h6>
                                                <div class="d-flex flex-wrap">
                                                    <c:forEach items="${requestScope.tList}" var="t">
                                                        <button 
                                                            type="button" 
                                                            class="btn category-filter-btn m-1 ${fn:contains(selectedTags, t.id) ? 'my-custom-active-class' : ''}" 
                                                            style="background-color: ${t.colorCode};" 
                                                            data-tag-id="${t.id}">
                                                            ${t.getName()}
                                                        </button>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Category Section -->
                                        <div class="row mb-4">
                                            <div class="col-12">
                                                <h6>Category</h6>
                                                <div class="d-flex flex-wrap">
                                                    <c:forEach items="${requestScope.cList}" var="category">
                                                        <button 
                                                            type="button" 
                                                            class="btn category-filter-btn-cate m-1 ${fn:contains(selectedCategories, category.id) ? 'my-custom-active-class' : ''}" 
                                                            data-category-id="${category.id}">
                                                            ${category.name}
                                                        </button>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Price Range Section -->
                                        <div class="row mb-4">
                                            <div class="col-12">
                                                <h6>Price Range</h6>
                                                <div id="price-range-slider"></div>
                                                <div class="d-flex justify-content-between mt-2">
                                                    <span id="minPriceDisplay">${min} VND</span>
                                                    <span>to</span>
                                                    <span id="maxPriceDisplay">${max} VND</span>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary" id="applyFilter">Apply Filter</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row pro">
                        <!-- Product Cards Section -->
                        <div id="product-list-item">
                            <div class="row row-cols-1 row-cols-md-5 g-4" style="margin-top: 10px;">
                                <c:choose>
                                    <c:when test="${empty requestScope.products}">
                                        <div class="container-fluid w-100" style="margin-top: 55px; margin-bottom: 55px;">
                                            <!-- Feedback Header -->
                                            <div class="bg-dark text-white pt-3 pb-1 text-center align-items-center">
                                                <h4 class="mb-3">Freeze does not find any products like you search: [${searchInput}]</h4>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${requestScope.products}" var="pro">
                                            <a href="productdetail?productId=${pro.productId}" style="text-decoration: none;">
                                                <div class="col">
                                                    <div class="card h-100">
                                                        <div class="image-container">
                                                            <img src="${pro.productThumb}" class="card-img-top" alt="${pro.productName}" width="100">
                                                        </div>
                                                        <div class="card-body">
                                                            <!-- Display product tags -->
                                                            <div class="badge-container">
                                                                <div class="main-badge">
                                                                    <c:forEach items="${pro.tagList}" var="tag">
                                                                        <span class="badge" style="background-color: ${tag.colorCode};">${tag.name}</span>
                                                                    </c:forEach>
                                                                </div>
                                                            </div>
                                                            <h5 class="card-title product-name">${pro.productName}</h5>
                                                            <!-- Display sub-category name -->
                                                            <p class="card-text">${pro.subCategoryName}</p>
                                                            <p class="card-text"><fmt:formatNumber value="${pro.productPrice}" pattern="###,###,###VND" /></p>
                                                            <div class="mt-auto">
                                                                <i class="fa-solid fa-star" style="color: #FFD43B;"> ${pro.star} ( ${pro.countFeedBack} )</i>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--Load More Button-->
        <div class="d-flex justify-content-center align-items-center mt-4">
            <button type="button" class="filter-btn" id="load-more" onclick="updateLoadMoreBtn(this)">Load More Item </button>
        </div>

        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />

        <script>
            $(document).ready(function () {
                // Show the modal when the filter button is clicked
                $('#filterBtn').click(function () {
                    $('#filterModal').modal('show');
                });

                // Apply filter button click
                $('#applyFilter').click(function () {
                    // Gather selected filter data
                    var selectedCategory = $('#category').val();
                    var priceMin = $('#priceMin').val();
                    var priceMax = $('#priceMax').val();

                    // Perform AJAX request or other actions with the filter data here
                    console.log("Category: " + selectedCategory + ", Min Price: " + priceMin + ", Max Price: " + priceMax);

                    // Close the modal after applying filter
                    $('#filterModal').modal('hide');
                });
            });

            var currentPage = ${page};
            var maxProduct = ${productCount};

            function getCurrentProductLoadMore() {
                // Count the number of product items currently displayed
                var currentCount = $("#product-list-item .card").length;
                console.log("Current number of products: " + currentCount);
                console.log(maxProduct - currentCount);
                return maxProduct - currentCount;
            }
            ;

            function updateLoadMoreBtn(loadMoreBtn) {
                // Calculate the remaining number of products to load
                var remainingProducts = getCurrentProductLoadMore();

                // Update the text of the Load More button with the remaining product count
                if (remainingProducts > 0) {
                    $(loadMoreBtn).text(`Load More Item (` + remainingProducts + `)`);
                } else {
                    // Hide or disable the button if there are no more products to load
                    $(loadMoreBtn).text('No more products to load');
                    $(loadMoreBtn).prop('disabled', true);
                }
            }
            ;

            $(document).ready(function () {

                updateLoadMoreBtn($("#load-more"));

                $("#load-more").click(function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/productlist",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: currentPage + 1
                        },
                        success: function (response) {
                            $("#product-list-item").append(response);
                            currentPage++;
                            updateLoadMoreBtn($("#load-more"));
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });
            }
            );

            // Format numbers to "###,###,###VND" format
            function formatCurrency(value) {
                return value.toLocaleString('en-US') + 'VND'; // Format as currency (Vietnamese dong)
            }

            $(document).ready(function () {
                // When the filter button is clicked
                $('#filterBtn').click(function (e) {
                    // Get the position and dimensions of the filter button
                    var buttonOffset = $(this).offset();
                    var buttonHeight = $(this).outerHeight();

                    // Adjust the modal's position dynamically and prepare it for the animation
                    var modal = $('#filterModal');
                    modal.css({
                        top: buttonOffset.top + buttonHeight - 10 + 'px', // Position it right below the button (start slightly above for the slide effect)
                        left: buttonOffset.left + 'px', // Align with the button's left side
                        display: 'block', // Ensure the modal is visible before animation
                        opacity: 0 // Start with it invisible
                    });

                    // Add the 'show' class to trigger the transition
                    setTimeout(function () {
                        modal.css('top', buttonOffset.top + buttonHeight + 10 + 'px'); // Animate to slightly lower position (creates the slide down effect)
                        modal.addClass('show').css('opacity', 1); // Fade in
                    }, 100);
                });

                // Close the modal when the close button is clicked or when clicking outside the modal
                $('.btn-close, [data-bs-dismiss="modal"]').click(function () {
                    closeModal();
                });

                // Function to close the modal with transition
                function closeModal() {
                    var modal = $('#filterModal');

                    // Slide the modal back up and fade out
                    modal.css('top', '-=20px'); // Move up by 20px
                    modal.removeClass('show').fadeOut(300); // Fade out and hide modal
                }

                // Initialize the dual-handle slider
                $("#price-range-slider").slider({
                    range: true, // Enable dual-handle
                    min: ${min}, // Set the minimum value of the range
                    max: ${max}, // Set the maximum value of the range
                    values: [${min}, ${max}], // Initial values for min and max
                    slide: function (event, ui) {
                        // Format and update the displayed price values as the user slides
                        $("#minPriceDisplay").html('<p class="card-text">' + formatCurrency(ui.values[0]) + '</p>');
                        $("#maxPriceDisplay").html('<p class="card-text">' + formatCurrency(ui.values[1]) + '</p>');
                    }
                });

                // Initial formatting when the page loads
                $("#minPriceDisplay").html('<p class="card-text">' + formatCurrency($("#price-range-slider").slider("values", 0)) + '</p>');
                $("#maxPriceDisplay").html('<p class="card-text">' + formatCurrency($("#price-range-slider").slider("values", 1)) + '</p>');

                $('#applyFilter').click(function () {
                    var selectedTags = [];
                    var selectedCategories = [];
                    var selectedMinPrice = $("#price-range-slider").slider("values", 0); // Get min price
                    var selectedMaxPrice = $("#price-range-slider").slider("values", 1); // Get max price

                    // Collect all selected tag IDs
                    $('.category-filter-btn.my-custom-active-class').each(function () {
                        selectedTags.push($(this).data('tag-id'));
                    });

                    // Collect all selected category IDs
                    $('.category-filter-btn-cate.my-custom-active-class').each(function () {
                        selectedCategories.push($(this).data('category-id'));
                    });

                    // Set hidden input fields with the selected filters
                    $('#filterForm').append('<input type="hidden" name="tags" value="' + selectedTags.join(',') + '" />');
                    $('#filterForm').append('<input type="hidden" name="categories" value="' + selectedCategories.join(',') + '" />');
                    $('#filterForm').append('<input type="hidden" name="minPrice" value="' + selectedMinPrice + '" />');
                    $('#filterForm').append('<input type="hidden" name="maxPrice" value="' + selectedMaxPrice + '" />');
                    $('#filterForm').append('<input type="hidden" name="type" value="filter" />');

                    // Debugging output (optional)
                    console.log("Selected Tags: " + selectedTags);
                    console.log("Selected Categories: " + selectedCategories);
                    console.log("Selected Price Range: " + selectedMinPrice + " - " + selectedMaxPrice);

                    //Submit the form to 'productlist?type=filter'
                    $('#filterForm').submit();
                });


                // Toggle active state for selected tag buttons
                $('.category-filter-btn').click(function () {
                    $(this).toggleClass('my-custom-active-class');
                });

                // Toggle active state for selected category buttons
                $('.category-filter-btn-cate').click(function () {
                    $(this).toggleClass('my-custom-active-class');
                });
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/asset/JS/ProductList.js"></script>
    </body>
</html>
