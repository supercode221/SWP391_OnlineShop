

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <title>Cart Contact</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <style>
            .product-list {
                max-height: 300px;
                overflow-y: auto;
            }
            .checkout-info, .payment-options, .order-summary {
                background-color: #f8f9fa;
                border: 1px solid #ddd;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            * a {
                text-decoration: none;
                color: black;
            }
        </style>
    </head>
    <body>
        <!-- Header import section -->
        <jsp:include page="../base-component/header.jsp" />

        <!-- Main Form Start -->
        <form id="paymentForm" action="checkoutUser" method="post" class="needs-validation" novalidate>
            <div class="container mt-5">
                <div class="row g-4">
                    <!-- Information Section -->
                    <div class="col-md-4">
                        <div class="checkout-info p-4 rounded">
                            <h2 class="mb-3">Contact Information</h2>
                            <c:if test="${not empty user}">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email:</label>
                                    <input type="email" class="form-control" id="emailField" name="email" value="${user.email}" disabled="">
                                </div>
                                <div class="mb-3">
                                    <label for="fullName" class="form-label">Full Name:</label>
                                    <input type="text" class="form-control" id="fullNameField" name="fullName" value="${user.getFirstName()} ${user.getLastName()}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="mobile" class="form-label">Phone Number:</label>
                                    <input type="text" class="form-control" id="mobileField" name="mobile" value="${user.phoneNumber}" required>
                                    <small id="mobileError" class="text-danger" style="display:none;">Invalid phone number. Please enter a valid Vietnamese phone number.</small>
                                </div>
                                <div class="mb-3">
                                    <label for="address" class="form-label">Address:</label>
                                    <div class="input-group">
                                        <select 
                                            class="form-select" 
                                            name="address" 
                                            id="addressField" 
                                            required>
                                            <c:if test="${empty addresses}">
                                                <option value="null" selected>No address available</option>
                                            </c:if>
                                            <c:forEach var="address" items="${addresses}">
                                                <option value="${address.id}" 
                                                        data-details="${address.details}" 
                                                        data-note="${address.note}">
                                                    ${address.format}
                                                </option>
                                            </c:forEach>
                                            <option value="add-new">Add New Address</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="details" class="form-label">Chi Tiết:</label>
                                    <input type="text" class="form-control" id="detailsField" name="details" value="" placeholder="Chi Tiết Địa Chỉ" required>
                                </div>
                                <div class="mb-3">
                                    <label for="note" class="form-label">Ghi Chú:</label>
                                    <input type="text" class="form-control" id="noteField" name="note" value="" placeholder="Ghi Chú Giao Hàng, Địa Chỉ">
                                </div>


                            </c:if>
                        </div>
                    </div>

                    <!-- Payment Section -->
                    <div class="col-md-3">
                        <div class="payment-options p-4 rounded">
                            <h3 class="mb-3">Payment Method</h3>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="vnpay" value="VNPAY" required>
                                <label class="form-check-label" for="vnpay">VNPAY</label>
                            </div>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="cod" value="COD" required>
                                <label class="form-check-label" for="cod">Cash on Delivery (COD)</label>
                            </div>
                            <small class="text-muted">Select your preferred payment method.</small>
                        </div>
                    </div>

                    <!-- Order Section -->
                    <div class="col-md-5">
                        <div class="order-summary p-1 rounded">
                            <h3 class="p-3">Order Summary</h3>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th scope="col">Image</th>
                                        <th scope="col">Product</th>
                                        <th scope="col">Price</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Size</th>
                                        <th scope="col">Color</th>
                                    </tr>
                                </thead>
                                <tbody class="product-list">
                                    <c:if test="${requestScope.cartDetails != null}">
                                        <c:forEach items="${requestScope.cartDetails.getProducts()}" var="c">
                                            <c:if test="${c.isChecked == '1' and c.quantity > 0}">
                                                <tr>
                                                    <td><img src="${c.thumbnailImage}" alt="Product Image" width="80" height="80"></td>
                                                    <td>
                                                        <input type="hidden" name="productName" value="${c.name}">
                                                        ${c.name}
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="productPrice" value="${c.price}">
                                                        <fmt:formatNumber value="${c.price}" pattern="###,###,### VND"/>
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="productQuantity" value="${c.quantity}">
                                                        ${c.quantity}
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="productSize" value="${c.size}">
                                                        ${c.size}
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="productColor" value="${c.colorName}">
                                                        ${c.colorName}
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>
                            <div class="total text-end p-3">
                                <h4 class="text-primary">Total:</h4>
                                <p><strong><fmt:formatNumber value="${cartTotal}" pattern="###,###,### VND"/></strong></p>
                                <input type="hidden" name="cartTotal" value="${cartTotal}">
                            </div>
                            <!-- Change and Submit Buttons -->
                            <div class="d-flex justify-content-between mx-2 mb-2">
                                <a href="cart" class="btn btn-outline-secondary">Back to Cart</a>
                                <button type="button" onclick="submitPaymentForm()" class="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <!-- Address Modal -->
        <div class="modal fade" id="addressModal" tabindex="-1" aria-labelledby="addressModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addressModalLabel">Add Address</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body w-100">
                        <div class="css_select_div">
                            <select class="css_select form-select w-100" id="tinh" name="tinh" title="Chọn Tỉnh Thành" required>
                                <option value="0">Tỉnh Thành</option>
                            </select> <br/>
                            <select class="css_select form-select w-100" id="quan" name="quan" title="Chọn Quận Huyện" required>
                                <option value="0">Quận Huyện</option>
                            </select> <br/>
                            <select class="css_select form-select w-100" id="xa" name="xa" title="Chọn Xã Phường" required>
                                <option value="0">Xã Phường</option>
                            </select> <br/>
                            <input type="text" class="form-control w-100" id="details" name="details" placeholder="Thêm Chi Tiết Địa Chỉ" required> <br/>
                            <input type="text" class="form-control w-100" id="note" name="note" placeholder="Ghi Chú">
                        </div>
                        <div id="existingAddresses" class="mt-3"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="selectAddress">Add Address</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).on('click', '.select-saved-address', function (e) {
                e.preventDefault();
                var selectedAddress = $(this).data('address');
                $('#addressField').val(selectedAddress);
                $('#addressIdField').val($(this).data('address-id'));
                $('#addressModal').modal('hide');
            });

            $(document).ready(function () {
                // Fetch provinces on page load
                $.ajax({
                    url: 'https://provinces.open-api.vn/api/p/',
                    method: 'GET',
                    success: function (data) {
                        $('#tinh').empty().append('<option value="0">Tỉnh Thành</option>');
                        data.forEach(function (item) {
                            $('#tinh').append("<option value='" + item.code + "'>" + item.name + "</option>");
                        });
                    },
                    error: function () {
                        alert('Không thể tải danh sách tỉnh/thành phố.');
                    }
                });

                // Fetch districts based on selected province
                $('#tinh').on('change', function () {
                    var provinceCode = $(this).val();
                    $('#quan').empty().append('<option value="0">Quận Huyện</option>');
                    $('#xa').empty().append('<option value="0">Xã Phường</option>');

                    if (provinceCode && provinceCode !== "0") {
                        $.ajax({
                            url: "https://provinces.open-api.vn/api/p/" + provinceCode + "?depth=2",
                            method: 'GET',
                            success: function (data) {
                                let dataS = data.districts;
                                dataS.forEach(function (item) {
                                    $('#quan').append('<option value="' + item.code + '">' + item.name + '</option>');
                                });
                            },
                            error: function () {
                                alert('Không thể tải danh sách quận/huyện.');
                            }
                        });
                    }
                });

                // Fetch wards based on selected district
                $('#quan').on('change', function () {
                    var districtCode = $(this).val();
                    $('#xa').empty().append('<option value="0">Xã Phường</option>');

                    if (districtCode && districtCode !== "0") {
                        $.ajax({
                            url: "https://provinces.open-api.vn/api/d/" + districtCode + "?depth=2",
                            method: 'GET',
                            success: function (data) {
                                let dataS = data.wards;
                                dataS.forEach(function (item) {
                                    $('#xa').append('<option value="' + item.code + '">' + item.name + '</option>');
                                });
                            },
                            error: function () {
                                alert('Không thể tải danh sách xã/phường.');
                            }
                        });
                    }
                });

                // Show modal when clicking "Add Address"
                $('#addressField').on('change', function () {
                    const selectedOption = $(this).find(':selected'); // Get the selected option
                    const selectedValue = $(this).val();

                    if (selectedValue === 'add-new') {
                        // Open modal for adding a new address
                        $('#addressModal').modal('show');
                    } else {
                        // Update details and note fields based on selected address
                        const details = selectedOption.data('details') || '';
                        const note = selectedOption.data('note') || '';

                        $('#detailsField').val(details);
                        $('#noteField').val(note);
                    }
                });



                // Handle address selection
                $('#selectAddress').on('click', function () {
                    var ward = $('#xa option:selected').text();
                    var district = $('#quan option:selected').text();
                    var province = $('#tinh option:selected').text();
                    var note = $('#note').val();
                    var details = $('#details').val();
                    var fullAddress = ward + ", " + district + ", " + province;
                    var selectedAddressId = $(this).data('address-id');

                    $('#addressField').val(fullAddress);
                    $('#addressModal').modal('hide');

                    $.ajax({
                        url: '${pageContext.request.contextPath}/saveAddress',
                        method: 'POST',
                        data: {
                            addressLine: fullAddress,
                            city: province,
                            details: details,
                            note: note,
                            district: district,
                            ward: ward
                        },
                        success: function (response) {
                            console.log(response);
                            if (response.success) {
                                alert("Địa chỉ đã được lưu thành công!");
                            } else {
                                alert("Không thể lưu địa chỉ.");
                            }
                            // Reload the page regardless of the outcome
                            location.reload();
                        },
                        error: function () {
                            alert("Lỗi xảy ra khi lưu địa chỉ.");
                        }
                    });
                });

                $('form').on('submit', function (e) {
                    var paymentMethodSelected = $('input[name="paymentMethod"]:checked').length > 0;
                    if (!paymentMethodSelected) {
                        e.preventDefault();
                        alert('Please select a payment method.');
                    } else if ($('input[name="paymentMethod"]:checked').val() === 'COD') {

                    }
                });
            });

            function isValidEmail(email) {
                // Regex kiểm tra định dạng email
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                return emailRegex.test(email);
            }

            function validateForm() {
                var fullNameField = document.getElementById('fullNameField');
                var emailField = document.getElementById('emailField');
                var addressField = document.getElementById('addressField');
                var mobileField = document.getElementById('mobileField');
                var mobileError = document.getElementById('mobileError');

                // Lấy giá trị của các trường
                var fullName = fullNameField.value.trim();
                var email = emailField.value.trim();
                var mobile = mobileField.value.trim();
                var address = addressField.value;

                // Kiểm tra Full Name
                if (fullName === "") {
                    alert('Full Name cannot be blank.');
                    fullNameField.focus();
                    return false;
                }

                // Kiểm tra Email
                if (!isValidEmail(email)) {
                    alert('Please enter a valid email address.');
                    emailField.focus();
                    return false;
                }

                // Kiểm tra số điện thoại
                if (!isValidVietnamPhoneNumber(mobile)) {
                    mobileError.style.display = 'block';
                    mobileField.focus();
                    return false;
                } else {
                    mobileError.style.display = 'none';
                }

                // Kiểm tra Address
                if (address === "null") {
                    alert('Please select or add a valid address.');
                    addressField.focus();
                    return false;
                }

                // Nếu tất cả hợp lệ, return true
                return true;
            }

            function submitPaymentForm() {
                if (validateForm()) {
                    $("#paymentForm").submit();
                }
            }

            function isValidVietnamPhoneNumber(phone) {
                const regexWithCountryCode = /^\+84\d{9}$/;
                const regexWithZero = /^0\d{9}$/;
                return regexWithCountryCode.test(phone) || regexWithZero.test(phone);
            }


        </script>





        <!-- Footer import section -->
        <jsp:include page="../base-component/footer.jsp" />
    </body>
</html>


