package app.services;

import app.models.*;
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

public class AdminService {

    public static List<SysMenu> getMenuTree(List<SysMenu> lists){
        List<SysMenu> tree = new ArrayList<>();

        for(SysMenu l1 : lists){
            if(l1.getParent() == null){
                tree.add(l1);
                for(SysMenu l2 : lists){
                    if(l2.getParent() != null) {
                        if (l2.getParent().getAlias().equals(l1.getAlias())) {
                            l2.setName("　├" + l2.getName());
                            tree.add(l2);
                        }
                    }
                }
            }
        }
        return tree;
    }

    public static Object getSessionUser(Request req, String key){
        Object result = new Object();
        Map<String, Object> user = req.session().attribute("admin_user");
        if(user != null){
            if(user.containsKey(key)){
                result = user.get(key);
            }
        }
        return result;
    }

    public static JSONArray permitMenus(long managerId, String isShow){
        JSONArray lists = new JSONArray();

        SysManager manager = SysManager.find.byId(managerId);
        if(manager != null){
            if(manager.getAdmin() == 1){
                List<SysMenu> operatorMenus = SysMenu.find.query().where()
                        .in("type", new Object[]{"管理", "系统"}).like("isShow", "%" + isShow + "%")
                        .order("type, priority desc, id").findList();
                for(SysMenu menu : operatorMenus){
                    JSONObject node = new JSONObject();
                    node.put("name", menu.getName());
                    node.put("alias", menu.getAlias());
                    node.put("parent", (menu.getParent() == null) ? null : menu.getParent().getAlias());
                    node.put("link", menu.getLink());
                    node.put("icon", menu.getIcon());
                    lists.add(node);
                }
            }else{
                Query<SysRoleMenu> menuQuery = SysRoleMenu.find.query().where().eq("role", manager.getRole()).select("menu");

                List<SysMenu> operatorMenus = SysMenu.find.query().where().in("id", menuQuery).eq("type", "管理")
                        .order("priority desc,id").findList();
                for(SysMenu menu : operatorMenus){
                    JSONObject node = new JSONObject();
                    node.put("name", menu.getName());
                    node.put("alias", menu.getAlias());
                    node.put("parent", (menu.getParent() == null) ? null : menu.getParent().getAlias());
                    node.put("link", menu.getLink());
                    node.put("icon", menu.getIcon());
                    lists.add(node);
                }
            }
        }
        return lists;
    }

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

    public static Map<String, String> getMap(Request req, String prefix){
        Map<String, String> ext = new HashMap<>();
        Map<String, String[]> queryMap = req.queryMap().toMap();
        for(String key : queryMap.keySet()){
            String value = StrUtil.toStr(queryMap.get(key)[0]);
            if(key.length() > prefix.length() && key.indexOf(prefix) > -1){
                key = key.substring(prefix.length());
                ext.put(key, value);
            }
        }
        return ext;
    }

    public static String underlineToCamel(String s) {
        String ss[] = s.split("_");
        String str = ss[0];
        if (ss.length > 1) {
            for (int i = 1; i < ss.length; i++) {
                if (ss[i].length() > 0) {
                    str += ss[i].substring(0, 1).toUpperCase() + ss[i].substring(1);
                }
            }
        }
        return str;
    }

    public static String getSession(Request req, String key) {
        String result = "";
        Object obj = req.session().attribute(key);
        if (obj != null) {
            result = obj.toString();
        }
        return result;
    }

    public static void setSession(Request req, String key, Object value) {
        req.session().attribute(key, value);
    }

    public static String getParams(String key) {
        String result = "";
        SysParams field = SysParams.find.query().where().eq("alias", key).setMaxRows(1).findOne();
        if(field != null){
            result = field.getValue();
        }
        return result;
    }
}