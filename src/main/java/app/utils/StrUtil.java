package app.utils;

import conf.AppConf;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class StrUtil {

    public static String createSn(String prefix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String orderNum = prefix + sdf.format(new Date());
        return orderNum;
    }

    public static String toStr(Object obj) {
        String str = obj == null ? "" : obj.toString();
        try {
            String encode = getEncoding(str);
            if (encode.equals("ISO-8859-1")) {
                str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            }
        } catch (Exception e) {
        }
        return str;
    }

    public static String getNumber(String str) {
        if (str == null) {
            str = "";
        }
        try {
            String str2 = "";
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 45 && str.charAt(i) <= 57 && str.charAt(i) != 47) {
                    str2 += str.charAt(i);
                }
            }
            str = str2;
        } catch (Exception e) {
        }
        return str;
    }

    public static int toInt(Object obj) {
        String str = obj == null ? "" : obj.toString();
        int num = 0;
        try {
            if (str != null && !str.equals("")) {
                num = Integer.parseInt(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    public static long toLong(Object obj) {
        String str = obj == null ? "" : obj.toString();
        long num = 0;
        try {
            if (str != null && !str.equals("")) {
                num = Long.parseLong(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    public static float toFloat(Object obj) {
        String str = obj == null ? "" : obj.toString();
        float num = 0;
        try {
            if (str != null && !str.equals("")) {
                num = Float.parseFloat(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    public static double toDouble(Object obj) {
        String str = obj == null ? "" : obj.toString();
        double num = 0;
        try {
            if (str != null && !str.equals("")) {
                num = Double.parseDouble(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    public static BigDecimal toBigDecimal(Object obj) {
        String str = obj == null ? "" : obj.toString();
        BigDecimal num = BigDecimal.valueOf(0);
        try {
            if (str != null) {
                num = new BigDecimal(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    public static String toStr(Date d, String format) {
        String str = "";
        if (d != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            str = dateFormat.format(d);
        }
        return str;
    }

    public static Date toDate(String str, String format) {
        return toDate(str, format, null);
    }

    public static Date toDate(String str, String format, Date defaultDate) {
        Date date = defaultDate;
        if (str != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            try {
                date = dateFormat.parse(str);
            } catch (Exception e) {
            }
        }
        return date;
    }

    public static String toWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String week = "";
        try {
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w >= 0 && w <= 6) {
                week = weekDays[w];
            }
        } catch (Exception e) {
        }
        return week;
    }

    public static int toWeekNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekNum = cal.get(Calendar.WEEK_OF_YEAR);
        return weekNum;
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public static int dateDiffDay(Date date) {
        int days = 0;
        if (date != null) {
            Date today = new Date();
            days = (int) ((date.getTime() - today.getTime()) / (1000 * 3600 * 24));
        }
        return days;
    }

    public static Date dateAddDay(Date date, int count) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, count);
        return calendar.getTime();
    }

    public static Date dateAddWeek(Date date, int count) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, count);
        return calendar.getTime();
    }

    public static Date dateAddMonth(Date date, int count) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTime();
    }

    public static Date dateAddSecond(Date date, int count) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, count);
        return calendar.getTime();
    }

    public static String getConfig(String key) {
        ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
        String result = config.containsKey(key) ? toStr(config.getString(key)) : "";
        return result;
    }

}
