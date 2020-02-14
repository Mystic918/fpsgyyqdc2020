package app.controllers.api;

import app.services.api.SurveyService;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class SurveyController {

    public static Route Enterprise = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();

        try {
            result = SurveyService.enterprise(req);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
        }

        return result;
    };

    public static Route EnterpriseSave = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();

        try {
            result = SurveyService.enterpriseSave(req);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
        }

        return result;
    };

    public static Route EnterprisePassword = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();

        try {
            result = SurveyService.enterprisePassword(req);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
        }

        return result;
    };

    public static Route PersonEnterprise = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();

        try {
            result = SurveyService.personEnterprise(req);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
        }

        return result;
    };

    public static Route PersonSave = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();

        try {
            result = SurveyService.personSave(req);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
        }

        return result;
    };

    public static Route EnterprisePersion = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();

        try {
            result = SurveyService.enterprisePersion(req);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
        }

        return result;
    };
}
