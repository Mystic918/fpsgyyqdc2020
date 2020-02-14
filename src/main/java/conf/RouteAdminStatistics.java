package conf;

import app.controllers.admin.StatisticsController;

import static spark.Spark.get;

public class RouteAdminStatistics {
    public static void init() {
        get(PathAdminStatistics.Person(), StatisticsController.Index);
    }

}
