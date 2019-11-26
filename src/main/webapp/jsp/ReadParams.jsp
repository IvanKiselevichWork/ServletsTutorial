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
    <title>ReadParams</title>
</head>
<body>
    <form action = "./readparams" method = "POST">
        First Name: <input type = "text" name = "first_name"> <br/>
        Last Name: <input type = "text" name = "last_name" /> <br/>
        <input type = "checkbox" name = "maths" checked = "checked" /> Maths <br/>
        <input type = "checkbox" name = "physics"  /> Physics <br/>
        <input type = "checkbox" name = "chemistry" checked = "checked" /> Chemistry <br/>
        <input type = "submit" value = "Submit" /> <br/>
    </form>
</body>
</html>
