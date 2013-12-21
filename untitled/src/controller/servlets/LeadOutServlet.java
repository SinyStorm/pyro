package controller.servlets;

import config.Configs;
import model.AuthorizedUser;
import model.DepositesRow;
import model.db.DBOperations;
import model.FullInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
вывод средств, конвертация бонуса во вклад
 */
public class LeadOutServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        HttpSession s = req.getSession();
        AuthorizedUser authUser = (AuthorizedUser)s.getAttribute("user");
        if(authUser==null){
            req.getRequestDispatcher("welcome.jsp").forward(req,res);
            return;
        }
        int id= Integer.parseInt(req.getParameter("id"));
        req.setAttribute("id", id);
        int hot = DBOperations.getHotCount(authUser.getAdmStmt());
        if(hot<0){
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        FullInfo fi = DBOperations.getFullInfo(id, authUser.getCmsStmt(), authUser.getAdmStmt());
        if(fi!=null) {
            req.setAttribute("info", fi);
        }
        else{
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        String type = req.getParameter("type");
        /*switch(type){
            case "out": {
                req.setAttribute("convert", false);
                DepositesRow dr = DBOperations.getDepById(id, authUser.getAdmStmt());
                if(dr!=null){
                    req.setAttribute("amount", "0 (удален из системы)");
                    req.setAttribute("payment", dr.getAmount());
                    try{
                    if(DBOperations.depLeadOut(id, authUser.getAdmStmt())){
                        authUser.getAdmStmt().execute("commit");
                    }else{
                        authUser.getAdmStmt().execute("rollback");
                        req.getRequestDispatcher("error.jsp").forward(req,res);
                        return;
                    }
                    req.setAttribute("title", "Вклад выведен");
                    req.getRequestDispatcher("lead-out.jsp").forward(req,res);
                    break;
                    }catch(SQLException e){
                        req.getRequestDispatcher("error.jsp").forward(req,res);
                        return;
                    }
                } else {
                    req.getRequestDispatcher("error.jsp").forward(req,res);
                    return;
                }
            }
            case "bonus": {
                req.setAttribute("convert", false);
                DepositesRow dr = DBOperations.getDepById(id, authUser.getAdmStmt());
                if(dr!=null){
                    req.setAttribute("amount", dr.getAmount());
                    req.setAttribute("payment", "0 (выведен из системы)");
                    try{
                        if(DBOperations.bonusLeadOut(id, authUser.getAdmStmt())){
                            authUser.getAdmStmt().execute("commit");
                        }else{
                            authUser.getAdmStmt().execute("rollback");
                            req.getRequestDispatcher("error.jsp").forward(req,res);
                            return;
                        }
                        req.setAttribute("title", "Бонус выведен");
                        req.getRequestDispatcher("lead-out.jsp").forward(req,res);
                        break;
                    }catch(SQLException e){
                        req.getRequestDispatcher("error.jsp").forward(req,res);
                        return;
                    }
                } else{
                    req.getRequestDispatcher("error.jsp").forward(req,res);
                    return;
                }
            }
            case "convert": {
                req.setAttribute("convert", "true");
                DepositesRow dr = DBOperations.getDepById(id, authUser.getAdmStmt());
                if(dr!=null){
                    try{
                        if(DBOperations.bonusConvert(id, authUser.getAdmStmt())){
                            authUser.getAdmStmt().execute("commit");
                            req.setAttribute("title", "Бонус переведен в счет основного вклада");
                        }  else{
                            authUser.getAdmStmt().execute("rollback");
                            req.getRequestDispatcher("error.jsp").forward(req,res);
                            return;
                        }
                    } catch (SQLException e) {
                        req.getRequestDispatcher("error.jsp").forward(req,res);
                        return;
                    }
                    int amount= dr.getAmount();
                    int bonus= dr.getBonus();
                    bonus*=Configs.LOSE;
                    amount+= bonus;
                    System.out.println(amount);
                    req.setAttribute("amount", amount);
                    req.setAttribute("bonus", bonus+" ( потеря: "+(Configs.LOSE)*100+"%)");
                    req.getRequestDispatcher("lead-out.jsp").forward(req,res);
                    break;
                } else{
                    req.getRequestDispatcher("error.jsp").forward(req,res);
                    return;
                }
            }
        }  */
    }
}
