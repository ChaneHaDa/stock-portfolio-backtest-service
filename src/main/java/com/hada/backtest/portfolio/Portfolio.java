package com.hada.backtest.portfolio;

import com.hada.backtest.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String portfolioName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser username;
}
