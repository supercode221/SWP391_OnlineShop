<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${requestScope.blog}" var="b">
    <div class="col">
        <div class="blog-main h-100">
            <a href="blogdetail?blogId=${b.id}" class="text-decoration-none text-black">
                <div class="blog-header">
                    <c:forEach items="${b.blogImageList}" var="bMed">
                        <c:if test="${bMed.type == 'thumbnail'}">
                            <img src="${bMed.link}" class="blog-image img-fluid">
                        </c:if>
                    </c:forEach>
                </div>
                <div class="blog-body">
                    <h4 class="blog-title">${b.title}</h4>
                    <p class="blog-minides">${b.miniDescription}</p>
                </div>
                <div class="blog-footer">
                    <p class="date">${b.createAt}</p>
                </div>
            </a>
        </div>
    </div>
</c:forEach>