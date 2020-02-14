package app;


import io.ebean.Platform;
import io.ebean.dbmigration.DbMigration;

public class MainDbMigration {

    public static void main(String[] args) {

        try{
            System.setProperty("ddl.migration.version", "1.1");
            System.setProperty("ddl.migration.name", "initial");
//        System.setProperty("ddl.migration.pendingDropsFor", "1.1");

            DbMigration dbMigration = new DbMigration();
            dbMigration.setPlatform(Platform.MYSQL);
            dbMigration.generateMigration();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
