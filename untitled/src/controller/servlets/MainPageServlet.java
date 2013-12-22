package controller.servlets;

import model.AuthorizedUser;
import model.db.DBOperations;
import model.DepositesRow;
import model.db.Queries;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * главная страница
 */
public class MainPageServlet extends HttpServlet{
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
        ResourceBundle rb = (ResourceBundle)this.getServletContext().getAttribute("ResourceBundle");
        req.setAttribute("title", rb.getString("WELCOME"));
        int hot = DBOperations.getHotCount(authUser.getAdmStmt());
        if(hot<0){
            req.setAttribute("error", rb.getString("COULD_NOT_GET_HOT_COUNT"));
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
        req.setAttribute("hot", hot);
        String sql= Queries.GET_HOT_DEPS;
        ArrayList<DepositesRow> al = DBOperations.getFromDB(sql, authUser.getAdmStmt());
        if(al!=null){
            req.setAttribute("results", al);
            req.getRequestDispatcher("main.jsp").forward(req,res);
            return;
        } else{
            req.getRequestDispatcher("error.jsp").forward(req,res);
            return;
        }
    }
}
