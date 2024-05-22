package com.hada.backtest.jpa.dto;

import com.hada.backtest.jpa.entity.PortfolioItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioInputItemDTO {
    private long id;
    @NotBlank
    private String stock;
    @Positive
    @Max(1)
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
        return new PortfolioInputItemDTO(entity.getId(), entity.getName() + ' ' + '(' + entity.getCode() + ')', entity.getWeight());
    }
}
