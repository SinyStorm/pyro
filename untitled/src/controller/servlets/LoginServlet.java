package controller.servlets;

import config.Configs;
import model.AuthorizedUser;
import model.User;
import model.db.Queries;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

/**
 * авторизация пользователя
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        try{
            Connection admCon = DriverManager.getConnection("jdbc:mysql://" + Configs.HOST + "/" + Configs.DEP_DB, Configs.USER, Configs.PASSWORD);
            Statement admStmt = admCon.createStatement();
            String login = (String)req.getParameter("login");
            String pwd = (String)req.getParameter("pwd");
            ResultSet rs = admStmt.executeQuery(Queries.GET_USER + login + "'");
            if(!rs.next()){
                req.setAttribute("error", "Не существует пользователя с таким логином!");
                req.getRequestDispatcher("welcome.jsp").forward(req, res);
                return;
            }
            String dbPwd = rs.getString("pwd");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                int tmp = (int)b & 0xff;
                if(tmp<16){
                    sb.append("0" + Integer.toHexString(tmp));
                }else{
                    sb.append(Integer.toHexString(tmp));
                }
            }
            pwd = sb.toString();
            if(!pwd.equals(dbPwd)){
                req.setAttribute("error", "Пароль введен неверно!");
                req.getRequestDispatcher("welcome.jsp").forward(req, res);
                return;
            }
            Connection cmsCon = DriverManager.getConnection("jdbc:mysql://" + Configs.HOST + "/" + Configs.CMS_DB, Configs.USER, Configs.PASSWORD);
            Statement cmsStmt = cmsCon.createStatement();
            User user = new User(login, pwd);
            AuthorizedUser authUser = new AuthorizedUser(user, cmsCon, admCon, cmsStmt, admStmt);
            HttpSession s = req.getSession();
            s.setAttribute("user",authUser);
            res.sendRedirect("maim");
        } catch (SQLException e) {
            req.getRequestDispatcher("error.jsp").forward(req, res);
        } catch (NoSuchAlgorithmException e) {
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
