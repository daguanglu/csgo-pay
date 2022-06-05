package com.yangguanglu.sptest.service.platform;

import com.alibaba.fastjson.JSONObject;
import com.yangguanglu.sptest.config.CSGOConfig;
import com.yangguanglu.sptest.model.buff.BuffRoot;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.service.PayService;
import com.yangguanglu.sptest.utile.SendEmailUtile;
import com.yangguanglu.sptest.utile.platform.BuffUtile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BuffServiceImpl implements PayService {

    @Override
    public void run(CsgoDTO csgoDTO) {
        String s3 = BuffUtile.jsonSwatch(csgoDTO);
        List<BuffRoot> buffRoots = JSONObject.parseArray(s3, BuffRoot.class);
        log.info("===========================buff平台============================");
        for (BuffRoot buffRoot : buffRoots) {
            log.info("name:{} price:{} paintwear:{} count:{}",csgoDTO.getName(), buffRoot.getPrice(), buffRoot.getAsset_info().getPaintwear(),CSGOConfig.count++);
            if(Double.parseDouble(buffRoot.getPrice()) <= Double.parseDouble(csgoDTO.getPrice()) && Double.parseDouble(buffRoot.getAsset_info().getPaintwear()) <= Double.parseDouble(csgoDTO.getPaintwear())) {
                StringBuilder stringBuilder = new StringBuilder();
                String msg = stringBuilder.append("商品名称:").append(csgoDTO.getName()).append("\r\n")
                        .append("价格:").append(buffRoot.getPrice()).append("\r\n")
                        .append("磨损:").append(buffRoot.getAsset_info().getPaintwear()).append("\r\n")
                        .append("商品url:").append("https://buff.163.com/goods/").append(buffRoot.getAsset_info().getGoods_id()).append("?from=market#tab=selling")
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
                SendEmailUtile.send(CSGOConfig.email,"网易BUFF报价",msg);
            }
        }
    }
}
