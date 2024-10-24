package com.hada.backtest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculatorRorReturnDTO {
    
    private double ror;
    private long profit;

    public CalculatorRorReturnDTO() {
        ror = 0.0;
        profit = 0;
    }

    public CalculatorRorReturnDTO(double ror, long profit) {
        this.ror = ror;
        this.profit = profit;
    }
}
