package model;

import java.util.Date;

/**
 * строка таблицы истории
 * только для чтения
 */
public class HistoryRow {
    private Date operationDate;
    private String comment;
    private int bonus;

    /**
     * конструктор по умолчанию
     */
    public HistoryRow() {
        this.operationDate = null;
        this.comment = null;
        bonus = 0;
    }

    /**
     * конструктор
     * @param operationDate - дата операции над вкладом
     * @param comment - коментарий к операции
     * @param bonus - начисленный бонус
     */
    public HistoryRow(Date operationDate, String comment, int bonus) {
        this.operationDate = operationDate;
        this.comment = comment;
        this.bonus = bonus;
    }

    /**
     * возвращает значение поля
     * @return дата операции над вкладом
     */
    public Date getOperationDate() {
        return operationDate;
    }

    /**
     * возвращает значение поля
     * @return коментарий к операции
     */
    public String getComment() {
        return comment;
    }

    /**
     * возвращает значение поля
     * @return начисленный бонус
     */
    public int getBonus() {
        return bonus;
    }
}
