package model;

import java.util.Date;

/**
 * строка таблицы с вкладами
 * только для чтения
 */
public class DepositesRow {
    private int id;
    private int userId;
    private int amount;
    private int startAmount;
    private int bonus;
    private int status;
    private Date dateOfDep;
    private Date dateOfRet;

    /**
     * конструктор по умолчанию
     */
    public DepositesRow() {
        this.id=0;
    }

    /**
     * конструктор
     * @param id - унакальный идентификатор вклада
     * @param userId - уникальный идентификатор вкладчика, которрому принадлежит вклад
     * @param amount - текущий объем вклада
     * @param startAmount - начальный объем вклада
     * @param bonus - текущий бонус по вкладу
     * @param status - текущий статус вклада
     * @param dateOfDep - дата вложения
     * @param dateOfRet - дата исполнения финансовых обязательств по вкладу
     */
    public DepositesRow(int id, int userId, int amount, int startAmount, int bonus, int status, Date dateOfDep, Date dateOfRet) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.startAmount = startAmount;
        this.bonus = bonus;
        this.status = status;
        this.dateOfDep = dateOfDep;
        this.dateOfRet = dateOfRet;
    }

    /**
     * возвращает значение поля
     * @return идентификатор вклада
     */
    public int getId() {
        return id;
    }

    /**
     * возвращает значение поля
     * @return идентификатор вкладчика, которому принадлежит вклад
     */
    public int getUserId() {
        return userId;
    }

    /**
     * возвращает значение поля
     * @return текущиий объем вклада
     */
    public int getAmount() {
        return amount;
    }

    /**
     * возвращает значение поля
     * @return начальный объем вклада
     */
    public int getStartAmount() {
        return startAmount;
    }

    /**
     * возвращает значение поля
     * @return текущий бонус по вкладу
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * возвращает значение поля
     * @return текущий статус вклада
     */
    public int getStatus() {
        return status;
    }

    /**
     * возвращает значение поля
     * @return дата вложения
     */
    public Date getDateOfDep() {
        return dateOfDep;
    }

    /**
     * возвращает значение поля
     * @return дата исполнения финансовых обязательств по вкладу
     */
    public Date getDateOfRet() {
        return dateOfRet;
    }

}
