<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${bloglist}" var="b">
    <div class="card-custom">
        <div class="card-body">
            <div class="post-id text-center">
                <p>ID:${b.id}</p>
            </div>
            <div class="post-thumbnail">
                <img src="${b.getBlogImageList().get(0).getLink()}" alt="" width="160" height="150" style="border-radius: 20px;">
            </div>
            <div class="post-title">
                <p><b>Title: </b>${b.title}</p>
            </div>
            <div class="post-cate">
                <p><b>Category: </b>${b.cate.name}</p>
            </div>
            <div class="post-author">
                <p><b>Author: </b>${b.author.authorName}</p>
            </div>
            <div class="post-status">
                <label for="status"><b>Status: </b></label>
                <select id="status" onchange="saveStatus(this, ${b.id})">
                    <c:forEach items="${statuslist}" var="sta">
                        <option <c:if test="${b.status == sta}">selected=""</c:if> >${sta}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <a href="blogdetailmanager?postId=${b.id}">
            <div class="card-footer-custom text-center">
                <p>View Detail</p>
            </div>
        </a>
    </div>
</c:forEach>