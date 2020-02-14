package app.controllers.main;

import app.services.UploadService;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class UploadController {

    public static Route UploadPic = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        try {
            result = UploadService.uploadPic(req);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    };

    public static Route UploadFile = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        try {
            result = UploadService.uploadFile(req);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    };

    public static Route UploadAudio = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        try {
            result = UploadService.uploadAudio(req);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    };

    public static Route UploadVideo = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        try {
            result = UploadService.uploadVideo(req);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    };

    public static Route UploadCkeditor = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        try {
            result = UploadService.uploadCkeditor(req);
        }catch (Exception e){
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "系统错误");
        }

        return result;
    };
}
