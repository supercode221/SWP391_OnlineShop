<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css" />
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">

        <style>
            /* Layout styling */
            body, html {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
                overflow-y: hidden;
            }

            * a {
                text-decoration: none;
                color: black;
            }

            .main-user-profile {
                margin: 48px auto;
                display: flex;
                /*justify-content: space-around;*/
                gap: 20px;
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

            .user-profile {
                background-color: white;
                box-shadow: 0 0 5px #ccc;
                padding: 20px;
                border-radius: 10px;
                /*height: 300px;*/
            }

            form {
                display: flex;
                align-items: center;
                /*height: 60vh;*/
                justify-content: space-around;
                /*height: 260px;*/
            }

            .user-information .user-input {
                border: solid 1px #ccc;
                padding: 3px 10px;
                width: 400px;
                margin-left: 20px;
                border-radius: 6px;
            }

            .user-information select {
                border: solid 1px #ccc;
                padding: 3px 10px;
                width: 400px;
                margin-left: 20px;
                border-radius: 6px;
            }

            .file-input {
                padding: 3px 10px;
                margin-left: 13px;
                border-radius: 6px;
            }

            .save-btn {
                display: none;
                margin-top: 20px;
            }

            .save-btn button {
                border: none;
                background-color: black;
                color: white;
                border-radius: 10px;
                padding: 5px 20px;
                font-weight: bold;
                transition: ease-in-out 0.2s;
            }

            .save-btn button:hover {
                background-color: white;
                color: black;
                box-shadow: inset 0 0 0 1px black;
            }

            .toggle-sidebar-btn {
                display: none;
            }
        </style>
    </head>

    <body>
        <jsp:include page="../base-component/header.jsp" />

        <%
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
        %>
        <script>
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: '<%= successMessage %>',
                confirmButtonText: 'OK'
            });
        </script>
        <%
            session.removeAttribute("successMessage");
            }
        %>

        <!-- Main layout container -->
        <div class="container main-user-profile">
            <!-- Sidebar -->
            <div class="side">
                <a href="userprofilecontroller" class="dropdown-item selected">User Profile</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="billlist" class="dropdown-item">My Orders</a></c:if>
                <c:if test="${sessionScope.roleId == 1}"><a href="refund" class="dropdown-item">Refund Request</a></c:if>
                    <a href="changepassword" class="dropdown-item">Change Password</a>
                <c:if test="${sessionScope.roleId == 1}"><a href="addresses" class="dropdown-item">Addresses</a></c:if>
                    <a href="LogOut" class="dropdown-item">Logout</a>
                </div>

                <!-- User Profile Form Content -->
                <div class="user-profile container-fluid">
                    <form id="editProfileForm" action="EditProfileController" method="post" onsubmit="return validateForm()">
                        <!-- Avatar -->
                        <div class="user-avatar">
                            <img id="avatarImageField" src="${user.getAvatarImage()}" class="rounded-circle mb-3" alt="User Profile Picture" width="150" height="150">
                    </div>

                    <!-- Hidden input for base64 image -->
                    <input type="hidden" id="avatarBase64" name="avatarBase64">

                    <div class="user-information">
                        <table>
                            <div class="mb-3">
                                <tr>
                                    <td><label for="avatarInput">Avatar</label></td>
                                    <td>
                                        <input id="avatarInput" oninput="handleFileUpload(this)" class="user-input file-input form-control" type="file" accept="image/*">
                                    </td>
                                </tr>
                            </div>

                            <div class="mb-3">
                                <tr>
                                    <td><label for="firstName">First Name</label></td>
                                    <td><input oninput="inputHandler(this)" class="user-input form-control" type="text" id="firstNameField" name="firstName" required value="${user.getFirstName()}"></td>
                                </tr>
                            </div>

                            <div class="mb-3">
                                <tr>
                                    <td><label for="lastName">Last Name</label></td>
                                    <td><input oninput="inputHandler(this)" class="user-input form-control" type="text" id="lastNameField" name="lastName" required value="${user.getLastName()}"></td>
                                </tr>                                
                            </div>

                            <div class="mb-3">
                                <tr>
                                    <td><label for="email">Email</label></td>
                                    <td><input oninput="inputHandler(this)" class="user-input form-control" type="email" id="email" name="email" value="${user.getEmail()}" required disabled readonly></td>
                                </tr>
                            </div>

                            <div class="mb-3">
                                <tr>
                                    <td><label for="phoneNumber">Phone Number</label></td>
                                    <td><input oninput="inputHandler(this)" class="user-input form-control" type="text" id="mobileField" name="phoneNumber" value="${user.getPhoneNumber()}"></td>
                                </tr>
                            </div>

                            <div class="mb-3">
                                <tr>
                                    <td><label for="address">Address</label></td>
                                    <td>
                                        <select id="addressField" class="form-select">
                                            <c:if test="${empty addresses}">
                                                <option value="0">No address available</option>
                                            </c:if>
                                            <c:forEach items="${addresses}" var="a">
                                                <option value="${a.id}">${a.format} </option>
                                            </c:forEach>
                                            <option value="add-new">Add New Address</option>
                                        </select>

                                    </td>
                                </tr>
                            </div>
                        </table>

                        <div class="save-btn text-end">
                            <button type="button" onclick="submitProfileForm()">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

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

        <jsp:include page="../base-component/footer.jsp" />

        <script>

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
                        inputHandler();
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
            });

            function validateForm() {
                // Retrieve form fields
                const firstName = document.getElementById("firstNameField").value.trim();
                const lastName = document.getElementById("lastNameField").value.trim();
                const phoneNumber = document.getElementById("mobileField").value.trim();
                const address = document.getElementById("addressField").value.trim();

                // Validation rules
                if (!firstName) {
                    alert("First name cannot be empty.");
                    return false;
                }
                if (!lastName) {
                    alert("Last name cannot be empty.");
                    return false;
                }
                if (!phoneNumber) {
                    alert("Phone number cannot be empty.");
                    return false;
                }
                if (!isValidVietnamPhoneNumber(phoneNumber)) {
                    alert("Phone number must be a valid Vietnamese phone number.");
                    return false;
                }
                if (address === "0" || address === "") {
                    alert("Please select a valid address.");
                    return false;
                }

                // If everything is valid, submit the form
                return true;
            }

            function handleFileUpload(input) {
                const file = input.files[0];
                const maxFileSize = 200 * 1024 * 1024; // 200MB

                if (!file) {
                    alert("No file selected.");
                    return;
                }

                if (file.size > maxFileSize) {
                    alert("File size exceeds 200MB!");
                    input.value = ""; // Reset the file input
                    return;
                }

                const reader = new FileReader();
                reader.onload = function (e) {
                    const base64Image = e.target.result;
                    // Update avatar image
                    document.getElementById("avatarImageField").src = base64Image;

                    // Update hidden input with base64 data
                    document.getElementById("avatarBase64").value = base64Image;
                };

                reader.readAsDataURL(file); // Convert file to base64
                inputHandler();
            }

            function submitProfileForm() {
                if (validateForm()) {
                    document.getElementById("editProfileForm").submit();
                }
            }

            function inputHandler(input) {
                $(".save-btn").fadeIn();
            }

            window.addEventListener("scroll", function () {
                const side = document.querySelector(".side");
                const scrollPoint = 190; // Adjust based on your layout
                console.log(window.scrollY);

                if (window.scrollY >= scrollPoint) {
                    side.classList.add("fixed");
                } else {
                    side.classList.remove("fixed");
                }
            });


            function isValidVietnamPhoneNumber(phone) {
                const regexWithCountryCode = /^\+84\d{9}$/;
                const regexWithZero = /^0\d{9}$/;
                return regexWithCountryCode.test(phone) || regexWithZero.test(phone);
            }

        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
