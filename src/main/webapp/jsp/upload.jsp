<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:wrapper>
<h2>Select XML file to upload</h2>
<form action="" method="post" enctype="multipart/form-data">
<div><input type="file" name="ffDataFile"/></div>
<div><input type="submit" value="Upload" name="submit"/></div>
</form>
</t:wrapper>
