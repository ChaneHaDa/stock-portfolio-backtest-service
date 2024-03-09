package com.hada.backtest.portfolio;

import com.hada.backtest.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByUsername(SiteUser username);

}
