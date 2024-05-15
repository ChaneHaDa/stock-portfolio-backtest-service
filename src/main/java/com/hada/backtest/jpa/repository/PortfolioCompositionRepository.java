package com.hada.backtest.jpa.repository;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioComposition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioCompositionRepository extends JpaRepository<PortfolioComposition, Long> {
    List<PortfolioComposition> findByPortfolio(Portfolio portfolio);
}
