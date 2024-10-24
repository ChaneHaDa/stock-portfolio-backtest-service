package com.hada.backtest.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockInfoDTO {

    private String stockName;
    private String stockCode;

    public StockInfoDTO() {
    }

    public StockInfoDTO(String stockName, String stockCode) {
        this.stockName = stockName;
        this.stockCode = stockCode;
    }
}
