package app.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class TplUtil {

    public static Map<String, String> toMap(Object obj) {
        Map<String, String> map = new HashMap<>();
        if (obj != null) {
            map = (Map<String, String>) obj;
        }
        return map;
    }

    public static Map<String, String> dictMap(String key) {
        Map<String, String> result = new LinkedHashMap<>();

        ResourceBundle config = ResourceBundle.getBundle("dict");
        String value = config.containsKey(key) ? config.getString(key) : "";
        value = StrUtil.toStr(value);

        String[] listArr = value.split(",");
        for (String itemString : listArr) {
            if (itemString.equals("")) {
                continue;
            }
            String[] itemArr = itemString.split("\\|");
            String keyStr = "";
            String valStr = "";
            if (itemArr.length == 1) {
                keyStr = itemArr[0];
                valStr = keyStr;
            }
            if (itemArr.length == 2) {
                keyStr = itemArr[0];
                valStr = itemArr[1];
            }
            result.put(keyStr, valStr);
        }
        return result;
    }

    public static String extToStr(String ext, String nodeName) {
        ext = ext == null ? "{}" : ext;
        JSONObject json = JSON.parseObject(ext);
        String str = "";
        if (json.containsKey(nodeName)) {
            str = json.getString(nodeName);
        }
        return str;
    }

    public static String dateToStr(Date d, String format) {
        String str = "";
        if (d != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            str = dateFormat.format(d);
        }
        return str;
    }

    public static String stringFormat(String f, String s) {
        String str = String.format(f, StrUtil.toInt(s));
        return str;
    }

    public static String[] split(String str, String splitStr) {
        str = (str == null) ? "" : str;
        String[] strs = str.split(splitStr, -1);
        return strs;
    }

    public static Date getToday() {
        Date date = new Date();
        return date;
    }

    public static String urlEncode(String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
        } catch (Exception e) {
        }
        return str;
    }

    public static String urlDecode(String str) {
        try {
            str = URLDecoder.decode(str, "utf-8");
        } catch (Exception e) {
        }
        return str;
    }

}
