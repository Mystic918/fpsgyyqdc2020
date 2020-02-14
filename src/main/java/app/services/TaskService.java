package app.services;

import app.models.SurveyPerson;
import app.utils.QqLbsUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.ebean.Expr;
import java.util.*;

public class TaskService {

    public static JSONObject personLocation(){
        JSONObject result = new JSONObject();

        int count = 0;
        List<SurveyPerson> lists = SurveyPerson.find.query().where()
                .or(Expr.eq("location", ""), Expr.isNull("location"))
                .setMaxRows(5)
                .findList();
        for(SurveyPerson field : lists){
            String ip = field.getIp();
            String location = "{}";
            JSONObject resultIp = QqLbsUtil.getInstatnce().locationIp(ip);
            if(resultIp.getIntValue("code") == 200){
                location = JSON.toJSONString(resultIp.getJSONObject("data"));
                count++;
            }
            field.setLocation(location);
            field.save();
        }
        result.put("code", 200);
        result.put("data", count);
        return result;
    }

}