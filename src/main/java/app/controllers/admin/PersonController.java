package app.controllers.admin;

import app.models.SurveyPerson;
import app.services.AdminService;
import app.utils.OfficeUtil;
import app.utils.StrUtil;
import app.utils.ViewUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import conf.PathAdmin;
import io.ebean.Expr;
import io.ebean.PagedList;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PersonController {

    private static String pageTitle = "员工管理";
    private static String pageParent = "admin_person";
    private static String pageCurrent = "";
    private static String pageUrl = PathAdmin.Person();

    public static Route List = (Request req, Response res) -> {
        String queryName = StrUtil.toStr(req.queryParams("query_name"));
        String queryEnterprise = StrUtil.toStr(req.queryParams("query_enterprise"));

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

        PagedList<SurveyPerson> pagedList = SurveyPerson.find.query().where()
            .or(Expr.like("name", "%" + queryName + "%"), Expr.like("mobile", "%" + queryName + "%"))
            .like("enterprise.name", "%" + queryEnterprise + "%")
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
        model.put("queryEnterprise", queryEnterprise);
        model.put("queryStart", queryStart);
        model.put("queryEnd", queryEnd);
        return ViewUtil.render(req, model, "views/admin/person/list.vm");
    };

    public static Route Add = (Request req, Response res) -> {
        SurveyPerson field = new SurveyPerson();

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);
        model.put("field", field);
        return ViewUtil.render(req, model, "views/admin/person/add.vm");
    };

    public static Route Save = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);

        String name = StrUtil.toStr(req.queryParams("name"));
        String code = StrUtil.toStr(req.queryParams("code"));
        String mobile = StrUtil.toStr(req.queryParams("mobile"));
        String ext = AdminService.getExt(req);

        SurveyPerson field = new SurveyPerson();
        field.setName(name);
        field.setCode(code);
        field.setMobile(mobile);
        field.setExt(ext);
        field.setUpdatedAt(new Date());
        field.setCreatedAt(new Date());

        model.put("field", field);
        String msg = null;
        int repeatCount = SurveyPerson.find.query().where()
            .eq("code", code)
            .isNull("deletedAt")
            .findCount();
        if(repeatCount > 0){
            msg = "信息已存在";
        }
        if(msg != null){
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/admin/person/add.vm");
        }

        try{
            field.save();
        }catch (Exception e){
            e.printStackTrace();
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/admin/person/add.vm");
        }
        res.redirect(pageUrl);
        return null;
    };

    public static Route Edit = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SurveyPerson field = SurveyPerson.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            res.redirect(pageUrl);
            return null;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);
        model.put("field", field);
        return ViewUtil.render(req, model, "views/admin/person/edit.vm");
    };

    public static Route Update = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle);
        model.put("parent", pageParent);
        model.put("current", pageCurrent);

        long id = StrUtil.toLong(req.params("id"));
        SurveyPerson field = SurveyPerson.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            res.redirect(pageUrl);
            return null;
        }

        String name = StrUtil.toStr(req.queryParams("name"));
        String code = StrUtil.toStr(req.queryParams("code"));
        String mobile = StrUtil.toStr(req.queryParams("mobile"));
        String ext = AdminService.getExt(req);

        field.setName(name);
        field.setCode(code);
        field.setMobile(mobile);
        field.setExt(ext);
        field.setUpdatedAt(new Date());

        model.put("field", field);
        String msg = null;
        int repeatCount = SurveyPerson.find.query().where().ne("id", id).eq("code", code).isNull("deletedAt").findCount();
        if(repeatCount > 0){
            msg = "信息已存在";
        }
        if(msg != null){
            model.put("msg", msg);
            return ViewUtil.render(req, model, "views/admin/person/edit.vm");
        }

        try{
            field.save();
        }catch (Exception e){
            e.printStackTrace();
            model.put("msg", "系统错误");
            return ViewUtil.render(req, model, "views/admin/person/edit.vm");
        }
        res.redirect(pageUrl);
        return null;
    };

    public static Route Remove = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SurveyPerson field = SurveyPerson.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            res.redirect(pageUrl);
            return null;
        }

        field.delete();

        res.redirect(pageUrl);
        return null;
    };

    public static Route Detail = (Request req, Response res) -> {
        long id = StrUtil.toLong(req.params("id"));
        SurveyPerson field = SurveyPerson.find.query().where()
            .eq("id", id)
            .isNull("deletedAt")
            .findOne();
        if(field == null){
            return "信息不存在";
        }

        Map<String, Object> model = new HashMap<>();
        model.put("field", field);
        return ViewUtil.render(req, model, "views/admin/person/detail.vm");
    };

    public static Route Export = (Request req, Response res) -> {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String queryName = StrUtil.toStr(req.queryParams("query_name"));
        String queryEnterprise = StrUtil.toStr(req.queryParams("query_enterprise"));

        Date startDate = StrUtil.toDate("2000-01-01", "yyyy-MM-dd");
        Date endDate = StrUtil.toDate("2099-12-31", "yyyy-MM-dd");
        String queryStart = StrUtil.toStr(req.queryParams("query_start"));
        Date queryStartDate = StrUtil.toDate(queryStart + " 00:00:00", "yyyy-MM-dd HH:mm:ss", startDate);
        String queryEnd = StrUtil.toStr(req.queryParams("query_end"));
        Date queryEndDate = StrUtil.toDate(queryEnd + " 23:59:59", "yyyy-MM-dd HH:mm:ss", endDate);

        List<SurveyPerson> persons = SurveyPerson.find.query().where()
                .or(Expr.like("name", "%" + queryName + "%"), Expr.like("mobile", "%" + queryName + "%"))
                .like("enterprise.name", "%" + queryEnterprise + "%")
                .ge("createdAt", queryStartDate)
                .le("createdAt", queryEndDate)
                .isNull("deletedAt")
                .order("enterprise_id desc").findList();

        List<String[]> lists = new ArrayList();
        for(SurveyPerson field : persons) {
            String[] items = new String[26];
            JSONObject extNode = JSON.parseObject(field.getExt());
            JSONObject locationNode = JSON.parseObject(field.getLocation());

            items[0] = field.getName();
            items[1] = field.getCode();
            items[2] = extNode.getString("place");
            items[3] = field.getMobile();
            items[4] = extNode.getString("work_unit");
            items[5] = extNode.getString("address");
            items[6] = extNode.getString("travel_leave");
            items[7] = extNode.getString("travel_back");
            items[8] = extNode.getString("travel_partner");
            items[9] = extNode.getString("travel_history");
            items[10] = extNode.getString("travel_pass");
            items[11] = extNode.getString("travel_touch");
            items[12] = extNode.getString("body_hot");
            items[13] = extNode.getString("body_cough");
            items[14] = extNode.getString("body_apart");
            items[15] = extNode.getString("relative_parent");
            items[16] = extNode.getString("relative_spouse");
            items[17] = extNode.getString("relative_child");
            items[18] = extNode.getString("back_live");
            items[19] = extNode.getString("back_eat");
            items[20] = extNode.getString("state_other");
            items[21] = field.getIp();
            items[22] = locationNode.getString("province");
            items[23] = locationNode.getString("city");
            items[24] = locationNode.getString("district");
            items[25] = df.format(field.getCreatedAt());
            lists.add(items);
        }

        try {
            String[] header = {"姓名", "身份证", "籍贯","手机号码","工作单位","现居住地","离开肇庆时间","回肇庆时间","随行人员",
                    "近14日旅行史","是否路经湖北或与湖北籍人员接触","是否与疑似或确诊病例密切接触","是否发热","是否有咳嗽症状",
                    "隔离情况","父母","配偶","子女","企业安排集中居住还是分散居住","企业是否安排集中用餐","其他需说明的情况","IP",
                    "IP所在省份","IP所在城市","IP所在区县","填报日期"};
            File file = OfficeUtil.createXlsx(header, new String[header.length], lists, "一员一档登记卡列表" + StrUtil.toStr(new Date(), "yyyyMMddHHmmss"));
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
