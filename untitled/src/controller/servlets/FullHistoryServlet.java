package controller.servlets;

import model.AuthorizedUser;
import model.db.DBOperations;
import model.HistoryRow;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
вывод полной истории
 */
public class FullHistoryServlet extends HttpServlet{
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
        int id= Integer.parseInt(req.getParameter("id"));
        req.setAttribute("title", ("Полная история вклада № "+ id));
        ArrayList<HistoryRow> history = DBOperations.getHistory(id, 0, authUser.getAdmStmt());
        if(history!=null){
            req.setAttribute("history", history);
        } else {
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        req.setAttribute("id", id);
        req.getRequestDispatcher("history.jsp").forward(req,res);
        return;
    }
}
