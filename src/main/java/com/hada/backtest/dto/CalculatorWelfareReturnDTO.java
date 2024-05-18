package com.hada.backtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CalculatorWelfareReturnDTO {
    private double totalProfit;
    private double finalAmount;
    private List<Double> revenueList;
    private List<Double> amountList;
    private List<Double> rorList;

    public CalculatorWelfareReturnDTO() {
        totalProfit = 0.0;
        finalAmount = 0.0;
    }

    public CalculatorWelfareReturnDTO(double totalProfit, double finalAmount, List<Double> revenueList, List<Double> amountList, List<Double> rorList) {
        this.totalProfit = totalProfit;
        this.finalAmount = finalAmount;
        this.revenueList = revenueList;
        this.amountList = amountList;
        this.rorList = rorList;
    }
}
