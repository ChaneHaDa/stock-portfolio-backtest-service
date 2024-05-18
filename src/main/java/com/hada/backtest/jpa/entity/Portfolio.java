package com.hada.backtest.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser siteUser;

    public Portfolio() {
    }

    public Portfolio(String name, String description, SiteUser siteUser) {
        this.name = name;
        this.description = description;
        this.siteUser = siteUser;
    }
}
