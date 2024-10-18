package com.hada.backtest.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BacktestReturnDTO {
    
    private double endAmount;
    private double totalRor;
    private double maxRor;
    private double minRor;
    private List<String> stockList;

    public BacktestReturnDTO() {
    }

    public BacktestReturnDTO(double endAmount, double totalRor, double maxRor, double minRor, List<String> stockList) {
        this.endAmount = endAmount;
        this.totalRor = totalRor;
        this.maxRor = maxRor;
        this.minRor = minRor;
        this.stockList = stockList;
    }
}
