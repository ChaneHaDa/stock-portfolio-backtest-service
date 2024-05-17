package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioItem;
import com.hada.backtest.jpa.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioItemService {
    private final PortfolioItemRepository portfolioItemRepository;

    public PortfolioItemService(PortfolioItemRepository portfolioItemRepository) {
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public void saveAll(List<PortfolioItem> portfolioItemList) {
        portfolioItemRepository.saveAll(portfolioItemList);
    }

    public List<PortfolioItem> getPortfolioCompositions(Portfolio portfolio) {
        return portfolioItemRepository.findByPortfolio(portfolio);
    }

    public void deleteAll(Portfolio portfolio) {
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findByPortfolio(portfolio);
        portfolioItemRepository.deleteAll(portfolioItemList);
    }
}
