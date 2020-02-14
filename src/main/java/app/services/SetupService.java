package app.services;

import app.models.SysManager;
import app.models.SysMenu;
import app.utils.SecurityUtil;

import java.util.Date;

public class SetupService {

    public static void initData() throws Exception {

        SysManager manager = null;
        manager = SysManager.find.query().where().eq("username", "admin").findOne();
        if (manager == null) {
            manager = new SysManager();
            manager.setUsername("admin");
            manager.setName("管理员");
            manager.setPassword(SecurityUtil.SHA("7811393"));
            manager.setState("开启");
            manager.setAdmin(1);
            manager.setCreatedAt(new Date());
            manager.setUpdatedAt(new Date());
            manager.save();
        }

        SysMenu menu = null;
        menu = SysMenu.find.query().where().eq("alias", "sys").isNull("parent_id").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("系统管理");
            menu.setAlias("sys");
            menu.setType("系统");
            menu.setIcon("fa fa-cog");
            menu.setPriority(99);
            menu.setIsShow(1);
            menu.save();
        }
        menu = SysMenu.find.query().where().eq("alias", "sys_manager").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("管理员管理");
            menu.setAlias("sys_manager");
            menu.setType("系统");
            menu.setParent(SysMenu.find.query().where().eq("alias", "sys").findOne());
            menu.setLink("/survey2020/admin/sys/manager");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }
        menu = SysMenu.find.query().where().eq("alias", "sys_menu").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("菜单管理");
            menu.setAlias("sys_menu");
            menu.setType("系统");
            menu.setParent(SysMenu.find.query().where().eq("alias", "sys").findOne());
            menu.setLink("/survey2020/admin/sys/menu");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }
        menu = SysMenu.find.query().where().eq("alias", "sys_role").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("角色管理");
            menu.setAlias("sys_role");
            menu.setType("系统");
            menu.setParent(SysMenu.find.query().where().eq("alias", "sys").findOne());
            menu.setLink("/survey2020/admin/sys/role");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }
        menu = SysMenu.find.query().where().eq("alias", "sys_params").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("系统参数");
            menu.setAlias("sys_params");
            menu.setType("系统");
            menu.setParent(SysMenu.find.query().where().eq("alias", "sys").findOne());
            menu.setLink("/survey2020/admin/sys/params");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }

        menu = SysMenu.find.query().where().eq("alias", "admin_enterprise").isNull("parent_id").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("企业管理");
            menu.setAlias("admin_enterprise");
            menu.setType("管理");
            menu.setLink("/survey2020/admin/enterprise");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }

        menu = SysMenu.find.query().where().eq("alias", "admin_person").isNull("parent_id").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("员工管理");
            menu.setAlias("admin_person");
            menu.setType("管理");
            menu.setLink("/survey2020/admin/person");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }

        menu = SysMenu.find.query().where().eq("alias", "admin_statistics").isNull("parent_id").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("数据统计");
            menu.setAlias("admin_statistics");
            menu.setType("管理");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }
        menu = SysMenu.find.query().where().eq("alias", "admin_statistics_enterprise").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("企业统计");
            menu.setAlias("admin_statistics_enterprise");
            menu.setType("管理");
            menu.setParent(SysMenu.find.query().where().eq("alias", "admin_statistics").findOne());
            menu.setLink("/survey2020/admin/statistics/enterprise");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }
        menu = SysMenu.find.query().where().eq("alias", "admin_statistics_person").findOne();
        if (menu == null) {
            menu = new SysMenu();
            menu.setName("员工统计");
            menu.setAlias("admin_statistics_person");
            menu.setType("管理");
            menu.setParent(SysMenu.find.query().where().eq("alias", "admin_statistics").findOne());
            menu.setLink("/survey2020/admin/statistics/person");
            menu.setPriority(0);
            menu.setIsShow(1);
            menu.save();
        }
    }

}
