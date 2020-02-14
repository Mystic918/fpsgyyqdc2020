package app.controllers.sys;

import app.models.SysMenu;
import app.models.SysRole;
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

public class RoleController {

    private static String pageTitle = "角色";
    private static String parent = "sys";
    private static String current = "sys_role";
    private static String[] types = new String[]{"用户", "管理"};

    public static Route List = (Request req, Response res) -> {
        List<SysRole> lists = SysRole.find.query().where()
                .order("type, name")
                .findList();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle + "列表");
        model.put("parent", parent);
        model.put("current", current);
        model.put("lists", lists);
        model.put("types", types);
        return ViewUtil.render(req, model, "views/sys/role/list.vm");
    };

    public static Route Add = (Request req, Response res) -> {
        SysRole field = new SysRole();

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("types", types);
        return ViewUtil.render(req, model, "views/sys/role/add.vm");
    };

    public static Route Save = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("types", types);

        String name = StrUtil.toStr(req.queryParams("name"));
        String type = StrUtil.toStr(req.queryParams("type"));

        SysRole field = new SysRole();
        field.setName(name);
        field.setType(type);

        try {
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/role/add.vm");
        }
        res.redirect(PathSys.Role());
        return null;

    };

    public static Route Edit = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SysRole field = SysRole.find.byId(id);

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("types", types);
        return ViewUtil.render(req, model, "views/sys/role/edit.vm");
    };

    public static Route Update = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("types", types);

        long id = StrUtil.toLong(req.params("id"));
        SysRole field = SysRole.find.byId(id);
        String name = StrUtil.toStr(req.queryParams("name"));
        String type = StrUtil.toStr(req.queryParams("type"));

        field.setName(name);
        field.setType(type);

        try {
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/role/edit.vm");
        }
        res.redirect(PathSys.Role());
        return null;
    };

    public static Route Delete = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SysRole field = SysRole.find.byId(id);
        try {
            if (field != null) {
                field.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.redirect(PathSys.Role());
        return null;
    };

    public static Route Menu = (Request req, Response res) -> {
        long roleId = StrUtil.toLong(req.params("id"));
        SysRole role = SysRole.find.byId(roleId);
        List<SysRoleMenu> roleMenus = SysRoleMenu.find.query().where().eq("role", role).order("menu.priority").findList();
        List<SysMenu> menus = SysMenu.find.query().where().eq("type", role.getType()).order("priority").findList();
        menus = AdminService.getMenuTree(menus);

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("role", role);
        model.put("roleMenus", roleMenus);
        model.put("menus", menus);
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        return ViewUtil.render(req, model, "views/sys/role/menu.vm");
    };

    public static Route MenuSave = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        long menuId = StrUtil.toLong(req.queryParams("menu_id"));
        SysRole role = SysRole.find.byId(id);
        SysMenu menu = SysMenu.find.byId(menuId);

        SysRoleMenu roleMenu = SysRoleMenu.find.query().where().eq("role", role).eq("menu", menu).findOne();
        if (roleMenu == null) {
            roleMenu = new SysRoleMenu();
            roleMenu.setRole(role);
            roleMenu.setMenu(menu);
            roleMenu.save();
        }

        res.redirect(PathSys.RoleMenu(id + ""));
        return null;
    };

    public static Route MenuDelete = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        long menuId = StrUtil.toLong(req.params("menu_id"));
        SysRoleMenu roleMenu = SysRoleMenu.find.byId(menuId);
        if (roleMenu != null) {
            roleMenu.delete();
        }

        res.redirect(PathSys.RoleMenu(id + ""));
        return null;
    };

}
