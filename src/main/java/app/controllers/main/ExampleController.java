package app.controllers.main;

import app.utils.OfficeUtil;
import app.utils.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExampleController {

    public static Route Xlsx = (Request req, Response res) ->{
        res.header("Content-Type", "application/vnd.ms-excel;charset=gb2312");

        try {
            String json = StrUtil.toStr(req.queryParams("json"));
            JSONObject jsonNode = JSON.parseObject(json);

            JSONArray headArray = jsonNode.getJSONArray("head");
            List<String> headList = new ArrayList<>();
            for(int i = 0; i < headArray.size(); i++){
                headList.add(headArray.getString(i));
            }
            String[] header = headList.toArray(new String[0]);
            String[] style = new String[header.length];

            JSONArray bodyArray = (JSONArray)jsonNode.get("body");
            List<String[]> lists = new ArrayList();
            for(int i = 0; i < bodyArray.size(); i++){
                JSONArray rowArray = bodyArray.getJSONArray(i);
                List<String> colList = new ArrayList<>();
                for(int j = 0; j < rowArray.size(); j++){
                    colList.add(rowArray.getString(j));
                }
                lists.add(colList.toArray(new String[0]));
            }

            String filename = jsonNode.getString("title");
            File file = OfficeUtil.createXlsx(header, style, lists, filename);
            filename = filename + ".xlsx";
            String path = file.getAbsolutePath();

            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            res.raw().reset();
            res.raw().addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            res.raw().addHeader("Content-Length", "" + file.length());

            OutputStream out = new BufferedOutputStream(res.raw().getOutputStream());
            out.write(buffer);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    };
}
