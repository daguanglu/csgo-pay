package com.yangguanglu.sptest.service;

import com.alibaba.fastjson.JSONObject;
import com.yangguanglu.sptest.model.buff.BuffRoot;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.model.c5.C5Root;
import com.yangguanglu.sptest.model.igex.IgexRoot;
import com.yangguanglu.sptest.model.youyou.YouRoot;
import com.yangguanglu.sptest.utile.platform.BuffUtile;
import com.yangguanglu.sptest.utile.HttpUtils;
import com.yangguanglu.sptest.utile.SendEmailUtile;
import com.yangguanglu.sptest.utile.platform.C5Utile;
import com.yangguanglu.sptest.utile.platform.IGEXUtile;
import com.yangguanglu.sptest.utile.platform.YouUtile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
public class CSGOService {

    @Value("${populate.list:}")
    private List<String> test;


    private static int count = 0;
    private static String email = "353633589@qq.com";
    private Set<CsgoDTO> csgoList = new HashSet<>();

    @PostConstruct
    public void init() {
        for (String s : test) {
            CsgoDTO csgoDTO = new CsgoDTO();
            String[] split = s.split("-");
            csgoDTO.setUrl(split[0]);
            csgoDTO.setPrice(split[1]);
            csgoDTO.setPaintwear(split[2]);
            csgoDTO.setName(split[3]);
            if(split.length > 4) {
                csgoDTO.setTemplateId(split[4]);
            }
            csgoList.add(csgoDTO);
        }
        log.info("=====================本次监听=======================");
        for (CsgoDTO dto : csgoList) {
            log.info(dto.toString());
        }
    }

    Set<String> set = new LinkedHashSet<>();

    @Scheduled(cron = "0/30 * * * * ?")
    public void monitor() {

        for (CsgoDTO csgoDTO : csgoList) {
            try {
                String host = HttpUtils.getHost(csgoDTO.getUrl());
                if("www.c5game.com".equals(host)) {
                    String s3 = C5Utile.jsonSwatch(csgoDTO);
                    List<C5Root> c5Roots = JSONObject.parseArray(s3, C5Root.class);
                    log.info("===========================c5平台============================");
                    for (C5Root c5Root : c5Roots) {
                        log.info("name:{} price:{} paintwear:{} count:{}",csgoDTO.getName(), c5Root.getCnyPrice(), c5Root.getAssetInfo().getWear(),count++);
                        if(Double.parseDouble(c5Root.getCnyPrice()) <= Double.parseDouble(csgoDTO.getPrice()) && Double.parseDouble(c5Root.getAssetInfo().getWear()) <= Double.parseDouble(csgoDTO.getPaintwear())) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String msg = stringBuilder.append("商品名称:").append(csgoDTO.getName()).append("\r\n")
                                    .append("价格:").append(c5Root.getCnyPrice()).append("\r\n")
                                    .append("磨损:").append(c5Root.getAssetInfo().getWear()).append("\r\n")
                                    //.append("商品url:").append("https://buff.163.com/goods/").append(buffRoot.getAsset_info().getGoods_id()).append("?from=market#tab=selling")
                                    .toString();

                            if(!set.isEmpty()) {
                                for (String s2 : set) {
                                    if (s2.equals(msg)) {
                                        log.info("邮件已发,请及时查看邮件:{}",msg);
                                        return;
                                    }
                                }
                            }
                            log.info("发送邮件:{}",email);
                            set.add(msg);
                            SendEmailUtile.send(email,"C5平台报价:{}",msg);
                        }
                    }
                    sleep();
                }else if("buff.163.com".equals(host)) {
                    String s3 = BuffUtile.jsonSwatch(csgoDTO);
                    List<BuffRoot> buffRoots = JSONObject.parseArray(s3, BuffRoot.class);
                    log.info("===========================buff平台============================");
                    for (BuffRoot buffRoot : buffRoots) {
                        log.info("name:{} price:{} paintwear:{} count:{}",csgoDTO.getName(), buffRoot.getPrice(), buffRoot.getAsset_info().getPaintwear(),count++);
                        if(Double.parseDouble(buffRoot.getPrice()) <= Double.parseDouble(csgoDTO.getPrice()) && Double.parseDouble(buffRoot.getAsset_info().getPaintwear()) <= Double.parseDouble(csgoDTO.getPaintwear())) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String msg = stringBuilder.append("商品名称:").append(csgoDTO.getName()).append("\r\n")
                                    .append("价格:").append(buffRoot.getPrice()).append("\r\n")
                                    .append("磨损:").append(buffRoot.getAsset_info().getPaintwear()).append("\r\n")
                                    .append("商品url:").append("https://buff.163.com/goods/").append(buffRoot.getAsset_info().getGoods_id()).append("?from=market#tab=selling")
                                    .toString();

                            if(!set.isEmpty()) {
                                for (String s2 : set) {
                                    if (s2.equals(msg)) {
                                        log.info("邮件已发,请及时查看邮件:{}",msg);
                                        return;
                                    }
                                }
                            }
                            log.info("发送邮件:{}",email);
                            set.add(msg);
                            SendEmailUtile.send(email,"网易BUFF报价",msg);
                        }
                    }
                    sleep();
                }
                else if("www.igxe.cn".equals(host)) {
                    List<IgexRoot> igexRoots = IGEXUtile.jsonSwatch(csgoDTO);
                    log.info("===========================IGXE平台============================");
                    for (IgexRoot igexRoot : igexRoots) {
                        log.info("name:{} price:{} paintwear:{} count:{}",csgoDTO.getName(), igexRoot.getUnit_price(), igexRoot.getExterior_wear(),count++);
                        if(Double.parseDouble(igexRoot.getUnit_price()) <= Double.parseDouble(csgoDTO.getPrice()) && Double.parseDouble(igexRoot.getExterior_wear()) <= Double.parseDouble(csgoDTO.getPaintwear())) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String msg = stringBuilder.append("商品名称:").append(csgoDTO.getName()).append("\r\n")
                                    .append("价格:").append(igexRoot.getUnit_price()).append("\r\n")
                                    .append("磨损:").append(igexRoot.getExterior_wear()).append("\r\n")
                                    //.append("商品url:").append("https://buff.163.com/goods/").append(buffRoot.getAsset_info().getGoods_id()).append("?from=market#tab=selling")
                                    .toString();

                            if(!set.isEmpty()) {
                                for (String s2 : set) {
                                    if (s2.equals(msg)) {
                                        log.info("邮件已发,请及时查看邮件:{}",msg);
                                        return;
                                    }
                                }
                            }
                            log.info("发送邮件:{}",email);
                            set.add(msg);
                            SendEmailUtile.send(email,"IGEX平台报价:{}",msg);
                        }

                    }
                    sleep();

                }else if("api.youpin898.com".equals(host)) {
                    List<YouRoot> youRoots = YouUtile.jsonSwatch(csgoDTO);
                    log.info("===========================youyou平台============================");
                    for (YouRoot youRoot : youRoots) {
                        //log.info("name:{} price:{} paintwear:{} count:{}",csgoDTO.getName(), youRoot.getPrice(), youRoot.getAbrade(),count++);
                        if(Double.parseDouble(youRoot.getPrice()) <= Double.parseDouble(csgoDTO.getPrice()) && Double.parseDouble(youRoot.getAbrade()) <= Double.parseDouble(csgoDTO.getPaintwear())) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String msg = stringBuilder.append("商品名称:").append(csgoDTO.getName()).append("\r\n")
                                    .append("价格:").append(youRoot.getPrice()).append("\r\n")
                                    .append("磨损:").append(youRoot.getAbrade()).append("\r\n")
                                    //.append("商品url:").append("https://buff.163.com/goods/").append(buffRoot.getAsset_info().getGoods_id()).append("?from=market#tab=selling")
                                    .toString();

                            if(!set.isEmpty()) {
                                for (String s2 : set) {
                                    if (s2.equals(msg)) {
                                        log.info("邮件已发,请及时查看邮件:{}",msg);
                                        return;
                                    }
                                }
                            }
                            log.info("发送邮件:{}",email);
                            set.add(msg);
                            SendEmailUtile.send(email,"悠悠有品报价",msg);
                        }
                    }
                    sleep();
                }else {
                    log.info("域名匹配异常，请检查：{}",csgoDTO.getUrl());
                }
                //增加判断调用什么utils

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
