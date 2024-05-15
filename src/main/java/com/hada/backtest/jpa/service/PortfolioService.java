package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.repository.PortfolioRepository;
import com.hada.backtest.jpa.entity.SiteUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public void save(Portfolio portfolio1) {
        portfolioRepository.save(portfolio1);
    }

    public List<Portfolio> getPortfoliosByUser(SiteUser user) {
        return portfolioRepository.findByUsername(user);
    }

    public Portfolio getPortfolio(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }

    public void delete(Long portfolioId) {
        portfolioRepository.deleteById(portfolioId);
    }
}
