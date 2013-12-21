package controller.servlets;

import model.AuthorizedUser;
import model.db.DBOperations;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
изменение статуса вклада
 */
public class ChangeStatusServlet extends HttpServlet{
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
        int hot = DBOperations.getHotCount(authUser.getAdmStmt());
        if(hot<0){
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        String type = req.getParameter("type");
        int id= Integer.parseInt(req.getParameter("id"));
        req.setAttribute("id",id);
        /*switch(type){
            case "invest":{
                String comment = req.getParameter("comment");
                try{
                    if(DBOperations.invest(id, comment, authUser.getAdmStmt())){
                        authUser.getAdmStmt().execute("commit");
                        req.setAttribute("result", "Вклад успешно инвестирован");
                        req.getRequestDispatcher("success.jsp").forward(req,res);
                    } else{
                        authUser.getAdmStmt().execute("rollback");
                        req.getRequestDispatcher("error.jsp").forward(req, res);
                    }
                    break;
                } catch (SQLException e) {
                    req.getRequestDispatcher("error.jsp").forward(req, res);
                }
            }
            case "bonus":{
                int bonus = Integer.parseInt(req.getParameter("bonus"));
                try{
                    if(DBOperations.addBonus(id, bonus, authUser.getAdmStmt())){
                        authUser.getAdmStmt().execute("commit");
                        req.setAttribute("result", "Бонус успешно начислен");
                        req.getRequestDispatcher("success.jsp").forward(req,res);
                    }else{
                        authUser.getAdmStmt().execute("rollback");
                        req.getRequestDispatcher("error.jsp").forward(req,res);
                    }
                    break;
                } catch (SQLException e) {
                    req.getRequestDispatcher("error.jsp").forward(req,res);
                }
            }
            case "freeze":{
                String comment = req.getParameter("comment");
                try{
                    if(DBOperations.freeze(id, comment, authUser.getAdmStmt())){
                        authUser.getAdmStmt().execute("commit");
                        req.setAttribute("result", "Вклад заморожен");
                        req.getRequestDispatcher("success.jsp").forward(req,res);
                    }
                    else {
                        authUser.getAdmStmt().execute("rollback");
                        req.getRequestDispatcher("error.jsp").forward(req,res);
                    }
                    break;
                } catch (SQLException e) {
                    req.getRequestDispatcher("error.jsp").forward(req,res);
                }
            }
            default:{
                req.getRequestDispatcher("error.jsp").forward(req,res);
            }
        }   */
    }
}
