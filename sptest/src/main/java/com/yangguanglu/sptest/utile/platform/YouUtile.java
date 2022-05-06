package com.yangguanglu.sptest.utile.platform;

import com.alibaba.fastjson.JSONObject;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.model.igex.IgexRoot;
import com.yangguanglu.sptest.model.youyou.YouRoot;
import com.yangguanglu.sptest.utile.HttpUtils;

import java.io.IOException;
import java.util.List;

public class YouUtile {

    public static List<YouRoot> jsonSwatch(CsgoDTO csgoDTO) {

        String url = csgoDTO.getUrl();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("templateId", csgoDTO.getTemplateId());
        jsonObject.put("pageSize", "200");
        jsonObject.put("pageIndex", "1");
        jsonObject.put("sortType", "1");
        jsonObject.put("listType", "30");
        jsonObject.put("listSortType", "1");
        try {
            String send = HttpUtils.send(url, jsonObject, "UTF-8");
            JSONObject jsonObject2 = JSONObject.parseObject(send);
            String data = jsonObject2.getString("Data");
            JSONObject jsonObject3 = JSONObject.parseObject(data);
            String commodityList = jsonObject3.getString("CommodityList");
            List<YouRoot> youRoots = JSONObject.parseArray(commodityList, YouRoot.class);
            return youRoots;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
