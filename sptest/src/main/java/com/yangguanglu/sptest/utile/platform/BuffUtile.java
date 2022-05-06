package com.yangguanglu.sptest.utile.platform;

import com.alibaba.fastjson.JSONObject;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.utile.HttpUtils;

public class BuffUtile {
    private static final String AAAAA = "&_=";

    public static String jsonSwatch(CsgoDTO csgoDTO) {
        String getUrl = csgoDTO.getUrl() + AAAAA + System.currentTimeMillis();
        String s = HttpUtils.getRequest(getUrl);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String data = jsonObject.getString("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data);
        String items = jsonObject1.getString("items");


        return items;

    }
}
