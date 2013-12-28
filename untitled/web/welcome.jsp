<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sid
  Date: 15.12.13
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
    <div id="wrapper">
        <center>
            <h1>Для начала работы с системой - авторизуйтесь</h1>
            <h1 class="warning">${error}</h1>
            <form action="login" method="POST">
                <input type="text" name="login">
                <input type="password" name="pwd">
                <input type="submit" />
            </form>
        </center>
    </div>
</body>
</html>