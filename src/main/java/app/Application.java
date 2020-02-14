package app;

import conf.AppConf;
import conf.AppInit;
import spark.servlet.SparkApplication;

import static spark.Spark.port;

public class Application implements SparkApplication {

    @Override
    public void init() {
        AppInit.setup();
    }

    public static void main(String[] args) {
        port(AppConf.PORT);
        AppConf.ENV = "dev";
        AppInit.setup();
    }
}
