<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.DepositesRow" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="config.Configs" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
 Показ таблиц с депозитами
--%>

<jsp:include page="header.jsp" flush="true" />
            <script type="text/javascript">
                $(document).ready(function(){
                    $("#main-menu li:nth-child(${menuindex}) a").addClass('active');
                });
            </script>
            <table class="list">
                <thead>
                    <th>Текущий размер вклада</th>
                    <th>Начальный вклад</th>
                    <th>Начисленный бонус</th>
                    <th>Статус вклада</th>
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
                    <td><%out.print(Configs.STATUS[result.getStatus()]); %></td>
                    <td><%out.print(formatter.format(result.getDateOfDep())); %></td>
                    <td><%if(result.getDateOfRet()!=null)out.print(formatter.format(result.getDateOfRet())); %></td>
                    <td><a class="button" title="Подробная информация" href="info?id=<%out.print(result.getId()); %>">?</a></td>
                 </tr>
                <% } %>
            </table>
        </div>
    </body>
</html>