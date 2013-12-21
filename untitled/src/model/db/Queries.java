package model.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
Запросы к БД
 */
public class Queries {
    public static final String GET_USER = "select * from users where login ='";
    public static final String GET_WAITING_DEPS =
            "select * from deposites where status=0";
    public static final String GET_READY_DEPS =
            "SELECT * FROM deposites WHERE status = 1";
    public static final String GET_INVESTED_DEPS =
            "SELECT * FROM deposites WHERE status = 2";
    public static final String GET_FROZEN_DEPS =
            "SELECT * FROM deposites WHERE status = 3";
    public static final String GET_HOT_DEPS;
    static{
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
        c.add(Calendar.DAY_OF_MONTH,5);
        Date deadLine = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat ("s");
        formatter.applyPattern("yyyy-MM-dd");
        GET_HOT_DEPS= "SELECT * FROM deposites WHERE dateOfRet <='" + formatter.format(deadLine)+"'";
    }

}
