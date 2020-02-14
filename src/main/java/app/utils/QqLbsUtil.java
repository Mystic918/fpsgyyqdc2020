package app.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import conf.AppConf;

import java.util.*;

public class QqLbsUtil {

    public static String key = null;
    public static String sk = null;
    public static String root = null;

    private static QqLbsUtil instance = null;

    public QqLbsUtil() {
        ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
        key = config.getString("qqlbs.key");
        sk = config.getString("qqlbs.sk");
        root = config.getString("qqlbs.root");
    }

    public static QqLbsUtil getInstatnce() {
        if (instance == null) {
            instance = new QqLbsUtil();
        }
        return instance;
    }

    public JSONObject location(String address, String city) {
        JSONObject result = new JSONObject();

        String path = "/ws/geocoder/v1/";
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("city", city);

        String url = createGetUrl(path, map);
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        String resultStr = HttpClientUtil.getInstance().sendHttpGet(url, params, headers);

        JSONObject resultJson = JSON.parseObject(resultStr);
        if (!resultJson.containsKey("status")) {
            result.put("code", 500);
            return result;
        }
        if(resultJson.getIntValue("status") != 0){
            result.put("code", 400);
            result.put("msg", resultJson.getString("message"));
            return result;
        }

        result.put("code", 200);
        result.put("data", resultJson.get("result"));
        return result;
    }

    public JSONObject address(String location) {
        JSONObject result = new JSONObject();

        String path = "/ws/geocoder/v1/";
        Map<String, String> map = new HashMap<>();
        map.put("location", location);

        String url = createGetUrl(path, map);
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        String resultStr = HttpClientUtil.getInstance().sendHttpGet(url, params, headers);

        JSONObject resultJson = JSON.parseObject(resultStr);
        if (!resultJson.containsKey("status")) {
            result.put("code", 500);
            return result;
        }
        if(resultJson.getIntValue("status") != 0){
            result.put("code", 400);
            result.put("msg", resultJson.getString("message"));
            return result;
        }

        result.put("code", 200);
        result.put("data", resultJson.get("result"));
        return result;
    }

    public JSONObject distance(String mode, String from, String to) {
        JSONObject result = new JSONObject();

        String path = "/ws/distance/v1/";
        Map<String, String> map = new HashMap<>();
        map.put("mode", mode);
        map.put("from", from);
        map.put("to", to);

        String url = createGetUrl(path, map);
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        String resultStr = HttpClientUtil.getInstance().sendHttpGet(url, params, headers);

        JSONObject resultJson = JSON.parseObject(resultStr);
        if (!resultJson.containsKey("status")) {
            result.put("code", 500);
            return result;
        }
        if(resultJson.getIntValue("status") != 0){
            result.put("code", 400);
            result.put("msg", resultJson.getString("message"));
            return result;
        }

        result.put("code", 200);
        result.put("data", resultJson.get("result"));
        return result;
    }

    public JSONObject translate(double fromLat, double fromLng, String from, String to) {
        JSONObject result = new JSONObject();
        double pi = 3.1415926535897932384626;
        double toLat = 0;
        double toLng = 0;

        if(from.equals("bd") && to.equals("qq")){
            double x = fromLng, y = fromLat;
            double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
            double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
            toLat = z * Math.sin(theta) + 0.006;
            toLng = z * Math.cos(theta) + 0.0065;
        }

        if(from.equals("qq") && to.equals("bd")){
            double x = fromLng - 0.0065, y = fromLat - 0.006;
            double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
            double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
            toLng = z * Math.cos(theta);
            toLat = z * Math.sin(theta);
        }
        JSONObject dataNode = new JSONObject();
        dataNode.put("lat", toLat);
        dataNode.put("lng", toLng);

        result.put("code", 200);
        result.put("data", dataNode);
        return result;
    }

    public JSONObject locationIp(String ip) {
        JSONObject result = new JSONObject();

        String path = "/ws/location/v1/ip";
        Map<String, String> map = new HashMap<>();
        map.put("ip", ip);

        String url = createGetUrl(path, map);
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        String resultStr = HttpClientUtil.getInstance().sendHttpGet(url, params, headers);

        JSONObject resultJson = JSON.parseObject(resultStr);
        if (!resultJson.containsKey("status")) {
            result.put("code", 500);
            return result;
        }
        if(resultJson.getIntValue("status") != 0){
            result.put("code", 400);
            result.put("msg", resultJson.getString("message"));
            return result;
        }

        JSONObject dataNode = new JSONObject();
        dataNode.put("lat", resultJson.getJSONObject("result").getJSONObject("location").getFloatValue("lat"));
        dataNode.put("lng", resultJson.getJSONObject("result").getJSONObject("location").getFloatValue("lng"));
        dataNode.put("nation", resultJson.getJSONObject("result").getJSONObject("ad_info").getString("nation"));
        dataNode.put("province", resultJson.getJSONObject("result").getJSONObject("ad_info").getString("province"));
        dataNode.put("city", resultJson.getJSONObject("result").getJSONObject("ad_info").getString("city"));
        dataNode.put("district", resultJson.getJSONObject("result").getJSONObject("ad_info").getString("district"));
        dataNode.put("adcode", resultJson.getJSONObject("result").getJSONObject("ad_info").getString("adcode"));

        result.put("code", 200);
        result.put("data", dataNode);
        return result;
    }

    private String createGetUrl(String path, Map<String, String> map) {
        map.put("key", key);
        Map<String, String> parmasMap = new TreeMap<>();
        for(String key : map.keySet()){
            parmasMap.put(key, map.get(key));
        }
        List<String> parmasList = new ArrayList<>();
        for(String key : parmasMap.keySet()){
            parmasList.add(key + "=" + parmasMap.get(key));
        }
        String sig = SecurityUtil.MD5(path + "?" + String.join("&", parmasList) + sk);
        parmasList.add("sig=" + sig);
        String url = root + path + "?" + String.join("&", parmasList);
        return url;
    }

}