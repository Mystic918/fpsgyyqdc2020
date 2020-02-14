package app.utils;

import conf.AppConf;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormUtil {

    public static boolean isDate(String str, String format) {
        boolean result = false;

        if (str != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                Date date = sdf.parse(str);
                result = true;
            } catch (Exception e) {
            }
        }

        return result;
    }

    public static boolean isMobile(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^((1[3-9]))\\d{9}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }

        return result;
    }

    public static boolean isComplexPassword(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,16}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }

        return result;
    }

    public static boolean isTradePassword(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^\\d{6}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }
        return result;
    }

    public static boolean isIdCard(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^\\d{15}$|^\\d{17}[0-9Xx]$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }

        return result;
    }

    public static boolean isEmail(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }

        return result;
    }

    public static boolean isBankCard(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^[1-9]?\\d{12,18}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }
        return result;
    }

    public static boolean isBusinessLicense(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }

        return result;
    }

    public static boolean isCarNo(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }

        return result;
    }

    public static boolean isSimplePwd(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^\\d{6}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }
        return result;
    }

    public static boolean isIp(String str) {
        boolean result = false;
        if (str != null) {
            String regex = "^([1-9]|[1-9]\\\\d|1\\\\d{2}|2[0-4]\\\\d|25[0-5])(\\\\.(\\\\d|[1-9]\\\\d|1\\\\d{2}|2[0-4]\\\\d|25[0-5])){3}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            result = m.matches();
        }

        return result;
    }

}
