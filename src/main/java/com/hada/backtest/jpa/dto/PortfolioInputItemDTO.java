package com.hada.backtest.jpa.dto;

import com.hada.backtest.jpa.entity.PortfolioItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioInputItemDTO {

    private long id;
    private String stock;
    private double weight;

    public PortfolioInputItemDTO() {
        id = 0;
        stock = "";
        weight = 0;
    }

    public PortfolioInputItemDTO(long id, String stock, double weight) {
        this.id = id;
        this.stock = stock;
        this.weight = weight;
    }

    public static PortfolioInputItemDTO fromEntity(PortfolioItem entity) {
        return new PortfolioInputItemDTO(entity.getId(), entity.getName() + ' ' + '(' + entity.getCode() + ')',
                entity.getWeight());
    }
}
