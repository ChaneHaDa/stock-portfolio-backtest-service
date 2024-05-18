package com.hada.backtest.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioInputDTO {
    private long id;
    private String name;
    private String description;
    private List<PortfolioInputItemDTO> items;
    private int size;

    public PortfolioInputDTO() {
        id = 0;
        name = "";
        description = "";
        size = 0;
    }

    public PortfolioInputDTO(long id, String name, String description, List<PortfolioInputItemDTO> items, int size) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;
        this.size = size;
    }
}
