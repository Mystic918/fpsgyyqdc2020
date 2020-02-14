package app.controllers.sys;

import app.models.SysParams;
import app.utils.StrUtil;
import app.utils.ViewUtil;
import conf.PathSys;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsController {

    private static String pageTitle = "参数";
    private static String parent = "sys";
    private static String current = "sys_params";

    public static Route List = (Request req, Response res) -> {
        List<SysParams> lists = SysParams.find.query().where()
                .order("alias")
                .findList();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle + "列表");
        model.put("parent", parent);
        model.put("current", current);
        model.put("lists", lists);
        return ViewUtil.render(req, model, "views/sys/params/list.vm");
    };

    public static Route Add = (Request req, Response res) -> {
        SysParams field = new SysParams();

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        return ViewUtil.render(req, model, "views/sys/params/add.vm");
    };

    public static Route Save = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);

        String name = StrUtil.toStr(req.queryParams("name"));
        String alias = StrUtil.toStr(req.queryParams("alias"));
        String value = StrUtil.toStr(req.queryParams("value"));
        String remark = StrUtil.toStr(req.queryParams("remark"));

        SysParams field = new SysParams();
        field.setName(name);
        field.setAlias(alias);
        field.setValue(value);
        field.setRemark(remark);

        try {
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/params/add.vm");
        }
        res.redirect(PathSys.Params());
        return null;

    };

    public static Route Edit = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SysParams field = SysParams.find.byId(id);

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);
        return ViewUtil.render(req, model, "views/sys/params/edit.vm");
    };

    public static Route Update = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", parent);
        model.put("current", current);

        long id = StrUtil.toLong(req.params("id"));
        SysParams field = SysParams.find.byId(id);
        String name = StrUtil.toStr(req.queryParams("name"));
        String alias = StrUtil.toStr(req.queryParams("alias"));
        String value = StrUtil.toStr(req.queryParams("value"));
        String remark = StrUtil.toStr(req.queryParams("remark"));

        field.setName(name);
        field.setAlias(alias);
        field.setValue(value);
        field.setRemark(remark);
        try {
            field.save();
        } catch (Exception e) {
            e.printStackTrace();
            model.put("field", field);
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/sys/params/edit.vm");
        }
        res.redirect(PathSys.Params());
        return null;
    };

    public static Route Delete = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SysParams field = SysParams.find.byId(id);
        try {
            if (field != null) {
                field.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.redirect(PathSys.Params());
        return null;
    };

}
