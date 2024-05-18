package com.hada.backtest.jpa.dto;

import com.hada.backtest.jpa.entity.StockPrice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StockPriceDTO {
    private String code;
    private Long price;
    private LocalDate date;
    private String name;

    public StockPriceDTO() {
    }

    public StockPriceDTO(String code, Long price, LocalDate date, String name) {
        this.code = code;
        this.price = price;
        this.date = date;
        this.name = name;
    }

    public static StockPriceDTO fromEntity(StockPrice stockPrice) {
        return new StockPriceDTO(stockPrice.getCode(), stockPrice.getPrice(), stockPrice.getDate(), stockPrice.getName());
    }
}
