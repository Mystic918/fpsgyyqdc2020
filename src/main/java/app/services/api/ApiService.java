package app.services.api;

import app.models.SysManager;
import app.models.SysMenu;
import app.models.SysParams;
import app.models.SysRoleMenu;
import app.utils.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ebean.Query;
import spark.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiService {

    public static String getExt(Request req){
        JSONObject extNode = new JSONObject();
        Map<String, String[]> queryMap = req.queryMap().toMap();
        for(String key : queryMap.keySet()){
            String value = StrUtil.toStr(queryMap.get(key)[0]);
            if(key.length() > 4 && key.substring(0, 4).equals("ext_")){
                key = key.substring(4);
                extNode.put(key, value);
            }
        }
        String ext = JSON.toJSONString(extNode);
        return ext;
    }

    public static String getIp(Request req){
        String result = "127.0.0.1";
        try{
            result = (req.headers("x-forwarded-for") == null) ? req.ip() : req.headers("x-forwarded-for");
            result = result.split(",")[0];
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}