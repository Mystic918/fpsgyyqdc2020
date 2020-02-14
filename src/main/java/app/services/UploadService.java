package app.services;

import app.models.TmpUpload;
import com.alibaba.fastjson.JSONObject;
import conf.AppConf;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UploadService {

    public static JSONObject uploadPic(Request req){
        JSONObject result = new JSONObject();

        if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement mce = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", mce);
        }

        try {
            Part part = req.raw().getPart("file");
            if(part == null){
                result.put("code", 500);
                return result;
            }

            ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
            String configType = config.getString("upload.image.type");
            long configSize = Long.parseLong(config.getString("upload.image.maxsize"));

            String fileName = part.getSubmittedFileName();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String fileType = part.getContentType();
            long fileSize = part.getSize();

            if(!configType.contains(fileType)){
                result.put("code", 400);
                result.put("msg", "文件格式不正确");
                return result;
            }
            if(fileSize > configSize * 1024){
                result.put("code", 400);
                result.put("msg", "文件不能大于" + configSize + "k");
                return result;
            }

            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("name", fileName);
            uploadParams.put("suffix", fileSuffix);
            uploadParams.put("type", fileType);
            uploadParams.put("size", fileSize);

            String fileUrl = UploadService.upload(uploadParams, part.getInputStream());
            if(fileUrl == null){
                result.put("code", 500);
            }else{
                result.put("code", 200);
                result.put("data", fileUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    }

    public static JSONObject uploadFile(Request req){
        JSONObject result = new JSONObject();

        if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement mce = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", mce);
        }

        try {
            Part part = req.raw().getPart("file");
            if(part == null){
                result.put("code", 500);
                return result;
            }

            ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
            long configSize = Long.parseLong(config.getString("upload.file.maxsize"));

            String fileName = part.getSubmittedFileName();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String fileType = part.getContentType();
            long fileSize = part.getSize();

            if(fileSize > configSize * 1024){
                result.put("code", 400);
                result.put("msg", "文件不能大于" + configSize + "k");
                return result;
            }

            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("name", fileName);
            uploadParams.put("suffix", fileSuffix);
            uploadParams.put("type", fileType);
            uploadParams.put("size", fileSize);

            String fileUrl = UploadService.upload(uploadParams, part.getInputStream());
            if(fileUrl == null){
                result.put("code", 500);
            }else{
                result.put("code", 200);
                result.put("data", fileUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    }

    public static JSONObject uploadAudio(Request req){
        JSONObject result = new JSONObject();

        if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement mce = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", mce);
        }

        try {
            Part part = req.raw().getPart("file");
            if(part == null){
                result.put("code", 500);
                return result;
            }

            ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
            long configSize = Long.parseLong(config.getString("upload.audio.maxsize"));

            String fileName = part.getSubmittedFileName();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String fileType = part.getContentType();
            long fileSize = part.getSize();

            if(fileType.indexOf("audio/") != 0){
                result.put("code", 400);
                result.put("msg", "文件格式不正确");
                return result;
            }
            if(fileSize > configSize * 1024){
                result.put("code", 400);
                result.put("msg", "文件不能大于" + configSize + "k");
                return result;
            }

            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("name", fileName);
            uploadParams.put("suffix", fileSuffix);
            uploadParams.put("type", fileType);
            uploadParams.put("size", fileSize);

            String fileUrl = UploadService.upload(uploadParams, part.getInputStream());
            if(fileUrl == null){
                result.put("code", 500);
            }else{
                result.put("code", 200);
                result.put("data", fileUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    }

    public static JSONObject uploadVideo(Request req){
        JSONObject result = new JSONObject();

        if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement mce = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", mce);
        }

        try {
            Part part = req.raw().getPart("file");
            if(part == null){
                result.put("code", 500);
                return result;
            }

            ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
            long configSize = Long.parseLong(config.getString("upload.video.maxsize"));

            String fileName = part.getSubmittedFileName();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String fileType = part.getContentType();
            long fileSize = part.getSize();

            if(fileType.indexOf("video/") != 0){
                result.put("code", 400);
                result.put("msg", "文件格式不正确");
                return result;
            }
            if(fileSize > configSize * 1024){
                result.put("code", 400);
                result.put("msg", "文件不能大于" + configSize + "k");
                return result;
            }

            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("name", fileName);
            uploadParams.put("suffix", fileSuffix);
            uploadParams.put("type", fileType);
            uploadParams.put("size", fileSize);

            String fileUrl = UploadService.upload(uploadParams, part.getInputStream());
            if(fileUrl == null){
                result.put("code", 500);
            }else{
                result.put("code", 200);
                result.put("data", fileUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }
        return result;
    }

    public static JSONObject uploadCkeditor(Request req){
        JSONObject result = new JSONObject();

        if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement mce = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", mce);
        }

        try {
            Part part = req.raw().getPart("upload");
            if(part == null){
                result.put("uploaded", false);
                result.put("msg", "系统错误");
                return result;
            }

            ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
            String configType = config.getString("upload.image.type");
            int configSize = Integer.parseInt(config.getString("upload.image.maxsize"));

            String fileName = part.getSubmittedFileName();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
            String fileType = part.getContentType();
            long fileSize = part.getSize();

            if(!configType.contains(fileType)){
                result.put("uploaded", false);
                result.put("msg", "文件格式不正确");
                return result;
            }
            if(fileSize > configSize * 1024){
                result.put("uploaded", false);
                result.put("msg", "文件不能大于" + configSize + "k");
                return result;
            }

            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("name", fileName);
            uploadParams.put("suffix", fileSuffix);
            uploadParams.put("type", fileType);
            uploadParams.put("size", fileSize);

            String fileUrl = UploadService.upload(uploadParams, part.getInputStream());
            if(fileUrl == null){
                result.put("uploaded", false);
            }else{
                result.put("uploaded", true);
                result.put("url", fileUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    public static JSONObject downloadPic(Map<String, Object> uploadParams, String url){
        JSONObject result = new JSONObject();

        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();

                String fileUrl = UploadService.upload(uploadParams, inputStream);
                if(fileUrl == null){
                    result.put("code", 500);
                }else{
                    result.put("code", 200);
                    result.put("data", fileUrl);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 500);
        }

        return result;
    }

    public static String getSysRoot(){
        ResourceBundle config = ResourceBundle.getBundle(AppConf.ENV);
        String sysRoot = config.getString("upload.root");
        if(sysRoot.equals("")){
            sysRoot = UploadService.class.getResource("/public").getPath();
        }
        if (sysRoot.substring(sysRoot.length() - 1).equals("/")) {
            sysRoot = sysRoot.substring(0, sysRoot.length() - 1);
        }
        if (sysRoot.contains(":")) {
            sysRoot = sysRoot.substring(1);
        }
        return sysRoot;
    }

    public static String upload(Map<String, Object> map, InputStream inputStream) {
        try {
            SimpleDateFormat nameFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

            String sysRoot = getSysRoot();

            String fileName = String.valueOf(map.get("name"));
            String fileSuffix = String.valueOf(map.get("suffix"));
            String fileType = String.valueOf(map.get("type"));
            String fileSize = String.valueOf(map.get("size"));

            String fileFinalName = nameFormat.format(new Date()) + "." + fileSuffix;

            String webPath = "/upload";
            String appPath = webPath + "/" + yearFormat.format(new Date());
            String sysPath = sysRoot + appPath;
            File file = new File(sysPath);

            file.mkdirs();

            String appFullFile = appPath + "/" + fileFinalName;
            String sysFullFile = sysPath + "/" + fileFinalName;

            TmpUpload upload= new TmpUpload();
            upload.setPath(appFullFile);
            upload.setName(fileName);
            upload.setSuffix(fileSuffix);
            upload.setType(fileType);
            upload.setLink(appFullFile);
            upload.setSize(fileSize);
            upload.save();

            Path out = Paths.get(sysFullFile);
            Files.copy(inputStream, out);

            return appFullFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}