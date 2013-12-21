<%@ page import="model.FullInfo" %>
<%@ page import="model.DepositesRow" %>
<%@ page import="config.Configs" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.HistoryRow" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  показ полной информации по вкладу
--%>
<jsp:include page="header.jsp" flush="true" />

<%
    DepositesRow dr = (DepositesRow)request.getAttribute("deposite");
    SimpleDateFormat formatter = new SimpleDateFormat ("s");
    formatter.applyPattern("dd-MM-yyyy");
%>
            <script type="text/javascript">
                <%@include file='js/accordeon.js' %>
            </script>
            <script type="text/javascript">
                <%@include file='js/jquery.inputmask.js' %>
            </script>
            <script type="text/javascript">
                $(document).ready(function(){
                    $("#percent").inputmask({
                        "mask": "9",
                        "repeat": 3,
                        "greedy": false
                    });
                });
            </script>
            <div id="column-right">
                <h3>Операции с вкладом: </h3>
                <ul>
                    <li><a id="inv" class="button" href="#">Инвестирование</a></li>
                    <div id="invest">
                        <form action="change-status">
                            <input type="hidden" value="<%out.print(dr.getId()); %>" name="id" />
                            <input type="hidden" value="invest" name="type" />
                            <p>Введите цель инвестирования</p>
                            <textarea name="comment"></textarea>
                            <input type="submit" style="display:none" id="inv-btn" />
                            <a class="submit" onclick="
                                if($('#invest textarea').val()==''){
                                    alert('Необходимо ввести цель инвестирования!');
                                }else{
                                    $('#inv-sbmt').fadeIn();
                                }
                                return false;"
                            >
                                Инвестировать
                            </a>
                        </form>
                    </div>
                    <li><a id="bon" class="button" href="#">Начисление бонуса</a></li>
                    <div id="bonus">
                        <form action="change-status">
                            <input type="hidden" value="<%out.print(dr.getId()); %>" name="id" />
                            <input type="hidden" value="bonus" name="type" />
                            <p>Введите начисляемый процент</p>
                            <input id="percent" type="text" name="bonus" />
                            <input type="submit" style="display:none" id="bon-btn" />
                            <a class="submit" onclick="
                                var p=$('#percent').val();
                                if(p==''){
                                    alert('Необходимо ввести размер бонуса!');
                                    return false;
                                }
                                if(p>=5){
                                    $('#bon-sbmt h2').text('Насление бонуса: '+ p + '%');
                                    $('#bon-sbmt').fadeIn();
                                }
                                else{
                                   alert('Бонус должен быть не менее 5%!');
                                }
                                return false;"
                             >
                                Начислить
                            </a>
                        </form>
                    </div>
                    <li><a id="fr" class="button" href="#">"Заморозка" вклада</a></li>
                    <div id="freeze">
                        <form action="change-status">
                            <input type="hidden" value="<%out.print(dr.getId()); %>" name="id" />
                            <input type="hidden" value="freeze" name="type" />
                            <p>Введите причину замораживания вклада</p>
                            <textarea name="comment"></textarea>
                            <input type="submit" style="display:none" id="fr-btn" />
                            <a class="submit" onclick="
                                if($('#freeze textarea').val()==''){
                                    alert('Необходимо ввести причину!');
                                }else{
                                    $('#fr-sbmt').fadeIn();
                                }
                                return false;"
                                    >
                                Заморозить
                            </a>
                        </form>
                    </div>
                    <li><a class="button" onclick="$('#dep-lead-out').fadeIn();">Вывести вклад</a></li>
                    <li><a class="button" onclick="$('#bon-lead-out').fadeIn();">Вывести бонус</a></li>
                    <li><a class="button" onclick="$('#bon-con').fadeIn();">Перевести бонус во вклад</a></li>
                </ul>
            </div>
            <div id="content">
                <h1>Вклад № <%=dr.getId()%> ( <%=formatter.format(dr.getDateOfDep())%>)</h1>
                <p>Текущее состояние:  <%=dr.getAmount()%></p>
                <p>Текущий бонус:  <%=dr.getBonus()%></p>
                <p>Начальное вложение:  <%=dr.getStartAmount()%></p>
                <h2>Статус вклада:  <%=Configs.STATUS[dr.getStatus()]%></h2>
                <% if(dr.getDateOfRet()!=null){ %>
                    <h3>
                        Ожидает вознаграждения в период c <%=formatter.format(dr.getDateOfRet())%> по
                        <%
                            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
                            calendar.setTime(dr.getDateOfRet());
                            calendar.add(Calendar.DAY_OF_MONTH,5);
                            out.print(formatter.format(calendar.getTime()));
                        %>
                    </h3>
                <% } %>
                <table id="info">
                    <tr>
                        <td>Полное имя вкладчика: </td>
                        <td><%=((FullInfo)request.getAttribute("info")).getFullName()%></td>
                    </tr>
                    <tr>
                        <td>Электронная почта: </td>
                        <td><%=((FullInfo)request.getAttribute("info")).getEmail()%></td>
                    </tr>
                    <tr>
                        <td>Реквизиты: </td>
                        <td><%=((FullInfo)request.getAttribute("info")).getRequisites()%></td>
                    </tr>
                </table>
            </div>
            <div style="clear: both;">
            <h2 style="margin-left: 20px">Краткая история</h2>
            <table id="history">
                <thead>
                    <th>Дата</th>
                    <th>Комментарий</th>
                    <th>Начислено бонуса</th>
                </thead>
                <% for(HistoryRow result: (ArrayList< HistoryRow>)request.getAttribute("history")) { %>
                    <tr>
                        <td><%out.print(formatter.format(result.getOperationDate())); %></td>
                        <td><%out.print(result.getComment()); %></td>
                        <td><%out.print(result.getBonus()); %></td>
                    </tr>
                <% } %>
            </table>
            <a href="history?id=<%out.print(dr.getId()); %>">Полная история операций...</a>
        </div>
        <%-- сообщения с запросом подтверждения действий --%>
        <div class="alert" id="inv-sbmt">
            <div class="back"></div>
            <div class="message-outer">
                <div class="message-inner">
                    <h2>Вклад будет инвестирован</h2>
                    <h3>Вы уверены, что хотите продолжить?</h3>
                    <a class="submit" href="#" onclick="$('#inv-btn').click();">Подтвердить</a>
                    <a class="submit" href="#" onclick="$('.alert').fadeOut();return false;">Отмена</a>
                </div>
            </div>
        </div>
        <div class="alert" id="bon-sbmt">
            <div class="back"></div>
            <div class="message-outer">
                <div class="message-inner">
                    <h2></h2>
                    <h3>Вы уверены, что хотите продолжить?</h3>
                    <a class="submit" href="#" onclick="$('#bon-btn').click();">Подтвердить</a>
                    <a class="submit" href="#" onclick="$('.alert').fadeOut();return false;">Отмена</a>
                 </div>
            </div>
        </div>
        <div class="alert" id="fr-sbmt">
            <div class="back"></div>
            <div class="message-outer">
                <div class="message-inner">
                    <h2>Вклад будет заморожен</h2>
                    <h3>Вы уверены, что хотите продолжить?</h3>
                    <a class="submit" href="#" onclick="$('#fr-btn').click();">Подтвердить</a>
                    <a class="submit" href="#" onclick="$('.alert').fadeOut();return false;">Отмена</a>
                </div>
            </div>
        </div>
        <div class="alert" id="dep-lead-out">
            <div class="back"></div>
            <div class="message-outer">
                <div class="message-inner">
                    <h2>Вклад будет выведен из системы</h2>
                    <h3>Вы уверены, что хотите продолжить?</h3>
                    <a class="submit" href="lead-out?id=<%out.print(dr.getId()); %>&type=out">Подтвердить</a>
                    <a class="submit" href="#" onclick="$('.alert').fadeOut();return false;">Отмена</a>
                </div>
            </div>
        </div>
        <div class="alert" id="bon-lead-out">
            <div class="back"></div>
            <div class="message-outer">
                <div class="message-inner">
                    <h2>Бонус будет выведен из системы</h2>
                    <h3>Вы уверены, что хотите продолжить?</h3>
                    <a class="submit" href="lead-out?id=<%out.print(dr.getId()); %>&type=bonus">Подтвердить</a>
                    <a class="submit" href="#" onclick="$('.alert').fadeOut();return false;">Отмена</a>
                </div>
            </div>
        </div>
        <div class="alert" id="bon-con">
            <div class="back"></div>
            <div class="message-outer">
                <div class="message-inner">
                    <h2>Бонус будет переведен в счет вклада</h2>
                    <h3>Вы уверены, что хотите продолжить?</h3>
                    <a class="submit" href="lead-out?id=<%out.print(dr.getId()); %>&type=convert">Подтвердить</a>
                    <a class="submit" href="#" onclick="$('.alert').fadeOut();return false;">Отмена</a>
                </div>
            </div>
        </div>
    </body>
</html>