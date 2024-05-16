package com.hada.backtest.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PortfolioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private double allocation;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

}
