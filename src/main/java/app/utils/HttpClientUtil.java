package app.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.*;

public class HttpClientUtil {
    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    private static HttpClientUtil instance = null;
    private HttpClientUtil(){}
    public static HttpClientUtil getInstance(){
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        return instance;
    }

    public String sendHttpGet(String httpUrl) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        return sendHttpGet(httpUrl, params, headers);
    }

    public String sendHttpGet(String httpUrl, Map<String, String> params) {
        Map<String, String> headers = new HashMap<>();
        return sendHttpGet(httpUrl, params, headers);
    }

    public String sendHttpGet(String httpUrl, Map<String, String> params, Map<String, String> headers) {
        String paramsStr = "";
        for(String key : params.keySet()){
            paramsStr = paramsStr + "&" + key + "=" +params.get(key);
        }
        if(paramsStr.length() > 0){
            httpUrl = httpUrl + "?" + paramsStr.substring(1);
        }

        // 创建httpPost
        HttpGet httpGet = new HttpGet(httpUrl);

        // headers
        Set<String> keySet = headers.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        for(int i = 0; i < keyArray.length; ++i) {
            String k = keyArray[i];
            if ((headers.get(k)).trim().length() > 0 && i != keyArray.length -1) {
                httpGet.addHeader(k, headers.get(k).trim());
            }
        }
        return httpGet(httpGet);
    }

    public String sendHttpPost(String httpUrl) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        return sendHttpPost(httpUrl, params, headers);
    }

    public String sendHttpPost(String httpUrl, Map<String, String> params) {
        Map<String, String> headers = new HashMap<>();
        return sendHttpPost(httpUrl, params, headers);
    }

    public String sendHttpPost(String httpUrl, Map<String, String> params, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(httpUrl);

        // headers
        Set<String> keySet = headers.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        for(int i = 0; i < keyArray.length; ++i) {
            String k = keyArray[i];
            if ((headers.get(k)).trim().length() > 0 && i != keyArray.length -1) {
                httpPost.addHeader(k, headers.get(k).trim());
            }
        }

        //params
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : params.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpPost(httpPost);
    }

    public String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);
        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        httpPost.setEntity(stringEntity);
        return httpPost(httpPost);
    }

    public String sendHttpPostJson(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);

        try {
            httpPost.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
            httpPost.setHeader("Accept", "application/json");
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpPost(httpPost);
    }

    public String sendHttpPostJson(String httpUrl, String params, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(httpUrl);

        // headers
        Set<String> keySet = headers.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        for(int i = 0; i < keyArray.length; ++i) {
            String k = keyArray[i];
            if ((headers.get(k)).trim().length() > 0 && i != keyArray.length -1) {
                httpPost.addHeader(k, headers.get(k).trim());
            }
        }

        try {
            httpPost.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
            httpPost.setHeader("Accept", "application/json");
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpPost(httpPost);
    }

    public String sendHttpPostXml(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);

        try {
            httpPost.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
            httpPost.setHeader("Accept", "application/soap+xml; charset=utf-8");
            httpPost.addHeader("Content-type","application/soap+xml; charset=utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpPost(httpPost);
    }

    //ssl
    public String sendHttpsPost(String httpUrl, String params, String sslPath, String sslPass) {
        HttpPost httpPost = new HttpPost(httpUrl);
        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        httpPost.setEntity(stringEntity);
        return httpsPost(httpPost, sslPath, sslPass);
    }

    private String httpGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnetcion(httpClient, response);
        }
        return responseContent;
    }

    private String httpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnetcion(httpClient, response);
        }
        return responseContent;
    }

    private String httpsPost(HttpPost httpPost, String sslPath, String sslPwd) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(sslPath));
            keyStore.load(instream, sslPwd.toCharArray());

            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, sslPwd.toCharArray()).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();

            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnetcion(httpClient, response);
        }
        return responseContent;
    }

    // 关闭连接,释放资源
    private void closeConnetcion(CloseableHttpClient httpClient, CloseableHttpResponse response){
        try {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

