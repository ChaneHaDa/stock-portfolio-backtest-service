package com.hada.backtest.jpa.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioStockDTO {
    
    private String name;
    private String code;
    private Map<String, Long> priceMap;
    private double weight;

    public PortfolioStockDTO() {
    }

    public PortfolioStockDTO(String name, String code, Map<String, Long> priceMap, double weight) {
        this.name = name;
        this.code = code;
        this.priceMap = priceMap;
        this.weight = weight;
    }
}
