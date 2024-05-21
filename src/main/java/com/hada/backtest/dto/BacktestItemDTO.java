package com.hada.backtest.dto;

import com.hada.backtest.jpa.dto.PortfolioInputItemDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BacktestItemDTO {
    @NotBlank
    private String stock;
    @Positive
    @Max(1)
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
