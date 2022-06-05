package com.yangguanglu.sptest.service.impl;

import com.yangguanglu.sptest.config.CSGOConfig;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.service.CSGOService;
import com.yangguanglu.sptest.service.PayService;
import com.yangguanglu.sptest.utile.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CSGOServiceImpl implements CSGOService {

    @Scheduled(cron = "0/30 * * * * ?")
    public void monitor() {

        for (CsgoDTO csgoDTO : CSGOConfig.csgoList) {
            try {
                String host = HttpUtils.getHost(csgoDTO.getUrl());
                PayService payService = CSGOConfig.service.get(host);
                payService.run(csgoDTO);
                sleep();
            } catch (Exception e) {
                log.error("JSON解析异常");
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
