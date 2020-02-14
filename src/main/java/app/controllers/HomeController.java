package app.controllers;

import app.utils.StrUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.URLEncoder;

public class HomeController {

    public static Route Index = (Request req, Response res) -> {
        return "欢迎访问猪猪养车!";
    };

    public static Route EntryUser = (Request req, Response res) -> {
        String referee = StrUtil.toStr(req.params("referee"));
        String openid = StrUtil.toStr(req.queryParams("openid"));
        String nickname = StrUtil.toStr(req.queryParams("nickname"));
        String headimgurl = StrUtil.toStr(req.queryParams("headimgurl"));

        if(openid.length() != 28){
            res.redirect("/tp/wechat/auth_user?return_url=" + req.url());
        }else{
            nickname = URLEncoder.encode(nickname, "utf-8");
            String url = "https://www.pigrepair.com/m/#/entry/wechat?openid=%s&nickname=%s&headimgurl=%s&referee=%s&type=%s";
            url = String.format(url, openid, nickname, headimgurl, referee, "user");
            res.redirect(url);
        }
        return null;
    };

    public static Route EntryMerchant = (Request req, Response res) -> {
        String referee = StrUtil.toStr(req.params("referee"));
        String openid = StrUtil.toStr(req.queryParams("openid"));
        String nickname = StrUtil.toStr(req.queryParams("nickname"));
        String headimgurl = StrUtil.toStr(req.queryParams("headimgurl"));

        if(openid.length() != 28){
            res.redirect("/tp/wechat/auth_user?return_url=" + req.url());
        }else{
            nickname = URLEncoder.encode(nickname, "utf-8");
            String url = "https://www.pigrepair.com/m/#/entry/wechat?openid=%s&nickname=%s&headimgurl=%s&referee=%s&type=%s";
            url = String.format(url, openid, nickname, headimgurl, referee, "merchant");
            res.redirect(url);
        }
        return null;
    };

    public static Route EntryReturn = (Request req, Response res) -> {
        String type = StrUtil.toStr(req.queryParams("path"));

        if(type.equals("order_keep")){
            String url = "https://www.pigrepair.com/m/#/user/order/keep";
            res.redirect(url);
        }

        if(type.equals("order_coupon")){
            String url = "https://www.pigrepair.com/m/#/user/order/card";
            res.redirect(url);
        }

        return null;
    };
}