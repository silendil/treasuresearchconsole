<%--
  Created by IntelliJ IDEA.
  User: silendil
  Date: 08.03.2016
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Show result of loading</title>
</head>
<body>

    <H1><spring:message code="${result}"/></H1>
    <form action="../show" method="get">
        <button type="submit"><label>show</label></button>
    </form>
</body>
</html>
