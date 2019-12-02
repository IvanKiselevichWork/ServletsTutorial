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
    <title>Send email</title>
</head>
<body>
    <form action = "./sendemail" method = "POST">
        Recipient email: <input type = "text" name = "email">
        <br />
        Subject: <input type = "text" name = "subject" />
        <br />
        Message: <input type = "text" name = "message" />
        <input type = "submit" value = "Submit" />
    </form>
</body>
</html>
