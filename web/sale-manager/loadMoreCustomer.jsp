<%-- 
    Document   : loadMoreCustomer
    Created on : Nov 23, 2024, 2:38:25 AM
    Author     : Nguyễn Nhật Minh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                    <p>Addressed: </p>
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
    </div>
</c:forEach>
<script>
    canloadmore = '${canloadmore}';
</script>
