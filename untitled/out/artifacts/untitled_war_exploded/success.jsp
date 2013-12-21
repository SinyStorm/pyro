<%--
успешное выполнение операции
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" flush="true" />
            <div id="content">
                <h1>${result}</h1>
                <a class="submit" href="info?id=${id}">Обратно к вкладу</a>
                <a class="submit" href="/">На главную</a>
            </div>
        </div>
    </body>
</html>