package model;

/**
 * объект пользователя системы
 */
public class User {
    private String login;
    private String pwd;

    /**
     * конструктор по умолчанию
     */
    public User() {
        this.login = null;
        this.pwd = null;
    }

    /**
     * конструктор
     * @param login - логин пользователя
     * @param pwd - пароль пользователя
     */
    public User(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
    }

    /**
     * возвращает значение поля
     * @return логин пользователя
     */
    public String getLogin() {
        return login;
    }

    /**
     * возвращает значение поля
     * @return пароль пользователя
     */
    public String getPwd() {
        return pwd;
    }
}
