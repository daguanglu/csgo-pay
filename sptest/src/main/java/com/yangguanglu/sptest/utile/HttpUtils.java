package com.yangguanglu.sptest.utile;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
 
import javax.net.ssl.SSLContext;
 
/**
 * <p>类名: HttpUtils</p>
 * <p>描述: http请求工具类</p>
 * <p>修改时间: 2019年04月30日 上午10:12:35</p>
 *
 * @author lidongyang
 */
public class HttpUtils {
 
    public static String defaultEncoding = "utf-8";
 
    /**
     * 发送http post请求，并返回响应实体
     *
     * @param url 请求地址
     * @return url响应实体
     */
    public static String postRequest(String url) {
        return postRequest(url, null, null);
    }
 
    /**
     * <p>方法名: postRequest</p>
     * <p>描述: 发送httpPost请求</p>
     *
     * @param url
     * @param params
     * @return
     */
    public static String postRequest(String url, Map<String, Object> params) {
        return postRequest(url, null, params);
    }
 
    /**
     * 发送http post请求，并返回响应实体
     *
     * @param url     访问的url
     * @param headers 请求需要添加的请求头
     * @param params  请求参数
     * @return
     */
    public static String postRequest(String url, Map<String, String> headers,
                                     Map<String, Object> params) {
        String result = null;
        CloseableHttpClient httpClient = buildHttpClient();
        HttpPost httpPost = new HttpPost(url);
 
        if (null != headers && headers.size() > 0) {
            for (Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.addHeader(new BasicHeader(key, value));
            }
        }
        if (null != params && params.size() > 0) {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(defaultEncoding)));
        }
 
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity,
                            Charset.forName(defaultEncoding));
                }
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return result;
    }
 
    /**
     * 发送http get请求
     *
     * @param url 请求url
     * @return url返回内容
     */
    public static String getRequest(String url) {
        return getRequest(url, null);
    }
 
 
    /**
     * 发送http get请求
     *
     * @param url    请求的url
     * @param params 请求的参数
     * @return
     */
    public static String getRequest(String url, Map<String, Object> params) {
        return getRequest(url, null, params);
    }
 
    /**
     * 发送http get请求
     *
     * @param url        请求的url
     * @param headersMap 请求头
     * @param params     请求的参数
     * @return
     */
    public static String getRequest(String url, Map<String, String> headersMap, Map<String, Object> params) {
        String result = null;
        CloseableHttpClient httpClient = buildHttpClient();
        try {
            String apiUrl = url;
            if (null != params && params.size() > 0) {
                StringBuffer param = new StringBuffer();
                int i = 0;
                for (String key : params.keySet()) {
                    if (i == 0)
                        param.append("?");
                    else
                        param.append("&");
                    param.append(key).append("=").append(params.get(key));
                    i++;
                }
                apiUrl += param;
            }
 
            HttpGet httpGet = new HttpGet(apiUrl);

            if (null != headersMap && headersMap.size() > 0) {
                for (Entry<String, String> entry : headersMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpGet.addHeader(new BasicHeader(key, value));
                    httpGet.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");
                }
            }
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, defaultEncoding);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
 
    /**
     * 创建httpclient
     *
     * @return
     */
    public static CloseableHttpClient buildHttpClient() {
        try {
            RegistryBuilder<ConnectionSocketFactory> builder = RegistryBuilder
                    .create();
            ConnectionSocketFactory factory = new PlainConnectionSocketFactory();
            builder.register("http", factory);
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            SSLContext context = SSLContexts.custom().useTLS()
                    .loadTrustMaterial(trustStore, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            LayeredConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(
                    context,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            builder.register("https", sslFactory);
            Registry<ConnectionSocketFactory> registry = builder.build();
            PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(
                    registry);
            ConnectionConfig connConfig = ConnectionConfig.custom()
                    .setCharset(Charset.forName(defaultEncoding)).build();
            SocketConfig socketConfig = SocketConfig.custom()
                    .setSoTimeout(100000).build();
            manager.setDefaultConnectionConfig(connConfig);
            manager.setDefaultSocketConfig(socketConfig);
            return HttpClientBuilder.create().setConnectionManager(manager)
                    .build();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHost(String url) {
        String host = "";
        try {
            URL Url = new URL(url);
            host = Url.getHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return host;

    }

    public static String send(String url, JSONObject jsonObject, String encoding) throws ParseException, IOException {
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        //System.out.println("请求地址：" + url);
//        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;

    }
}