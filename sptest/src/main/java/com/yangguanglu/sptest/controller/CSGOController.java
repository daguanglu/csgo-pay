package com.yangguanglu.sptest.controller;

import com.yangguanglu.sptest.config.CSGOConfig;
import com.yangguanglu.sptest.service.CSGOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CSGOController {

    @Autowired
    CSGOService csgoService;

    @Autowired
    CSGOConfig csgoConfig;

    @PostMapping(value = "/add")
    public String addUrl(@RequestParam("url") String url) {
        csgoConfig.test.add(url);
        log.info("添加监听链接:{}",url);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : csgoConfig.test) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    @PostMapping(value = "/del")
    public String delUrl(@RequestParam("url") String url) {
        csgoConfig.test.remove(url);
        log.info("删除监听链接:{}",url);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : csgoConfig.test) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    @GetMapping(value = "/query")
    public String query() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : csgoConfig.test) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
