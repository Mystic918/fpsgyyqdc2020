package conf;

public class PathSys {

    public static String UploadPic(){
        return "/survey2020/json/admin/upload/pic";
    }
    public static String UploadFile(){
        return "/survey2020/json/admin/upload/file";
    }
    public static String UploadAudio(){
        return "/survey2020/json/admin/upload/audio";
    }
    public static String UploadVideo(){
        return "/survey2020/json/admin/upload/video";
    }
    public static String UploadCkeditor(){
        return "/survey2020/json/admin/upload/ckeditor";
    }
    public static String ExampleXlsx(){
        return "/survey2020/admin/home/example/xlsx";
    }

    public static String Init() {
        return "/survey2020/init/setup";
    }

    public static String Filter() {
        return "/survey2020/admin/*";
    }

    public static String JsonFilter() {
        return "/survey2020/json/admin/*";
    }

    public static String Root() {
        return "/survey2020/admin";
    }

    public static String Login() {
        return "/survey2020/admin_login";
    }

    public static String Logout() {
        return "/survey2020/admin/home/logout";
    }

    public static String Setup() {
        return "/survey2020/admin/home/setup";
    }

    public static String Home() {
        return "/survey2020/admin/home";
    }

    public static String LeftMenu() {
        return "/survey2020/json/admin/left_menu";
    }

    public static String Passwd() {
        return "/survey2020/admin/home/passwd";
    }

    public static String Profile() {
        return "/survey2020/admin/home/profile";
    }

    public static String Manager() {
        return "/survey2020/admin/sys/manager";
    }

    public static String ManagerAdd() {
        return "/survey2020/admin/sys/manager/add";
    }

    public static String ManagerEdit(String id) {
        return "/survey2020/admin/sys/manager/" + id + "/edit";
    }

    public static String ManagerDelete(String id) {
        return "/survey2020/admin/sys/manager/" + id + "/delete";
    }

    public static String Menu() {
        return "/survey2020/admin/sys/menu";
    }

    public static String MenuAdd() {
        return "/survey2020/admin/sys/menu/add";
    }

    public static String MenuEdit(String id) {
        return "/survey2020/admin/sys/menu/" + id + "/edit";
    }

    public static String MenuDelete(String id) {
        return "/survey2020/admin/sys/menu/" + id + "/delete";
    }

    public static String MenuSort() {
        return "/survey2020/admin/sys/menu/sort";
    }

    public static String Role() {
        return "/survey2020/admin/sys/role";
    }

    public static String RoleAdd() {
        return "/survey2020/admin/sys/role/add";
    }

    public static String RoleEdit(String id) {
        return "/survey2020/admin/sys/role/" + id + "/edit";
    }

    public static String RoleDelete(String id) {
        return "/survey2020/admin/sys/role/" + id + "/delete";
    }

    public static String RoleMenu(String id) {
        return "/survey2020/admin/sys/role/" + id + "/menu";
    }

    public static String RoleMenuDelete(String id, String menu_id) {
        return "/survey2020/admin/sys/role/" + id + "/menu/" + menu_id + "/delete";
    }

    public static String Params() {
        return "/survey2020/admin/sys/params";
    }

    public static String ParamsAdd() {
        return "/survey2020/admin/sys/params/add";
    }

    public static String ParamsEdit(String id) {
        return "/survey2020/admin/sys/params/" + id + "/edit";
    }

    public static String ParamsDelete(String id) {
        return "/survey2020/admin/sys/params/" + id + "/delete";
    }

}