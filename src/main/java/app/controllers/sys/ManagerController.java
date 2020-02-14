package app.controllers.sys;

import app.models.SysManager;
import app.models.SysRole;
import app.utils.SecurityUtil;
import app.utils.StrUtil;
import app.utils.ViewUtil;
import conf.PathSys;
import io.ebean.PagedList;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerController {

    private static String pageTitle = "管理员";
    private static String parent = "sys";
    private static String current = "sys_manager";
    private static String[] states = new String[]{"开启", "关闭"};

    public static Route List = (Request req, Response res) -> {
        String queryName = StrUtil.toStr(req.queryParams("query_name"));
        String queryUsername = StrUtil.toStr(req.queryParams("query_username"));

        int page = StrUtil.toInt(req.queryParams("page"));
        int pagesize = StrUtil.toInt(req.queryParams("pagesize"));
        pagesize = pagesize == 0 ? 10 : pagesize;

        int offset = page * pagesize;
        int maxrow = pagesize;

        PagedList<SysManager> pagedList = SysManager.find.query().where()
                .like("name", "%" + queryName + "%").like("username", "%" + queryUsername + "%").order("id desc")
                .setFirstRow(offset).setMaxRows(maxrow).findPagedList();
        pagedList.loadCount();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle + "列表");
        model.put("parent", parent);
        model.put("current", current);
        model.put("current", current);
        model.put("pagedList", pagedList);
        model.put("queryName", queryName);
        model.put("queryUsername", queryUsername);
        return ViewUtil.render(req, model, "views/sys/manager/list.vm");
    };

    public static Route Add = (Request req, Response res) -> {
        List<SysRole> roles = SysRole.find.query().where().eq("type", "管理").findList();
        SysManager field = new SysManager();

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("roles", roles);
        model.put("states", states);
        return ViewUtil.render(req, model, "views/sys/manager/add.vm");
    };

    public static Route Save = (Request req, Response res) -> {
        List<SysRole> roles = SysRole.find.query().where().eq("type", "管理").findList();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("roles", roles);
        model.put("states", states);

        String username = StrUtil.toStr(req.queryParams("username"));
        String password = StrUtil.toStr(req.queryParams("password"));
        String name = StrUtil.toStr(req.queryParams("name"));
        String mobile = StrUtil.toStr(req.queryParams("mobile"));
        String email = StrUtil.toStr(req.queryParams("email"));
        int admin = StrUtil.toInt(req.queryParams("admin"));
        String state = StrUtil.toStr(req.queryParams("state"));
        long roleId = StrUtil.toLong(req.queryParams("role_id"));
        SysRole role = SysRole.find.byId(roleId);

        SysManager field = new SysManager();
        field.setUsername(username);
        field.setName(name);
        field.setMobile(mobile);
        field.setEmail(email);
        field.setAdmin(admin);
        field.setState(state);
        field.setRole(role);
        field.setCreatedAt(new Date());

        model.put("field", field);
        String msg = null;
        int checkUnique = SysManager.find.query().where().eq("username", username).findCount();
        if (checkUnique > 0) {
            msg = "用户已存在";
        }
        if (msg != null) {
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/admin/manager/add.vm");
        }

        try {
            field.setPassword(SecurityUtil.SHA(password));
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
            field.setPassword("");
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/manager/add.vm");
        }
        res.redirect(PathSys.Manager());
        return null;

    };

    public static Route Edit = (Request req, Response res) -> {
        List<SysRole> roles = SysRole.find.query().where().eq("type", "管理").findList();

        long id = StrUtil.toLong(req.params("id"));
        SysManager field = SysManager.find.byId(id);
        field.setPassword("******");

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("roles", roles);
        model.put("states", states);
        return ViewUtil.render(req, model, "views/sys/manager/edit.vm");
    };

    public static Route Update = (Request req, Response res) -> {
        List<SysRole> roles = SysRole.find.query().where().eq("type", "管理").findList();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        model.put("roles", roles);
        model.put("states", states);

        long id = StrUtil.toLong(req.params("id"));
        SysManager field = SysManager.find.byId(id);

        String username = StrUtil.toStr(req.queryParams("username"));
        String password = StrUtil.toStr(req.queryParams("password"));
        String name = StrUtil.toStr(req.queryParams("name"));
        String mobile = StrUtil.toStr(req.queryParams("mobile"));
        String email = StrUtil.toStr(req.queryParams("email"));
        String state = StrUtil.toStr(req.queryParams("state"));
        int admin = StrUtil.toInt(req.queryParams("admin"));
        long roleId = StrUtil.toLong(req.queryParams("role_id"));
        SysRole role = SysRole.find.byId(roleId);

        field.setUsername(username);
        field.setName(name);
        field.setMobile(mobile);
        field.setEmail(email);
        field.setAdmin(admin);
        field.setRole(role);
        field.setState(state);

        model.put("field", field);
        String msg = null;
        int checkUnique = SysManager.find.query().where().ne("id", id).eq("username", username).findCount();
        if (checkUnique > 0) {
            msg = "用户已存在";
        }
        if (msg != null) {
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/admin/manager/edit.vm");
        }

        try {
            if (!password.equals("******")) {
                field.setPassword(SecurityUtil.SHA(password));
                field.setUpdatedAt(null);
            }
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
            field.setPassword("");
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/manager/edit.vm");
        }
        res.redirect(PathSys.Manager());
        return null;
    };

    public static Route Delete = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SysManager field = SysManager.find.byId(id);
        try {
            if (field != null) {
                field.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.redirect(PathSys.Manager());
        return null;
    };

}
