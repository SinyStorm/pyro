<%@ page import="model.FullInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  успешный вывод средств
--%>
<jsp:include page="header.jsp" flush="true" />
            <div id="content">
            <% if((request.getAttribute("convert"))!="true"){ %>
                <h1>Средства успешно выведены из системы</h1>
                <h2>Вклад № ${id}</h2>
                <p>Текущее состояние: ${amount}</p>
                <p>Выведено средств: ${payment}</p>
                <h2>Данные вкладчика: </h2>
                <p>ФИО: <%=((FullInfo)request.getAttribute("info")).getFullName()%></p>
                <p>Email: <%=((FullInfo)request.getAttribute("info")).getEmail()%></p>
                <p>Реквизиты к выплате: <%=((FullInfo)request.getAttribute("info")).getRequisites()%></p>
                <h2 style="color:#ff0000;">ВНИМАНИЕ:<br /> Средства выведены только из системы! Необходимо совершить банковский перевод вручную!</h2>
            <% } else { %>
                <h1>Бонус успешно переведен в счет основного вклада</h1>
                <h2>Вклад № ${id}</h2>
                <p>Текущее состояние: ${amount}</p>
                <p>Зачеслено: ${bonus}</p>
            <% } %>
            <a class="submit" href="info?id=${id}">Обратно к вкладу</a>
            <a class="submit" href="/">На главную</a>
            </div>
        </div>
    </body>
</html>