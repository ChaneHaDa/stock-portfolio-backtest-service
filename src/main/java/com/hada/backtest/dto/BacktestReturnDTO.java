package com.hada.backtest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BacktestReturnDTO {
    private double endAmount;
    private double totalRor;
    private double maxRor;
    private double minRor;
    private List<String> stockList;
}
