package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.dto.PortfolioInputDTO;
import com.hada.backtest.jpa.dto.PortfolioItemDTO;
import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioItem;
import com.hada.backtest.jpa.repository.PortfolioItemRepository;
import com.hada.backtest.jpa.repository.PortfolioRepository;
import com.hada.backtest.jpa.entity.SiteUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, PortfolioItemRepository portfolioItemRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public void savePortfolio(PortfolioInputDTO portfolioInputDTO, SiteUser user) {
        List<PortfolioItem> portfolioItemList = portfolioInputDTO.getItems().stream()
                .map(PortfolioItemDTO::fromDTO)
                .toList();
        Portfolio portfolio = new Portfolio(portfolioInputDTO.getName(), portfolioInputDTO.getDescription(), user);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        portfolioItemList.forEach(portfolioItem -> portfolioItem.setPortfolio(savedPortfolio));
        portfolioItemRepository.saveAll(portfolioItemList);
    }

    public void save(Portfolio portfolio1) {
        portfolioRepository.save(portfolio1);
    }

    public List<Portfolio> getPortfoliosByUser(SiteUser user) {
        return portfolioRepository.findBySiteUser(user);
    }

    public Portfolio getPortfolio(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }

    public void delete(Long portfolioId) {
        portfolioRepository.deleteById(portfolioId);
    }
}
