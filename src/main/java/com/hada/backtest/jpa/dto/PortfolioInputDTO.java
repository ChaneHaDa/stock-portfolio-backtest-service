package com.hada.backtest.jpa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioInputDTO {
    private String name;
    private String description;
    private List<PortfolioItemDTO> items;

    public PortfolioInputDTO() {
    }

    public PortfolioInputDTO(String name, String description, List<PortfolioItemDTO> items) {
        this.name = name;
        this.description = description;
        this.items = items;
    }
}
