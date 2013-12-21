package controller.servlets;

import model.AuthorizedUser;
import model.db.DBOperations;
import model.DepositesRow;
import model.FullInfo;
import model.HistoryRow;

import javax.servlet.ServletException;
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
 вывод подробной информации по вкладу
 */
public class FullInfoServlet extends HttpServlet{
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
        req.setAttribute("title", ("Полная информация по вкладу № "+ id));
        FullInfo fi = DBOperations.getFullInfo(id, authUser.getCmsStmt(), authUser.getAdmStmt());
        if(fi!=null) {
            req.setAttribute("info", fi);
        }
        else{
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        ArrayList<HistoryRow> history = DBOperations.getHistory(id, 3, authUser.getAdmStmt());
        if(history!=null){
            req.setAttribute("history", history);
        } else {
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        DepositesRow dep = DBOperations.getDepById(id, authUser.getAdmStmt());
        if(dep!=null){
            req.setAttribute("deposite",dep);
        } else {
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        req.getRequestDispatcher("info.jsp").forward(req,res);
        return;
    }
}
