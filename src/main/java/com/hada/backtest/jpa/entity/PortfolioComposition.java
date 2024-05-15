package com.hada.backtest.jpa.entity;

import com.hada.backtest.jpa.entity.Portfolio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PortfolioComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String itmsNm;

    private String srtnCd;

    private double allocation;

}
