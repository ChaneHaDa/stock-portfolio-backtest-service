package com.hada.backtest.jpa.repository;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findBySiteUser(SiteUser username);
}
