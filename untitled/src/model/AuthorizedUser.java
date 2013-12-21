package model;

import config.Configs;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

/**
 * объект авторизованного пользователя
 */
public class AuthorizedUser {
    private User user;
    private Connection cmsCon;
    private Connection admCon;
    private Statement cmsStmt;
    private Statement admStmt;

    /**
     * конструктор по умолчанию
     */
    public AuthorizedUser() {
        this.user = null;
        this.cmsCon = null;
        this.admCon = null;
        this.cmsStmt = null;
        this.admStmt = null;
    }

    /**
     * конструктор
     * @param user - объект пользователя
     * @param cmsCon - соединение с базой данных CMS
     * @param admCon - соединение с базой данных вкладов
     * @param cmsStmt
     * @param admStmt
     */
    public AuthorizedUser(User user, Connection cmsCon, Connection admCon, Statement cmsStmt, Statement admStmt) {
        this.user = user;
        this.cmsCon = cmsCon;
        this.admCon = admCon;
        this.cmsStmt = cmsStmt;
        this.admStmt = admStmt;
    }

    /**
     * возвращает значение поля
     * @return пользователь системы
     */
    public User getUser() {
        return user;
    }

    /**
     * возвращает значение поля
     * @return соединение с базой данных CMS
     */
    public Connection getCmsCon() {
        return cmsCon;
    }

    /**
     * возвращает значение поля
     * @return соединение с базой данных вкладов
     */
    public Connection getAdmCon() {
        return admCon;
    }

    /**
     * возвращает значение поля
     * @return
     */
    public Statement getCmsStmt() {
        return cmsStmt;
    }

    /**
     * возвращает значение поля
     * @return
     */
    public Statement getAdmStmt() {
        return admStmt;
    }
}
