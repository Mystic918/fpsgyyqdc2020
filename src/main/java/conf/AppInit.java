package conf;


import app.utils.*;

import static spark.Spark.*;

public class AppInit {

    public static void setup() {
        staticFiles.location("/public");
        RouteApi.init();
        RouteAdmin.init();
        RouteSys.init();
        //RouteAdminStatistics.init();
        EbeanUtil.getInstance().setup();
    }

}
