package com.hada.backtest.jpa.controller;

import com.hada.backtest.dto.BacktestInputDTO;
import com.hada.backtest.dto.BacktestItemDTO;
import com.hada.backtest.jpa.dto.PortfolioInputDTO;
import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.service.PortfolioService;
import com.hada.backtest.jpa.service.SiteUserService;
import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final SiteUserService siteUserService;

    public PortfolioController(PortfolioService portfolioService, SiteUserService siteUserService) {
        this.portfolioService = portfolioService;
        this.siteUserService = siteUserService;
    }

    @PostMapping("/save")
    public String savePortfolio(@ModelAttribute PortfolioInputDTO portfolioInputDTO, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        portfolioService.savePortfolio(portfolioInputDTO, principal.getName());
        return "redirect:/portfolio";
    }

    @GetMapping("")
    public String getPotfolios(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(siteUserService.getUser(principal.getName()));
        model.addAttribute("portfolios", portfolios);
        return "portfolio_index";
    }

    @PostMapping("/compositions")
    public String getPortfolio(@RequestParam("id") Long id, Model model) {
        PortfolioInputDTO portfolioInputDTO = portfolioService.getPortfolioComposition(id);
        model.addAttribute("compostionDTO", portfolioInputDTO);
        return "portfolio_compositions";
    }

    @PostMapping("/backtest")
    public String getBacktest(@ModelAttribute PortfolioInputDTO portfolioInputDTO, Model model) {
        List<BacktestItemDTO> backtestItemDTOS = portfolioInputDTO.getPortfolioInputItemDTOS().stream()
                .map(BacktestItemDTO::fromPortfolioInoutItemDTO).toList();
        BacktestInputDTO backtestInputDTO = new BacktestInputDTO("2020", "2023", 100000, backtestItemDTOS,
                backtestItemDTOS.size());
        model.addAttribute("backtestInputDTO", backtestInputDTO);
        return "backtest_portfolio";
    }

    @PostMapping("/update")
    public String updatePortfolio(@ModelAttribute PortfolioInputDTO portfolioInputDTO, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        portfolioService.updatePortfolio(portfolioInputDTO);

        return "redirect:/portfolio";
    }

    @PostMapping("/delete")
    public String deletePortfolio(@RequestParam("id") Long id) {
        portfolioService.delete(id);
        return "redirect:/portfolio";
    }
}
