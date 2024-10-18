package com.hada.backtest.jpa.repository;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {
    List<PortfolioItem> findByPortfolio(Portfolio portfolio);
}
