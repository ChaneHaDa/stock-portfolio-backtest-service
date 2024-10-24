package com.hada.backtest.jpa.dto;

import com.hada.backtest.jpa.entity.StockPrice;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockPriceDTO {
    private String stockCode;
    private Long stockPrice;
    private LocalDate date;
    private String stockName;

    public StockPriceDTO() {
    }

    public StockPriceDTO(String stockCode, Long stockPrice, LocalDate date, String stockName) {
        this.stockCode = stockCode;
        this.stockPrice = stockPrice;
        this.date = date;
        this.stockName = stockName;
    }

    public static StockPriceDTO fromEntity(StockPrice stockPrice) {
        return new StockPriceDTO(stockPrice.getCode(), stockPrice.getPrice(), stockPrice.getDate(),
                stockPrice.getName());
    }
}
