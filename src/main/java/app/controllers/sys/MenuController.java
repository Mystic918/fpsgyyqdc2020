package app.controllers.sys;

import app.models.SysMenu;
import app.models.SysRoleMenu;
import app.services.AdminService;
import app.utils.StrUtil;
import app.utils.ViewUtil;
import conf.PathSys;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuController {

    private static String pageTitle = "菜单";
    private static String parent = "sys";
    private static String current = "sys_menu";
    private static String[] types = new String[]{"管理", "系统"};

    public static Route List = (Request req, Response res) -> {
        List<SysMenu> lists = SysMenu.find.query().where().order("type, priority desc,id").findList();
        lists = AdminService.getMenuTree(lists);

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle + "列表");
        model.put("parent", parent);
        model.put("current", current);
        model.put("lists", lists);
        model.put("types", types);
        return ViewUtil.render(req, model, "views/sys/menu/list.vm");
    };

    public static Route Add = (Request req, Response res) -> {
        SysMenu field = new SysMenu();

        List<SysMenu> parents = SysMenu.find.query().where().isNull("parent.id").findList();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("field", field);
        model.put("parents", parents);
        model.put("types", types);
        return ViewUtil.render(req, model, "views/sys/menu/add.vm");
    };

    public static Route Save = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("types", types);

        String name = StrUtil.toStr(req.queryParams("name"));
        String alias = StrUtil.toStr(req.queryParams("alias"));
        String type = StrUtil.toStr(req.queryParams("type"));
        String icon = StrUtil.toStr(req.queryParams("icon"));
        String link = StrUtil.toStr(req.queryParams("link"));
        int isShow = StrUtil.toInt(req.queryParams("is_show"));
        long parentId = StrUtil.toLong(req.queryParams("parent_id"));
        SysMenu parent = SysMenu.find.byId(parentId);

        SysMenu field = new SysMenu();
        field.setName(name);
        field.setAlias(alias);
        field.setType(type);
        field.setParent(parent);
        field.setIcon(icon);
        field.setLink(link);
        field.setIsShow(isShow);

        try {
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.redirect(PathSys.Menu());
        return null;
    };

    public static Route Edit = (Request req, Response res) -> {
        String id = req.params("id");
        SysMenu field = SysMenu.find.byId(Long.parseLong(id));

        List<SysMenu> parents = SysMenu.find.query().where().isNull("parent.id").findList();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("field", field);
        model.put("parents", parents);
        model.put("types", types);
        return ViewUtil.render(req, model, "views/sys/menu/edit.vm");
    };

    public static Route Update = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("types", types);

        long id = StrUtil.toLong(req.params("id"));
        String name = StrUtil.toStr(req.queryParams("name"));
        String alias = StrUtil.toStr(req.queryParams("alias"));
        String type = StrUtil.toStr(req.queryParams("type"));
        String icon = StrUtil.toStr(req.queryParams("icon"));
        String link = StrUtil.toStr(req.queryParams("link"));
        int isShow = StrUtil.toInt(req.queryParams("is_show"));
        long parentId = StrUtil.toLong(req.queryParams("parent_id"));
        SysMenu parent = SysMenu.find.byId(parentId);

        SysMenu field = SysMenu.find.byId(id);
        field.setName(name);
        field.setAlias(alias);
        field.setType(type);
        field.setParent(parent);
        field.setIcon(icon);
        field.setLink(link);
        field.setIsShow(isShow);

        try {
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.redirect(PathSys.Menu());
        return null;
    };

    public static Route Delete = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SysMenu field = SysMenu.find.byId(id);
        try {
            if (field != null) {
                SysRoleMenu.find.query().where().eq("menu.id", field.getId()).delete();
                field.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.redirect(PathSys.Menu());
        return null;
    };

    public static Route Sort = (Request req, Response res) -> {
        String[] ids = req.queryMap("id").values();
        String[] priorities = req.queryMap("priority").values();

        for (int i = 0; i < ids.length; i++) {
            SysMenu menu = SysMenu.find.byId(StrUtil.toLong(ids[i]));
            if (menu != null) {
                menu.setPriority(StrUtil.toInt(priorities[i]));
                menu.save();
            }
        }

        res.redirect(PathSys.Menu());
        return null;
    };

}
