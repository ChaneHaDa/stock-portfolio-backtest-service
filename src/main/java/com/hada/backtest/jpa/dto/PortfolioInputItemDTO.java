package com.hada.backtest.jpa.dto;

import com.hada.backtest.jpa.entity.PortfolioItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioInputItemDTO {

    private long id;
    private String stockName;
    private double weight;

    public PortfolioInputItemDTO() {
        id = 0;
        stockName = "";
        weight = 0;
    }

    public PortfolioInputItemDTO(long id, String stockName, double weight) {
        this.id = id;
        this.stockName = stockName;
        this.weight = weight;
    }

    public static PortfolioInputItemDTO fromEntity(PortfolioItem entity) {
        return new PortfolioInputItemDTO(entity.getId(), entity.getName() + ' ' + '(' + entity.getCode() + ')',
                entity.getWeight());
    }
}
