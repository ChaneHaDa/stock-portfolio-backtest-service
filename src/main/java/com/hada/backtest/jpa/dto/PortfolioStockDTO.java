package com.hada.backtest.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PortfolioStockDTO {
    private String name;
    private String code;
    private Map<String, Long> priceMap;
    private double weight;
}
