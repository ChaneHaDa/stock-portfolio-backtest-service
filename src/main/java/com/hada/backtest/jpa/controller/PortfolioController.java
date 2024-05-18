package com.hada.backtest.jpa.controller;

import com.hada.backtest.dto.BacktestInputDTO;
import com.hada.backtest.dto.BacktestItemDTO;
import com.hada.backtest.jpa.dto.CompostionDTO;
import com.hada.backtest.jpa.dto.PortfolioInputDTO;
import com.hada.backtest.jpa.entity.Portfolio;

import com.hada.backtest.jpa.service.PortfolioService;
import com.hada.backtest.jpa.service.SiteUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if(principal == null) {
            return "redirect:/user/login";
        }
        portfolioService.savePortfolio(portfolioInputDTO, principal.getName());
        return "redirect:/portfolio";
    }

    @GetMapping("")
    public String getPotfolios(Principal principal, Model model) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(siteUserService.getUser(principal.getName()));
        model.addAttribute("portfolios", portfolios);
        return "portfolio_index";
    }

    @PostMapping("/compositions")
    public String getPortfolio(@RequestParam("id") Long id, Model model) {
        CompostionDTO compostionDTO = portfolioService.getPortfolioComposition(id);
        model.addAttribute("compostionDTO", compostionDTO);
        return "portfolio_compositions";
    }

    @PostMapping("/backtest")
    public String getBacktest(@ModelAttribute CompostionDTO compostionDTO, Model model) {
        List<BacktestItemDTO> items = compostionDTO.getItems().stream().map(BacktestItemDTO::fromCompostionItemDTO).toList();
        BacktestInputDTO backtestInputDTO = new BacktestInputDTO("2020", "2023", 100000, items, items.size());
        model.addAttribute("backtestInputDTO", backtestInputDTO);
        return "backtest_portfolio";
    }

    @PostMapping("/update")
    public String updatePortfolio(@ModelAttribute CompostionDTO compostionDTO, Principal principal) {
        if(principal == null) {
            return "redirect:/user/login";
        }
        portfolioService.updatePortfolio(compostionDTO);

        return "redirect:/portfolio";
    }

    @PostMapping("/delete")
    public String deletePortfolio(@RequestParam("id") Long id) {
        portfolioService.delete(id);
        return "redirect:/portfolio";
    }
}
