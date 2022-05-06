package com.yangguanglu.sptest.utile.platform;

import com.alibaba.fastjson.JSONObject;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.model.igex.IgexRoot;
import com.yangguanglu.sptest.utile.HttpUtils;

import java.util.List;

public class IGEXUtile {

    public static List<IgexRoot> jsonSwatch(CsgoDTO csgoDTO) {

        String s = HttpUtils.getRequest(csgoDTO.getUrl());

        JSONObject jsonObject = JSONObject.parseObject(s);
        String d_list = jsonObject.getString("d_list");
        List<IgexRoot> igexRoots = JSONObject.parseArray(d_list, IgexRoot.class);


        return igexRoots;
    }

}
