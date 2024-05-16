package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioItem;
import com.hada.backtest.jpa.repository.PortfolioCompositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioCompositionService {
    private final PortfolioCompositionRepository portfolioCompositionRepository;

    public PortfolioCompositionService(PortfolioCompositionRepository portfolioCompositionRepository) {
        this.portfolioCompositionRepository = portfolioCompositionRepository;
    }

    public void saveAll(List<PortfolioItem> portfolioItemList) {
        portfolioCompositionRepository.saveAll(portfolioItemList);
    }

    public List<PortfolioItem> getPortfolioCompositions(Portfolio portfolio) {
        return portfolioCompositionRepository.findByPortfolio(portfolio);
    }

    public void deleteAll(Portfolio portfolio) {
        List<PortfolioItem> portfolioItemList = portfolioCompositionRepository.findByPortfolio(portfolio);
        portfolioCompositionRepository.deleteAll(portfolioItemList);
    }
}
