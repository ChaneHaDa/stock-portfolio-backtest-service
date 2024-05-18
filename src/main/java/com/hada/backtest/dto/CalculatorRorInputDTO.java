package com.hada.backtest.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculatorRorInputDTO {

    @PositiveOrZero
    private double buyPrice;
    @PositiveOrZero
    private double sellPrice;
    @PositiveOrZero
    private long quantity;

    public CalculatorRorInputDTO() {
        buyPrice = 0.0;
        sellPrice = 0.0;
        quantity = 0;
    }

    public CalculatorRorInputDTO(double buyPrice, double sellPrice, long quantity) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }

}
