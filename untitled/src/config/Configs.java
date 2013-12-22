package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
Конфигурационные данные, глобальные переменные
 */
public class Configs {
    public static final double LOSE = 0.15;

    public static final String[] STATUS =
            {"В ожидании", "Готов", "Инвестирован", "Заморожен"};
    //--------------------------------------------------------------------------------------------------------------------
    public final static String confDefault = "conf.properties";
    public enum RequiredDBProperties {
        cms_db,
        dep_db,
        host,
        user,
        password
    };

    public static Properties dbProperties = new Properties();
    public static void loadDBProperties() throws IOException {
        dbProperties.load(ClassLoader.getSystemResourceAsStream(confDefault));
    }
    public static void loadDBProperties(InputStream is) throws IOException {
        dbProperties.load(is);
        //RequiredDBProperties.cms_db. = dbProperties.getProperty("host");

    }
//---------------------------------------------------------------------------------------------------------------------
            public static void main(String args[]){
                //System.out.println(RequiredDBProperties.cms_db);

            }
}
