package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.dto.PortfolioInputDTO;
import com.hada.backtest.jpa.dto.PortfolioInputItemDTO;
import com.hada.backtest.jpa.dto.PortfolioItemDTO;
import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioItem;
import com.hada.backtest.jpa.entity.SiteUser;
import com.hada.backtest.jpa.repository.PortfolioItemRepository;
import com.hada.backtest.jpa.repository.PortfolioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioItemRepository portfolioItemRepository;
    private final SiteUserService siteUserService;

    public PortfolioService(PortfolioRepository portfolioRepository, PortfolioItemRepository portfolioItemRepository,
                            SiteUserService siteUserService) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.siteUserService = siteUserService;
    }

    public void savePortfolio(PortfolioInputDTO portfolioInputDTO, String username) {
        List<PortfolioItemDTO> portfolioItemDTOList = portfolioInputDTO.getPortfolioInputItemDTOS().stream()
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

    public PortfolioInputDTO getPortfolioComposition(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElse(null);
        List<PortfolioItem> portfolioItems = portfolioItemRepository.findByPortfolio(portfolio);
        List<PortfolioInputItemDTO> portfolioItemDTOList = portfolioItems.stream()
                .map(PortfolioInputItemDTO::fromEntity)
                .toList();
        return new PortfolioInputDTO(portfolio.getId(), portfolio.getName(), portfolio.getDescription(),
                portfolioItemDTOList, portfolioItemDTOList.size());
    }


    public List<Portfolio> getPortfoliosByUser(SiteUser user) {
        return portfolioRepository.findBySiteUser(user);
    }

    public void delete(Long id) {
        portfolioRepository.deleteById(id);
    }

    public void updatePortfolio(PortfolioInputDTO portfolioInputDTO) {
        Portfolio portfolio = portfolioRepository.findById(portfolioInputDTO.getId()).orElse(null);
        List<PortfolioItem> portfolioItems = portfolioItemRepository.findByPortfolio(portfolio);
        portfolio.setName(portfolioInputDTO.getName());
        portfolio.setDescription(portfolioInputDTO.getDescription());

        for (int i = portfolioInputDTO.getPortfolioInputItemDTOSize(); i < portfolioItems.size(); i++) {
            PortfolioItem portfolioItem = portfolioItems.get(i);
            portfolioItemRepository.delete(portfolioItem);
            portfolioItems.remove(i);
        }

        for (int i = 0; i < portfolioInputDTO.getPortfolioInputItemDTOS().size(); i++) {
            PortfolioInputItemDTO compostionItemDTO = portfolioInputDTO.getPortfolioInputItemDTOS().get(i);
            String stockName = compostionItemDTO.getStockName();
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
