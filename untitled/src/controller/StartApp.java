package controller;

import config.*;

import javax.servlet.*;
import java.io.IOException;
import java.util.*;

/**
 * @author  Natalia Voronova
 * Created with IntelliJ IDEA.
 * Date: 15.12.13
 * Time: 5:28
 */
public class StartApp implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent){
        ServletContext sc= servletContextEvent.getServletContext();

        sc.setAttribute("ResourceBundle", ResourceBundle.getBundle("ResourceBundle", new Locale("ru")));

        try {
            Configs.loadDBProperties(sc.getResourceAsStream(Configs.confDefault));
            System.out.println("Configs.dbProperties.size():  " + Configs.dbProperties.size());
            for(Map.Entry<Object, Object> p : Configs.dbProperties.entrySet())
                System.out.println(p.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void contextDestroyed(ServletContextEvent servletContextEvent){
    }
}
