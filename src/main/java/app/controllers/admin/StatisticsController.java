package app.controllers.admin;

import app.models.SurveyPerson;
import app.utils.ViewUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import conf.PathAdmin;
import io.ebean.Expr;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsController {
    private static String pageTitle = "员工统计";

    public static Route Index = (Request req, Response res) -> {
        List<SurveyPerson> persons = SurveyPerson.find.query().where()
                .isNull("deletedAt")
                .order("enterprise_id desc").findList();

        List<SurveyPerson> travelPasss = new ArrayList<SurveyPerson>();
        int travelPassCount = 0;

        for(SurveyPerson field : persons) {
            JSONObject extNode = JSON.parseObject(field.getExt());
            if(extNode.get("travel_pass").equals("是")){
                travelPasss.add(field);
                travelPassCount++;
            }
        }
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", pageTitle + "汇总");

        return ViewUtil.render(req, model, "views/admin/statistics/persion/index.vm");
    };
}
