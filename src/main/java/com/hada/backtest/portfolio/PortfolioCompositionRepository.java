package com.hada.backtest.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioCompositionRepository extends JpaRepository<PortfolioComposition, Long> {
    List<PortfolioComposition> findByPortfolio(Portfolio portfolio);
}
