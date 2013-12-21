package model;

/**
 * поолная информация по вкладу
 * только для чтения
 */
public class FullInfo {
    private String fullName;
    private String email;
    private String requisites;

    /**
     * конструктор по умолчанию
     */
    public FullInfo() {
        this.fullName = null;
        this.email = null;
        this.requisites = null;
    }

    /**
     * конструктор
     * @param fullName - полное имя вкладчика
     * @param email - электронная почта вкладчика
     * @param requisites - платежные реквизиты вкладчика
     */
    public FullInfo(String fullName, String email, String requisites) {
        this.fullName = fullName;
        this.email = email;
        this.requisites = requisites;
    }

    /**
     * возвращает значение поля
     * @return полное имя вкладчика
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * возвращает значение поля
     * @return электронная почта вкладчика
     */
    public String getEmail() {
        return email;
    }

    /**
     * возвращает значение поля
     * @return платежные реквизиты вкладчика
     */
    public String getRequisites() {
        return requisites;
    }
}
