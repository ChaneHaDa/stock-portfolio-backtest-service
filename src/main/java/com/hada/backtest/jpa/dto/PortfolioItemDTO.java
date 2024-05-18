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

    public static PortfolioItemDTO fromPortfolioInputItemDTO(PortfolioInputItemDTO dto) {
        String stockName = dto.getStock();
        int indexOfParenthesis1 = stockName.lastIndexOf('(');
        int indexOfParenthesis2 = stockName.lastIndexOf(')');
        String code = stockName.substring(indexOfParenthesis1 + 1, indexOfParenthesis2);
        String name = stockName.substring(0, indexOfParenthesis1 - 1);
        return new PortfolioItemDTO(code, name, dto.getWeight());
    }
}
