package com.hada.backtest.jpa.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioInputDTO {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Valid
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
