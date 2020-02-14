package app.controllers.admin;

import app.models.SurveyEnterprise;
import app.services.AdminService;
import app.utils.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import conf.PathAdmin;
import io.ebean.PagedList;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.*;
import java.util.*;

public class EnterpriseController {

    private static String pageTitle = "企业管理";
    private static String pageParent = "admin_enterprise";
    private static String pageCurrent = "";
    private static String pageUrl = PathAdmin.Enterprise();

    public static Route List = (Request req, Response res) -> {
        String queryName = StrUtil.toStr(req.queryParams("query_name"));
        Date startDate = StrUtil.toDate("2000-01-01", "yyyy-MM-dd");
        Date endDate = StrUtil.toDate("2099-12-31", "yyyy-MM-dd");
        String queryStart = StrUtil.toStr(req.queryParams("query_start"));
        Date queryStartDate = StrUtil.toDate(queryStart + " 00:00:00", "yyyy-MM-dd HH:mm:ss", startDate);
        String queryEnd = StrUtil.toStr(req.queryParams("query_end"));
        Date queryEndDate = StrUtil.toDate(queryEnd + " 23:59:59", "yyyy-MM-dd HH:mm:ss", endDate);

        int page = StrUtil.toInt(req.queryParams("page"));
        int pagesize = StrUtil.toInt(req.queryParams("pagesize"));
        pagesize = pagesize == 0 ? 10 : pagesize;

        int offset = page * pagesize;
        int maxrow = pagesize;

        PagedList<SurveyEnterprise> pagedList = SurveyEnterprise.find.query().where()
            .like("name", "%" + queryName + "%")
            .ge("createdAt", queryStartDate)
            .le("createdAt", queryEndDate)
            .isNull("deletedAt")
            .order("id desc")
            .setFirstRow(offset).setMaxRows(maxrow).findPagedList();
        pagedList.loadCount();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle + "列表");
        model.put("parent", pageParent);
        model.put("current", pageCurrent);
        model.put("pagedList", pagedList);
        model.put("queryName", queryName);
        model.put("queryStart", queryStart);
        model.put("queryEnd", queryEnd);
        return ViewUtil.render(req, model, "views/admin/enterprise/list.vm");
    };

    public static Route AjaxList = (Request req, Response res) -> {
        List<SurveyEnterprise> lists = SurveyEnterprise.find.query().where()
            .isNull("deletedAt")
            .order("name")
            .findList();

        JSONArray listArray = new JSONArray();

        for(SurveyEnterprise item : lists){
            JSONObject itemNode = new JSONObject();
            itemNode.put("id", item.getId());
            itemNode.put("name", item.getName());
            listArray.add(itemNode);
        }

        return listArray;
    };

    public static Route Add = (Request req, Response res) -> {
        SurveyEnterprise field = new SurveyEnterprise();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);
        model.put("field", field);
        return ViewUtil.render(req, model, "views/admin/enterprise/add.vm");
    };

    public static Route Save = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);

        String name = StrUtil.toStr(req.queryParams("name"));
        String code = StrUtil.toStr(req.queryParams("code"));
        String password = StrUtil.toStr(req.queryParams("password"));
        String ext = AdminService.getExt(req);

        SurveyEnterprise field = new SurveyEnterprise();
        field.setName(name);
        field.setCode(code);
        field.setExt(ext);
        field.setUpdatedAt(new Date());
        field.setCreatedAt(new Date());

        model.put("field", field);
        String msg = null;
        int repeatCount = SurveyEnterprise.find.query().where().eq("code", code).findCount();
        if(repeatCount > 0){
            msg = "信息已存在";
        }
        if(msg != null){
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/admin/enterprise/add.vm");
        }

        try{
            field.setPassword(SecurityUtil.SHA(password));
            field.save();
        }catch (Exception e){
            e.printStackTrace();
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/admin/enterprise/add.vm");
        }
        res.redirect(pageUrl);
        return null;
    };

    public static Route Edit = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            res.redirect(pageUrl);
            return null;
        }
        field.setPassword("******");

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);
        model.put("field", field);
        return ViewUtil.render(req, model, "views/admin/enterprise/edit.vm");
    };

    public static Route Update = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);

        long id = StrUtil.toLong(req.params("id"));
        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            res.redirect(pageUrl);
            return null;
        }

        String name = StrUtil.toStr(req.queryParams("name"));
        String code = StrUtil.toStr(req.queryParams("code"));
        String password = StrUtil.toStr(req.queryParams("password"));
        String ext = AdminService.getExt(req);

        field.setName(name);
        field.setCode(code);
        field.setExt(ext);
        field.setUpdatedAt(new Date());

        model.put("field", field);
        String msg = null;
        int repeatCount = SurveyEnterprise.find.query().where().ne("id", id).eq("code", code).findCount();
        if(repeatCount > 0){
            msg = "信息已存在";
        }
        if (!password.equals("******")) {
            if(!FormUtil.isTradePassword(password)){
                msg = "密码格式不正确";
            }
            field.setPassword(SecurityUtil.SHA(password));
        }
        if(msg != null){
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/admin/enterprise/edit.vm");
        }

        try{
            field.save();
        }catch (Exception e){
            e.printStackTrace();
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/admin/enterprise/edit.vm");
        }
        res.redirect(pageUrl);
        return null;
    };

    public static Route Remove = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            res.redirect(pageUrl);
            return null;
        }

        field.setDeletedAt(new Date());
        field.save();

        res.redirect(pageUrl);
        return null;
    };

    public static Route Detail = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            return "信息不存在";
        }

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        return ViewUtil.render(req, model, "views/admin/enterprise/detail.vm");
    };

    public static Route Export = (Request req, Response res) -> {
        String queryName = StrUtil.toStr(req.queryParams("query_name"));
        Date startDate = StrUtil.toDate("2000-01-01", "yyyy-MM-dd");
        Date endDate = StrUtil.toDate("2099-12-31", "yyyy-MM-dd");
        String queryStart = StrUtil.toStr(req.queryParams("query_start"));
        Date queryStartDate = StrUtil.toDate(queryStart + " 00:00:00", "yyyy-MM-dd HH:mm:ss", startDate);
        String queryEnd = StrUtil.toStr(req.queryParams("query_end"));
        Date queryEndDate = StrUtil.toDate(queryEnd + " 23:59:59", "yyyy-MM-dd HH:mm:ss", endDate);

        List<SurveyEnterprise> enterprises = SurveyEnterprise.find.query().where()
            .like("name", "%" + queryName + "%")
            .ge("createdAt", queryStartDate)
            .le("createdAt", queryEndDate)
            .isNull("deletedAt")
            .order("id desc")
            .findList();

        List<String[]> lists = new ArrayList();
        for (SurveyEnterprise field : enterprises) {
            String[] items = new String[12];
            items[0] = field.getName();
            items[1] = field.getCode();
            items[2] = TplUtil.extToStr(field.getExt(), "address");
            items[3] = TplUtil.extToStr(field.getExt(), "phone_24");
            items[4] = TplUtil.extToStr(field.getExt(), "fr");
            items[5] = TplUtil.extToStr(field.getExt(), "contact");
            items[6] = TplUtil.extToStr(field.getExt(), "phone");
            items[7] = TplUtil.extToStr(field.getExt(), "mobile");
            items[8] = TplUtil.extToStr(field.getExt(), "total_person");
            items[9] = TplUtil.extToStr(field.getExt(), "key_person");
            items[10] = TplUtil.extToStr(field.getExt(), "back_person");
            items[11] = StrUtil.toStr(field.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
            lists.add(items);
        }
        try {
            String[] header = {"企业名称", "统一社会信用代码", "企业地址", "24小时值班电话", "法定代表人",
                "联系人", "联系电话", "手机号码", "员工总人数", "来自或去过疫情重点地区人数", "返岗人数", "创建时间"};
            File file = OfficeUtil.createXlsx(header, new String[header.length], lists, "企业列表" + StrUtil.toStr(new Date(), "yyyyMMddHHmmss"));
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            res.raw().reset();
            res.raw().addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
            res.raw().addHeader("Content-Length", "" + file.length());

            OutputStream out = new BufferedOutputStream(res.raw().getOutputStream());
            res.raw().setContentType("application/vnd.ms-excel;charset=gb2312");
            out.write(buffer);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.redirect(pageUrl);
        return null;
    };

}
