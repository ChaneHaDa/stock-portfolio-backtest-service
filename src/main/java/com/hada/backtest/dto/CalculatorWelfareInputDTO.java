package com.hada.backtest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculatorWelfareInputDTO {
    
    private double price;
    private long term;
    private double ror;

    public CalculatorWelfareInputDTO() {
        price = 0.0;
        term = 0;
        ror = 0.0;
    }

    public CalculatorWelfareInputDTO(double price, long term, double ror) {
        this.price = price;
        this.term = term;
        this.ror = ror;
    }
}
