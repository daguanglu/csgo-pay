package com.yangguanglu.sptest.utile.platform;

import com.alibaba.fastjson.JSONObject;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.utile.HttpUtils;

public class C5Utile {

    public static String jsonSwatch(CsgoDTO csgoDTO) {
        String getUrl = csgoDTO.getUrl();
        String s = HttpUtils.getRequest(getUrl);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String data = jsonObject.getString("data");
        JSONObject jsonObject2 = JSONObject.parseObject(data);
        String data2 = jsonObject2.getString("list");


        return data2;

    }
}
