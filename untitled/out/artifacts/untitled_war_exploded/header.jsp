<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
 шапка админки
--%>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>${title}</title>
        <style>
            <%@include file='css/style.css' %>
        </style>
        <script type="text/javascript">
            <%@include file='js/jquery-1.7.1.min.js' %>
        </script>
    </head>
    <body>
        <div id="wrapper">
            <div id=#header">
                <ul id="main-menu">
                    <li>
                        <a href="deposites?type=ready">Вклады, готовые к инвестированию</a>
                    </li><li>
                        <a href="deposites?type=invested">Инвестированные вклады</a>
                    </li><li>
                        <a href="deposites?type=waiting">Вклады в ожидании</a>
                    </li><li>
                        <a href="deposites?type=frozen">Долгосрочные вложения</a>
                    </li><li>
                        <a href="deposites?type=hot">Горящие!<span class="warning">(${hot})</span></a>
                    </li>
                </ul>
            </div>
