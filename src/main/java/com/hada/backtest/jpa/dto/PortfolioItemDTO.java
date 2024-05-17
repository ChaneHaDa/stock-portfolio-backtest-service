package com.hada.backtest.jpa.dto;

import com.hada.backtest.jpa.entity.PortfolioItem;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PortfolioItemDTO {
    private String code;
    private String name;
    private double weight;

    public PortfolioItemDTO() {
    }

    public PortfolioItemDTO(String code, String name, double weight) {
        this.code = code;
        this.name = name;
        this.weight = weight;
    }

    public static PortfolioItem fromDTO(PortfolioItemDTO dto) {
        return new PortfolioItem(dto.getCode(), dto.getName(), dto.getWeight(), null);
    }
}
