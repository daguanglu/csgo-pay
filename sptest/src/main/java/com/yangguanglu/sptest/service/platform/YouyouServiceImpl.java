package com.yangguanglu.sptest.service.platform;

import com.yangguanglu.sptest.config.CSGOConfig;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.model.youyou.YouRoot;
import com.yangguanglu.sptest.service.PayService;
import com.yangguanglu.sptest.utile.SendEmailUtile;
import com.yangguanglu.sptest.utile.platform.YouUtile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class YouyouServiceImpl implements PayService {

    @Override
    public void run(CsgoDTO csgoDTO) {
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

                if(!CSGOConfig.set.isEmpty()) {
                    for (String s2 : CSGOConfig.set) {
                        if (s2.equals(msg)) {
                            log.info("邮件已发,请及时查看邮件:{}",msg);
                            return;
                        }
                    }
                }
                log.info("发送邮件:{}",CSGOConfig.email);
                CSGOConfig.set.add(msg);
                SendEmailUtile.send(CSGOConfig.email,"悠悠有品报价",msg);
            }
        }
    }
}
