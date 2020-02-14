package conf;

import app.controllers.admin.*;

import static spark.Spark.*;

public class RouteAdmin {

    public static void init() {

        get(PathAdmin.Enterprise(), EnterpriseController.List);
        get(PathAdmin.EnterpriseAdd(), EnterpriseController.Add);
        post(PathAdmin.EnterpriseAdd(), EnterpriseController.Save);
        get(PathAdmin.EnterpriseEdit(":id"), EnterpriseController.Edit);
        post(PathAdmin.EnterpriseEdit(":id"), EnterpriseController.Update);
        post(PathAdmin.EnterpriseDelete(":id"), EnterpriseController.Remove);
        get(PathAdmin.EnterpriseAjax(), EnterpriseController.AjaxList);
        get(PathAdmin.EnterpriseDetail(":id"), EnterpriseController.Detail);
        get(PathAdmin.EnterpriseExport(), EnterpriseController.Export);

        get(PathAdmin.Person(), PersonController.List);
        get(PathAdmin.PersonAdd(), PersonController.Add);
        post(PathAdmin.PersonAdd(), PersonController.Save);
        get(PathAdmin.PersonEdit(":id"), PersonController.Edit);
        post(PathAdmin.PersonEdit(":id"), PersonController.Update);
        post(PathAdmin.PersonDelete(":id"), PersonController.Remove);
        get(PathAdmin.PersonDetail(":id"), PersonController.Detail);
        get(PathAdmin.PersonExport(), PersonController.Export);

    }
}