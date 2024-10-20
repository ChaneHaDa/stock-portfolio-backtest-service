package com.hada.backtest.jpa.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioInputDTO {

    private long id;
    private String name;
    private String description;
    private List<PortfolioInputItemDTO> portfolioInputItemDTOS;
    private int portfolioInputItemDTOSize;

    public PortfolioInputDTO() {
        id = 0;
        name = "";
        description = "";
        portfolioInputItemDTOSize = 0;
    }

    public PortfolioInputDTO(long id, String name, String description,
                             List<PortfolioInputItemDTO> portfolioInputItemDTOS, int portfolioInputItemDTOSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.portfolioInputItemDTOS = portfolioInputItemDTOS;
        this.portfolioInputItemDTOSize = portfolioInputItemDTOSize;
    }
}
