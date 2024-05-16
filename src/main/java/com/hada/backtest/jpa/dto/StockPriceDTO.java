package com.hada.backtest.jpa.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StockPriceDTO {
    private String code;
    private Long price;
    private LocalDate date;

}
