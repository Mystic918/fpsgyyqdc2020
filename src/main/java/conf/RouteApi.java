package conf;

import app.controllers.api.*;
import app.controllers.main.TaskController;

import static spark.Spark.*;

public class RouteApi {

    public static void init() {

        get("/fps/task/person/location", TaskController.PersonLocation);

        get("/survey2020/api/enterprise", SurveyController.Enterprise);
        post("/survey2020/api/enterprise", SurveyController.EnterpriseSave);
        post("/survey2020/api/enterprise/password", SurveyController.EnterprisePassword);
        get("/survey2020/api/enterprise/person", SurveyController.EnterprisePersion);

        get("/survey2020/api/person/:eid", SurveyController.PersonEnterprise);
        post("/survey2020/api/person/:eid", SurveyController.PersonSave);

    }
}