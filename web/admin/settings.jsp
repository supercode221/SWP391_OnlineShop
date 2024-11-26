<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users Manager</title>
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

                <!-- User List Header -->
                <div class="d-flex align-items-center">
                    <i class="fa fa-tasks mb-2 p-2" aria-hidden="true"></i>
                    <h3 class="ms-3 fw-bold" style="font-size: 25px;">User List</h3>
                </div>
            </div>
            <div id="item-list" class="table-custom">
                <!-- Search and Filter Bar -->
                <form method="post" action="users">
                    <div class="d-flex align-items-center mt-3 input-group">
                        <input type="hidden" name="action" value="filter"/>

                        <!-- Role Filter -->
                        <select class="form-select" name="roleId">
                            <option value="all" <c:if test="${selectingRoleId == null}">selected</c:if>>All Role</option>
                            <c:forEach var="r" items="${roleList}">
                                <option <c:if test="${selectingRoleId != null and r.id == selectingRoleId}">selected</c:if> value="${r.id}">${r.name}</option>
                            </c:forEach>
                        </select>

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
                            There're no user as your filter!
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="u" items="${users}">
                            <div class="row mb-4 p-3 rounded shadow">
                                <!-- Product Image -->
                                <div class="col-md-3">
                                    <img src="${u.avatarImage}" class="img-fluid rounded" alt="" width="150" height="350"/>
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
                                            <button class="editBtn" onclick="getData(${u.id}, ${u.role.id}, '${u.status}')">Edit</button>
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
                                            <p>Role: ${u.role.name}</p>
                                            <p>Addresses: </p>
                                            <c:choose>
                                                <c:when test="${u.addressList == null}">
                                                    <p class="text-danger">No Address Added!</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach var="a" items="${u.addressList}">
                                                        <p>${a.format}</p>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <!-- Bootstrap Modal for Editing User Info -->
                                <!-- Modal Structure -->
                                <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="editModalLabel">Edit User Information</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <form class="form-group" method="POST" action="users">
                                                <input type="hidden" name="action" value="update"/>
                                                <input type="hidden" name="userId" value="${u.id}"/>
                                                <div class="modal-body">
                                                    <!-- Your form for editing user information -->
                                                    <div class="mb-3">
                                                        <label for="role" class="form-label">Role</label>
                                                        <select class="form-select" name="roleId">
                                                            <c:forEach var="r" items="${roleList}">
                                                                <option value="${r.id}">${r.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="status" class="form-label">Status</label>
                                                        <select class="form-select" name="status">
                                                            <c:forEach var="s" items="${statusList}">
                                                                <option value="${s}">${s}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <!-- Add more input fields as needed -->
                                                </div>
                                            </form>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <button type="button" class="btn btn-primary" onclick="saveChanges(this)">Save changes</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <script>
            var g_page = 1;

            var g_userId;
            var g_roleId;
            var g_status;

            $(document).ready(function () {
                var activeSideBar = $("#setting-list");
                activeSideBar.addClass("active-custom");

                $("#load-more").click(function () {
                    console.log(g_page);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/users",
                        type: "POST",
                        data: {
                            action: "load-more",
                            page: g_page + 1
                        },
                        success: function (response) {
                            $("#item-list").append(response);
                            g_page++;
                        },
                        error: function (xhr, status, error) {
                            alert("Đã xảy ra lỗi trong quá trình cập nhật: " + error);
                        }
                    });
                });
            });

            function saveChanges(button) {
                // Lấy giá trị từ các trường trong form
                var selectedRoleId = $("#editModal select[name='roleId']").val();
                var selectedStatus = $("#editModal select[name='status']").val();
                if (selectedRoleId != g_roleId && g_roleId == 2) {
                    alert("You can't demote an admin!");
                    return;
                }
                if (selectedStatus !== "active" && g_roleId == 2) {
                    alert("You can't inactive an admin!");
                    return;
                }


                g_roleId = $("#editModal select[name='roleId']").val();
                g_status = $("#editModal select[name='status']").val();


                console.log(g_userId);
                console.log(g_roleId);
                console.log(g_status);

                // Sử dụng $.ajax để gửi dữ liệu lên server
                $.ajax({
                    url: "users", // Thay URL này bằng URL endpoint xử lý của bạn (ví dụ: /updateUser)
                    type: "POST",
                    data: {
                        userId: g_userId, // g_userId là biến global lưu userId
                        roleId: g_roleId,
                        status: g_status,
                        action: 'update'
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


            function getData(userId, roldId, status) {
                g_userId = userId;
                g_roleId = roldId;
                g_status = status;
                console.log(g_roleId);
                console.log(g_userId);
                console.log(g_status);

                // Populate modal fields
                var roleSelect = document.querySelector("#editModal select[name='roleId']");
                var statusSelect = document.querySelector("#editModal select[name='status']");

                // Set selected role in the dropdown
                if (roleSelect) {
                    for (let i = 0; i < roleSelect.options.length; i++) {
                        if (roleSelect.options[i].value == g_roleId) {
                            roleSelect.options[i].selected = true;
                            break;
                        }
                    }
                }

                // Set selected status in the dropdown
                if (statusSelect) {
                    for (let i = 0; i < statusSelect.options.length; i++) {
                        if (statusSelect.options[i].value == g_status) {
                            statusSelect.options[i].selected = true;
                            break;
                        }
                    }
                }
                console.log(roleSelect);
                console.log(statusSelect);
            }

            function toggleDetails(button) {
                const detailsContent = button.closest('.row').querySelector('.details-content');

                if (detailsContent.style.height === '0px' || detailsContent.style.height === '') {
                    detailsContent.style.height = detailsContent.scrollHeight + 'px';
                } else {
                    detailsContent.style.height = '0px';
                }
            }

            function showModal() {
                var modal = new bootstrap.Modal(document.getElementById('editModal'), {
                    keyboard: false
                });
                modal.show();
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