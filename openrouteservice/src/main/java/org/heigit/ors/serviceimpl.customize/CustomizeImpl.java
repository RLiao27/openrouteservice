package org.heigit.ors.serviceImpl.customize;

import com.alibaba.fastjson.JSON;
import org.heigit.ors.api.responses.customize.FeatureCollectionVO;
import org.heigit.ors.rpc.HttpClientUtils;
import org.heigit.ors.service.customize.CustomizeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomizeImpl implements CustomizeService {


    @Override
    public FeatureCollectionVO getResult(String profile, String apiKey, String start, String end) {
        Map paramMap = new HashMap();
        paramMap.put("api_key", apiKey);
        paramMap.put("start", start);
        paramMap.put("end", end);
        String result = HttpClientUtils.doGet("https://api.openrouteservice.org/v2/directions/" + profile, paramMap);
        FeatureCollectionVO featureCollectionVO = JSON.parseObject(result, FeatureCollectionVO.class);
        return featureCollectionVO;
    }
}
