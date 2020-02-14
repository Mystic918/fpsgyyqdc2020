package conf;

import app.controllers.main.*;
import app.controllers.sys.*;

import static spark.Spark.*;

public class RouteSys {

    public static void init() {

        before(PathSys.Filter(), EntryController.Auth);
        before(PathSys.JsonFilter(), EntryController.AuthJson);

        post(PathSys.UploadPic(), UploadController.UploadPic);
        post(PathSys.UploadFile(), UploadController.UploadFile);
        post(PathSys.UploadAudio(), UploadController.UploadAudio);
        post(PathSys.UploadVideo(), UploadController.UploadVideo);
        post(PathSys.UploadCkeditor(), UploadController.UploadCkeditor);

        post(PathSys.ExampleXlsx(), ExampleController.Xlsx);

        get(PathSys.Root(), EntryController.Root);
        get(PathSys.Login(), EntryController.Login);
        post(PathSys.Login(), EntryController.CheckLogin);
        get(PathSys.Logout(), EntryController.Logout);
        get(PathSys.Home(), EntryController.Home);
        get(PathSys.Setup(), EntryController.Setup);
        post(PathSys.Setup(), EntryController.SetupSave);
        get(PathSys.LeftMenu(), EntryController.LeftMenu);

        get(PathSys.Init(), EntryController.InitData);

        get(PathSys.Passwd(), ProfileController.Passwd);
        post(PathSys.Passwd(), ProfileController.PasswdSave);
        get(PathSys.Profile(), ProfileController.Profile);
        post(PathSys.Profile(), ProfileController.ProfileSave);

        get(PathSys.Manager(), ManagerController.List);
        get(PathSys.ManagerAdd(), ManagerController.Add);
        post(PathSys.ManagerAdd(), ManagerController.Save);
        get(PathSys.ManagerEdit(":id"), ManagerController.Edit);
        post(PathSys.ManagerEdit(":id"), ManagerController.Update);
        post(PathSys.ManagerDelete(":id"), ManagerController.Delete);

        post(PathSys.MenuSort(), MenuController.Sort);
        get(PathSys.Menu(), MenuController.List);
        get(PathSys.MenuAdd(), MenuController.Add);
        post(PathSys.MenuAdd(), MenuController.Save);
        get(PathSys.MenuEdit(":id"), MenuController.Edit);
        post(PathSys.MenuEdit(":id"), MenuController.Update);
        post(PathSys.MenuDelete(":id"), MenuController.Delete);

        get(PathSys.Role(), RoleController.List);
        get(PathSys.RoleAdd(), RoleController.Add);
        post(PathSys.RoleAdd(), RoleController.Save);
        get(PathSys.RoleEdit(":id"), RoleController.Edit);
        post(PathSys.RoleEdit(":id"), RoleController.Update);
        post(PathSys.RoleDelete(":id"), RoleController.Delete);
        get(PathSys.RoleMenu(":id"), RoleController.Menu);
        post(PathSys.RoleMenu(":id"), RoleController.MenuSave);
        post(PathSys.RoleMenuDelete(":id", ":menu_id"), RoleController.MenuDelete);

        get(PathSys.Params(), ParamsController.List);
        get(PathSys.ParamsAdd(), ParamsController.Add);
        post(PathSys.ParamsAdd(), ParamsController.Save);
        get(PathSys.ParamsEdit(":id"), ParamsController.Edit);
        post(PathSys.ParamsEdit(":id"), ParamsController.Update);
        post(PathSys.ParamsDelete(":id"), ParamsController.Delete);
    }
}