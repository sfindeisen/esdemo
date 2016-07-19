<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    pageContext.setAttribute("eshost", com.eisenbits.esdemo.Constants.ElasticSearchHost);
    pageContext.setAttribute("esport", com.eisenbits.esdemo.Constants.ElasticSearchPort);
    pageContext.setAttribute("esdidx", com.eisenbits.esdemo.Constants.IndexName);
%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:wrapper>
<h2>ElasticSearch connection and status</h2>
<table id="cluster-health">
<tr><td>host</td><td>${eshost}</td></tr>
<tr><td>port</td><td>${esport}</td></tr>
<tr><td>index name</td><td>${esdidx}</td></tr>
<tr><td>cluster status</td><td>${esd_cluster_health}</td></tr>
</table>
</t:wrapper>
