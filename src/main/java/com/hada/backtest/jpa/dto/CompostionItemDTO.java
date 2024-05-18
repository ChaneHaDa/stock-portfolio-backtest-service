package com.hada.backtest.jpa.dto;

import com.hada.backtest.jpa.entity.PortfolioItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompostionItemDTO {
    private long id;
    private String stock;
    private double weight;

    public static CompostionItemDTO fromEntity(PortfolioItem entity) {
        return new CompostionItemDTO(entity.getId(), entity.getName() + ' ' + '(' + entity.getCode() + ')', entity.getWeight());
    }
}
