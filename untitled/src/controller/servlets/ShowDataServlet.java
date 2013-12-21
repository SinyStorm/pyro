package controller.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AuthorizedUser;
import model.db.DBOperations;
import model.DepositesRow;
import model.db.Queries;

/**
 Возвращает депозиты, отфильтрованные по статусу
 */
public class ShowDataServlet extends HttpServlet {
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
        req.setAttribute("hot", hot);
        String sql="";
        String type = req.getParameter("type");
       /* switch(type){
            case "ready": {
                sql= Queries.GET_READY_DEPS;
                req.setAttribute("title", "Вклады, готовые к инвестированию");
                req.setAttribute("menuindex", 1);
                break;
            }
            case "invested":{
                sql= Queries.GET_INVESTED_DEPS;
                req.setAttribute("title", "");
                req.setAttribute("menuindex", 2);
                break;
            }
            case "hot":{
                sql= Queries.GET_HOT_DEPS;
                req.setAttribute("title", "Горящие вклады");
                req.setAttribute("menuindex", 5);
                break;
            }
            case "frozen":{
                sql= Queries.GET_FROZEN_DEPS;
                req.setAttribute("title", "Замороженные вклады");
                req.setAttribute("menuindex", 4);
                break;
            }
            case "waiting":{
                sql= Queries.GET_WAITING_DEPS;
                req.setAttribute("title", "Вклады в ожидании");
                req.setAttribute("menuindex", 3);
                break;
            }
            default:{
                req.getRequestDispatcher("error.jsp").forward(req,res);
                return;
            }
        }     */
        ArrayList<DepositesRow> al= DBOperations.getFromDB(sql, authUser.getAdmStmt());
        if(al!=null){
            req.setAttribute("results", al);
            req.getRequestDispatcher("show.jsp").forward(req,res);
            return;
        } else{
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
    }
}
