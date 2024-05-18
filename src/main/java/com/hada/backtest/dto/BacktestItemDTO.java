package com.hada.backtest.dto;

import com.hada.backtest.jpa.dto.PortfolioInputItemDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BacktestItemDTO {
    private String stock;
    private double weight;

    public BacktestItemDTO() {
    }

    public BacktestItemDTO(String stock, double weight) {
        this.stock = stock;
        this.weight = weight;
    }

    public static BacktestItemDTO fromPortfolioInoutItemDTO(PortfolioInputItemDTO dto) {
        return new BacktestItemDTO(dto.getStock(), dto.getWeight());
    }
}
