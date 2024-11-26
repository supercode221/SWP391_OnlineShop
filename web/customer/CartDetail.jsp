<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>

    <head>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <title>${sessionScope.username}'s Cart</title>
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
        </style>
    </head>

    <body class="bg-light">

        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />

        <!-- Cart Details Process -->
        <div class="container py-4">
            <div class="d-flex justify-content-between align-items-center mb-4">

                <!-- Shopping Cart Header -->
                <div class="d-flex align-items-center">
                    <i class="fas fa-shopping-cart fs-1"></i>
                    <h1 class="ms-4 fw-bold">Shopping Cart</h1>
                </div>

                <!-- Search and Filter Bar -->
                <form method="post" action="cart">
                    <div class="d-flex align-items-center mt-3 input-group">
                        <input type="hidden" name="action" value="search"/>

                        <!-- Category Filter -->
                        <select class="form-select" name="category">
                            <option value="all" <c:if test="${selectingCategoryId == null}">selected</c:if>>All Categories</option>
                            <c:forEach var="category" items="${categories}">
                                <option <c:if test="${selectingCategoryId != null and category.id == selectingCategoryId}" >selected</c:if> value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>

                        <!-- Tag Filter -->
                        <select class="form-select" name="tag">
                            <option value="all" <c:if test="${selectingTagId == null}">selected</c:if>>All Tags</option>
                            <c:forEach var="tag" items="${tags}">
                                <option <c:if test="${selectingTagId != null and tag.id == selectingTagId}" >selected</c:if> value="${tag.id}">${tag.name}</option>
                            </c:forEach>
                        </select>

                        <!-- Text Box Input To Search String --> 
                        <input type="text" class="form-control" placeholder="Search . . . " name="searchInput" value="${searchInput}">

                        <!-- Submit Form Button -->
                        <button type="submit" class="btn btn-outline-secondary">Search</button>
                    </div>
                </form>

                <!-- Edit button -->
                <div class="d-flex align-items-center">
                    <button class="btn btn-primary" onclick="toggleEditMode()">Edit</button>
                </div>
            </div>

            <!-- Displays Items -->
            <div class="bg-white shadow-sm rounded overflow-hidden">
                <c:choose>
                    <c:when test="${cart == null or cart.products == null}">
                        <c:if test="${isSearching == null}">
                            <div class="alert alert-warning">
                                Your cart is empty!
                            </div>
                        </c:if>

                        <c:if test="${isSearching != null}">
                            <div class="alert alert-warning">
                                Your cart is no product like your search: "${realSearchingKeyword}"!
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <div id="cart-items">
                            <c:forEach var="item" items="${cart.products}">
                                <input class="displayed-item" id="displayed-item" type="hidden" name="displayed-item" value="${item.id}" />
                                <div class="p-4">
                                    <div class="d-flex align-items-center">

                                        <!--Checkbox for individual items-->
                                        <input 
                                            id="checkbox-${item.id}" 
                                            name="checkbox" 
                                            class="me-4 form-check-input item-checkbox" 
                                            type="checkbox" 
                                            value="${item.id}" 
                                            data-id="${item.id}" 
                                            data-size="${item.size}" 
                                            data-color="${item.colorName}" 
                                            onchange="updateCheckBox(this, '${item.id}', '${item.size}', '${item.colorName}')" 
                                            <c:if test="${item.isChecked == '1'}">
                                                checked
                                            </c:if> 
                                            />

                                        <div class="d-flex align-items-center flex-grow-1">

                                            <!--Item's Image-->
                                            <img alt="Placeholder image" class="me-4" height="100" src="${item.thumbnailImage}" width="100"/>
                                            <div>

                                                <!--Item's Name-->
                                                <a class="h5 text-primary fw-bold text-decoration-none" href="productdetail?productId=${item.id}">${item.name}</a>

                                                <!--Item's Tag List-->
                                                <div class="mt-2">
                                                    <c:forEach var="tag" items="${item.tagList}">
                                                        <span class="badge" style="background-color: ${tag.colorCode}; margin-top: 7px; margin-bottom: 7px">${tag.name}</span>
                                                    </c:forEach>
                                                </div>

                                                <!--Item's Price-->
                                                <div class="mt-2 h6 fw-bold">
                                                    ${item.des}
                                                </div>
                                                <!--Item's Price-->
                                                <div class="mt-2 h6 fw-bold">
                                                    <fmt:formatNumber value="${item.price}" pattern="###,###,###VND"/>
                                                </div>
                                                <div class="mt-2 h6 fw-bold">
                                                    <p>Size: ${item.getSize()}</p>
                                                </div>
                                                <div class="mt-2 h6 fw-bold">
                                                    <p>Color: ${item.getColorName()} 
                                                        <span class="badge" style="background-color: ${item.getColorCode()};
                                                              display: inline-block;
                                                              width: 15px;
                                                              height: 15px;
                                                              border: 1px solid #000;
                                                              margin-left: 5px;
                                                              box-shadow: 0 0 5px #ccc"></span>
                                                    </p>
                                                </div>

                                                <!--Quantity Process-->
                                                <div class="d-flex align-items-center mt-2">
                                                    <button class="btn btn-light" onclick="decreaseQuantity(this,${item.stock})">-</button>
                                                    <input 
                                                        id="quantity" 
                                                        class="mx-2 form-control text-center quantity-input" 
                                                        style="width: 80px;" 
                                                        type="number" 
                                                        min="0" 
                                                        max="<c:if test="${item.stock > 0}">${item.stock}</c:if><c:if test="${item.stock == 0}">0</c:if>" 
                                                        value="<c:if test="${item.quantity <= item.stock}">${item.quantity}</c:if><c:if test="${item.stock == 0}">0</c:if>" 
                                                        onchange="quantityChange(this,${item.id}, '${item.size}', '${item.colorName}')"/>
                                                    <button class="btn btn-light" onclick="increaseQuantity(this,${item.stock})">+</button>
                                                    <c:if test="${item.stock == 0}">
                                                        <p class="text-danger text-success align-items-center ms-1 mt-3">Out of stock!</p>
                                                    </c:if>
                                                    <c:if test="${item.stock != 0}">
                                                        <p class="text-warning align-items-center ms-1 mt-3">Stock Left: ${item.stock}</p>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-column gap-2 d-none edit-buttons">
                                            <form action="cart" method="post">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="colorName" value="${item.getColorName()}">
                                                <input type="hidden" name="size" value="${item.getSize()}">
                                                <button class="btn btn-danger w-100" name="productId" value="${item.id}">Delete</button>
                                            </form>
                                            <form action="cart" method="post">
                                                <input type="hidden" name="action" value="similar-items">
                                                <button class="btn btn-dark w-100" name="productId" value="${item.id}">Similar Products</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>             
                    </c:otherwise>
                </c:choose>
            </div>

            <!--Load More Button-->
            <div class="d-flex justify-content-center align-items-center mt-4">
                <button class="btn btn-secondary" id="load-more">Load More</button>
            </div>

            <!--Fixed bottom bar-->
            <div class="fixed-bottom bg-white shadow-sm p-4">
                <div class="d-flex justify-content-between align-items-center">

                    <!-- Select All Checkbox -->
                    <div class="d-flex align-items-center">
                        <input 
                            class="me-2 form-check-input" 
                            id="select-all" 
                            type="checkbox" 
                            onchange="selectAllItems(this)" 
                            />
                        <label for="select-all" class="text-dark">Select All</label>
                    </div>

                    <!--Total Price Display-->
                    <div class="d-flex align-items-center">
                        <span class="h5 fw-bold me-4">Total: <span id="total-price">0</span>VND</span>

                        <!-- Buy Button -->
                        <form id="buy-form" action="cart" method="post">
                            <input type="hidden" name="action" value="buy" />
                            <input type="hidden" id="selectedProductIds" name="selectedProductIds" value="" />
                            <input type="hidden" id="quantities" name="quantities" value="" />
                            <input type="hidden" id="buy-displayed-item" name="displayedItem" value="" />
                            <button  type="submit" class="btn btn-success">Buy</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />

        <script>

            let canLoadMore;

            function updateCheckBox(checkbox, itemId, itemSize, itemColorName) {
                updateTotalPrice();
                // Get the new checked status from the checkbox
                const isChecked = $(checkbox).is(':checked') ? 1 : 0;

                // Make an AJAX request to update the checked status on the server
                $.ajax({
                    url: 'cart', // Replace with your server endpoint for updating the checkbox status
                    type: 'POST',
                    data: {
                        action: "update-checkbox",
                        itemId: itemId,
                        size: itemSize,
                        colorName: itemColorName,
                        isChecked: isChecked
                    },
                    success: function (response) {
                        // Optionally, handle the response, e.g., updating the UI or showing a success message
                        console.log("Checkbox updated successfully:", response);
                    },
                    error: function (xhr, status, error) {
                        // Handle errors, like showing an error message
                        console.error("Failed to update checkbox:", error);
                    }
                });
            }

            function quantityChange(input, productId, productSize, productColorName) {
                // Handle success, like updating the total price
                updateTotalPrice();

                // Get the new quantity from the input
                const newQuantity = $(input).val();

                // Make an AJAX request to update the quantity on the server
                $.ajax({
                    url: 'cart', // Replace with your server endpoint for updating quantity
                    type: 'POST',
                    data: {
                        action: "update-quantity",
                        productId: productId,
                        size: productSize,
                        colorName: productColorName,
                        quantity: newQuantity
                    },
                    success: function (response) {

                        // Optionally, you can also handle any UI changes or show a success message
                        console.log("Quantity updated successfully:", response);
                    },
                    error: function (xhr, status, error) {
                        // Handle errors, like showing an error message
                        console.error("Failed to update quantity:", error);
                    }
                });
            }

            function submitBuyForm() {
                
                var selectedProductIds = [];
                $('#cart-items .item-checkbox:checked').each(function () {
                    selectedProductIds.push($(this).val());
                });
                var quantities = [];
                $('#cart-items .quantity-input').each(function () {
                    quantities.push($(this).val());
                });
                var allDisplayedItemIds = [];
                $('#cart-items .displayed-item').each(function () {
                    allDisplayedItemIds.push($(this).val());
                });

                console.log(selectedProductIds);
                console.log(quantities);
                console.log(allDisplayedItemIds);

                $('#selectedProductIds').val(selectedProductIds.join(','));
                $('#quantities').val(quantities.join(','));
                $('#buy-displayed-item').val(allDisplayedItemIds.join(','));

                $('#buy-form').submit();
            }

            var currentPage = ${page};
            function toggleEditMode() {
                var editButtons = document.querySelectorAll('.edit-buttons');
                editButtons.forEach(function (buttonGroup) {
                    buttonGroup.classList.toggle('d-none');
                });
            }

            function increaseQuantity(button, stock) {
                var input = button.previousElementSibling;
                var value = parseInt(input.value, 10);
                value = isNaN(value) ? 0 : value;
                value = stock == 0 ? 0 : value;
                value++;
                value = value >= stock ? stock : value;
                input.value = value;
                updateQuantity(input); // Call the updated updateQuantity function with the input field
            }

            function decreaseQuantity(button, stock) {
                var input = button.nextElementSibling;
                var value = parseInt(input.value, 10);
                value = isNaN(value) ? 0 : value;
                value--;
                value < 0 ? value = 0 : '';
                input.value = value;
                updateQuantity(input); // Call the updated updateQuantity function with the input field
            }

            function updateQuantity(input) {
                const newQuantity = $(input).val();
                const productId = $(input).closest('.p-4').find('.item-checkbox').val();
                const itemSize = $(input).closest('.p-4').find('input[name="size"]').val();
                const itemColorName = $(input).closest('.p-4').find('input[name="colorName"]').val();

                $.ajax({
                    url: 'cart',
                    type: 'POST',
                    data: {
                        action: "update-quantity",
                        productId: productId,
                        size: itemSize,
                        colorName: itemColorName,
                        quantity: newQuantity
                    },
                    success: function (response) {
                        console.log("Quantity updated successfully:", response);
                        updateTotalPrice(); // Update total price after successful quantity change
                    },
                    error: function (xhr, status, error) {
                        console.error("Failed to update quantity:", error);
                    }
                });
            }

            function formatNumber(num) {
                return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
            }

            function updateTotalPrice() {
                var totalPrice = 0;
                $('#cart-items .item-checkbox:checked').each(function () {
                    var quantity = parseInt($(this).closest('.p-4').find('input[type="number"]').val(), 10);
                    var price = $(this).closest('.p-4').find('.h6').text().replace('VND', '').replace('.', '').replace('.', '');
                    totalPrice += quantity * parseInt(price) * 1000;
                });
                $('#total-price').text(formatNumber(totalPrice));
            }

            function selectAllItems(selectAllCheckbox) {
                // Lấy danh sách tất cả các checkbox của các sản phẩm
                const itemCheckboxes = document.querySelectorAll('.item-checkbox');

                // Kiểm tra trạng thái của checkbox "Select All"
                const isChecked = selectAllCheckbox.checked;

                // Duyệt qua tất cả các checkbox sản phẩm
                itemCheckboxes.forEach((checkbox) => {
                    // Cập nhật trạng thái checkbox (bật hoặc tắt)
                    checkbox.checked = isChecked;

                    // Lấy thông tin từ các thuộc tính data-*
                    const itemId = checkbox.getAttribute('data-id');
                    const itemSize = checkbox.getAttribute('data-size');
                    const itemColor = checkbox.getAttribute('data-color');

                    // Gọi hàm updateCheckBox cho từng checkbox
                    updateCheckBox(checkbox, itemId, itemSize, itemColor);
                });
            }

            $(document).ready(function () {

                canLoadMore = ${canLoadMore};
                if (canLoadMore == false) {
                    $("#load-more").css("display", "none");
                }

                $("#load-more").click(function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/cart",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: currentPage + 1
                        },
                        success: function (response) {
                            $("#cart-items").append(response);
                            currentPage++;
                            updateTotalPrice();

                            if (canLoadMore == false) {
                                $("#load-more").css("display", "none");
                            }
                        },
                        error: function (xhr, status, error) {
                            console.log("Error: " + error);
                        }
                    });
                });
                updateTotalPrice();
            }
            );
        </script>
    </body>
</html>
