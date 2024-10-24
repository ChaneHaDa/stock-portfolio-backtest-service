package com.hada.backtest.dto;

import com.hada.backtest.jpa.dto.PortfolioInputItemDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BacktestItemDTO {

    private String stockName;
    private double weight;

    public BacktestItemDTO() {
    }

    public BacktestItemDTO(String stockName, double weight) {
        this.stockName = stockName;
        this.weight = weight;
    }

    public static BacktestItemDTO fromPortfolioInoutItemDTO(PortfolioInputItemDTO dto) {
        return new BacktestItemDTO(dto.getStockName(), dto.getWeight());
    }
}
