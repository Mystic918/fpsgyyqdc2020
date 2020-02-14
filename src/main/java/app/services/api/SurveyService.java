package app.services.api;

import app.models.SurveyEnterprise;
import app.models.SurveyPerson;
import app.utils.FormUtil;
import app.utils.QqLbsUtil;
import app.utils.SecurityUtil;
import app.utils.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import spark.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SurveyService {

    public static JSONObject enterprise(Request req) {
        JSONObject result = new JSONObject();

        String code = StrUtil.toStr(req.queryParams("code"));
        String password = StrUtil.toStr(req.queryParams("password"));
        password = SecurityUtil.base64Decode(password);
        password = SecurityUtil.SHA(password);

        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("code", code)
            .isNull("deletedAt")
            .setMaxRows(1)
            .findOne();
        JSONObject dataNode = new JSONObject();

        if (field == null) {
            dataNode.put("id", 0);
            result.put("code", 200);
            result.put("data", dataNode);
            return result;
        }

        if (!password.equals(field.getPassword())) {
            result.put("code", 400);
            result.put("msg", "查询密码不正确");
            return result;
        }

        dataNode.put("id", field.getId());
        dataNode.put("name", field.getName());
        dataNode.put("code", field.getCode());
        JSONObject extNode = JSON.parseObject(field.getExt());
        for (String extKey : extNode.keySet()) {
            dataNode.put("ext_" + extKey, extNode.get(extKey));
        }

        result.put("code", 200);
        result.put("data", dataNode);
        return result;
    }

    public static JSONObject enterprisePassword(Request req) {
        JSONObject result = new JSONObject();

        String code = StrUtil.toStr(req.queryParams("code"));
        String password = StrUtil.toStr(req.queryParams("password"));
        password = SecurityUtil.base64Decode(password);
        password = SecurityUtil.SHA(password);
        String passwordNew = StrUtil.toStr(req.queryParams("password_new"));
        passwordNew = SecurityUtil.base64Decode(passwordNew);
        passwordNew = SecurityUtil.SHA(passwordNew);

        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("code", code)
            .eq("password", password)
            .isNull("deletedAt")
            .setMaxRows(1)
            .findOne();

        if (field == null) {
            result.put("code", 400);
            result.put("msg", "信息不存在");
            return result;
        }

        field.setPassword(passwordNew);
        field.save();

        result.put("code", 200);
        return result;
    }

    public static JSONObject enterprisePersion(Request req) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JSONObject result = new JSONObject();
        JSONArray dataArray = new JSONArray();

        String code = StrUtil.toStr(req.queryParams("code"));
        String password = StrUtil.toStr(req.queryParams("password"));
        password = SecurityUtil.base64Decode(password);
        password = SecurityUtil.SHA(password);

        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("code", code)
            .eq("password", password)
            .isNull("deletedAt")
            .setMaxRows(1)
            .findOne();

        if (field == null) {
            result.put("code", 400);
            result.put("msg", "信息不存在");
            return result;
        }

        List<SurveyPerson> persons = SurveyPerson.find.query().where()
            .eq("enterprise", field)
            .isNull("deletedAt")
            .findList();

        for (SurveyPerson p : persons) {
            JSONObject itemNode = new JSONObject();
            itemNode.put("name", p.getName());

            String mobile = p.getMobile().length() > 3 ? p.getMobile().substring(p.getMobile().length() - 4) : p.getMobile();
            itemNode.put("mobile", "****" + mobile);

            String idCode = p.getCode().length() > 3 ? p.getCode().substring(p.getCode().length() - 4) : p.getCode();
            itemNode.put("code", "****" + idCode);

            JSONObject extNode = JSON.parseObject(p.getExt());
            for (String extKey : extNode.keySet()) {
                itemNode.put("ext_" + extKey, extNode.get(extKey));
            }
            itemNode.put("created_at", df.format(p.getCreatedAt()));

            dataArray.add(itemNode);
        }

        result.put("data", dataArray);
        result.put("code", 200);
        return result;
    }

    public static JSONObject enterpriseSave(Request req) {
        JSONObject result = new JSONObject();

        String name = StrUtil.toStr(req.queryParams("name"));
        String code = StrUtil.toStr(req.queryParams("code"));
        String password = StrUtil.toStr(req.queryParams("password"));
        password = SecurityUtil.base64Decode(password);
        password = SecurityUtil.SHA(password);
        String ext = ApiService.getExt(req);

        SurveyEnterprise field = SurveyEnterprise.find.query().where()
            .eq("code", code)
            .isNull("deletedAt")
            .setMaxRows(1)
            .findOne();

        if (field == null) {
            field = new SurveyEnterprise();
            field.setName(name);
            field.setCode(code);
            field.setPassword(password);
            field.setCreatedAt(new Date());
        }
        if (!password.equals(field.getPassword())) {
            result.put("code", 400);
            result.put("msg", "查询密码不正确");
            return result;
        }

        field.setExt(ext);
        field.setUpdatedAt(new Date());
        field.save();

        result.put("code", 200);
        result.put("data", field.getId());
        return result;
    }

    public static JSONObject personEnterprise(Request req) {
        JSONObject result = new JSONObject();

        long eid = StrUtil.toLong(req.params("eid"));

        SurveyEnterprise field = SurveyEnterprise.find.byId(eid);
        if (field == null) {
            result.put("code", 400);
            result.put("msg", "企业不存在");
            return result;
        }

        JSONObject dataNode = new JSONObject();
        dataNode.put("id", field.getId());
        dataNode.put("name", field.getName());

        result.put("code", 200);
        result.put("data", dataNode);
        return result;
    }

    public static JSONObject personSave(Request req) {
        JSONObject result = new JSONObject();

        long eid = StrUtil.toLong(req.params("eid"));
        String name = StrUtil.toStr(req.queryParams("name"));
        String code = StrUtil.toStr(req.queryParams("code"));
        String mobile = StrUtil.toStr(req.queryParams("mobile"));
        String ext = ApiService.getExt(req);

        SurveyEnterprise enterprise = SurveyEnterprise.find.query().where()
            .eq("id", eid)
            .isNull("deletedAt")
            .findOne();

        if (enterprise == null) {
            result.put("code", 400);
            result.put("msg", "企业不存在");
            return result;
        }
        if (!FormUtil.isIdCard(code)) {
            result.put("code", 400);
            result.put("msg", "身份证格式不正确");
            return result;
        }
        if (!FormUtil.isMobile(mobile)) {
            result.put("code", 400);
            result.put("msg", "手机格式不正确");
            return result;
        }

        int checkField = SurveyPerson.find.query().where()
            .eq("code", code)
            .isNull("deletedAt")
            .findCount();
        if (checkField > 0) {
            result.put("code", 400);
            result.put("msg", "请勿重复申请");
            return result;
        }

        String ip = ApiService.getIp(req);
        String location = "{}";
        try {
            JSONObject resultIp = QqLbsUtil.getInstatnce().locationIp(ip);
            if (resultIp.getIntValue("code") == 200) {
                location = JSON.toJSONString(resultIp.getJSONObject("data"));
            }
        } catch (Exception e) {
        }

        SurveyPerson field = new SurveyPerson();
        field.setEnterprise(enterprise);
        field.setName(name);
        field.setCode(code);
        field.setMobile(mobile);
        field.setExt(ext);
        field.setCreatedAt(new Date());
        field.setUpdatedAt(new Date());
        field.setIp(ip);
        field.setLocation(location);
        field.save();

        result.put("code", 200);
        return result;
    }
}