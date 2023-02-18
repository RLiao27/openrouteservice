package org.heigit.ors.api.controllers;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.heigit.ors.api.requests.common.APIEnums;
import org.heigit.ors.api.requests.customize.CustomizeRequest;
import org.heigit.ors.api.responses.centrality.json.JsonCentralityResponse;
import org.heigit.ors.api.responses.customize.CustomizeResponse;
import org.heigit.ors.api.responses.customize.FeatureCollectionVO;
import org.heigit.ors.centrality.CentralityResult;
import org.heigit.ors.exceptions.StatusCodeException;
import org.heigit.ors.service.customize.CustomizeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Api(value = "Customize Service", tags = "Customize")
@RequestMapping("/v2/customize")
@Slf4j
public class CustomizeAPI {
    @Resource
    CustomizeService customizeService;

    @PostMapping(value = "/{profile}/income/list/json", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<CustomizeResponse> incomeList(@ApiParam(value = "Specifies the profile.", required = true, example = "driving-car") @PathVariable APIEnums.Profile profile, @RequestBody CustomizeRequest request) throws StatusCodeException {
        List<List<Double>> bbox = request.getBbox();
        List<Double> doubles = bbox.get(0);
        List<Double> doubles1 = bbox.get(1);
        StringBuilder sb1 = new StringBuilder();
        sb1.append(doubles.get(0));
        sb1.append("," + doubles.get(1));

        StringBuilder sb2 = new StringBuilder();
        sb2.append(doubles1.get(0));
        sb2.append("," + doubles1.get(1));

        FeatureCollectionVO featureCollectionVO = customizeService.getResult(profile + "", "5b3ce3597851110001cf6248893c4fc898ea4ac1ab3d213f7b675f7a", sb1.toString(), sb2.toString());
        Double distance = featureCollectionVO.getFeatures().get(0).getProperties().getSegments().get(0).getDistance();
        Double duration = featureCollectionVO.getFeatures().get(0).getProperties().getSegments().get(0).getDuration();
        CustomizeResponse customizeResponse = new CustomizeResponse();
        BigDecimal spendAmount = new BigDecimal(0);
        BigDecimal finallyDuration = NumberUtil.round(new BigDecimal(duration), 2);

        if (request.getChoiceTollWays()) {
            spendAmount = NumberUtil.round(NumberUtil.mul(distance, new Double(0.05)), 2);
            finallyDuration = NumberUtil.round(NumberUtil.mul(duration, new Double(0.75)), 2);
        }
        customizeResponse.setDuration(finallyDuration);
        customizeResponse.setDistance(distance);
        customizeResponse.setSpentAmount(spendAmount);

        //

        return new ResponseEntity(customizeResponse, HttpStatus.OK);
    }

}
