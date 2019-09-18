package cn.ccsu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 日期工具类
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 12:05
 */
public class DateUtil
{
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    public static Date getDate()
    {
        return new Date();
    }

    public static Date paeserStringToDate(String dataString)
    {
        Date datetimeObj = null;
        try {
            datetimeObj = sdf.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datetimeObj;
    }
    public static String paeserDateToString(Date data)
    {
        return sdf.format(data);
    }






}
