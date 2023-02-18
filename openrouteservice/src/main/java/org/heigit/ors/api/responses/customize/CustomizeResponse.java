package org.heigit.ors.api.responses.customize;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomizeResponse {
    private Double distance;
    private BigDecimal duration;
    private BigDecimal spentAmount;


}
