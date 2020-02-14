package app.controllers.main;

import app.services.TaskService;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class TaskController {

    public static Route PersonLocation = (Request req, Response res) -> {
        res.header("Content-Type", "application/json;charset=UTF-8");
        JSONObject result = new JSONObject();
        try {
            int count = 0;
            for(int i = 0; i < 100; i++){
                JSONObject resultLocation = TaskService.personLocation();
                if(resultLocation.getIntValue("data") == 0){
                    break;
                }
                count += resultLocation.getIntValue("data");
                Thread.currentThread().sleep(800);
            }
            result.put("code", 200);
            result.put("data", count);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    };

}
