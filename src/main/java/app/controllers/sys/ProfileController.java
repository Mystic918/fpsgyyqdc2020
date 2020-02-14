package app.controllers.sys;

import app.models.SysManager;
import app.services.AdminService;
import app.utils.SecurityUtil;
import app.utils.StrUtil;
import app.utils.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class ProfileController {

    public static Route Passwd = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(req, model, "views/sys/profile/passwd.vm");
    };

    public static Route PasswdSave = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();

        String username = AdminService.getSessionUser(req, "name").toString();
        String password = StrUtil.toStr(req.queryParams("password"));
        String password1 = StrUtil.toStr(req.queryParams("password1"));
        String password2 = StrUtil.toStr(req.queryParams("password2"));

        String msg = null;

        SysManager field = SysManager.find.query().where().eq("username", username).findOne();
        password = SecurityUtil.SHA(password);

        if (!field.getPassword().equals(password)) {
            msg = "旧密码不正确";
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/sys/profile/passwd.vm");
        }

        if (!password1.equals(password2)) {
            msg = "两次密码不一致";
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/sys/profile/passwd.vm");
        }

        password1 = SecurityUtil.SHA(password1);
        field.setPassword(password1);
        try {
            field.save();
            model.put("success", "修改成功");
            return ViewUtil.render(req, model, "views/sys/profile/passwd.vm");
        } catch (Exception e) {
            e.printStackTrace();
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/profile/passwd.vm");
        }
    };

    public static Route Profile = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        String username = AdminService.getSessionUser(req, "name").toString();
        SysManager field = SysManager.find.query().where().eq("username", username).findOne();

        model.put("field", field);
        return ViewUtil.render(req, model, "views/sys/profile/profile.vm");
    };

    public static Route ProfileSave = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();

        String username = AdminService.getSessionUser(req, "name").toString();
        SysManager field = SysManager.find.query().where().eq("username", username).findOne();

        String name = StrUtil.toStr(req.queryParams("name"));
        String mobile = StrUtil.toStr(req.queryParams("mobile"));
        String email = StrUtil.toStr(req.queryParams("email"));

        field.setName(name);
        field.setMobile(mobile);
        field.setEmail(email);

        try {
            field.save();
            model.put("field", field);
            model.put("success", "修改成功");
            return ViewUtil.render(req, model, "views/sys/profile/profile.vm");
        } catch (Exception e) {
            e.printStackTrace();
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/profile/profile.vm");
        }
    };

}
