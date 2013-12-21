package tests;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Sid
 * Date: 25.11.13
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
public class MyFirstServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("test", "BOOYAKASHA!");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
