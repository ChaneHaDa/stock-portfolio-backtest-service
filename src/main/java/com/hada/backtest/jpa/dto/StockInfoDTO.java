package com.hada.backtest.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockInfoDTO {
    private String name;
    private String code;

    public StockInfoDTO() {
    }

    public StockInfoDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
