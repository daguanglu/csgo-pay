package com.yangguanglu.sptest;

import com.alibaba.fastjson.JSONObject;

import com.yangguanglu.sptest.model.igex.IgexRoot;
import com.yangguanglu.sptest.utile.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSGOTest {



    @Test
    public static void test01() {
        //String url = "https://www.c5game.com/gw/steamtrade/sga/sell/v1/list?itemId=808857281272217600&delivery=0&bargain=0&levelName=&slotStickerIds=&sort=0&paintSeed=&page=1&containSticker=&reqId=16515467626554269368";
        String url = "https://api.youpin898.com/api/homepage/es/commodity/GetCsGoPagedList";

        Map<String, Object> param = new HashMap<>();
        param.put("templateId","46298");
        param.put("pageSize","5");
        param.put("pageIndex","1");
        param.put("sortType","1");
        param.put("listType","10");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("templateId","46298");
        jsonObject1.put("pageSize","5");
        jsonObject1.put("pageIndex","1");
        jsonObject1.put("sortType","1");
        jsonObject1.put("listType","10");

        try {
            String send = send(url, jsonObject1, "UTF-8");
            System.out.println(send);
        } catch (IOException e) {
            e.printStackTrace();
        }



        String s = HttpUtils.postRequest(url, param);

        //System.out.println(s);

        JSONObject jsonObject = JSONObject.parseObject(s);
        String data = jsonObject.getString("Data");
        //System.out.println(data);
    }

    public static void test02() {
        String host = "";
        try {
            URL Url = new URL("https://www.igxe.cn/product/trade/730/658899");
            host = Url.getHost();
            System.out.println(host);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        test01();
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
        System.out.println("请求地址：" + url);
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
