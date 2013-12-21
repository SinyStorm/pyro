
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.DepositesRow" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" flush="true" />
<% if(((request.getAttribute("hot")).toString()).compareTo("0")!=0){ %>
<div id="content">
    <h1>Добро пожаловать в администраторскую зону!</h1>

    <h2 class="warning">У Вас ${hot} горящих вкладов!</h2>
</div>
<table class="list">
    <thead>
    <th>Текущий размер вклада</th>
    <th>Начальный вклад</th>
    <th>Начисленный бонус</th>
    <th>Вклад внесен</th>
    <th>Дата погашения обязательств</th>
    </thead>
    <%
        SimpleDateFormat formatter = new SimpleDateFormat ("s");
        formatter.applyPattern("dd-MM-yyyy");
        for(DepositesRow result: (ArrayList<DepositesRow>)request.getAttribute("results")) {
    %>
    <tr>
        <td><%out.print(result.getAmount()); %></td>
        <td><%out.print(result.getStartAmount()); %></td>
        <td><%out.print(result.getBonus()); %></td>
        <td><%out.print(formatter.format(result.getDateOfDep())); %></td>
        <td><%if(result.getDateOfRet()!=null)out.print(formatter.format(result.getDateOfRet())); %></td>
        <td><a class="info" title="Подробная информация" href="info?id=<%out.print(result.getId()); %>">?</a></td>
    </tr>
    <% } %>
</table>
<%} else{ %>
<h1 align="center">Добро пожаловать в администраторскую зону!</h1>
<% } %>
</div>
</body>
</html>





