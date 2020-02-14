package conf;

public class PathAdmin {

    // 企业管理
    public static String Enterprise() {
        return "/survey2020/admin/enterprise";
    }
    public static String EnterpriseAdd() {
        return "/survey2020/admin/enterprise/add";
    }
    public static String EnterpriseEdit(String id) {
        return "/survey2020/admin/enterprise/" + id + "/edit";
    }
    public static String EnterpriseDelete(String id) {
        return "/survey2020/admin/enterprise/" + id + "/delete";
    }
    public static String EnterpriseAjax() {
        return "/survey2020/json/admin/enterprise";
    }
    public static String EnterpriseDetail(String id) {
        return "/survey2020/admin/enterprise/" + id + "/detail";
    }
    public static String EnterpriseExport() {
        return "/survey2020/admin/enterprise/export";
    }

    // 员工管理
    public static String Person() {
        return "/survey2020/admin/person";
    }
    public static String PersonAdd() {
        return "/survey2020/admin/person/add";
    }
    public static String PersonEdit(String id) {
        return "/survey2020/admin/person/" + id + "/edit";
    }
    public static String PersonDelete(String id) {
        return "/survey2020/admin/person/" + id + "/delete";
    }
    public static String PersonDetail(String id) {
        return "/survey2020/admin/person/" + id + "/detail";
    }
    public static String PersonExport() {
        return "/survey2020/admin/person/export";
    }
}