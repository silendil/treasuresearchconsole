<%--
  Created by IntelliJ IDEA.
  User: silendil
  Date: 08.03.2016
  Time: 2:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Loading maze</title>
</head>
<body>
    <form method="post" enctype="multipart/form-data" action="loadfile/process">
        <label>Choose file</label>
        <input type="file" name="file" />
        <button type="submit"><label>load a file</label></button>
    </form>
</body>
</html>
