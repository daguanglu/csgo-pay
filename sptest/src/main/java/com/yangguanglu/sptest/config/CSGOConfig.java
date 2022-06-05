package com.yangguanglu.sptest.config;

import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.service.impl.CSGOServiceImpl;
import com.yangguanglu.sptest.service.PayService;
import com.yangguanglu.sptest.service.platform.BuffServiceImpl;
import com.yangguanglu.sptest.service.platform.C5ServiceImpl;
import com.yangguanglu.sptest.service.platform.IgexServiceImpl;
import com.yangguanglu.sptest.service.platform.YouyouServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
@Slf4j
public class CSGOConfig {

    public static CSGOServiceImpl csgoService;
    public static Set<String> set;

    @Value("${populate.list:}")
    public List<String> test;


    public static int count = 0;
    public static String email = "353633589@qq.com";
    public static Set<CsgoDTO> csgoList = new HashSet<>();

    public static Map<String, PayService> service = new HashMap<>();

    @PostConstruct
    private void init() {
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

        //添加平台到map
        service.put("www.c5game.com", new C5ServiceImpl());
        service.put("buff.163.com", new BuffServiceImpl());
        service.put("www.igxe.cn", new IgexServiceImpl());
        service.put("api.youpin898.com", new YouyouServiceImpl());

        for (String s : service.keySet()) {
            log.info("添加service:{}",s);
        }
        csgoService = new CSGOServiceImpl();
        set = new LinkedHashSet<>();
    }
}
