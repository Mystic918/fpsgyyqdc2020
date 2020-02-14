package app.utils;

import conf.AppConf;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;

import java.util.Properties;

public class EbeanUtil {

    private static EbeanUtil instance;

    public static EbeanUtil getInstance(){
        if (instance == null){
            instance = new EbeanUtil();
        }
        return instance;
    }

    public void setup() {
        try {
            Properties p = new Properties();
            p.load(getClass().getResourceAsStream("/"+ AppConf.ENV+".properties"));
            ServerConfig config = new ServerConfig();
            config.setName("default");
            config.setDefaultServer(true);
            config.loadFromProperties(p);
            EbeanServer server = EbeanServerFactory.create(config);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
