<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customers</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="icon" href="${pageContext.request.contextPath}/asset/Image/Favicon/icon2.gif" type="image/gif">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/footer.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/header.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/CSS/sidebar.css"/>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            * a {
                text-decoration: none;
                color: black;
            }

            body {
                background-color: #ffffff;
                font-family: Arial, sans-serif;
            }

            .table-custom {
                width: 100%;
                margin: 20px 0;
                border-collapse: collapse;
            }

            .table-custom table {
                width: 100%;
                border: 1px solid #ddd;
                border-radius: 5px;
                overflow: hidden;
            }

            .table-custom td {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
                color: black;
            }

            .table-custom th {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
                color: #e94560;
            }

            .table-custom th {
                background-color: #f4f4f4;
                font-weight: bold;
            }

            .table-custom tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .table-custom tr:hover {
                background-color: #f1f1f1;
            }

            .table-custom td {
                border-right: 1px solid #ddd;
            }

            .table-custom td:last-child {
                border-right: none;
            }

            .btn button{
                border: none;
                background-color: black;
                color: white;
                transition: ease-in-out 0.2s;
                padding: 10px;
                border-radius: 5px;
            }

            .btn button:hover{
                background-color: white;
                color: black;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            .test p {
                margin-bottom: 2px;
            }

            .input-group {
                margin-bottom: 20px;
            }

            .details-content {
                overflow: hidden;
                height: 0;
                transition: height 0.5s ease;
            }

            .modal-content {
                transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
            }

            .modal.fade .modal-content {
                transform: scale(0.9);
                opacity: 0;
            }

            .modal.show .modal-content {
                transform: scale(1);
                opacity: 1;
            }

        </style>
    </head>

    <body>
        <!-- Header import section -->
        <div class="content" id="content">
            <jsp:include page="../base-component/header.jsp" />
        </div>

        <jsp:include page="../base-component/sidebar.jsp" />

        <div class="content1" id="content1">
            <div class="d-flex justify-content-between align-items-center mb-4">

                <!-- Customer List Header -->
                <div class="d-flex align-items-center">
                    <i class="fa fa-tasks mb-2 p-2" aria-hidden="true"></i>
                    <h3 class="ms-3 fw-bold" style="font-size: 25px;">Customer List</h3>
                </div>
            </div>
            <div id="item-list" class="table-custom">
                <!-- Search and Filter Bar -->
                <form method="post" action="customers">
                    <div class="d-flex align-items-center mt-3 input-group">
                        <input type="hidden" name="action" value="filter"/>

                        <!-- Status Filter -->
                        <select class="form-select" name="status">
                            <option value="all" <c:if test="${selectingStatus == null}">selected</c:if>>All Status</option>
                            <c:forEach var="s" items="${statusList}">
                                <option <c:if test="${selectingStatus != null and s == selectingStatus}" >selected</c:if> value="${s}">${s}</option>
                            </c:forEach>
                        </select>

                        <!-- Text Box Input To Search String --> 
                        <input type="text" class="form-control w-25" placeholder="Search . . . " name="searchInput" value="${searchInput}">

                        <!-- Submit Form Button -->
                        <button type="submit" class="btn btn-outline-secondary">Search</button>
                    </div>
                </form>
                <c:choose>
                    <c:when test="${users == null}">
                        <div class="alert alert-warning">
                            There're no customers as your filter!
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="u" items="${users}">
                            <div class="row mb-4 p-3 rounded shadow">
                                <!-- Product Image -->
                                <div class="col-md-3">
                                    <img src="${u.avatarImage}" class="img-fluid rounded" alt="" width="150" height="150" style="
                                         max-width: 150px;
                                         max-height: 150px
                                         "/>
                                </div>

                                <!-- Product Details (Vertically Centered) -->
                                <div class="test col-md-6 d-flex align-items-center">
                                    <div>
                                        <h5>${u.lastName} ${u.firstName}</h5>
                                        <p>${u.email}</p>
                                        <c:choose>
                                            <c:when test="${u.phone != null and u.phone != 'null'}">
                                                Phone Number: <span>${u.phone} </span>
                                            </c:when>
                                            <c:otherwise>
                                                Phone Number: <span class="text-danger">No Phone Number Provided!</span>
                                            </c:otherwise>
                                        </c:choose>
                                        <br/>
                                        <c:choose>
                                            <c:when test="${u.status == 'active'}">
                                                Status: <span class="text-success text-capitalize">${u.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                Status: <span class="text-dark">${u.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <!-- Actions (View Details / Remove) -->
                                <div class="col-md-3 d-flex align-items-center">
                                    <div class="btn">
                                        <a href="javascript:void(0);" onclick="toggleDetails(this);">
                                            <button>Detail</button>
                                        </a>
                                        <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#editModal">
                                            <button class="editBtn" onclick="getData(${u.id})">Edit</button>
                                        </a>
                                    </div>
                                </div>

                                <!-- Hidden details section -->
                                <div class="details-content" style="height: 0; overflow: hidden; transition: height 0.5s ease; margin-top: 10px;">
                                    <div class="row">
                                        <div class="col-3">
                                            <img src="${pageContext.request.contextPath}/asset/Image/preppy-aesthetic-fashion-collage-desktop-wallpaper-4k.jpg" class="img-fluid rounded" alt="" width="150" height="350"/>
                                        </div>
                                        <div class="col-6">
                                            <p>Role: customer</p>
                                            <p>Addresses: </p>
                                            <c:choose>
                                                <c:when test="${u.addressList == null}">
                                                    <p class="text-danger">No Address Added!</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach var="a" items="${u.addressList}">
                                                        <p class="form-control">${a.format}</p>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
            <c:if test="${filtering == null}">
                <div class="btn">
                    <a>
                        <button id="load-more">
                            Load More
                        </button>
                    </a>
                </div>
            </c:if>
        </div>

        <!-- Bootstrap Modal for Editing User Info -->
        <!-- Modal Structure -->
        <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Edit User Information</h5>
                        <button type="button" class="btn-close" aria-label="Close" data-bs-dismiss="modal" data-bs-target="#editModal"></button>
                    </div>
                    <form class="form-group" method="POST" action="customers">
                        <input type="hidden" name="action" value="update"/>
                        <input type="hidden" name="userId" value="${u.id}"/>
                        <div class="modal-body">
                            <!-- Your form for editing user information -->
                            <div class="mb-3">
                                <label for="email" class="form-label">Email:</label>
                                <input type="email" class="form-control" id="emailField" name="email" value="" required readonly disabled>
                            </div>
                            <div class="mb-3">
                                <label for="firstName" class="form-label">First Name:</label>
                                <input type="text" class="form-control" id="firstNameField" name="firstName" value="" required>
                            </div>
                            <div class="mb-3">
                                <label for="lastName" class="form-label">Last Name:</label>
                                <input type="text" class="form-control" id="lastNameField" name="lastName" value="" required>
                            </div>
                            <div class="mb-3">
                                <label for="mobile" class="form-label">Phone Number:</label>
                                <input type="text" class="form-control" id="mobileField" name="mobile" value="" required>
                                <small id="mobileError" class="text-danger" style="display:none;">Invalid phone number. Please enter a valid Vietnamese phone number.</small>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address:</label>
                                <div class="input-group">
                                    <select class="form-select" name="address" id="addressField" required>
                                        <!-- wait input from js -->
                                    </select>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="details" class="form-label">Chi Tiết:</label>
                                <input type="text" class="form-control" id="detailsField" name="details" value="" placeholder="Chi Tiết Địa Chỉ" readonly disabled>
                            </div>
                            <div class="mb-3">
                                <label for="note" class="form-label">Ghi Chú:</label>
                                <input type="text" class="form-control" id="noteField" name="note" value="" placeholder="Ghi Chú Giao Hàng, Địa Chỉ" readonly disabled>
                            </div>
                            <!-- Add more input fields as needed -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="hideEditModal()">Close</button>
                            <button type="button" class="btn btn-primary" onclick="saveChanges(this)">Save changes</button>
                        </div>
                    </form>
                </div>
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
        <script>
            let canloadmore;

            var g_page = 1;

            // customer
            // name, address, mobile, 
            let g_userId;

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

                    $('#addressModal').modal('hide');

                    $.ajax({
                        url: '${pageContext.request.contextPath}/customers',
                        method: 'POST',
                        data: {
                            action: 'addAddress',
                            city: province,
                            details: details,
                            note: note,
                            district: district,
                            ward: ward,
                            userId: g_userId
                        },
                        success: function (response) {
                            console.log(response);
                            if (response.success) {
                                alert("Địa chỉ đã được lưu thành công!");
                            } else {
                                alert("Không thể lưu địa chỉ.");
                            }
                            // Reload the page regardless of the outcome
                            getData(g_userId);
                        },
                        error: function () {
                            alert("Lỗi xảy ra khi lưu địa chỉ.");
                        }
                    });

                    $('#xa').prop('selectedIndex', 0); // Đặt về option đầu tiên
                    $('#quan').prop('selectedIndex', 0); // Đặt về option đầu tiên
                    $('#tinh').prop('selectedIndex', 0); // Đặt về option đầu tiên
                    $('#note').val(''); // Xóa nội dung text trong trường ghi chú
                    $('#details').val(''); // Xóa nội dung text trong trường chi tiết
                });

                canloadmore = '${canloadmore ? 'true' : 'false'}';
                console.log("canloadmore? " + canloadmore);
                $("#load-more").click(function () {
                    console.log(g_page);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/customers",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: g_page + 1
                        },
                        success: function (response) {
                            $("#item-list").append(response);
                            g_page++;
                            if (canloadmore !== 'true') {
                                $('#load-more').hide();
                            }
                        },
                        error: function (xhr, status, error) {
                            alert("Không còn khách hàng nào khác");
                        }
                    });
                });
                if (canloadmore !== 'true') {
                    $('#load-more').hide();
                }
            });

            $(document).ready(function () {
                var activeSideBar = $("#customer-list");
                activeSideBar.addClass("active-custom");
            });

            function saveChanges(button) {
                // Lấy giá trị từ các trường trong form
                var selectedAddress = $("#editModal select[name='address']").val();
                var firstName = $("#firstNameField").val();
                var lastName = $("#lastNameField").val();
                var mobile = $("#mobileField").val();

                if (firstName.trim() === '') {
                    alert('First Name cannot be blank.');
                    return;
                }

                if (lastName.trim() === '') {
                    alert('Last Name cannot be blank.');
                    return;
                }

                if (mobile !== 'No phone number provided!') {
                    const regexWithCountryCode = /^\+84\d{9}$/;
                    const regexWithZero = /^0\d{9}$/;
                    if (!regexWithCountryCode.test(mobile) && !regexWithZero.test(mobile)) {
                        alert('Invalid phone number. Please enter a valid Vietnamese phone number.');
                        return;
                    }
                }

                // Sử dụng $.ajax để gửi dữ liệu lên server
                $.ajax({
                    url: "customers", // Thay URL này bằng URL endpoint xử lý của bạn (ví dụ: /updateUser)
                    type: "POST",
                    data: {
                        action: 'update',
                        userId: g_userId,
                        firstName: firstName,
                        lastName: lastName,
                        phoneNumber: mobile,
                        addressId: selectedAddress
                    }, // Dữ liệu được gửi dưới dạng JSON
                    success: function (response) {
                        // Xử lý khi cập nhật thành công
                        alert("Dữ liệu đã được cập nhật thành công!");
                        // Đóng modal
                        var modal = bootstrap.Modal.getInstance(document.querySelector('#editModal'));
                        modal.hide();
                        window.location.reload(true);
                        // Có thể thực hiện reload hoặc cập nhật giao diện sau khi lưu thành công
                    },
                    error: function (xhr, status, error) {
                        // Xử lý khi xảy ra lỗi
                        alert("Đã xảy ra lỗi trong quá trình cập nhật: " + error);
                    }
                });
            }


            function getData(userId) {

                g_userId = userId;

                $.ajax({
                    url: "${pageContext.request.contextPath}/customers",
                    type: "POST",
                    data: {
                        action: "load-user-info",
                        userId: userId
                    },
                    success: function (response) {
                        console.log(response);
                        $('#emailField').val(response.email);
                        $('#firstNameField').val(response.firstName);
                        $('#lastNameField').val(response.lastName);
                        $('#mobileField').val(response.phone ? response.phone : 'No phone number provided!');
                        $('#addressField').empty();
                        if (response.addressList && response.addressList.length > 0) {
                            response.addressList.forEach(function (address) {
                                $('#addressField').append(`<option value="` + address.id + `"data-details="` + address.details + `"data-note="` + address.note + `">` + address.format + `</option>`);
                            });
                        } else {
                            $('#addressField').append('<option value="0">No address available</option>');
                        }
                        $('#addressField').append('<option value="add-new">Add New Address</option>');
                    },
                    error: function (xhr, status, error) {
                        alert("Lỗi trong quá trình tải lên thông tin khách hàng.");
                    }
                });
            }

            function toggleDetails(button) {
                const detailsContent = button.closest('.row').querySelector('.details-content');

                if (detailsContent.style.height === '0px' || detailsContent.style.height === '') {
                    detailsContent.style.height = detailsContent.scrollHeight + 'px';
                } else {
                    detailsContent.style.height = '0px';
                }
            }

            function showEditModal() {
                var editModal = new bootstrap.Modal(document.getElementById('editModal'), {
                    keyboard: false
                });
                editModal.show();
            }

            function showAddressModal() {
                var addressModal = new bootstrap.Modal(document.getElementById('addressModal'), {
                    keyboard: false
                });
                addressModal.show();
            }

            const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
            const sidebar = document.getElementById('sidebar');
            const content = document.getElementById('content');
            const content1 = document.getElementById('content1');
            const navbar = document.getElementById('navbar');

            toggleSidebarBtn.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
                content.classList.toggle('collapsed');
                content1.classList.toggle('collapsed');
                navbar.classList.toggle('collapsed');
                toggleSidebarBtn.classList.toggle('collapsed');
            });
        </script>
    </body>
</html>

</body>
</html>
