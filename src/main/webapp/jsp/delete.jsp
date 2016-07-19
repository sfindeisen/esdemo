<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    pageContext.setAttribute("esdidx", com.eisenbits.esdemo.Constants.IndexName);
%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:wrapper>
<h2>This will drop the index (<c:out value="${esdidx}"/>)</h2>
<form action="" method="post">
<div><input type="submit" value="Delete all documents" name="submit"/></div>
</form>
</t:wrapper>
