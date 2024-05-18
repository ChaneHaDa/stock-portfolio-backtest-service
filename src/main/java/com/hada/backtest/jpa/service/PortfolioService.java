package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.dto.*;
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
    private final SiteUserService siteUserService;

    public PortfolioService(PortfolioRepository portfolioRepository, PortfolioItemRepository portfolioItemRepository, SiteUserService siteUserService) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.siteUserService = siteUserService;
    }

    public void savePortfolio(PortfolioInputDTO portfolioInputDTO, String username) {
        List<PortfolioItemDTO> portfolioItemDTOList = portfolioInputDTO.getItems().stream()
                .map(PortfolioItemDTO::fromPortfolioInputItemDTO)
                .toList();
        List<PortfolioItem> portfolioItemList = portfolioItemDTOList.stream()
                .map(PortfolioItemDTO::fromDTO)
                .toList();
        SiteUser user = siteUserService.getUser(username);
        Portfolio portfolio = new Portfolio(portfolioInputDTO.getName(), portfolioInputDTO.getDescription(), user);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        portfolioItemList.forEach(portfolioItem -> portfolioItem.setPortfolio(savedPortfolio));
        portfolioItemRepository.saveAll(portfolioItemList);
    }

    public CompostionDTO getPortfolioComposition(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElse(null);
        List<PortfolioItem> portfolioItems = portfolioItemRepository.findByPortfolio(portfolio);
        List<CompostionItemDTO> portfolioItemDTOList = portfolioItems.stream()
                .map(CompostionItemDTO::fromEntity)
                .toList();
        return new CompostionDTO(portfolio.getId(), portfolio.getName(), portfolio.getDescription(), portfolioItemDTOList, portfolioItemDTOList.size());
    }


    public List<Portfolio> getPortfoliosByUser(SiteUser user) {
        return portfolioRepository.findBySiteUser(user);
    }

    public void delete(Long id) {
        portfolioRepository.deleteById(id);
    }

    public void updatePortfolio(CompostionDTO compostionDTO) {
        Portfolio portfolio = portfolioRepository.findById(compostionDTO.getId()).orElse(null);
        List<PortfolioItem> portfolioItems = portfolioItemRepository.findByPortfolio(portfolio);
        portfolio.setName(compostionDTO.getName());
        portfolio.setDescription(compostionDTO.getDescription());

        for(int i = compostionDTO.getSize(); i < portfolioItems.size(); i++) {
            PortfolioItem portfolioItem = portfolioItems.get(i);
            portfolioItemRepository.delete(portfolioItem);
            portfolioItems.remove(i);
        }

        for (int i = 0; i < compostionDTO.getItems().size(); i++) {
            CompostionItemDTO compostionItemDTO = compostionDTO.getItems().get(i);
            String stockName = compostionItemDTO.getStock();
            int indexOfParenthesis1 = stockName.lastIndexOf('(');
            int indexOfParenthesis2 = stockName.lastIndexOf(')');
            String code = stockName.substring(indexOfParenthesis1 + 1, indexOfParenthesis2);
            String name = stockName.substring(0, indexOfParenthesis1 - 1);

            if (i < portfolioItems.size()) {
                PortfolioItem portfolioItem = portfolioItems.get(i);
                portfolioItem.setWeight(compostionItemDTO.getWeight());
                portfolioItem.setCode(code);
                portfolioItem.setName(name);
            } else {
                PortfolioItem portfolioItem = new PortfolioItem(code, name, compostionItemDTO.getWeight(), portfolio);
                portfolioItems.add(portfolioItem);
            }
        }

        portfolioRepository.save(portfolio);
        portfolioItemRepository.saveAll(portfolioItems);
    }
}
