package com.yangguanglu.sptest.service.platform;

import com.alibaba.fastjson.JSONObject;
import com.yangguanglu.sptest.config.CSGOConfig;
import com.yangguanglu.sptest.model.buff.CsgoDTO;
import com.yangguanglu.sptest.model.c5.C5Root;
import com.yangguanglu.sptest.service.PayService;
import com.yangguanglu.sptest.utile.SendEmailUtile;
import com.yangguanglu.sptest.utile.platform.C5Utile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class C5ServiceImpl implements PayService {

    @Override
    public void run(CsgoDTO csgoDTO) {
        String s3 = C5Utile.jsonSwatch(csgoDTO);
        List<C5Root> c5Roots = JSONObject.parseArray(s3, C5Root.class);
        log.info("===========================c5平台============================");
        for (C5Root c5Root : c5Roots) {
            log.info("name:{} price:{} paintwear:{} count:{}", csgoDTO.getName(), c5Root.getCnyPrice(), c5Root.getAssetInfo().getWear(), CSGOConfig.count++);
            if (Double.parseDouble(c5Root.getCnyPrice()) <= Double.parseDouble(csgoDTO.getPrice()) && Double.parseDouble(c5Root.getAssetInfo().getWear()) <= Double.parseDouble(csgoDTO.getPaintwear())) {
                StringBuilder stringBuilder = new StringBuilder();
                String msg = stringBuilder.append("商品名称:").append(csgoDTO.getName()).append("\r\n")
                        .append("价格:").append(c5Root.getCnyPrice()).append("\r\n")
                        .append("磨损:").append(c5Root.getAssetInfo().getWear()).append("\r\n")
                        //.append("商品url:").append("https://buff.163.com/goods/").append(buffRoot.getAsset_info().getGoods_id()).append("?from=market#tab=selling")
                        .toString();

                if (!CSGOConfig.set.isEmpty()) {
                    for (String s2 : CSGOConfig.set) {
                        if (s2.equals(msg)) {
                            log.info("邮件已发,请及时查看邮件:{}", msg);
                            return;
                        }
                    }
                }
                log.info("发送邮件:{}", CSGOConfig.email);
                CSGOConfig.set.add(msg);
                SendEmailUtile.send(CSGOConfig.email, "C5平台报价:{}", msg);
            }
        }
    }
}
