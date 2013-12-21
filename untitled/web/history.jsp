<%@ page import="model.HistoryRow" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
полная история
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" flush="true" />
            <div id="content">
                <h1>Вклад № ${id}</h1>
                <h2>Полная история изменений</h2>
                <a class="submit" href="info?id=${id}">Обратно к вкладу</a>
                <a class="submit" href="/">На главную</a>
            </div>
            <table id="history">
                <tr>
                    <td>Дата</td>
                    <td>Комментарий</td>
                    <td>Начислено бонуса</td>
                </tr>
                <%
                    SimpleDateFormat formatter = new SimpleDateFormat ("s");
                    formatter.applyPattern("dd-MM-yyyy");
                    for(HistoryRow result: (ArrayList< HistoryRow>)request.getAttribute("history")) { %>
                        <tr>
                            <td><%out.print(formatter.format(result.getOperationDate())); %></td>
                            <td><%out.print(result.getComment()); %></td>
                            <td><%out.print(result.getBonus()); %></td>
                        </tr>
                <% } %>
            </table>
            <div align="center">
            <a class="submit" href="info?id=${id}">Обратно к вкладу</a>
            <a class="submit" href="/">На главную</a>
            </div>
</body>
</html>