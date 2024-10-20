package com.hada.backtest.jpa.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioStockDTO {

    private String stockName;
    private String stockCode;
    private Map<String, Long> priceMap;
    private double weight;

    public PortfolioStockDTO() {
    }

    public PortfolioStockDTO(String stockName, String stockCode, Map<String, Long> priceMap, double weight) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.priceMap = priceMap;
        this.weight = weight;
    }
}
