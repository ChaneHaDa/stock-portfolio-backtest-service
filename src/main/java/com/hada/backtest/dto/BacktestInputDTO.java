package com.hada.backtest.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BacktestInputDTO {
    
    private String startDate;
    private String endDate;
    private double startAmount;
    List<BacktestItemDTO> items;
    private int size;

    public BacktestInputDTO() {
    }

    public BacktestInputDTO(String startDate, String endDate, double startAmount, List<BacktestItemDTO> items,
                            int size) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startAmount = startAmount;
        this.items = items;
        this.size = size;
    }
}
