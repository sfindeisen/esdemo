<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ElasticSearch Demo</title>
<link rel="stylesheet" type="text/css" href="./css/main.css"/>
</head>
<body>
<h1 id="main-title">Welcome to ElasticSearch Demo!</h1>
<div id="menu">
<ul id="menu-list">
<li><a href="./search">Search</a></li>
<li><a href="./upload">Upload data</a></li>
<li><a href="./delete">Delete data</a></li>
<li><a href="./status">Status</a></li>
</ul>
</div>
<div id="main">
<c:if test="${null != errorMsg}"><p class="error"><c:out value="${errorMsg}"/></p></c:if>
<c:if test="${null != infoMsg}"><p class="info"><c:out value="${infoMsg}"/></p></c:if>
<jsp:doBody/>
</div>
</body>
</html>
