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
        <title>CSV Uploading Form</title>
    </head>

    <body>
        <h3>CSV file Upload:</h3>
        Select a .cvs file to upload: <br />
        <form action = "./postcvs" method = "post" enctype = "multipart/form-data">
            <input type = "file" name = "file" size = "50" />
            <br />
            <input type = "submit" value = "Upload File" />
        </form>
    </body>
</html>
