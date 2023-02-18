package org.heigit.ors.service.customize;

import org.heigit.ors.api.responses.customize.FeatureCollectionVO;
import org.springframework.stereotype.Service;


@Service
public interface CustomizeService {
    FeatureCollectionVO getResult(String profile,String apiKey, String start, String end);
}
