<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 28-Nov-19
  Time: 9:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Set Cookies Form</title>
</head>
<body>
<form action = "./setcookies" method = "POST">
    Cookie name: <input type = "text" name = "cookie_name">
    <br />
    Cookie value: <input type = "text" name = "cookie_value" />
    <input type = "submit" value = "Submit" />
</form>
</body>
</html>
