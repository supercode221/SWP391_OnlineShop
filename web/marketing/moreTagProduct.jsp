<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<span id="tag-${t.id}-pro-${p}" class="badge" style="background-color: ${t.colorCode}; gap: 5px;">
    ${t.name} 
    <i class="fas fa-times" style="cursor: pointer;" onclick="removeTag(${t.id}, ${p})"></i>
</span>