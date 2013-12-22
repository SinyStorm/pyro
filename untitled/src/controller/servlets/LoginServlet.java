package controller.servlets;

import config.Configs;
import model.AuthorizedUser;
import model.User;
import model.db.Queries;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * авторизация пользователя
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        ResourceBundle rb = (ResourceBundle)this.getServletContext().getAttribute("ResourceBundle");
        try{
            Connection admCon = DriverManager.getConnection("jdbc:mysql://" +
                    Configs.dbProperties.getProperty("host") + "/" +
                    Configs.dbProperties.getProperty("dep_db"),
                    Configs.dbProperties.getProperty("user"),
                    Configs.dbProperties.getProperty("password")
            );
            Statement admStmt = admCon.createStatement();
            String login = req.getParameter("login");
            String pwd = req.getParameter("pwd");
            ResultSet rs = admStmt.executeQuery(Queries.GET_USER + login + "'");
            if(!rs.next()){
                req.setAttribute("error", rb.getString("USER_DOES_NOT_EXIST"));
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
                req.setAttribute("error", rb.getString("WRONG_PASSWORD"));
                req.getRequestDispatcher("welcome.jsp").forward(req, res);
                return;
            }
            Connection cmsCon = DriverManager.getConnection("jdbc:mysql://" +
                    Configs.dbProperties.getProperty("host") + "/" +
                    Configs.dbProperties.getProperty("cms_db"),
                    Configs.dbProperties.getProperty("user"),
                    Configs.dbProperties.getProperty("password")
            );
            Statement cmsStmt = cmsCon.createStatement();
            User user = new User(login, pwd);
            AuthorizedUser authUser = new AuthorizedUser(user, cmsCon, admCon, cmsStmt, admStmt);
            HttpSession s = req.getSession();
            s.setAttribute("user",authUser);
            res.sendRedirect("main");
        } catch (SQLException e) {
            req.setAttribute("error", rb.getString("COULD_NOT_CONNECT_TO_DB"));
            req.getRequestDispatcher("error.jsp").forward(req, res);
        } catch (NoSuchAlgorithmException e) {
            req.setAttribute("error", rb.getString("COULD_NOT_RESOLVE_MD5"));
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
