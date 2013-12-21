package model.db;

/**
Функции запросов к БД
 */

import com.mysql.jdbc.Driver;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import config.Configs;
import model.DepositesRow;
import model.FullInfo;
import model.HistoryRow;
import static java.sql.DriverManager.*;

public class DBOperations {
    static SimpleDateFormat formatter = new SimpleDateFormat ("s");
    static{formatter.applyPattern("yyyy-MM-dd"); }

    /**
     * Возвращает список вкладов
     * @param query - запрос на выборку
     * @return - возвращает набор строк из БД
     */
    public static ArrayList<DepositesRow> getFromDB(String query, Statement stmt){
        try {
            ResultSet rs =  stmt.executeQuery(query);
            ArrayList<DepositesRow> al = new ArrayList<DepositesRow>();
            while(rs.next()){
                al.add( new DepositesRow(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("amount"),
                        rs.getInt("startAmount"),
                        rs.getInt("bonus"),
                        rs.getInt("status"),
                        rs.getDate("dateOfDep"),
                        rs.getDate("dateOfRet")
                ));
            }
            return al;
        } catch (SQLException e) {
           return null;
        }
    }

    /**
     * количество вкладов, у которых истекает период исполнения финансовых обязательств
     * @param stmt
     * @return количество вкладов
     */
    public static int getHotCount(Statement stmt){
        try{
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
            calendar.add(Calendar.DAY_OF_MONTH,5);
            Date deadLine = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat ("s");
            formatter.applyPattern("yyyy-MM-dd");
            String query = "SELECT count(id) FROM deposites WHERE dateOfRet <='" + formatter.format(deadLine)+"'";
            registerDriver(new Driver());
            ResultSet rs =  stmt.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            return -1;
        }
    }

    /**
     *
     * @param depId - уникальный идентификатор вклада
     * @param limit - количество выводимых строк
     * @param stmt -
     * @return история изменений
     */
    public static ArrayList<HistoryRow> getHistory(int depId, int limit, Statement stmt){
        try {
            String query = "SELECT * FROM history WHERE depId="+depId+" order by `id` DESC";
            if(limit>0)query+=" LIMIT "+limit;
            ResultSet rs =  stmt.executeQuery(query);
            ArrayList<HistoryRow> history= new ArrayList<HistoryRow>();
            while(rs.next()){
                history.add(new HistoryRow(
                        rs.getDate("operationDate"),
                        rs.getString("comment"),
                        rs.getInt("bonus")
                ));
            }
            return history;
        } catch (SQLException e) {
            return null;
        }
    }
     public static void main(String[] args) throws SQLException {
         //System.out.println(getHotCount());
      }

    /**
     *
     * @param status -
     * @param depId - уникальный идентификатор вклада
     * @param stmt -
     * @return успешное/неуспешное выполнение операции
     */
    public static boolean updateDepStatus(int status, int depId, Statement stmt){
        try {
            String query = "UPDATE deposites SET status ="+status+" WHERE id="+depId;
            stmt.executeUpdate(query);
            query = "INSERT INTO history (depId, operationDate, `comment`) values ("+depId+", '"+formatter.format((new java.util.Date()))+"', 'Статус вклада изменен на "+Configs.STATUS[status]+"')";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * информация по вкладу
     * @param depId - уникальный идентификатор вклада
     * @param stmt
     * @return объект Вклад
     */
    public static DepositesRow getDepById(int depId, Statement stmt){
        try {
            String query = "SELECT * FROM deposites WHERE id="+depId;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return new DepositesRow(
                    rs.getInt("id"),
                    rs.getInt("userId"),
                    rs.getInt("amount"),
                    rs.getInt("startAmount"),
                    rs.getInt("bonus"),
                    rs.getInt("status"),
                    rs.getDate("dateOfDep"),
                    rs.getDate("dateOfRet")
            );
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * информация по вкладчику, которому принадлежит вклад
     * @param depId - уникальный идентификатор вклада
     * @param cmsStmt -
     * @param admStmt -
     * @return объек информации о вкладчике
     */
    public static FullInfo getFullInfo(int depId, Statement cmsStmt, Statement admStmt){
        try {
            String query =  "SELECT userId FROM deposites WHERE id="+depId;
            ResultSet rs = admStmt.executeQuery(query);
            rs.next();
            String userId=rs.getString("userId");
            query = "SELECT a.fullname, a.email, ae.reqs FROM modx_web_user_attributes a, modx_web_user_attributes_extended ae WHERE a.id="+userId+" AND ae.id="+userId;
            rs = cmsStmt.executeQuery(query);
            rs.next();
            FullInfo fi = new FullInfo(
                    rs.getString("fullname"),
                    rs.getString("email"),
                    rs.getString("reqs")
            );
            return fi;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * начисление бонуса, возврат из процесса инвестирования
     * @param depId - уникальный идентификатор вклада
     * @param percents - начисляемый процент от основных средств вклада
     * @param stmt -
     * @return  успешное/неуспешное выполнение операции
     */
    public static boolean addBonus(int depId, int percents, Statement stmt){
        try {
            stmt.execute("start transaction");
            String query = "SELECT amount, bonus FROM deposites WHERE id="+depId;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int amount = Integer.parseInt(rs.getString("amount"));
            int bonus = amount * percents / 100;
            query = "INSERT INTO history (depId, operationDate, `comment`) values ("+depId+", '"+formatter.format((new java.util.Date()))+"', 'Начислен бонус в размере "+bonus+" руб.')";
            bonus+= Integer.parseInt(rs.getString("bonus"));
            stmt.executeUpdate(query);
            query = "UPDATE deposites SET bonus ="+bonus+", dateOfRet = null";
            stmt.executeUpdate(query);
            if(updateDepStatus(0,depId, stmt)){
                return true;
            } else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * вывод бонусных средств вклада
     * @param depId - уникальный идентификатор вклада
     * @param stmt -
     * @return  успешное/неуспешное выполнение операции
     */
    public static boolean bonusLeadOut(int depId, Statement stmt){
        try {
            String query = "UPDATE deposites SET bonus = 0 WHERE id="+depId;
            stmt.execute("start transaction");
            stmt.executeUpdate(query);
            query = "INSERT INTO history (depId, operationDate, `comment`) values ("+depId+", '"+formatter.format((new java.util.Date()))+"', 'Бонус выведен из системы.')";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * вывод основных средств вклада, вклад удален
     * @param depId - уникальный идентификатор вклада
     * @param stmt -
     * @return  успешное/неуспешное выполнение операции
     */
    public static boolean depLeadOut(int depId, Statement stmt){
        try {
            stmt.execute("start transaction");
            String query = "DELETE FROM deposites WHERE id="+depId;
            //stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * конвертация бонусных средств в основны
     * @param depId - уникальный идентификатор вклада
     * @param stmt -
     * @return успешное/неуспешное выполнение операции
     */
    public static boolean bonusConvert(int depId, Statement stmt){
        try {
            stmt.execute("start transaction");
            String query = "SELECT amount, bonus FROM deposites WHERE id="+depId;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int amount = Integer.parseInt(rs.getString("amount"));
            int bonus = Integer.parseInt(rs.getString("bonus"));
            bonus*=Configs.LOSE;
            amount+=bonus;
            query = "UPDATE deposites SET bonus=0, amount="+amount;
            stmt.executeUpdate(query);
            query = "INSERT INTO history (depId, operationDate, `comment`) values ("+depId+", '"+formatter.format((new java.util.Date()))+"', 'Бонус переведен на основной счет вклада.')";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * инвестирование вклада
     * @param depId - уникальный идентификатор вклада
     * @param comment - комментарий к операции
     * @param stmt -
     * @return успешное/неуспешное выполнение операции
     */
    public static boolean invest(int depId, String comment, Statement stmt){
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
            Date date = calendar.getTime();
            stmt.execute("start transaction");
            String query = "INSERT INTO history (depId, operationDate, `comment`) values ("+depId+", '"+formatter.format(date)+"', '"+comment+"')";
            stmt.executeUpdate(query);
            calendar.add(Calendar.DAY_OF_MONTH,25);
            date = calendar.getTime();
            query="UPDATE deposites SET dateOfRet ='"+formatter.format(date)+"' WHERE id="+depId;
            stmt.executeUpdate(query);
            if(updateDepStatus(2,depId, stmt)){
                return true;
            } else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * замораживание вклада
     * @param depId - уникальный идентификатор вклада
     * @param comment - причина заморозки
     * @param stmt -
     * @return успешное/неуспешное выполнение операции
     */
    public static boolean freeze(int depId, String comment, Statement stmt){
        try {
            stmt.execute("start transaction");
            String query = "INSERT INTO history (depId, operationDate, `comment`) values ("+depId+", '"+formatter.format((new java.util.Date()))+"',"+"'<span>"+comment+"</span>')";
            stmt.executeUpdate(query);
            if(updateDepStatus(3,depId, stmt)){
                return true;
            } else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
