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
    List<BacktestItemDTO> backtestItemDTOS;
    private int backtestItemDTOSize;

    public BacktestInputDTO() {
    }

    public BacktestInputDTO(String startDate, String endDate, double startAmount,
                            List<BacktestItemDTO> backtestItemDTOS,
                            int backtestItemDTOSize) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startAmount = startAmount;
        this.backtestItemDTOS = backtestItemDTOS;
        this.backtestItemDTOSize = backtestItemDTOSize;
    }
}
