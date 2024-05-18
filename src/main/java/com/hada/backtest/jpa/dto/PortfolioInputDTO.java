package com.hada.backtest.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioInputDTO {
    private long id;
    private String name;
    private String description;
    private List<PortfolioInputItemDTO> items;
    private int size;
}
