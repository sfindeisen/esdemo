<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t"  tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="sres" scope="page" value="${esd_search_results}"/>
<c:set var="hits" scope="page" value="${sres.hits.hits}"/>

<t:wrapper>
<%@include file="search-form.jsp" %>
<h2>Search results</h2>
<p>Got <c:out value="${fn:length(hits)}"/> hits in <c:out value="${sres.getTookInMillis()}"/> ms.</p>
<c:if test="${1 <= fn:length(hits)}">
<table id="search-results">
<tr>
<th>id</th>
<th>score</th>
<th>type</th>
<th></th>
</tr>
<c:forEach items="${hits}" var="item">
<tr>
<td><a href="view?id=<c:out value="${item.id}"/>"><c:out value="${item.id}"/></a></td>
<td><c:out value="${item.score}"/></td>
<td><c:out value="${item.type}"/></td>
<td>
<c:forEach items="${item.getHighlightFields().values()}" var="hf">
<c:forEach items="${hf.getFragments()}" var="frag">
<%-- TODO escapeXml=false is too much - we only need to retain <em>, still want to escape & etc. --%>
<c:out value="${frag}" escapeXml="false"/>
</c:forEach>
</c:forEach>
</td>
</tr>
</c:forEach>
</table>
</c:if>
</t:wrapper>
