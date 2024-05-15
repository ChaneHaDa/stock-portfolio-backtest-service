package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioComposition;
import com.hada.backtest.jpa.repository.PortfolioCompositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioCompositionService {
    private final PortfolioCompositionRepository portfolioCompositionRepository;

    public PortfolioCompositionService(PortfolioCompositionRepository portfolioCompositionRepository) {
        this.portfolioCompositionRepository = portfolioCompositionRepository;
    }

    public void saveAll(List<PortfolioComposition> portfolioCompositionList) {
        portfolioCompositionRepository.saveAll(portfolioCompositionList);
    }

    public List<PortfolioComposition> getPortfolioCompositions(Portfolio portfolio) {
        return portfolioCompositionRepository.findByPortfolio(portfolio);
    }

    public void deleteAll(Portfolio portfolio) {
        List<PortfolioComposition> portfolioCompositionList = portfolioCompositionRepository.findByPortfolio(portfolio);
        portfolioCompositionRepository.deleteAll(portfolioCompositionList);
    }
}
