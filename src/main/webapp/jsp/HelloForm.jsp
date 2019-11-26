<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 26-Nov-19
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HelloForm</title>
</head>
<body>
    <form action = "./helloform" method = "POST">
        First Name: <input type = "text" name = "first_name">
        <br />
        Last Name: <input type = "text" name = "last_name" />
        <input type = "submit" value = "Submit" />
    </form>
</body>
</html>
