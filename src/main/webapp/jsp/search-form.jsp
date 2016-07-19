<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="" method="post" autocomplete="on">
<fieldset>
<legend>Name</legend>
<div><input type="text"     name="ffNameTerm" value="<c:out value="${param.ffNameTerm}"/>" placeholder="Search term..."/></div>
<div><input type="checkbox" name="ffNameFull" id="ffNameFull" <c:if test="${null!=param.ffNameFull}">checked="checked"</c:if>/>Full text?</div>
</fieldset>
<fieldset>
<legend>Description</legend>
<div><input type="text"     name="ffDescTerm" value="<c:out value="${param.ffDescTerm}"/>" placeholder="Search term..."/></div>
<div><input type="checkbox" name="ffDescFull" id="ffDescFull" <c:if test="${null!=param.ffDescFull}">checked="checked"</c:if>/>Full text?</div>
</fieldset>
<fieldset>
<legend>Max price</legend>
<div><input type="text"     name="ffMaxPrice" value="<c:out value="${param.ffMaxPrice}"/>" placeholder="max price"/></div>
</fieldset>
<div><input type="submit"   name="submit" value="Search"/></div>
</form>
