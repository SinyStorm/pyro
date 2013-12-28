

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.DepositesRow" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" flush="true" />
<% if(((request.getAttribute("hot")).toString()).compareTo("0")!=0){ %>
<div id="content">
    <h1>${ResourceBundle.WELCOME}</h1>

    <h2 class="warning">У Вас ${hot} "горящих" вкладов!</h2> ${ResourceBundle.WELCOME}
</div>
<table class="list">
    <thead>
    <th>${ResourceBundle.AMOUNT}</th>
    <th>${ResourceBundle.START_AMOUNT}</th>
    <th>${ResourceBundle.BONUS}</th>
    <th>${ResourceBundle.DATE}</th>
    <th>${ResourceBundle.RETURN_DATE}</th>
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
        <td><a class="info" title="${ResourceBundle.MORE}" href="info?id=<%out.print(result.getId()); %>">?</a></td>
    </tr>
    <% } %>
</table>
<%} else{ %>
<h1 align="center">${ResourceBundle.WELCOME}</h1>

<% } %>
</div>
</body>
</html>





